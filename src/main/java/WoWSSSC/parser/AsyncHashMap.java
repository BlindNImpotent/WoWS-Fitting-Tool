package WoWSSSC.parser;

import WoWSSSC.model.WoWSAPI.consumables.Consumables;
import WoWSSSC.model.WoWSAPI.consumables.ConsumablesData;
import WoWSSSC.model.WoWSAPI.exterior.Exterior;
import WoWSSSC.model.WoWSAPI.exterior.ExteriorData;
import WoWSSSC.model.WoWSAPI.info.Encyclopedia;
import WoWSSSC.model.WoWSAPI.shipprofile.Ship;
import WoWSSSC.model.WoWSAPI.skills.CrewSkills;
import WoWSSSC.model.WoWSAPI.skills.CrewSkillsData;
import WoWSSSC.model.WoWSAPI.warships.Warship;
import WoWSSSC.model.WoWSAPI.warships.WarshipData;
import WoWSSSC.model.WoWSAPI.upgrade.Upgrade;
import WoWSSSC.model.WoWSAPI.upgrade.UpgradeData;
import WoWSSSC.model.WoWSAPI.warships.WarshipModulesTree;
import WoWSSSC.model.gameparams.Consumables.Consumable;
import WoWSSSC.utils.Sorter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Created by Aesis on 2016-11-15.
 */
@Component
public class AsyncHashMap implements CommandLineRunner
{
    @Autowired
    private APIJsonParser apiJsonParser;

    @Autowired
    private LinkedHashMap<String, LinkedHashMap> data;

    @Autowired
    private HashMap<String, Ship> shipHashMap;

    @Autowired
    private Sorter sorter;

    private ObjectMapper mapper = new ObjectMapper();

    private LinkedHashMap<String, Warship> rawShipData = new LinkedHashMap<>();

    @Override
    public void run(String... strings) throws Exception
    {
        List<String> nationsString = new ArrayList<>();
        List<String> shipTypeString = new ArrayList<>();

        LinkedHashMap<String, LinkedHashMap> nations = new LinkedHashMap<>();

        apiJsonParser.setNotification();

        apiJsonParser.setGameParams();

        HashMap<String, Warship> tempWarships = apiJsonParser.getTotalWarships();
        tempWarships.values().forEach(warship ->
        {
            if (warship.getNext_ships().size() > 0)
            {
                Warship tempWS = new Warship(warship.getNation(), warship.getType(), warship.getName(), warship.getImages(), null).setTier(warship.getTier()).setShip_id(warship.getShip_id());
                warship.getNext_ships().keySet().forEach(shipKey ->
                {
                    tempWarships.get(shipKey).setPrevWarship(tempWS);
                });
            }
        });

        Encyclopedia encyclopedia = apiJsonParser.getEncyclopedia().getData();

        nationsString.addAll(encyclopedia.getShip_nations().keySet());
        shipTypeString.addAll(encyclopedia.getShip_types().keySet());
        Collections.sort(nationsString);
        Collections.sort(shipTypeString);

        LinkedHashMap<String, LinkedHashMap<String, CompletableFuture<WarshipData>>> futures = new LinkedHashMap<>();

        for (int i = 0; i < nationsString.size(); i++)
        {
            LinkedHashMap<String, CompletableFuture<WarshipData>> temp = new LinkedHashMap<>();
            for (int j = 0; j < shipTypeString.size(); j++)
            {
                temp.put(shipTypeString.get(j), apiJsonParser.getNationShip(nationsString.get(i), shipTypeString.get(j)));
            }
            futures.put(nationsString.get(i), temp);
        }

        CompletableFuture<CrewSkillsData> crewsSkillsData = apiJsonParser.getCrewSkills();

        CompletableFuture<ConsumablesData> consumablesData = apiJsonParser.getConsumables();

        CompletableFuture.runAsync(() -> futures.entrySet().forEach(futureEntry ->
        {
            LinkedHashMap<String, LinkedHashMap> wsdlhm = new LinkedHashMap<>();

            futureEntry.getValue().entrySet().forEach(shipType ->
            {
                WarshipData wsd = new WarshipData();
                try
                {
                    shipType.getValue().get().getData().entrySet().forEach(warship ->
                    {
                        Warship tws = tempWarships.get(String.valueOf(warship.getValue().getShip_id()));

                        Warship prevShip;
                        long nextShipXp = 0;
                        if (tws.getPrevWarship() != null)
                        {
                            prevShip = tempWarships.get(String.valueOf(tws.getPrevWarship().getShip_id()));

                            if (prevShip.getNext_ships() != null)
                            {
                                nextShipXp = prevShip.getNext_ships().get(String.valueOf(tws.getShip_id()));
                            }
                        }

                        if (tws.getPrevWarship() != null)
                        {
                            tws.getPrevWarship().setNextShipXp(nextShipXp);
                        }
                        warship.getValue().setPrevWarship(tws.getPrevWarship());
                        String key = warship.getValue().getName();
                        Warship value = warship.getValue();

                        if (value.getNext_ships() != null)
                        {
                            List<Warship> nextWarshipRow = new ArrayList<>();

                            value.getWarshipModulesTreeTable().values().forEach(row ->
                            {
                                for (int i = 0; i < row.size(); i++)
                                {
                                    if (nextWarshipRow.size() < row.size())
                                    {
                                        nextWarshipRow.add(null);
                                    }
                                }
                            });

                            value.getWarshipModulesTreeTable().values().forEach(row ->
                            {
                                for (int i = 0; i < row.size(); i++)
                                {
                                    if (row.get(i) != null)
                                    {
                                        WarshipModulesTree tempWSMT = row.get(i);

                                        if (tempWSMT.getNext_ships() != null)
                                        {
                                            for (int j = 0; j < tempWSMT.getNext_ships().size(); j++)
                                            {
                                                Warship totalWarship = tempWarships.get(String.valueOf(tempWSMT.getNext_ships().get(j)));
                                                Warship nextWarshipTemp = new Warship(totalWarship.getNation(), totalWarship.getType(), totalWarship.getName(), totalWarship.getImages(), null).setTier(totalWarship.getTier()).setShip_id(totalWarship.getShip_id());

                                                if (value.getNext_ships() != null)
                                                {
                                                    for (Map.Entry<String, Long> entry : value.getNext_ships().entrySet())
                                                    {
                                                        if (Long.parseLong(entry.getKey()) == nextWarshipTemp.getShip_id())
                                                        {
                                                            nextWarshipTemp.setFromPreviousShipXp(entry.getValue());
                                                        }
                                                    }
                                                }

                                                if (i < nextWarshipRow.size() && nextWarshipRow.get(i) == null)
                                                {
                                                    nextWarshipRow.set(i, nextWarshipTemp);
                                                }
                                                else if (i - j - 1 >= 0 && i + j + 1 < nextWarshipRow.size())
                                                {
                                                    if (nextWarshipRow.get(i - j - 1) == null)
                                                    {
                                                        nextWarshipRow.set(i - j - 1, nextWarshipTemp);
                                                    }
                                                    else if (nextWarshipRow.get(i + j + 1) == null)
                                                    {
                                                        nextWarshipRow.set(i + j + 1, nextWarshipTemp);
                                                    }
                                                }
                                                else
                                                {
                                                    nextWarshipRow.set(i, nextWarshipTemp);
                                                }
                                            }
                                        }
                                    }
                                }
                            });
                            long notNull = nextWarshipRow.stream().filter(module -> module != null).count();

                            if (notNull == 0)
                            {
                                value.setNextWarship(null);
                            }
                            value.setNextWarship(nextWarshipRow);

                            try
                            {
                                setUpgradesPerShip(value, consumablesData.get().getData());
                            }
                            catch (InterruptedException | ExecutionException e)
                            {
                                e.printStackTrace();
                            }
                        }
                        wsd.getData().put(key, value);
                    });
                }
                catch (InterruptedException | ExecutionException e)
                {
                    e.printStackTrace();
                }
                wsd.setData(sorter.sortShips(wsd.getData()));

                wsdlhm.put(shipType.getKey(), wsd.getData());
            });
            nations.put(futureEntry.getKey(), setPremium(wsdlhm));
//            nations.put(futureEntry.getKey(), wsdlhm);
        })).join();

        tempWarships.clear();

        LinkedHashMap<String, LinkedHashMap> tempTree = new LinkedHashMap<>();

        nations.entrySet().forEach(entry -> tempTree.put(entry.getKey(), setShipTree(entry.getValue())));

        LinkedHashMap<String, Consumables> tempUpgrades = new LinkedHashMap<>();
        consumablesData.get().getData().entrySet().forEach(entry ->
        {
            tempUpgrades.put(entry.getKey(), entry.getValue());
        });

        LinkedHashMap<String, LinkedHashMap> tempExteriors = new LinkedHashMap<>();
        LinkedHashMap<String, Consumables> tempFlags = new LinkedHashMap<>();
        LinkedHashMap<String, Consumables> tempPermoflage = new LinkedHashMap<>();
        LinkedHashMap<String, Consumables> tempCamouflage = new LinkedHashMap<>();

        consumablesData.get().getData().entrySet().forEach(entry ->
        {
            if (entry.getValue().getType().equals("Flags"))
            {
//                List<String> stringsList = new ArrayList<>();
//                stringsList.add(entry.getValue().getDescription());
//                entry.getValue().getProfileHashMap().values().forEach(value ->
//                {
//                    if (value != null)
//                    {
//                        String tempDescription = "\n" + value.get("description");
//                        stringsList.add(tempDescription);
//                    }
//                });
//
//                String finalString = "";
//                for (String s : stringsList)
//                {
//                    finalString = finalString + s;
//                }
//                entry.getValue().setDescription(finalString);

                tempFlags.put(entry.getKey(), entry.getValue());
            }
            else if (entry.getValue().getType().equals("Camouflage"))
            {
                tempCamouflage.put(entry.getKey(), entry.getValue());
            }
            else if (entry.getValue().getType().equals("Permoflage"))
            {
                tempPermoflage.put(entry.getKey(), entry.getValue());
            }
            else if (entry.getValue().getType().equals("Modernization"))
            {
                tempUpgrades.put(entry.getKey(), entry.getValue());
            }
        });

        tempExteriors.put("Flags", tempFlags);
        tempExteriors.put("Camouflage", tempCamouflage);
        tempExteriors.put("Permoflage", tempPermoflage);

        LinkedHashMap<String, LinkedHashMap> tempPremiumTable = new LinkedHashMap<>();
        for (Map.Entry<String, LinkedHashMap> nation : tempTree.entrySet())
        {
            LinkedHashMap<String, LinkedHashMap<String, Warship>> temp = new LinkedHashMap<>();

            for (int i = 1; i <= 10; i++)
            {
                temp.put(String.valueOf(i), null);

                for (Warship premium1 : ((LinkedHashMap<String, Warship>) nation.getValue().get("Premium")).values())
                {
                    if (i == premium1.getTier())
                    {
                        LinkedHashMap<String, Warship> tempTier = new LinkedHashMap<>();

                        for (Warship premium2 : ((LinkedHashMap<String, Warship>) nation.getValue().get("Premium")).values())
                        {
                            if (premium2.getTier() == i)
                            {
                                tempTier.put(premium2.getName(), premium2);
//                                tempTier.put(premium2.getName().replace("'", ""), premium2);
                            }
                        }
                        temp.put(String.valueOf(i), tempTier);
                    }
                }
            }
            tempPremiumTable.put(nation.getKey(), temp);
        }

        data.clear();
        data.put("encyclopedia", mapper.convertValue(encyclopedia, LinkedHashMap.class));
        data.put("nations", tempTree);
        data.put("premiumTable", tempPremiumTable);
        data.put("rawShipData", rawShipData);
//        data.put("nations", nations);
        data.put("upgrades", tempUpgrades);
        data.put("skills", setCrewSkills(crewsSkillsData.get().getData()));
        data.put("exteriors", tempExteriors);
//        data.put("exteriors", setExteriors(exteriorData.get().getData()));

//        data.put("test", tempTree);

        shipHashMap.clear();
    }

    private LinkedHashMap<String, LinkedHashMap> setPremium(LinkedHashMap<String, LinkedHashMap> nation)
    {
        LinkedHashMap<String, LinkedHashMap> tempNation = new LinkedHashMap<>();
        LinkedHashMap<String, Warship> tempPremium = new LinkedHashMap<>();
        LinkedHashMap<String, Warship> tempARP = new LinkedHashMap<>();

        nation.entrySet().forEach(shipType ->
        {
            LinkedHashMap<String, Warship> tempShips = new LinkedHashMap<>();

            shipType.getValue().entrySet().forEach(ship ->
            {
                Map.Entry<String, Warship> temp = (Map.Entry<String, Warship>) ship;

                if (temp.getValue().isIs_premium() || temp.getValue().getPrice_gold() > 0 || temp.getValue().getPrice_credit() == 1 || (temp.getValue().getPrice_credit() == 0 && temp.getValue().getTier() != 1))
                {
                    if (temp.getValue().getName().contains("ARP "))
                    {
                        temp.getValue().setType("Arpeggio");
                        tempARP.put(temp.getKey(), temp.getValue());
                    }
                    else
                    {
                        temp.getValue().setType("Premium");
                        tempPremium.put(temp.getKey(), temp.getValue());
                    }
                }
                else
                {
                    tempShips.put(temp.getKey(), temp.getValue());
                }
            });

            if (tempShips.size() != 0)
            {
                tempNation.put(shipType.getKey(), tempShips);
            }
        });

        if (tempPremium.size() != 0)
        {
            LinkedHashMap<String, Warship> tempSortedPremium = sorter.sortShips(tempPremium);
            tempNation.put("Premium", tempSortedPremium);
        }

        if (tempARP.size() != 0)
        {
            LinkedHashMap<String, Warship> tempSortedARP = sorter.sortShips(tempARP);
            tempNation.put("Arpeggio", tempSortedARP);
        }

        return tempNation;
    }

    private void setUpgradesPerShip(Warship warship, LinkedHashMap<String, Consumables> consumables)
    {
        LinkedHashMap<String, Consumables> tempConsumablesHM = new LinkedHashMap<>();

        warship.getUpgrades().forEach(upgrade_id ->
        {
            Consumables tempConsumables = consumables.get(String.valueOf(upgrade_id));
            if (tempConsumables != null)
            {
                if (tempConsumables.getPrice_credit() == 125000)
                {
                    tempConsumables.setUpgradeSlot("slot1");
                }
                else if (tempConsumables.getPrice_credit() == 500000)
                {
                    tempConsumables.setUpgradeSlot("slot2");
                }
                else if (tempConsumables.getPrice_credit() == 3000000)
                {
                    tempConsumables.setUpgradeSlot("slot3");
                }
                else if (tempConsumables.getPrice_credit() == 250000)
                {
                    tempConsumables.setUpgradeSlot("slot4");
                }
                else if (tempConsumables.getPrice_credit() == 1000000)
                {
                    tempConsumables.setUpgradeSlot("slot5");
                }
                else if (tempConsumables.getPrice_credit() == 2000000)
                {
                    tempConsumables.setUpgradeSlot("slot6");
                }

                tempConsumablesHM.put(tempConsumables.getName(), tempConsumables);
            }
        });
        warship.setUpgradesNew(tempConsumablesHM);
    }

    private LinkedHashMap<String, LinkedHashMap> setCrewSkills(LinkedHashMap<String, CrewSkills> crewSkills)
    {
        LinkedHashMap<String, LinkedHashMap> temp = new LinkedHashMap<>();

        crewSkills.entrySet().forEach(entry ->
        {
            int tier = entry.getValue().getTier();
            LinkedHashMap<String, CrewSkills> tempTier = new LinkedHashMap<>();

            for (int i = 0; i < 8; i++)
            {
                CrewSkills tempCrewSkills = new CrewSkills();
                tempCrewSkills.setType_id(i);
                tempTier.put(String.valueOf(i), tempCrewSkills);
            }

            crewSkills.values().forEach(value ->
            {
                if (value.getTier() == tier)
                {
                    List<String> tempPerkDescription = new ArrayList<>();
                    String tempDescription = "";

                    value.getPerks().forEach(perk -> tempPerkDescription.add(perk.getDescription()));

                    for (int i = 0; i < tempPerkDescription.size(); i++)
                    {
                        tempDescription = tempDescription + tempPerkDescription.get(i);
                        if (i < tempPerkDescription.size() - 1)
                        {
                            tempDescription = tempDescription + "\n";
                        }
                    }
                    value.setDescription(tempDescription);

                    tempTier.put(String.valueOf(value.getType_id()), value);
                }
            });

            temp.put(String.valueOf(tier), sorter.sortCrewSkills(tempTier));
        });
        LinkedHashMap<String, LinkedHashMap> sorted = new LinkedHashMap<>();
        temp.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(entry -> sorted.put(entry.getKey(), entry.getValue()));

        return sorted;
    }

    private LinkedHashMap<String, LinkedHashMap> setExteriors(LinkedHashMap<String, Exterior> exteriorData)
    {
        LinkedHashMap<String, LinkedHashMap> tempExteriors = new LinkedHashMap<>();
        exteriorData.values().forEach(value -> {
            String type = value.getType();
            LinkedHashMap<String, Exterior> temp = new LinkedHashMap<>();

            exteriorData.values().forEach(newVal ->
            {
                if (newVal.getType().equals(type))
                {
                    temp.put(String.valueOf(newVal.getExterior_id()), newVal);
                }
            });
            tempExteriors.put(type, temp);
        });

        return tempExteriors;
    }

    private LinkedHashMap<String, LinkedHashMap<String, Warship>> setShipTree(LinkedHashMap<String, LinkedHashMap> nation)
    {
        LinkedHashMap<String, LinkedHashMap<String, Warship>> temp = new LinkedHashMap<>();

        nation.entrySet().forEach(shipType ->
        {
            LinkedHashMap<String, Warship> test = new LinkedHashMap<>();

            int isTrueMaxTier = 0;
            int isFalseMaxTier = 0;

            LinkedHashMap<String, LinkedHashMap<String, Warship>> wslhm = mapper.convertValue(shipType.getValue(), LinkedHashMap.class);

            for (Map.Entry<String, LinkedHashMap<String, Warship>> entry : wslhm.entrySet())
            {
                Warship tempWarship = mapper.convertValue(entry.getValue(), Warship.class);

                test.put(tempWarship.getName(), tempWarship);
//                test.put(tempWarship.getName().replace("'", ""), tempWarship);

                List<Warship> tempNextWarships = tempWarship.getNextWarship().stream().filter(nextWarship -> nextWarship != null).collect(Collectors.toList());

                for (int i = 0; i < tempNextWarships.size(); i++)
                {
                    if (tempNextWarships.get(i).getType().equals(tempWarship.getType()))
                    {
                        Warship nextWarship = mapper.convertValue(wslhm.get(tempNextWarships.get(i).getName()), Warship.class);

                        boolean isFirst = true;
                        if ((i > 0 && tempNextWarships.get(0).getType().equals(tempNextWarships.get(i).getType())) || !test.get(tempWarship.getName()).isFirst())
                        {
                            isFirst = false;
                        }

                        nextWarship.setFirst(isFirst);

                        if (nextWarship.getNextWarship().stream().filter(nw -> nw != null).count() == 0)
                        {
                            if (nextWarship.isFirst())
                            {
                                isTrueMaxTier = (int) nextWarship.getTier();
                            }
                            else
                            {
                                isFalseMaxTier = (int) nextWarship.getTier();
                            }
                        }

                        test.put(nextWarship.getName(), nextWarship);
                    }
                }
            }

            for (Warship warship : test.values())
            {
                if (warship.isFirst())
                {
                    warship.setMaxTier(isTrueMaxTier);
                }
                else
                {
                    warship.setMaxTier(isFalseMaxTier);
                }
                test.put(warship.getName(), warship);
//                test.put(warship.getName().replace("'", ""), warship);
                rawShipData.put(warship.getName(), warship);
//                rawShipData.put(warship.getName().replace("'", ""), warship);
            }
            temp.put(shipType.getKey(), test);
        });

        return temp;
    }
}
