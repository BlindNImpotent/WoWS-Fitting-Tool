package WoWSSSC.parser;

import WoWSSSC.model.info.Encyclopedia;
import WoWSSSC.model.warships.Warship;
import WoWSSSC.model.warships.WarshipData;
import WoWSSSC.model.upgrade.Upgrade;
import WoWSSSC.model.upgrade.UpgradeData;
import WoWSSSC.utils.Sorter;
import lombok.Data;
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
@Data
@Component
public class AsyncHashMap implements CommandLineRunner
{
    @Autowired
    private APIJsonParser apiJsonParser;

    @Autowired
    private LinkedHashMap<String, LinkedHashMap> data;

    @Autowired
    private ThreadPoolTaskExecutor executor;

    private Sorter sorter = new Sorter();

    private List<String> nationsString = new ArrayList<>();
    private List<String> shipTypeString = new ArrayList<>();

    private LinkedHashMap<String, LinkedHashMap> nations = new LinkedHashMap<>();

    private LinkedHashMap<String, LinkedHashMap> upgrades = new LinkedHashMap<>();

    @Override
    public void run(String... strings) throws Exception
    {
        executor.initialize();

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

        data.put("nations", nations);
        data.put("upgrades", upgradeData.get().getData());

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
}
