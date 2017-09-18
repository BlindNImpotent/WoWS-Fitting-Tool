package WoWSSSC.model.gameparams.ShipComponents.DiveBomber;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Created by Aesis on 2017-05-17.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiveBomber
{
    private String planeType;

    private DiveBomberPlane plane;
    private DiveBomberBomb bomb;
}
