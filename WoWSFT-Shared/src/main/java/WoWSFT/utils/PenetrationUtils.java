package WoWSFT.utils;

import WoWSFT.model.gameparams.ship.component.artillery.Shell;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class PenetrationUtils
{
    // SHELL CONSTANTS
    private static final double a = 9.80665; // GRAVITY
    private static final double T_0 = 288.0; // TEMPERATURE AT SEA LEVEL
    private static final double L = 0.0065; // TEMPERATURE LAPSE RATE
    private static final double p_0 = 101325.0; // PRESSURE AT SEA LEVEL
    private static final double R = 8.31447; // UNIV GAS CONSTANT
    private static final double M = 0.0289644; // MOLAR MASS OF AIR
    
    public static void setPenetration(Shell ArtyShell, double maxVertAngle, double minDistV, double maxDist, boolean apShell)
    {
        // SHELL CONSTANTS
        double C = 0.5561613; // PENETRATION
        double W = ArtyShell.getBulletMass(); // SHELL WEIGHT
        double D = ArtyShell.getBulletDiametr(); // SHELL DIAMETER
        double c_D = ArtyShell.getBulletAirDrag();  // SHELL DRAG
        double V_0 = ArtyShell.getBulletSpeed(); // SHELL MUZZLE VELOCITY
        double K = ArtyShell.getBulletKrupp(); // SHELL KRUPP
        double ricochet = (ArtyShell.getBulletAlwaysRicochetAt() + ArtyShell.getBulletCapNormalizeMaxAngle()) * Math.PI / 360.0 * 2.0; // Ignores after ricochet

        double cw_1 = 1.0; // QUADRATIC DRAG COEFFICIENT
        double cw_2 = 100.0 + (1000.0 / 3.0) * D; // LINEAR DRAG COEFFICIENT

        C = C * K / 2400.0; // KRUPP INCLUSION
        double k = 0.5 * c_D * Math.pow((D / 2.0), 2.0) * Math.PI / W; // CONSTANTS TERMS OF DRAG

        List<Double> alpha = linspace(Math.PI * maxVertAngle / 360.0 * 2.0); // ELEV. ANGLES 0...MAX
        double dt = 0.1; // TIME STEP

        LinkedHashMap<String, Double> penetration = new LinkedHashMap<>();
        LinkedHashMap<String, Double> flightTime = new LinkedHashMap<>();
        LinkedHashMap<String, Double> impactAngle = new LinkedHashMap<>();
//        LinkedHashMap<String, Double> launchAngle = new LinkedHashMap<>();
        List<String> distanceList = new ArrayList<>();

        double maxDistCalc = 0.0;
        double prevMax = 0.0;

        // for each alpha angle do:
        for (int i = 0; i < alpha.size(); i++) {
            double v_x = Math.cos(alpha.get(i)) * V_0;
            double v_y = Math.sin(alpha.get(i)) * V_0;

            double y = 0.0;
            double x = 0.0;
            double t = 0.0;

            double tX_1 = 0.0;
            double tX_2 = 0.0;
            double tY_1 = 0.0;
            double tY_2 = 0.0;

            boolean tempNext = true;

            while (tempNext) { // follow flight path until shell hits ground again
                tempNext = y >= 0.0;

                if (tempNext) {
                    tX_1 = x;
                    tY_1 = y;
                } else {
                    tX_2 = x;
                    tY_2 = y;
                }

                x = x + dt * v_x;
                y = y + dt * v_y;

                double T = T_0 - L * y;
                double p = p_0 * Math.pow(1 - L * y / T_0, (a * M / (R * L)));
                double rho = p * M / (R * T);

                v_x = v_x - dt * k * rho * (cw_1 * Math.pow(v_x, 2) + cw_2 * v_x);
                v_y = v_y - dt * a - dt * k * rho * (cw_1 * Math.pow(v_y, 2) + cw_2 * Math.abs(v_y)) * Math.signum(v_y);

                t = t + dt;
            }

            double v_total = Math.pow((Math.pow(v_y, 2.0) + Math.pow(v_x, 2.0)), 0.5);
            double p_athit = C * Math.pow(v_total, 1.1) * Math.pow(W, 0.55) / Math.pow(D * 1000.0, 0.65); // PENETRATION FORMULA
            double IA = Math.atan(Math.abs(v_y) / Math.abs(v_x)); // IMPACT ANGLE ON BELT ARMOR

            maxDistCalc = getMidAtY(tX_1, tY_1, tX_2, tY_2, 0.0);

            if (IA > ricochet || prevMax > maxDistCalc || maxDistCalc > maxDist * 1.5 || maxDistCalc > 25000.0) {
                break;
            }
            prevMax = maxDistCalc;

            flightTime.put(String.valueOf(maxDistCalc), t / 3.0);

            if (apShell) {
                penetration.put(String.valueOf(maxDistCalc), Math.cos(IA) * p_athit);
                impactAngle.put(String.valueOf(maxDistCalc), IA * 180.0 / Math.PI);
                distanceList.add(String.valueOf(maxDistCalc));
//                launchAngle.put(String.valueOf(maxDistCalc), alpha.get(i));
            }
        }

        if (apShell) {
            ArtyShell.setShell(flightTime, penetration, impactAngle, distanceList, null, minDistV, true);
        } else {
            ArtyShell.setShell(flightTime, null, null, null, null, minDistV, false);
        }
    }

    public static double getMidAtY(double x1, double y1, double x2, double y2, double yAxis)
    {
        double a = (y2 - y1) / (x2 - x1);
        double c = y1 - (a * x1);

        return (yAxis - c) / a;
    }

    private static List<Double> linspace(double end)
    {
        List<Double> alpha = new ArrayList<>();
        double begin = 0.0;
        double incrementalBig = 0.00375;

        while (begin <= end) {
            alpha.add(begin);
            begin += incrementalBig;
        }

        return alpha;
    }
}
