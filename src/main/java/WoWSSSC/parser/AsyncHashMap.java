package WoWSSSC.parser;

import WoWSSSC.model.exterior.Exterior;
import WoWSSSC.model.exterior.ExteriorData;
import WoWSSSC.model.info.Encyclopedia;
import WoWSSSC.model.shipprofile.Ship;
import WoWSSSC.model.skills.CrewSkills;
import WoWSSSC.model.skills.CrewSkillsData;
import WoWSSSC.model.warships.Warship;
import WoWSSSC.model.warships.WarshipData;
import WoWSSSC.model.upgrade.Upgrade;
import WoWSSSC.model.upgrade.UpgradeData;
import WoWSSSC.model.warships.WarshipModulesTree;
import WoWSSSC.utils.Sorter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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

        apiJsonParser.setGameParams();

        Encyclopedia encyclopedia = apiJsonParser.getEncyclopedia().getData();

        encyclopedia.getShip_nations().entrySet().forEach(entry -> nationsString.add(entry.getKey()));
        encyclopedia.getShip_types().entrySet().forEach(entry -> shipTypeString.add(entry.getKey()));
        Collections.sort(nationsString);
        Collections.sort(shipTypeString);

        LinkedHashMap<String, LinkedHashMap<String, Future<WarshipData>>> futures = new LinkedHashMap<>();

        HashMap<String, Warship> tempWarships = new HashMap<>();

        for (int i = 0; i < nationsString.size(); i++)
        {
            LinkedHashMap<String, Future<WarshipData>> temp = new LinkedHashMap<>();
            for (int j = 0; j < shipTypeString.size(); j++)
            {
                Future<WarshipData> nationShip = apiJsonParser.getNationShip(nationsString.get(i), shipTypeString.get(j));
                temp.put(shipTypeString.get(j), nationShip);

//                nationShip.get().getData().values().forEach(warship -> tempWarships.put(String.valueOf(warship.getShip_id()), new Warship(warship.getNation(), warship.getType(), warship.getName(), warship.getImages())));
            }
            futures.put(nationsString.get(i), temp);
        }

        Future<UpgradeData> upgradeData = apiJsonParser.getUpgrades();
        Future<CrewSkillsData> crewsSkillsData = apiJsonParser.getCrewSkills();
        Future<ExteriorData> exteriorData = apiJsonParser.getExteriorData();

        futures.entrySet().forEach(futureEntry ->
        {
            LinkedHashMap<String, LinkedHashMap> wsdlhm = new LinkedHashMap<>();

            futureEntry.getValue().entrySet().forEach(shipType ->
            {
                while (!shipType.getValue().isDone())
                {

                }

                WarshipData wsd = new WarshipData();
                try
                {
                    // To do
                    shipType.getValue().get().getData().entrySet().forEach(warship ->
                    {
                        String key = null;
                        Warship value = null;

                        try
                        {
                            key = warship.getValue().get().getName();
                            value = warship.getValue().get();
                        }
                        catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                        catch (ExecutionException e)
                        {
                            e.printStackTrace();
                        }

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
                                                Warship nextWarshipTemp = tempWarships.get(String.valueOf(tempWSMT.getNext_ships().get(j)));

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
                        }
                        wsd.getData().put(key, new AsyncResult<>(value));
                    });
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                catch (ExecutionException e)
                {
                    e.printStackTrace();
                }

                while (wsd.getData().values().stream().filter(futureWS -> futureWS.isDone()).count() < wsd.getData().values().size())
                {

                }

                wsd.setData(sorter.sortShips(wsd.getData()));
                wsdlhm.put(shipType.getKey(), wsd.getData());
            });
            nations.put(futureEntry.getKey(), setPremium(wsdlhm));
        });

        setUpgradesPerShip(nations, upgradeData.get().getData());

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
        LinkedHashMap<String, Future<Warship>> tempPremium = new LinkedHashMap<>();

        nation.entrySet().forEach(shipType ->
        {
            LinkedHashMap<String, Future<Warship>> tempShips = new LinkedHashMap<>();

            shipType.getValue().entrySet().forEach(ship ->
            {
                Map.Entry<String, Future<Warship>> temp = (Map.Entry<String, Future<Warship>>) ship;
                try {
                    if (temp.getValue().get().isIs_premium())
                    {
                        tempPremium.put(temp.getKey(), temp.getValue());
                    }
                    else
                    {
                        tempShips.put(temp.getKey(), temp.getValue());
                    }
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                catch (ExecutionException e)
                {
                    e.printStackTrace();
                }
            });
            tempNation.put(shipType.getKey(), tempShips);
        });
        LinkedHashMap<String, Future<Warship>> tempSortedPremium = sorter.sortShips(tempPremium);
        tempNation.put("Premium", tempSortedPremium);
        return tempNation;
    }

    private void setUpgradesPerShip(LinkedHashMap<String, LinkedHashMap> nations, LinkedHashMap<String, Upgrade> upgrades)
    {
        nations.entrySet().forEach(nation -> nation.getValue().entrySet().forEach(shipType -> ((Map.Entry<String, LinkedHashMap>) shipType).getValue().entrySet().forEach(ship ->
        {
            LinkedHashMap<String, Upgrade> tempUpgrades = new LinkedHashMap<>();
            ((Map.Entry<String, Warship>) ship).getValue().getUpgrades().forEach(upgrade_id ->
            {
                Upgrade tempUpgrade = upgrades.get(String.valueOf(upgrade_id));
                tempUpgrades.put(tempUpgrade.getName(), tempUpgrade);
            });
            ((Map.Entry<String, Warship>) ship).getValue().setUpgradesNew(tempUpgrades);
        })));
    }

    private LinkedHashMap<String, LinkedHashMap> setCrewSkills(LinkedHashMap<String, CrewSkills> crewSkills)
    {
        LinkedHashMap<String, LinkedHashMap> temp = new LinkedHashMap<>();

        crewSkills.entrySet().forEach(entry ->
        {
            int tier = entry.getValue().getTier();
            LinkedHashMap<String, CrewSkills> tempTier = new LinkedHashMap<>();

            for (int i = 0; i < 7; i++)
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

        return temp;
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
