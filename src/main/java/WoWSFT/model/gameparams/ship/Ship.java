package WoWSFT.model.gameparams.ship;


import WoWSFT.config.WoWSFT;
import WoWSFT.model.gameparams.TypeInfo;
import WoWSFT.model.gameparams.ship.abilities.ShipAbilities;
import WoWSFT.model.gameparams.ship.component.airdefense.AirDefense;
import WoWSFT.model.gameparams.ship.component.artillery.Artillery;
import WoWSFT.model.gameparams.ship.upgrades.ShipUpgradeInfo;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Data
@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ship
{
    private HashMap<String, Object> components = new HashMap<>();

    private LinkedHashMap<String, Object> airArmament = new LinkedHashMap<>();
    private LinkedHashMap<String, AirDefense> airDefense = new LinkedHashMap<>();
    private LinkedHashMap<String, Artillery> artillery = new LinkedHashMap<>();
    private LinkedHashMap<String, Object> atba = new LinkedHashMap<>();
    private LinkedHashMap<String, Object> engine = new LinkedHashMap<>();
    private LinkedHashMap<String, Object> fireControl = new LinkedHashMap<>();
    private LinkedHashMap<String, Object> hull = new LinkedHashMap<>();
    private LinkedHashMap<String, Object> torpedoes = new LinkedHashMap<>();
    private LinkedHashMap<String, Object> flightControl = new LinkedHashMap<>();
    private LinkedHashMap<String, Object> diveBomber = new LinkedHashMap<>();
    private LinkedHashMap<String, Object> fighter = new LinkedHashMap<>();
    private LinkedHashMap<String, Object> torpedoBomber = new LinkedHashMap<>();

    private ShipAbilities ShipAbilities;
    private ShipUpgradeInfo ShipUpgradeInfo;

    private float apDamageLimitCoeff;
    private BattleLevels battleLevels;
    private boolean canEquipCamouflage;
    private String defaultCrew;
    private String group;
    private long id;
    private String index;
    @JsonAlias("isPaperShip")
    private boolean paperShip;
    private int level;
    private int maxEquippedFlags;
    private String name;
    private String navalFlag;
    private boolean needShowProjectYear;
    private String peculiarity;
    private List<Float> steerAngle;
    private TypeInfo typeinfo;
    private int weight;
    private String realShipType;

    @JsonAnySetter
    public void setUpComponents(String name, Object value)
    {
        components.put(name, value);
    }
}
