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

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
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

    private LinkedHashMap<String, LinkedHashMap> France = new LinkedHashMap<>();
    private LinkedHashMap<String, LinkedHashMap> Germany = new LinkedHashMap<>();
    private LinkedHashMap<String, LinkedHashMap> Japan = new LinkedHashMap<>();
    private LinkedHashMap<String, LinkedHashMap> Pan_Asia = new LinkedHashMap<>();
    private LinkedHashMap<String, LinkedHashMap> Poland = new LinkedHashMap<>();
    private LinkedHashMap<String, LinkedHashMap> UK = new LinkedHashMap<>();
    private LinkedHashMap<String, LinkedHashMap> USA = new LinkedHashMap<>();
    private LinkedHashMap<String, LinkedHashMap> USSR = new LinkedHashMap<>();
    private LinkedHashMap<String, LinkedHashMap> nations = new LinkedHashMap<>();

    private LinkedHashMap<String, LinkedHashMap> upgrades = new LinkedHashMap<>();

    private LinkedHashMap<String, LinkedHashMap> data = new LinkedHashMap<>();

//    public AsyncHashMap(APIJsonParser apiJsonParser)
//    {
//        this.apiJsonParser = apiJsonParser;
//    }

    @Override
    public void run(String... strings) throws Exception
    {
        Future<WarshipData> franceAirCarrier = apiJsonParser.getNationShip(france, AirCarrier);
        Future<WarshipData> germanyAirCarrier = apiJsonParser.getNationShip(germany, AirCarrier);
        Future<WarshipData> japanAirCarrier = apiJsonParser.getNationShip(japan, AirCarrier);
        Future<WarshipData> pan_asiaAirCarrier = apiJsonParser.getNationShip(pan_asia, AirCarrier);
        Future<WarshipData> polandAirCarrier = apiJsonParser.getNationShip(poland, AirCarrier);
        Future<WarshipData> ukAirCarrier = apiJsonParser.getNationShip(uk, AirCarrier);
        Future<WarshipData> usaAirCarrier = apiJsonParser.getNationShip(usa, AirCarrier);
        Future<WarshipData> ussrAirCarrier = apiJsonParser.getNationShip(ussr, AirCarrier);

        Future<WarshipData> franceBattleship = apiJsonParser.getNationShip(france, Battleship);
        Future<WarshipData> germanyBattleship = apiJsonParser.getNationShip(germany, Battleship);
        Future<WarshipData> japanBattleship = apiJsonParser.getNationShip(japan, Battleship);
        Future<WarshipData> pan_asiaBattleship = apiJsonParser.getNationShip(pan_asia, Battleship);
        Future<WarshipData> polandBattleship = apiJsonParser.getNationShip(poland, Battleship);
        Future<WarshipData> ukBattleship = apiJsonParser.getNationShip(uk, Battleship);
        Future<WarshipData> usaBattleship = apiJsonParser.getNationShip(usa, Battleship);
        Future<WarshipData> ussrBattleship = apiJsonParser.getNationShip(ussr, Battleship);

        Future<WarshipData> franceCruiser = apiJsonParser.getNationShip(france, Cruiser);
        Future<WarshipData> germanyCruiser = apiJsonParser.getNationShip(germany, Cruiser);
        Future<WarshipData> japanCruiser = apiJsonParser.getNationShip(japan, Cruiser);
        Future<WarshipData> pan_asiaCruiser = apiJsonParser.getNationShip(pan_asia, Cruiser);
        Future<WarshipData> polandCruiser = apiJsonParser.getNationShip(poland, Cruiser);
        Future<WarshipData> ukCruiser = apiJsonParser.getNationShip(uk, Cruiser);
        Future<WarshipData> usaCruiser = apiJsonParser.getNationShip(usa, Cruiser);
        Future<WarshipData> ussrCruiser = apiJsonParser.getNationShip(ussr, Cruiser);

        Future<WarshipData> franceDestroyer = apiJsonParser.getNationShip(france, Destroyer);
        Future<WarshipData> germanyDestroyer = apiJsonParser.getNationShip(germany, Destroyer);
        Future<WarshipData> japanDestroyer = apiJsonParser.getNationShip(japan, Destroyer);
        Future<WarshipData> pan_asiaDestroyer = apiJsonParser.getNationShip(pan_asia, Destroyer);
        Future<WarshipData> polandDestroyer = apiJsonParser.getNationShip(poland, Destroyer);
        Future<WarshipData> ukDestroyer = apiJsonParser.getNationShip(uk, Destroyer);
        Future<WarshipData> usaDestroyer = apiJsonParser.getNationShip(usa, Destroyer);
        Future<WarshipData> ussrDestroyer = apiJsonParser.getNationShip(ussr, Destroyer);

        Future<UpgradeData> upgradeData = apiJsonParser.getUpgrades();

        France.put(AirCarrier, franceAirCarrier.get().getData());
        France.put(Battleship, franceBattleship.get().getData());
        France.put(Cruiser, franceCruiser.get().getData());
        France.put(Destroyer, franceDestroyer.get().getData());

        Germany.put(AirCarrier, germanyAirCarrier.get().getData());
        Germany.put(Battleship, germanyBattleship.get().getData());
        Germany.put(Cruiser, germanyCruiser.get().getData());
        Germany.put(Destroyer, germanyDestroyer.get().getData());

        Japan.put(AirCarrier, japanAirCarrier.get().getData());
        Japan.put(Battleship, japanBattleship.get().getData());
        Japan.put(Cruiser, japanCruiser.get().getData());
        Japan.put(Destroyer, japanDestroyer.get().getData());

        Pan_Asia.put(AirCarrier, pan_asiaAirCarrier.get().getData());
        Pan_Asia.put(Battleship, pan_asiaBattleship.get().getData());
        Pan_Asia.put(Cruiser, pan_asiaCruiser.get().getData());
        Pan_Asia.put(Destroyer, pan_asiaDestroyer.get().getData());

        Poland.put(AirCarrier, polandAirCarrier.get().getData());
        Poland.put(Battleship, polandBattleship.get().getData());
        Poland.put(Cruiser, polandCruiser.get().getData());
        Poland.put(Destroyer, polandDestroyer.get().getData());

        UK.put(AirCarrier, ukAirCarrier.get().getData());
        UK.put(Battleship, ukBattleship.get().getData());
        UK.put(Cruiser, ukCruiser.get().getData());
        UK.put(Destroyer, ukDestroyer.get().getData());

        USA.put(AirCarrier, usaAirCarrier.get().getData());
        USA.put(Battleship, usaBattleship.get().getData());
        USA.put(Cruiser, usaCruiser.get().getData());
        USA.put(Destroyer, usaDestroyer.get().getData());

        USSR.put(AirCarrier, ussrAirCarrier.get().getData());
        USSR.put(Battleship, ussrBattleship.get().getData());
        USSR.put(Cruiser, ussrCruiser.get().getData());
        USSR.put(Destroyer, ussrDestroyer.get().getData());

        France = setPremium(France);
        Germany = setPremium(Germany);
        Japan = setPremium(Japan);
        Pan_Asia = setPremium(Pan_Asia);
        Poland = setPremium(Poland);
        UK = setPremium(UK);
        USA = setPremium(USA);
        USSR = setPremium(USSR);

        nations.put(france, France);
        nations.put(germany, Germany);
        nations.put(japan, Japan);
        nations.put(pan_asia, Pan_Asia);
        nations.put(poland, Poland);
        nations.put(uk, UK);
        nations.put(usa, USA);
        nations.put(ussr, USSR);

        setUpgradesPerShip(nations, upgradeData.get().getData());
//        upgrades = setUpgrades(upgradeData.get().getData());

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
