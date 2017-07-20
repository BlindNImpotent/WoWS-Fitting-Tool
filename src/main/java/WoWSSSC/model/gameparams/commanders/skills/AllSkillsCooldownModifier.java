package WoWSSSC.model.gameparams.commanders.skills;

import lombok.Data;

@Data
public class AllSkillsCooldownModifier
{
    private int tier;
    private int column;
    private int skillType;
    private boolean turnOffOnRetraining;

    private float reloadCoefficient;
}
