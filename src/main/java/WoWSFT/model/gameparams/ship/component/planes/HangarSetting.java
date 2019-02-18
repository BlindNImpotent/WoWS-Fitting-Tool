package WoWSFT.model.gameparams.ship.component.planes;

import lombok.Data;

@Data
public class HangarSetting
{
    private int maxValue;
    private int restoreAmount;
    private int startValue;
    private float timeToRestore;
}
