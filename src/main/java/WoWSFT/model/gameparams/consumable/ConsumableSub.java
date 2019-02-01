package WoWSFT.model.gameparams.consumable;

import WoWSFT.config.WoWSFT;
import WoWSFT.utils.CommonUtils;
import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.Data;

@Data
@WoWSFT
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
    private String livePointEffect; //
    private int numConsumables;
    private String planeType;
    private float radius;
    private float regenerationHPSpeed;
    private float regenerationRate;
    private float reloadTime;
    private String spawnEffect; //
    private String spawnPointEffect; //
    private float startDelayTime;
    private float timeDelayAttack;
    private float timeToHeaven;
    private float timeToTryingCatch;
    private float timeWaitDelayAttack;
    private float torpedoReloadTime;
    private float workTime;

    @JsonGetter
    public float getDistShipReal()
    {
        return CommonUtils.getDistCoefWG(distShip);
    }

    @JsonGetter
    public float getDistTorpedoReal()
    {
        return CommonUtils.getDistCoefWG(distTorpedo);
    }
}
