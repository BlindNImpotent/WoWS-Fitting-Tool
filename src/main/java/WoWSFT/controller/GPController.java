package WoWSFT.controller;

import WoWSFT.model.gameparams.commander.Commander;
import WoWSFT.model.gameparams.consumable.Consumable;
import WoWSFT.model.gameparams.modernization.Modernization;
import WoWSFT.model.gameparams.ship.Ship;
import WoWSFT.model.gameparams.ship.ShipIndex;
import WoWSFT.model.gameparams.ship.component.artillery.Artillery;
import WoWSFT.model.gameparams.ship.component.artillery.Shell;
import WoWSFT.model.gameparams.ship.component.atba.Secondary;
import WoWSFT.model.gameparams.ship.component.torpedo.TorpedoAmmo;
import WoWSFT.service.GPService;
import WoWSFT.service.ParamService;
import WoWSFT.service.ParserService;
import WoWSFT.utils.PenetrationUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static WoWSFT.model.Constant.*;

/**
 * Created by Aesis on 2016-10-15.
 */
@Controller
public class GPController extends ExceptionController
{
    @Autowired
    @Qualifier (value = "loadFinish")
    private HashMap<String, Integer> loadFinish;

    @Autowired
    @Qualifier (value = "gameParamsHM")
    private HashMap<String, Object> gameParamsHM;

    @Autowired
    @Qualifier (value = "notification")
    private LinkedHashMap<String, String> notification;

    @Autowired
    @Qualifier (value = "global")
    private HashMap<String, HashMap<String, Object>> global;

    @Autowired
    @Qualifier (value = TYPE_SHIP)
    private LinkedHashMap<String, Ship> ships;

    @Autowired
    @Qualifier (value =  TYPE_CONSUMABLE)
    private LinkedHashMap<String, Consumable> consumables;

    @Autowired
    @Qualifier (value = TYPE_SHIP_LIST)
    private LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<Integer, List<ShipIndex>>>>> shipsList;

    @Autowired
    @Qualifier (value = TYPE_UPGRADE)
    private LinkedHashMap<Integer, LinkedHashMap<String, Modernization>> upgrades;

    @Autowired
    @Qualifier (value = TYPE_COMMANDER)
    private LinkedHashMap<String, Commander> commanders;

    @Autowired
    private GPService gpService;

    @Autowired
    private ParamService paramService;

    @Autowired
    private ParserService parserService;

    private ObjectMapper mapper = new ObjectMapper();

    @ModelAttribute(name = "language")
    public void setLanguage(Model model, HttpServletRequest request, HttpServletResponse response)
    {
        String[] lang = request.getParameterMap().get("lang");
        if (lang == null || lang.length == 0) {
            model.addAttribute("lang", "en");
        } else {
            String l = "en";
            for (String s : lang) {
                if (globalLanguage.contains(s)) {
                    l = s;
                    break;
                }
            }
            model.addAttribute("lang", l);
        }
    }

    @ResponseBody
    @GetMapping(value = "/data")
    public Object tester(@RequestParam(required = false, defaultValue = "") String type,
                         @RequestParam(required = false, defaultValue = "") String index,
                         @RequestParam(required = false, defaultValue = "") String lang) throws Exception
    {
        if (type.equalsIgnoreCase(TYPE_SHIP)) {
            if (StringUtils.isNotEmpty(lang)) {
                return gpService.getShip(index);
            }
            return ships.get(index);
        } else if (type.equalsIgnoreCase(TYPE_UPGRADE)) {
            return upgrades;
        } else if (type.equalsIgnoreCase(TYPE_CONSUMABLE)) {
            return consumables;
        } else if (type.equalsIgnoreCase(TYPE_COMMANDER)) {
            return commanders;
        } else if (type.equalsIgnoreCase(TYPE_SHIP_LIST)) {
            return shipsList;
        }
        return gameParamsHM.get(index);
    }

    @GetMapping(value = "")
    public String getHome(Model model)
    {
        if (loadFinish.get("loadFinish") == 0) {
            return "loadPage";
        }

        model.addAttribute("notification", notification);

        return "home";
    }

    @RequestMapping(value = "/ship", method = { RequestMethod.GET, RequestMethod.POST })
    public String getWarship(HttpServletRequest request, Model model,
                             @RequestParam(required = false, defaultValue = "en") String lang,
                             @RequestParam(required = false, defaultValue = "") String index,
                             @RequestParam(required = false, defaultValue = "") String modules,
                             @RequestParam(required = false, defaultValue = "") String upgrades,
                             @RequestParam(required = false, defaultValue = "PAW001") String commander,
                             @RequestParam(required = false, defaultValue = "0") long skills) throws Exception
    {
        model.addAttribute("single", true);
        model.addAttribute("IDS", IDS);

        lang = globalLanguage.contains(lang) ? lang : "en";
        model.addAttribute("global", global.get(lang));

        if (StringUtils.isNotEmpty(index)) {
            model.addAttribute("index", index);
            model.addAttribute("dataIndex", 0);

            Commander crew = gpService.getCommander(commander);
            skills = skills > maxBitsToInt ? 0 : skills;

            Ship ship = getShip(index, modules, upgrades, skills, commander, crew, false);
            model.addAttribute(TYPE_WARSHIP, ship);

            if ("post".equalsIgnoreCase(request.getMethod())) {
                return "Joint/rightInfo :: rightInfo";
            }

            model.addAttribute(TYPE_SKILL, crew);
        }
        model.addAttribute("nations", shipsList);

        return "FittingTool/ftHome";
    }

    @ResponseBody
    @PostMapping(value = "/shipData")
    public Ship getWarshipData(@RequestParam(required = false, defaultValue = "en") String lang,
                               @RequestParam(required = false, defaultValue = "") String index,
                               @RequestParam(required = false, defaultValue = "") String modules,
                               @RequestParam(required = false, defaultValue = "") String upgrades,
                               @RequestParam(required = false, defaultValue = "PAW001") String commander,
                               @RequestParam(required = false, defaultValue = "0") long skills) throws Exception
    {
        if (StringUtils.isNotEmpty(index)) {
            return getShip(index, modules, upgrades, skills, commander, null, true);
        }
        throw new NullPointerException();
    }

    private Ship getShip(String index, String modules, String upgrades, long skills, String commander, Commander crew, boolean data) throws Exception
    {
        Ship ship = mapper.readValue(mapper.writeValueAsString(gpService.getShip(index)), Ship.class);
        parserService.parseModules(ship, modules);
        gpService.setShipAmmo(ship);
        parserService.parseUpgrades(ship, upgrades);
        parserService.parseSkills(ship, skills);
        paramService.setParameters(ship, data ? gpService.getCommander(commander) : crew);

        return ship;
    }

    @RequestMapping (value = "/arty", method = RequestMethod.GET)
    public String getArtyChart(Model model)
    {
        model.addAttribute("IDS", IDS);
        model.addAttribute("global", global.get("en"));
        model.addAttribute("nations", shipsList);

        return "ArtyChart/acHome";
    }

    @ResponseBody
    @RequestMapping (value = "/arty", method = RequestMethod.POST)
    public Artillery test123(@RequestParam String index,
                             @RequestParam String artyId)
    {
        return new Artillery();
//        return ships.get(index);
//        return gpService.setShipArty(nation, shipType, ship, shipId, artyId, new ArrayList<>(), true).getArtillery();
    }
}