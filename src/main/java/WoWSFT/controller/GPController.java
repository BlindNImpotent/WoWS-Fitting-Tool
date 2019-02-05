package WoWSFT.controller;

import WoWSFT.model.gameparams.ship.Ship;
import WoWSFT.service.GPService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;

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
    private GPService gpService;

    private ObjectMapper mapper = new ObjectMapper();

    private HashMap<String, Ship> ships = new HashMap<>();

    private Ship getShipFromIndex(String index, String language) throws Exception {
        if (ships.size() == 0) {
            ships = mapper.convertValue(gameParamsHM.get(TYPE_SHIP), new TypeReference<HashMap<String, Ship>>(){});
        }
        return gpService.getShip(ships.get(index), gpService.getConsumables(), language);
    }

    @ResponseBody
    @GetMapping(value = "/data")
    public Object tester(@RequestParam String type,
                         @RequestParam(required = false) String ship) throws Exception
    {
        if (TYPE_SHIP.equalsIgnoreCase(type)) {
            return getShipFromIndex(ship, "en");
        }

        return mapper.convertValue(gameParamsHM.get(type), new TypeReference<LinkedHashMap<String, Object>>(){});
    }

    @GetMapping(value = "/")
    public String getHome(Model model)
    {
        model.addAttribute("notification", notification);

        return "home";
    }

    @GetMapping(value = "/ship")
    public String getWarship(Model model, @RequestParam(required = false, defaultValue = "") String index, @RequestParam(required = false, defaultValue = "en") String language) throws Exception
    {
        model.addAttribute("single", true);
        model.addAttribute("IDS", IDS);
        model.addAttribute("global", global.get(language));
        model.addAttribute("nations", gameParamsHM.get(TYPE_SHIP_LIST));

        if (StringUtils.isNotEmpty(index)) {
            model.addAttribute(TYPE_WARSHIP, getShipFromIndex(index.toUpperCase(), language));
            model.addAttribute(TYPE_UPGRADE, gpService.getUpgrades(language));
            model.addAttribute(TYPE_SKILL, gpService.getCommander(language));
        }

        return "FittingTool/ftHome";
    }
}