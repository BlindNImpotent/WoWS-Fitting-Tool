package WoWSSSC.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Aesis on 2017. 5. 21..
 */
@Configuration
@ComponentScan(basePackages = {"WoWSSSC"})
public class CustomFilter implements Filter
{
    @Autowired
    private DiscordWebhook discordWebhook;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

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

        try
        {
            chain.doFilter(req, res);
        }
        catch (Exception exception)
        {
            try {
                discordWebhook.sendDiscordWebHookError(exception, request);
//                throw exception;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void destroy()
    {

    }
}
