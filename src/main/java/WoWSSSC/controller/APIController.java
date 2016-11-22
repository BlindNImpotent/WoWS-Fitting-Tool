package WoWSSSC.controller;

import WoWSSSC.model.shipprofile.Ship;
import WoWSSSC.service.APIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;

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

    private HashMap<String, Ship> shipAPIs = new HashMap<>();

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
        model.addAttribute("data", data);
        if (!nation.equals("") && !shipType.equals("") && !ship.equals(""))
        {
            model.addAttribute("warship", ((LinkedHashMap<String, LinkedHashMap>) data.get("nations").get(nation)).get(shipType).get(ship));
        }

        return "warshipPage :: warshipStats";
    }

    @RequestMapping (value = "/shipAPI", method = RequestMethod.GET)
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
                    @RequestParam(required = false, defaultValue = "") String Torpedoes
            )
    {

        String key = "&ship_id=" + ship_id + "&artillery_id=" + Artillery + "&dive_bomber_id=" + DiveBomber + "&engine_id=" + Engine
                + "&fighter_id=" + Fighter + "&fire_control_id=" + Suo + "&flight_control_id=" + FlightControl + "&hull_id=" + Hull + "&torpedo_bomber_id=" + TorpedoBomber + "&torpedoes_id=" + Torpedoes;

        if (!shipAPIs.containsKey(key))
        {
            Ship shipAPI = apiService.getShipAPI(ship_id, Artillery, DiveBomber, Engine, Fighter, Suo, FlightControl, Hull, TorpedoBomber, Torpedoes);

            if (shipAPI != null)
            {
                shipAPIs.put(key, shipAPI);
            }
        }
        model.addAttribute("shipAPI", shipAPIs.get(key));

        return "shipAPIPage :: shipAPIData";
    }
}