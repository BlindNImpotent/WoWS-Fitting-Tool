package WoWSSSC.controller;

import WoWSSSC.service.APIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;

/**
 * Created by Aesis on 2016-10-15.
 */
@Controller
public class APIController
{
    @Autowired
    private APIService apiService;

    @ResponseBody
    @RequestMapping (value = "/test", method = RequestMethod.GET)
    public LinkedHashMap<String, LinkedHashMap> data()
    {
        return apiService.getData();
    }
}