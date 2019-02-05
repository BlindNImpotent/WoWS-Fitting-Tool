package WoWSFT.parser;

import WoWSFT.model.gameparams.TypeInfo;
import WoWSFT.model.gameparams.commander.Commander;
import WoWSFT.model.gameparams.consumable.Consumable;
import WoWSFT.model.gameparams.modernization.Modernization;
import WoWSFT.model.gameparams.ship.Ship;
import WoWSFT.model.gameparams.ship.ShipIndex;
import WoWSFT.model.gameparams.ship.component.airdefense.AirDefense;
import WoWSFT.model.gameparams.ship.component.artillery.Artillery;
import WoWSFT.model.gameparams.ship.component.atba.ATBA;
import WoWSFT.model.gameparams.ship.component.engine.Engine;
import WoWSFT.model.gameparams.ship.component.firecontrol.FireControl;
import WoWSFT.model.gameparams.ship.component.hull.Hull;
import WoWSFT.model.gameparams.ship.component.torpedo.Torpedo;
import WoWSFT.model.gameparams.ship.upgrades.ShipUpgrade;
import WoWSFT.utils.CommonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static WoWSFT.model.Constant.*;

/**
 * Created by Qualson-Lee on 2016-11-15.
 */
@Slf4j
public class JsonParser
{
    @Autowired
    @Qualifier (value = "notification")
    private LinkedHashMap<String, String> notification;

    @Autowired
    @Qualifier (value = "nameToId")
    private HashMap<String, String> nameToId;

    @Autowired
    @Qualifier (value = "idToName")
    private HashMap<String, String> idToName;

    @Autowired
    @Qualifier (value = "gameParamsHM")
    private HashMap<String, Object> gameParamsHM;

    @Autowired
    @Qualifier (value = "global")
    private HashMap<String, HashMap<String, Object>> global;

    private ObjectMapper mapper = new ObjectMapper();

    private static HashSet<String> excludeShipGroups = new HashSet<>(Arrays.asList("unavailable", "disabled", "preserved", "clan"));
    private static HashSet<String> excludeShipNations = new HashSet<>(Arrays.asList("Events", "disabled", "preserved", "clan"));
    private static HashSet<String> excludeShipSpecies = new HashSet<>(Arrays.asList("Auxiliary", "Submarine"));
    private static HashSet<String> excludeCompStats = new HashSet<>(Arrays.asList("directors", "finders", "radars"));
    private static HashSet<String> excludeModernization = new HashSet<>(Arrays.asList("extra"));

    private static HashSet<String> coeff = new HashSet<>(Arrays.asList("coef", "maxdist", "idealradius"));
    private static HashSet<String> noUnit = new HashSet<>(Arrays.asList("num"));
    private static HashSet<String> extra = new HashSet<>(Arrays.asList("count", "level", "additional"));
    private static HashSet<String> meter = new HashSet<>(Arrays.asList("radius", "height", "dist"));
    private static HashSet<String> rate = new HashSet<>(Arrays.asList("regeneration", "boostcoeff", "probabilitybonus", "chance"));
    private static HashSet<String> rateNoSym = new HashSet<>(Arrays.asList("step"));
    private static HashSet<String> multiple = new HashSet<>(Arrays.asList("multiplier"));
    private static HashSet<String> extraAngle = new HashSet<>(Arrays.asList("gunbonus"));
    private static HashSet<String> angle = new HashSet<>(Arrays.asList("angle"));
    private static HashSet<String> time = new HashSet<>(Arrays.asList("time"));
    private static HashSet<String> globalLanguage = new HashSet<>(Arrays.asList("en", "kr"));
    private static HashSet<String> speed = new HashSet<>(Arrays.asList("speedbonus"));

    @Async
    public void setNotification() throws IOException
    {
        log.info("Setting up notification");

        Resource notificationFile = new ClassPathResource("/json/notification/notification.json");

        LinkedHashMap<String, String> temp = mapper.readValue(notificationFile.getURL(), new TypeReference<LinkedHashMap<String, String>>(){});

        notification.putAll(temp);
        temp.clear();
    }

//    @Async
    public void setGlobal() throws IOException
    {
        log.info("Setting up Global");

        for (String language : globalLanguage) {
            Resource GlobalFile = new ClassPathResource("/json/live/global-" + language + ".json");
            HashMap<String, Object> temp = mapper.readValue(GlobalFile.getInputStream(), new TypeReference<HashMap<String, Object>>() {});
            global.put(language, temp);
        }
    }

//    @Async
    public void setGameParams() throws IOException
    {
        log.info("Setting up GameParams");

        Resource GameParamsFile = new ClassPathResource("/json/live/GameParams.json");

        LinkedHashMap<String, LinkedHashMap<String, Object>> temp = mapper.readValue(GameParamsFile.getInputStream(), new TypeReference<LinkedHashMap<String, LinkedHashMap<String, Object>>>(){});

        LinkedHashMap<String, Ship> tempShips = new LinkedHashMap<>();
        LinkedHashMap<String, Consumable> tempConsumables = new LinkedHashMap<>();
        LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<Integer, List<ShipIndex>>>>> shipsList = new LinkedHashMap<>();
        LinkedHashMap<Integer, LinkedHashMap<String, Modernization>> tempUpgrades = new LinkedHashMap<>();
        LinkedHashMap<String, Commander> tempCommanders = new LinkedHashMap<>();

        for (int i = 0; i < 6; i++) {
            tempUpgrades.put(i, new LinkedHashMap<>());
        }

        temp.forEach((key, value) -> {
            TypeInfo typeInfo = mapper.convertValue(value.get("typeinfo"), TypeInfo.class);

            if (typeInfo.getType().equalsIgnoreCase("Ship") && !excludeShipNations.contains(typeInfo.getNation()) && !excludeShipSpecies.contains(typeInfo.getSpecies())) {
                Ship ship = mapper.convertValue(value, Ship.class);
                if (!excludeShipGroups.contains(ship.getGroup()) && StringUtils.isEmpty(ship.getDefaultCrew())) {
                    addShips(ship, tempShips, shipsList);
                }
            } else if (typeInfo.getType().equalsIgnoreCase("Modernization")) {
                Modernization modernization = mapper.convertValue(value, Modernization.class);
                if (modernization.getSlot() >= 0) {
                    setBonusParams(key, mapper.convertValue(modernization, new TypeReference<LinkedHashMap<String, Object>>(){}), modernization.getBonus(), "modernization");
                    tempUpgrades.get(modernization.getSlot()).put(modernization.getName(), modernization);
                }
            } else if (typeInfo.getType().equalsIgnoreCase("Ability") && !excludeShipNations.contains(typeInfo.getNation()) && !key.contains("Super")) {
                Consumable consumable = mapper.convertValue(value, Consumable.class);
                consumable.getSubConsumables().forEach((sub, val) -> setBonusParams(key, mapper.convertValue(val, new TypeReference<LinkedHashMap<String, Object>>(){}), val.getBonus(), "consumable"));
                tempConsumables.put(key, consumable);
            } else if (typeInfo.getType().equalsIgnoreCase("Crew")) {
                Commander commander = mapper.convertValue(value, Commander.class);
                if (commander.getName().contains("DefaultCrew") || commander.getCrewPersonality().isUnique()) {
                    commander.getCSkills().forEach(row -> row.forEach(skill -> setBonusParams(key, mapper.convertValue(skill, new TypeReference<LinkedHashMap<String, Object>>(){}), skill.getBonus(), "commander")));
                    tempCommanders.put(key, commander);
                }
            }
//            nameToId.put(key, String.valueOf(value.get("id")));
//            idToName.put(String.valueOf(value.get("id")), key);
//            gameParamsHM.put(String.valueOf(value.get("id")), value);
        });

        gameParamsHM.put(TYPE_SHIP, tempShips);
        gameParamsHM.put(TYPE_SHIP_LIST, sortShipsList(tempShips, shipsList));
        gameParamsHM.put(TYPE_UPGRADE, sortUpgrades(tempUpgrades));
        gameParamsHM.put(TYPE_CONSUMABLE, tempConsumables);
        gameParamsHM.put(TYPE_COMMANDER, tempCommanders);

        temp.clear();
    }

    private void addShips(Ship ship, LinkedHashMap<String, Ship> tempShips, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<Integer, List<ShipIndex>>>>> shipsList)
    {
        sortShipUpgradeInfo(ship);
        setRealShipType(ship);
        setRows(ship);

        tempShips.put(ship.getIndex(), ship);

        addToShipsList(ship, shipsList);
    }

    private void sortShipUpgradeInfo(Ship ship)
    {
        ship.getShipUpgradeInfo().getComponents().forEach((key, value) -> {
            value.forEach(upgrade -> {
                if (upgrade.getPosition() == 3 && ship.getShipUpgradeInfo().getComponents().get(key).size() < 3) {
                    upgrade.setPosition(2);
                }

                upgrade.getComponents().forEach((cKey, cValue) -> {
                    if (!excludeCompStats.contains(cKey)) {
                        ship.getCompStats().putIfAbsent(cKey, new LinkedHashMap<>());

                        if (cKey.equalsIgnoreCase(artillery)) {
                            cValue.forEach(cVal -> ship.getCompStats().get(cKey).put(cVal, mapper.convertValue(ship.getComponents().get(cVal), Artillery.class)));
                        } else if (cKey.equalsIgnoreCase(airDefense)) {
                            cValue.forEach(cVal -> ship.getCompStats().get(cKey).put(cVal, mapper.convertValue(ship.getComponents().get(cVal), AirDefense.class)));
                        } else if (cKey.equalsIgnoreCase(atba)) {
                            cValue.forEach(cVal -> ship.getCompStats().get(cKey).put(cVal, mapper.convertValue(ship.getComponents().get(cVal), ATBA.class)));
                        } else if (cKey.equalsIgnoreCase(engine)) {
                            cValue.forEach(cVal -> ship.getCompStats().get(cKey).put(cVal, mapper.convertValue(ship.getComponents().get(cVal), Engine.class)));
                        } else if (cKey.equalsIgnoreCase(fireControl)) {
                            cValue.forEach(cVal -> ship.getCompStats().get(cKey).put(cVal, mapper.convertValue(ship.getComponents().get(cVal), FireControl.class)));
                        } else if (cKey.equalsIgnoreCase(hull)) {
                            cValue.forEach(cVal -> ship.getCompStats().get(cKey).put(cVal, mapper.convertValue(ship.getComponents().get(cVal), Hull.class)));
                        } else if (cKey.equalsIgnoreCase(torpedoes)) {
                            cValue.forEach(cVal -> ship.getCompStats().get(cKey).put(cVal, mapper.convertValue(ship.getComponents().get(cVal), Torpedo.class)));
                        } else {
                            cValue.forEach(cVal -> ship.getCompStats().get(cKey).put(cVal, ship.getComponents().get(cVal)));
                        }
                    }
                });
            });
            value.sort(Comparator.comparingInt(ShipUpgrade::getPosition).thenComparing(ShipUpgrade::getName));
        });

        ship.getShipUpgradeInfo().getComponents().forEach((key, value) -> {
            value.forEach(upgrade -> {
                if (StringUtils.isNotEmpty(upgrade.getPrev())) {
                    for (Map.Entry<String, List<ShipUpgrade>> entry : ship.getShipUpgradeInfo().getComponents().entrySet()) {
                        ShipUpgrade tSU = entry.getValue().stream().filter(v -> v.getName().equalsIgnoreCase(upgrade.getPrev())).findFirst().orElse(null);
                        if (tSU != null) {
                            upgrade.setPrevType(tSU.getUcTypeShort());
                            upgrade.setPrevPosition(tSU.getPosition());
                            break;
                        }
                    }
                }
            });
        });

        ship.setComponents(new LinkedHashMap<>());
    }

    private void setRealShipType(Ship ship)
    {
        if ("upgradeable".equalsIgnoreCase(ship.getGroup()) || "start".equalsIgnoreCase(ship.getGroup())) {
            ship.setRealShipType(ship.getTypeinfo().getSpecies());
            ship.setResearch(true);
        } else {
            ship.setRealShipType("Premium");
        }
    }

    private void setRows(Ship ship)
    {
        LinkedHashMap<String, Integer> colCount = new LinkedHashMap<>();

        int maxRows = 0;
        for (int i = 1; i <= 3; i++) {
            int pos = i;
            AtomicBoolean hasRow = new AtomicBoolean(false);
            ship.getShipUpgradeInfo().getComponents().forEach((key, compList) -> {
                colCount.putIfAbsent(key, 0);
                int posCount = (int) compList.stream().filter(c -> c.getPosition() == pos).count();

                if (posCount > 0) {
                    hasRow.set(true);
                }

                if (colCount.get(key) < posCount) {
                    colCount.put(key, posCount);
                }
            });

            if (hasRow.get()) {
                maxRows++;
            }
        }
        ship.getShipUpgradeInfo().setCols(colCount).setMaxRows(maxRows);
    }

    private void addToShipsList(Ship ship, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<Integer, List<ShipIndex>>>>> shipsList)
    {
        shipsList.putIfAbsent(ship.getTypeinfo().getNation().toUpperCase(), new LinkedHashMap<>());
        shipsList.get(ship.getTypeinfo().getNation().toUpperCase()).putIfAbsent(ship.getRealShipTypeId().toUpperCase(), new LinkedHashMap<>());
        shipsList.get(ship.getTypeinfo().getNation().toUpperCase()).get(ship.getRealShipTypeId().toUpperCase()).putIfAbsent(ship.getTypeinfo().getSpecies().toUpperCase(), new LinkedHashMap<>());
        shipsList.get(ship.getTypeinfo().getNation().toUpperCase()).get(ship.getRealShipTypeId().toUpperCase()).get(ship.getTypeinfo().getSpecies().toUpperCase()).putIfAbsent(ship.getLevel(), new ArrayList<>());

        shipsList.get(ship.getTypeinfo().getNation().toUpperCase()).get(ship.getRealShipTypeId().toUpperCase()).get(ship.getTypeinfo().getSpecies().toUpperCase()).get(ship.getLevel())
                .add(new ShipIndex(ship.getName(), ship.getIndex(), "", ship.isResearch()));
    }

    private LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<Integer, List<ShipIndex>>>>> sortShipsList(
            LinkedHashMap<String, Ship> tempShips, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<Integer, List<ShipIndex>>>>> shipsList)
    {
        shipsList.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(nation -> {
            nation.getValue().entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(realShipType -> {
                realShipType.getValue().forEach((shipType, tiers) -> {
                    tiers.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(tier -> {
                        shipsList.get(nation.getKey()).get(realShipType.getKey()).get(shipType).remove(tier.getKey());
                        shipsList.get(nation.getKey()).get(realShipType.getKey()).get(shipType).put(tier.getKey(), tier.getValue());
                    });

                    int tier = 10;
                    while (tier > 0) {
                        if (CollectionUtils.isNotEmpty(tiers.get(tier))) {
                            tiers.get(tier).sort(Comparator.comparing(ShipIndex::getIndex));

                            int cTier = tier;
                            AtomicInteger pos = new AtomicInteger(1);
                            tiers.get(tier).forEach(ship -> {
                                if (ship.isResearch()) {
                                    if (ship.getPosition() == 0) {
                                        ship.setPosition(pos.getAndIncrement());
                                    }

                                    if (CollectionUtils.isNotEmpty(shipsList.get(nation.getKey()).get(realShipType.getKey()).get(shipType).get(cTier - 1))) {
                                        shipsList.get(nation.getKey()).get(realShipType.getKey()).get(shipType).get(cTier - 1).forEach(tShip -> {
                                            if (tempShips.get(tShip.getIndex()).getTypeinfo().getSpecies().equalsIgnoreCase(shipType)) {
                                                tempShips.get(tShip.getIndex()).getShipUpgradeInfo().getComponents().forEach((comp, list) -> list.forEach(u1 -> {
                                                    if (u1.getNextShips().contains(ship.getIdentifier())) {
                                                        tempShips.get(ship.getIndex()).setPrevShipIndex(tShip.getIndex());

                                                        if (list.stream().filter(u2 -> CollectionUtils.isNotEmpty(u2.getNextShips())).count() == 1) {
                                                            tShip.setPosition(ship.getPosition());
                                                        }
                                                    }
                                                }));
                                            }
                                        });
                                    }
                                }
                            });
                            tiers.get(tier).sort(Comparator.comparingInt(ShipIndex::getPosition));
                        }
                        tier--;
                    }
                });
                shipsList.get(nation.getKey()).remove(realShipType.getKey());
                shipsList.get(nation.getKey()).put(realShipType.getKey(), realShipType.getValue());
            });
            shipsList.remove(nation.getKey());
            shipsList.put(nation.getKey(), nation.getValue());
        });

        return shipsList;
    }

    private LinkedHashMap<Integer, LinkedHashMap<String, Modernization>> sortUpgrades(LinkedHashMap<Integer, LinkedHashMap<String, Modernization>> upgrades)
    {
        upgrades.forEach((slot, mod) -> mod.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(u -> {
            upgrades.get(slot).remove(u.getKey());
            upgrades.get(slot).put(u.getKey(), u.getValue());
        }));

        return upgrades;
    }

    private void setBonusParams(String key, LinkedHashMap<String, Object> tempCopy, LinkedHashMap<String, String> bonus, String type)
    {
        tempCopy.forEach((param, cVal) -> {
            if ("modernization".equalsIgnoreCase(type)) {
                if (cVal instanceof Float && ((float) cVal != 0)) {
                    if (excludeModernization.stream().anyMatch(param.toLowerCase()::contains)) {
                        bonus.put(MODIFIER + param.toUpperCase(), CommonUtils.getNumSym((float) cVal));
                    } else {
                        bonus.put(MODIFIER + param.toUpperCase() + (key.contains("WorkTime") ? "_MODERNIZATION" : ""), CommonUtils.getNumSym(CommonUtils.getBonusCoef((float) cVal)) + " %");
                    }
                }
            } else if ("consumable".equalsIgnoreCase(type) || "commander".equalsIgnoreCase(type)) {
                if ((key.toLowerCase().contains("forsag") && param.contains("boostCoeff")) || speed.stream().anyMatch(param.toLowerCase()::contains)) {
                    bonus.put(MODIFIER + param.toUpperCase(), CommonUtils.getNumSym((float) cVal) + " kts");
                } else if (rate.stream().anyMatch(param.toLowerCase()::contains)) {
                    bonus.put(MODIFIER + param.toUpperCase(), CommonUtils.getNumSym(CommonUtils.getBonus((float) cVal)) + " %");
                } else if (multiple.stream().anyMatch(param.toLowerCase()::contains)) {
                    bonus.put(MODIFIER + param.toUpperCase(), "X " + CommonUtils.replaceZero(cVal.toString()));
                } else if (coeff.stream().anyMatch(param.toLowerCase()::contains)) {
                    bonus.put(MODIFIER + param.toUpperCase(), CommonUtils.getNumSym(CommonUtils.getBonusCoef((float) cVal)) + " %");
                } else if (noUnit.stream().anyMatch(param.toLowerCase()::contains)) {
                    bonus.put(MODIFIER + param.toUpperCase(), (float) cVal > 0 ? CommonUtils.replaceZero(cVal.toString()) : "∞");
                } else if (meter.stream().anyMatch(param.toLowerCase()::contains)) {
                    bonus.put(MODIFIER + param.toUpperCase(), CommonUtils.replaceZero(cVal.toString()) + " m");
                } else if (rateNoSym.stream().anyMatch(param.toLowerCase()::contains)) {
                    bonus.put(MODIFIER + param.toUpperCase(), CommonUtils.replaceZero(cVal.toString()) + " %");
                } else if (time.stream().anyMatch(param.toLowerCase()::contains)) {
                    bonus.put(MODIFIER + param.toUpperCase(), CommonUtils.replaceZero(cVal.toString()) + " s");
                } else if (extraAngle.stream().anyMatch(param.toLowerCase()::contains)) {
                    bonus.put(MODIFIER + param.toUpperCase(), CommonUtils.getNumSym((float) cVal) + " °");
                } else if (angle.stream().anyMatch(param.toLowerCase()::contains)) {
                    bonus.put(MODIFIER + param.toUpperCase(), CommonUtils.replaceZero(cVal.toString()) + " °");
                } else if (extra.stream().anyMatch(param.toLowerCase()::contains)) {
                    bonus.put(MODIFIER + param.toUpperCase(), CommonUtils.getNumSym((float) cVal));
                }
            }
        });
    }
}
