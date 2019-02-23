package WoWSFT.utils;


import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

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

    public static void sendDiscordWH(String message) throws IOException
    {
        String content = "{\"content\": \"```java\\n" + message + "\\n```\"}";

        URL url =  new URL("https://discordapp.com/api/webhooks/");
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.addRequestProperty("Content-Type", "application/json; charset=utf-8");
        connection.addRequestProperty("User-Agent", "WoWSFT Webhook");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setDoInput(true);

        OutputStream os = connection.getOutputStream();
        os.write(content.getBytes());
        os.flush();
        os.close();

        connection.disconnect();
    }
}
