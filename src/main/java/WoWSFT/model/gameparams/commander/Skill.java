package WoWSFT.model.gameparams.commander;

import WoWSFT.config.WoWSFT;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;

@Data
@WoWSFT
public class Skill
{
    private int tier;
    @JsonInclude
    private int column;
    @JsonIgnore
    private int skillType;
    @JsonIgnore
    private boolean turnOffOnRetraining;

    private float diveBomber = 1f;
    private float fighter = 1f;
    private float torpedoBomber = 1f;
    private float radiusCoefficient = 1f;
    private float smallGunReloadCoefficient = 1f;
    private float smallGunRangeCoefficient = 1f;
    private float consumablePlaneSpeedCoefficient = 1f;
    private float reloadCoefficient = 1f;
    private float alertMinDistance;
    private float critTimeCoefficient = 1f;
    private float atbaIdealRadiusHi = 1f;
    private float atbaIdealRadiusLo = 1f;
    private float fightersAmmunitionCoefficient = 1f;
    private float fightersEfficiencyCoefficient = 1f;
    private float probabilityBonus = 1f;
    private float probabilityCoefficient = 1f;
    private float switchAmmoReloadCoef = 1f;

    private float chanceToSetOnFireBonus = 1f;
    private float chanceToSetOnFireBonusBig = 1f;
    private float chanceToSetOnFireBonusSmall = 1f;
    private float thresholdPenetrationCoefficient = 1f;
    private float thresholdPenetrationCoefficientBig = 1f;
    private float thresholdPenetrationCoefficientSmall = 1f;

    private float hpStep;
    private float timeStep = 1f;
    private float critRudderTimeCoefficient = 1f;
    private float bigGunBonus;
    private float smallGunBonus;
    private float critProbCoefficient = 1f;
    private float fightersSpeedCoefficient = 1f;
    private float fightersVisibCoefficient = 1f;
    private float fightersVitalCoefficient = 1f;
    private float burnTimePenalty;
    private float diveBombersPrepareCoefficient = 1f;
    private float fightersPrepareCoefficient = 1f;
    private float torpedoBombersPrepareCoefficient = 1f;
    private float vitalityCoefficient = 1f;
    private float fightersPassiveEfficiencyCoefficient = 1f;
    private float additionalConsumables;
    private float healthPerLevel;
    private float torpedoRangeCoefficient = 1f;
    private float torpedoSpeedBonus;
    private float rangeCoefficient = 1f;
    private float bomberCoefficient = 1f;
    private float launcherCoefficient = 1f;
    private float aircraftCarrierCoefficient = 1f;
    private float battleshipCoefficient = 1f;
    private float cruiserCoefficient = 1f;
    private float destroyerCoefficient = 1f;
    private float submarineCoefficient = 1f;
    private float squadronCoefficient = 1f;
    private float nearAuraDamageCoefficient = 1f;
    private float advancedOuterAuraDamageCoefficient = 1f;
    private float extraFighterCount;
    private float fighterLifeTimeCoefficient = 1f;
    private float hangarSizeBonus;
    private float planeSpawnTimeCoefficient = 1f;
    private float prioritySectorStrengthCoefficient = 1f;
    private float sectorSwitchDelayCoefficient = 1f;
    private float bombProbabilityBonus = 1f;
    private float rocketProbabilityBonus = 1f;
    private float flightSpeedCoefficient = 1f;
    private float forsageDurationCoefficient = 1f;
    private float squadronHealthStep;
    private float squadronSpeedStep = 1f;
    private float nearAuraDamageTakenCoefficient = 1f;
    private float planeHealthPerLevel;
    private float planeTorpedoRangeCoefficient = 1f;
    private float planeTorpedoSpeedBonus;
    private float planeRangeCoefficient = 1f;
    @JsonAlias("isEpic")
    private boolean epic;
    private String modifier;

    private LinkedHashMap<String, String> bonus = new LinkedHashMap<>();
    private String description = "";
    private String image;

    public String getImage()
    {
        return "https://cdn.wowsft.com/images/crew_commander/skills/icon_perk_" + modifier + ".png";
    }
}
