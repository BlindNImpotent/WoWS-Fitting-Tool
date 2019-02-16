package WoWSFT.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Constant
{
    public static BigDecimal distCoefWG = new BigDecimal(100f / 3f);

    public static final String IDS = "IDS_";
    public static final String MODIFIER = "PARAMS_MODIFIER_";
    public static final String CONSUME = "DOCK_CONSUME_";
    public static final String TITLE = "TITLE_";
    public static final String DESCRIPTION = "DESCRIPTION_";
    public static final String DESC = "DESC_";

    public static final String TYPE_SHIP = "ships";
    public static final String TYPE_SHIP_LIST = "shipsList";
    public static final String TYPE_WARSHIP = "warship";
    public static final String TYPE_MODULE = "modules";
    public static final String TYPE_UPGRADE = "upgrades";
    public static final String TYPE_CONSUMABLE = "consumables";
    public static final String TYPE_COMMANDER = "commanders";
    public static final String TYPE_SKILL = "skills";
    public static final String TYPE_SHELL = "shells";

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
    public static final List<String> compStatsList = new ArrayList<>(Arrays.asList(flightControl, airArmament, airDefense, artillery, atba, hull, suo, torpedoes, fighter, torpedoBomber, diveBomber, engine));

    public static final HashSet<String> globalLanguage = new HashSet<>(Arrays.asList("en", "ko"));

    public static final HashSet<String> excludeShipGroups = new HashSet<>(Arrays.asList("unavailable", "disabled", "preserved", "clan", "demoWithoutStats"));
    public static final HashSet<String> excludeShipNations = new HashSet<>(Arrays.asList("Events", "disabled", "preserved", "clan"));
    public static final HashSet<String> excludeShipSpecies = new HashSet<>(Arrays.asList("Auxiliary", "Submarine"));
    public static final HashSet<String> excludeCompStats = new HashSet<>(Arrays.asList("directors", "finders", "radars"));
    public static final HashSet<String> excludeModernization = new HashSet<>(Arrays.asList("extra"));

    public static final HashSet<String> coeff = new HashSet<>(Arrays.asList("coef", "maxdist", "idealradius"));
    public static final HashSet<String> noUnit = new HashSet<>(Arrays.asList("num"));
    public static final HashSet<String> extra = new HashSet<>(Arrays.asList("count", "level", "additional"));
    public static final HashSet<String> meter = new HashSet<>(Arrays.asList("radius", "height", "dist"));
    public static final HashSet<String> rate = new HashSet<>(Arrays.asList("regeneration", "boostcoeff", "probabilitybonus", "chance"));
    public static final HashSet<String> rateNoSym = new HashSet<>(Arrays.asList("step"));
    public static final HashSet<String> multiple = new HashSet<>(Arrays.asList("multiplier"));
    public static final HashSet<String> extraAngle = new HashSet<>(Arrays.asList("gunbonus"));
    public static final HashSet<String> angle = new HashSet<>(Arrays.asList("angle"));
    public static final HashSet<String> time = new HashSet<>(Arrays.asList("time"));
    public static final HashSet<String> speed = new HashSet<>(Arrays.asList("speedbonus"));

    public static final long maxBitsToInt = 4294967295L;
}
