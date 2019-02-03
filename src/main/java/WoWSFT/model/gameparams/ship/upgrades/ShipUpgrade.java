package WoWSFT.model.gameparams.ship.upgrades;

import WoWSFT.config.WoWSFT;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static WoWSFT.model.Constant.*;

@Data
@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShipUpgrade
{
    private List<String> disabledAbilities;
    private List<String> nextShips = new ArrayList<>();
    private String fullName;
    private String name;
    private String prev;
    private String ucType;
    private String ucTypeShort;
    private int position;

    private LinkedHashMap<String, List<String>> components = new LinkedHashMap<>();
    private String image;

    @JsonIgnore
    private ObjectMapper mapper = new ObjectMapper();

    @JsonSetter
    public void setUcType(String ucType)
    {
        this.ucType = ucType;

        if (StringUtils.isNotEmpty(ucType)) {
            ucTypeShort = StringUtils.uncapitalize(ucType.replace("_", ""));
        } else {
            ucTypeShort = ucType;
        }
    }

    @JsonSetter
    public void setComponents(String name, Object value)
    {
        components.putIfAbsent(name, new ArrayList<>());
        components.get(name).add(mapper.convertValue(value, new TypeReference<List<String>>(){}));
    }

    @JsonGetter
    public String getImage()
    {
        if (StringUtils.isEmpty(image)) {
            if (StringUtils.isNotEmpty(ucTypeShort)) {
                String type;
                if (ucTypeShort.equalsIgnoreCase(artillery)) {
                    type = "maingun";
                } else if (ucTypeShort.equalsIgnoreCase(suo)) {
                    type = "radar";
                } else if (ucTypeShort.equalsIgnoreCase(fighter)) {
                    type = "avia_fighter";
                } else if (ucTypeShort.equalsIgnoreCase(torpedoBomber)) {
                    type = "avia_torpedo";
                } else if (ucTypeShort.equalsIgnoreCase(diveBomber)) {
                    type = "avia_bomber";
                } else {
                    type = ucTypeShort.toLowerCase();
                }
                return "https://glossary-na-static.gcdn.co/icons/wows/current/module/icon_module_" + type + ".png";
            }
            return "";
        }
        return image;
    }
}
