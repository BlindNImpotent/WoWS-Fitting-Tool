package WoWSFT.model.gameparams.ship.component.artillery;

import WoWSFT.config.WoWSFT;
import WoWSFT.model.gameparams.ship.component.airdefense.Aura;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class Artillery
{
    private Aura auraFar;
    private Aura auraMedium;
    private Aura auraNear;
    private List<Turret> turrets = new ArrayList<>();
    private LinkedHashMap<String, Integer> turretTypes = new LinkedHashMap<>();

    private float artificialOffset;
    private float maxDist;
    private float minDistH;
    private float minDistV;
    private boolean normalDistribution;
    private float sigmaCount;
    private float taperDist;

    private LinkedHashMap<String, Shell> shells = new LinkedHashMap<>();

    @JsonIgnore
    private ObjectMapper mapper = new ObjectMapper();

    @JsonAnySetter
    public void setGuns(String name, Object value)
    {
        if (name.contains("Far")) {
            auraFar = mapper.convertValue(value, Aura.class);
        } else if (name.contains("Medium")) {
            auraMedium = mapper.convertValue(value, Aura.class);
        } else if (name.contains("Near")) {
            auraNear = mapper.convertValue(value, Aura.class);
        } else if (value instanceof HashMap) {
            HashMap<String, Object> tempObject = mapper.convertValue(value, new TypeReference<HashMap<String, Object>>(){});

            if (tempObject.containsKey("HitLocationArtillery")) {
                Turret turret = mapper.convertValue(value, Turret.class);
                turrets.add(turret);

                if (turretTypes.containsKey(turret.getName())) {
                    turretTypes.put(turret.getName(), turretTypes.get(turret.getName()) + 1);
                } else {
                    turretTypes.put(turret.getName(), 1);
                }
            }
        }
    }
}
