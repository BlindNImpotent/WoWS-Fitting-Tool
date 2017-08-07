package WoWSSSC.controller;

import WoWSSSC.config.DiscordWebhook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class ExceptionController
{
    @Autowired
    private DiscordWebhook discordWebhook;

    @ExceptionHandler(Exception.class)
    public void sendError(Throwable t, HttpServletRequest request) throws Exception
    {
        log.error(t.getLocalizedMessage(), t);

        if (!discordWebhook.getClientIPAddress(request).equalsIgnoreCase("localhost"))
        {
            discordWebhook.sendDiscordWebHookError((Exception) t, request);
        }

        throw (Exception) t;
    }
}
