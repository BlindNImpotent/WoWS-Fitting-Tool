package WoWSSSC.model.gameparams.ShipComponents.AA;

import WoWSSSC.model.gameparams.ShipComponents.ATBA.Secondary;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class AirDefense
{
    private HashMap<String, Secondary> antiAirGuns = new HashMap<>();
    private HashMap<String, Aura> auras = new HashMap<>();
    private HashSet<String> auraTypes = new HashSet<>();
    private float auraCoeff;
    private float prioritySectorChangeDelay;
    private float prioritySectorDisableDelay;
    private float prioritySectorEnableDelay;
    private float prioritySectorStrength;
    private List<List<Float>> sectors;

    @JsonIgnore
    private ObjectMapper mapper = new ObjectMapper();

    @JsonAnySetter
    public void setAA(String name, Object value)
    {
        if (value instanceof LinkedHashMap && mapper.convertValue(value, LinkedHashMap.class).get("HitLocationAirDefense") != null)
        {
            Secondary secondary = mapper.convertValue(value, Secondary.class);
            antiAirGuns.put(name, secondary);
        }
        else if (value instanceof LinkedHashMap && mapper.convertValue(value, LinkedHashMap.class).get("guns") != null)
        {
            Aura tempAura = mapper.convertValue(value, Aura.class);
            tempAura.setAuraType(name);
            auras.put(name, tempAura);
            auraTypes.add(name);
        }
    }
}
