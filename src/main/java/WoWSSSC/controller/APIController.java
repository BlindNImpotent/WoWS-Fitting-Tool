package WoWSSSC.controller;

import WoWSSSC.model.ShipComponents;
import WoWSSSC.model.WoWSAPI.shipprofile.Ship;
import WoWSSSC.model.gameparams.test.GameParamsValues;
import WoWSSSC.model.WoWSAPI.skills.CrewSkills;
import WoWSSSC.service.APIService;
import WoWSSSC.service.GPService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Aesis on 2016-10-15.
 */
@Controller
public class APIController
{
    @Autowired
    private APIService apiService;

    @Autowired
    private GPService gpService;

    @Autowired
    private LinkedHashMap<String, LinkedHashMap> data;

    @Autowired
    private HashMap<String, LinkedHashMap> gameParamsCHM;

    @Autowired
    private LinkedHashMap<String, String> notification;

    private static final Logger logger = LoggerFactory.getLogger(APIController.class);

    ObjectMapper mapper = new ObjectMapper();

    @ResponseBody
    @RequestMapping (value = "/data", method = RequestMethod.GET)
    public LinkedHashMap<String, LinkedHashMap> getData()
    {
        return data;
    }

    @ResponseBody
    @RequestMapping (value = "/gameParams/{id}", method = RequestMethod.GET)
    public HashMap<String, LinkedHashMap> getGameParamsCHM(@PathVariable("id") String id)
    {
        return gameParamsCHM.get(id);
    }

    @RequestMapping (value = "/", method = RequestMethod.GET)
    public String home(HttpServletRequest request, Model model, @RequestParam(required = false, defaultValue="false") boolean mobile)
    {
        model.addAttribute("data", data);
        model.addAttribute("notification", notification);

        if (mobile)
        {
            return "mobile";
        }
        return "home";
    }

    @RequestMapping (value = "/warship", method = { RequestMethod.GET, RequestMethod.POST })
    public String getWarship
            (
                    HttpServletRequest request,
                    Model model,
                    RedirectAttributes redirectAttributes,
                    @RequestParam(required = false) String nation,
                    @RequestParam(required = false) String shipType,
                    @RequestParam(required = false) String ship,
                    @RequestParam(required = false) HashSet<String> modules,
                    @RequestParam(required = false) HashSet<String> upgrades,
                    @RequestParam(required = false) String skills,
                    @RequestParam(required = false) boolean camo,
                    @RequestParam(required = false, defaultValue = "false") boolean mobile
            ) throws IOException
    {
        model.addAttribute("notification", notification);
        if (nation != null && shipType != null && ship != null)
        {
            if (request.getMethod().equalsIgnoreCase("POST"))
            {
                logger.info("Loading " + nation + " " + shipType + " " + ship);
                model.addAttribute("warship", ((LinkedHashMap<String, LinkedHashMap>) data.get("nations").get(nation)).get(shipType).get(ship));
                return "warshipPage :: warshipStats";
            }
            else
            {
                logger.info("Loading " + nation + " " + shipType + " " + ship + " from /warship?" + request.getQueryString());

                HashSet<CrewSkills> crewSkills = skills != null ? mapper.readValue(skills, HashSet.class) : new HashSet<>();

                redirectAttributes.addFlashAttribute("url", "/warship?" + request.getQueryString());
                redirectAttributes.addFlashAttribute("modules", modules);
                redirectAttributes.addFlashAttribute("upgrades", upgrades);
                redirectAttributes.addFlashAttribute("crewSkills", crewSkills);
                redirectAttributes.addFlashAttribute("camo", camo);
                redirectAttributes.addFlashAttribute("warship", ((LinkedHashMap<String, LinkedHashMap>) data.get("nations").get(nation)).get(shipType).get(ship));
            }
        }
        return "redirect:/?mobile=" + mobile;
    }

    @RequestMapping (value = "/shipAPI", method = RequestMethod.POST)
    public String getShipAPI
            (
                    HttpServletRequest request,
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
                    @RequestParam(required = false, defaultValue = "false") boolean stockCompare,
                    @RequestParam(required = false, defaultValue = "false") boolean upgradeCompare,
                    @RequestBody(required = false) HashMap<String, List> upgradesSkills,
                    @RequestParam(required = false, defaultValue = "false") boolean mobile
            ) throws ExecutionException, InterruptedException, IOException
    {
        if (!ship_id.equals(""))
        {
            String returnedKey = apiService.setShipAPI(nation, shipType, ship, ship_id, Artillery, DiveBomber, Engine, Fighter, Suo, FlightControl, Hull, TorpedoBomber, Torpedoes);
            model.addAttribute("shipAPI", apiService.getUpgradeSkillStats(returnedKey, nation, shipType, ship, ship_id, upgradesSkills));

            model.addAttribute("upgradeCompare", upgradeCompare);
            if (upgradeCompare)
            {
                model.addAttribute("configurationAPI", apiService.getUpgradeSkillStats(returnedKey, nation, shipType, ship, ship_id, new HashMap<>()));
            }

            model.addAttribute("stockCompare", stockCompare);
            if (stockCompare)
            {
                String stockKey = apiService.setShipAPI(nation, shipType, ship, ship_id, "", "", "", "", "", "", "", "", "");
                model.addAttribute("stockAPI", apiService.getUpgradeSkillStats(stockKey, nation, shipType, ship, ship_id, new HashMap<>()));
            }
        }

        if (mobile)
        {
            return "shipAPIPageMobile :: shipAPIData";
        }
        return "shipAPIPage :: shipAPIData";
    }

    @ResponseBody
    @RequestMapping (value = "/gpService", method = RequestMethod.GET)
    public ShipComponents test
    (
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
            @RequestParam(required = false) List<String> modules
    ) throws IllegalAccessException
    {
        return gpService.setShipGP(nation, shipType, ship, ship_id, Artillery, DiveBomber, Engine, Fighter, Suo, FlightControl, Hull, TorpedoBomber, Torpedoes, modules);
    }
}