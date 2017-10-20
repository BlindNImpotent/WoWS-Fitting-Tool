package WoWSSSC.config;

import WoWSSSC.model.BlockIp;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Aesis on 2017. 5. 21..
 */
@Configuration
@ComponentScan(basePackages = {"WoWSSSC"})
public class CustomFilter implements Filter
{
    @Autowired
    private DiscordWebhook discordWebhook;

    private static HashSet<String> blockIP = new HashSet<>();

    private HashMap<String, BlockIp> ipMap = new HashMap<>();

    static
    {
        blockIP.add("52.71.155.178");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        if (request.getRequestURI().equalsIgnoreCase("/"))
        {
            if (!ipMap.containsKey(discordWebhook.getClientIPAddress(request)))
            {
                ipMap.put(discordWebhook.getClientIPAddress(request), new BlockIp(discordWebhook.getClientIPAddress(request)));
            }
            else
            {
                if (ipMap.get(discordWebhook.getClientIPAddress(request)).getCount() < 5)
                {
                    ipMap.get(discordWebhook.getClientIPAddress(request)).doCount();
                }
                else
                {
                    if (System.currentTimeMillis() - ipMap.get(discordWebhook.getClientIPAddress(request)).getCreated().getTime() < 60000)
                    {
                        if (!blockIP.contains(discordWebhook.getClientIPAddress(request)))
                        {
                            ipMap.get(discordWebhook.getClientIPAddress(request)).setCreated(new Date());
                            blockIP.add(discordWebhook.getClientIPAddress(request));
                        }
                    }
                    else
                    {
                        blockIP.remove(discordWebhook.getClientIPAddress(request));
                        ipMap.get(discordWebhook.getClientIPAddress(request)).reset();
                    }
                }
            }
        }

        if (blockIP.contains(discordWebhook.getClientIPAddress(request)))
        {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

//        StringBuilder https = new StringBuilder("https://");
//        String serverName = request.getServerName();
//        String uri = request.getRequestURI();
//        String queryString = request.getQueryString();
//
//        String protoHeader = request.getHeader("X-Forwarded-Proto");
//        if ("http".equalsIgnoreCase(protoHeader))
//        {
//            if (StringUtils.isNotEmpty(serverName))
//            {
//                https.append(serverName);
//            }
//
//            if (StringUtils.isNotEmpty(uri))
//            {
//                https.append(uri);
//            }
//
//            if (StringUtils.isNotEmpty(queryString))
//            {
//                https.append(queryString);
//            }
//
//            response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
//            response.setHeader("Location", https.toString());
//            response.setHeader("Connection", "close");
////            response.sendRedirect(https.toString());
//        }

        chain.doFilter(req, res);
//        try
//        {
//            chain.doFilter(req, res);
//        }
//        catch (Exception exception)
//        {
//            try {
//                discordWebhook.sendDiscordWebHookError(exception, request);
////                throw exception;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }

    @Override
    public void destroy()
    {

    }
}
