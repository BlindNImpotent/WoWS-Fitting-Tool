package WoWSSSC.model.gameparams.test;

import WoWSSSC.model.gameparams.test.Values.ShipAbilities.ShipAbilities;
import WoWSSSC.model.gameparams.test.Values.ShipModernization.ShipModernization;
import WoWSSSC.model.gameparams.test.Values.ShipUpgradeInfo.ShipUpgradeInfo;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Aesis on 2016-12-03.
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class GameParamsValues
{
    @JsonIgnore
    ObjectMapper mapper = new ObjectMapper();

    @JsonProperty(value = "CautiousBehaviour")
    private HashMap CautiousBehaviour;
    private float aaAmmoAmount;
    private float activeAntiAirAura;
    private float angleOfClimb;
    private float angleOfDive;
    private float bombFallingTime;
    private float bombFallingTimeGround;
    private String bombName;
    private List<Float> bombSalvoCoef;
    private List<Float> bombSalvoCoefDefense;
    private List<Float> bombSalvoCoefGround;
    private List<Float> bombSalvoCoefGroundDefense;
    private float bombingAttackAngle;
    private float bombingAttackAngleGround;
    private float bombingAttackPointDistance;
    private float bombingAttackPointDistanceGround;
    private float bombingAttackPointHeight;
    private float bombingAttackPointHeightGround;
    private float bombingDelay;
    private float bombingDelayGround;
    private float bombingDeploymentDistance;
    private float bombingDropPointTime;
    private float bombingDropPointTimeGround;
    private float bombingRunAwayDistance;
    private float bombingRunAwayDistanceGround;
    private float bombingRunAwayHeight;
    private float bombingRunAwayHeightGround;
    private float bombingStartPointDistance;
    private float bombingStartPointDistanceGround;
    private float bombingStartPointHeight;
    private float bombingStartPointHeightGround;
    private float bombingStartTime;
    private float bombingStartTimeGround;
    private float climbSpeedCoef;
    private float deathPenalty;
    private String defenceTracerName;
    private String engineSoundType;
    private float escortPlaneAttackDist;
    private float escortPlaneChaseDist;
    private float escortPlaneFollowDistance;
    private float escortShipAttackDist;
    private float escortShipChaseDist;
    private float escortShipFollowDistance;
    private String fightTracerName;
    private float fightingFormationDistance;
    private float fightingStartDistance;
    private float fightingStopDistance;
    private float flightHeight;
    private float flightRadius;
    private HashSet formationAttack;
    private HashSet formationAttackMove;
    private HashSet formationAvoidThreat;
    private HashSet formationBombing;
    private HashSet formationEscortPlane;
    private HashSet formationEscortShip;
    private HashSet formationMove;
    private HashSet formationMoveToTarget;
    private HashSet formationSpinAround;
    private float fuelTime;
    private float inLandingQueueFlightHeight;
    private float inLandingQueueFlightRadius;
    private boolean isIsConsumablePlane;
    private float launchDelayTime;
    private float launchMeetingTime;
    private float maxVisibilityFactor;
    private float maxVisibilityFactorByPlane;
    private float minVisibilityFactor;
    private float minVisibilityFactorByPlane;
    private String model;
    private float movementAcknowledgeDelay;
    private float numFloats;
    private float numPlanesDefault;
    private float outAttackDistance;
    private float passiveAntiAirAura;
    private float planeMaxPitchSpeed;
    private float planePitchAccelerationTime;
    private float planeSpeedupCoef;
    private float planeTurnRadius;
    private float planeYawAccelerationTime;
    private float prepareTime;
    private float speedAttack;
    private float speedAttackMove;
    private float speedDefense;
    private float speedEscort;
    private float speedLanding;
    private float speedMove;
    private float speedMoveWithBomb;
    private float speedSpinAround;
    private float squadronTurnRadius;
    private float startAngle;
    private float stormingAmmoSpendCoeff;
    private List<Float> stormingArea;
    private float stormingAuraCoeff;
    private HashSet stormingFormation;
    private HashSet stormingMoveAttackFormation;
    private float stormingSpeed;
    private float stormingTime;
    private String stormingTracerName;
    private float torpedoAimDist;
    private float torpedoSpreading;
    private float torpedoSpreadingDefense;
    private float torpedoSpreadingGround;
    private float torpedoSpreadingGroundDefense;
    private float visionRayToShip;
    private float visionToPlane;
    private float visionToShip;
    private float visionToTorpedo;
    private float vitalityTime;
    private float vitalityTimeAir;

    private float angle;
    private float dist;
    private boolean hidePlane;
    private float rotateTime;

    private String gunNode;
    private String nation;
    private String otype;
    private String stype;

    @JsonProperty(value = "AAAura")
    private float AAAura;
    @JsonProperty(value = "AAPassiveAura")
    private float AAPassiveAura;
    @JsonProperty(value = "GSIdealRadius")
    private float GSIdealRadius;
    @JsonProperty(value = "GSMaxDist")
    private float GSMaxDist;
    @JsonProperty(value = "GSShotDelay")
    private float GSShotDelay;
    @JsonProperty(value = "PMDetonationProb")
    private float PMDetonationProb;
    private float abilReloadTimeFactor;
    private float afterBattleRepair;
    private float burnChanceFactorBig;
    private float burnChanceFactorSmall;
    private float burnTime;
    private String camouflage;
    private boolean canBuy;
    private float collisionDamageApply;
    private float collisionDamageNerf;
    private long costCR;
    private long costGold;
    private float expFactor;
    private float floodChanceFactor;
    private float floodTime;
    private boolean hidden;
    private float regenerationHPSpeed;
    private float shootShift;
    private long sortOrder;
    private float speedCoef;
    private float visibilityFactor;
    private float visibilityFactorByPlane;

    @JsonProperty(value = "HitLocationAirDefense")
    private WoWSSSC.model.gameparams.Values.HitLocation HitLocationAirDefense;
    private float antiAirAuraDistance;
    private float antiAirAuraStrength;
    private float barrelDiameter;
    private String deadMesh;
    private float numBarrels;
    private boolean smallGun;

    @JsonProperty(value = "HitLocationArtillery")
    private WoWSSSC.model.gameparams.Values.HitLocation HitLocationArtillery;
    private float aiMGmaxEllipseRanging;
    private float aiMGmedEllipseRanging;
    private float aiMGminEllipseRanging;
    private List<String> ammoList;
    private float coeffPerSecondMin;
    private List deadZone;
    private float delim;
    private float ellipseRangeMax;
    private float ellipseRangeMin;
    private List<Float> horizSector;
    private float idealDistance;
    private float idealRadius;
    private String lensEffect;
    private float maxEllipseRanging;
    private float medEllipseRanging;
    private float minEllipseRanging;
    private float minRadius;
    private List miscFilter;
    private boolean miscFilterMode;
    private float onMoveTarPosCoeffDelim;
    private float onMoveTarPosCoeffMaxDist;
    private float onMoveTarPosCoeffZero;
    private float onMoveTarPosDelim;
    private List pitchDeadZones;
    private List<Float> position;
    private String purgingEffect;
    private float radiusOnDelim;
    private float radiusOnMax;
    private float radiusOnZero;
    private float reduceTime;
    private List<Float> rotationSpeed;
    private float shotDelay;
    private String shotEffect;
    private List<Float> vertSector;

    @JsonProperty(value = "HitLocationATBA")
    private WoWSSSC.model.gameparams.Values.HitLocation HitLocationATBA;
    private float aiATBAmultiplier;
    private long clusterID;

    @JsonProperty(value = "HitLocationTorpedo")
    private WoWSSSC.model.gameparams.Values.HitLocation HitLocationTorpedo;
    private List<Float> mainSector;
    private float numAmmos;
    private float timeBetweenShots;
    private float timeToChangeAngle;
    private float timeToChangeSpeed;
    private List<Float> torpedoAngles;

    private float alphaDamage;
    private float alphaPiercingHE;
    private String ammoType;
    private float bulletAirDrag;
    private float bulletAlwaysRicochetAt;
    private boolean bulletCap;
    private float bulletCapNormalizeMaxAngle;
    private float bulletDetonator;
    private float bulletDetonatorSpread;
    private float bulletDetonatorThreshold;
    private float bulletDiametr;
    private float bulletKrupp;
    private float bulletMass;
    private float bulletPenetrationSpread;
    private float bulletRicochetAt;
    private float bulletSpeed;
    private float bulletUnderwaterDistFactor;
    private float bulletUnderwaterPenetrationFactor;
    private float bulletWaterDrag;
    private float burnProb;
    private float damage;
    private float directDamage;
    private String ground;
    private String hatTracerTexture;
    private float hitDecalSize;
    private String ownHatTracerTexture;
    private String ownTracerEffect;
    private String ownTracerTexture;
    private String projDestroyedEffect;
    private float shellGlow;
    private float shellLength;
    private float shellModelScale;
    private String shipDestroyEffect;
    private String shotSound;
    private float smokeAlphaFalloff;
    private float smokeOpacity;
    private String smokeTexture;
    private float smokeThickness;
    private float smokeTileLength;
    private float splashArmorCoeff;
    private float splashCubeSize;
    private String tracerEffect;
    private float tracerLength;
    private float tracerOpacity;
    private String tracerTexture;
    private float tracerThickness;
    private boolean useSpecialShellModel;
    private boolean uwAbility;
    private float uwCritical;
    private String water;
    private List<Float> waterRefractionReflectDeltaAngleInterval;

    private float innerBombsPercentage;
    private List<Float> innerSalvoSize;
    private List<Float> outterSalvoSize;
    private List<Float> salvoSize;

    private float alertDist;
    private float armingTime;
    private float speed;

    private List<Float> bulletSplashCubesDamage;
    private float underwaterSplashBPDamageMultiplier;

    private boolean isIsFake;
    private WoWSSSC.model.gameparams.Values.PlanesReserveAssignment planesReserveAssignment;
    private float prepareTimeFactor;
    private HashSet<List> squadrons;
    private float default_distance;
    private float max_distance;
    private float min_distance;
    private float maxDist;
    private float minDistH;
    private float minDistV;
    private float sigmaCount;
    private float taperDist;
    private float targetWeightAllyAttacker;
    private float targetWeightDist;
    private float targetWeightHealth;
    private float targetWeightIncoming;
    private float targetWeightMaxDist;
    private float targetWeightMaxHealth;
    private float targetWeightMinDist;
    private float targetWeightMinHealth;
    private float targetWeightMyAttacker;
    private float targetWeightOutcoming;
    private float targetWeightType;
    private HashMap<String, List<Float>> targetWeightTypeTable;
    private float targetWeightYaw;

    private float auraCoeff;
    private float deckPlaceCount;
    private float launchPrepareTime;
    private String launchpadType;
    private float planesReserveCapacity;

    private float armorCoeff;
    private float autoRepairTime;
    private float autoRepairTimeMin;
    private float buoyancyShipPercent;
    private String burnNode;
    private boolean canBeDestroyed;
    private float coeffLaunchTimeDamaged;
    private List<Float> critProb;
    private List<Float> critProbHP;
    private List damageEffects;
    private List<String> deathEffects;
    private String detonateEffect;
    private boolean detonateOnDead;
    private List<Float> hitDetonationMaxProbAtDamage;
    private List<Float> hitDetonationMinProbAtDamage;
    private String hlType;
    private float maxBuoyancy;
    private float maxHP;
    private float moduleCritCoeffCR;
    private float moduleCritCoeffXP;
    private String parentHL;
    private float regeneratedHPPart;
    private float rndPartHP;
    private List<String> splashBoxes;
    private float tbSkipProbMax;
    private float tbSkipProbMin;
    private float transmitBPDamageToParentFactor;
    private float transmitHPDamageToParentFactor;
    private float volume;
    private float volumeCoeff;

    private String detonationEffect;
    private boolean enableCrewSelectedTargetCoeff;
    private boolean enableDispersion;
    private List<String> guns;
    private boolean isIsJoint;
    private float selectedTargetCoeff;
    private float timeUniversalsOff;
    private float timeUniversalsOn;
    private float tracerVelocity;
    private String type;

    private HashMap<String, Float> armor;
    private List<Float> armourCas;
    private List<Float> armourCit;
    private List<Float> armourDeck;
    private List<Float> armourExtremities;
    private Float backwardPowerCoef;
    private String boilerExplosionPostDeath;
    private String buoyancy;
    private List<List> burnNodes;
    private List<Float> floodParams;
    private String deathBubblesEffect;
    private String deathDetonateEffect;
    private String deathFaultEffect;
    private String deathFireEffect;
    private String deathFoamEffect;
    private String deathFountainsEffect;
    private String deathFumingEffect;
    private String deathPlaneEffect;
    private String deathPostFireEffect;
    private String deathShellEffect;
    private String deathTorpedoEffect;
    private float deckHeight;
    private String defaultCamouflage;
    private float draft;


    private HashMap<String, GameParamsValues> shipModules = new HashMap<>();
    @JsonAnySetter
    public void setShipModules(String name, Object value)
    {
        shipModules.put(name, mapper.convertValue(value, GameParamsValues.class));
    }

    @JsonProperty(value = "ShipAbilities")
    private ShipAbilities ShipAbilities;
    @JsonProperty(value = "ShipModernization")
    private ShipModernization ShipModernization;
    @JsonProperty(value = "ShipUpgradeInfo")
    private ShipUpgradeInfo ShipUpgradeInfo;
    private WoWSSSC.model.gameparams.Values.BattleLevel battleLevel;
    private boolean canEquipCamouflage;
    private String defaultCrew;
    private float flagsScale;
    private String group;
    private WoWSSSC.model.gameparams.Values.GroupCustom groupCustom;
    private long id;
    private String index;
    private boolean isPaperShip;
    private int level;
    private int maxEquippedFlags;
    private String name;
    private float nationFlagScale;
    private String nativePermoflage;
    private String peculiarity;
//    private HashSet<String> peculiarityEffects;
    private String peculiarityFlag;
//    private HashSet<String> peculiarityModels;
    private List<String> permoflages;
    private WoWSSSC.model.gameparams.Values.TypeInfo typeinfo;
    private String unpeculiarShip;
    private int weight;
}
