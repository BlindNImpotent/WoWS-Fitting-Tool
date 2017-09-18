package WoWSSSC.model.gameparams.ShipComponents.FireControl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FireControl
{
    private float maxDistCoef;
    private float sigmaCountCoef;
}
