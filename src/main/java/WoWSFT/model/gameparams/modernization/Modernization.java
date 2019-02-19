package WoWSFT.model.gameparams.modernization;

import WoWSFT.config.WoWSFT;
import WoWSFT.model.gameparams.TypeInfo;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Data
@WoWSFT
public class Modernization
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
