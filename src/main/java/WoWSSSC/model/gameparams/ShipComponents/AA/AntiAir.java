package WoWSSSC.model.gameparams.ShipComponents.AA;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AntiAir
{
    private float antiAirAuraDistance;
    private float antiAirAuraStrength;
    private float barrelDiameter;
    private int numBarrels;
    private float shotDelay;
    private String name;
    private String realName;
    private int count;
    private String auraType;
    private float maxDistance;
    private float minDistance;

    public float getRealDistance()
    {
        return antiAirAuraDistance / (33f + (1f / 3f));
    }

    public String getAuraType()
    {
        if (StringUtils.isNotEmpty(auraType)) {
            return auraType.replace("Aura", "");
        }
        return auraType;
    }
}
