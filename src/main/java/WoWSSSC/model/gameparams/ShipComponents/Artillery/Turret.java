package WoWSSSC.model.gameparams.ShipComponents.Artillery;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Created by Aesis on 2017-05-03.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Turret
{
    private float barrelDiameter;
}
