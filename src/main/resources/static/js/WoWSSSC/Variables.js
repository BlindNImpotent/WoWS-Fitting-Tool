

//    var tier = 0;
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