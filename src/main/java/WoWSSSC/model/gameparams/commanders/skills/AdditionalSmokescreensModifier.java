package WoWSSSC.model.gameparams.commanders.skills;

import lombok.Data;

@Data
public class AdditionalSmokescreensModifier
{
    private int tier;
    private int column;
    private int skillType;
    private boolean turnOffOnRetraining;

    private int radiusCoefficient;
}
