package WoWSFT.model.gameparams.ship.component.firecontrol;

import WoWSFT.config.WoWSFT;
import lombok.Data;

@Data
@WoWSFT
public class FireControl
{
    private double maxDistCoef;
    private double sigmaCountCoef;
}
