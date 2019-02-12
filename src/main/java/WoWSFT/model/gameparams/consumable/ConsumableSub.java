package WoWSFT.model.gameparams.consumable;

import WoWSFT.config.WoWSFT;
import WoWSFT.utils.CommonUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

import java.util.LinkedHashMap;

@Data
@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsumableSub
{
    private float activationDelay;
    private float areaDamageMultiplier;
    private float artilleryDistCoeff;
    private float backwardEngineForsag;
    private float backwardEngineForsagMaxSpeed;
    private float boostCoeff;
    private float bubbleDamageMultiplier;
    private float climbAngle;
    private String consumableType;
    private float criticalChance;
    private float distanceToKill;
    private float distShip;
    private float distTorpedo;
    private float dogFightTime;
    private String fightersName;
    private float fightersNum;
    private float forwardEngineForsag;
    private float forwardEngineForsagMaxSpeed;
    private String group;
    private float height;
    private float lifeTime;
    private float numConsumables;
    private String planeType;
    private float radius;
    private float regenerationHPSpeed;
    private float regenerationRate;
    private float reloadTime;
    private float startDelayTime;
    private float timeDelayAttack;
    private float timeToHeaven;
    private float timeToTryingCatch;
    private float timeWaitDelayAttack;
    private float torpedoReloadTime;
    private float workTime;

    private LinkedHashMap<String, String> bonus = new LinkedHashMap<>();

    public float getDistanceToKill()
    {
        return CommonUtils.getDistCoefWG(distanceToKill);
    }

    public float getHeight()
    {
        return CommonUtils.getDistCoefWG(height);
    }

    public float getRadius()
    {
        return CommonUtils.getDistCoefWG(radius);
    }

    public float getDistShip()
    {
        return CommonUtils.getDistCoefWG(distShip);
    }

    public float setDistTorpedo()
    {
        return CommonUtils.getDistCoefWG(distTorpedo);
    }
}
