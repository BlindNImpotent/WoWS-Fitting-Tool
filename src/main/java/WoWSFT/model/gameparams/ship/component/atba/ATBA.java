package WoWSFT.model.gameparams.ship.component.atba;

import WoWSFT.config.WoWSFT;
import WoWSFT.model.gameparams.ship.component.airdefense.Aura;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Data
@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
public class ATBA
{
    private List<Aura> auraFar = new ArrayList<>();
    private List<Aura> auraMedium = new ArrayList<>();
    private List<Aura> auraNear = new ArrayList<>();

    private List<Secondary> turrets = new ArrayList<>();
    private LinkedHashMap<String, Secondary> secondaries = new LinkedHashMap<>();

    private float maxDist;
    private float minDistH;
    private float minDistV;
    private float sigmaCount;
    private float taperDist;
    @JsonInclude
    private float GSIdealRadius = 1f;

    @JsonIgnore
    private ObjectMapper mapper = new ObjectMapper();

    @JsonAnySetter
    public void setGuns(String name, Object value)
    {
        if (name.contains("Far")) {
            auraFar.add(mapper.convertValue(value, Aura.class));
        } else if (name.contains("Med")) {
            auraMedium.add(mapper.convertValue(value, Aura.class));
        } else if (name.contains("Near")) {
            auraNear.add(mapper.convertValue(value, Aura.class));
        } else if (value instanceof HashMap) {
            HashMap<String, Object> tempObject = mapper.convertValue(value, new TypeReference<HashMap<String, Object>>(){});

            if (tempObject.containsKey("HitLocationATBA")) {
                Secondary tempS = mapper.convertValue(value, Secondary.class);

                if (!secondaries.containsKey(tempS.getName())) {
                    tempS.setCount(1);
                    secondaries.put(tempS.getName(), tempS);
                } else {
                    secondaries.get(tempS.getName()).setCount(secondaries.get(tempS.getName()).getCount() + 1);
                }
            }
        }
    }
}
