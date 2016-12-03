package WoWSSSC.model.gameparams;

import WoWSSSC.model.gameparams.Ship.BattleLevel;
import WoWSSSC.model.gameparams.Ship.GroupCustom;
import WoWSSSC.model.gameparams.Ship.ShipAbilities.ShipAbilities;
import WoWSSSC.model.gameparams.Ship.ShipModernization.ShipModernization;
import WoWSSSC.model.gameparams.Ship.ShipUpgradeInfo.ShipUpgradeInfo;
import WoWSSSC.model.gameparams.Ship.TypeInfo;
import com.fasterxml.jackson.annotation.*;
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
    private float launchPrepareTime;
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
    private List miscFilter;
    private boolean miscFilterMode;
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

    @JsonProperty(value = "ShipAbilities")
    private ShipAbilities ShipAbilities;
    @JsonProperty(value = "ShipModernization")
    private ShipModernization ShipModernization;
    @JsonProperty(value = "ShipUpgradeInfo")
    private ShipUpgradeInfo ShipUpgradeInfo;
    private BattleLevel battleLevel;
    private boolean canEquipCamouflage;
    private String defaultCrew;
    private float flagsScale;
    private String group;
    private GroupCustom groupCustom;
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
    private TypeInfo typeinfo;
    private String unpeculiarShip;
    private int weight;
}
