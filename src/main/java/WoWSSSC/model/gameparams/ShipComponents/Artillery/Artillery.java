package WoWSSSC.model.gameparams.ShipComponents.Artillery;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

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
    private int aimLevel;
    private LinkedHashMap<String, Integer> ammoPool;
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
    private long penetrationHE;
    private float penetrationHEFloat;

    private APShell APShell;

    @JsonIgnore
    private ObjectMapper mapper = new ObjectMapper();

    @JsonAnySetter
    public void setTurrets(String name, Object value)
    {
        turrets.put(name, mapper.convertValue(value, Turret.class));

        if (barrelDiameter < mapper.convertValue(value, Turret.class).getBarrelDiameter() * 1000)
        {
            barrelDiameter = (int) (mapper.convertValue(value, Turret.class).getBarrelDiameter() * 1000);
        }
    }

    public void setPenetrationHEWithNation(String nation)
    {
        if ("germany".equalsIgnoreCase(nation))
        {
            penetrationHE = Math.round((float) barrelDiameter / 4);
            penetrationHEFloat = (float) barrelDiameter / 4;
        }
        else
        {
            penetrationHE = Math.round((float) barrelDiameter / 6);
            penetrationHEFloat = (float) barrelDiameter / 6;
        }
    }
}
