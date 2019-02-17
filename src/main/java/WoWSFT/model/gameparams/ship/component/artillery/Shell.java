package WoWSFT.model.gameparams.ship.component.artillery;

import WoWSFT.model.gameparams.TypeInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

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

//    @JsonIgnore
    private LinkedHashMap<String, Float> penetration;
//    @JsonIgnore
    private LinkedHashMap<String, Float> flightTime;
//    @JsonIgnore
    private LinkedHashMap<String, Float> impact;
    @JsonIgnore
    private LinkedHashMap<String, Float> launchAngle;
//    @JsonIgnore
//    private LinkedHashMap<String, Float> vertPlus;
//    @JsonIgnore
//    private LinkedHashMap<String, Float> vertMinus;
//    @JsonIgnore
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
    
    @JsonIgnore
    private float distFive = 5000f;
    @JsonIgnore
    private float distTen = 10000f;
    @JsonIgnore
    private float distFifteen = 15000f;
    @JsonIgnore
    private float distTwenty = 20000f;

    public void setAlphaPiercingHE(float alphaPiercingHE)
    {
        this.alphaPiercingHE = alphaPiercingHE;
        this.alphaPiercingHEReal = alphaPiercingHE - 1f;
    }

//    public void setMaxDist(float maxDist)
//    {
//        String maxOne = "0";
//        String maxTwo = "";
//
//        for (Map.Entry<String, Float> entry : this.flightTime.entrySet()) {
//            float tempFloat = Float.parseFloat(entry.getKey());
//
//            if (tempFloat < maxDist) {
//                if (Float.parseFloat(maxOne) < tempFloat) {
//                    maxOne = entry.getKey();
//                }
//            } else if (tempFloat >= maxDist) {
//                if (StringUtils.isEmpty(maxTwo)) {
//                    maxTwo = entry.getKey();
//                }
//            }
//        }
//
//        if (StringUtils.isNotEmpty(maxOne) && StringUtils.isNotEmpty(maxTwo)) {
//            if (penetration != null) {
//                penetrationAtMax = setMiddleAtDistance(Float.parseFloat(maxOne), penetration.get(maxOne), Float.parseFloat(maxTwo), penetration.get(maxTwo), maxDist);
//                impactAtMax = setMiddleAtDistance(Float.parseFloat(maxOne), impact.get(maxOne), Float.parseFloat(maxTwo), impact.get(maxTwo), maxDist);
//
////                vertMinusAtMax = calcVertDist(maxDist, (getVertDist(maxOne, maxDist, true) + getVertDist(maxTwo, maxDist, true)) / 2f);
////                vertPlusAtMax = calcVertDist(maxDist, (getVertDist(maxOne, maxDist, false) + getVertDist(maxTwo, maxDist, false)) / 2f);
//            }
//            flightTimeAtMax = setMiddleAtDistance(Float.parseFloat(maxOne), flightTime.get(maxOne), Float.parseFloat(maxTwo), flightTime.get(maxTwo), maxDist);
//        }
//    }

    public void setShell(LinkedHashMap<String, Float> flightTime,
                         LinkedHashMap<String, Float> penetration, LinkedHashMap<String, Float> impact, List<String> distanceList, LinkedHashMap<String, Float> launchAngle,
                         float minDistV, boolean apShell)
    {
        this.penetration = penetration;
        this.flightTime = flightTime;
        this.impact = impact;
        this.distanceList = distanceList;
//        this.launchAngle = launchAngle;
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

            if (tempFloat < distFive) {
                if (Float.parseFloat(fiveOne) < tempFloat) {
                    fiveOne = entry.getKey();
                }
            } else if (tempFloat >= distFive && tempFloat < distTen) {
                if (StringUtils.isEmpty(fiveTwo)) {
                    fiveTwo = entry.getKey();
                }

                if (Float.parseFloat(tenOne) < tempFloat) {
                    tenOne = entry.getKey();
                }
            } else if (tempFloat >= distTen && tempFloat < distFifteen) {
                if (StringUtils.isEmpty(tenTwo)) {
                    tenTwo = entry.getKey();
                }

                if (Float.parseFloat(fifteenOne) < tempFloat) {
                    fifteenOne = entry.getKey();
                }
            } else if (tempFloat >= distFifteen && tempFloat < distTwenty) {
                if (StringUtils.isEmpty(fifteenTwo)) {
                    fifteenTwo = entry.getKey();
                }

                if (Float.parseFloat(twentyOne) < tempFloat) {
                    twentyOne = entry.getKey();
                }
            } else if (tempFloat >= distTwenty) {
                if (StringUtils.isEmpty(twentyTwo)) {
                    twentyTwo = entry.getKey();
                }
            }
        }

        if (StringUtils.isNotEmpty(fiveOne) && StringUtils.isNotEmpty(fiveTwo)) {
            if (apShell) {
                penetrationAtFive = setMiddleAtDistance(Float.parseFloat(fiveOne), penetration.get(fiveOne), Float.parseFloat(fiveTwo), penetration.get(fiveTwo), distFive);
                impactAtFive = setMiddleAtDistance(Float.parseFloat(fiveOne), impact.get(fiveOne), Float.parseFloat(fiveTwo), impact.get(fiveTwo), distFive);

//                vertMinusAtFive = calcVertDist(distFive, (getVertDist(fiveOne, distFive, true) + getVertDist(fiveTwo, distFive, true)) / 2f);
//                vertPlusAtFive = calcVertDist(distFive, (getVertDist(fiveOne, distFive, false) + getVertDist(fiveTwo, distFive, false)) / 2f);
            }
            flightTimeAtFive = setMiddleAtDistance(Float.parseFloat(fiveOne), flightTime.get(fiveOne), Float.parseFloat(fiveTwo), flightTime.get(fiveTwo), distFive);
        }

        if (StringUtils.isNotEmpty(tenOne) && StringUtils.isNotEmpty(tenTwo)) {
            if (apShell) {
                penetrationAtTen = setMiddleAtDistance(Float.parseFloat(tenOne), penetration.get(tenOne), Float.parseFloat(tenTwo), penetration.get(tenTwo), distTen);
                impactAtTen = setMiddleAtDistance(Float.parseFloat(tenOne), impact.get(tenOne), Float.parseFloat(tenTwo), impact.get(tenTwo), distTen);

//                vertMinusAtTen = calcVertDist(distTen, (getVertDist(tenOne, distTen, true) + getVertDist(tenTwo, distTen, true)) / 2f);
//                vertPlusAtTen = calcVertDist(distTen, (getVertDist(tenOne, distTen, false) + getVertDist(tenTwo, distTen, false)) / 2f);
            }
            flightTimeAtTen = setMiddleAtDistance(Float.parseFloat(tenOne), flightTime.get(tenOne), Float.parseFloat(tenTwo), flightTime.get(tenTwo), distTen);
        }

        if (StringUtils.isNotEmpty(fifteenOne) && StringUtils.isNotEmpty(fifteenTwo)) {
            if (apShell) {
                penetrationAtFifteen = setMiddleAtDistance(Float.parseFloat(fifteenOne), penetration.get(fifteenOne), Float.parseFloat(fifteenTwo), penetration.get(fifteenTwo), distFifteen);
                impactAtFifteen = setMiddleAtDistance(Float.parseFloat(fifteenOne), impact.get(fifteenOne), Float.parseFloat(fifteenTwo), impact.get(fifteenTwo), distFifteen);

//                vertMinusAtFifteen = calcVertDist(distFifteen, (getVertDist(fifteenOne, distFifteen, true) + getVertDist(fifteenTwo, distFifteen, true)) / 2f);
//                vertPlusAtFifteen = calcVertDist(distFifteen, (getVertDist(fifteenOne, distFifteen, false) + getVertDist(fifteenTwo, distFifteen, false)) / 2f);
            }
            flightTimeAtFifteen = setMiddleAtDistance(Float.parseFloat(fifteenOne), flightTime.get(fifteenOne), Float.parseFloat(fifteenTwo), flightTime.get(fifteenTwo), distFifteen);
        }

        if (StringUtils.isNotEmpty(twentyOne) && StringUtils.isNotEmpty(twentyTwo)) {
            if (apShell) {
                penetrationAtTwenty = setMiddleAtDistance(Float.parseFloat(twentyOne), penetration.get(twentyOne), Float.parseFloat(twentyTwo), penetration.get(twentyTwo), distTwenty);
                impactAtTwenty = setMiddleAtDistance(Float.parseFloat(twentyOne), impact.get(twentyOne), Float.parseFloat(twentyTwo), impact.get(twentyTwo), distTwenty);

//                vertMinusAtTwenty = calcVertDist(distTwenty, (getVertDist(twentyOne, distTwenty, true) + getVertDist(twentyTwo, distTwenty, true)) / 2f);
//                vertPlusAtTwenty = calcVertDist(distTwenty, (getVertDist(twentyOne, distTwenty, false) + getVertDist(twentyTwo, distTwenty, false)) / 2f);
            }
            flightTimeAtTwenty = setMiddleAtDistance(Float.parseFloat(twentyOne), flightTime.get(twentyOne), Float.parseFloat(twentyTwo), flightTime.get(twentyTwo), distTwenty);
        }
    }
    
    private float calcVertDist(float dist, float vert)
    {
        return dist + (vert - dist);
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

        if (StringUtils.isEmpty(e1) || StringUtils.isEmpty(e2)) {
            return 0;
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