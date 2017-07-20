package WoWSSSC.model.gameparams.commanders.skills;

import lombok.Data;

@Data
public class OutOfAttackModifier
{
    private int tier;
    private int column;
    private int skillType;
    private boolean turnOffOnRetraining;

    private float fightersSpeedCoefficient;
    private float fightersVisibCoefficient;
    private float fightersVitalCoefficient;
}
