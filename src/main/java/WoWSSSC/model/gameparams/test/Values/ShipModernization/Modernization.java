package WoWSSSC.model.gameparams.test.Values.ShipModernization;

import WoWSSSC.model.gameparams.TypeInfo;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import java.util.List;

/**
 * Created by Aesis on 2017-05-23.
 */
@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class Modernization
{
    private float AAAura;
    private float AAMaxDist;
    private float ADMaxHP;
    private float GMCritProb;
    private float GMIdealRadius;
    private float GMMaxDist;
    private float GMMaxHP;
    private float GMRepairTime;
    private float GMRotationSpeed;
    private float GMShotDelay;
    private float GMSigmaCount;
    private float GSIdealRadius;
    private float GSMaxDist;
    private float GSMaxHP;
    private float GSShotDelay;
    private float GSSigmaCount;
    private float GTCritProb;
    private float GTMaxHP;
    private float GTRepairTime;
    private float GTRotationSpeed;
    private float GTShotDelay;
    private float PMCritProb;
    private float PMDetonationProb;
    private float PMRepairTime;
    private float SGCritProb;
    private float SGRepairTime;
    private float SGRudderTime;
    private float airDefenseDispWorkTime;
    private float airplanesAmmoCount;
    private float airplanesAntiAirAura;
    private float airplanesBomberVitalityTime;
    private float airplanesFighterVitalityTime;
    private float airplanesPrepareTime;
    private float airplanesSpeed;
    private float burnProb;
    private float burnTime;
    private int costCR;
    private float costGold;
    private float crashCrewWorkTime;
    private float engineBackwardForsageMaxSpeed;
    private float engineBackwardForsagePower;
    private float engineBackwardUpTime;
    private float engineCritProb;
    private float engineForwardForsageMaxSpeed;
    private float engineForwardForsagePower;
    private float engineForwardUpTime;
    private float engineRepairTime;
    private float floodProb;
    private float floodTime;
    private long id;
    private String index;
    private String name;
    private float rlsSearchWorkTime;
    private float scoutWorkTime;
    private float shootShift;
    private float smokeGeneratorLifeTime;
    private float smokeGeneratorWorkTime;
    private float sonarSearchWorkTime;
    private float speedBoosterWorkTime;
    private float type;
    private TypeInfo typeinfo;
    private float visibilityDistCoeff;
    private float visionDistCoeff;
    private float visionTorpedoCoeff;
    private float visionXRayShipCoeff;
    
    private float trigger1SearchWorkTime;
    private float trigger2SearchWorkTime;
    private float trigger3SearchWorkTime;
    private float trigger4SearchWorkTime;
    private float trigger5SearchWorkTime;
    private float trigger6SearchWorkTime;

    private String upgradeSlot;
    private int tempCR;

    private List<String> excludes;
    private List<String> group;
    private List<String> nation;
    private List<Integer> shiplevel;
    private List<String> ships;
    private List<String> shiptype;
    private int slot;
}