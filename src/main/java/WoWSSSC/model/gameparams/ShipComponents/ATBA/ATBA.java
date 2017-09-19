package WoWSSSC.model.gameparams.ShipComponents.ATBA;

import WoWSSSC.model.gameparams.ShipComponents.AA.Aura;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Qualson-Lee on 2017-05-25.
 */
@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class ATBA
{
    private Aura auraFar;
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
    private HashMap<String, List> targetWeightTypeTable;
    private float targetWeightYaw;

    private HashMap<String, Secondary> secondaries = new HashMap<>();

    private List<Secondary> secondariesList = new ArrayList<>();

    @JsonIgnore
    private ObjectMapper mapper = new ObjectMapper();

    @JsonAnySetter
    public void setSecondaries(String name, Object value)
    {
        if (!(value instanceof String) && mapper.convertValue(value, LinkedHashMap.class).get("HitLocationATBA") != null)
        {
            Secondary secondary = mapper.convertValue(value, Secondary.class);
            secondaries.put(name, secondary);
        }
        else if (!(value instanceof String) && mapper.convertValue(value, LinkedHashMap.class).get("guns") != null)
        {
            auraFar = mapper.convertValue(value, Aura.class);
        }
    }
}
