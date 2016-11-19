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

    @RequestMapping (value = "/", method = RequestMethod.GET)
    public String home
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

        return "home";
    }

    @RequestMapping (value = "/shipAPI", method = RequestMethod.GET)
    public String getShipParameterAPI
            (
                    Model model,
                    @RequestParam(required = false, defaultValue = "") String ship_id,
                    @RequestParam(required = false, defaultValue = "") String artillery_id,
                    @RequestParam(required = false, defaultValue = "") String dive_bomber_id,
                    @RequestParam(required = false, defaultValue = "") String engine_id,
                    @RequestParam(required = false, defaultValue = "") String fighter_id,
                    @RequestParam(required = false, defaultValue = "") String fire_control_id,
                    @RequestParam(required = false, defaultValue = "") String flight_control_id,
                    @RequestParam(required = false, defaultValue = "") String hull_id,
                    @RequestParam(required = false, defaultValue = "") String torpedo_bomber_id,
                    @RequestParam(required = false, defaultValue = "") String torpedoes_id
            )
    {
        String key = "&ship_id=" + ship_id + "&artillery_id=" + artillery_id + "&dive_bomber_id=" + dive_bomber_id + "&engine_id=" + engine_id
                + "&fighter_id=" + fighter_id + "&fire_control_id=" + fire_control_id + "&flight_control_id=" + flight_control_id + "&hull_id=" + hull_id + "&torpedo_bomber_id=" + torpedo_bomber_id + "&torpedoes_id=" + torpedoes_id;

        if (!shipAPIs.containsKey(key))
        {
            shipAPIs.put(key, apiService.getShipAPI(ship_id, artillery_id, dive_bomber_id, engine_id, fighter_id, fire_control_id, flight_control_id, hull_id, torpedo_bomber_id, torpedoes_id));
        }

        model.addAttribute("shipAPI", shipAPIs.get(key));

        return "shipAPI :: shipAPIStats";
    }
}