package WoWSSSC.controller;

import WoWSSSC.service.APIService;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Created by Aesis on 2016-10-15.
 */
@Controller
public class APIController
{
    @Autowired
    private APIService apiService;


    @RequestMapping (value = "/", method = RequestMethod.GET)
    public String getShip(Model model) throws IOException, ParseException
    {
        model.addAttribute("shipListJSON", apiService.getWarships());

        return "home";
    }
}