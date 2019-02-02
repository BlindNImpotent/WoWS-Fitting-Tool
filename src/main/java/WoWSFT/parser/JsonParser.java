package WoWSFT.parser;

import WoWSFT.model.gameparams.TypeInfo;
import WoWSFT.model.gameparams.consumable.Consumable;
import WoWSFT.model.gameparams.modernization.Modernization;
import WoWSFT.model.gameparams.modernization.Upgrades;
import WoWSFT.model.gameparams.ship.Ship;
import WoWSFT.model.gameparams.ship.ShipIndex;
import WoWSFT.model.gameparams.ship.component.airdefense.AirDefense;
import WoWSFT.model.gameparams.ship.component.artillery.Artillery;
import WoWSFT.model.gameparams.ship.component.atba.ATBA;
import WoWSFT.model.gameparams.ship.component.engine.Engine;
import WoWSFT.model.gameparams.ship.component.firecontrol.FireControl;
import WoWSFT.model.gameparams.ship.component.hull.Hull;
import WoWSFT.model.gameparams.ship.upgrades.ShipComponents;
import WoWSFT.model.gameparams.ship.upgrades.ShipUpgrade;
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
    private HashMap<String, Object> global;

    private ObjectMapper mapper = new ObjectMapper();

    private static final String regex = "[^\\p{L}\\p{N}]+";

    private static HashSet<String> excludeShipGroups = new HashSet<>(Arrays.asList("unavailable", "disabled", "preserved", "clan"));
    private static HashSet<String> excludeShipNations = new HashSet<>(Arrays.asList("Events", "disabled", "preserved", "clan"));
    private static HashSet<String> excludeShipSpecies = new HashSet<>(Arrays.asList("Auxiliary", "Submarine", "AirCarrier"));

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

        Resource GlobalFile = new ClassPathResource("/json/live/global.json");

        HashMap<String, Object> temp = mapper.readValue(GlobalFile.getInputStream(), new TypeReference<HashMap<String, Object>>(){});

        global.putAll(temp);
        temp.clear();
    }

//    @Async
    public void setGameParams() throws IOException
    {
        log.info("Setting up GameParams");

        Resource GameParamsFile = new ClassPathResource("/json/live/GameParams.json");

        HashMap<String, LinkedHashMap> temp = mapper.readValue(GameParamsFile.getInputStream(), new TypeReference<HashMap<String, LinkedHashMap>>(){});

        HashMap<String, Ship> tempShips = new HashMap<>();
        HashMap<String, Consumable> tempConsumables = new HashMap<>();
        //nation, realShipType, shipType, tier, shipList
        HashMap<String, HashMap<String, HashMap<String, HashMap<Integer, List<ShipIndex>>>>> shipsList = new HashMap<>();
        Upgrades tempUpgrades = new Upgrades();

        temp.forEach((key, value) -> {
            TypeInfo typeInfo = mapper.convertValue(value.get("typeinfo"), TypeInfo.class);

            if (typeInfo.getType().equalsIgnoreCase("Ship") && !excludeShipNations.contains(typeInfo.getNation()) && !excludeShipSpecies.contains(typeInfo.getSpecies())) {
                Ship ship = mapper.convertValue(value, Ship.class);
                if (!excludeShipGroups.contains(ship.getGroup()) && StringUtils.isEmpty(ship.getDefaultCrew())) {
                    addShips(ship, tempShips, shipsList);
                }
            }
            else if (typeInfo.getType().equalsIgnoreCase("Modernization")) {
                Modernization modernization = mapper.convertValue(value, Modernization.class);
                setUpgrades(tempUpgrades, modernization);
            }
            else if (typeInfo.getType().equalsIgnoreCase("Ability") && !excludeShipNations.contains(typeInfo.getNation()) && !key.contains("Super")) {
                Consumable consumable = mapper.convertValue(value, Consumable.class);
                tempConsumables.put(key, consumable);
            }

//            nameToId.put(key, String.valueOf(value.get("id")));
//            idToName.put(String.valueOf(value.get("id")), key);
//            gameParamsHM.put(String.valueOf(value.get("id")), value);
        });



        gameParamsHM.put(TYPE_SHIP, tempShips);
        gameParamsHM.put(TYPE_SHIP_LIST, sortShipsList(tempShips, shipsList));
        gameParamsHM.put(TYPE_UPGRADE, sortUpgrades(tempUpgrades));
        gameParamsHM.put(TYPE_CONSUMABLE, tempConsumables);

        temp.clear();
    }

    private void addShips(Ship ship, HashMap<String, Ship> tempShips, HashMap<String, HashMap<String, HashMap<String, HashMap<Integer, List<ShipIndex>>>>> shipsList)
    {
        sortShipUpgradeInfo(ship);
        sortComponents(ship);
        setRealShipType(ship);
        setAbilitySlots(ship);

        ship.setFullName(global.get("IDS_" + ship.getIndex().toUpperCase() + "_FULL").toString());

        tempShips.put(ship.getIndex(), ship);

        addToShipsList(ship, shipsList);
    }

    private void sortShipUpgradeInfo(Ship ship)
    {
        ship.getShipUpgradeInfo().getArtillery().sort(Comparator.comparingInt(ShipUpgrade::getPosition).thenComparing(ShipUpgrade::getName));
        ship.getShipUpgradeInfo().getEngine().sort(Comparator.comparingInt(ShipUpgrade::getPosition).thenComparing(ShipUpgrade::getName));
        ship.getShipUpgradeInfo().getHull().sort(Comparator.comparingInt(ShipUpgrade::getPosition).thenComparing(ShipUpgrade::getName));
        ship.getShipUpgradeInfo().getSuo().sort(Comparator.comparingInt(ShipUpgrade::getPosition).thenComparing(ShipUpgrade::getName));
        ship.getShipUpgradeInfo().getTorpedoes().sort(Comparator.comparingInt(ShipUpgrade::getPosition).thenComparing(ShipUpgrade::getName));
        ship.getShipUpgradeInfo().getFlightControl().sort(Comparator.comparingInt(ShipUpgrade::getPosition).thenComparing(ShipUpgrade::getName));
        ship.getShipUpgradeInfo().getDiveBomber().sort(Comparator.comparingInt(ShipUpgrade::getPosition).thenComparing(ShipUpgrade::getName));
        ship.getShipUpgradeInfo().getFighter().sort(Comparator.comparingInt(ShipUpgrade::getPosition).thenComparing(ShipUpgrade::getName));
        ship.getShipUpgradeInfo().getTorpedoBomber().sort(Comparator.comparingInt(ShipUpgrade::getPosition).thenComparing(ShipUpgrade::getName));
    }

    private void sortComponents(Ship ship)
    {
        ship.getShipUpgradeInfo().getArtillery().forEach(upgrade -> addToShipComponents(ship, upgrade.getComponents()));
        ship.getShipUpgradeInfo().getEngine().forEach(upgrade -> addToShipComponents(ship, upgrade.getComponents()));
        ship.getShipUpgradeInfo().getHull().forEach(upgrade -> addToShipComponents(ship, upgrade.getComponents()));
        ship.getShipUpgradeInfo().getSuo().forEach(upgrade -> addToShipComponents(ship, upgrade.getComponents()));
        ship.getShipUpgradeInfo().getTorpedoes().forEach(upgrade -> addToShipComponents(ship, upgrade.getComponents()));
        ship.getShipUpgradeInfo().getFlightControl().forEach(upgrade -> addToShipComponents(ship, upgrade.getComponents()));
        ship.getShipUpgradeInfo().getDiveBomber().forEach(upgrade -> addToShipComponents(ship, upgrade.getComponents()));
        ship.getShipUpgradeInfo().getFighter().forEach(upgrade -> addToShipComponents(ship, upgrade.getComponents()));
        ship.getShipUpgradeInfo().getTorpedoBomber().forEach(upgrade -> addToShipComponents(ship, upgrade.getComponents()));

        ship.setComponents(new HashMap<>());
    }

    private void addToShipComponents(Ship ship, ShipComponents shipComponents)
    {
        shipComponents.getAirArmament().forEach(component -> ship.getAirArmament().put(component, ship.getComponents().get(component)));
        shipComponents.getAirDefense().forEach(component -> ship.getAirDefense().put(component, mapper.convertValue(ship.getComponents().get(component), AirDefense.class)));
        shipComponents.getArtillery().forEach(component -> ship.getArtillery().put(component, mapper.convertValue(ship.getComponents().get(component), Artillery.class)));
        shipComponents.getAtba().forEach(component -> ship.getAtba().put(component, mapper.convertValue(ship.getComponents().get(component), ATBA.class)));
        shipComponents.getEngine().forEach(component -> ship.getEngine().put(component, mapper.convertValue(ship.getComponents().get(component), Engine.class)));
        shipComponents.getFireControl().forEach(component -> ship.getFireControl().put(component, mapper.convertValue(ship.getComponents().get(component), FireControl.class)));
        shipComponents.getHull().forEach(component -> ship.getHull().put(component, mapper.convertValue(ship.getComponents().get(component), Hull.class)));
        shipComponents.getTorpedoes().forEach(component -> ship.getTorpedoes().put(component, ship.getComponents().get(component)));

        shipComponents.getFlightControl().forEach(component -> ship.getFlightControl().put(component, ship.getComponents().get(component)));
        shipComponents.getDiveBomber().forEach(component -> ship.getDiveBomber().put(component, ship.getComponents().get(component)));
        shipComponents.getFighter().forEach(component -> ship.getFighter().put(component, ship.getComponents().get(component)));
        shipComponents.getTorpedoBomber().forEach(component -> ship.getTorpedoBomber().put(component, ship.getComponents().get(component)));
    }

    private void setRealShipType(Ship ship)
    {
        if ("upgradeable".equalsIgnoreCase(ship.getGroup()) || "start".equalsIgnoreCase(ship.getGroup())) {
            ship.setRealShipType(ship.getTypeinfo().getSpecies());
            ship.setResearch(true);
        }
        else {
            ship.setRealShipType("Premium");
        }
    }

    private void setAbilitySlots(Ship ship)
    {
        if (CollectionUtils.isEmpty(ship.getShipAbilities().getAbilitySlot0().getAbils())) {
            ship.getShipAbilities().setAbilitySlot0(null);
        }

        if (CollectionUtils.isEmpty(ship.getShipAbilities().getAbilitySlot1().getAbils())) {
            ship.getShipAbilities().setAbilitySlot1(null);
        }

        if (CollectionUtils.isEmpty(ship.getShipAbilities().getAbilitySlot2().getAbils())) {
            ship.getShipAbilities().setAbilitySlot2(null);
        }

        if (CollectionUtils.isEmpty(ship.getShipAbilities().getAbilitySlot3().getAbils())) {
            ship.getShipAbilities().setAbilitySlot3(null);
        }

        if (CollectionUtils.isEmpty(ship.getShipAbilities().getAbilitySlot4().getAbils())) {
            ship.getShipAbilities().setAbilitySlot4(null);
        }
    }

    private void addToShipsList(Ship ship, HashMap<String, HashMap<String, HashMap<String, HashMap<Integer, List<ShipIndex>>>>> shipsList)
    {
        if (!shipsList.containsKey(ship.getTypeinfo().getNation())) {
            shipsList.put(ship.getTypeinfo().getNation(), new HashMap<>());
        }

        if (!shipsList.get(ship.getTypeinfo().getNation()).containsKey(ship.getRealShipType())) {
            shipsList.get(ship.getTypeinfo().getNation()).put(ship.getRealShipType(), new HashMap<>());
        }

        if (!shipsList.get(ship.getTypeinfo().getNation()).get(ship.getRealShipType()).containsKey(ship.getTypeinfo().getSpecies())) {
            shipsList.get(ship.getTypeinfo().getNation()).get(ship.getRealShipType()).put(ship.getTypeinfo().getSpecies(), new HashMap<>());
        }

        if (!shipsList.get(ship.getTypeinfo().getNation()).get(ship.getRealShipType()).get(ship.getTypeinfo().getSpecies()).containsKey(ship.getLevel())) {
            shipsList.get(ship.getTypeinfo().getNation()).get(ship.getRealShipType()).get(ship.getTypeinfo().getSpecies()).put(ship.getLevel(), new ArrayList<>());
        }

        shipsList.get(ship.getTypeinfo().getNation()).get(ship.getRealShipType()).get(ship.getTypeinfo().getSpecies()).get(ship.getLevel()).add(new ShipIndex(ship.getName(), ship.getIndex(), ship.getFullName(), ship.isResearch()));
    }

    private void setUpgrades(Upgrades upgrades, Modernization modernization)
    {
        switch (modernization.getSlot()) {
            case 0: upgrades.getSlot0().put(modernization.getName(), modernization); break;
            case 1: upgrades.getSlot1().put(modernization.getName(), modernization); break;
            case 2: upgrades.getSlot2().put(modernization.getName(), modernization); break;
            case 3: upgrades.getSlot3().put(modernization.getName(), modernization); break;
            case 4: upgrades.getSlot4().put(modernization.getName(), modernization); break;
            case 5: upgrades.getSlot5().put(modernization.getName(), modernization); break;
        }
    }

    private HashMap<String, HashMap<String, HashMap<String, HashMap<Integer, List<ShipIndex>>>>> sortShipsList(HashMap<String, Ship> tempShips, HashMap<String, HashMap<String, HashMap<String, HashMap<Integer, List<ShipIndex>>>>> shipsList)
    {
        shipsList.forEach((nation, realShipTypes) -> realShipTypes.forEach((realShipType, shipTypes) -> shipTypes.forEach((shipType, tiers) -> {
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

                            if (CollectionUtils.isNotEmpty(shipsList.get(nation).get(realShipType).get(shipType).get(cTier - 1))) {
                                shipsList.get(nation).get(realShipType).get(shipType).get(cTier - 1).forEach(tShip -> {
                                    if (tempShips.get(tShip.getIndex()).getTypeinfo().getSpecies().equalsIgnoreCase(shipType)) {
                                        if (tempShips.get(tShip.getIndex()).getShipUpgradeInfo().getUpgrades().values().stream().anyMatch(u -> u.getNextShips().contains(ship.getIdentifier()))
                                            && tempShips.get(tShip.getIndex()).getShipUpgradeInfo().getUpgrades().values().stream().filter(u -> CollectionUtils.isNotEmpty(u.getNextShips())).count() == 1) {
                                            tShip.setPosition(ship.getPosition());
                                        }
                                    }
                                });
                            }
                        }
                    });
                    tiers.get(tier).sort(Comparator.comparingInt(ShipIndex::getPosition));
                }
                tier--;
            }
        })));

        return shipsList;
    }

    private Upgrades sortUpgrades(Upgrades upgrades)
    {
        upgrades.getSlot0().entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(u -> {upgrades.getSlot0().remove(u.getKey());upgrades.getSlot0().put(u.getKey(), u.getValue());});
        upgrades.getSlot1().entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(u -> {upgrades.getSlot1().remove(u.getKey());upgrades.getSlot1().put(u.getKey(), u.getValue());});
        upgrades.getSlot2().entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(u -> {upgrades.getSlot2().remove(u.getKey());upgrades.getSlot2().put(u.getKey(), u.getValue());});
        upgrades.getSlot3().entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(u -> {upgrades.getSlot3().remove(u.getKey());upgrades.getSlot3().put(u.getKey(), u.getValue());});
        upgrades.getSlot4().entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(u -> {upgrades.getSlot4().remove(u.getKey());upgrades.getSlot4().put(u.getKey(), u.getValue());});
        upgrades.getSlot5().entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(u -> {upgrades.getSlot5().remove(u.getKey());upgrades.getSlot5().put(u.getKey(), u.getValue());});

        return upgrades;
    }
}
