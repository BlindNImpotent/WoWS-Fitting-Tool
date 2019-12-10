package WoWSFT.model.gameparams.consumable;

import WoWSFT.config.WoWSFT;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;

@Data
@WoWSFT
public class ConsumableSub
{
    private double activationDelay;
    private List<String> affectedClasses;
    private double areaDamageMultiplier;
    private double artilleryDistCoeff;
    private double backwardEngineForsag;
    private double backwardEngineForsagMaxSpeed;
    private double boostCoeff;
    private double bubbleDamageMultiplier;
    private double climbAngle;
    private String consumableType;
    private double criticalChance;
    private double distanceToKill;
    private double distShip;
    private double distTorpedo;
    private double dogFightTime;
    private String fightersName;
    private double fightersNum;
    private double flyAwayTime;
    private double forwardEngineForsag;
    private double forwardEngineForsagMaxSpeed;
    private String group;
    private double height;
    private double lifeTime;
    private double numConsumables;
    private String planeType;
    private double radius;
    private double regenerationHPSpeed;
    private double regenerationRate;
    private double reloadTime;
    private double spawnBackwardShift;
    private double speedLimit;
    private double startDelayTime;
    private double timeDelayAttack;
    private double timeFromHeaven;
    private double timeToHeaven;
    private double timeToTryingCatch;
    private double timeWaitDelayAttack;
    private double torpedoReloadTime;
    private double workTime;

    // Effects. Ignore.
    private String livePointEffect;
    private String radarEffect;
    private String radarEffectForPlayer;
    private String spawnEffect;
    private String spawnPointEffect;
    private String waveEffect;

    private String descIDs;
    private String iconIDs;
    private String titleIDs;

    private LinkedHashMap<String, String> bonus = new LinkedHashMap<>();
}
