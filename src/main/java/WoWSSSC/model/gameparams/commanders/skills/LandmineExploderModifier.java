package WoWSSSC.model.gameparams.commanders.skills;

import lombok.Data;

@Data
public class LandmineExploderModifier
{
    private int tier;
    private int column;
    private int skillType;
    private boolean turnOffOnRetraining;

    private float chanceToSetOnFireBonus;
    private float thresholdPenetrationCoefficient;
}
