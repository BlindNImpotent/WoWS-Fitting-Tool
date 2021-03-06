package WoWSFT.model.gameparams.ship.upgrades;

import WoWSFT.config.WoWSFT;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
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
    private String prev = "";
    private String ucType;
    private int position;
    @JsonInclude
    private int elem;

    private String prevType;
    @JsonInclude
    private int prevPosition;
    private int prevElem;

    private LinkedHashMap<String, List<String>> components = new LinkedHashMap<>();

    @JsonIgnore
    private ObjectMapper mapper = new ObjectMapper();

    public String getUcTypeShort()
    {
        if (StringUtils.isNotEmpty(ucType)) {
            return StringUtils.uncapitalize(ucType.replace("_", ""));
        }
        return ucType;
    }

    @JsonSetter
    public void setComponents(Object value)
    {
        LinkedHashMap<String, List<String>> temp = mapper.convertValue(value, new TypeReference<LinkedHashMap<String, List<String>>>(){});

        temp.forEach((key, list) -> {
            String name = key;
            if (key.equalsIgnoreCase(fireControl)) {
                name = suo;
            }
            Collections.sort(list);
            components.put(name, list);
        });
    }

    public String getPrevType()
    {
        if (StringUtils.isEmpty(prevType)) {
            return getUcTypeShort();
        }
        return prevType;
    }

    public String getImage()
    {
        String tempUcTypeShort = getUcTypeShort();
        if (StringUtils.isNotEmpty(tempUcTypeShort)) {
            return "https://cdn.wowsft.com/images/modules/" + tempUcTypeShort.toLowerCase() + ".png";
        }
        return "";
    }
}
