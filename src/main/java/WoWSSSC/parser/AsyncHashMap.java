package WoWSSSC.parser;

import WoWSSSC.model.info.Encyclopedia;
import WoWSSSC.model.skills.CrewSkills;
import WoWSSSC.model.skills.CrewSkillsData;
import WoWSSSC.model.warships.Warship;
import WoWSSSC.model.warships.WarshipData;
import WoWSSSC.model.upgrade.Upgrade;
import WoWSSSC.model.upgrade.UpgradeData;
import WoWSSSC.utils.Sorter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.*;
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
    private ThreadPoolTaskExecutor executor;

    @Autowired
    private Sorter sorter;

    @Override
    public void run(String... strings) throws Exception
    {
        executor.initialize();

        List<String> nationsString = new ArrayList<>();
        List<String> shipTypeString = new ArrayList<>();

        LinkedHashMap<String, LinkedHashMap> nations = new LinkedHashMap<>();

        Encyclopedia encyclopedia = apiJsonParser.getEncyclopedia().getData();

        encyclopedia.getShip_nations().entrySet().forEach(entry -> nationsString.add(entry.getKey()));
        encyclopedia.getShip_types().entrySet().forEach(entry -> shipTypeString.add(entry.getKey()));
        Collections.sort(nationsString);
        Collections.sort(shipTypeString);

        LinkedHashMap<String, LinkedHashMap<String, Future<WarshipData>>> futures = new LinkedHashMap<>();

        for (int i = 0; i < nationsString.size(); i++)
        {
            LinkedHashMap<String, Future<WarshipData>> temp = new LinkedHashMap<>();
            for (int j = 0; j < shipTypeString.size(); j++)
            {
                temp.put(shipTypeString.get(j), apiJsonParser.getNationShip(nationsString.get(i), shipTypeString.get(j)));
            }
            futures.put(nationsString.get(i), temp);
        }

        Future<UpgradeData> upgradeData = apiJsonParser.getUpgrades();
        Future<CrewSkillsData> crewsSkillsData = apiJsonParser.getCrewSkills();

        futures.entrySet().forEach(futureEntry ->
        {
            LinkedHashMap<String, LinkedHashMap> wsdlhm = new LinkedHashMap<>();
            futureEntry.getValue().entrySet().forEach(shipType ->
            {
                WarshipData wsd = new WarshipData();

                try {
                    shipType.getValue().get().getData().entrySet().forEach(warship ->
                    {
                        String key = warship.getValue().getName();
                        Warship value = warship.getValue();
                        wsd.getData().put(key, value);
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
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

        executor.shutdown();
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
                if (temp.getValue().isIs_premium())
                {
                    tempPremium.put(temp.getKey(), temp.getValue());
                }
                else
                {
                    tempShips.put(temp.getKey(), temp.getValue());
                }
            });
            tempNation.put(shipType.getKey(), tempShips);
        });
        LinkedHashMap<String, Warship> tempSortedPremium = sorter.sortShips(tempPremium);
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
}
