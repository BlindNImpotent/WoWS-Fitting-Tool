package WoWSSSC.model.gameparams.ShipComponents.Artillery;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Aesis on 2017-05-09.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArtyShell
{
    private float alphaDamage;
    private float alphaPiercingHE;
    private float alphaPiercingHEReal;
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
    private LinkedHashMap<Float, Float> flightTime;
    private LinkedHashMap<Float, Float> impact;
    private LinkedHashMap<Float, Float> vertPlus;
    private LinkedHashMap<Float, Float> vertMinus;
    private List<Float> distanceList;
    private float penetrationAtFive;
    private float penetrationAtTen;
    private float penetrationAtFifteen;
    private float penetrationAtTwenty;
    private float penetrationAtMax;
    private float flightTimeAtFive;
    private float flightTimeAtTen;
    private float flightTimeAtFifteen;
    private float flightTimeAtTwenty;
    private float flightTimeAtMax;
    private float impactAtFive;
    private float impactAtTen;
    private float impactAtFifteen;
    private float impactAtTwenty;
    private float impactAtMax;
    private float vertPlusAtFive;
    private float vertPlusAtTen;
    private float vertPlusAtFifteen;
    private float vertPlusAtTwenty;
    private float vertPlusAtMax;
    private float vertMinusAtFive;
    private float vertMinusAtTen;
    private float vertMinusAtFifteen;
    private float vertMinusAtTwenty;
    private float vertMinusAtMax;

    private int penetrationIFHE;

    public void setAlphaPiercingHE(float alphaPiercingHE)
    {
        this.alphaPiercingHE = alphaPiercingHE;
        this.alphaPiercingHEReal = alphaPiercingHE - 1f;
    }

    public void setMaxDist(float maxDist)
    {
        float maxOne = 0f;
        float maxTwo = 0f;

        for (Map.Entry<Float, Float> entry : this.flightTime.entrySet())
        {
            float tempFloat = entry.getKey();

            if (tempFloat < maxDist)
            {
                if (maxOne < tempFloat)
                {
                    maxOne = tempFloat;
                }
            }
            else if (tempFloat >= maxDist)
            {
                if (maxTwo == 0f)
                {
                    maxTwo = tempFloat;
                }
            }
        }

        if (maxOne != 0f && maxTwo != 0f)
        {
            if (penetration != null)
            {
                penetrationAtMax = setMiddleAtDistance(maxOne, penetration.get(maxOne), maxTwo, penetration.get(maxTwo), maxDist);
                impactAtMax = setMiddleAtDistance(maxOne, impact.get(maxOne), maxTwo, impact.get(maxTwo), maxDist);
            }
            flightTimeAtMax = setMiddleAtDistance(maxOne, flightTime.get(maxOne), maxTwo, flightTime.get(maxTwo), maxDist);

            if (vertPlus != null && vertMinus != null) {
                vertPlusAtMax = setMiddleAtDistance(maxOne, vertPlus.get(maxOne), maxTwo, vertPlus.get(maxTwo), maxDist);
                vertMinusAtMax = setMiddleAtDistance(maxOne, vertMinus.get(maxOne), maxTwo, vertMinus.get(maxTwo), maxDist);
            }
        }
    }

    public void setAPShell(LinkedHashMap<Float, Float> penetration, LinkedHashMap<Float, Float> flightTime, LinkedHashMap<Float, Float> impact,
                           LinkedHashMap<Float, Float> vertPlus, LinkedHashMap<Float, Float> vertMinus,
                           List<Float> distanceList)
    {
        this.penetration = penetration;
        this.flightTime = flightTime;
        this.impact = impact;
        this.vertPlus = vertPlus;
        this.vertMinus = vertMinus;
        this.distanceList = distanceList;

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
            penetrationAtFive = setMiddleAtDistance(fiveOne, penetration.get(fiveOne), fiveTwo, penetration.get(fiveTwo), 5000f);
            flightTimeAtFive = setMiddleAtDistance(fiveOne, flightTime.get(fiveOne), fiveTwo, flightTime.get(fiveTwo), 5000f);
            impactAtFive = setMiddleAtDistance(fiveOne, impact.get(fiveOne), fiveTwo, impact.get(fiveTwo), 5000f);
            vertPlusAtFive = setMiddleAtDistance(fiveOne, vertPlus.get(fiveOne), fiveTwo, vertPlus.get(fiveTwo), 5000f);
            vertMinusAtFive = setMiddleAtDistance(fiveOne, vertMinus.get(fiveOne), fiveTwo, vertMinus.get(fiveTwo), 5000f);
        }

        if (tenOne != 0f && tenTwo != 0f)
        {
            penetrationAtTen = setMiddleAtDistance(tenOne, penetration.get(tenOne), tenTwo, penetration.get(tenTwo), 10000f);
            flightTimeAtTen = setMiddleAtDistance(tenOne, flightTime.get(tenOne), tenTwo, flightTime.get(tenTwo), 10000f);
            impactAtTen = setMiddleAtDistance(tenOne, impact.get(tenOne), tenTwo, impact.get(tenTwo), 10000f);
            vertPlusAtTen = setMiddleAtDistance(tenOne, vertPlus.get(tenOne), tenTwo, vertPlus.get(tenTwo), 10000f);
            vertMinusAtTen = setMiddleAtDistance(tenOne, vertMinus.get(tenOne), tenTwo, vertMinus.get(tenTwo), 10000f);
        }

        if (fifteenOne != 0f && fifteenTwo != 0f)
        {
            penetrationAtFifteen = setMiddleAtDistance(fifteenOne, penetration.get(fifteenOne), fifteenTwo, penetration.get(fifteenTwo), 15000f);
            flightTimeAtFifteen = setMiddleAtDistance(fifteenOne, flightTime.get(fifteenOne), fifteenTwo, flightTime.get(fifteenTwo), 15000f);
            impactAtFifteen = setMiddleAtDistance(fifteenOne, impact.get(fifteenOne), fifteenTwo, impact.get(fifteenTwo), 15000f);
            vertPlusAtFifteen = setMiddleAtDistance(fifteenOne, vertPlus.get(fifteenOne), fifteenTwo, vertPlus.get(fifteenTwo), 15000f);
            vertMinusAtFifteen = setMiddleAtDistance(fifteenOne, vertMinus.get(fifteenOne), fifteenTwo, vertMinus.get(fifteenTwo), 15000f);
        }

        if (twentyOne != 0f && twentyTwo != 0f)
        {
            penetrationAtTwenty = setMiddleAtDistance(twentyOne, penetration.get(twentyOne), twentyTwo, penetration.get(twentyTwo), 20000f);
            flightTimeAtTwenty = setMiddleAtDistance(twentyOne, flightTime.get(twentyOne), twentyTwo, flightTime.get(twentyTwo), 20000f);
            impactAtTwenty = setMiddleAtDistance(twentyOne, impact.get(twentyOne), twentyTwo, impact.get(twentyTwo), 20000f);
            vertPlusAtTwenty = setMiddleAtDistance(twentyOne, vertPlus.get(twentyOne), twentyTwo, vertPlus.get(twentyTwo), 20000f);
            vertMinusAtTwenty = setMiddleAtDistance(twentyOne, vertMinus.get(twentyOne), twentyTwo, vertMinus.get(twentyTwo), 20000f);
        }
    }

    public void setHEShell(LinkedHashMap<Float, Float> flightTime)
    {
        this.flightTime = flightTime;

        float fiveOne = 0f;
        float fiveTwo = 0f;
        float tenOne = 0f;
        float tenTwo = 0f;
        float fifteenOne = 0f;
        float fifteenTwo = 0f;
        float twentyOne = 0f;
        float twentyTwo = 0f;

        for (Map.Entry<Float, Float> entry : this.flightTime.entrySet())
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
            flightTimeAtFive = setMiddleAtDistance(fiveOne, flightTime.get(fiveOne), fiveTwo, flightTime.get(fiveTwo), 5000f);
        }

        if (tenOne != 0f && tenTwo != 0f)
        {
            flightTimeAtTen = setMiddleAtDistance(tenOne, flightTime.get(tenOne), tenTwo, flightTime.get(tenTwo), 10000f);
        }

        if (fifteenOne != 0f && fifteenTwo != 0f)
        {
            flightTimeAtFifteen = setMiddleAtDistance(fifteenOne, flightTime.get(fifteenOne), fifteenTwo, flightTime.get(fifteenTwo), 15000f);
        }

        if (twentyOne != 0f && twentyTwo != 0f)
        {
            flightTimeAtTwenty = setMiddleAtDistance(twentyOne, flightTime.get(twentyOne), twentyTwo, flightTime.get(twentyTwo), 20000f);
        }
    }

    private float setMiddleAtDistance(Float x1, Float y1, Float x2, Float y2, Float mid)
    {
        if (x1 == null || x2 == null || y1 == null || y2 == null) {
            return 0f;
        }

        float a = (y2 - y1) / (x2 - x1);
        float c = y1 - (a * x1);

        return a * mid + c;
    }
}
