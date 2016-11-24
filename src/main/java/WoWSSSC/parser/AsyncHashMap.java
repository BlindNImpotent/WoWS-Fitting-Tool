package WoWSSSC.parser;

import WoWSSSC.model.warships.Warship;
import WoWSSSC.model.warships.WarshipData;
import WoWSSSC.model.upgrade.Upgrade;
import WoWSSSC.model.upgrade.UpgradeData;
import WoWSSSC.utils.Sorter;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
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

    private Sorter sorter = new Sorter();

    private final static String france = "france";
    private final static String germany = "germany";
    private final static String japan = "japan";
    private final static String pan_asia = "pan_asia";
    private final static String poland = "poland";
    private final static String uk = "uk";
    private final static String usa = "usa";
    private final static String ussr = "ussr";

    private final static String AirCarrier = "AirCarrier";
    private final static String Battleship = "Battleship";
    private final static String Cruiser = "Cruiser";
    private final static String Destroyer = "Destroyer";
    private final static String Premium = "Premium";

    private final static String[] nationsString = { france, germany, japan, pan_asia, poland, uk, usa, ussr };
    private final static String[] shipTypeString = { AirCarrier, Battleship, Cruiser, Destroyer };

    private LinkedHashMap<String, LinkedHashMap> nations = new LinkedHashMap<>();

    private LinkedHashMap<String, LinkedHashMap> upgrades = new LinkedHashMap<>();

    @Override
    public void run(String... strings) throws Exception
    {
        LinkedHashMap<String, LinkedHashMap<String, Future<WarshipData>>> futures = new LinkedHashMap<>();

        for (int i = 0; i < nationsString.length; i++)
        {
            LinkedHashMap<String, Future<WarshipData>> temp = new LinkedHashMap<>();
            for (int j = 0; j < shipTypeString.length; j++)
            {
                temp.put(shipTypeString[j], apiJsonParser.getNationShip(nationsString[i], shipTypeString[j]));
            }
            futures.put(nationsString[i], temp);
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
        tempNation.put(Premium, tempSortedPremium);
        return tempNation;
    }

    private LinkedHashMap<String, LinkedHashMap> setUpgrades(LinkedHashMap<String, Upgrade> upgrades)
    {
        LinkedHashMap<String, LinkedHashMap> tempUpgrades = new LinkedHashMap<>();

        LinkedHashMap<String, Upgrade> temp125k = new LinkedHashMap<>();
        LinkedHashMap<String, Upgrade> temp250k = new LinkedHashMap<>();
        LinkedHashMap<String, Upgrade> temp500k = new LinkedHashMap<>();
        LinkedHashMap<String, Upgrade> temp1000k = new LinkedHashMap<>();
        LinkedHashMap<String, Upgrade> temp2000k = new LinkedHashMap<>();
        LinkedHashMap<String, Upgrade> temp3000k = new LinkedHashMap<>();

        upgrades.entrySet().forEach(tempUpgrade ->
        {
            if (tempUpgrade.getValue().getPrice_credit() == 125000)
            {
                temp125k.put(tempUpgrade.getKey(), tempUpgrade.getValue());
            }
            else if (tempUpgrade.getValue().getPrice_credit() == 250000)
            {
                temp250k.put(tempUpgrade.getKey(), tempUpgrade.getValue());
            }
            else if (tempUpgrade.getValue().getPrice_credit() == 500000)
            {
                temp500k.put(tempUpgrade.getKey(), tempUpgrade.getValue());
            }
            else if (tempUpgrade.getValue().getPrice_credit() == 1000000)
            {
                temp1000k.put(tempUpgrade.getKey(), tempUpgrade.getValue());
            }
            else if (tempUpgrade.getValue().getPrice_credit() == 2000000)
            {
                temp2000k.put(tempUpgrade.getKey(), tempUpgrade.getValue());
            }
            else if (tempUpgrade.getValue().getPrice_credit() == 3000000)
            {
                temp3000k.put(tempUpgrade.getKey(), tempUpgrade.getValue());
            }
            else
            {
                System.out.println(tempUpgrade.getValue().getPrice_credit());
            }
        });

        LinkedHashMap<String, Upgrade> sortedTemp125k = sorter.sortUpgrades(temp125k);
        LinkedHashMap<String, Upgrade> sortedTemp250k = sorter.sortUpgrades(temp250k);
        LinkedHashMap<String, Upgrade> sortedTemp500k = sorter.sortUpgrades(temp500k);
        LinkedHashMap<String, Upgrade> sortedTemp1000k = sorter.sortUpgrades(temp1000k);
        LinkedHashMap<String, Upgrade> sortedTemp2000k = sorter.sortUpgrades(temp2000k);
        LinkedHashMap<String, Upgrade> sortedTemp3000k = sorter.sortUpgrades(temp3000k);
        LinkedHashMap<String, Upgrade> sortedUpgrades = sorter.sortUpgrades(upgrades);

        tempUpgrades.put("125000", sortedTemp125k);
        tempUpgrades.put("250000", sortedTemp250k);
        tempUpgrades.put("500000", sortedTemp500k);
        tempUpgrades.put("1000000", sortedTemp1000k);
        tempUpgrades.put("2000000", sortedTemp2000k);
        tempUpgrades.put("3000000", sortedTemp3000k);
        tempUpgrades.put("all", sortedUpgrades);

        return tempUpgrades;
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
