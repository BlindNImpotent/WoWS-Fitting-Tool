package WoWSFT.model.gameparams.ship;

import WoWSFT.config.WoWSFT;
import WoWSFT.model.gameparams.TypeInfo;
import WoWSFT.model.gameparams.commander.Commander;
import WoWSFT.model.gameparams.consumable.Consumable;
import WoWSFT.model.gameparams.modernization.Modernization;
import WoWSFT.model.gameparams.ship.abilities.AbilitySlot;
import WoWSFT.model.gameparams.ship.component.ShipComponent;
import WoWSFT.model.gameparams.ship.component.artillery.Turret;
import WoWSFT.model.gameparams.ship.component.torpedo.Launcher;
import WoWSFT.model.gameparams.ship.upgrades.ShipUpgradeInfo;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Data
@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ship
{
    private LinkedHashMap<String, Object> tempComponents = new LinkedHashMap<>();
    private ShipComponent components = new ShipComponent();

    @JsonAlias("ShipAbilities")
    private LinkedHashMap<String, AbilitySlot> shipAbilities;
    @JsonAlias("ShipUpgradeInfo")
    private ShipUpgradeInfo shipUpgradeInfo;

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
    private String realShipTypeId;
    private String fullName;
    private boolean research;
    private String prevShipIndex;
    private String typeImage;
    private String imageMedium;
    private String imageSmall;

    private List<List<Consumable>> consumables = new ArrayList<>();
    private List<List<Modernization>> upgrades = new ArrayList<>();
    private int upgradesRow;
    private List<Integer> sUpgrades = new ArrayList<>();
    private List<Integer> sSkills = new ArrayList<>();
    private int sSkillPts;
    private LinkedHashMap<String, String> modules = new LinkedHashMap<>();
    private LinkedHashMap<String, Integer> positions = new LinkedHashMap<>();

    private Commander commander;

    private List<Turret> turrets;
    private List<Launcher> launchers;

    @JsonSetter
    public void setRealShipType(String realShipType)
    {
        if (StringUtils.isNotEmpty(realShipType)) {
            this.realShipType = realShipType;
            this.realShipTypeId = realShipType.toUpperCase();
            if ("Premium".equalsIgnoreCase(realShipType)) {
                this.realShipTypeId = "FILTER_PREMIUM";
            }
        }
    }

    @JsonAnySetter
    public void setUpTempComponents(String name, Object value)
    {
        tempComponents.put(name, value);
    }

    public String getTypeImage()
    {
        if (typeinfo != null && StringUtils.isNotEmpty(typeinfo.getSpecies()) && StringUtils.isNotEmpty(realShipType)) {
            return "https://glossary-na-static.gcdn.co/icons/wows/current/vehicle/types/" + typeinfo.getSpecies() + (realShipType.equalsIgnoreCase("Premium") ? "/premium" : "/normal") + ".png";
        }
        return "";
    }

    public String getImageMedium()
    {
        if (StringUtils.isNotEmpty(index)) {
            return "https://glossary-na-static.gcdn.co/icons/wows/current/vehicle/medium/" + index + ".png";
        }
        return "";
    }

    public String getImageSmall()
    {
        if (StringUtils.isNotEmpty(index)) {
            return "https://glossary-na-static.gcdn.co/icons/wows/current/vehicle/small/" + index + ".png";
        }
        return "";
    }
}
