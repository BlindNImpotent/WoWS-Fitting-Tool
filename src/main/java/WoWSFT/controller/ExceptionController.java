package WoWSFT.controller;

import WoWSFT.config.CustomMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import static WoWSFT.model.Constant.GENERAL_INTERNAL_ERROR;

@Slf4j
public class ExceptionController
{
    @ResponseBody
    @ExceptionHandler({NullPointerException.class, IndexOutOfBoundsException.class})
    public CustomMessage sendError(Throwable t, HttpServletRequest request)
    {
        t.printStackTrace();

        log.info(t.getLocalizedMessage(), request.getRequestURL().toString() + (StringUtils.isNotEmpty(request.getQueryString()) ? "?" + request.getQueryString() : ""));

        return new CustomMessage("1001", GENERAL_INTERNAL_ERROR);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public CustomMessage otherErrors(Throwable t, HttpServletRequest request)
    {
        log.info(request.getRequestURL().toString() + (StringUtils.isNotEmpty(request.getQueryString()) ? "?" + request.getQueryString() : ""));
        log.error(t.getLocalizedMessage(), t);

        return new CustomMessage("1001", GENERAL_INTERNAL_ERROR);
    }
}
