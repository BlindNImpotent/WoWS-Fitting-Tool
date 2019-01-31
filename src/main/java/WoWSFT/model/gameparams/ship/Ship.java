package WoWSFT.model.gameparams.ship;


import WoWSFT.config.WoWSFT;
import WoWSFT.model.gameparams.TypeInfo;
import WoWSFT.model.gameparams.ship.abilities.ShipAbilities;
import WoWSFT.model.gameparams.ship.upgrades.ShipUpgradeInfo;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ship
{
    private HashMap<String, Object> components = new HashMap<>();

    private HashMap<String, Object> airArmament = new HashMap<>();
    private HashMap<String, Object> airDefense = new HashMap<>();
    private HashMap<String, Object> artillery = new HashMap<>();
    private HashMap<String, Object> atba = new HashMap<>();
    private HashMap<String, Object> directors = new HashMap<>();
    private HashMap<String, Object> engine = new HashMap<>();
    private HashMap<String, Object> finders = new HashMap<>();
    private HashMap<String, Object> fireControl = new HashMap<>();
    private HashMap<String, Object> hull = new HashMap<>();
    private HashMap<String, Object> radars = new HashMap<>();
    private HashMap<String, Object> torpedoes = new HashMap<>();
    private HashMap<String, Object> flightControl = new HashMap<>();
    private HashMap<String, Object> diveBomber = new HashMap<>();
    private HashMap<String, Object> fighter = new HashMap<>();
    private HashMap<String, Object> torpedoBomber = new HashMap<>();

    private ShipAbilities ShipAbilities;
    private ShipUpgradeInfo ShipUpgradeInfo;

    private float apDamageLimitCoeff;
    private BattleLevels battleLevels;
    private boolean canEquipCamouflage;
    private float deathTimeFactor;
    private String defaultCrew;
    private String group;
    private long id;
    private String index;
    private boolean isPaperShip;
    private int level;
    private int maxEquippedFlags;
    private String name;
    private String navalFlag;
    private boolean needShowProjectYear;
    private String peculiarity;
    private List<Float> steerAngle;
    private TypeInfo typeinfo;
    private int weight;

    @JsonAnySetter
    public void setUpComponents(String name, Object value)
    {
        components.put(name, value);
    }
}
