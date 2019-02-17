package WoWSFT.utils;

import WoWSFT.model.gameparams.ship.component.artillery.Shell;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class PenetrationUtils
{
    // SHELL CONSTANTS
    private static final float a = 9.80665f; // GRAVITY
    private static final float T_0 = 288f; // TEMPERATURE AT SEA LEVEL
    private static final float L = 0.0065f; // TEMPERATURE LAPSE RATE
    private static final float p_0 = 101325f; // PRESSURE AT SEA LEVEL
    private static final float R = 8.31447f; // UNIV GAS CONSTANT
    private static final float M = 0.0289644f; // MOLAR MASS OF AIR
    
    public static void setPenetration(Shell ArtyShell, float maxVertAngle, float minDistV, float maxDist, boolean apShell)
    {
        // SHELL CONSTANTS
        float C = 0.5561613f; // PENETRATION
        float W = ArtyShell.getBulletMass(); // SHELL WEIGHT
        float D = ArtyShell.getBulletDiametr(); // SHELL DIAMETER
        float c_D = ArtyShell.getBulletAirDrag();  // SHELL DRAG
        float V_0 = ArtyShell.getBulletSpeed(); // SHELL MUZZLE VELOCITY
        float K = ArtyShell.getBulletKrupp(); // SHELL KRUPP
        float ricochet = (ArtyShell.getBulletAlwaysRicochetAt() + ArtyShell.getBulletCapNormalizeMaxAngle()) * (float) Math.PI / 360f * 2f; // Ignores after ricochet

        float cw_1 = 1f; // QUADRATIC DRAG COEFFICIENT
        float cw_2 = 100f + (1000f / 3f) * D; // LINEAR DRAG COEFFICIENT

        C = C * K / 2400f; // KRUPP INCLUSION
        float k = 0.5f * c_D * (float) Math.pow((D / 2f), 2f) * (float) Math.PI / W; // CONSTANTS TERMS OF DRAG

        List<Float> alpha = linspace((float) Math.PI * maxVertAngle / 360f * 2f); // ELEV. ANGLES 0...MAX
        float dt = 0.1f; // TIME STEP

        LinkedHashMap<String, Float> penetration = new LinkedHashMap<>();
        LinkedHashMap<String, Float> flightTime = new LinkedHashMap<>();
        LinkedHashMap<String, Float> impactAngle = new LinkedHashMap<>();
//        LinkedHashMap<String, Float> launchAngle = new LinkedHashMap<>();
        List<String> distanceList = new ArrayList<>();

        float maxDistCalc = 0f;

        // for each alpha angle do:
        for (int i = 0; i < alpha.size(); i++) {
            float v_x = (float) Math.cos(alpha.get(i)) * V_0;
            float v_y = (float) Math.sin(alpha.get(i)) * V_0;

            float y = 0f;
            float x = 0f;
            float t = 0f;

            float tX_1 = 0f;
            float tX_2 = 0f;
            float tY_1 = 0f;
            float tY_2 = 0f;

            boolean tempNext = true;

            while (tempNext) { // follow flight path until shell hits ground again
                tempNext = y >= 0f;

                if (tempNext) {
                    tX_1 = x;
                    tY_1 = y;
                } else {
                    tX_2 = x;
                    tY_2 = y;
                }

                x = x + dt * v_x;
                y = y + dt * v_y;

                float T = T_0 - L * y;
                float p = p_0 * (float) Math.pow(1 - L * y / T_0, (a * M / (R * L)));
                float rho = p * M / (R * T);

                v_x = v_x - dt * k * rho * (cw_1 * (float) Math.pow(v_x, 2) + cw_2 * v_x);
                v_y = v_y - dt * a - dt * k * rho * (cw_1 * (float) Math.pow(v_y, 2) + cw_2 * Math.abs(v_y)) * Math.signum(v_y);

                t = t + dt;
            }

            float v_total = (float) Math.pow((Math.pow(v_y, 2f) + Math.pow(v_x, 2f)), 0.5f);
            float p_athit = C * (float) Math.pow(v_total, 1.1f) * (float) Math.pow(W, 0.55f) / (float) Math.pow(D * 1000f, 0.65f); // PENETRATION FORMULA
            float IA = (float) Math.atan(Math.abs(v_y) / Math.abs(v_x)); // IMPACT ANGLE ON BELT ARMOR

            maxDistCalc = getMidAtY(tX_1, tY_1, tX_2, tY_2, 0f);

            if (IA > ricochet || maxDistCalc > 25000f) {
                break;
            }

            flightTime.put(String.valueOf(maxDistCalc), t / 3f);

            if (apShell) {
                penetration.put(String.valueOf(maxDistCalc), (float) Math.cos(IA) * p_athit);
                impactAngle.put(String.valueOf(maxDistCalc), IA * 180f / ((float) Math.PI));
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

    public static float getMidAtY(float x1, float y1, float x2, float y2, float yAxis)
    {
        float a = (y2 - y1) / (x2 - x1);
        float c = y1 - (a * x1);

        return (yAxis - c) / a;
    }

    private static List<Float> linspace(float end)
    {
        List<Float> alpha = new ArrayList<>();
        float begin = 0f;
        float incrementalBig = 0.005f;

        while (begin <= end) {
            alpha.add(begin);
            begin += incrementalBig;
        }

        return alpha;
    }
}
