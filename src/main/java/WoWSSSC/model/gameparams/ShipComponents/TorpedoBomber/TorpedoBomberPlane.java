package WoWSSSC.model.gameparams.ShipComponents.TorpedoBomber;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Created by Aesis on 2017. 5. 17..
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TorpedoBomberPlane
{
    private String bombName;
}
