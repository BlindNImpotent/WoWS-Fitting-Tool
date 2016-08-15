var skillsImageLocation;
var camouImageLocation;
var flagImageLocation;
var upgradeImageLocation;

var shipName;
var shipNation;
var shipType;
var crewNation;

var scaleDist = 0.03;

var apiShipJSON;

var ship_id_str;

var gpShipJSON;
var gpShipUpgradeInfo;
var GP_TurretJSON;

var apiArtilleryUpgradeJSON;
var apiHullUpgradeJSON;
var apiEngineUpgradeJSON;
var apiRadarUpgradeJSON;
var apiTorpedoUpgradeJSON;
var apiFlightControlUpgradeJSON;
var apiFighterUpgradeJSON;
var apiTorpedoBomberUpgradeJSON;
var apiDiveBomberUpgradeJSON;

var camouflageList;
var camouCodeList = [];

var flagsList;
var flagCodeList = [];

var skills;

var tier = 0;
var maxRepairCost = 0;
var maxHP = 0;
var speed = 0;
var speedCoef = 1;
var rudderShift = 0;
var turningRadius = 0;
var sConceal = 0;
var aConceal = 0;
var burnTime = 0;
var floodTime = 0;
var engineObj;
var horsePower;

var numTurrets = 0;
var numBarrels = 0;
var turretBarrelDiameter = 0;
var maxMainGunRange = 0;
var maxDistCoef = 1;
var mainGunReload = 0;
var mainGunRotation = 0;
var mainGunRotationTime = 0;

var APShellSpeed = 0;
var APShellDMG = 0;
var HEShellSpeed = 0;
var HEShellDMG = 0;
var HEShellBurnProb = 0;
var mainGunDispersionTangent = 0
var mainGunDispersionRange = 0;
var sigmaCount = 0;
var stealthFireSurfaceDetection = 0;
var AAFireAirDetection = 0;

var AAFarBarrelDiameter = 0;
var antiAirAuraDistanceFar = 0;
var AAFarDPS = 0;
var antiAirAuraDistanceMedium = 0;
var AAMediumDPS = 0;
var antiAirAuraDistanceNear = 0;
var AANearDPS = 0;
var secondaryMaxDist = 0;

var torpDiameter = 0;
var torpedoRotation = 0;
var numTubes = 0;
var numTorpTurrets = 0;
var maxTorpedoRange = 0;
var torpedoReload = 0;
var torpedoSpeed = 0;
var torpedoVisibilityFactor = 0;

var planesReserveCapacity = 0;
var torpedoBomberSquadronCount = 0;
var diveBomberSquadronCount = 0;
var fighterSquadronCount = 0;
var scoutSquadronCount = 0;
var totalSquadronCount = 0;
var torpedoBomberSquadronSize = 0;
var diveBomberSquadronSize = 0;
var fighterSquadronSize = 0;
var scoutSquadronSize = 0;
var torpedoBomberCount = 0;
var diveBomberCount = 0;
var fighterCount = 0;
var scoutCount = 0;

var fighterName;
var torpedoBomberName;
var diveBomberName;

var fighterVitalityTime = 0;
var fighterAaAmmoAmount = 0;
var fighterPrepareTime = 0;
var fighterFlightHeight = 0;
var fighterSpeedMove = 0;
var fighterPlaneTurnRadius = 0;
var fighterSquadronTurnRadius = 0;
var fighterActiveAntiAirAura = 0;
var fighterPatrolAttackDist = 0;
var fighterPatrolChaseDist = 0;
var fighterEscortShipFollowDistance = 0;
var fighterEscortShipAttackDist = 0;
var fighterEscortShipChaseDist = 0;
var fighterEscortPlaneFollowDistance = 0;
var fighterEscortPlaneAttackDist = 0;
var fighterEscortPlaneChaseDist = 0;
var fighterVisionToPlane = 0;
var fighterVisionToShip = 0;
var fighterVisionToTorpedo = 0;




//PCM003_Airplanes_Mod_I Slot 1
var AGM1_airplanesAntiAirAura = 1;

//PCM030_MainWeapon_Mod_I Slot 1
var MAM1_GMCritProb = 1;
var MAM1_GMMaxHP = 1;
var MAM1_GMRepairTime = 1;
var MAM1_GTCritProb = 1;
var MAM1_GTMaxHP = 1;
var MAM1_GTRepairTime = 1;

//PCM031_SecondaryWeapon_Mod_I Slot 1
var AAM1_ADMaxHP = 1;
var AAM1_GSMaxHP = 1;

//PCM032_PowderMagazine_Mod_I Slot 1
var MM1_PMDetonationProb = 1;

//PCM034_Guidance_Mod_0 Slot 1
var ASM0_GMIdealRadius = 1;
var ASM0_GMRotationSpeed = 1;
var ASM0_GSIdealRadius = 1;
var ASM0_GSMaxDist = 1;
var ASM0_GTRotationSpeed = 1;

//PCM009_FlightControl_Mod_I Slot 2
var FCM1_airplanesPrepareTime = 1;

//PCM010_Airplanes_Mod_II Slot 2
var AGM2_airplanesFighterVitalityTime = 1;

//PCM033_Guidance_Mod_I Slot 2
var ASM1_GMIdealRadius = 1;
var ASM1_GSIdealRadius = 1;
var ASM1_GSMaxDist = 1;
var ASM1_GTRotationSpeed = 1;

//PCM028_FireControl_Mod_I_US Slot 2
var APRM1US_GMMaxDist = 1;
var APRM1US_GSMaxDist = 1;
var APRM1US_GSIdealRadius = 1;

//PCM006_MainGun_Mod_II Slot 2
var MBM2_GMRotationSpeed = 1;
var MBM2_GMShotDelay = 1;

//PCM011_AirDefense_Mod_II Slot 2
var AAGM2_AAMaxDist = 1;

//PCM012_SecondaryGun_Mod_II Slot 2
var SBM2_GSMaxDist = 1;
var SBM2_GSIdealRadius = 1;

//PCM016_FlightControl_Mod_II Slot 3
var FCM2_airplanesSpeed = 1;

//PCM017_Airplanes_Mod_III Slot 3
var AGM3_airplanesBomberVitalityTime = 1;

//PCM013_MainGun_Mod_III Slot 3
var MBM3_GMRotationSpeed = 1;
var MBM3_GMShotDelay = 1;

//PCM014_Torpedo_Mod_III Slot 3
var TTMM3_GTCritProb = 1;
var TTMM3_GTShotDelay = 1;

//PCM015_FireControl_Mod_II Slot 3
var GFCSM2_GMMaxDist = 1;

//PCM018_AirDefense_Mod_III Slot 3
var AAGM3_AAAura = 1;

//PCM019_SecondaryGun_Mod_III Slot 3
var SBM3_GSShotDelay = 1;

//PCM029_FireControl_Mod_II_US Slot 3
var APRM2US_GMIdealRadius = 1;

//PCM020_DamageControl_Mod_I Slot 4
var DCSM1_burnProb = 1;
var DCSM1_floodProb = 1;

//PCM021_Engine_Mod_I Slot 4
var PM1_engineCritProb = 1;
var PM1_engineRepairTime = 1;

//PCM022_SteeringGear_Mod_I Slot 4
var SGM1_SGCritProb = 1;
var SGM1_SGRepairTime = 1;

//PCM023_DamageControl_Mod_II Slot 5
var DCSM2_burnTime = 1;
var DCSM2_floodTime = 1;

//PCM024_Engine_Mod_II Slot 5
var PM2_engineBackwardForsageMaxSpeed = 1;
var PM2_engineBackwardUpTime = 1;
var PM2_engineForwardForsageMaxSpeed = 1;
var PM2_engineForwardUpTime = 1;

//PCM025_SteeringGear_Mod_II Slot 5
var SGM2_SGRudderTime = 1;

//PCM026_LookoutStation_Mod_I Slot 6
var TASM1_visionDistCoeff = 1;
var TASM1_visionTorpedoCoeff = 1;
var TASM1_visionXRayShipCoeff = 1;

//PCM027_ConcealmentMeasures_Mod_I Slot 6
var CSM1_visibilityDistCoeff = 1;

var skillPointsCount = 0;
var airDefenceEfficiencyCoefficient = 1;
var smallGunReloadCoefficient = 1;
var airDefenceRangeCoefficient = 1;
var smallGunRangeCoefficient = 1;
var diveBomberCountSkill = 0;
var fighterCountSkill = 0;
var torpedoBomberCountSkill = 0;
var reloadCoefficientAllSkills = 1;
var ArtilleryAlertModifier = 0;
var critTimeCoefficient = 1;
var atbaIdealRadiusHi = 1;
var atbaIdealRadiusLo = 1;
var airDefenceSelectedTargetCoefficient = 1;
var reloadCoefficientDamageControl = 1;
var fightersEfficiencyCoefficient = 0;
var probabilityBonusFire = 0;
var probabilityCoefficientFire = 1;
var switchAmmoReloadCoef = 1;
var lastChanceHp = 1;
var lastChanceReloadCoefficient = 1;
var critRudderTimeCoefficient = 1;
var bigGunBonus = 0;
var smallGunBonus = 0;
var critProbCoefficient = 0;
var diveBombersPrepareCoefficient = 1;
var fightersPrepareCoefficient = 1;
var torpedoBombersPrepareCoefficient = 1;
var vitalityCoefficientPlane = 1;
var fightersPassiveEfficiencyCoefficient = 1;
var additionalConsumables = 0;
var healthPerLevel = 0;
var torpedoRangeCoefficient = 1;
var torpedoSpeedBonus = 0;
var rangeCoefficientTorpedoAlert = 0;
var bomberCoefficientTorpedoReload = 1;
var launcherCoefficientTorpedoReload = 1;
var aircraftCarrierCoefficientConceal = 1;
var battleshipCoefficientConceal = 1;
var cruiserCoefficientConceal = 1;
var destroyerCoefficientConceal = 1;
var VisibilityModifier = 1;

var consumable1Name;
var numConsumables1 = 0;
var reloadTime1 = 0;
var workTime1 = 0;

var consumable2Name;
var numConsumables2 = 0;
var reloadTime2 = 0;
var workTime2 = 0;

var consumable3Name;
var numConsumables3 = 0;
var reloadTime3 = 0;
var workTime3 = 0;

var consumable4Name;
var numConsumables4 = 0;
var reloadTime4 = 0;
var workTime4 = 0;

var visibilityFactorCamo = 1;
var visibilityFactorByPlaneCamo = 1;
var afterBattleRepairCamo = 1;
var expFactorCamo = 1;
var shootShiftCamo = 1;

var AAAuraFlag = 1;
var AAPassiveAuraFlag = 1;
var afterBattleRepairFlag = 1;
var crewExpFactorFlag_ZH = 1;
var crewExpFactorFlag_BD = 1;
var expFactorFlag = 1;
var GSIdealRadiusFlag = 1;
var GSMaxDistFlag = 1;
var GSShotDelayFlag = 1;
var PMDetonationProbFlag_JC = 1;
var PMDetonationProbFlag_IX = 1;
var PMDetonationProbFlag_JW1 = 1;
var abilReloadTimeFactorFlag = 1;
var burnChanceFactorBigFlag_VL = 0;
var burnChanceFactorSmallFlag_VL = 0;
var burnChanceFactorBigFlag_IX = 0;
var burnChanceFactorSmallFlag_IX = 0;
var burnTimeFlag = 1;
var collisionDamageApplyFlag = 1;
var collisionDamageNerfFlag = 1;
var creditsFactorFlag = 1;
var floodChanceFactorFlag_VL = 1;
var floodChanceFactorFlag_JW1 = 1;
var floodTimeFlag = 1;
var freeExpFactorFlag = 1;
var regenerationHPSpeedFlag = 1;
var shootShiftFlag = 1;
var speedCoefFlag = 1;
var visibilityFactorFlag = 1;
var visibilityFactorByPlaneFlag = 1;


var upgrades1;
var upgrades2;
var upgrades3;
var upgrades4;
var upgrades5;
var upgrades6;