package WoWSSSC.model.gameparams.ShipComponents.Artillery;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Aesis on 2017-05-09.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class APShell
{
    private float alphaDamage;
    private float alphaPiercingHE;
    private String ammoType;
    private float bulletAirDrag;
    private float bulletAlwaysRicochetAt;
    private boolean bulletCap;
    private float bulletCapNormalizeMaxAngle;
    private float bulletDetonator;
    private float bulletDetonatorSpread;
    private float bulletDetonatorThreshold;
    private float bulletDiametr;
    private float bulletKrupp;
    private float bulletMass;
    private float bulletPenetrationSpread;
    private float bulletRicochetAt;
    private float bulletSpeed;
    private float bulletUnderwaterDistFactor;
    private float bulletUnderwaterPenetrationFactor;
    private float bulletWaterDrag;
    private float burnProb;
    private int costCR;
    private float damage;
    private float directDamage;
    private long id;
    private String name;
    private float shellLength;

    private LinkedHashMap<Float, Float> penetration;
    private float penetrationAtFive;
    private float penetrationAtTen;
    private float penetrationAtFifteen;

    public void setPenetration(LinkedHashMap<Float, Float> penetration)
    {
        this.penetration = penetration;

        float fiveOne = 0f;
        float fiveTwo = 0f;
        float tenOne = 0f;
        float tenTwo = 0f;
        float fifteenOne = 0f;
        float fifteenTwo = 0f;

        for (Map.Entry<Float, Float> entry : this.penetration.entrySet())
        {
            float tempFloat = entry.getKey();

            if (tempFloat < 5000f)
            {
                if (fiveOne < tempFloat)
                {
                    fiveOne = tempFloat;
                }
            }
            else if (tempFloat >= 5000f && tempFloat < 10000f)
            {
                if (fiveTwo == 0f)
                {
                    fiveTwo = tempFloat;
                }

                if (tenOne < tempFloat)
                {
                    tenOne = tempFloat;
                }
            }
            else if (tempFloat >= 10000f && tempFloat < 15000f)
            {
                if (tenTwo == 0f)
                {
                    tenTwo = tempFloat;
                }

                if (fifteenOne < tempFloat)
                {
                    fifteenOne = tempFloat;
                }
            }
            else if (tempFloat >= 15000f)
            {
                if (fifteenTwo == 0f)
                {
                    fifteenTwo = tempFloat;
                }
            }
        }

        if (fiveOne != 0f && fiveTwo != 0f)
        {
            penetrationAtFive = setPenetrationAtDistance(penetration.get(fiveOne), penetration.get(fiveTwo));
        }

        if (tenOne != 0f && tenTwo != 0f)
        {
            penetrationAtTen = setPenetrationAtDistance(penetration.get(tenOne), penetration.get(tenTwo));
        }

        if (fifteenOne != 0f && fifteenTwo != 0f)
        {
            penetrationAtFifteen = setPenetrationAtDistance(penetration.get(fifteenOne), penetration.get(fifteenTwo));
        }
    }

    private float setPenetrationAtDistance(float y1, float y2)
    {
        return (y1 + y2) / 2f;
    }
}
