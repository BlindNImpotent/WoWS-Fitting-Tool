package WoWSSSC.controller;

import WoWSSSC.service.JSONService;
import org.apache.catalina.connector.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping (value = "/apiShipJSON/{name}", method = RequestMethod.GET)
    public JSONObject apiShipJSON(@PathVariable("name") String name) throws IOException, ParseException
    {
        jsonService.setShipJSON(name);
        return jsonService.getApiShipJSON();
    }

    @RequestMapping (value = "/gpShipJSON", method = RequestMethod.GET)
    public JSONObject GameParams() throws IOException, ParseException
    {
        return jsonService.getGpShipJSON();
    }

    @RequestMapping (value = "/GameParams/index/{index}", method = RequestMethod.GET)
    public JSONObject GameParamsIndexJSON(@PathVariable("index") String index)
    {
        return jsonService.getGameParamsIndexJSON(index);
    }

    @RequestMapping (value = "/GameParams/name/{name}", method = RequestMethod.GET)
    public JSONObject GameParamsNameJSON(@PathVariable("name") String name)
    {
        return jsonService.getGameParamsNameJSON(name);
    }

    @RequestMapping (value = "/API/module_hull/{moduleId}", method = RequestMethod.GET)
    public JSONObject Modules_HullJSON(@PathVariable("moduleId") String moduleId)
    {
        return jsonService.getAPI_HullJSON(moduleId);
    }

}
