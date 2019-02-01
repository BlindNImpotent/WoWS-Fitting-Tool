package WoWSFT.model.gameparams.ship.component.firecontrol;

import WoWSFT.config.WoWSFT;
import lombok.Data;

@Data
@WoWSFT
public class FireControl
{
    private float maxDistCoef;
    private float sigmaCountCoef;
}
