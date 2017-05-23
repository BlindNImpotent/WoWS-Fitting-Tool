package WoWSSSC.controller;

import WoWSSSC.model.WoWSAPI.shipprofile.Ship;
import WoWSSSC.model.WoWSAPI.warships.Warship;
import WoWSSSC.service.APIService;
import WoWSSSC.service.APIServiceTest;
import WoWSSSC.service.GPService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Aesis on 2016-10-15.
 */
@Controller
public class APIControllerTest
{
    @Autowired
    private APIService apiService;

    @Autowired
    private APIServiceTest apiServiceTest;

    @Autowired
    private GPService gpService;

    @Autowired
    private LinkedHashMap<String, LinkedHashMap> data;

    @Autowired
    @Qualifier (value = "gameParamsCHM")
    private HashMap<String, LinkedHashMap> gameParamsCHM;

    @Autowired
    private LinkedHashMap<String, String> notification;

    @Autowired
    private HashMap<String, Ship> shipHashMap;

    private static final Logger logger = LoggerFactory.getLogger(APIControllerTest.class);

    ObjectMapper mapper = new ObjectMapper();

    @RequestMapping (value = "/ship", method = RequestMethod.GET)
    public String getWarship
            (
                    HttpServletRequest request,
                    Model model,
                    @RequestParam(required = false, defaultValue = "") String nation,
                    @RequestParam(required = false, defaultValue = "") String shipType,
                    @RequestParam(required = false, defaultValue = "") String shipName,
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
//                    @RequestBody(required = false) HashMap<String, List> upgradesSkills,
                    @RequestParam(required = false, defaultValue = "") String slot1,
                    @RequestParam(required = false, defaultValue = "") String slot2,
                    @RequestParam(required = false, defaultValue = "") String slot3,
                    @RequestParam(required = false, defaultValue = "") String slot4,
                    @RequestParam(required = false, defaultValue = "") String slot5,
                    @RequestParam(required = false, defaultValue = "") String slot6,
                    @RequestParam(required = false, defaultValue = "false") boolean camo,
                    @RequestParam(required = false, defaultValue = "false") boolean stockCompare,
                    @RequestParam(required = false, defaultValue = "false") boolean upgradeCompare,
                    @RequestParam(required = false, defaultValue = "false") boolean mobile
            ) throws Exception
    {
        model.addAttribute("notification", notification);
        model.addAttribute("encyclopedia", data.get("encyclopedia"));
        model.addAttribute("data", data);

        if (StringUtils.isNotEmpty(nation) && StringUtils.isNotEmpty(shipType) && StringUtils.isNotEmpty(shipName))
        {
            logger.info("Loading " + nation + " " + shipType + " " + shipName);

            Warship warship = (Warship) ((LinkedHashMap<String, LinkedHashMap>) data.get("nations").get(nation)).get(shipType).get(shipName);
            model.addAttribute("warship", warship);

            List<String> modules = apiServiceTest.setModules(warship, nation, shipType, shipName, ship_id, Artillery, DiveBomber, Engine, Fighter, Suo, FlightControl, Hull, TorpedoBomber, Torpedoes);
            model.addAttribute("modules", modules);

//            List<String> modules = Stream.of(Artillery, DiveBomber, Engine, Fighter, Suo, FlightControl, Hull, TorpedoBomber, Torpedoes).collect(Collectors.toList());

            model.addAttribute("nation", nation).addAttribute("shipType", shipType).addAttribute("shipName", shipName)
                    .addAttribute("ship_id", ship_id).addAttribute("Artillery", Artillery).addAttribute("DiveBomber", DiveBomber).addAttribute("Engine", Engine)
                    .addAttribute("Fighter", Fighter).addAttribute("Suo", Suo).addAttribute("FlightControl", FlightControl).addAttribute("Hull", Hull)
                    .addAttribute("TorpedoBomber", TorpedoBomber).addAttribute("Torpedoes", Torpedoes);

            model.addAttribute("slot1", slot1).addAttribute("slot2", slot2).addAttribute("slot3", slot3).addAttribute("slot4", slot4).addAttribute("slot5", slot5).addAttribute("slot6", slot6);

            List<String> upgrades = Stream.of(slot1, slot2, slot3, slot4, slot5, slot6).collect(Collectors.toList());
            model.addAttribute("upgrades", upgrades);

            HashMap<String, List> upgradesSkills = new HashMap<>();
            upgradesSkills.put("upgrades", upgrades);

            String returnedKey = apiService.setShipAPI(nation, shipType, shipName, ship_id, Artillery, DiveBomber, Engine, Fighter, Suo, FlightControl, Hull, TorpedoBomber, Torpedoes, modules);
            Ship shipAPI = apiService.getUpgradeSkillStats(returnedKey, nation, shipType, shipName, ship_id, Artillery, DiveBomber, Engine, Fighter, Suo, FlightControl, Hull, TorpedoBomber, Torpedoes, modules, upgradesSkills, 100);
            model.addAttribute("shipAPI", shipAPI);

            if (shipAPI != null)
            {
                model.addAttribute("shipComponents", shipAPI.getShipComponents());
            }

            model.addAttribute("mobile", mobile);

        }
        return "test/home";
    }
}