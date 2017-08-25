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
    private float penetrationAtTwenty;

    public void setPenetration(LinkedHashMap<Float, Float> penetration)
    {
        this.penetration = penetration;

        float fiveOne = 0f;
        float fiveTwo = 0f;
        float tenOne = 0f;
        float tenTwo = 0f;
        float fifteenOne = 0f;
        float fifteenTwo = 0f;
        float twentyOne = 0f;
        float twentyTwo = 0f;

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
            else if (tempFloat >= 15000f && tempFloat < 20000f)
            {
                if (fifteenTwo == 0f)
                {
                    fifteenTwo = tempFloat;
                }

                if (twentyOne < tempFloat)
                {
                    twentyOne = tempFloat;
                }
            }
            else if (tempFloat >= 20000f)
            {
                if (twentyTwo == 0f)
                {
                    twentyTwo = tempFloat;
                }
            }
        }

        if (fiveOne != 0f && fiveTwo != 0f)
        {
            penetrationAtFive = setPenetrationAtDistance(fiveOne, penetration.get(fiveOne), fiveTwo, penetration.get(fiveTwo), 5000f);
        }

        if (tenOne != 0f && tenTwo != 0f)
        {
            penetrationAtTen = setPenetrationAtDistance(tenOne, penetration.get(tenOne), tenTwo, penetration.get(tenTwo), 10000f);
        }

        if (fifteenOne != 0f && fifteenTwo != 0f)
        {
            penetrationAtFifteen = setPenetrationAtDistance(fifteenOne, penetration.get(fifteenOne), fifteenTwo, penetration.get(fifteenTwo), 15000f);
        }

        if (twentyOne != 0f && twentyTwo != 0f)
        {
            penetrationAtTwenty = setPenetrationAtDistance(twentyOne, penetration.get(twentyOne), twentyTwo, penetration.get(twentyTwo), 20000f);
        }
    }

    private float setPenetrationAtDistance(float x1, float y1, float x2, float y2, float mid)
    {
        float a = (y2 - y1) / (x2 - x1);
        float c = y1 - (a * x1);

        return a * mid + c;
    }
}
