package WoWSSSC.controller;

import WoWSSSC.model.shipprofile.ShipData;
import WoWSSSC.model.warships.Warship;
import WoWSSSC.service.APIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
    private RestTemplate restTemplate;

    private Warship warship;

    @ResponseBody
    @RequestMapping (value = "/test", method = RequestMethod.GET)
    public LinkedHashMap<String, LinkedHashMap> data()
    {
        return apiService.getData();
    }

    @RequestMapping (value = "/", method = RequestMethod.GET)
    public String home(Model model)
    {
        model.addAttribute("data", apiService.getData());
        model.addAttribute("warship", warship);
        return "home";
    }

    @ResponseBody
    @RequestMapping (value = "/", method = RequestMethod.POST)
    public void getWarship(
            @RequestParam(required = false, defaultValue = "") String nation,
            @RequestParam(required = false, defaultValue = "") String shipType,
            @RequestParam(required = false, defaultValue = "") String ship
    )
    {
        if (!nation.equals("") && !shipType.equals("") && !ship.equals(""))
        {
            LinkedHashMap<String, LinkedHashMap> shipTypes = (LinkedHashMap<String, LinkedHashMap>) apiService.getData().get("nations").get(nation);
            LinkedHashMap<String, Warship> warships = shipTypes.get(shipType);
            warship = warships.get(ship);
        }
    }

    @ResponseBody
    @RequestMapping (value = "/shipAPI", method = RequestMethod.GET)
    public ShipData getShipParameterAPI(
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
        String url = "https://api.worldofwarships.com/wows/encyclopedia/shipprofile/?application_id=137f0721e1b1baf30d6dcd1968fc260c&artillery_id=3433017136&ship_id=4180621104&hull_id=3443273520";
        return restTemplate.getForObject(url, ShipData.class);
    }
}