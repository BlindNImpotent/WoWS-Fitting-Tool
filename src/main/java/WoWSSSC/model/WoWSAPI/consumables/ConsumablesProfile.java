package WoWSSSC.model.WoWSAPI.consumables;

import WoWSSSC.model.WoWSAPI.consumables.factors.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

/**
 * Created by Aesis on 2017. 4. 20..
 */
@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class ConsumablesProfile
{
    private AAAura AAAura;
    private AAMaxDist AAMaxDist;
    private AAPassiveAura AAPassiveAura;
    private AbilReloadTimeFactor abilReloadTimeFactor;
    private ADMaxHP ADMaxHP;
    private AfterBattleRepair afterBattleRepair;
    private AirDefenseDispWorkTime airDefenseDispWorkTime;
    private AirplanesAmmoCount airplanesAmmoCount;
    private AirplanesAntiAirAura airplanesAntiAirAura;
    private AirplanesBomberVitalityTime airplanesBomberVitalityTime;
    private AirplanesFighterVitalityTime airplanesFighterVitalityTime;
    private AirplanesPrepareTime airplanesPrepareTime;
    private AirplanesSpeed airplanesSpeed;
    private BurnChanceFactorBig burnChanceFactorBig;
    private BurnChanceFactorSmall burnChanceFactorSmall;
    private BurnProb burnProb;
    private BurnTime burnTime;
    private CollisionDamageApply collisionDamageApply;
    private CollisionDamageNerf collisionDamageNerf;
    private CrashCrewWorkTime crashCrewWorkTime;
    private CreditsFactor creditsFactor;
    private CrewExpFactor crewExpFactor;
    private EngineCritProb engineCritProb;
    private EngineForwardForsageMaxSpeed engineForwardForsageMaxSpeed;
    private EngineForwardUpTime engineForwardUpTime;
    private EngineRepairTime engineRepairTime;
    private ExpFactor expFactor;
    private FloodChanceFactor floodChanceFactor;
    private FloodProb floodProb;
    private FloodTime floodTime;
    private FreeExpFactor freeExpFactor;
    private GMCritProb GMCritProb;
    private GMIdealRadius GMIdealRadius;
    private GMMaxDist GMMaxDist;
    private GMMaxHP GMMaxHP;
    private GMRepairTime GMRepairTime;
    private GMRotationSpeed GMRotationSpeed;
    private GMShotDelay GMShotDelay;
    private GSIdealRadius GSIdealRadius;
    private GSMaxDist GSMaxDist;
    private GSMaxHP GSMaxHP;
    private GSShotDelay GSShotDelay;
    private GTCritProb GTCritProb;
    private GTMaxHP GTMaxHP;
    private GTRepairTime GTRepairTime;
    private GTRotationSpeed GTRotationSpeed;
    private GTShotDelay GTShotDelay;
    private PMDetonationProb PMDetonationProb;
    private RegenerationHPSpeed regenerationHPSpeed;
    private RLSSearchWorkTime rlsSearchWorkTime;
    private ShootShift shootShift;
    private ScoutWorkTime scoutWorkTime;
    private SGCritProb SGCritProb;
    private SGRepairTime SGRepairTime;
    private SGRudderTime SGRudderTime;
    private SmokeGeneratorLifeTime smokeGeneratorLifeTime;
    private SmokeGeneratorWorkTime smokeGeneratorWorkTime;
    private SonarSearchWorkTime sonarSearchWorkTime;
    private SpeedBoosterWorkTime speedBoosterWorkTime;
    private SpeedCoef speedCoef;
    private VisibilityDistCoeff visibilityDistCoeff;
    private VisibilityFactor visibilityFactor;
    private VisionDistCoeff visionDistCoeff;
    private VisionTorpedoCoeff visionTorpedoCoeff;
    private VisionXRayShipCoeff visionXRayShipCoeff;
}
