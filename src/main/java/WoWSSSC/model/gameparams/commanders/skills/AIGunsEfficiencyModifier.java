package WoWSSSC.model.gameparams.commanders.skills;

import lombok.Data;

@Data
public class AIGunsEfficiencyModifier
{
    private int tier;
    private int column;
    private int skillType;
    private boolean turnOffOnRetraining;

    private float airDefenceEfficiencyCoefficient;
    private float smallGunReloadCoefficient;
}
