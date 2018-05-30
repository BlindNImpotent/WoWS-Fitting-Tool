package WoWSSSC.model.gameparams.commanders;

import lombok.Data;

@Data
public class UniqueSkillModifier
{
    private int numConsumables;
    private float regenerationHPSpeed;

    private float airplaneReloadCoeff;
    private float artilleryReloadCoeff;
    private float torpedoReloadCoeff;
    private float reloadCoeff;
    private float visibilityFactor;
    private float visibilityFactorByPlane;

    private int workTime;

    private int uniqueType;
}
