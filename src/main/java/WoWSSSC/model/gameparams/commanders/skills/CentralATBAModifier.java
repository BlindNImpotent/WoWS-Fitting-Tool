package WoWSSSC.model.gameparams.commanders.skills;

import lombok.Data;

@Data
public class CentralATBAModifier
{
    private int tier;
    private int column;
    private int skillType;
    private boolean turnOffOnRetraining;

    private float atbaIdealRadiusHi;
    private float atbaIdealRadiusLo;
}
