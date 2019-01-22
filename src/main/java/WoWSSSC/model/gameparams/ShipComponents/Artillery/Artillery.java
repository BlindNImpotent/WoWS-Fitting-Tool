package WoWSSSC.model.gameparams.ShipComponents.Artillery;

import WoWSSSC.model.gameparams.ShipComponents.AA.Aura;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Aesis on 2017-05-03.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Artillery
{
    private LinkedHashMap<String, Turret> turrets = new LinkedHashMap<>();
    private LinkedHashMap<String, Integer> turretNames = new LinkedHashMap<>();
    private List<Turret> turretsList = new ArrayList<>();
    private int numBarrel;

//    private LinkedHashMap<String, LinkedHashMap> AuraFar;
//    private LinkedHashMap<String, LinkedHashMap> AuraMedium;
//    private LinkedHashMap<String, LinkedHashMap> AuraNear;
    private int aimLevel;
    private LinkedHashMap<String, Integer> ammoPool;
    private int artificialOffset;
    private float maxDist;
    private float minDistH;
    private float minDistV;
    private boolean normalDistribution;
    private float sigmaCount;
    private float taperDist;
    private List<String> targetTypeIgnore;
    private float targetWeightAllyAttacker;
    private float targetWeightDist;
    private float targetWeightHealth;
    private float targetWeightIncoming;
    private float targetWeightMaxDist;
    private float targetWeightMaxHealth;
    private float targetWeightMinDist;
    private float targetWeightMinHealth;
    private float targetWeightMyAttacker;
    private float targetWeightOutcoming;
    private float targetWeightType;
    private LinkedHashMap<String, List> targetWeightTypeTable;
    private float targetWeightYaw;

    private int barrelDiameter;
    private int overmatch;
    private float rotationDeg;
    private float shotDelay;

    private ArtyShell APShell;
    private ArtyShell HEShell;

    private Aura auraFar;
    private float maxVertAngle;
    private float idealDistance;
    private float idealRadius;
    private float minRadius;
    private float GMIdealRadius = 1f;

    @JsonIgnore
    public float getMaxDispersion()
    {
        return ((idealRadius * GMIdealRadius - minRadius) / 1000f * (maxDist / 30f) + minRadius) * 30f;
    }

    @JsonIgnore
    private ObjectMapper mapper = new ObjectMapper();

    @JsonAnySetter
    public void setTurrets(String name, Object value)
    {
        LinkedHashMap temp = mapper.convertValue(value, LinkedHashMap.class);
        idealDistance = ((Double) temp.get("idealDistance")).floatValue();
        idealRadius = ((Double) temp.get("idealRadius")).floatValue();
        minRadius = ((Double) temp.get("minRadius")).floatValue();
        if (temp.get("HitLocationArtillery") != null)
        {
            Turret turret = mapper.convertValue(value, Turret.class);
            turrets.put(name, turret);
            turretsList.add(turret);

            if (barrelDiameter != (int) (turret.getBarrelDiameter() * 1000))
            {
                barrelDiameter = (int) (turret.getBarrelDiameter() * 1000);
                overmatch = (int) Math.floor(barrelDiameter / 14.3);
                rotationDeg = turret.getRotationSpeed().get(0);
                shotDelay = turret.getShotDelay();
            }
        }
        else if (temp.get("guns") != null)
        {
            auraFar = mapper.convertValue(value, Aura.class);
        }
    }
}
