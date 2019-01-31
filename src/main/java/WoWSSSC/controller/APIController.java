package WoWSSSC.controller;

import WoWSSSC.model.WoWSAPI.info.Encyclopedia;
import WoWSSSC.model.WoWSAPI.warships.Warship;
import WoWSSSC.model.WoWSAPI.skills.CrewSkills;
import WoWSSSC.model.gameparams.ShipComponents.Artillery.Artillery;
import WoWSSSC.service.APIService;
import WoWSSSC.service.GPService;
import WoWSSSC.service.ParserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.*;

/**
 * Created by Aesis on 2016-10-15.
 */
@Controller
public class APIController extends ExceptionController
{
    @Autowired
    private APIService apiService;

    @Autowired
    private GPService gpService;

    @Autowired
    private ParserService parserService;

    @Autowired
    private HashMap<String, LinkedHashMap<String, LinkedHashMap>> data;

    @Autowired
    private LinkedHashMap<String, String> notification;

    @Autowired
    private HashMap<String, Integer> loadFinish;

    private static final Logger logger = LoggerFactory.getLogger(APIController.class);

    private ObjectMapper mapper = new ObjectMapper();

    private boolean isLive = true;
    private String serverParam = "live";
    private String serverParamAddress = "";

    private Encyclopedia encyclopedia;

    @ResponseBody
    @RequestMapping (value = "/data")
    public Warship getData(@RequestParam String nation, @RequestParam String shipType, @RequestParam String ship)
    {
        return (Warship) ((LinkedHashMap<String, LinkedHashMap>) data.get(serverParam).get("nations").get(nation)).get(shipType).get(ship);
    }

    @RequestMapping (value = "", method = RequestMethod.GET)
    public String home(Model model)
    {
        if (loadFinish.get("loadFinish") == 0) {
            return "loadPage";
        }

        model.addAttribute("isLive", isLive);
        model.addAttribute("serverParam", serverParamAddress);
        model.addAttribute("notification", notification);

        if (encyclopedia == null) {
            encyclopedia = mapper.convertValue(data.get(serverParam).get("encyclopedia"), Encyclopedia.class);
        }
        model.addAttribute("encyclopedia", encyclopedia);

        return "home";
    }

    @RequestMapping (value = "/WarshipStats", method = RequestMethod.GET)
    public String WarshipStats( HttpServletRequest request,
                                Model model,
                                @RequestParam(required = false) String nation,
                                @RequestParam(required = false) String shipType,
                                @RequestParam(required = false) String ship,
                                @RequestParam(required = false) HashSet<String> modules,
                                @RequestParam(required = false) HashSet<String> upgrades,
//                                @RequestParam(required = false) HashSet<String> flags,
                                @RequestParam(required = false) HashSet<String> consumables,
                                @RequestParam(required = false) String skills,
                                @RequestParam(required = false) HashSet<String> skillsH,
                                @RequestParam(required = false) String uSkills,
                                @RequestParam(required = false) HashSet<String> uSkillsH,
                                @RequestParam(required = false, defaultValue = "100") int adrenalineValue,
                                @RequestParam(required = false, defaultValue = "100") int ar,
                                @RequestParam(required = false, defaultValue = "false") boolean camo,
                                @RequestParam(required = false, defaultValue = "default") String commander,
                                @RequestParam(required = false, defaultValue = "") String moduleN,
                                @RequestParam(required = false, defaultValue = "") String upgradeN,
                                @RequestParam(required = false, defaultValue = "") String skillN,
                                @RequestParam(required = false, defaultValue = "") String uSkillN,
//                                @RequestParam(required = false, defaultValue = "") String flagN,
                                @RequestParam(required = false, defaultValue = "") String s0,
                                @RequestParam(required = false, defaultValue = "") String s1,
                                @RequestParam(required = false, defaultValue = "") String s2,
                                @RequestParam(required = false, defaultValue = "") String s3,
                                @RequestParam(required = false, defaultValue = "") String dataIndex,
                                @RequestParam(required = false, defaultValue = "true") boolean single) throws IOException
    {
        if (modules != null && modules.size() <= 1) {
            return "redirect:/WarshipStats?nation=" + nation +"&shipType=" + shipType + "&ship=" + ship;
        }

        model.addAttribute("serverParam", serverParamAddress);
        model.addAttribute("nations", data.get(serverParam).get("nations"));
        model.addAttribute("uniqueSkills", data.get(serverParam).get("uniqueSkills"));
        model.addAttribute("skills", data.get(serverParam).get("skills"));
        model.addAttribute("exteriors", data.get(serverParam).get("exteriors"));
        model.addAttribute("notification", notification);
        model.addAttribute("encyclopedia", data.get(serverParam).get("encyclopedia"));
        model.addAttribute("single", single);
        
        if (StringUtils.isNotEmpty(nation) && StringUtils.isNotEmpty(shipType) && StringUtils.isNotEmpty(ship)) {
            ship = URLDecoder.decode(ship, "UTF-8");

            logger.info("Loading " + nation + " " + shipType + " " + ship + " from /WarshipStats?" + request.getQueryString());

            Warship warship = (Warship) ((LinkedHashMap<String, LinkedHashMap>) data.get(serverParam).get("nations").get(nation)).get(shipType).get(ship);

            if (StringUtils.isNotEmpty(moduleN)) {
                modules = parserService.getModules(warship, moduleN);
            }

            if (StringUtils.isNotEmpty(upgradeN)) {
                upgrades = parserService.getUpgrades(warship, upgradeN);
            }

            HashSet<CrewSkills> crewSkills = parserService.getSkills(skills, skillN, skillsH);
            HashSet<String> crewUSkills = parserService.getUSkills(serverParam, nation, commander, uSkills, uSkillN, uSkillsH);

//            if (StringUtils.isNotEmpty(flagN)) {
//                flags = parserService.getFlags(serverParam, flagN);
//            }

            if (StringUtils.isNotEmpty(s0) || StringUtils.isNotEmpty(s1) || StringUtils.isNotEmpty(s2) || StringUtils.isNotEmpty(s3)) {
                consumables = parserService.getConsumables(s0, s1, s2, s3);
            }

            model.addAttribute("url", "/warship?" + request.getQueryString());
            model.addAttribute("modules", modules);
            model.addAttribute("upgrades", upgrades);
            model.addAttribute("adrenalineValue", ar != 100 ? ar : adrenalineValue);
//            model.addAttribute("flags", flags);
            model.addAttribute("consumables", consumables);
            model.addAttribute("crewSkills", crewSkills);
            model.addAttribute("crewUSkills", crewUSkills);
            model.addAttribute("camo", camo);
            model.addAttribute("warship", warship);
            model.addAttribute("nation", nation).addAttribute("shipType", shipType).addAttribute("ship", ship);
            model.addAttribute("commanders", ((LinkedHashMap<String, LinkedHashMap>) data.get(serverParam).get("commanders").get(nation)).keySet());

            commander = URLDecoder.decode(commander, "UTF-8");
            commander = commander.equalsIgnoreCase("Steven Seagal") ? "John Doe" : commander;
            model.addAttribute("sCommander", commander);
            model.addAttribute("dataIndex", dataIndex);
        }

        return single ? "FittingTool/ftHome" : "Joint/shipSelect :: warshipStats";
    }

    @RequestMapping (value = "/warship", method = { RequestMethod.GET, RequestMethod.POST })
    public String getWarship(HttpServletRequest request,
                            Model model,
                            @RequestParam(required = false) String nation,
                            @RequestParam(required = false) String shipType,
                            @RequestParam(required = false) String ship,
                            @RequestParam(required = false, defaultValue = "true") boolean single,
                            @RequestParam(required = false, defaultValue = "default") String commander) throws IOException
    {
        if (nation != null && shipType != null && ship != null) {
            ship = URLDecoder.decode(ship, "UTF-8");

            if (request.getMethod().equalsIgnoreCase("post")) {
                model.addAttribute("serverParam", serverParamAddress);
                model.addAttribute("nations", data.get(serverParam).get("nations"));
                model.addAttribute("uniqueSkills", data.get(serverParam).get("uniqueSkills"));
                model.addAttribute("skills", data.get(serverParam).get("skills"));
                model.addAttribute("exteriors", data.get(serverParam).get("exteriors"));
                model.addAttribute("notification", notification);
                model.addAttribute("encyclopedia", data.get(serverParam).get("encyclopedia"));
                model.addAttribute("single", single);

                logger.info("Loading " + nation + " " + shipType + " " + ship);
                model.addAttribute("warship", ((LinkedHashMap<String, LinkedHashMap>) data.get(serverParam).get("nations").get(nation)).get(shipType).get(ship));
                model.addAttribute("commanders", ((LinkedHashMap<String, LinkedHashMap>) data.get(serverParam).get("commanders").get(nation)).keySet());

                commander = URLDecoder.decode(commander, "UTF-8");
                commander = commander.equalsIgnoreCase("Steven Seagal") ? "John Doe" : commander;
                model.addAttribute("sCommander", commander);

                return "Joint/shipSelect :: warshipStats";
            }
            return "redirect:/WarshipStats?" + request.getQueryString();
        }
        return "redirect:/WarshipStats";
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
                    @RequestParam(required = false, defaultValue = "100") int adrenalineValue,
                    @RequestParam(required = false, defaultValue = "100") int ar,
                    @RequestParam(required = false) List<String> modules,
                    @RequestParam(required = false, defaultValue = "") String moduleN,
                    @RequestParam(required = false, defaultValue = "") String upgradeN,
                    @RequestParam(required = false, defaultValue = "") String skillN,
                    @RequestParam(required = false, defaultValue = "") String uSkillN,
                    @RequestParam(required = false, defaultValue = "default") String commander,
//                    @RequestParam(required = false, defaultValue = "") String flagN,
                    @RequestParam(required = false, defaultValue = "false") boolean camo,
                    @RequestParam(required = false, defaultValue = "false") boolean upgradeCompare,
                    @RequestBody(required = false) HashMap<String, List> upgradesSkills,
                    @RequestParam(required = false, defaultValue = "") String s0,
                    @RequestParam(required = false, defaultValue = "") String s1,
                    @RequestParam(required = false, defaultValue = "") String s2,
                    @RequestParam(required = false, defaultValue = "") String s3,
                    @RequestParam(required = false, defaultValue = "true") boolean isFT,
                    @RequestParam(required = false, defaultValue = "0") String dataIndex,
                    @RequestParam(required = false, defaultValue = "true") boolean single) throws Exception
    {
        if (!ship_id.equals("")) {
            ship = URLDecoder.decode(ship, "UTF-8");

            logger.info("Ship API - " + nation + " " + shipType + " " + ship);

            Warship warship = (Warship) ((LinkedHashMap<String, LinkedHashMap>) data.get(serverParam).get("nations").get(nation)).get(shipType).get(ship);

            parserService.setApiModules(model,
                    serverParam,
                    nation, shipType, ship, ship_id, warship,
                    moduleN, modules, Artillery, DiveBomber, Engine, Fighter, Suo, FlightControl, Hull, TorpedoBomber, Torpedoes,
                    upgradesSkills, upgradeN, commander, skillN, uSkillN, camo, "", adrenalineValue, ar, s0, s1, s2, s3, upgradeCompare, dataIndex, isLive);

            model.addAttribute("single", single);
        }

        return "Joint/apiPage :: shipAPIData";
    }

    @RequestMapping (value = "/shipStatComparison", method = RequestMethod.GET)
    public String shipStatComparisonTree(Model model, HttpServletRequest request)
    {
        model.addAttribute("serverParam", serverParamAddress);
        model.addAttribute("nations", data.get(serverParam).get("nations"));
        model.addAttribute("encyclopedia", data.get(serverParam).get("encyclopedia"));

        return "ShipComparison/scHome";
    }

    @RequestMapping (value = "/shipStatSelection", method = RequestMethod.GET)
    public String shipStatSelection()
    {
        return "redirect:/shipStatComparison";
    }

    @RequestMapping (value = "/shipTree", method = RequestMethod.GET)
    public String shipTree(Model model)
    {
        model.addAttribute("serverParam", serverParamAddress);
        model.addAttribute("encyclopedia", data.get(serverParam).get("encyclopedia"));
        model.addAttribute("nations", data.get(serverParam).get("nations"));

        return "WarshipResearch/shipTree";
    }

    @ResponseBody
    @RequestMapping (value = "/shipTree", method = RequestMethod.POST)
    public long getXp(@RequestBody List<String> shipList)
    {
        logger.info("Calculating xp between " + shipList.get(0) + " and " + shipList.get(1));

        long xp = apiService.getXp(shipList, isLive);

        if (xp >= 0) {
            logger.info(String.format("%,d", xp)  + " xp required");
        }
        else {
            logger.info("Wrong path of research");
        }

        return xp;
    }

    @RequestMapping (value = "/arty", method = RequestMethod.GET)
    public String getArtyChart(Model model)
    {
        model.addAttribute("serverParam", serverParamAddress);
        model.addAttribute("nations", data.get(serverParam).get("nations"));
        model.addAttribute("encyclopedia", data.get(serverParam).get("encyclopedia"));

        return "ArtyChart/acHome";
    }

    @ResponseBody
    @RequestMapping (value = "/arty", method = RequestMethod.POST)
    public Artillery test123(@RequestParam String nation,
                             @RequestParam String shipType,
                             @RequestParam String ship,
                             @RequestParam String shipId,
                             @RequestParam String artyId) throws Exception
    {
        return gpService.setShipArty(nation, shipType, ship, shipId, artyId, new ArrayList<>(), true).getArtillery();
    }

    @ResponseBody
    @RequestMapping (value = "/shortenUrl", method = RequestMethod.POST)
    public String getShortUrl(@RequestBody String longUrl) throws Exception
    {
        return apiService.shortenUrl(longUrl);
    }
}