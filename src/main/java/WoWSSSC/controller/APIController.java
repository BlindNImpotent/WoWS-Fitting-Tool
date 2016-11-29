package WoWSSSC.controller;

import WoWSSSC.model.warships.Warship;
import WoWSSSC.service.APIService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

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

    @Autowired
    private HashMap<String, HashMap> gameParamsCHM;

    private static final Logger logger = LoggerFactory.getLogger(APIController.class);

    @ResponseBody
    @RequestMapping (value = "/data", method = RequestMethod.GET)
    public LinkedHashMap<String, LinkedHashMap> getData()
    {
        return data;
    }

    @ResponseBody
    @RequestMapping (value = "/gameParams", method = RequestMethod.GET)
    public HashMap<String, HashMap> getGameParamsCHM()
    {
        return gameParamsCHM;
    }

    @RequestMapping (value = "/", method = RequestMethod.GET)
    public String home(Model model)
    {
        model.addAttribute("data", data);

        return "home";
    }

    @RequestMapping (value = "/warship", method = { RequestMethod.GET, RequestMethod.POST })
    public String getWarship
            (
                    HttpServletRequest request,
                    Model model,
                    RedirectAttributes redirectAttributes,
                    @RequestParam(required = false, defaultValue = "") String nation,
                    @RequestParam(required = false, defaultValue = "") String shipType,
                    @RequestParam(required = false, defaultValue = "") String ship
            )
    {
        if (!nation.equals("") && !shipType.equals("") && !ship.equals(""))
        {
            logger.info("Loading " + nation + " " + shipType + " " + ship);
            model.addAttribute("warship", ((LinkedHashMap<String, LinkedHashMap>) data.get("nations").get(nation)).get(shipType).get(ship));

            if (request.getMethod().equalsIgnoreCase("POST"))
            {
                return "warshipPage :: warshipStats";
            }
            else
            {
                redirectAttributes.addFlashAttribute("warship", ((LinkedHashMap<String, LinkedHashMap>) data.get("nations").get(nation)).get(shipType).get(ship));
            }
        }
        return "redirect:";
    }

    @RequestMapping (value = "/shipAPI", method = RequestMethod.POST)
    public String getShipAPI
            (
                    Model model,
                    @RequestParam(required = false, defaultValue = "") String nation,
                    @RequestParam(required = false, defaultValue = "") String shipType,
                    @RequestParam(required = false, defaultValue = "") String ship,
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
                    @RequestBody(required = false) HashMap<String, List> upgradesSkills
            )
    {
        if (!ship_id.equals(""))
        {
            String key = "&ship_id=" + ship_id + "&artillery_id=" + Artillery + "&dive_bomber_id=" + DiveBomber + "&engine_id=" + Engine
                    + "&fighter_id=" + Fighter + "&fire_control_id=" + Suo + "&flight_control_id=" + FlightControl + "&hull_id=" + Hull + "&torpedo_bomber_id=" + TorpedoBomber + "&torpedoes_id=" + Torpedoes;

            apiService.setShipAPI(ship_id, Artillery, DiveBomber, Engine, Fighter, Suo, FlightControl, Hull, TorpedoBomber, Torpedoes);

            model.addAttribute("shipAPI", apiService.getUpgradeSkillStats(key, nation, shipType, ship, upgradesSkills));
        }

        return "shipAPIPage :: shipAPIData";
    }
}