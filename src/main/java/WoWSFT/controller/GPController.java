package WoWSFT.controller;

import WoWSFT.model.gameparams.commander.Commander;
import WoWSFT.model.gameparams.ship.Ship;
import WoWSFT.model.gameparams.ship.ShipIndex;
import WoWSFT.model.gameparams.ship.component.artillery.Shell;
import WoWSFT.service.GPService;
import WoWSFT.service.ParamService;
import WoWSFT.service.ParserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static WoWSFT.model.Constant.*;

@Slf4j
@Controller
public class GPController extends ExceptionController
{
    private final HashMap<String, Integer> loadFinish;
    private final LinkedHashMap<String, LinkedHashMap<String, String>> notification;
    private final LinkedHashMap<String, LinkedHashMap<String, String>> translation;
    private final HashMap<String, HashMap<String, Object>> global;
    private final LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<Integer, List<ShipIndex>>>>> shipsList;
    private final LinkedHashMap<String, Commander> commanders;
    private final ParamService paramService;
    private final ParserService parserService;
    private final GPService gpService;

    private static final String lang = "en";

    private ObjectMapper mapper = new ObjectMapper();

    public GPController(
            @Qualifier(value = "loadFinish") HashMap<String, Integer> loadFinish,
            @Qualifier(value = "notification") LinkedHashMap<String, LinkedHashMap<String, String>> notification,
            @Qualifier(value = "translation") LinkedHashMap<String, LinkedHashMap<String, String>> translation,
            @Qualifier(value = "global") HashMap<String, HashMap<String, Object>> global,
            @Qualifier(value = TYPE_SHIP_LIST) LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<Integer, List<ShipIndex>>>>> shipsList,
            @Qualifier(value = TYPE_COMMANDER) LinkedHashMap<String, Commander> commanders,
            GPService gpService,
            ParamService paramService,
            ParserService parserService)
    {
        this.loadFinish = loadFinish;
        this.notification = notification;
        this.translation = translation;
        this.global = global;
        this.shipsList = shipsList;
        this.commanders = commanders;
        this.gpService = gpService;
        this.paramService = paramService;
        this.parserService = parserService;
    }

    @ModelAttribute(name = "language")
    public void setLanguage(Model model)
    {
        model.addAttribute("lang", lang);
    }

    @GetMapping(value = "")
    public String getHome(Model model)
    {
        if (loadFinish.get("loadFinish") == 0) {
            return "loadPage";
        }
        model.addAttribute("notification", notification.get(lang.toLowerCase()));
        model.addAttribute("trans", translation.get(lang.toLowerCase()));

        return "home";
    }

    @RequestMapping(value = "/ship", method = { RequestMethod.GET, RequestMethod.POST })
    public String getWarship(HttpServletRequest request, Model model,
                             @RequestParam(required = false, defaultValue = "") String index,
                             @RequestParam(required = false, defaultValue = "") String modules,
                             @RequestParam(required = false, defaultValue = "") String upgrades,
                             @RequestParam(required = false, defaultValue = "") String consumables,
                             @RequestParam(required = false, defaultValue = "PCW001") String commander,
                             @RequestParam(required = false, defaultValue = "0") long skills,
                             @RequestParam(required = false, defaultValue = "100") int ar) throws Exception
    {
        model.addAttribute("single", true);
        model.addAttribute("IDS", IDS);

        model.addAttribute("global", global.get(lang.toLowerCase()));
        model.addAttribute("trans", translation.get(lang.toLowerCase()));

        if (StringUtils.isNotEmpty(index)) {
            model.addAttribute("index", index.toUpperCase());
            model.addAttribute("dataIndex", 0);
            model.addAttribute("commanders", commanders);
            skills = skills > maxBitsToInt ? 0 : skills;

            Ship ship = getShip(index.toUpperCase(), modules, upgrades, consumables, skills, commander.toUpperCase(), ar);
            model.addAttribute(TYPE_WARSHIP, ship);

            if ("post".equalsIgnoreCase(request.getMethod())) {
                return "Joint/rightInfo :: rightInfo";
            }
        }
        model.addAttribute("nations", shipsList);

        return "FittingTool/ftHome";
    }

    private Ship getShip(String index, String modules, String upgrades, String consumables, long skills, String commander, int ar) throws Exception
    {
        Ship ship = mapper.readValue(mapper.writeValueAsString(gpService.getShip(index)), Ship.class);
        parserService.parseModules(ship, modules);
        gpService.setShipAmmo(ship);
        parserService.parseConsumables(ship, consumables);
        parserService.parseUpgrades(ship, upgrades);
        parserService.parseSkills(ship, skills, ar);
        paramService.setAA(ship);

        if (!"PCW001".equals(commander)
                && (commanders.get(commander) == null || !commanders.get(commander).getCrewPersonality().getShips().getNation().contains(ship.getTypeinfo().getNation()))) {
            commander = "PCW001";
        }
        ship.setCommander(commanders.get(commander));
        paramService.setParameters(ship);

        return ship;
    }

    @RequestMapping(value = "/arty", method = RequestMethod.GET)
    public String getArtyChart(Model model)
    {
        model.addAttribute("IDS", IDS);
        model.addAttribute("global", global.get(lang.toLowerCase()));
        model.addAttribute("trans", translation.get(lang.toLowerCase()));
        model.addAttribute("nations", shipsList);

        return "ArtyChart/acHome";
    }

    @ResponseBody
    @RequestMapping(value = "/arty", method = RequestMethod.POST)
    public Shell getShellData(@RequestParam String index, @RequestParam String artyId) throws Exception
    {
        return gpService.getArtyAmmoOnly(index, artyId);
    }

    @GetMapping(value = "/research")
    public String getResearch(Model model)
    {
        model.addAttribute("global", global.get(lang.toLowerCase()));
        model.addAttribute("trans", translation.get(lang.toLowerCase().toLowerCase()));
        model.addAttribute("IDS", IDS);
        model.addAttribute("nations", shipsList);

        return "Research/shipTree";
    }
}