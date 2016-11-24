package WoWSSSC.controller;

import WoWSSSC.model.shipprofile.Ship;
import WoWSSSC.model.shipprofile.profile.Mobility;
import WoWSSSC.service.APIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Aesis on 2016-10-15.
 */
@Controller
public class APIController
{
    @Autowired
    private APIService apiService;

    @Autowired
    private LinkedHashMap<String, LinkedHashMap> data;

    @ResponseBody
    @RequestMapping (value = "/data", method = RequestMethod.GET)
    public LinkedHashMap<String, LinkedHashMap> getData()
    {
        return data;
    }

    @RequestMapping (value = "/", method = RequestMethod.GET)
    public String home(Model model)
    {
        model.addAttribute("data", data);

        return "home";
    }

    @RequestMapping (value = "/warship", method = RequestMethod.GET)
    public String getWarship
            (
                    Model model,
                    @RequestParam(required = false, defaultValue = "") String nation,
                    @RequestParam(required = false, defaultValue = "") String shipType,
                    @RequestParam(required = false, defaultValue = "") String ship
            )
    {
        if (!nation.equals("") && !shipType.equals("") && !ship.equals(""))
        {
            model.addAttribute("warship", ((LinkedHashMap<String, LinkedHashMap>) data.get("nations").get(nation)).get(shipType).get(ship));
        }

        return "warshipPage :: warshipStats";
    }

    @RequestMapping (value = "/shipAPI", method = RequestMethod.POST)
    public String getShipAPI
            (
                    Model model,
                    @RequestParam(required = false, defaultValue = "") String ship_id,
                    @RequestParam(required = false, defaultValue = "") String Artillery,
                    @RequestParam(required = false, defaultValue = "") String DiveBomber,
                    @RequestParam(required = false, defaultValue = "") String Engine,
                    @RequestParam(required = false, defaultValue = "") String Fighter,
                    @RequestParam(required = false, defaultValue = "") String Suo,
                    @RequestParam(required = false, defaultValue = "") String FlightControl,
                    @RequestParam(required = false, defaultValue = "") String Hull,
                    @RequestParam(required = false, defaultValue = "") String TorpedoBomber,
                    @RequestParam(required = false, defaultValue = "") String Torpedoes,
                    @RequestParam(required = false, defaultValue = "") String price_125000,
                    @RequestParam(required = false, defaultValue = "") String price_250000,
                    @RequestParam(required = false, defaultValue = "") String price_500000,
                    @RequestParam(required = false, defaultValue = "") String price_1000000,
                    @RequestParam(required = false, defaultValue = "") String price_2000000,
                    @RequestParam(required = false, defaultValue = "") String price_3000000,
                    @RequestBody(required = false) List<HashMap> skills
            )
    {
        if (!ship_id.equals(""))
        {
            String key = "&ship_id=" + ship_id + "&artillery_id=" + Artillery + "&dive_bomber_id=" + DiveBomber + "&engine_id=" + Engine
                    + "&fighter_id=" + Fighter + "&fire_control_id=" + Suo + "&flight_control_id=" + FlightControl + "&hull_id=" + Hull + "&torpedo_bomber_id=" + TorpedoBomber + "&torpedoes_id=" + Torpedoes;

            apiService.setShipAPI(ship_id, Artillery, DiveBomber, Engine, Fighter, Suo, FlightControl, Hull, TorpedoBomber, Torpedoes);

            List<String> upgrades = new ArrayList<>();
            upgrades.add(price_125000);
            upgrades.add(price_250000);
            upgrades.add(price_500000);
            upgrades.add(price_1000000);
            upgrades.add(price_2000000);
            upgrades.add(price_3000000);

            model.addAttribute("shipAPI", apiService.getUpgradeSkillStats(key, upgrades, skills));
        }

        return "shipAPIPage :: shipAPIData";
    }
}