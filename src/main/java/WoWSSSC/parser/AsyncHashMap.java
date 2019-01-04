package WoWSSSC.parser;

import WoWSSSC.config.CustomProperties;
import WoWSSSC.model.WoWSAPI.APIAddress;
import WoWSSSC.model.WoWSAPI.commanders.Commanders;
import WoWSSSC.model.WoWSAPI.commanders.CommandersData;
import WoWSSSC.model.WoWSAPI.commanders.CommandersRankData;
import WoWSSSC.model.WoWSAPI.consumables.Consumables;
import WoWSSSC.model.WoWSAPI.consumables.ConsumablesData;
import WoWSSSC.model.WoWSAPI.info.Encyclopedia;
import WoWSSSC.model.WoWSAPI.shipprofile.Ship;
import WoWSSSC.model.WoWSAPI.skills.CrewSkills;
import WoWSSSC.model.WoWSAPI.skills.CrewSkillsData;
import WoWSSSC.model.WoWSAPI.warships.Warship;
import WoWSSSC.model.WoWSAPI.warships.WarshipData;
import WoWSSSC.model.WoWSAPI.warships.WarshipModulesTree;
import WoWSSSC.model.gameparams.commanders.GPCommander;
import WoWSSSC.model.gameparams.commanders.UniqueTemp;
import WoWSSSC.model.gameparams.test.Values.ShipModernization.Modernization;
import WoWSSSC.utils.Sorter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Created by Aesis on 2016-11-15.
 */
@Slf4j
@Component
public class AsyncHashMap implements CommandLineRunner {
    @Autowired
    @Qualifier(value = "APIAddress")
    private APIAddress apiAddress;

    @Autowired
    private APIJsonParser apiJsonParser;

    @Autowired
    private HashMap<String, LinkedHashMap<String, LinkedHashMap>> data;

    @Autowired
    private HashMap<String, Ship> shipHashMap;

    @Autowired
    @Qualifier(value = "gameParamsCHM")
    private HashMap<String, HashMap<String, LinkedHashMap>> gameParamsCHM;

    @Autowired
    @Qualifier (value = "idToName")
    private HashMap<String, HashMap<String, String>> idToName;

    @Autowired
    private HashMap<String, Integer> loadFinish;

    @Autowired
    private Sorter sorter;

    @Autowired
    private CustomProperties customProperties;

    private boolean isFirstRun = true;

    private ObjectMapper mapper = new ObjectMapper();

    private LinkedHashMap<String, Warship> rawShipData = new LinkedHashMap<>();

    private String serverParam = "live";

    private Encyclopedia encyclopedia;

    @Override
    public void run(String... strings) throws Exception
    {
        CompletableFuture<CrewSkillsData> crewsSkillsData;
        CompletableFuture<CommandersData> commandersData;
        CompletableFuture<CommandersRankData> commandersRankData;
        CompletableFuture<ConsumablesData> consumablesData;
        HashMap<String, Warship> tempWarships;
        LinkedHashMap<String, LinkedHashMap<String, CompletableFuture<WarshipData>>> futures = new LinkedHashMap<>();

//        String language = customProperties.getLanguage();
        String server = customProperties.getServer();

        LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Warship>>> nations = new LinkedHashMap<>();

        if (isFirstRun) {
            apiJsonParser.setNotification();

            apiJsonParser.setGameParams();

            apiJsonParser.setGlobal();
        }

        if (!"na".equalsIgnoreCase(apiAddress.getAddress()) && !"asia".equalsIgnoreCase(apiAddress.getAddress())) {
            loadFinish.clear();
            loadFinish.put("loadFinish", 0);

            Encyclopedia encyclopediaNA = !"asia".equalsIgnoreCase(server) ? apiJsonParser.getEncyclopedia_NA().get().getData() : null;
            Encyclopedia encyclopediaRU = !"asia".equalsIgnoreCase(server) ? apiJsonParser.getEncyclopedia_RU().get().getData() : null;
            Encyclopedia encyclopediaASIA = "asia".equalsIgnoreCase(server) ? apiJsonParser.getEncyclopedia_ASIA().get().getData() : null;

            if (encyclopediaASIA != null) {
                encyclopedia = encyclopediaASIA;
                encyclopedia.setRegion("ASIA");
                apiAddress.setAddress("ASIA");
            }
            if (encyclopediaNA == null && encyclopediaRU != null) {
                encyclopedia = encyclopediaRU;
                encyclopedia.setRegion("RU");
                apiAddress.setAddress("RU");
            } else if (encyclopediaRU == null && encyclopediaNA != null) {
                encyclopedia = encyclopediaNA;
                encyclopedia.setRegion("NA");
                apiAddress.setAddress("NA");
            } else if (encyclopediaNA != null){
                String[] ruSplit = encyclopediaRU.getGame_version().split("\\.");
                String[] naSplit = encyclopediaNA.getGame_version().split("\\.");

                boolean ruHigher = false;
                for (int i = 0; i < ruSplit.length && i < naSplit.length; i++) {
                    if (Integer.parseInt(ruSplit[i]) > Integer.parseInt(naSplit[i])) {
                        ruHigher = true;
                        break;
                    }
                }

                if (!ruHigher) {
                    ruHigher = ruSplit.length > naSplit.length;
                }

                if (ruHigher) {
                    encyclopedia = encyclopediaRU;
                    encyclopedia.setRegion("RU");
                    apiAddress.setAddress("RU");
                } else {
                    encyclopedia = encyclopediaNA;
                    encyclopedia.setRegion("NA");
                    apiAddress.setAddress("NA");
                }
            }

            isFirstRun = true;

            if (encyclopediaNA != null || encyclopediaRU != null || encyclopediaASIA != null) {
                crewsSkillsData = apiJsonParser.getCrewSkills();
                commandersData = apiJsonParser.getCommanders();
                commandersRankData = apiJsonParser.getCommandersRanks();
                consumablesData = apiJsonParser.getConsumables();
                tempWarships = apiJsonParser.getTotalWarships();

                data.clear();

                tempWarships.values().forEach(warship -> {
                    if (warship.getNext_ships().size() > 0) {
                        Warship tempWS = new Warship(warship.getNation(), warship.getType(), warship.getName(), warship.getImages(), null).setTier(warship.getTier()).setShip_id(warship.getShip_id());
                        warship.getNext_ships().keySet().forEach(shipKey -> tempWarships.get(shipKey).setPrevWarship(tempWS));
                    }
                });

                List<String> nationsString = new ArrayList<>(encyclopedia.getShip_nations().keySet());
                List<String> shipTypeString = new ArrayList<>(encyclopedia.getShip_types().keySet());
                Collections.sort(nationsString);
                Collections.sort(shipTypeString);

                LinkedHashMap<String, Consumables> tempUpgrades = new LinkedHashMap<>(consumablesData.get().getData());
                LinkedHashMap<String, LinkedHashMap<String, Consumables>> tempExteriors = new LinkedHashMap<>();
                LinkedHashMap<String, Consumables> tempFlags = new LinkedHashMap<>();
                LinkedHashMap<String, Consumables> tempPermoflage = new LinkedHashMap<>();
                LinkedHashMap<String, Consumables> tempCamouflage = new LinkedHashMap<>();
                LinkedHashMap<String, Commanders> tempCommanders = new LinkedHashMap<>(commandersData.get().getData());
                LinkedHashMap<String, LinkedHashMap<String, UniqueTemp>> uniqueSkills = new LinkedHashMap<>();

                consumablesData.get().getData().forEach((key, value1) -> {
                    if (value1.getType().equals("Flags")) {
                        List<String> stringsList = new ArrayList<>();
                        LinkedHashMap<String, HashMap<String, String>> profileHashMap = mapper.convertValue(value1.getProfile(), new TypeReference<LinkedHashMap<String, HashMap<String, String>>>(){});

                        profileHashMap.values().forEach(value -> {
                            if (value != null) {
                                stringsList.add("\n" + value.get("description"));
                            }
                        });

                        String finalString = "";
                        for (String s : stringsList) {
                            finalString = finalString.concat(s);
                        }
                        value1.setDescription(value1.getDescription() + finalString);
                        value1.setBonusDescription(finalString.replaceFirst("\n", ""));

                        tempFlags.put(key, value1);
                    } else if (value1.getType().equals("Camouflage")) {
                        tempCamouflage.put(key, value1);
                    } else if (value1.getType().equals("Permoflage")) {
                        tempPermoflage.put(key, value1);
                    } else if (value1.getType().equals("Modernization")) {
                        List<String> stringsList = new ArrayList<>();
                        stringsList.add(value1.getDescription());

                        LinkedHashMap<String, HashMap<String, String>> profileHashMap = mapper.convertValue(value1.getProfile(), new TypeReference<LinkedHashMap<String, HashMap<String, String>>>(){});

                        profileHashMap.values().forEach(value -> {
                            if (value != null) {
                                stringsList.add("\n" + value.get("description"));
                            }
                        });

                        String finalString = "";
                        for (String s : stringsList) {
                            finalString = finalString.concat(s);
                        }
                        value1.setDescription(finalString);

                        tempUpgrades.put(key, value1);
                    }
                });

                tempExteriors.put("Flags", tempFlags);
                tempExteriors.put("Camouflage", tempCamouflage);
                tempExteriors.put("Permoflage", tempPermoflage);

                for (String s : nationsString) {
                    LinkedHashMap<String, CompletableFuture<WarshipData>> temp = new LinkedHashMap<>();
                    for (String s1 : shipTypeString) {
                        temp.put(s1, apiJsonParser.getNationShip(s, s1));
                    }
                    futures.put(s, temp);
                }

                Thread.sleep(10000);

                while ((isFirstRun && "live".equalsIgnoreCase(serverParam)) || ((!isFirstRun && "test".equalsIgnoreCase(serverParam)) && gameParamsCHM.size() > 1)) {
                    CompletableFuture.runAsync(() -> futures.forEach((key1, value1) -> {
                        LinkedHashMap<String, LinkedHashMap<String, Warship>> wsdlhm = new LinkedHashMap<>();

                        value1.forEach((key2, value2) -> {
                            WarshipData wsd = new WarshipData();
                            try {
                                value2.get().getData().entrySet().stream().filter(oWarship -> !oWarship.getValue().getName().startsWith("[")).forEach(warship -> {
                                    Warship tws = tempWarships.get(String.valueOf(warship.getValue().getShip_id()));

                                    Warship prevShip;
                                    long nextShipXp = 0;
                                    if (tws.getPrevWarship() != null) {
                                        prevShip = tempWarships.get(String.valueOf(tws.getPrevWarship().getShip_id()));

                                        if (prevShip != null) {
                                            tws.getPrevWarship().setPrice_credit(prevShip.getPrice_credit());
                                            if (prevShip.getNext_ships() != null) {
                                                nextShipXp = prevShip.getNext_ships().get(String.valueOf(tws.getShip_id()));
                                            }
                                        }
                                    }

                                    if (tws.getPrevWarship() != null) {
                                        tws.getPrevWarship().setNextShipXp(nextShipXp);
                                    }
                                    warship.getValue().setPrevWarship(tws.getPrevWarship());
                                    String key = warship.getValue().getName();
                                    Warship value = warship.getValue();

                                    if (value.getNext_ships() != null) {
                                        List<Warship> nextWarshipRow = new ArrayList<>();

                                        value.getWarshipModulesTreeTable().values().forEach(row -> {
                                            for (int i = 0; i < row.size(); i++) {
                                                if (nextWarshipRow.size() < row.size()) {
                                                    nextWarshipRow.add(null);
                                                }
                                            }
                                        });

                                        value.getWarshipModulesTreeTable().values().forEach(row -> {
                                            for (int i = 0; i < row.size(); i++) {
                                                if (row.get(i) != null) {
                                                    WarshipModulesTree tempWSMT = row.get(i);

                                                    if (tempWSMT.getNext_ships() != null) {
                                                        for (int j = 0; j < tempWSMT.getNext_ships().size(); j++) {
                                                            Warship totalWarship = tempWarships.get(String.valueOf(tempWSMT.getNext_ships().get(j)));
                                                            Warship nextWarshipTemp = new Warship(totalWarship.getNation(), totalWarship.getType(), totalWarship.getName(), totalWarship.getImages(), null).setTier(totalWarship.getTier()).setShip_id(totalWarship.getShip_id());
                                                            nextWarshipTemp.setPrice_credit(totalWarship.getPrice_credit());

                                                            if (value.getNext_ships() != null) {
                                                                for (Map.Entry<String, Long> entry : value.getNext_ships().entrySet()) {
                                                                    if (Long.parseLong(entry.getKey()) == nextWarshipTemp.getShip_id()) {
                                                                        nextWarshipTemp.setFromPreviousShipXp(entry.getValue());
                                                                    }
                                                                }
                                                            }

                                                            if (i < nextWarshipRow.size() && nextWarshipRow.get(i) == null) {
                                                                nextWarshipRow.set(i, nextWarshipTemp);
                                                            } else if (i - j - 1 >= 0 && i + j + 1 < nextWarshipRow.size()) {
                                                                if (nextWarshipRow.get(i - j - 1) == null) {
                                                                    nextWarshipRow.set(i - j - 1, nextWarshipTemp);
                                                                } else if (nextWarshipRow.get(i + j + 1) == null) {
                                                                    nextWarshipRow.set(i + j + 1, nextWarshipTemp);
                                                                }
                                                            } else {
                                                                nextWarshipRow.set(i, nextWarshipTemp);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        });
                                        long notNull = nextWarshipRow.stream().filter(Objects::nonNull).count();

                                        if (notNull == 0) {
                                            value.setNextWarship(null);
                                        }
                                        value.setNextWarship(nextWarshipRow);

                                        try {
                                            setUpgradesPerShip(value, consumablesData.get().getData());
                                        } catch (InterruptedException | ExecutionException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    if (gameParamsCHM.get(serverParam).containsKey(String.valueOf(value.getShip_id()))) {
                                        wsd.getData().put(key, value);
                                    }
                                });
                            } catch (InterruptedException | ExecutionException e) {
                                e.printStackTrace();
                            }
                            wsd.setData(sorter.sortShips(wsd.getData()));

                            wsdlhm.put(key2, wsd.getData());
                        });
                        nations.put(key1, setPremium(wsdlhm));
                    })).join();

                    LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Warship>>> tempTree = new LinkedHashMap<>();

                    nations.forEach((key, value) -> tempTree.put(key, setShipTree(value)));

                    LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Warship>>> tempPremiumTable = new LinkedHashMap<>();
                    for (Map.Entry<String, LinkedHashMap<String, LinkedHashMap<String, Warship>>> nation : tempTree.entrySet()) {
                        LinkedHashMap<String, LinkedHashMap<String, Warship>> temp = new LinkedHashMap<>();

                        for (int i = 1; i <= 10; i++) {
                            temp.put(String.valueOf(i), null);

                            LinkedHashMap<String, Warship> tempTier = new LinkedHashMap<>();
                            if (nation.getValue().get("Premium") != null) {
                                for (Warship premium1 : (nation.getValue().get("Premium")).values()) {
                                    if (i == premium1.getTier()) {
                                        for (Warship premium2 : nation.getValue().get("Premium").values()) {
                                            if (premium2.getTier() == i) {
                                                tempTier.put(premium2.getName(), premium2);
//                                tempTier.put(premium2.getName().replace("'", ""), premium2);
                                            }
                                        }
                                    }
                                }
                            }

                            if (nation.getValue().get("Arpeggio") != null) {
                                for (Warship premium1 : nation.getValue().get("Arpeggio").values()) {
                                    if (i == premium1.getTier()) {
                                        for (Warship premium2 : nation.getValue().get("Arpeggio").values()) {
                                            if (premium2.getTier() == i) {
                                                tempTier.put(premium2.getName(), premium2);
//                                tempTier.put(premium2.getName().replace("'", ""), premium2);
                                            }
                                        }
                                    }
                                }
                            }

                            if (nation.getValue().get("HSF") != null) {
                                for (Warship premium1 : nation.getValue().get("HSF").values()) {
                                    if (i == premium1.getTier()) {
                                        for (Warship premium2 : nation.getValue().get("HSF").values()) {
                                            if (premium2.getTier() == i) {
                                                tempTier.put(premium2.getName(), premium2);
//                                tempTier.put(premium2.getName().replace("'", ""), premium2);
                                            }
                                        }
                                    }
                                }
                            }

                            if (tempTier.size() > 0) {
                                temp.put(String.valueOf(i), tempTier);
                            }
                        }
                        tempPremiumTable.put(nation.getKey(), temp);
                    }

                    LinkedHashMap<String, LinkedHashMap> tempData = new LinkedHashMap<>();
                    tempData.put("encyclopedia", mapper.convertValue(encyclopedia, LinkedHashMap.class));
                    tempData.put("nations", tempTree);
                    tempData.put("premiumTable", tempPremiumTable);
                    tempData.put("rawShipData", rawShipData);
                    tempData.put("upgrades", tempUpgrades);
                    tempData.put("skills", setCrewSkills(crewsSkillsData.get().getData()));
                    tempData.put("uniqueSkills", uniqueSkills);
                    tempData.put("commanders", getCommanders(tempCommanders, uniqueSkills));
                    tempData.put("commandersRanks", commandersRankData.get().getData());
                    tempData.put("exteriors", tempExteriors);
                    data.put(serverParam, tempData);

                    isFirstRun = false;
                    serverParam = "live".equalsIgnoreCase(serverParam) ? "test" : "live";
                }
                shipHashMap.clear();
                tempWarships.clear();
            }
            loadFinish.put("loadFinish", 1);
            log.info("Load finished");
        }
    }

    private LinkedHashMap<String, LinkedHashMap<String, Warship>> setPremium(LinkedHashMap<String, LinkedHashMap<String, Warship>> nation)
    {
        LinkedHashMap<String, LinkedHashMap<String, Warship>> tempNation = new LinkedHashMap<>();
        LinkedHashMap<String, Warship> tempPremium = new LinkedHashMap<>();
        LinkedHashMap<String, Warship> tempARP = new LinkedHashMap<>();
        LinkedHashMap<String, Warship> tempHSF = new LinkedHashMap<>();

        nation.forEach((key, value) -> {
            LinkedHashMap<String, Warship> tempShips = new LinkedHashMap<>();

            value.forEach((key1, value1) -> {
                if (value1.isIs_premium() || value1.getPrice_gold() > 0 || value1.getPrice_credit() == 1 || (value1.getPrice_credit() == 0 && value1.getTier() != 1)) {
                    if (value1.getName().contains("ARP ")) {
                        value1.setType("Arpeggio");
                        value1.setPremium(true);
                        tempARP.put(key1, value1);
                    } else if (value1.getName().contains("HSF ")) {
                        value1.setType("HSF");
                        value1.setPremium(true);
                        tempHSF.put(key1, value1);
                    } else {
                        value1.setType("Premium");
                        value1.setPremium(true);
                        tempPremium.put(key1, value1);
                    }
                } else {
                    tempShips.put(key1, value1);
                }
            });

            if (tempShips.size() != 0) {
                tempNation.put(key, tempShips);
            }
        });

        if (tempPremium.size() != 0) {
            LinkedHashMap<String, Warship> tempSortedPremium = sorter.sortShips(tempPremium);
            tempNation.put("Premium", tempSortedPremium);
        }

        if (tempARP.size() != 0) {
            LinkedHashMap<String, Warship> tempSortedARP = sorter.sortShips(tempARP);
            tempNation.put("Arpeggio", tempSortedARP);
        }

        if (tempHSF.size() != 0) {
            LinkedHashMap<String, Warship> tempSortedHSF = sorter.sortShips(tempHSF);
            tempNation.put("HSF", tempSortedHSF);
        }

        return tempNation;
    }

    private void setUpgradesPerShip(Warship warship, LinkedHashMap<String, Consumables> consumables)
    {
        LinkedHashMap<String, Consumables> tempConsumablesHM = new LinkedHashMap<>();

        consumables.values().forEach(c -> {
            if (c.getType().equalsIgnoreCase("Modernization")) {
                Modernization mdz = mapper.convertValue(gameParamsCHM.get(serverParam).get(String.valueOf(c.getConsumable_id())), Modernization.class);

                if (mdz.getNation().stream().anyMatch(n -> n.equalsIgnoreCase(warship.getNationConvert())) && mdz.getShiptype().contains(warship.getDefaultType()) && mdz.getShiplevel().contains((int) warship.getTier()) && !mdz.getExcludes().contains(idToName.get(serverParam).get(String.valueOf(warship.getShip_id())))) {
                    if (!warship.getUpgrades().contains(mdz.getId())) {
                        warship.getUpgrades().add(mdz.getId());
                    }
                }
                else if (mdz.getShips().size() > 0) {
                    for (String s : mdz.getShips()) {
                        if (s.equalsIgnoreCase(idToName.get(serverParam).get(String.valueOf(warship.getShip_id())))) {
                            if (!warship.getUpgrades().contains(mdz.getId())) {
                                warship.getUpgrades().add(mdz.getId());
                            }
                        }
                    }
                }
            }
        });

        List<Integer> priceCredits = new ArrayList<>();
        priceCredits.add(125000);
        priceCredits.add(250000);
        priceCredits.add(500000);
        priceCredits.add(1000000);
        priceCredits.add(2000000);
        priceCredits.add(1250000);

        warship.getUpgrades().forEach(upgrade_id ->
        {
            Consumables tempConsumables = consumables.get(String.valueOf(upgrade_id));
            if (tempConsumables != null) {
                if (tempConsumables.getPrice_credit() == 125000) {
                    tempConsumables.setUpgradeSlot("slot1");
                } else if (tempConsumables.getPrice_credit() == 250000) {
                    tempConsumables.setUpgradeSlot("slot2");
                } else if (tempConsumables.getPrice_credit() == 500000) {
                    tempConsumables.setUpgradeSlot("slot3");
                } else if (tempConsumables.getPrice_credit() == 1000000) {
                    tempConsumables.setUpgradeSlot("slot4");
                } else if (tempConsumables.getPrice_credit() == 2000000) {
                    tempConsumables.setUpgradeSlot("slot5");
                } else if (tempConsumables.getPrice_credit() == 3000000) {
                    tempConsumables.setUpgradeSlot("slot6");
                } else if (tempConsumables.getPrice_credit() == 1250000 || !priceCredits.contains((int) tempConsumables.getPrice_credit())) {
                    Modernization mod = mapper.convertValue(gameParamsCHM.get(serverParam).get(String.valueOf(tempConsumables.getConsumable_id())), Modernization.class);

                    if (mod.getSlot() == 0) {
                        tempConsumables.setPrice_credit(125000);
                        tempConsumables.setUpgradeSlot("slot1");
                    } else if (mod.getSlot() == 1) {
                        tempConsumables.setPrice_credit(250000);
                        tempConsumables.setUpgradeSlot("slot2");
                    } else if (mod.getSlot() == 2) {
                        tempConsumables.setPrice_credit(500000);
                        tempConsumables.setUpgradeSlot("slot3");
                    } else if (mod.getSlot() == 3) {
                        tempConsumables.setPrice_credit(1000000);
                        tempConsumables.setUpgradeSlot("slot4");
                    } else if (mod.getSlot() == 4) {
                        tempConsumables.setPrice_credit(2000000);
                        tempConsumables.setUpgradeSlot("slot5");
                    } else if (mod.getSlot() == 5) {
                        tempConsumables.setPrice_credit(3000000);
                        tempConsumables.setUpgradeSlot("slot6");
                    }
                }
//                String localizedName = (String) global.get(serverParam).get("IDS_TITLE_" + idToName.get(serverParam).get(String.valueOf(upgrade_id)).toUpperCase());
//                tempConsumables.setName(StringUtils.isNotEmpty(localizedName) ? localizedName : tempConsumables.getName());

                tempConsumablesHM.put(tempConsumables.getName(), tempConsumables);
            }
        });
        warship.setUpgradesNew(tempConsumablesHM);
    }

    private LinkedHashMap<String, LinkedHashMap<String, CrewSkills>> setCrewSkills(LinkedHashMap<String, CrewSkills> crewSkills)
    {
        LinkedHashMap<String, LinkedHashMap<String, CrewSkills>> temp = new LinkedHashMap<>();

        crewSkills.forEach((key, value1) -> {
            int tier = value1.getTier();
            LinkedHashMap<String, CrewSkills> tempTier = new LinkedHashMap<>();

            for (int i = 0; i < 8; i++) {
                CrewSkills tempCrewSkills = new CrewSkills();
                tempCrewSkills.setType_id(i);
                tempTier.put(String.valueOf(i), tempCrewSkills);
            }

            crewSkills.values().forEach(value -> {
                if (value.getTier() == tier) {
                    List<String> tempPerkDescription = new ArrayList<>();
                    String tempDescription = "";

                    value.getPerks().forEach(perk -> tempPerkDescription.add(perk.getDescription()));

                    for (int i = 0; i < tempPerkDescription.size(); i++) {
                        tempDescription = tempDescription.concat(tempPerkDescription.get(i));
                        if (i < tempPerkDescription.size() - 1) {
                            tempDescription = tempDescription + "\n";
                        }
                    }
                    value.setDescription(tempDescription);

                    tempTier.put(String.valueOf(value.getType_id()), value);
                }
            });

            temp.put(String.valueOf(tier), sorter.sortCrewSkills(tempTier));
        });
        LinkedHashMap<String, LinkedHashMap<String, CrewSkills>> sorted = new LinkedHashMap<>();
        temp.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(entry -> sorted.put(entry.getKey(), entry.getValue()));

        return sorted;
    }

    private LinkedHashMap<String, LinkedHashMap<String, Warship>> setShipTree(LinkedHashMap<String, LinkedHashMap<String, Warship>> nation)
    {
        LinkedHashMap<String, LinkedHashMap<String, Warship>> temp = new LinkedHashMap<>();

        nation.forEach((key, value) -> {
            LinkedHashMap<String, Warship> test = new LinkedHashMap<>();

            int isTrueMaxTier = 0;
            int isTrueMinTier = 0;
            int isFalseMaxTier = 0;
            int isFalseMinTier = 0;

            for (Map.Entry<String, Warship> entry : value.entrySet()) {
                Warship tempWarship = mapper.convertValue(entry.getValue(), Warship.class);

                if (isTrueMinTier == 0) {
                    isTrueMinTier = (int) tempWarship.getTier();
                }

                test.put(tempWarship.getName(), tempWarship);
//                test.put(tempWarship.getName().replace("'", ""), tempWarship);

                List<Warship> tempNextWarships = tempWarship.getNextWarship().stream().filter(Objects::nonNull).collect(Collectors.toList());

                for (int i = 0; i < tempNextWarships.size(); i++) {
                    if (tempNextWarships.get(i).getType().equals(tempWarship.getType())) {
                        Warship nextWarship = mapper.convertValue(value.get(tempNextWarships.get(i).getName()), Warship.class);

                        if (nextWarship != null) {
                            boolean isFirst = true;
                            if ((i > 0 && tempNextWarships.get(0).getType().equals(tempNextWarships.get(i).getType())) || !test.get(tempWarship.getName()).isFirst()) {
                                isFirst = false;

                                if (isFalseMinTier == 0) {
                                    isFalseMinTier = (int) tempWarship.getTier();
                                }
                            }

                            nextWarship.setFirst(isFirst);

                            if (nextWarship.getNextWarship().stream().noneMatch(Objects::nonNull)) {
                                if (nextWarship.isFirst()) {
                                    isTrueMaxTier = (int) nextWarship.getTier();
                                } else {
                                    isFalseMaxTier = (int) nextWarship.getTier();
                                }
                            }

                            test.put(nextWarship.getName(), nextWarship);
                        }
                    }
                }
            }

            for (Warship warship : test.values()) {
                if (warship.isFirst()) {
                    warship.setFirstMinTier(isTrueMinTier);
                    warship.setFirstMaxTier(isTrueMaxTier);
                    if (isFalseMaxTier != 0) {
                        warship.setHasSecondLine(true);
                        warship.setSecondMaxTier(isFalseMaxTier);
                        warship.setSecondMinTier(isFalseMinTier);
                    }
                }
//                else
//                {
//                    warship.setMaxTier(isFalseMaxTier);
//                }
                test.put(warship.getName(), warship);
//                test.put(warship.getName().replace("'", ""), warship);
                rawShipData.put(warship.getName(), warship);
//                rawShipData.put(warship.getName().replace("'", ""), warship);
            }
            temp.put(key, test);
        });

        return temp;
    }

    private LinkedHashMap<String, LinkedHashMap<String, GPCommander>> getCommanders(LinkedHashMap<String, Commanders> commanders, LinkedHashMap<String, LinkedHashMap<String, UniqueTemp>> uniqueSkills)
    {
        LinkedHashMap<String, LinkedHashMap<String, GPCommander>> allCommanders = new LinkedHashMap<>();

        LinkedHashMap<String, GPCommander> defaultCommanders = new LinkedHashMap<>();

        commanders.forEach((key, value) -> {
            if (value.getFirst_names().size() > 1 && value.getLast_names().size() > 0) {
                GPCommander gpCommander = mapper.convertValue(gameParamsCHM.get(serverParam).get(key), GPCommander.class);
                defaultCommanders.put(value.getNation(), gpCommander);
            }
        });

        defaultCommanders.forEach((key, value) -> {
            LinkedHashMap<String, GPCommander> nationCommanders = new LinkedHashMap<>();
            nationCommanders.put("default", value);

            for (Map.Entry<String, Commanders> cEntry : commanders.entrySet()) {
                if (cEntry.getValue().getNation().equalsIgnoreCase(key)) {
                    String temp1 = value.getSkills().getModifiers().toString();

                    GPCommander gpCommander = mapper.convertValue(gameParamsCHM.get(serverParam).get(cEntry.getKey()), GPCommander.class);
                    if (gpCommander != null) {
                        String temp2 = gpCommander.getSkills().getModifiers().toString();

                        if (!temp1.equalsIgnoreCase(temp2) || gpCommander.getUniqueSkills().getModifier().size() > 0) {
                            String name = cEntry.getValue().getFirst_names().size() > 0 ? cEntry.getValue().getFirst_names().get(0) : gpCommander.getName();
                            name = name.replace(".", "");
                            nationCommanders.put(name, gpCommander);

                            if (gpCommander.getUniqueSkills().getModifier().size() > 0) {
                                uniqueSkills.put(name, gpCommander.getUniqueSkills().getModifier());
                            }
                        }
                    }
                }
            }

            allCommanders.put(key, nationCommanders);
        });
        return allCommanders;
    }
}
