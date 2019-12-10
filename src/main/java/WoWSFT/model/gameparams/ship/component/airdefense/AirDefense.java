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

    private double ownerlessTracesScatterCoefficient;
    private double prioritySectorChangeDelay;
    private double prioritySectorDisableDelay;
    private double prioritySectorEnableDelay;
    private double prioritySectorStrength;
    private List<List<Double>> sectors;
    private List<List<Object>> prioritySectorPhases;
    private double prioritySectorPreparation;
    private double prioritySectorDuration;
    private double prioritySectorDamageInitial;
    private double prioritySectorCoefficientInitial;
    private double prioritySectorCoefficientDuring;

    @JsonIgnore
    private ObjectMapper mapper = new ObjectMapper();

    public void setPrioritySectorPhases(List<List<Object>> prioritySectorPhases)
    {
        this.prioritySectorPhases = prioritySectorPhases;

        if (prioritySectorPhases != null && prioritySectorPhases.size() == 2) {
            prioritySectorPreparation = Double.parseDouble(String.valueOf(prioritySectorPhases.get(0).get(0)));
            prioritySectorDuration = Double.parseDouble(String.valueOf(prioritySectorPhases.get(1).get(0)));
            prioritySectorDamageInitial = Double.parseDouble(String.valueOf(prioritySectorPhases.get(0).get(2)));
            prioritySectorCoefficientInitial = Double.parseDouble(String.valueOf(prioritySectorPhases.get(0).get(3)));
            prioritySectorCoefficientDuring = Double.parseDouble(String.valueOf(prioritySectorPhases.get(0).get(4)));
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
