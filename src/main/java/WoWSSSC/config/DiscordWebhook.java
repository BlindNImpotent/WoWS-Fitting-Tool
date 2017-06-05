package WoWSSSC.config;

import org.json.JSONObject;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Qualson-Lee on 2017-06-05.
 */
@Component
@ComponentScan(basePackages = {"WoWSSSC"})
public class DiscordWebhook
{
    private static final int MAX_STACK_LINE = 5;
    private static final String DiscordWebHookURL = "https://discordapp.com/api/webhooks/321093994824073217/-Lq1pkgooEZMHctMTWO4wo0OHLci5r-vsoAJS0U0tEd9ng9J80RmdPk95NSlu-IhvvP2";

    public void sendDiscordWebHook(Exception e, HttpServletRequest request) throws Exception
    {
        URL url = new URL(DiscordWebHookURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches (false);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("charset", "UTF-8");

        JSONObject object = new JSONObject();

        String message =
                "```" +
                "URL: " +  request.getRequestURL().toString() + "?" + request.getQueryString() + "\n\n" +
                "Referrer: " + request.getHeader("Referer") + "\n\n" +
                "Client IP Address : " + getClientIPAddress(request) + " / " + request.getRemoteAddr() + "\n\n" +
                "Method: " + request.getMethod() + "\n\n" +
                "Error: " + e.getMessage() + "\n" + printStackTraceToString(e) +
                "```";

        object.put("content", message);

        OutputStream os = connection.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
        osw.write(object.toString());
        osw.flush();
        osw.close();

        connection.getResponseCode();
    }

    private static String printStackTraceToString(Exception e) {

        StringBuffer sb = new StringBuffer();
        try {
            sb.append(e.toString());
            sb.append("\n");
            StackTraceElement element[] = e.getStackTrace();

            // for (int idx = 0; idx < element.length; idx++) {
            for (int idx = 0; idx < (element.length > MAX_STACK_LINE ? MAX_STACK_LINE : element.length); idx++) {
                sb.append("\tat ");
                sb.append(element[idx].toString());
                sb.append("\n");
            }
        } catch (Exception ex) {
            return e.toString();
        }

        return sb.toString();
    }

    private String getClientIPAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }

        if (ipAddress.equalsIgnoreCase("0:0:0:0:0:0:0:1"))
        {
            return "localhost";
        }

        return ipAddress;
    }
}
