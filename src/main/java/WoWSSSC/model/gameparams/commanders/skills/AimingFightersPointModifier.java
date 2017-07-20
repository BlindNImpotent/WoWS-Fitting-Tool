package WoWSSSC.model.gameparams.commanders.skills;

import lombok.Data;

@Data
public class AimingFightersPointModifier
{
    private int tier;
    private int column;
    private int skillType;
    private boolean turnOffOnRetraining;

    private int additionalConsumablePlane;
    private float consumablePlaneSpeedCoefficient;
}
