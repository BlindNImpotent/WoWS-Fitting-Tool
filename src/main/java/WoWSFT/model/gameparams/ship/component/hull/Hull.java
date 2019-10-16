package WoWSFT.model.gameparams.ship.component.hull;

import WoWSFT.config.WoWSFT;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

import java.math.BigDecimal;
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
    private float burnProb;
    private float burnDamage;
    private float burnTime;
    private float burnSize;
    private float deckHeight;
    private float deepwaterVisibilityCoeff;
    private float dockYOffset;
    private float draft;
    private float enginePower;
    private List<List<Float>> floodNodes;
    private float floodProb;
    private float floodProtection;
    private float floodDamage;
    private float floodTime;
    private float floodSize;
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

    @JsonInclude
    private int burnSizeSkill = 4;

    @JsonSetter
    public void setFloodNodes(List<List<Float>> floodNodes)
    {
        this.floodNodes = floodNodes;
        this.floodProb = floodNodes.get(0).get(0);
        this.floodProtection = 100f - (floodNodes.get(0).get(0) * 3.0f * 100f);
        this.floodDamage = floodNodes.get(0).get(1);
        this.floodTime = floodNodes.get(0).get(2);
        this.floodSize = floodNodes.size();
    }

    @JsonSetter
    public void setBurnNodes(List<List<Object>> burnNodes)
    {
        this.burnNodes = burnNodes;
        this.burnProb = new BigDecimal((double) burnNodes.get(0).get(0)).floatValue();
        this.burnDamage = new BigDecimal((double) burnNodes.get(0).get(1)).floatValue();
        this.burnTime = new BigDecimal((double) burnNodes.get(0).get(2)).floatValue();
        this.burnSize = burnNodes.size();
    }
}
