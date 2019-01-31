package WoWSSSC.model.gameparams.Consumables;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Created by Aesis on 2017-04-19.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsumableType
{
    private String consumableType; //
    private int numConsumables; //
    private float reloadTime; //
    private float workTime; //
    private float regenerationHPSpeed; //
    private float auraCoeff;
    private float artilleryDistCoeff;
    private float activationDelay;
    private float height;
    private float lifeTime; //
    private float radius;
    private float startDelayTime;
    private float backwardEngineForsag;
    private float backwardEngineForsagMaxSpeed;
    private float boostCoeff;
    private float forwardEngineForsag;
    private float forwardEngineForsagMaxSpeed;
    private float distShip; //
    private float distTorpedo; //
    private float torpedoReloadTime;
    private float areaDamageMultiplier;
    private float bubbleDamageMultiplier;

    public float getBoostCoeffModified()
    {
        if ("speedBoosters".equalsIgnoreCase(consumableType)) {
            return boostCoeff + 1;
        }
        return boostCoeff;
    }

    public float getDistShipModified()
    {
        return distShip / (33f + (1f / 3f));
    }

    public float getDistTorpedoModified()
    {
        return distTorpedo / (33f + (1f / 3f));
    }


//    public void setBoostCoeff(float boostCoeff)
//    {
//        this.boostCoeff = 1 + boostCoeff;
//    }
//
//    public void setDistShip(float distShip)
//    {
//        this.distShip = distShip / (33 + (1 / 3));
//    }
//
//    public void setDistTorpedo(float distTorpedo)
//    {
//        this.distTorpedo = distTorpedo / (33 + (1 / 3));
//    }
}
