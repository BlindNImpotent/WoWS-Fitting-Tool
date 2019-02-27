package WoWSFT.model.gameparams.consumable;

import WoWSFT.config.WoWSFT;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;

@Data
@WoWSFT
public class ConsumableSub
{
    private float activationDelay;
    private List<String> affectedClasses;
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

    // Effects. Ignore.
    private String livePointEffect;
    private String radarEffect;
    private String radarEffectForPlayer;
    private String spawnEffect;
    private String spawnPointEffect;
    private String waveEffect;

    private LinkedHashMap<String, String> bonus = new LinkedHashMap<>();
}
