package WoWSFT.model.gameparams.ship.component.airdefense;

import WoWSFT.config.WoWSFT;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.List;

@Data
@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
public class AirDefense
{
    private Aura auraFar;
    private Aura auraMedium;
    private Aura auraNear;

    private float ownerlessTracesScatterCoefficient;
    private float prioritySectorChangeDelay;
    private float prioritySectorDisableDelay;
    private float prioritySectorEnableDelay;
    private float prioritySectorStrength;
    private List<List<Float>> sectors;

    @JsonIgnore
    private ObjectMapper mapper = new ObjectMapper();

    @JsonAnySetter
    public void setAura(String name, Object value)
    {
        if (name.contains("Far")) {
            auraFar = mapper.convertValue(value, Aura.class);
        }
        else if (name.contains("Medium")) {
            auraMedium = mapper.convertValue(value, Aura.class);
        }
        else if (name.contains("Near")) {
            auraNear = mapper.convertValue(value, Aura.class);
        }
    }
}
