package WoWSFT.model.gameparams.ship.component.hull;

import WoWSFT.config.WoWSFT;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

import java.util.List;

@Data
@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
public class Hull
{
    private double backwardPowerCoef;
    private double baseUnderwaterPitchAngle;
    private double buoyancy;
    private double buoyancyRudderTime;
    private List<List<Object>> burnNodes;
    private double burnProb;
    private double burnDamage;
    private double burnTime;
    private double burnSize;
    private double deckHeight;
    private double deepwaterVisibilityCoeff;
    private double dockYOffset;
    private double draft;
    private double enginePower;
    private List<List<Double>> floodNodes;
    private double floodProb;
    private double floodProtection;
    private double floodDamage;
    private double floodTime;
    private double floodSize;
    private double health;
    @JsonAlias("isBlind")
    private boolean blind;
    private double mass;
    private double maxBuoyancyLevel;
    private double maxBuoyancySpeed;
    private double maxRudderAngle;
    private double maxSpeed;
    private int numOfParts;
    private double pushingMaxRudderAngle;
    private double pushingMinRudderAngle;
    private double regenerationHPSpeed;
    private double repairingCoeff;
    private double rollEffect;
    private double rudderPower;
    private double rudderTime;
    private double sideDragCoef;
    private List<Double> size;
    private double smokeScanRadius;
    private double speedCoef;
    private double tonnage;
    private double torpedoImpactMassImpulseCoeff;
    private double turningRadius;
    private double underwaterMaxRudderAngle;
    private double underwaterRollEffect;
    private double underwaterVisibilityCoeff;
    private double visibilityCoefATBA;
    private double visibilityCoefATBAByPlane;
    private double visibilityCoefFire;
    private double visibilityCoefFireByPlane;
    private double visibilityCoefGK;
    private double visibilityCoefGKByPlane;
    private double visibilityCoefGKInSmoke;
    private double visibilityCoeff;
    private double visibilityCoeffUnderwater;
    private double visibilityFactor;
    private double visibilityFactorByPlane;
    private double visibilityFactorInSmoke;

    @JsonInclude
    private int burnSizeSkill = 4;

    @JsonSetter
    public void setFloodNodes(List<List<Double>> floodNodes)
    {
        this.floodNodes = floodNodes;
        this.floodProb = floodNodes.get(0).get(0);
        this.floodProtection = 100.0 - (floodNodes.get(0).get(0) * 3.0 * 100.0);
        this.floodDamage = floodNodes.get(0).get(1);
        this.floodTime = floodNodes.get(0).get(2);
        this.floodSize = floodNodes.size();
    }

    @JsonSetter
    public void setBurnNodes(List<List<Object>> burnNodes)
    {
        this.burnNodes = burnNodes;
        this.burnProb = (double) burnNodes.get(0).get(0);
        this.burnDamage = (double) burnNodes.get(0).get(1);
        this.burnTime = (double) burnNodes.get(0).get(2);
        this.burnSize = burnNodes.size();
    }
}
