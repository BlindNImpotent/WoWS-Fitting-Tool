package WoWSFT.utils;

public class CommonUtils
{
    public static float getDistCoefWG(Number number)
    {
        return Math.round(number.floatValue() / (100f / 3f) * 1000f);
    }
}
