package WoWSFT.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static WoWSFT.model.Constant.GENERAL_INTERNAL_ERROR;

@Slf4j
@ControllerAdvice
public class CustomControllerAdvice
{
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public void test(Throwable t, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        log.info(request.getRequestURL().toString() + (StringUtils.isNotEmpty(request.getQueryString()) ? "?" + request.getQueryString() : ""));
        log.error(t.getLocalizedMessage(), t);

        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        response.getWriter().write("{\"message\":\"" + GENERAL_INTERNAL_ERROR + "\",\"status\":\"1001\"}");
    }
}
