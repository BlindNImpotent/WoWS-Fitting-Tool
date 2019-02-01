package WoWSFT.model.gameparams.ship.component.hull;

import WoWSFT.config.WoWSFT;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
public class Hull
{
    private float backwardPowerCoef;
    private float baseUnderwaterPitchAngle;
    private float buoyancy;
    private float buoyancyRudderTime;
    private List<List<Object>> burnNodes;
    private float deckHeight;
    private float deepwaterVisibilityCoeff;
    private float dockYOffset;
    private float draft;
    private float enginePower;
    private List<Float> floodParams;
    private float health;
    @JsonAlias("isBlind")
    private boolean blind;
    private float mass;
    private float maxBuoyancyLevel;
    private float maxBuoyancySpeed;
    private float maxRudderAngle;
    private float maxSpeed;
    private int numOfParts;
    private float pushingMaxRudderAngle;
    private float pushingMinRudderAngle;
    private float regenerationHPSpeed;
    private float repairingCoeff;
    private float rollEffect;
    private float rudderPower;
    private float rudderTime;
    private float sideDragCoef;
    private List<Float> size;
    private float smokeScanRadius;
    private float speedCoef;
    private float tonnage;
    private float torpedoImpactMassImpulseCoeff;
    private float turningRadius;
    private float underwaterMaxRudderAngle;
    private float underwaterRollEffect;
    private float underwaterVisibilityCoeff;
    private float visibilityCoefATBA;
    private float visibilityCoefATBAByPlane;
    private float visibilityCoefFire;
    private float visibilityCoefFireByPlane;
    private float visibilityCoefGK;
    private float visibilityCoefGKByPlane;
    private float visibilityCoefGKInSmoke;
    private float visibilityCoeff;
    private float visibilityCoeffUnderwater;
    private float visibilityFactor;
    private float visibilityFactorByPlane;
    private float visibilityFactorInSmoke;
}
