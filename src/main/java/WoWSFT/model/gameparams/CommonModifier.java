package WoWSFT.model.gameparams;

import WoWSFT.config.WoWSFT;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;

@Data
@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonModifier
{
    @JsonProperty("AAExtraBubbles")
    private float aaextraBubbles;
    @JsonProperty("AANearDamage")
    private float aanearDamage = 1f;
    @JsonProperty("AAOuterDamage")
    private float aaouterDamage = 1f;
    @JsonProperty("ADMaxHP")
    private float admaxHP = 1f;
    @JsonProperty("GMCritProb")
    private float gmcritProb = 1f;
    @JsonProperty("GMIdealRadius")
    private float gmidealRadius = 1f;
    @JsonProperty("GMMaxDist")
    private float gmmaxDist = 1f;
    @JsonProperty("GMMaxHP")
    private float gmmaxHP = 1f;
    @JsonProperty("GMRepairTime")
    private float gmrepairTime = 1f;
    @JsonProperty("GMRotationSpeed")
    private float gmrotationSpeed = 1f;
    @JsonProperty("GMShotDelay")
    private float gmshotDelay = 1f;
    @JsonProperty("GMSigmaCount")
    private float gmsigmaCount = 1f;
    @JsonProperty("GSIdealRadius")
    private float gsidealRadius = 1f;
    @JsonProperty("GSMaxDist")
    private float gsmaxDist = 1f;
    @JsonProperty("GSMaxHP")
    private float gsmaxHP = 1f;
    @JsonProperty("GSShotDelay")
    private float gsshotDelay = 1f;
    @JsonProperty("GSSigmaCount")
    private float gssigmaCount = 1f;
    @JsonProperty("GTCritProb")
    private float gtcritProb = 1f;
    @JsonProperty("GTMaxHP")
    private float gtmaxHP = 1f;
    @JsonProperty("GTRepairTime")
    private float gtrepairTime = 1f;
    @JsonProperty("GTRotationSpeed")
    private float gtrotationSpeed = 1f;
    @JsonProperty("GTShotDelay")
    private float gtshotDelay = 1f;
    @JsonProperty("PMCritProb")
    private float pmcritProb = 1f;
    @JsonProperty("PMDetonationProb")
    private float pmdetonationProb = 1f;
    @JsonProperty("PMRepairTime")
    private float pmrepairTime = 1f;
    @JsonProperty("SGCritProb")
    private float sgcritProb = 1f;
    @JsonProperty("SGRepairTime")
    private float sgrepairTime = 1f;
    @JsonProperty("SGRudderTime")
    private float sgrudderTime = 1f;
    private float airDefenseDispWorkTime = 1f;
    private float airplanesDiveBombersHealth = 1f;
    private float airplanesEmptyReturnSpeed = 1f;
    private float airplanesExtraHangarSize;
    private float airplanesFighterAimingTime = 1f;
    private float airplanesFightersHealth = 1f;
    private float airplanesForsageDuration = 1f;
    private float airplanesHealth = 1f;
    private float airplanesSpawnTime = 1f;
    private float airplanesSpeed = 1f;
    private float airplanesTorpedoAimingTime = 1f;
    private float airplanesTorpedoBombersHealth = 1f;
    private float burnProb = 1f;
    private float burnTime = 1f;
    private float crashCrewWorkTime = 1f;
    private float engineBackwardForsageMaxSpeed = 1f;
    private float engineBackwardForsagePower = 1f;
    private float engineBackwardUpTime = 1f;
    private float engineCritProb = 1f;
    private float engineForwardForsageMaxSpeed = 1f;
    private float engineForwardForsagePower = 1f;
    private float engineForwardUpTime = 1f;
    private float engineRepairTime = 1f;
    private float floodProb = 1f;
    private float floodTime = 1f;
    private float invulnerableWorkTime = 1f;
    private float rlsSearchWorkTime = 1f;
    private float scoutWorkTime = 1f;
    private float shootShift = 1f;
    private float smokeGeneratorLifeTime = 1f;
    private float smokeGeneratorWorkTime = 1f;
    private float sonarSearchWorkTime = 1f;
    private float speedBoosterWorkTime = 1f;
    private float squadronVisibilityDistCoeff = 1f;
    private float trigger1SearchWorkTime = 1f;
    private float trigger2SearchWorkTime = 1f;
    private float trigger3SearchWorkTime = 1f;
    private float trigger4SearchWorkTime = 1f;
    private float trigger5SearchWorkTime = 1f;
    private float trigger6SearchWorkTime = 1f;
    private float visibilityDistCoeff = 1f;
    private float visionDistCoeff = 1f;
    private float visionTorpedoCoeff = 1f;
    private float visionXRayShipCoeff = 1f;

    //
    private String modifier;
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
}
