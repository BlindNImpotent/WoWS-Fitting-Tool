package WoWSSSC.controller;

import JAR.Calc;
import JAR.JSParser;
import Parser.API_Parser;
import Parser.GameParams_Parser;
import Parser.JSON_Parser;
import WoWSSSC.model.ShipNameList;
import WoWSSSC.service.HomeService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.*;
import java.util.List;

/**
 * Created by Aesis on 2016-08-04.
 */
@Controller
public class HomeController
{
    @Autowired
    private HomeService homeService;

    @RequestMapping (value = "/", method = RequestMethod.GET)
    public String home(Model model) throws IOException, ParseException
    {
        model.addAttribute("nameList", homeService.getNameList());

        return "home";
    }
}
