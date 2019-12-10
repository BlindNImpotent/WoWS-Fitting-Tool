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

    private double maxDist;
    private double minDistH;
    private double minDistV;
    private double sigmaCount;
    private double taperDist;
    @JsonInclude
    private double GSIdealRadius = 1.0;

    @JsonIgnore
    private ObjectMapper mapper = new ObjectMapper();

    @JsonAnySetter
    public void setGuns(String name, Object value)
    {
        if (value instanceof HashMap) {
            HashMap<String, Object> tempObject = mapper.convertValue(value, new TypeReference<HashMap<String, Object>>(){});

            if ("far".equalsIgnoreCase((String) tempObject.get("type"))) {
                auraFar.add(mapper.convertValue(value, Aura.class));
            } else if ("medium".equalsIgnoreCase((String) tempObject.get("type"))) {
                auraMedium.add(mapper.convertValue(value, Aura.class));
            } else if ("near".equalsIgnoreCase((String) tempObject.get("type"))) {
                auraNear.add(mapper.convertValue(value, Aura.class));
            } else if (tempObject.containsKey("HitLocationATBA")) {
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
