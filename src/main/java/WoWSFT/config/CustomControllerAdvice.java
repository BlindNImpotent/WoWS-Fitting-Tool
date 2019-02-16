package WoWSFT.config;

import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static WoWSFT.model.Constant.GENERAL_INTERNAL_ERROR;

@ControllerAdvice
public class CustomControllerAdvice
{
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public void test(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        response.getWriter().write("{\"message\":\"" + GENERAL_INTERNAL_ERROR + "\",\"status\":\"1001\"}");
    }
}
