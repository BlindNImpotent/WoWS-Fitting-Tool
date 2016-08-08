package WoWSSSC.controller;

import WoWSSSC.service.JSONService;
import org.apache.catalina.connector.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Aesis on 2016-08-08.
 */
@RestController
public class JSONController
{
    @Autowired
    private JSONService jsonService;

    @RequestMapping (value = "/GameParams", method = RequestMethod.GET)
    public JSONObject GameParams() throws IOException, ParseException
    {
        return jsonService.getGameParams();
    }
}
