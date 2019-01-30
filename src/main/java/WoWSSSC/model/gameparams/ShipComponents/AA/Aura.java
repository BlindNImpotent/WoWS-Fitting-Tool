package WoWSSSC.model.gameparams.ShipComponents.AA;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties (ignoreUnknown = true)
public class Aura
{
    private List<String> guns;
    private float selectedTargetCoeff;
    private float shotDelay;
    private String auraType;
    private float maxDistance;
    private float minDistance;
}
