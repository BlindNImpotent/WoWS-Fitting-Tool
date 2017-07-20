package WoWSSSC.model.gameparams.commanders.skills;

import lombok.Data;

@Data
public class TorpedoAcceleratorModifier
{
    private int tier;
    private int column;
    private int skillType;
    private boolean turnOffOnRetraining;

    private float torpedoRangeCoefficient;
    private float torpedoSpeedBonus;
}
