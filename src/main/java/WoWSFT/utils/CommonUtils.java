package WoWSFT.utils;

import static WoWSFT.model.Constant.*;

public class CommonUtils
{
    public static Float getDistCoefWG(Number number)
    {
        return Math.round(number.floatValue() / distCoefWG.floatValue() * 1000f) / 1000f;
    }

    public static float getBonusCoef(Number number)
    {
        return (Math.round(number.floatValue() * 1000f) - 1000f) / 10f;
    }

    public static float getBonus(Number number)
    {
        return Math.round(number.floatValue() * 1000f) / 10f;
    }

    public static String replaceZero(String number)
    {
        return number.endsWith(".0") ? number.substring(0, number.length() - 2) : number;
    }

    public static String getNumSym(Number number)
    {
        return (number.floatValue() >= 0 ? "+" : "") + replaceZero(number.toString());
    }
}
