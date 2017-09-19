package WoWSSSC.model.gameparams.ShipComponents.AA;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties (ignoreUnknown = true)
public class AuraFar
{
    private List<String> guns;
    private float selectedTargetCoeff;
    private float shotDelay;
}
