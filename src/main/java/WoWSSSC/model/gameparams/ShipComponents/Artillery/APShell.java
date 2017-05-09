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

        float fiveOne = 1000;
        float fiveTwo = -1000;
        float tenOne = 1000;
        float tenTwo = -1000;
        float fifteenOne = 1000;
        float fifteenTwo = -1000;

        for (Map.Entry<Float, Float> entry : penetration.entrySet())
        {
            float diffFive = 5000 - entry.getKey();
            if (diffFive >= 0 && diffFive < fiveOne)
            {
                fiveOne = entry.getKey();
            }
            else if (diffFive <= 0 && diffFive > fiveTwo)
            {
                fiveTwo = entry.getKey();
            }
            
            float diffTen = 10000 - entry.getKey();
            if (diffTen >= 0 && diffTen < tenOne)
            {
                tenOne = entry.getKey();
            }
            else if (diffTen <= 0 && diffTen > tenTwo)
            {
                tenTwo = entry.getKey();
            }

            float diffFifteen = 15000 - entry.getKey();
            if (diffFifteen >= 0 && diffFifteen < fifteenOne)
            {
                fifteenOne = entry.getKey();
            }
            else if (diffFifteen <= 0 && diffFifteen > fifteenTwo)
            {
                fifteenTwo = entry.getKey();
            }
        }

        if (penetration.get(fiveOne) != null && fiveOne > 4000 && penetration.get(fiveTwo) != null && fiveTwo < 6000)
        {
            penetrationAtFive = setPenetrationAtDistance(fiveOne, penetration.get(fiveOne), fiveTwo, penetration.get(fiveTwo), 5000);
        }
        if (penetration.get(tenOne) != null && tenOne > 9000 && penetration.get(tenTwo) != null && tenTwo < 11000)
        {
            penetrationAtTen = setPenetrationAtDistance(tenOne, penetration.get(tenOne), tenTwo, penetration.get(tenTwo), 5000);
        }
        if (penetration.get(fifteenOne) != null && fifteenOne > 14000 && penetration.get(fifteenTwo) != null && fifteenTwo < 16000)
        {
            penetrationAtFifteen = setPenetrationAtDistance(fifteenOne, penetration.get(fifteenOne), fifteenTwo, penetration.get(fifteenTwo), 5000);
        }
    }

    private float setPenetrationAtDistance(float x1, float y1, float x2, float y2, float target)
    {
        float m = (y2 - y1) / (x2 - x1);
        float b = y1 - m * x1;

        return m * target + b;
    }
}
