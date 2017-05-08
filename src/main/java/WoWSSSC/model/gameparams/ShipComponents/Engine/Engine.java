package WoWSSSC.model.gameparams.ShipComponents.Engine;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Created by Aesis on 2017. 5. 8..
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Engine
{
    private double backwardEngineUpTime;
    private double forwardEngineUpTime;
    private double histEnginePower;
    private double speedCoef;
}
