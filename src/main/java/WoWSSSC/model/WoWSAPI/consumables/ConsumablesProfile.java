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
    private Default AAAura; //
    private Default AAMaxDist; //
    private Default AAPassiveAura; //
    private Default abilReloadTimeFactor; //
    private Default ADMaxHP; //
    private Default afterBattleRepair;
    private Default airDefenseDispWorkTime; //
    private Default airplanesAmmoCount;
    private Default airplanesAntiAirAura;
    private Default airplanesBomberVitalityTime;
    private Default airplanesFighterVitalityTime;
    private Default airplanesPrepareTime; //
    private Default airplanesSpeed; //
    private Default burnChanceFactorBig; //
    private Default burnChanceFactorSmall; //
    private Default burnProb;
    private Default burnTime; //
    private Default collisionDamageApply;
    private Default collisionDamageNerf;
    private Default crashCrewWorkTime; //
    private Default creditsFactor;
    private Default crewExpFactor;
    private Default engineCritProb;
    private Default engineForwardForsageMaxSpeed;
    private Default engineForwardUpTime;
    private Default engineRepairTime;
    private Default expFactor;
    private Default floodChanceFactor;
    private Default floodProb;
    private Default floodTime; //
    private Default freeExpFactor;
    private Default GMCritProb;
    private Default GMIdealRadius; //
    private Default GMMaxDist; //
    private Default GMMaxHP;
    private Default GMRepairTime;
    private Default GMRotationSpeed; //
    private Default GMShotDelay; //
    private Default GSIdealRadius;
    private Default GSMaxDist; //
    private Default GSMaxHP;
    private Default GSShotDelay; //
    private Default GTCritProb;
    private Default GTMaxHP;
    private Default GTRepairTime;
    private Default GTRotationSpeed;
    private Default GTShotDelay; //
    private Default PMDetonationProb;
    private Default regenerationHPSpeed;
    private Default rlsSearchWorkTime; //
    private Default shootShift;
    private Default scoutWorkTime;
    private Default SGCritProb;
    private Default SGRepairTime;
    private Default SGRudderTime; //
    private Default smokeGeneratorLifeTime;
    private Default smokeGeneratorWorkTime; //
    private Default sonarSearchWorkTime; //
    private Default speedBoosterWorkTime; //
    private Default speedCoef; //
    private Default visibilityDistCoeff; //
    private Default visibilityFactor;
    private Default visionDistCoeff;
    private Default visionTorpedoCoeff;
    private Default visionXRayShipCoeff;
}
