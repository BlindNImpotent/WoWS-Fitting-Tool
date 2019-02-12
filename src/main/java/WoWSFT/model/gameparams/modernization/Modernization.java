package WoWSFT.model.gameparams.modernization;

import WoWSFT.config.WoWSFT;
import WoWSFT.model.gameparams.TypeInfo;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Data
@WoWSFT
public class Modernization
{
    private float AAExtraBubbles;
    private float AANearDamage = 1f;
    private float AAOuterDamage = 1f;
    private float ADMaxHP = 1f;
    private float GMCritProb = 1f;
    private float GMIdealRadius = 1f;
    private float GMMaxDist = 1f;
    private float GMMaxHP = 1f;
    private float GMRepairTime = 1f;
    private float GMRotationSpeed = 1f;
    private float GMShotDelay = 1f;
    private float GMSigmaCount = 1f;
    private float GSIdealRadius = 1f;
    private float GSMaxDist = 1f;
    private float GSMaxHP = 1f;
    private float GSShotDelay = 1f;
    private float GSSigmaCount = 1f;
    private float GTCritProb = 1f;
    private float GTMaxHP = 1f;
    private float GTRepairTime = 1f;
    private float GTRotationSpeed = 1f;
    private float GTShotDelay = 1f;
    private float PMCritProb = 1f;
    private float PMDetonationProb = 1f;
    private float PMRepairTime = 1f;
    private float SGCritProb = 1f;
    private float SGRepairTime = 1f;
    private float SGRudderTime = 1f;
    private float airDefenseDispWorkTime = 1f;
    private float airplanesDiveBombersHealth = 1f;
    private float airplanesEmptyReturnSpeed = 1f;
    private float airplanesExtraHangarSize;
    private float airplanesFighterAimingTime;
    private float airplanesFightersHealth = 1f;
    private float airplanesForsageDuration = 1f;
    private float airplanesHealth = 1f;
    private float airplanesSpawnTime = 1f;
    private float airplanesSpeed = 1f;
    private float airplanesTorpedoAimingTime;
    private float airplanesTorpedoBombersHealth = 1f;
    private float burnProb = 1f;
    private float burnTime = 1f;
    private int costCR;
    private int costGold;
    private float crashCrewWorkTime = 1f;
    private float engineBackwardForsageMaxSpeed = 1f;
    private float engineBackwardForsagePower = 1f;
    private float engineBackwardUpTime = 1f;
    private float engineCritProb = 1f;
    private float engineForwardForsageMaxSpeed = 1f;
    private float engineForwardForsagePower = 1f;
    private float engineForwardUpTime = 1f;
    private float engineRepairTime = 1f;
    private List<String> excludes = new ArrayList<>();
    private float floodProb = 1f;
    private float floodTime = 1f;
    private List<String> group = new ArrayList<>();
    private long id;
    private String index;
    private float invulnerableWorkTime = 1f;
    private String name;
    private List<String> nation = new ArrayList<>();
    private float rlsSearchWorkTime = 1f;
    private float scoutWorkTime = 1f;
    private List<Integer> shiplevel = new ArrayList<>();
    private List<String> ships = new ArrayList<>();
    private List<String> shiptype = new ArrayList<>();
    private float shootShift = 1f;
    @JsonInclude
    private int slot;
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
    private int type;
    private TypeInfo typeinfo;
    private float visibilityDistCoeff = 1f;
    private float visionDistCoeff = 1f;
    private float visionTorpedoCoeff = 1f;
    private float visionXRayShipCoeff = 1f;

    private String fullName;
    private String image;

    private LinkedHashMap<String, String> bonus = new LinkedHashMap<>();
    private String description = "";

    public String getImage()
    {
        return "https://glossary-na-static.gcdn.co/icons/wows/current/modernization/icon_modernization_" + name + ".png";
    }
}
