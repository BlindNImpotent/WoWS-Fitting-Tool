package WoWSSSC.parser;

import WoWSSSC.model.ship.Ship;
import WoWSSSC.model.ship.ShipData;
import WoWSSSC.model.upgrade.Upgrade;
import WoWSSSC.model.upgrade.UpgradeData;
import WoWSSSC.utils.Sorter;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
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
    private final APIJsonParser apiJsonParser;

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

    private Sorter sorter = new Sorter();

    public AsyncHashMap(APIJsonParser apiJsonParser)
    {
        this.apiJsonParser = apiJsonParser;
    }

    @Override
    public void run(String... strings) throws Exception
    {
        Future<ShipData> franceAirCarrier = apiJsonParser.getNationShip(france, AirCarrier);
        Future<ShipData> germanyAirCarrier = apiJsonParser.getNationShip(germany, AirCarrier);
        Future<ShipData> japanAirCarrier = apiJsonParser.getNationShip(japan, AirCarrier);
        Future<ShipData> pan_asiaAirCarrier = apiJsonParser.getNationShip(pan_asia, AirCarrier);
        Future<ShipData> polandAirCarrier = apiJsonParser.getNationShip(poland, AirCarrier);
        Future<ShipData> ukAirCarrier = apiJsonParser.getNationShip(uk, AirCarrier);
        Future<ShipData> usaAirCarrier = apiJsonParser.getNationShip(usa, AirCarrier);
        Future<ShipData> ussrAirCarrier = apiJsonParser.getNationShip(ussr, AirCarrier);

        Future<ShipData> franceBattleship = apiJsonParser.getNationShip(france, Battleship);
        Future<ShipData> germanyBattleship = apiJsonParser.getNationShip(germany, Battleship);
        Future<ShipData> japanBattleship = apiJsonParser.getNationShip(japan, Battleship);
        Future<ShipData> pan_asiaBattleship = apiJsonParser.getNationShip(pan_asia, Battleship);
        Future<ShipData> polandBattleship = apiJsonParser.getNationShip(poland, Battleship);
        Future<ShipData> ukBattleship = apiJsonParser.getNationShip(uk, Battleship);
        Future<ShipData> usaBattleship = apiJsonParser.getNationShip(usa, Battleship);
        Future<ShipData> ussrBattleship = apiJsonParser.getNationShip(ussr, Battleship);

        Future<ShipData> franceCruiser = apiJsonParser.getNationShip(france, Cruiser);
        Future<ShipData> germanyCruiser = apiJsonParser.getNationShip(germany, Cruiser);
        Future<ShipData> japanCruiser = apiJsonParser.getNationShip(japan, Cruiser);
        Future<ShipData> pan_asiaCruiser = apiJsonParser.getNationShip(pan_asia, Cruiser);
        Future<ShipData> polandCruiser = apiJsonParser.getNationShip(poland, Cruiser);
        Future<ShipData> ukCruiser = apiJsonParser.getNationShip(uk, Cruiser);
        Future<ShipData> usaCruiser = apiJsonParser.getNationShip(usa, Cruiser);
        Future<ShipData> ussrCruiser = apiJsonParser.getNationShip(ussr, Cruiser);

        Future<ShipData> franceDestroyer = apiJsonParser.getNationShip(france, Destroyer);
        Future<ShipData> germanyDestroyer = apiJsonParser.getNationShip(germany, Destroyer);
        Future<ShipData> japanDestroyer = apiJsonParser.getNationShip(japan, Destroyer);
        Future<ShipData> pan_asiaDestroyer = apiJsonParser.getNationShip(pan_asia, Destroyer);
        Future<ShipData> polandDestroyer = apiJsonParser.getNationShip(poland, Destroyer);
        Future<ShipData> ukDestroyer = apiJsonParser.getNationShip(uk, Destroyer);
        Future<ShipData> usaDestroyer = apiJsonParser.getNationShip(usa, Destroyer);
        Future<ShipData> ussrDestroyer = apiJsonParser.getNationShip(ussr, Destroyer);

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

        upgrades = setUpgrades(upgradeData.get().getData());

        data.put("nations", nations);
        data.put("upgrades", upgrades);
    }

    private LinkedHashMap<String, LinkedHashMap> setPremium(LinkedHashMap<String, LinkedHashMap> nation)
    {
        LinkedHashMap<String, LinkedHashMap> tempNation = new LinkedHashMap<>();
        LinkedHashMap<String, Ship> tempPremium = new LinkedHashMap<>();

        nation.entrySet().forEach(shipType ->
        {
            LinkedHashMap<String, Ship> tempShips = new LinkedHashMap<>();

            shipType.getValue().entrySet().forEach(ship ->
            {
                Map.Entry<String, Ship> temp = (Map.Entry<String, Ship>) ship;
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
        LinkedHashMap<String, Ship> tempSortedPremium = sorter.sortShips(tempPremium);
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
}
