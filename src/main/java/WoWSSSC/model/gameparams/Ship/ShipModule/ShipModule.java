package WoWSSSC.model.gameparams.Ship.ShipModule;

import WoWSSSC.model.gameparams.GameParamsValues;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Aesis on 2016-12-03.
 */
@Data
public class ShipModule
{
    @JsonIgnore
    ObjectMapper mapper = new ObjectMapper();

    private boolean isIsFake;
    private PlanesReserveAssignment planesReserveAssignment;
    private float prepareTimeFactor;
    private HashSet<List> squadrons;
    private float default_distance;
    private float max_distance;
    private float min_distance;
    private float maxDist;
    private float minDistH;
    private float minDistV;
    private float sigmaCount;
    private float taperDist;
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
    private HashMap<String, List<Float>> targetWeightTypeTable;
    private float targetWeightYaw;

    private HashMap<String, GameParamsValues> subModules = new HashMap<>();

    @JsonAnySetter
    public void setSubModules(String name, Object value)
    {
        subModules.put(name, mapper.convertValue(value, GameParamsValues.class));
    }
}
