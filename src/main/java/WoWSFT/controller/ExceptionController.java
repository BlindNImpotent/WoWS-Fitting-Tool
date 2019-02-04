package WoWSFT.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class ExceptionController
{
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public void doIndexError(Throwable t, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        if (request.getMethod().equalsIgnoreCase("get")) {
            response.sendRedirect(request.getRequestURI());
        }
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("");
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public String sendError(Throwable t, HttpServletRequest request) throws Exception
    {
        if (t instanceof NullPointerException) {
            log.info("Null Point");
            return "";
        } else {
            log.error(t.getLocalizedMessage(), t);
        }

        throw (Exception) t;
    }
}
