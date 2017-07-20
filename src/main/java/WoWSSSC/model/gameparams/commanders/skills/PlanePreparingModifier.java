package WoWSSSC.model.gameparams.commanders.skills;

import lombok.Data;

@Data
public class PlanePreparingModifier
{
    private int tier;
    private int column;
    private int skillType;
    private boolean turnOffOnRetraining;

    private float diveBombersPrepareCoefficient;
    private float fightersPrepareCoefficient;
    private float torpedoBombersPrepareCoefficient;
    private float vitalityCoefficient;
}
