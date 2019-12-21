package WoWSFT.utils;

import static WoWSFT.model.Constant.*;

public class CommonUtils
{
    public static Double getDistCoefWG(Number number)
    {
        return Math.round(number.doubleValue() / distCoefWG.doubleValue() * 1000.0) / 1000.0;
    }

    public static double getBonusCoef(Number number)
    {
        return (Math.round(number.doubleValue() * 1000.0) - 1000.0) / 10.0;
    }

    public static double getBonus(Number number)
    {
        return Math.round(number.doubleValue() * 1000.0) / 10.0;
    }

    public static String replaceZero(String number)
    {
        return number.endsWith(".0") ? number.substring(0, number.length() - 2) : number;
    }

    public static String getNumSym(Number number)
    {
        return (number.doubleValue() >= 0 ? "+" : "") + replaceZero(number.toString());
    }

    public static double getDecimalRounded(double num, int digits)
    {
        double rounder = Math.pow(10.0, digits);

        return Math.round(num * rounder) / rounder;
    }
}
