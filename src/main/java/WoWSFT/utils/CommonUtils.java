package WoWSFT.utils;

public class CommonUtils
{
    public static float getDistCoefWG(Number number)
    {
        return Math.round(number.floatValue() / (100f / 3f) * 1000f);
    }

    public static float getBonusCoef(Number number)
    {
        return (Math.round(number.floatValue() * 1000f) - 1000f) / 10;
    }

    public static float getBonus(Number number)
    {
        return Math.round(number.floatValue() * 100f);
    }
}
