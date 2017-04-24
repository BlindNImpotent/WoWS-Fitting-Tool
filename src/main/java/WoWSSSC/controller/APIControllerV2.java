package WoWSSSC.controller;

import WoWSSSC.model.ShipComponents;
import WoWSSSC.model.WoWSAPI.shipprofile.Ship;
import WoWSSSC.model.WoWSAPI.skills.CrewSkills;
import WoWSSSC.model.WoWSAPI.warships.Warship;
import WoWSSSC.model.WoWSAPI.warships.WarshipModulesTree;
import WoWSSSC.service.APIService;
import WoWSSSC.service.APIServiceV2;
import WoWSSSC.service.GPService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Aesis on 2016-10-15.
 */
@Controller
public class APIControllerV2
{
    @Autowired
    private APIServiceV2 apiServiceV2;

    @Autowired
    private GPService gpService;

    @Autowired
    private LinkedHashMap<String, LinkedHashMap> data;

    @Autowired
    private HashMap<String, LinkedHashMap> gameParamsCHM;

    @Autowired
    private LinkedHashMap<String, String> notification;

    @Autowired
    private HashMap<String, Ship> shipHashMap;

    private static final Logger logger = LoggerFactory.getLogger(APIControllerV2.class);

    ObjectMapper mapper = new ObjectMapper();

    @RequestMapping (value = "/ship", method = RequestMethod.GET)
    public String getWarship
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
//                    @RequestParam(required = false) List<String> modules,
                    @RequestBody(required = false) HashMap<String, List> upgradesSkills,
                    @RequestParam(required = false, defaultValue = "false") boolean stockCompare,
                    @RequestParam(required = false, defaultValue = "false") boolean upgradeCompare,
                    @RequestParam(required = false, defaultValue = "false") boolean mobile
            ) throws Exception
    {
        model.addAttribute("notification", notification);
        model.addAttribute("encyclopedia", data.get("encyclopedia"));
        model.addAttribute("data", data);

        if (StringUtils.isNotEmpty(nation) && StringUtils.isNotEmpty(shipType) && StringUtils.isNotEmpty(ship))
        {
            logger.info("Loading " + nation + " " + shipType + " " + ship);

            Warship warship = (Warship) ((LinkedHashMap<String, LinkedHashMap>) data.get("nations").get(nation)).get(shipType).get(ship);
            model.addAttribute("warship", warship);

            List<String> modules = apiServiceV2.setModules(warship, nation, shipType, ship, ship_id, Artillery, DiveBomber, Engine, Fighter, Suo, FlightControl, Hull, TorpedoBomber, Torpedoes);
            model.addAttribute("modules", modules);

//            List<String> modules = Stream.of(Artillery, DiveBomber, Engine, Fighter, Suo, FlightControl, Hull, TorpedoBomber, Torpedoes).collect(Collectors.toList());

            model.addAttribute("nation", nation).addAttribute("shipType", shipType).addAttribute("ship", ship)
                    .addAttribute("ship_id", ship_id).addAttribute("Artillery", Artillery).addAttribute("DiveBomber", DiveBomber).addAttribute("Engine", Engine)
                    .addAttribute("Fighter", Fighter).addAttribute("Suo", Suo).addAttribute("FlightControl", FlightControl).addAttribute("Hull", Hull)
                    .addAttribute("TorpedoBomber", TorpedoBomber).addAttribute("Torpedoes", Torpedoes);

            model.addAttribute("mobile", mobile);


        }
        return "v2/home";
    }
}