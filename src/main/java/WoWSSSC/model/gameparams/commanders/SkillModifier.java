package WoWSSSC.model.gameparams.commanders;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class SkillModifier
{
    private int tier;
    private int column;
    private int skillType;
    private boolean turnOffOnRetraining;

    private int diveBomber;
    private int fighter;
    private int torpedoBomber;
    private int radiusCoefficient;
    private float airDefenceEfficiencyCoefficient;
    private float smallGunReloadCoefficient;
    private float airDefenceRangeCoefficient;
    private float smallGunRangeCoefficient;
    private int additionalConsumablePlane;
    private float consumablePlaneSpeedCoefficient;
    private float reloadCoefficient;
    private float alertMinDistance;
    private float critTimeCoefficient;
    private float airDefenceSelectedTargetCoefficient;
    private float atbaIdealRadiusHi;
    private float atbaIdealRadiusLo;
    private float fightersAmmunitionCoefficient;
    private float fightersEfficiencyCoefficient;
    private float probabilityBonus;
    private float probabilityCoefficient;
    private float switchAmmoReloadCoef;

    private float chanceToSetOnFireBonus;
    private float chanceToSetOnFireBonusBig;
    private float chanceToSetOnFireBonusSmall;
    private float thresholdPenetrationCoefficient;
    private float thresholdPenetrationCoefficientBig;
    private float thresholdPenetrationCoefficientSmall;

    private int hpStep;
    private float timeStep;
    private float critRudderTimeCoefficient;
    private float bigGunBonus;
    private float smallGunBonus;
    private float critProbCoefficient;
    private float fightersSpeedCoefficient;
    private float fightersVisibCoefficient;
    private float fightersVitalCoefficient;
    private float burnTimePenalty;
    private float diveBombersPrepareCoefficient;
    private float fightersPrepareCoefficient;
    private float torpedoBombersPrepareCoefficient;
    private float vitalityCoefficient;
    private float fightersPassiveEfficiencyCoefficient;
    private int additionalConsumables;
    private int healthPerLevel;
    private float torpedoRangeCoefficient;
    private float torpedoSpeedBonus;
    private float rangeCoefficient;
    private float bomberCoefficient;
    private float launcherCoefficient;
    private float aircraftCarrierCoefficient;
    private float battleshipCoefficient;
    private float cruiserCoefficient;
    private float destroyerCoefficient;
    private float submarineCoefficient;

    private boolean isEpic;
}
