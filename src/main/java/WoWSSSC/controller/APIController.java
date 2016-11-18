package WoWSSSC.controller;

import WoWSSSC.model.warships.Ship;
import WoWSSSC.service.APIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @RequestMapping (value = "/", method = RequestMethod.GET)
    public String test(
            Model model,
            @RequestParam(required = false, defaultValue = "") String nation,
            @RequestParam(required = false, defaultValue = "") String shipType,
            @RequestParam(required = false, defaultValue = "") String ship,
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

        if (!nation.equals("") && !shipType.equals("") && !ship.equals(""))
        {
            LinkedHashMap<String, LinkedHashMap> shipTypes = (LinkedHashMap<String, LinkedHashMap>) apiService.getData().get("nations").get(nation);
            LinkedHashMap<String, Ship> ships = shipTypes.get(shipType);
            Ship warship = ships.get(ship);

            model.addAttribute("warship", warship);
        }

        model.addAttribute("data", apiService.getData());

        return "home";
    }
}