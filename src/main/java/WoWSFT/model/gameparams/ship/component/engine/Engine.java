package WoWSFT.model.gameparams.ship.component.engine;

import WoWSFT.config.WoWSFT;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
public class Engine
{
    private float backwardEngineForsag;
    private float backwardEngineForsagMaxSpeed;
    private float backwardEngineUpTime;
    private float backwardSpeedOnFlood;
    private float damagedEnginePowerMultiplier;
    private float damagedEnginePowerTimeMultiplier;
    private float forwardEngineForsag;
    private float forwardEngineForsagMaxSpeed;
    private float forwardEngineUpTime;
    private float forwardSpeedOnFlood;
    private float histEnginePower;
    private float speedCoef;
}
