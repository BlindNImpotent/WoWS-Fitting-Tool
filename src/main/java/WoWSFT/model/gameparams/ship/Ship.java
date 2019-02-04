package WoWSFT.model.gameparams.ship;

import WoWSFT.config.WoWSFT;
import WoWSFT.model.gameparams.TypeInfo;
import WoWSFT.model.gameparams.ship.abilities.AbilitySlot;
import WoWSFT.model.gameparams.ship.upgrades.ShipUpgradeInfo;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;

@Data
@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ship
{
    private LinkedHashMap<String, Object> components = new LinkedHashMap<>();
    private LinkedHashMap<String, LinkedHashMap<String, Object>> compStats = new LinkedHashMap<>();

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
    public void setUpComponents(String name, Object value)
    {
        components.put(name, value);
    }

    @JsonGetter
    public String getTypeImage()
    {
        if (StringUtils.isEmpty(typeImage)) {
            if (typeinfo != null && StringUtils.isNotEmpty(typeinfo.getSpecies()) && StringUtils.isNotEmpty(realShipType)) {
                return "https://glossary-na-static.gcdn.co/icons/wows/current/vehicle/types/" + typeinfo.getSpecies() + (realShipType.equalsIgnoreCase("Premium") ? "/normal" : "/premium") + ".png";
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
}
