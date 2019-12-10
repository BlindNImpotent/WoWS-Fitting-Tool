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
    private double aaextraBubbles;
    @JsonProperty("AANearDamage")
    private double aanearDamage = 1.0;
    @JsonProperty("AAOuterDamage")
    private double aaouterDamage = 1.0;
    @JsonProperty("ADMaxHP")
    private double admaxHP = 1.0;
    @JsonProperty("GMCritProb")
    private double gmcritProb = 1.0;
    @JsonProperty("GMIdealRadius")
    private double gmidealRadius = 1.0;
    @JsonProperty("GMMaxDist")
    private double gmmaxDist = 1.0;
    @JsonProperty("GMMaxHP")
    private double gmmaxHP = 1.0;
    @JsonProperty("GMRepairTime")
    private double gmrepairTime = 1.0;
    @JsonProperty("GMRotationSpeed")
    private double gmrotationSpeed = 1.0;
    @JsonProperty("GMShotDelay")
    private double gmshotDelay = 1.0;
    @JsonProperty("GMSigmaCount")
    private double gmsigmaCount = 1.0;
    @JsonProperty("GSIdealRadius")
    private double gsidealRadius = 1.0;
    @JsonProperty("GSMaxDist")
    private double gsmaxDist = 1.0;
    @JsonProperty("GSMaxHP")
    private double gsmaxHP = 1.0;
    @JsonProperty("GSShotDelay")
    private double gsshotDelay = 1.0;
    @JsonProperty("GSSigmaCount")
    private double gssigmaCount = 1.0;
    @JsonProperty("GTCritProb")
    private double gtcritProb = 1.0;
    @JsonProperty("GTMaxHP")
    private double gtmaxHP = 1.0;
    @JsonProperty("GTRepairTime")
    private double gtrepairTime = 1.0;
    @JsonProperty("GTRotationSpeed")
    private double gtrotationSpeed = 1.0;
    @JsonProperty("GTShotDelay")
    private double gtshotDelay = 1.0;
    @JsonProperty("PMCritProb")
    private double pmcritProb = 1.0;
    @JsonProperty("PMDetonationProb")
    private double pmdetonationProb = 1.0;
    @JsonProperty("PMRepairTime")
    private double pmrepairTime = 1.0;
    @JsonProperty("SGCritProb")
    private double sgcritProb = 1.0;
    @JsonProperty("SGRepairTime")
    private double sgrepairTime = 1.0;
    @JsonProperty("SGRudderTime")
    private double sgrudderTime = 1.0;
    private double airDefenseDispWorkTime = 1.0;
    private double airplanesDiveBombersHealth = 1.0;
    private double airplanesEmptyReturnSpeed = 1.0;
    private double airplanesExtraHangarSize;
    private double airplanesFighterAimingTime;
    private double airplanesFightersHealth = 1.0;
    private double airplanesForsageDuration = 1.0;
    private double airplanesHealth = 1.0;
    private double airplanesSpawnTime = 1.0;
    private double airplanesSpeed = 1.0;
    private double airplanesTorpedoAimingTime;
    private double airplanesTorpedoBombersHealth = 1.0;
    private double burnProb = 1.0;
    private double burnTime = 1.0;
    private double crashCrewWorkTime = 1.0;
    private double engineBackwardForsageMaxSpeed = 1.0;
    private double engineBackwardForsagePower = 1.0;
    private double engineBackwardUpTime = 1.0;
    private double engineCritProb = 1.0;
    private double engineForwardForsageMaxSpeed = 1.0;
    private double engineForwardForsagePower = 1.0;
    private double engineForwardUpTime = 1.0;
    private double engineRepairTime = 1.0;
    private double floodProb = 1.0;
    private double floodTime = 1.0;
    private double invulnerableWorkTime = 1.0;
    private double rlsSearchWorkTime = 1.0;
    private double scoutWorkTime = 1.0;
    private double shootShift = 1.0;
    private double smokeGeneratorLifeTime = 1.0;
    private double smokeGeneratorWorkTime = 1.0;
    private double sonarSearchWorkTime = 1.0;
    private double speedBoosterWorkTime = 1.0;
    private double squadronVisibilityDistCoeff = 1.0;
    private double trigger1SearchWorkTime = 1.0;
    private double trigger2SearchWorkTime = 1.0;
    private double trigger3SearchWorkTime = 1.0;
    private double trigger4SearchWorkTime = 1.0;
    private double trigger5SearchWorkTime = 1.0;
    private double trigger6SearchWorkTime = 1.0;
    private double visibilityDistCoeff = 1.0;
    private double visionDistCoeff = 1.0;
    private double visionTorpedoCoeff = 1.0;
    private double visionXRayShipCoeff = 1.0;

    //
    private String modifier;
    private double diveBomber = 1.0;
    private double fighter = 1.0;
    private double torpedoBomber = 1.0;
    private double radiusCoefficient = 1.0;
    private double smallGunReloadCoefficient = 1.0;
    private double smallGunRangeCoefficient = 1.0;
    private double consumablePlaneSpeedCoefficient = 1.0;
    private double reloadCoefficient = 1.0;
    private double alertMinDistance;
    private double critTimeCoefficient = 1.0;
    private double atbaIdealRadiusHi = 1.0;
    private double atbaIdealRadiusLo = 1.0;
    private double fightersAmmunitionCoefficient = 1.0;
    private double fightersEfficiencyCoefficient = 1.0;
    private double probabilityBonus;
    private double probabilityCoefficient = 1.0;
    private double switchAmmoReloadCoef = 1.0;

    private double chanceToSetOnFireBonus;
    private double chanceToSetOnFireBonusBig;
    private double chanceToSetOnFireBonusSmall;
    private double thresholdPenetrationCoefficient = 1.0;
    private double thresholdPenetrationCoefficientBig = 1.0;
    private double thresholdPenetrationCoefficientSmall = 1.0;

    private double hpStep = 1.0;
    private double timeStep;
    private double critRudderTimeCoefficient = 1.0;
    private double bigGunBonus;
    private double smallGunBonus;
    private double critProbCoefficient = 1.0;
    private double fightersSpeedCoefficient = 1.0;
    private double fightersVisibCoefficient = 1.0;
    private double fightersVitalCoefficient = 1.0;
    private double burnTimePenalty;
    private double diveBombersPrepareCoefficient = 1.0;
    private double fightersPrepareCoefficient = 1.0;
    private double torpedoBombersPrepareCoefficient = 1.0;
    private double vitalityCoefficient = 1.0;
    private double fightersPassiveEfficiencyCoefficient = 1.0;
    private double additionalConsumables;
    private double healthPerLevel;
    private double torpedoRangeCoefficient = 1.0;
    private double torpedoSpeedBonus;
    private double rangeCoefficient = 1.0;
    private double bomberCoefficient = 1.0;
    private double launcherCoefficient = 1.0;
    private double aircraftCarrierCoefficient = 1.0;
    private double battleshipCoefficient = 1.0;
    private double cruiserCoefficient = 1.0;
    private double destroyerCoefficient = 1.0;
    private double submarineCoefficient = 1.0;
    private double squadronCoefficient = 1.0;
    private double nearAuraDamageCoefficient = 1.0;
    private double advancedOuterAuraDamageCoefficient = 1.0;
    private double extraFighterCount;
    private double fighterLifeTimeCoefficient = 1.0;
    private double hangarSizeBonus;
    private double planeSpawnTimeCoefficient = 1.0;
    private double prioritySectorStrengthCoefficient = 1.0;
    private double sectorSwitchDelayCoefficient = 1.0;
    private double bombProbabilityBonus;
    private double rocketProbabilityBonus;
    private double flightSpeedCoefficient = 1.0;
    private double forsageDurationCoefficient = 1.0;
    private double squadronHealthStep = 1.0;
    private double squadronSpeedStep = 1.0;
    private double nearAuraDamageTakenCoefficient = 1.0;
    private double planeHealthPerLevel;
    private double planeTorpedoRangeCoefficient = 1.0;
    private double planeTorpedoSpeedBonus;
    private double planeRangeCoefficient = 1.0;
    private double prioSectorCooldownCoefficient = 1.0;
    private double prioSectorPhaseDurationCoefficient = 1.0;
    private double prioSectorStartPhaseStrengthCoefficient = 1.0;
    private double prioSectorStrengthCoefficient = 1.0;
}
