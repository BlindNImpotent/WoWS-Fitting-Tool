package WoWSFT.model.gameparams.ship.component.artillery;

import WoWSFT.model.gameparams.TypeInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Shell
{
    private double alphaDamage;
    private double alphaPiercingCS;
    private double alphaPiercingHE;
    private double alphaPiercingHEReal;
    private String ammoType;
    private double bulletAirDrag;
    private double bulletAlwaysRicochetAt;
    private boolean bulletCap;
    private double bulletCapNormalizeMaxAngle;
    private double bulletDetonator;
    private double bulletDetonatorSpread;
    private double bulletDetonatorThreshold;
    private double bulletDiametr;
    private double bulletKrupp;
    private double bulletMass;
    private double bulletPenetrationSpread;
    private double bulletRicochetAt;
    private double bulletSpeed;
    private double bulletUnderwaterDistFactor;
    private double bulletUnderwaterPenetrationFactor;
    private double bulletWaterDrag;
    private double burnProb;
    private int costCR;
    private double damage;
    private double directDamage;
    private long id;
    private String index;
    private String name;
    private TypeInfo typeinfo;
    private boolean uwAbility;
    private double uwCritical;
    private double volume;
    private List<Double> waterRefractionReflectDeltaAngleInterval;

//    @JsonIgnore
    private LinkedHashMap<String, Double> penetration;
//    @JsonIgnore
    private LinkedHashMap<String, Double> flightTime;
//    @JsonIgnore
    private LinkedHashMap<String, Double> impact;
    @JsonIgnore
    private LinkedHashMap<String, Double> launchAngle;
//    @JsonIgnore
//    private LinkedHashMap<String, Double> vertPlus;
//    @JsonIgnore
//    private LinkedHashMap<String, Double> vertMinus;
//    @JsonIgnore
    private List<String> distanceList;
    private double minDistV;
    private double penetrationAtFive;
    private double penetrationAtTen;
    private double penetrationAtFifteen;
    private double penetrationAtTwenty;
    private double penetrationAtMax;
    private double flightTimeAtFive;
    private double flightTimeAtTen;
    private double flightTimeAtFifteen;
    private double flightTimeAtTwenty;
    private double flightTimeAtMax;
    private double impactAtFive;
    private double impactAtTen;
    private double impactAtFifteen;
    private double impactAtTwenty;
    private double impactAtMax;
    private double vertPlusAtFive;
    private double vertPlusAtTen;
    private double vertPlusAtFifteen;
    private double vertPlusAtTwenty;
    private double vertPlusAtMax;
    private double vertMinusAtFive;
    private double vertMinusAtTen;
    private double vertMinusAtFifteen;
    private double vertMinusAtTwenty;
    private double vertMinusAtMax;

    private int penetrationIFHE;
    
    @JsonIgnore
    private double distFive = 5000.0;
    @JsonIgnore
    private double distTen = 10000.0;
    @JsonIgnore
    private double distFifteen = 15000.0;
    @JsonIgnore
    private double distTwenty = 20000.0;

    public void setAlphaPiercingHE(double alphaPiercingHE)
    {
        this.alphaPiercingHE = alphaPiercingHE;
        this.alphaPiercingHEReal = alphaPiercingHE - 1.0;
    }

//    public void setMaxDist(double maxDist)
//    {
//        String maxOne = "0";
//        String maxTwo = "";
//
//        for (Map.Entry<String, Double> entry : this.flightTime.entrySet()) {
//            double tempFloat = Double.parseDouble(entry.getKey());
//
//            if (tempFloat < maxDist) {
//                if (Double.parseDouble(maxOne) < tempFloat) {
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
//                penetrationAtMax = setMiddleAtDistance(Double.parseDouble(maxOne), penetration.get(maxOne), Double.parseDouble(maxTwo), penetration.get(maxTwo), maxDist);
//                impactAtMax = setMiddleAtDistance(Double.parseDouble(maxOne), impact.get(maxOne), Double.parseDouble(maxTwo), impact.get(maxTwo), maxDist);
//
////                vertMinusAtMax = calcVertDist(maxDist, (getVertDist(maxOne, maxDist, true) + getVertDist(maxTwo, maxDist, true)) / 2.0);
////                vertPlusAtMax = calcVertDist(maxDist, (getVertDist(maxOne, maxDist, false) + getVertDist(maxTwo, maxDist, false)) / 2.0);
//            }
//            flightTimeAtMax = setMiddleAtDistance(Double.parseDouble(maxOne), flightTime.get(maxOne), Double.parseDouble(maxTwo), flightTime.get(maxTwo), maxDist);
//        }
//    }

    public void setShell(LinkedHashMap<String, Double> flightTime,
                         LinkedHashMap<String, Double> penetration, LinkedHashMap<String, Double> impact, List<String> distanceList, LinkedHashMap<String, Double> launchAngle,
                         double minDistV, boolean apShell)
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

        LinkedHashMap<String, Double> tempData;

        if (apShell) {
            tempData = penetration;
        } else {
            tempData = flightTime;
        }

        for (Map.Entry<String, Double> entry : tempData.entrySet()) {
            double tempFloat = Double.parseDouble(entry.getKey());

            if (tempFloat < distFive) {
                if (Double.parseDouble(fiveOne) < tempFloat) {
                    fiveOne = entry.getKey();
                }
            } else if (tempFloat >= distFive && tempFloat < distTen) {
                if (StringUtils.isEmpty(fiveTwo)) {
                    fiveTwo = entry.getKey();
                }

                if (Double.parseDouble(tenOne) < tempFloat) {
                    tenOne = entry.getKey();
                }
            } else if (tempFloat >= distTen && tempFloat < distFifteen) {
                if (StringUtils.isEmpty(tenTwo)) {
                    tenTwo = entry.getKey();
                }

                if (Double.parseDouble(fifteenOne) < tempFloat) {
                    fifteenOne = entry.getKey();
                }
            } else if (tempFloat >= distFifteen && tempFloat < distTwenty) {
                if (StringUtils.isEmpty(fifteenTwo)) {
                    fifteenTwo = entry.getKey();
                }

                if (Double.parseDouble(twentyOne) < tempFloat) {
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
                penetrationAtFive = setMiddleAtDistance(Double.parseDouble(fiveOne), penetration.get(fiveOne), Double.parseDouble(fiveTwo), penetration.get(fiveTwo), distFive);
                impactAtFive = setMiddleAtDistance(Double.parseDouble(fiveOne), impact.get(fiveOne), Double.parseDouble(fiveTwo), impact.get(fiveTwo), distFive);

//                vertMinusAtFive = calcVertDist(distFive, (getVertDist(fiveOne, distFive, true) + getVertDist(fiveTwo, distFive, true)) / 2.0);
//                vertPlusAtFive = calcVertDist(distFive, (getVertDist(fiveOne, distFive, false) + getVertDist(fiveTwo, distFive, false)) / 2.0);
            }
            flightTimeAtFive = setMiddleAtDistance(Double.parseDouble(fiveOne), flightTime.get(fiveOne), Double.parseDouble(fiveTwo), flightTime.get(fiveTwo), distFive);
        }

        if (StringUtils.isNotEmpty(tenOne) && StringUtils.isNotEmpty(tenTwo)) {
            if (apShell) {
                penetrationAtTen = setMiddleAtDistance(Double.parseDouble(tenOne), penetration.get(tenOne), Double.parseDouble(tenTwo), penetration.get(tenTwo), distTen);
                impactAtTen = setMiddleAtDistance(Double.parseDouble(tenOne), impact.get(tenOne), Double.parseDouble(tenTwo), impact.get(tenTwo), distTen);

//                vertMinusAtTen = calcVertDist(distTen, (getVertDist(tenOne, distTen, true) + getVertDist(tenTwo, distTen, true)) / 2.0);
//                vertPlusAtTen = calcVertDist(distTen, (getVertDist(tenOne, distTen, false) + getVertDist(tenTwo, distTen, false)) / 2.0);
            }
            flightTimeAtTen = setMiddleAtDistance(Double.parseDouble(tenOne), flightTime.get(tenOne), Double.parseDouble(tenTwo), flightTime.get(tenTwo), distTen);
        }

        if (StringUtils.isNotEmpty(fifteenOne) && StringUtils.isNotEmpty(fifteenTwo)) {
            if (apShell) {
                penetrationAtFifteen = setMiddleAtDistance(Double.parseDouble(fifteenOne), penetration.get(fifteenOne), Double.parseDouble(fifteenTwo), penetration.get(fifteenTwo), distFifteen);
                impactAtFifteen = setMiddleAtDistance(Double.parseDouble(fifteenOne), impact.get(fifteenOne), Double.parseDouble(fifteenTwo), impact.get(fifteenTwo), distFifteen);

//                vertMinusAtFifteen = calcVertDist(distFifteen, (getVertDist(fifteenOne, distFifteen, true) + getVertDist(fifteenTwo, distFifteen, true)) / 2.0);
//                vertPlusAtFifteen = calcVertDist(distFifteen, (getVertDist(fifteenOne, distFifteen, false) + getVertDist(fifteenTwo, distFifteen, false)) / 2.0);
            }
            flightTimeAtFifteen = setMiddleAtDistance(Double.parseDouble(fifteenOne), flightTime.get(fifteenOne), Double.parseDouble(fifteenTwo), flightTime.get(fifteenTwo), distFifteen);
        }

        if (StringUtils.isNotEmpty(twentyOne) && StringUtils.isNotEmpty(twentyTwo)) {
            if (apShell) {
                penetrationAtTwenty = setMiddleAtDistance(Double.parseDouble(twentyOne), penetration.get(twentyOne), Double.parseDouble(twentyTwo), penetration.get(twentyTwo), distTwenty);
                impactAtTwenty = setMiddleAtDistance(Double.parseDouble(twentyOne), impact.get(twentyOne), Double.parseDouble(twentyTwo), impact.get(twentyTwo), distTwenty);

//                vertMinusAtTwenty = calcVertDist(distTwenty, (getVertDist(twentyOne, distTwenty, true) + getVertDist(twentyTwo, distTwenty, true)) / 2.0);
//                vertPlusAtTwenty = calcVertDist(distTwenty, (getVertDist(twentyOne, distTwenty, false) + getVertDist(twentyTwo, distTwenty, false)) / 2.0);
            }
            flightTimeAtTwenty = setMiddleAtDistance(Double.parseDouble(twentyOne), flightTime.get(twentyOne), Double.parseDouble(twentyTwo), flightTime.get(twentyTwo), distTwenty);
        }
    }
    
    private double calcVertDist(double dist, double vert)
    {
        return dist + (vert - dist);
    }
    
    private double getVertDist(String dist, double mid, boolean low)
    {
        double minDistVOffset = minDistV / 2.0;
        double radAtDist = Math.atan(minDistVOffset / mid);

        if (low) {
            radAtDist = -radAtDist;
        }

        double arcRad = launchAngle.get(dist);
        String e1 = "";
        String e2 = "";

        for (Map.Entry<String, Double> entry : launchAngle.entrySet()) {
            double tempFloat = entry.getValue();

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

        return (Double.parseDouble(e1) + Double.parseDouble(e2)) / 2.0;
    }

    private double setMiddleAtDistance(Double x1, Double y1, Double x2, Double y2, Double mid)
    {
        if (x1 == null || x2 == null || y1 == null || y2 == null) {
            return 0.0;
        }

        double a = (y2 - y1) / (x2 - x1);
        double c = y1 - (a * x1);

        return a * mid + c;
    }
}