package WoWSSSC.model.gameparams.ShipComponents.DiveBomber;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * Created by Aesis on 2017-05-17.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiveBomberPlane
{
    private String bombName;

    private float bombFallingTime;
    private float bombFallingTimeGround;
    private List<Float> bombSalvoCoef;
    private List<Float> bombSalvoCoefDefense;
    private List<Float> bombSalvoCoefGround;
    private List<Float> bombSalvoCoefGroundDefense;
}
