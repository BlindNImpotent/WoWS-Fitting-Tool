package WoWSSSC.controller;

import JAR.Calc;
import JAR.JSParser;
import Parser.API_Parser;
import Parser.GameParams_Parser;
import Parser.JSON_Parser;
import WoWSSSC.model.ShipNameList;
import WoWSSSC.service.HomeService;
import WoWSSSC.service.JSONService;
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

    @Autowired
    private JSONService jsonService;

    @RequestMapping (value = "/ship/{name}", method = RequestMethod.GET)
    public String home(Model model, @PathVariable("name") String name) throws IOException, ParseException
    {
        jsonService.setShipJSON(name);

        model.addAttribute("nameList", homeService.getNameList());
        model.addAttribute("name", name);
        model.addAttribute("ship_id_str", jsonService.getShip_id_str());

        model.addAttribute("nation", jsonService.getNation());

        model.addAttribute("gpShipJSON", jsonService.getGpShipJSON());

        model.addAttribute("turretList", jsonService.getAPI_ArtilleryUpgrade());
        model.addAttribute("hullList", jsonService.getAPI_HullUpgrade());
        model.addAttribute("engineList", jsonService.getAPI_EngineUpgrade());
        model.addAttribute("radarList", jsonService.getAPI_RadarUpgrade());
        model.addAttribute("torpedoList", jsonService.getAPI_TorpedoUpgrade());

//        model.addAttribute("apiTurretJSON", jsonService.getAPI_ArtilleryUpgradeJSON());
//        model.addAttribute("apiHullJSON", jsonService.getAPI_HullUpgradeJSON());
//        model.addAttribute("apiEngineJSON", jsonService.getAPI_EngineUpgradeJSON());
//        model.addAttribute("apiRadarJSON", jsonService.getAPI_RadarUpgradeJSON());
//        model.addAttribute("apiTorpedoJSON", jsonService.getAPI_TorpedoUpgradeJSON());

        return "ship";
    }
}
