package WoWSSSC.config;

import WoWSSSC.model.BlockIp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
@Slf4j
@Configuration
@EnableConfigurationProperties(CustomProperties.class)
@ComponentScan(basePackages = {"WoWSSSC"})
public class CustomFilter implements Filter
{
    @Autowired
    private HashMap<String, Integer> loadFinish;

    @Autowired
    private CustomProperties customProperties;

    private static HashSet<String> blockIP = new HashSet<>();

    private HashMap<String, BlockIp> ipMap = new HashMap<>();

    private static HashSet<String> ignoreUri = new HashSet<>();

    static
    {
        blockIP.add("52.71.155.178");

        ignoreUri.add("/favicon");
        ignoreUri.add("/js");
        ignoreUri.add("/css");
        ignoreUri.add("/images");
        ignoreUri.add("/json");
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

        String url = request.getRequestURL().toString();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();

        if ("https".equalsIgnoreCase(customProperties.getProtocol()) && url.contains("http://") && !url.contains("https://")) {
            url = url.replace("http", "https") + (StringUtils.isNotEmpty(queryString) ? "?" + queryString : "");
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
            response.setHeader("Location", url);
            chain.doFilter(request, response);
            return;
        }

        if (loadFinish.get("loadFinish") == 0 && !request.getRequestURI().equalsIgnoreCase("/") && !isIgnore(request.getRequestURI())) {
            request.getRequestDispatcher("/").forward(request, response);
            return;
        }

        if (StringUtils.isNotEmpty(queryString) && request.getQueryString().contains("/images/Icon/WoWSFT_Icon.png")) {
            response.sendRedirect(request.getRequestURI() + "?" + queryString.replace("/images/Icon/WoWSFT_Icon.png", ""));
            return;
        }

        String ipAddress = getClientIPAddress(request);

        if (request.getRequestURI().equalsIgnoreCase("/") && loadFinish.get("loadFinish") != 0)
        {
            if (!ipMap.containsKey(ipAddress))
            {
                ipMap.put(ipAddress, new BlockIp(ipAddress));
            }
            else
            {
                if (ipMap.get(ipAddress).getBlockCount() < 3)
                {
                    if (ipMap.get(ipAddress).getCount() < 5)
                    {
                        ipMap.get(ipAddress).doCount();
                    }
                    else
                    {
                        if (System.currentTimeMillis() - ipMap.get(ipAddress).getCreated().getTime() < 60 * 1000)
                        {
                            if (!blockIP.contains(ipAddress))
                            {
                                ipMap.get(ipAddress).setCreated(new Date()).setBlockCreated(new Date()).addBlockCount();
                                blockIP.add(ipAddress);

                                log.error("Blocked: " + ipAddress + ", count: " + ipMap.get(ipAddress).getBlockCount());
                            }
                        }
                        else
                        {
                            blockIP.remove(ipAddress);
                            ipMap.get(ipAddress).reset();
                        }
                    }
                }
                else
                {
                    if (System.currentTimeMillis() - ipMap.get(ipAddress).getBlockCreated().getTime() > 24 * 60 * 60 * 1000)
                    {
                        blockIP.remove(ipAddress);
                        ipMap.get(ipAddress).resetBlock();
                    }
                }
            }
        }

        if (blockIP.contains(ipAddress))
        {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        chain.doFilter(req, res);
    }

    @Override
    public void destroy()
    {

    }

    private boolean isIgnore(String address)
    {
        return ignoreUri.stream().anyMatch(ig -> address.toLowerCase().contains(ig));
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
