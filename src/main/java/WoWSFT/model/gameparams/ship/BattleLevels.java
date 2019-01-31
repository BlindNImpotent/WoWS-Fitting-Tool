package WoWSFT.model.gameparams.ship;

import WoWSFT.config.WoWSFT;
import lombok.Data;

import java.util.List;

@Data
@WoWSFT
public class BattleLevels
{
    private List<Integer> COOPERATIVE;
    private List<Integer> PVP;
}
