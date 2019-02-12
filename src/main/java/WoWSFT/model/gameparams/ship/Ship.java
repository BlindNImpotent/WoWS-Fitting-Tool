package WoWSFT.model.gameparams.ship;

import WoWSFT.config.WoWSFT;
import WoWSFT.model.gameparams.TypeInfo;
import WoWSFT.model.gameparams.consumable.Consumable;
import WoWSFT.model.gameparams.modernization.Modernization;
import WoWSFT.model.gameparams.ship.abilities.AbilitySlot;
import WoWSFT.model.gameparams.ship.component.ShipComponent;
import WoWSFT.model.gameparams.ship.upgrades.ShipUpgradeInfo;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Data
@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ship
{
    private LinkedHashMap<String, Object> tempComponents = new LinkedHashMap<>();
    private ShipComponent components = new ShipComponent();

    private LinkedHashMap<String, AbilitySlot> ShipAbilities;
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
    private String realShipTypeId;
    private String fullName;
    private boolean research;
    private String prevShipIndex;
    private String typeImage;
    private String imageMedium;
    private String imageSmall;

    private List<List<Consumable>> consumables;
    private List<List<Modernization>> upgrades;
    private HashMap<String, String> modules = new HashMap<>();
    private HashMap<String, Integer> positions = new HashMap<>();

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

    @JsonGetter
    public String getTypeImage()
    {
        if (StringUtils.isEmpty(typeImage)) {
            if (typeinfo != null && StringUtils.isNotEmpty(typeinfo.getSpecies()) && StringUtils.isNotEmpty(realShipType)) {
                return "https://glossary-na-static.gcdn.co/icons/wows/current/vehicle/types/" + typeinfo.getSpecies() + (realShipType.equalsIgnoreCase("Premium") ? "/premium" : "/normal") + ".png";
            }
            return "";
        }
        return typeImage;
    }

    @JsonGetter
    public String getImageMedium()
    {
        if (StringUtils.isEmpty(imageMedium)) {
            if (StringUtils.isNotEmpty(index)) {
                return "https://glossary-na-static.gcdn.co/icons/wows/current/vehicle/medium/" + index + ".png";
            }
            return "";
        }
        return imageMedium;
    }

    @JsonGetter
    public String getImageSmall()
    {
        if (StringUtils.isEmpty(imageSmall)) {
            if (StringUtils.isNotEmpty(index)) {
                return "https://glossary-na-static.gcdn.co/icons/wows/current/vehicle/small/" + index + ".png";
            }
            return "";
        }
        return imageSmall;
    }
}
