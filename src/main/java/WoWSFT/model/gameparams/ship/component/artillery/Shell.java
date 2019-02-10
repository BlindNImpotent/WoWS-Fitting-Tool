package WoWSFT.model.gameparams.ship.component.artillery;

import WoWSFT.model.gameparams.TypeInfo;
import WoWSFT.utils.PenetrationUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Shell
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
    private String index;
    private String name;
    private TypeInfo typeinfo;
    private boolean uwAbility;
    private float uwCritical;
    private float volume;
    private List<Float> waterRefractionReflectDeltaAngleInterval;

    private LinkedHashMap<String, Float> penetration;
    private LinkedHashMap<String, Float> flightTime;
    private LinkedHashMap<String, Float> impact;
    private LinkedHashMap<String, Float> launchAngle;
    private LinkedHashMap<String, Float> vertPlus;
    private LinkedHashMap<String, Float> vertMinus;
    private List<String> distanceList;
    private float minDistV;
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
        String maxOne = "0";
        String maxTwo = "";

        for (Map.Entry<String, Float> entry : this.flightTime.entrySet()) {
            float tempFloat = Float.parseFloat(entry.getKey());

            if (tempFloat < maxDist) {
                if (Float.parseFloat(maxOne) < tempFloat) {
                    maxOne = entry.getKey();
                }
            } else if (tempFloat >= maxDist) {
                if (StringUtils.isEmpty(maxTwo)) {
                    maxTwo = entry.getKey();
                }
            }
        }

        if (StringUtils.isNotEmpty(maxOne) && StringUtils.isNotEmpty(maxTwo)) {
            if (penetration != null) {
                penetrationAtMax = setMiddleAtDistance(Float.parseFloat(maxOne), penetration.get(maxOne), Float.parseFloat(maxTwo), penetration.get(maxTwo), maxDist);
                impactAtMax = setMiddleAtDistance(Float.parseFloat(maxOne), impact.get(maxOne), Float.parseFloat(maxTwo), impact.get(maxTwo), maxDist);
            }
            flightTimeAtMax = setMiddleAtDistance(Float.parseFloat(maxOne), flightTime.get(maxOne), Float.parseFloat(maxTwo), flightTime.get(maxTwo), maxDist);

//            if (vertPlus != null && vertMinus != null) {
//                vertPlusAtMax = setMiddleAtDistance(maxOne, vertPlus.get(maxOne), maxTwo, vertPlus.get(maxTwo), maxDist);
//                vertMinusAtMax = setMiddleAtDistance(maxOne, vertMinus.get(maxOne), maxTwo, vertMinus.get(maxTwo), maxDist);
//            }
        }
    }

    public void setShell(LinkedHashMap<String, Float> flightTime,
                         LinkedHashMap<String, Float> penetration, LinkedHashMap<String, Float> impact, List<String> distanceList, LinkedHashMap<String, Float> launchAngle,
                         float minDistV, boolean apShell)
    {
        this.penetration = penetration;
        this.flightTime = flightTime;
        this.impact = impact;
        this.distanceList = distanceList;
        this.launchAngle = launchAngle;
        this.minDistV = minDistV;

        String fiveOne = "0";
        String fiveTwo = "";
        String tenOne = "0";
        String tenTwo = "";
        String fifteenOne = "0";
        String fifteenTwo = "";
        String twentyOne = "0";
        String twentyTwo = "";

        LinkedHashMap<String, Float> tempData;

        if (apShell) {
            tempData = penetration;
        } else {
            tempData = flightTime;
        }

        for (Map.Entry<String, Float> entry : tempData.entrySet()) {
            float tempFloat = Float.parseFloat(entry.getKey());

            if (tempFloat < 5000f) {
                if (Float.parseFloat(fiveOne) < tempFloat) {
                    fiveOne = entry.getKey();
                }
            } else if (tempFloat >= 5000f && tempFloat < 10000f) {
                if (StringUtils.isEmpty(fiveTwo)) {
                    fiveTwo = entry.getKey();
                }

                if (Float.parseFloat(tenOne) < tempFloat) {
                    tenOne = entry.getKey();
                }
            } else if (tempFloat >= 10000f && tempFloat < 15000f) {
                if (StringUtils.isEmpty(tenTwo)) {
                    tenTwo = entry.getKey();
                }

                if (Float.parseFloat(fifteenOne) < tempFloat) {
                    fifteenOne = entry.getKey();
                }
            } else if (tempFloat >= 15000f && tempFloat < 20000f) {
                if (StringUtils.isEmpty(fifteenTwo)) {
                    fifteenTwo = entry.getKey();
                }

                if (Float.parseFloat(twentyOne) < tempFloat) {
                    twentyOne = entry.getKey();
                }
            } else if (tempFloat >= 20000f) {
                if (StringUtils.isEmpty(twentyTwo)) {
                    twentyTwo = entry.getKey();
                }
            }
        }

        if (StringUtils.isNotEmpty(fiveOne) && StringUtils.isNotEmpty(fiveTwo)) {
            if (apShell) {
                penetrationAtFive = setMiddleAtDistance(Float.parseFloat(fiveOne), penetration.get(fiveOne), Float.parseFloat(fiveTwo), penetration.get(fiveTwo), 5000f);
                impactAtFive = setMiddleAtDistance(Float.parseFloat(fiveOne), impact.get(fiveOne), Float.parseFloat(fiveTwo), impact.get(fiveTwo), 5000f);

//                vertPlusAtFive = setMiddleAtDistance(fiveOne, vertPlus.get(fiveOne), fiveTwo, vertPlus.get(fiveTwo), 5000f);
//                vertMinusAtFive = setMiddleAtDistance(fiveOne, vertMinus.get(fiveOne), fiveTwo, vertMinus.get(fiveTwo), 5000f);
            }
            flightTimeAtFive = setMiddleAtDistance(Float.parseFloat(fiveOne), flightTime.get(fiveOne), Float.parseFloat(fiveTwo), flightTime.get(fiveTwo), 5000f);
        }

        if (StringUtils.isNotEmpty(tenOne) && StringUtils.isNotEmpty(tenTwo)) {
            if (apShell) {
                penetrationAtTen = setMiddleAtDistance(Float.parseFloat(tenOne), penetration.get(tenOne), Float.parseFloat(tenTwo), penetration.get(tenTwo), 10000f);
                impactAtTen = setMiddleAtDistance(Float.parseFloat(tenOne), impact.get(tenOne), Float.parseFloat(tenTwo), impact.get(tenTwo), 10000f);

                vertMinusAtTen = (getVertDist(tenOne, 10000f, true) + getVertDist(tenTwo, 10000f, true)) / 2f;
                vertPlusAtTen = (getVertDist(tenOne, 10000f, false) + getVertDist(tenTwo, 10000f, false)) / 2f;
            }
            flightTimeAtTen = setMiddleAtDistance(Float.parseFloat(tenOne), flightTime.get(tenOne), Float.parseFloat(tenTwo), flightTime.get(tenTwo), 10000f);
        }

        if (StringUtils.isNotEmpty(fifteenOne) && StringUtils.isNotEmpty(fifteenTwo)) {
            if (apShell) {
                penetrationAtFifteen = setMiddleAtDistance(Float.parseFloat(fifteenOne), penetration.get(fifteenOne), Float.parseFloat(fifteenTwo), penetration.get(fifteenTwo), 15000f);
                impactAtFifteen = setMiddleAtDistance(Float.parseFloat(fifteenOne), impact.get(fifteenOne), Float.parseFloat(fifteenTwo), impact.get(fifteenTwo), 15000f);

                vertMinusAtFifteen = (getVertDist(fifteenOne, 15000f, true) + getVertDist(fifteenTwo, 15000f, true)) / 2f;
                vertPlusAtFifteen = (getVertDist(fifteenOne, 15000f, false) + getVertDist(fifteenTwo, 15000f, false)) / 2f;
            }
            flightTimeAtFifteen = setMiddleAtDistance(Float.parseFloat(fifteenOne), flightTime.get(fifteenOne), Float.parseFloat(fifteenTwo), flightTime.get(fifteenTwo), 15000f);
        }

        if (StringUtils.isNotEmpty(twentyOne) && StringUtils.isNotEmpty(twentyTwo)) {
            if (apShell) {
                penetrationAtTwenty = setMiddleAtDistance(Float.parseFloat(twentyOne), penetration.get(twentyOne), Float.parseFloat(twentyTwo), penetration.get(twentyTwo), 20000f);
                impactAtTwenty = setMiddleAtDistance(Float.parseFloat(twentyOne), impact.get(twentyOne), Float.parseFloat(twentyTwo), impact.get(twentyTwo), 20000f);

                vertMinusAtTwenty = (getVertDist(twentyOne, 20000f, true) + getVertDist(twentyTwo, 20000f, true)) / 2f;
                vertPlusAtTwenty = (getVertDist(twentyOne, 20000f, false) + getVertDist(twentyTwo, 20000f, false)) / 2f;
            }
            flightTimeAtTwenty = setMiddleAtDistance(Float.parseFloat(twentyOne), flightTime.get(twentyOne), Float.parseFloat(twentyTwo), flightTime.get(twentyTwo), 20000f);
        }
    }
    
    private float getVertDist(String dist, float mid, boolean low)
    {
        float minDistVOffset = minDistV / 2f;
        float radAtDist = (float) Math.atan(minDistVOffset / mid);

        if (low) {
            radAtDist = -radAtDist;
        }

        float arcRad = launchAngle.get(dist);
        String e1 = "";
        String e2 = "";

        for (Map.Entry<String, Float> entry : launchAngle.entrySet()) {
            float tempFloat = entry.getValue();

            if (tempFloat < arcRad + radAtDist) {
                e1 = entry.getKey();
            } else if (tempFloat >= arcRad + radAtDist) {
                e2 = entry.getKey();
                break;
            }
        }

        return (Float.parseFloat(e1) + Float.parseFloat(e2)) / 2f;
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