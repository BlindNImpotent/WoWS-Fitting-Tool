package WoWSFT.model.gameparams.commander;

import WoWSFT.config.WoWSFT;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;

@Data
@WoWSFT
public class Skill
{
    private int tier;
    @JsonInclude
    private int column;
    @JsonIgnore
    private int skillType;
    @JsonIgnore
    private boolean turnOffOnRetraining;

    private float diveBomber;
    private float fighter;
    private float torpedoBomber;
    private float radiusCoefficient;
    private float smallGunReloadCoefficient;
    private float smallGunRangeCoefficient;
    private float consumablePlaneSpeedCoefficient;
    private float reloadCoefficient;
    private int alertMinDistance;
    private float critTimeCoefficient;
    private float atbaIdealRadiusHi;
    private float atbaIdealRadiusLo;
    private float fightersAmmunitionCoefficient;
    private float fightersEfficiencyCoefficient;
    private float probabilityBonus;
    private float probabilityCoefficient;
    private float switchAmmoReloadCoef;

    private float chanceToSetOnFireBonus;
    private float chanceToSetOnFireBonusBig;
    private float chanceToSetOnFireBonusSmall;
    private float thresholdPenetrationCoefficient;
    private float thresholdPenetrationCoefficientBig;
    private float thresholdPenetrationCoefficientSmall;

    private float hpStep;
    private float timeStep;
    private float critRudderTimeCoefficient;
    private float bigGunBonus;
    private float smallGunBonus;
    private float critProbCoefficient;
    private float fightersSpeedCoefficient;
    private float fightersVisibCoefficient;
    private float fightersVitalCoefficient;
    private float burnTimePenalty;
    private float diveBombersPrepareCoefficient;
    private float fightersPrepareCoefficient;
    private float torpedoBombersPrepareCoefficient;
    private float vitalityCoefficient;
    private float fightersPassiveEfficiencyCoefficient;
    private int additionalConsumables;
    private int healthPerLevel;
    private float torpedoRangeCoefficient;
    private int torpedoSpeedBonus;
    private float rangeCoefficient;
    private float bomberCoefficient;
    private float launcherCoefficient;
    private float aircraftCarrierCoefficient;
    private float battleshipCoefficient;
    private float cruiserCoefficient;
    private float destroyerCoefficient;
    private float submarineCoefficient;
    private float squadronCoefficient;
    private float nearAuraDamageCoefficient;
    private float advancedOuterAuraDamageCoefficient;
    private int extraFighterCount;
    private float fighterLifeTimeCoefficient;
    private float hangarSizeBonus;
    private float planeSpawnTimeCoefficient;
    private float prioritySectorStrengthCoefficient;
    private float sectorSwitchDelayCoefficient;
    private float bombProbabilityBonus;
    private float rocketProbabilityBonus;
    private float flightSpeedCoefficient;
    private float forsageDurationCoefficient;
    private float squadronHealthStep;
    private float squadronSpeedStep;
    private float nearAuraDamageTakenCoefficient;
    private int planeHealthPerLevel;
    private float planeTorpedoRangeCoefficient;
    private int planeTorpedoSpeedBonus;
    private float planeRangeCoefficient;
    @JsonAlias("isEpic")
    private boolean epic;
    private String modifier;

    private LinkedHashMap<String, String> bonus = new LinkedHashMap<>();
    private String description = "";
    private String image;

    @JsonGetter
    public String getImage()
    {
        if (StringUtils.isEmpty(image)) {
            return "https://glossary-na-static.gcdn.co/icons/wows/current/crew_commander/perks/" + modifier + ".png";
        }
        return image;
    }
}
