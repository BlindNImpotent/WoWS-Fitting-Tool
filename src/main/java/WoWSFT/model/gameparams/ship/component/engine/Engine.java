package WoWSFT.model.gameparams.ship.component.engine;

import WoWSFT.config.WoWSFT;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
public class Engine
{
    private double backwardEngineForsag;
    private double backwardEngineForsagMaxSpeed;
    private double backwardEngineUpTime;
    private double backwardSpeedOnFlood;
    private double damagedEnginePowerMultiplier;
    private double damagedEnginePowerTimeMultiplier;
    private double forwardEngineForsag;
    private double forwardEngineForsagMaxSpeed;
    private double forwardEngineUpTime;
    private double forwardSpeedOnFlood;
    private double histEnginePower;
    private double speedCoef;
}
