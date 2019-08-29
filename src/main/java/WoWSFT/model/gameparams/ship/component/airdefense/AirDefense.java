package WoWSFT.model.gameparams.ship.component.airdefense;

import WoWSFT.config.WoWSFT;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
public class AirDefense
{
    private List<Aura> auraFar = new ArrayList<>();
    private List<Aura> auraMedium = new ArrayList<>();
    private List<Aura> auraNear = new ArrayList<>();

    private float ownerlessTracesScatterCoefficient;
    private float prioritySectorChangeDelay;
    private float prioritySectorDisableDelay;
    private float prioritySectorEnableDelay;
    private float prioritySectorStrength;
    private List<List<Float>> sectors;
    private List<List<Object>> prioritySectorPhases;
    private float prioritySectorPreparation;
    private float prioritySectorDuration;
    private float prioritySectorDamageInitial;
    private float prioritySectorCoefficientInitial;
    private float prioritySectorCoefficientDuring;

    @JsonIgnore
    private ObjectMapper mapper = new ObjectMapper();

    public void setPrioritySectorPhases(List<List<Object>> prioritySectorPhases)
    {
        this.prioritySectorPhases = prioritySectorPhases;

        if (prioritySectorPhases != null && prioritySectorPhases.size() == 2) {
            prioritySectorPreparation = Float.parseFloat(String.valueOf(prioritySectorPhases.get(0).get(0)));
            prioritySectorDuration = Float.parseFloat(String.valueOf(prioritySectorPhases.get(1).get(0)));
            prioritySectorDamageInitial = Float.parseFloat(String.valueOf(prioritySectorPhases.get(0).get(2)));
            prioritySectorCoefficientInitial = Float.parseFloat(String.valueOf(prioritySectorPhases.get(0).get(3)));
            prioritySectorCoefficientDuring = Float.parseFloat(String.valueOf(prioritySectorPhases.get(0).get(4)));
        }
    }

    @JsonAnySetter
    public void setAura(String name, Object value)
    {
        if (value instanceof HashMap) {
            HashMap<String, Object> tempObject = mapper.convertValue(value, new TypeReference<HashMap<String, Object>>(){});

            if ("far".equalsIgnoreCase((String) tempObject.get("type"))) {
                auraFar.add(mapper.convertValue(value, Aura.class));
            } else if ("medium".equalsIgnoreCase((String) tempObject.get("type"))) {
                auraMedium.add(mapper.convertValue(value, Aura.class));
            } else if ("near".equalsIgnoreCase((String) tempObject.get("type"))) {
                auraNear.add(mapper.convertValue(value, Aura.class));
            }
        }
    }
}
