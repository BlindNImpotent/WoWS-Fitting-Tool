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

//    @RequestMapping (value = "/", method = RequestMethod.GET)
//    public String home(Model model) throws IOException, ParseException
//    {
//        model.addAttribute("nameList", homeService.getNameList());
//
//        return "home";
//    }

    @RequestMapping (value = "/", method = RequestMethod.GET)
    public String ship(Model model, @RequestParam(required = false, defaultValue = "") String shipName) throws IOException, ParseException
    {
        model.addAttribute("nameList", homeService.getNameList());

        if (!shipName.equals(""))
        {
            jsonService.setShipJSON(shipName);

            model.addAttribute("name", shipName);

            model.addAttribute("imagesMedium", jsonService.getImagesMedium());
            model.addAttribute("ship_id_str", jsonService.getShip_id_str());

            model.addAttribute("nation", jsonService.getNation());

            model.addAttribute("gpShipJSON", jsonService.getGpShipJSON());

            model.addAttribute("turretUpgradeList", jsonService.getAPI_ArtilleryUpgrade());
            model.addAttribute("hullUpgradeList", jsonService.getAPI_HullUpgrade());
            model.addAttribute("engineUpgradeList", jsonService.getAPI_EngineUpgrade());
            model.addAttribute("radarUpgradeList", jsonService.getAPI_RadarUpgrade());
            model.addAttribute("torpedoUpgradeList", jsonService.getAPI_TorpedoUpgrade());
            model.addAttribute("flightControlUpgradeList", jsonService.getAPI_FlightControlUpgrade());
            model.addAttribute("fighterUpgradeList", jsonService.getAPI_FighterUpgrade());
            model.addAttribute("torpedoBomberUpgradeList", jsonService.getAPI_TorpedoBomberUpgrade());
            model.addAttribute("diveBomberUpgradeList", jsonService.getAPI_DiveBomberUpgrade());

            model.addAttribute("apiTurretJSON", jsonService.getAPI_ArtilleryUpgradeJSON());
            model.addAttribute("apiHullJSON", jsonService.getAPI_HullUpgradeJSON());
            model.addAttribute("apiEngineJSON", jsonService.getAPI_EngineUpgradeJSON());
            model.addAttribute("apiRadarJSON", jsonService.getAPI_RadarUpgradeJSON());
            model.addAttribute("apiTorpedoJSON", jsonService.getAPI_TorpedoUpgradeJSON());
            model.addAttribute("apiFlightControlJSON", jsonService.getAPI_FlightControlUpgradeJSON());
            model.addAttribute("apiFighterJSON", jsonService.getAPI_FighterUpgradeJSON());
            model.addAttribute("apiTorpedoBomberJSON", jsonService.getAPI_TorpedoBomberUpgradeJSON());
            model.addAttribute("apiDiveBomberJSON", jsonService.getAPI_DiveBomberUpgradeJSON());

            model.addAttribute("turretIndexList", jsonService.getAPI_ArtilleryUpgradeIndexList());
            model.addAttribute("hullIndexList", jsonService.getAPI_HullUpgradeIndexList());
            model.addAttribute("engineIndexList", jsonService.getAPI_EngineUpgradeIndexList());
            model.addAttribute("radarIndexList", jsonService.getAPI_RadarUpgradeIndexList());
            model.addAttribute("torpedoIndexList", jsonService.getAPI_TorpedoUpgradeIndexList());
            model.addAttribute("flightControlIndexList", jsonService.getAPI_FlightControlUpgradeIndexList());
            model.addAttribute("fighterIndexList", jsonService.getAPI_FighterUpgradeIndexList());
            model.addAttribute("torpedoBomberIndexList", jsonService.getAPI_TorpedoBomberUpgradeIndexList());
            model.addAttribute("diveBomberIndexList", jsonService.getAPI_DiveBomberUpgradeIndexList());

            model.addAttribute("modSlot1", jsonService.getModSlot1());
            model.addAttribute("modSlot2", jsonService.getModSlot2());
            model.addAttribute("modSlot3", jsonService.getModSlot3());
            model.addAttribute("modSlot4", jsonService.getModSlot4());
            model.addAttribute("modSlot5", jsonService.getModSlot5());
            model.addAttribute("modSlot6", jsonService.getModSlot6());
        }

        return "home";
    }
}
