package WoWSSSC.model.gameparams.commanders.skills;

import lombok.Data;

@Data
public class VisibilityModifier
{
    private int tier;
    private int column;
    private int skillType;
    private boolean turnOffOnRetraining;

    private float aircraftCarrierCoefficient;
    private float battleshipCoefficient;
    private float cruiserCoefficient;
    private float destroyerCoefficient;
}
