package WoWSSSC.model.gameparams.commanders.skills;

import lombok.Data;

@Data
public class AIGunsRangeModifier
{
    private int tier;
    private int column;
    private int skillType;
    private boolean turnOffOnRetraining;

    private float airDefenceRangeCoefficient;
    private float smallGunRangeCoefficient;
}
