package WoWSFT.parser;

import WoWSFT.model.gameparams.TypeInfo;
import WoWSFT.model.gameparams.commander.Commander;
import WoWSFT.model.gameparams.consumable.Consumable;
import WoWSFT.model.gameparams.flag.Flag;
import WoWSFT.model.gameparams.modernization.Modernization;
import WoWSFT.model.gameparams.ship.Ship;
import WoWSFT.model.gameparams.ship.ShipIndex;
import WoWSFT.model.gameparams.ship.component.airarmament.AirArmament;
import WoWSFT.model.gameparams.ship.component.airdefense.AirDefense;
import WoWSFT.model.gameparams.ship.component.artillery.Artillery;
import WoWSFT.model.gameparams.ship.component.atba.ATBA;
import WoWSFT.model.gameparams.ship.component.engine.Engine;
import WoWSFT.model.gameparams.ship.component.firecontrol.FireControl;
import WoWSFT.model.gameparams.ship.component.flightcontrol.FlightControl;
import WoWSFT.model.gameparams.ship.component.hull.Hull;
import WoWSFT.model.gameparams.ship.component.torpedo.Torpedo;
import WoWSFT.model.gameparams.ship.upgrades.ShipUpgrade;
import WoWSFT.service.ParamService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import static WoWSFT.model.Constant.*;

/**
 * Created by Qualson-Lee on 2016-11-15.
 */
@Slf4j
public class JsonParser
{
    @Autowired
    @Qualifier(value = "notification")
    private LinkedHashMap<String, LinkedHashMap<String, String>> notification;

    @Autowired
    @Qualifier(value = "translation")
    private LinkedHashMap<String, LinkedHashMap<String, String>> translation;

    @Autowired
    @Qualifier(value = "nameToId")
    private HashMap<String, String> nameToId;

    @Autowired
    @Qualifier(value = "idToName")
    private HashMap<String, String> idToName;

    @Autowired
    @Qualifier(value = "gameParamsHM")
    private HashMap<String, Object> gameParamsHM;

    @Autowired
    @Qualifier(value = "global")
    private HashMap<String, HashMap<String, Object>> global;

    @Autowired
    @Qualifier(value = TYPE_SHIP)
    private LinkedHashMap<String, Ship> ships;

    @Autowired
    @Qualifier(value = TYPE_MISC)
    private LinkedHashMap<String, Object> misc;

    @Autowired
    @Qualifier(value = TYPE_CONSUMABLE)
    private LinkedHashMap<String, Consumable> consumables;

    @Autowired
    @Qualifier(value = TYPE_SHIP_LIST)
    private LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<Integer, List<ShipIndex>>>>> shipsList;

    @Autowired
    @Qualifier(value = TYPE_UPGRADE)
    private LinkedHashMap<Integer, LinkedHashMap<String, Modernization>> upgrades;

    @Autowired
    @Qualifier(value = TYPE_COMMANDER)
    private LinkedHashMap<String, Commander> commanders;

    @Autowired
    @Qualifier(value = TYPE_FLAG)
    private LinkedHashMap<String, Flag> flags;

    @Autowired
    private ParamService paramService;

    private ObjectMapper mapper = new ObjectMapper();

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

        ZipFile zf = new ZipFile(new ClassPathResource("/json/live/GameParams.zip").getFile().getPath());
        LinkedHashMap<String, LinkedHashMap<String, Object>> temp = mapper.readValue(zf.getInputStream(zf.entries().nextElement()), new TypeReference<LinkedHashMap<String, LinkedHashMap<String, Object>>>(){});

        for (int i = 0; i < 6; i++) {
            upgrades.put(i, new LinkedHashMap<>());
        }

        temp.forEach((key, value) -> {
            TypeInfo typeInfo = mapper.convertValue(value.get("typeinfo"), TypeInfo.class);

            if (typeInfo.getType().equalsIgnoreCase("Ship") && !excludeShipNations.contains(typeInfo.getNation()) && !excludeShipSpecies.contains(typeInfo.getSpecies())) {
                Ship ship = mapper.convertValue(value, Ship.class);
                if (!excludeShipGroups.contains(ship.getGroup()) && (StringUtils.isEmpty(ship.getDefaultCrew()) || ship.getDefaultCrew().contains("PWW"))
//                    && !supertestShipGroups.contains(ship.getGroup())
                ) {
                    ship.getShipUpgradeInfo().getComponents().forEach((cType, c) ->
                            c.forEach(su -> {
                                for (String s : excludeCompStats) {
                                    su.getComponents().remove(s);
                                }
                                su.setElem(componentsList.indexOf(cType));
                            }));
                    addShips(ship);
                }
            } else if (typeInfo.getType().equalsIgnoreCase("Modernization")) {
                Modernization modernization = mapper.convertValue(value, Modernization.class);
                if (modernization.getSlot() >= 0) {
                    paramService.setBonusParams(key, mapper.convertValue(modernization, new TypeReference<LinkedHashMap<String, Object>>(){}), modernization.getBonus());
                    upgrades.get(modernization.getSlot()).put(modernization.getName(), modernization);
                }
            } else if (typeInfo.getType().equalsIgnoreCase("Ability") && !excludeShipNations.contains(typeInfo.getNation()) && !key.contains("Super")) {
                Consumable consumable = mapper.convertValue(value, Consumable.class);
                consumables.put(key, consumable);
            } else if (typeInfo.getType().equalsIgnoreCase("Crew")) {
                Commander commander = mapper.convertValue(value, Commander.class);
                if (!"Events".equalsIgnoreCase(commander.getTypeinfo().getNation())) {
                    if (!commander.getCrewPersonality().isUnique() && commander.getTypeinfo().getNation().equals("Common")) {
                        commander.setIdentifier("IDS_CREW_LASTNAME_DEFAULT");
                        commanders.put(commander.getIndex().toUpperCase(), commander);
                    } else if (commander.getCrewPersonality().isUnique()) {
                        commander.setIdentifier(IDS + commander.getCrewPersonality().getPersonName().toUpperCase());
                        commanders.put(commander.getIndex().toUpperCase(), commander);
                    }
                }
            } else if (typeInfo.getType().equalsIgnoreCase("Exterior") && typeInfo.getSpecies().equalsIgnoreCase("Flags")) {
                Flag flag = mapper.convertValue(value, Flag.class);
                if (flag.getGroup() == 0) {
                    flag.setIdentifier(IDS + flag.getName().toUpperCase());
                    paramService.setBonusParams(key, mapper.convertValue(flag, new TypeReference<LinkedHashMap<String, Object>>(){}), flag.getBonus());
                    flags.put(flag.getName(), flag);
                }
            } else if (miscList.contains(typeInfo.getType())) {
                misc.put(key, value);
            } else {
                gameParamsHM.put(key, value);
            }
//            nameToId.put(key, String.valueOf(value.get("id")));
//            idToName.put(String.valueOf(value.get("id")), key);
//            gameParamsHM.put(String.valueOf(value.get("id")), value);
        });

        generateShipsList();
        sortUpgrades();
        setCommanderParams();
        sortFlags();

        temp.clear();
    }

    private void addShips(Ship ship)
    {
        sortShipUpgradeInfo(ship);
        setRealShipType(ship);
        setRows(ship);

        ships.put(ship.getIndex(), ship);
        idToName.put(ship.getName(), ship.getIndex());
        try {
            nameToId.put(global.get(EN).get(IDS + ship.getIndex().toUpperCase() + "_FULL").toString(), ship.getIndex().toUpperCase());
        } catch (NullPointerException npe) {
            log.info(ship.getIndex().toUpperCase());
        }
    }

    private void sortShipUpgradeInfo(Ship ship)
    {
        ship.getShipUpgradeInfo().getComponents().forEach((key, value) -> {
            value.forEach(upgrade -> {
                if (upgrade.getPosition() == 3 && ship.getShipUpgradeInfo().getComponents().get(key).size() < 3) {
                    upgrade.setPosition(2);
                }

                upgrade.getComponents().forEach((cKey, cValue) -> {
                    if (cKey.equalsIgnoreCase(artillery)) {
                        cValue.forEach(cVal -> ship.getComponents().getArtillery().put(cVal, mapper.convertValue(ship.getTempComponents().get(cVal), Artillery.class)));
                    } else if (cKey.equalsIgnoreCase(airDefense)) {
                        cValue.forEach(cVal -> ship.getComponents().getAirDefense().put(cVal, mapper.convertValue(ship.getTempComponents().get(cVal), AirDefense.class)));
                    } else if (cKey.equalsIgnoreCase(atba)) {
                        cValue.forEach(cVal -> ship.getComponents().getAtba().put(cVal, mapper.convertValue(ship.getTempComponents().get(cVal), ATBA.class)));
                    } else if (cKey.equalsIgnoreCase(engine)) {
                        cValue.forEach(cVal -> ship.getComponents().getEngine().put(cVal, mapper.convertValue(ship.getTempComponents().get(cVal), Engine.class)));
                    } else if (cKey.equalsIgnoreCase(suo)) {
                        cValue.forEach(cVal -> ship.getComponents().getSuo().put(cVal, mapper.convertValue(ship.getTempComponents().get(cVal), FireControl.class)));
                    } else if (cKey.equalsIgnoreCase(hull)) {
                        cValue.forEach(cVal -> ship.getComponents().getHull().put(cVal, mapper.convertValue(ship.getTempComponents().get(cVal), Hull.class)));
                    } else if (cKey.equalsIgnoreCase(torpedoes)) {
                        cValue.forEach(cVal -> ship.getComponents().getTorpedoes().put(cVal, mapper.convertValue(ship.getTempComponents().get(cVal), Torpedo.class)));
                    } else if (cKey.equalsIgnoreCase(airArmament)) {
                        cValue.forEach(cVal -> ship.getComponents().getAirArmament().put(cVal, mapper.convertValue(ship.getTempComponents().get(cVal), AirArmament.class)));
                    } else if (cKey.equalsIgnoreCase(flightControl)) {
                        cValue.forEach(cVal -> ship.getComponents().getFlightControl().put(cVal, mapper.convertValue(ship.getTempComponents().get(cVal), FlightControl.class)));
                    } else if (cKey.equalsIgnoreCase(fighter) || cKey.equalsIgnoreCase(diveBomber) || cKey.equalsIgnoreCase(torpedoBomber)) {
                        cValue.forEach(cVal -> {
                            HashMap<String, String> tempPlaneType = mapper.convertValue(ship.getTempComponents().get(cVal), new TypeReference<HashMap<String, String>>(){});
                            ship.getPlanes().put(cVal, tempPlaneType.get("planeType"));
                        });
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
                            upgrade.setPrevElem(componentsList.indexOf(tSU.getUcTypeShort()));

                            if (upgrade.getPosition() == upgrade.getPrevPosition() && upgrade.getUcTypeShort().equalsIgnoreCase(upgrade.getPrevType())) {
                                upgrade.setPosition(upgrade.getPrevPosition() + 1);
                            }
                            break;
                        }
                    }
                }
            });
        });

        ship.setTempComponents(new LinkedHashMap<>());
    }

    private void setRealShipType(Ship ship)
    {
        if (researchShipGroups.contains(ship.getGroup())) {
            ship.setRealShipType(ship.getTypeinfo().getSpecies());
            ship.setResearch(true);
        } else if (premiumShipGroups.contains(ship.getGroup())) {
            ship.setRealShipType("Premium");
        } else if (supertestShipGroups.contains(ship.getGroup())) {
            ship.setRealShipType("Test_Sample");
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

    private void generateShipsList()
    {
        ships.forEach((index, ship) -> ship.getShipUpgradeInfo().getComponents().forEach((cKey, components) -> components.forEach(component -> {
            if (CollectionUtils.isNotEmpty(component.getNextShips())) {
                component.getNextShips().forEach(ns -> {
                    if (idToName.get(ns) != null) {
                        int currentPosition = component.getPosition();
                        String current = component.getName();
                        String prev = component.getPrev();
                        String prevType = component.getPrevType();
                        int compXP = 0;
                        while (currentPosition > 1 && StringUtils.isNotEmpty(prev) && StringUtils.isNotEmpty(prevType) && gameParamsHM.containsKey(current)) {
                            HashMap<String, Object> comp = mapper.convertValue(gameParamsHM.get(current), new TypeReference<HashMap<String, Object>>(){});
                            compXP += (int) comp.get("costXP");

                            if (StringUtils.isNotEmpty(prev)) {
                                List<ShipUpgrade> tempSUList = ship.getShipUpgradeInfo().getComponents().get(prevType);
                                for (ShipUpgrade su : tempSUList) {
                                    if (su.getName().equalsIgnoreCase(prev)) {
                                        currentPosition = su.getPosition();
                                        prevType = su.getPrevType();
                                        current = su.getName();
                                        prev = su.getPrev();
                                        break;
                                    }
                                }
                            } else {
                                break;
                            }
                        }
                        ships.get(idToName.get(ns)).setPrevShipIndex(ship.getIndex()).setPrevShipName(ship.getName()).setPrevShipXP(ship.getShipUpgradeInfo().getCostXP()).setPrevShipCompXP(compXP);
                    }
                });
            }
        })));

        ships.forEach((index, ship) -> {
            shipsList.putIfAbsent(ship.getTypeinfo().getNation(), new LinkedHashMap<>());
            shipsList.get(ship.getTypeinfo().getNation()).putIfAbsent(ship.getRealShipTypeId().toUpperCase(), new LinkedHashMap<>());
            shipsList.get(ship.getTypeinfo().getNation()).get(ship.getRealShipTypeId().toUpperCase()).putIfAbsent(ship.getTypeinfo().getSpecies().toUpperCase(), new LinkedHashMap<>());
            shipsList.get(ship.getTypeinfo().getNation()).get(ship.getRealShipTypeId().toUpperCase()).get(ship.getTypeinfo().getSpecies().toUpperCase()).putIfAbsent(ship.getLevel(), new ArrayList<>());

            List<String> arties = new ArrayList<>();
            ship.getShipUpgradeInfo().getComponents().get(artillery).forEach(arty -> arties.add(arty.getName()));

            shipsList.get(ship.getTypeinfo().getNation()).get(ship.getRealShipTypeId().toUpperCase()).get(ship.getTypeinfo().getSpecies().toUpperCase()).get(ship.getLevel())
                    .add(new ShipIndex(ship.getName(), ship.getIndex(), ship.getPrevShipIndex(), ship.getPrevShipName(),
                            ship.isResearch(), ship.getShipUpgradeInfo().getCostXP(), ship.getPrevShipXP(), ship.getPrevShipCompXP(), arties));
        });

        shipsList.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(nation -> {
            nation.getValue().entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(realShipType -> {
                realShipType.getValue().forEach((shipType, tiers) -> {
                    tiers.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(tier -> {
                        tier.getValue().sort(Comparator.comparing(ShipIndex::getIndex));
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
                                            if (ships.get(tShip.getIndex()).getTypeinfo().getSpecies().equalsIgnoreCase(shipType)) {
                                                ships.get(tShip.getIndex()).getShipUpgradeInfo().getComponents().forEach((comp, list) -> list.forEach(u1 -> {
                                                    if (u1.getNextShips().contains(ship.getIdentifier())) {
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
                if (realShipType.getKey().equalsIgnoreCase("FILTER_PREMIUM")) {
                    shipsList.get(nation.getKey()).remove(realShipType.getKey());
                    shipsList.get(nation.getKey()).put(realShipType.getKey(), realShipType.getValue());
                } else if (realShipType.getKey().equalsIgnoreCase("TEST_SAMPLE")) {
                    shipsList.get(nation.getKey()).remove(realShipType.getKey());
                    shipsList.get(nation.getKey()).put(realShipType.getKey(), realShipType.getValue());
                }
            });
            shipsList.remove(nation.getKey());
            shipsList.put(nation.getKey(), nation.getValue());
        });
        LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<Integer, List<ShipIndex>>>> russia = shipsList.get("Russia");
        shipsList.remove("Russia");
        shipsList.put("Russia", russia);
    }

    private void sortUpgrades()
    {
        upgrades.forEach((slot, mod) -> mod.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(u -> {
            upgrades.get(slot).remove(u.getKey());
            upgrades.get(slot).put(u.getKey(), u.getValue());
        }));
    }

    private void sortFlags()
    {
        flags.values().stream().sorted(Comparator.comparingInt(Flag::getSortOrder)).forEach(flag -> {
            flags.remove(flag.getName());
            flags.put(flag.getName(), flag);
        });
    }

    private void setCommanderParams()
    {
        commanders.forEach((key, commander) ->
                commander.getCSkills().forEach(r -> r.forEach(s -> s.setBonus(paramService.getBonus(mapper.convertValue(s, new TypeReference<LinkedHashMap<String, Object>>(){}))))));
    }

    public void createFile() throws IOException
    {
        String tempFile = new ClassPathResource("/json/live/GameParams.zip").getURL().getPath().replaceFirst("/", "").replace("live/GameParams.zip", "files/");
        if (tempFile.startsWith("var") || tempFile.startsWith("Users")) {
            tempFile = "/" + tempFile;
        }
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        File folder = new File(tempFile);
        folder.mkdirs();

//        File[] listFiles = folder.listFiles();
//        if (folder.listFiles() != null) {
            for (File f : Objects.requireNonNull(folder.listFiles())) {
                f.delete();
            }
//        }

        for (Map.Entry<String, Ship> entry : ships.entrySet()) {
            String tempJson = tempFile + entry.getKey() + ".json";
            File f = new File(tempJson);
            mapper.writerWithDefaultPrettyPrinter().writeValue(f, entry.getValue());
        }

        for (Map.Entry<String, Object> entry : misc.entrySet()) {
            String tempJson = tempFile + entry.getKey() + ".json";
            File f = new File(tempJson);
            mapper.writerWithDefaultPrettyPrinter().writeValue(f, entry.getValue());
        }

        File f = new File(tempFile + "shipsList.json");
        mapper.writerWithDefaultPrettyPrinter().writeValue(f, shipsList);

        f = new File(tempFile + "upgrades.json");
        mapper.writerWithDefaultPrettyPrinter().writeValue(f, upgrades);

        f = new File(tempFile + "consumables.json");
        mapper.writerWithDefaultPrettyPrinter().writeValue(f, consumables);

        f = new File(tempFile + "commanders.json");
        mapper.writerWithDefaultPrettyPrinter().writeValue(f, commanders);

//        f = new File(tempFile + "gameParamsHM.json");
//        mapper.writerWithDefaultPrettyPrinter().writeValue(f, gameParamsHM);

        tempFile = tempFile.replace("files/", "files.zip");
        byte[] buffer = new byte[1024];
        FileOutputStream fos = new FileOutputStream(tempFile);
        ZipOutputStream zos = new ZipOutputStream(fos);

        for (File file : Objects.requireNonNull(folder.listFiles())) {
            FileInputStream fis = new FileInputStream(file);
            zos.putNextEntry(new ZipEntry(file.getName()));

            int length;
            while ((length = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, length);
            }

            zos.closeEntry();
            fis.close();
        }
        zos.close();
        fos.close();

//        String zipCopy = tempFile.replace(File.separator, "/").replace("target/classes", "src/main/resources");
//        InputStream in = new FileInputStream(tempFile);
//        OutputStream out = new FileOutputStream(zipCopy);
//
//        int length;
//        while ((length = in.read(buffer)) > 0){
//            out.write(buffer, 0, length);
//        }
//
//        in.close();
//        out.close();
    }
}
