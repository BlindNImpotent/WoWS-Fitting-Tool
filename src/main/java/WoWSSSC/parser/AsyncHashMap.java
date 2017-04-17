package WoWSSSC.parser;

import WoWSSSC.model.WoWSAPI.exterior.Exterior;
import WoWSSSC.model.WoWSAPI.exterior.ExteriorData;
import WoWSSSC.model.WoWSAPI.info.Encyclopedia;
import WoWSSSC.model.WoWSAPI.shipprofile.Ship;
import WoWSSSC.model.WoWSAPI.skills.CrewSkills;
import WoWSSSC.model.WoWSAPI.skills.CrewSkillsData;
import WoWSSSC.model.WoWSAPI.warships.TotalWarship;
import WoWSSSC.model.WoWSAPI.warships.Warship;
import WoWSSSC.model.WoWSAPI.warships.WarshipData;
import WoWSSSC.model.WoWSAPI.upgrade.Upgrade;
import WoWSSSC.model.WoWSAPI.upgrade.UpgradeData;
import WoWSSSC.model.WoWSAPI.warships.WarshipModulesTree;
import WoWSSSC.utils.Sorter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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

    @Override
    public void run(String... strings) throws Exception
    {
        List<String> nationsString = new ArrayList<>();
        List<String> shipTypeString = new ArrayList<>();

        LinkedHashMap<String, LinkedHashMap> nations = new LinkedHashMap<>();

        apiJsonParser.setNotification();

        apiJsonParser.setGameParams();

        HashMap<String, TotalWarship> tempWarships = apiJsonParser.getTotalWarships();

        tempWarships.values().forEach(warship ->
        {
            if (warship.getNext_ships().size() > 0)
            {
                warship.getNext_ships().keySet().forEach(shipKey ->
                {
                    tempWarships.values().forEach(tempWarship ->
                    {
                        if (String.valueOf(tempWarship.getShip_id()).equals(shipKey))
                        {
                            Warship tempWS = new Warship(warship.getNation(), warship.getType(), warship.getName(), warship.getImages(), null);
                            tempWarship.setPrevWarship(tempWS);
                        }
                    });
                });
            }
        });

        Encyclopedia encyclopedia = apiJsonParser.getEncyclopedia().getData();

        encyclopedia.getShip_nations().entrySet().forEach(entry -> nationsString.add(entry.getKey()));
        encyclopedia.getShip_types().entrySet().forEach(entry -> shipTypeString.add(entry.getKey()));
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

        CompletableFuture<UpgradeData> upgradeData = apiJsonParser.getUpgrades();
        CompletableFuture<CrewSkillsData> crewsSkillsData = apiJsonParser.getCrewSkills();
        CompletableFuture<ExteriorData> exteriorData = apiJsonParser.getExteriorData();

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
                        TotalWarship tws = tempWarships.get(String.valueOf(warship.getValue().getShip_id()));
                        warship.getValue().setPrevWarship(tws.getPrevWarship());
                        String key = warship.getValue().getName();
                        key = key.replace("'", "");
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
                                                TotalWarship totalWarship = tempWarships.get(String.valueOf(tempWSMT.getNext_ships().get(j)));
                                                Warship nextWarshipTemp = new Warship(totalWarship.getNation(), totalWarship.getType(), totalWarship.getName(), totalWarship.getImages(), null);

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
                                setUpgradesPerShip(value, upgradeData.get().getData());
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

        data.clear();
        data.put("nations", nations);
        data.put("upgrades", upgradeData.get().getData());
        data.put("skills", setCrewSkills(crewsSkillsData.get().getData()));
        data.put("exterior", exteriorData.get().getData());
        data.put("exteriors", setExteriors(exteriorData.get().getData()));
        shipHashMap.clear();
    }

    private LinkedHashMap<String, LinkedHashMap> setPremium(LinkedHashMap<String, LinkedHashMap> nation)
    {
        LinkedHashMap<String, LinkedHashMap> tempNation = new LinkedHashMap<>();
        LinkedHashMap<String, Warship> tempPremium = new LinkedHashMap<>();

        nation.entrySet().forEach(shipType ->
        {
            LinkedHashMap<String, Warship> tempShips = new LinkedHashMap<>();

            shipType.getValue().entrySet().forEach(ship ->
            {
                Map.Entry<String, Warship> temp = (Map.Entry<String, Warship>) ship;

                if (temp.getValue().isIs_premium() || temp.getValue().getPrice_gold() > 0 || temp.getValue().getPrice_credit() == 1 || (temp.getValue().getPrice_credit() == 0 && temp.getValue().getTier() != 1))
                {
                    tempPremium.put(temp.getKey(), temp.getValue());
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

        return tempNation;
    }

    private void setUpgradesPerShip(Warship warship, LinkedHashMap<String, Upgrade> upgrades)
    {
        LinkedHashMap<String, Upgrade> tempUpgrades = new LinkedHashMap<>();

        warship.getUpgrades().forEach(upgrade_id ->
        {
            Upgrade tempUpgrade = upgrades.get(String.valueOf(upgrade_id));
            if (tempUpgrade != null)
            {
                tempUpgrades.put(tempUpgrade.getName(), tempUpgrade);
            }
        });
        warship.setUpgradesNew(tempUpgrades);
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
}
