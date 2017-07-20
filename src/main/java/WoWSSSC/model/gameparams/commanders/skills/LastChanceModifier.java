package WoWSSSC.model.gameparams.commanders.skills;

import lombok.Data;

@Data
public class LastChanceModifier
{
    private int tier;
    private int column;
    private int skillType;
    private boolean turnOffOnRetraining;

    private int hpStep;
    private float timeStep;
}
