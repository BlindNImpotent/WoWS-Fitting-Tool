package WoWSFT.model.gameparams.ship.component.airdefense;

import WoWSFT.config.WoWSFT;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.ArrayList;
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

    @JsonIgnore
    private ObjectMapper mapper = new ObjectMapper();

    @JsonAnySetter
    public void setAura(String name, Object value)
    {
        if (name.contains("Far")) {
            auraFar.add(mapper.convertValue(value, Aura.class));
        } else if (name.contains("Medium")) {
            auraMedium.add(mapper.convertValue(value, Aura.class));
        } else if (name.contains("Near")) {
            auraNear.add(mapper.convertValue(value, Aura.class));
        }
    }
}
