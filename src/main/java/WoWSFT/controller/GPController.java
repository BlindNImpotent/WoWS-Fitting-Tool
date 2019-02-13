package WoWSFT.controller;

import WoWSFT.model.gameparams.commander.Commander;
import WoWSFT.model.gameparams.consumable.Consumable;
import WoWSFT.model.gameparams.modernization.Modernization;
import WoWSFT.model.gameparams.ship.Ship;
import WoWSFT.model.gameparams.ship.ShipIndex;
import WoWSFT.service.GPService;
import WoWSFT.service.ParserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static WoWSFT.model.Constant.*;

/**
 * Created by Aesis on 2016-10-15.
 */
@Controller
public class GPController extends ExceptionController
{
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
    private ParserService parserService;

    private ObjectMapper mapper = new ObjectMapper();

    @ResponseBody
    @GetMapping(value = "/data")
    public Object tester(@RequestParam(required = false, defaultValue = "") String type,
                         @RequestParam(required = false, defaultValue = "") String index,
                         @RequestParam(required = false, defaultValue = "") String lang) throws Exception
    {
        if (type.equalsIgnoreCase(TYPE_SHIP)) {
            if (StringUtils.isNotEmpty(lang)) {
                return gpService.getShip(index, lang, "");
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

    @GetMapping(value = "/")
    public String getHome(Model model)
    {
        model.addAttribute("notification", notification);

        return "home";
    }

    @GetMapping(value = "/ship")
    public String getWarship(Model model, @RequestParam(required = false, defaultValue = "en") String language,
                             @RequestParam(required = false, defaultValue = "") String index,
                             @RequestParam(required = false, defaultValue = "") String modules) throws Exception
    {
        model.addAttribute("single", true);
        model.addAttribute("IDS", IDS);
        model.addAttribute("global", global.get(language));
        model.addAttribute("nations", shipsList);

        if (StringUtils.isNotEmpty(index)) {
            model.addAttribute("index", index);
            model.addAttribute("dataIndex", 0);
            model.addAttribute(TYPE_WARSHIP, gpService.getShip(index, language, modules));
            model.addAttribute(TYPE_SKILL, gpService.getCommander(language));
        }

        return "FittingTool/ftHome";
    }

    @ResponseBody
    @PostMapping(value = "/ship")
    public Ship getWarshipData(@RequestParam(required = false, defaultValue = "en") String language,
                             @RequestParam(required = false, defaultValue = "") String index,
                             @RequestParam(required = false, defaultValue = "") String modules) throws Exception
    {
        if (StringUtils.isNotEmpty(index)) {
            return gpService.getShip(index, language, modules);
        }
        throw new NullPointerException();
    }
}