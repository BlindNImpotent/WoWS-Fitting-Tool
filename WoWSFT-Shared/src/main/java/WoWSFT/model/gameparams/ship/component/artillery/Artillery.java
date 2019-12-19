package WoWSFT.model.gameparams.ship.component.artillery;

import WoWSFT.config.WoWSFT;
import WoWSFT.model.gameparams.ship.component.airdefense.Aura;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.*;

@Data
@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
public class Artillery
{
    private List<Aura> auraFar = new ArrayList<>();
    private List<Aura> auraMedium = new ArrayList<>();
    private List<Aura> auraNear = new ArrayList<>();

    private List<Turret> turrets = new ArrayList<>();
    private LinkedHashMap<Integer, List<Object>> turretTypes = new LinkedHashMap<>();

    private double artificialOffset;
    private double maxDist;
    private double minDistH;
    private double minDistV;
    private boolean normalDistribution;
    private double sigmaCount;
    private double taperDist;
    @JsonInclude
    private double GMIdealRadius = 1.0;
    private double barrelDiameter;

    private LinkedHashMap<String, Shell> shells = new LinkedHashMap<>();

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
            } else if (tempObject.containsKey("HitLocationArtillery")) {
                Turret turret = mapper.convertValue(value, Turret.class);
                turrets.add(turret);
                this.barrelDiameter = turret.getBarrelDiameter();

                if (turretTypes.containsKey(turret.getNumBarrels())) {
                    turretTypes.get(turret.getNumBarrels()).set(0, (int) turretTypes.get(turret.getNumBarrels()).get(0) + 1);
                } else {
                    List<Object> tObject = new ArrayList<>();
                    tObject.add(1);
                    tObject.add(turret.getName());
                    turretTypes.put(turret.getNumBarrels(), tObject);
                }
            }
        }
    }
}
