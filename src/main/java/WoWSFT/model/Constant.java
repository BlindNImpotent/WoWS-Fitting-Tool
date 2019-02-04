package WoWSFT.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Constant
{
    public static BigDecimal distCoefWG = new BigDecimal(100f / 3f);

    public static final String IDS = "IDS_";

    public static final String TYPE_SHIP = "ships";
    public static final String TYPE_SHIP_LIST = "shipsList";
    public static final String TYPE_WARSHIP = "warship";
    public static final String TYPE_UPGRADE = "upgrades";
    public static final String TYPE_CONSUMABLE = "consumables";

    public static final String flightControl = "flightControl";
    public static final String artillery = "artillery";
    public static final String hull = "hull";
    public static final String torpedoes = "torpedoes";
    public static final String suo = "suo";
    public static final String fighter = "fighter";
    public static final String torpedoBomber = "torpedoBomber";
    public static final String diveBomber = "diveBomber";
    public static final String engine = "engine";

    public static final String airArmament = "airArmament";
    public static final String airDefense = "airDefense";
    public static final String atba = "atba";
    public static final String fireControl = "fireControl";

    public static final List<String> componentsList = new ArrayList<>(Arrays.asList(flightControl, artillery, hull, torpedoes, suo, fighter, torpedoBomber, diveBomber, engine));
}
