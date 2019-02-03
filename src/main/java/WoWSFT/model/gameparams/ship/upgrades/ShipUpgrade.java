package WoWSFT.model.gameparams.ship.upgrades;

import WoWSFT.config.WoWSFT;
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
}
