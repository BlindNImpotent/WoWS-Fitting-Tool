package WoWSSSC.controller;

import WoWSSSC.config.DiscordWebhook;
import WoWSSSC.model.WoWSAPI.ModuleId;
import WoWSSSC.model.WoWSAPI.consumables.Consumables;
import WoWSSSC.model.WoWSAPI.info.Encyclopedia;
import WoWSSSC.model.WoWSAPI.warships.Warship;
import WoWSSSC.model.WoWSAPI.warships.WarshipModulesTree;
import WoWSSSC.model.email.EmailModel;
import WoWSSSC.model.WoWSAPI.shipprofile.Ship;
import WoWSSSC.model.WoWSAPI.skills.CrewSkills;
import WoWSSSC.model.gameparams.commanders.GPCommander;
import WoWSSSC.model.gameparams.commanders.UniqueTemp;
import WoWSSSC.service.APIService;
import WoWSSSC.service.MailService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
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
    private MailService mailService;

    @Autowired
    private HashMap<String, LinkedHashMap<String, LinkedHashMap>> data;

    @Autowired
    private LinkedHashMap<String, String> notification;

    @Autowired
    private HashMap<String, Ship> shipHashMap;

    @Autowired
    private DiscordWebhook discordWebhook;

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

        if (encyclopedia == null)
        {
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
                                @RequestParam(required = false) HashSet<String> flags,
                                @RequestParam(required = false) HashSet<String> consumables,
                                @RequestParam(required = false) String skills,
                                @RequestParam(required = false) HashSet<String> skillsH,
                                @RequestParam(required = false) String uSkills,
                                @RequestParam(required = false) HashSet<String> uSkillsH,
                                @RequestParam(required = false, defaultValue = "100") int adrenalineValue,
                                @RequestParam(required = false, defaultValue = "100") int ar,
                                @RequestParam(required = false, defaultValue = "false") boolean camo,
                                @RequestParam(required = false, defaultValue = "false") boolean mobile,
                                @RequestParam(required = false, defaultValue = "default") String commander,
                                @RequestParam(required = false, defaultValue = "") String moduleN,
                                @RequestParam(required = false, defaultValue = "") String upgradeN,
                                @RequestParam(required = false, defaultValue = "") String skillN,
                                @RequestParam(required = false, defaultValue = "") String uSkillN,
                                @RequestParam(required = false, defaultValue = "") String flagN,
                                @RequestParam(required = false, defaultValue = "") String s0,
                                @RequestParam(required = false, defaultValue = "") String s1,
                                @RequestParam(required = false, defaultValue = "") String s2,
                                @RequestParam(required = false, defaultValue = "") String s3) throws IOException
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
        
        if (StringUtils.isNotEmpty(nation) && StringUtils.isNotEmpty(shipType) && StringUtils.isNotEmpty(ship))
        {
            ship = URLDecoder.decode(ship, "UTF-8");

            logger.info("Loading " + nation + " " + shipType + " " + ship + " from /WarshipStats?" + request.getQueryString());

            Warship warship = (Warship) ((LinkedHashMap<String, LinkedHashMap>) data.get(serverParam).get("nations").get(nation)).get(shipType).get(ship);

            if (StringUtils.isNotEmpty(moduleN)) {
                int lengthT = moduleN.length();
                modules = new HashSet<>();
                Iterable<LinkedHashMap> tempT = warship.getWarshipModulesTreeNew().values();
                for (int i = 0; i < lengthT; i++) {
                    int moduleM = Integer.parseInt("" + moduleN.charAt(i)) - 1;
                    LinkedHashMap<String, WarshipModulesTree> tempWMT = IterableUtils.get(tempT, i);
                    Iterable<WarshipModulesTree> tempM = tempWMT.values();
                    modules.add(String.valueOf(IterableUtils.get(tempM, moduleM).getModule_id()));
                }
            }

            if (StringUtils.isNotEmpty(upgradeN)) {
                int lengthT = upgradeN.length();
                upgrades = new HashSet<>();
                Iterable<LinkedHashMap> tempT = warship.getUpgradesNew().values();
                for (int i = 0; i < lengthT; i++) {
                    int upgradeM = Integer.parseInt("" + upgradeN.charAt(i)) - 1;
                    if (upgradeM >= 0) {
                        LinkedHashMap<String, Consumables> tempWMT = IterableUtils.get(tempT, i);
                        Iterable<Consumables> tempM = tempWMT.values();
                        upgrades.add(String.valueOf(IterableUtils.get(tempM, upgradeM).getConsumable_id()));
                    }
                }
            }

            if (StringUtils.isNotEmpty(skills) && !skills.contains("tier") && !skills.contains("type_id") && !skills.contains("[]"))
            {
                skills = new String(Base64.getDecoder().decode(skills));
                skills = URLDecoder.decode(skills, "UTF-8");
            }

            if (StringUtils.isNotEmpty(uSkills))
            {
                uSkills = new String(Base64.getDecoder().decode(uSkills));
                uSkills = URLDecoder.decode(uSkills, "UTF-8");
            }

            HashSet<CrewSkills> crewSkills = new HashSet<>();
            if (StringUtils.isNotEmpty(skillN)) {
                int lengthT = skillN.length();
                for (int i = 0; i < lengthT; i++) {
                    int x = Integer.parseInt("" + skillN.charAt(i));
                    if (x == 1) {
                        CrewSkills cs = new CrewSkills();
                        cs.setTier((i / 8) + 1);
                        cs.setType_id(i % 8);
                        crewSkills.add(cs);
                    }
                }
            }
            else if (CollectionUtils.isEmpty(skillsH)) {
                crewSkills = skills != null ? mapper.readValue(skills, new TypeReference<HashSet<CrewSkills>>(){}) : new HashSet<>();
            }
            else {
                for (String sh : skillsH) {
                    String[] split = sh.split("_");
                    CrewSkills cs = new CrewSkills();
                    cs.setTier(Integer.parseInt(split[0]));
                    cs.setType_id(Integer.parseInt(split[1]));
                    crewSkills.add(cs);
                }
            }

            HashSet<String> crewUSkills = new HashSet<>();
            if (StringUtils.isNotEmpty(uSkillN)) {
                int lengthT = uSkillN.length();
                Iterable<UniqueTemp> tempUSkills = ((LinkedHashMap<String, GPCommander>) data.get(serverParam).get("commanders").get(nation)).get(commander).getUniqueSkills().getModifier().values();
                for (int i = 0; i < lengthT; i++) {
                    int uSkillM = Integer.parseInt("" + uSkillN.charAt(i));
                    if (uSkillM == 1) {
                        crewUSkills.add(IterableUtils.get(tempUSkills, i).getIdentifier());
                    }
                }
            }
            else {
                crewUSkills = CollectionUtils.isEmpty(uSkillsH) ? (uSkills != null ? mapper.readValue(uSkills, HashSet.class) : new HashSet<>()) : uSkillsH;
            }

            if (StringUtils.isNotEmpty(flagN)) {
                flags = new HashSet<>();
                Iterable<Consumables> flagsIter = ((LinkedHashMap<String, Consumables>) data.get(serverParam).get("exteriors").get("Flags")).values();
                for (int i = 0; i < flagN.length(); i++) {
                    int flagM = Integer.parseInt("" + flagN.charAt(i));
                    if (flagM == 1) {
                        flags.add(String.valueOf(IterableUtils.get(flagsIter, i).getConsumable_id()));
                    }
                }
            }

            if (StringUtils.isNotEmpty(s0) || StringUtils.isNotEmpty(s1) || StringUtils.isNotEmpty(s2) || StringUtils.isNotEmpty(s3)) {
                consumables = new HashSet<>();
                if (StringUtils.isNotEmpty(s0)) {
                    consumables.add(s0);
                }
                if (StringUtils.isNotEmpty(s1)) {
                    consumables.add(s1);
                }
                if (StringUtils.isNotEmpty(s2)) {
                    consumables.add(s2);
                }
                if (StringUtils.isNotEmpty(s3)) {
                    consumables.add(s3);
                }
            }

            model.addAttribute("url", "/warship?" + request.getQueryString());
            model.addAttribute("modules", modules);
            model.addAttribute("upgrades", upgrades);
            model.addAttribute("adrenalineValue", ar != 100 ? ar : adrenalineValue);
            model.addAttribute("flags", flags);
            model.addAttribute("consumables", consumables);
            model.addAttribute("crewSkills", crewSkills);
            model.addAttribute("crewUSkills", crewUSkills);
            model.addAttribute("camo", camo);
            model.addAttribute("mobile", mobile);
            model.addAttribute("warship", warship);
            model.addAttribute("nation", nation).addAttribute("shipType", shipType).addAttribute("ship", ship);
            model.addAttribute("commanders", ((LinkedHashMap<String, LinkedHashMap>) data.get(serverParam).get("commanders").get(nation)).keySet());

            commander = URLDecoder.decode(commander, "UTF-8");
            commander = commander.equalsIgnoreCase("Steven Seagal") ? "John Doe" : commander;
            model.addAttribute("sCommander", commander);
        }

//        return "WarshipStats/warshipHome";
        return "FittingTool/ftHome";
    }

    @RequestMapping (value = "/warship", method = { RequestMethod.GET, RequestMethod.POST })
    public String getWarship(HttpServletRequest request,
                            Model model,
                            @RequestParam(required = false) String nation,
                            @RequestParam(required = false) String shipType,
                            @RequestParam(required = false) String ship,
                            @RequestParam(required = false) HashSet<String> modules,
                            @RequestParam(required = false) HashSet<String> upgrades,
                            @RequestParam(required = false) HashSet<String> flags,
                            @RequestParam(required = false) HashSet<String> consumables,
                            @RequestParam(required = false) String skills,
                            @RequestParam(required = false) String uSkills,
                            @RequestParam(required = false, defaultValue = "100") int adrenalineValue,
                            @RequestParam(required = false) boolean camo,
                            @RequestParam(required = false, defaultValue = "false") boolean mobile,
                            @RequestParam(required = false, defaultValue = "default") String commander) throws IOException
    {
        if (nation != null && shipType != null && ship != null)
        {
            ship = URLDecoder.decode(ship, "UTF-8");

            if (request.getMethod().equalsIgnoreCase("post"))
            {
                model.addAttribute("serverParam", serverParamAddress);
                model.addAttribute("nations", data.get(serverParam).get("nations"));
                model.addAttribute("uniqueSkills", data.get(serverParam).get("uniqueSkills"));
                model.addAttribute("skills", data.get(serverParam).get("skills"));
                model.addAttribute("exteriors", data.get(serverParam).get("exteriors"));
                model.addAttribute("notification", notification);
                model.addAttribute("encyclopedia", data.get(serverParam).get("encyclopedia"));

                logger.info("Loading " + nation + " " + shipType + " " + ship);
                model.addAttribute("warship", ((LinkedHashMap<String, LinkedHashMap>) data.get(serverParam).get("nations").get(nation)).get(shipType).get(ship));
                model.addAttribute("commanders", ((LinkedHashMap<String, LinkedHashMap>) data.get(serverParam).get("commanders").get(nation)).keySet());

                commander = URLDecoder.decode(commander, "UTF-8");
                commander = commander.equalsIgnoreCase("Steven Seagal") ? "John Doe" : commander;
                model.addAttribute("sCommander", commander);

//                return "WarshipStats/warshipPage :: warshipStats";
                return "FittingTool/ftShipSelect :: warshipStats";
            }
        }
        return "redirect:/WarshipStats?" + request.getQueryString();
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
                    @RequestParam(required = false, defaultValue = "") String flagN,
                    @RequestParam(required = false, defaultValue = "false") boolean camo,
                    @RequestParam(required = false, defaultValue = "false") boolean stockCompare,
                    @RequestParam(required = false, defaultValue = "false") boolean upgradeCompare,
                    @RequestBody(required = false) HashMap<String, List> upgradesSkills,
                    @RequestParam(required = false, defaultValue = "") String s0,
                    @RequestParam(required = false, defaultValue = "") String s1,
                    @RequestParam(required = false, defaultValue = "") String s2,
                    @RequestParam(required = false, defaultValue = "") String s3
            ) throws Exception
    {
        if (!ship_id.equals(""))
        {
            ship = URLDecoder.decode(ship, "UTF-8");

            logger.info("Ship API - " + nation + " " + shipType + " " + ship);

            Warship warship = (Warship) ((LinkedHashMap<String, LinkedHashMap>) data.get(serverParam).get("nations").get(nation)).get(shipType).get(ship);

            if (StringUtils.isNotEmpty(moduleN)) {
                int lengthT = moduleN.length();
                modules = new ArrayList<>();
                Iterable<LinkedHashMap> tempT = warship.getWarshipModulesTreeNew().values();
                for (int i = 0; i < lengthT; i++) {
                    int moduleM = Integer.parseInt("" + moduleN.charAt(i)) - 1;
                    LinkedHashMap<String, WarshipModulesTree> tempWMT = IterableUtils.get(tempT, i);
                    Iterable<WarshipModulesTree> tempM = tempWMT.values();
                    WarshipModulesTree tempWMTClass = IterableUtils.get(tempM, moduleM);
                    modules.add(String.valueOf(tempWMTClass.getModule_id()));

                    if (tempWMTClass.getType().equals("Artillery")) {
                        Artillery = String.valueOf(tempWMTClass.getModule_id());
                    }
                    else if (tempWMTClass.getType().equals("DiveBomber")) {
                        DiveBomber = String.valueOf(tempWMTClass.getModule_id());
                    }
                    else if (tempWMTClass.getType().equals("Engine")) {
                        Engine = String.valueOf(tempWMTClass.getModule_id());
                    }
                    else if (tempWMTClass.getType().equals("Fighter")) {
                        Fighter = String.valueOf(tempWMTClass.getModule_id());
                    }
                    else if (tempWMTClass.getType().equals("Suo")) {
                        Suo = String.valueOf(tempWMTClass.getModule_id());
                    }
                    else if (tempWMTClass.getType().equals("FlightControl")) {
                        FlightControl = String.valueOf(tempWMTClass.getModule_id());
                    }
                    else if (tempWMTClass.getType().equals("Hull")) {
                        Hull = String.valueOf(tempWMTClass.getModule_id());
                    }
                    else if (tempWMTClass.getType().equals("TorpedoBomber")) {
                        TorpedoBomber = String.valueOf(tempWMTClass.getModule_id());
                    }
                    else if (tempWMTClass.getType().equals("Torpedoes")) {
                        Torpedoes = String.valueOf(tempWMTClass.getModule_id());
                    }
                }
            }

            if (upgradesSkills == null) {
                upgradesSkills = new HashMap<>();
            }

            if (StringUtils.isNotEmpty(upgradeN)) {
                int lengthT = upgradeN.length();
                Iterable<LinkedHashMap> tempT = warship.getUpgradesNew().values();
                List<String> upgrades = new ArrayList<>();
                for (int i = 0; i < lengthT; i++) {
                    int upgradeM = Integer.parseInt("" + upgradeN.charAt(i)) - 1;
                    if (upgradeM >= 0) {
                        LinkedHashMap<String, Consumables> tempWMT = IterableUtils.get(tempT, i);
                        Iterable<Consumables> tempM = tempWMT.values();
                        upgrades.add(String.valueOf(IterableUtils.get(tempM, upgradeM).getConsumable_id()));
                    }
                }
                upgradesSkills.put("upgrades", upgrades);
            }

            commander = URLDecoder.decode(commander, "UTF-8");
            commander = commander.equalsIgnoreCase("Steven Seagal") ? "John Doe" : commander;
            List<String> commanderList = new ArrayList<>();
            commanderList.add(commander);
            upgradesSkills.put("commander", commanderList);

            if (StringUtils.isNotEmpty(skillN)) {
                int lengthT = skillN.length();
                List<HashMap> skills = new ArrayList<>();
                for (int i = 0; i < lengthT; i++) {
                    int x = Integer.parseInt("" + skillN.charAt(i));
                    if (x == 1) {
                       HashMap tempHash = new HashMap();
                       tempHash.put("tier", String.valueOf((i / 8) + 1));
                       tempHash.put("type_id", String.valueOf(i % 8));
                       skills.add(tempHash);
                    }
                }
                upgradesSkills.put("skills", skills);
            }

            if (StringUtils.isNotEmpty(uSkillN)) {
                int lengthT = uSkillN.length();
                Iterable<UniqueTemp> tempUSkills = ((LinkedHashMap<String, GPCommander>) data.get(serverParam).get("commanders").get(nation)).get(commander).getUniqueSkills().getModifier().values();
                List<String> uSkillList = new ArrayList<>();
                for (int i = 0; i < lengthT; i++) {
                    int uSkillM = Integer.parseInt("" + uSkillN.charAt(i));
                    if (uSkillM == 1) {
                        uSkillList.add(IterableUtils.get(tempUSkills, i).getIdentifier());
                    }
                }
                upgradesSkills.put("uSkills", uSkillList);
            }

            List<Boolean> camouflage = new ArrayList<>();
            camouflage.add(camo);
            upgradesSkills.put("camouflage", camouflage);

            if (StringUtils.isNotEmpty(flagN)) {
                List<String> tempFList = new ArrayList<>();
                Iterable<Consumables> flagsIter = ((LinkedHashMap<String, Consumables>) data.get(serverParam).get("exteriors").get("Flags")).values();
                for (int i = 0; i < flagN.length(); i++) {
                    int flagM = Integer.parseInt("" + flagN.charAt(i));
                    if (flagM == 1) {
                        tempFList.add(String.valueOf(IterableUtils.get(flagsIter, i).getConsumable_id()));
                    }
                }
                upgradesSkills.put("flags", tempFList);
            }

            adrenalineValue = ar != 100 ? ar : adrenalineValue;
            String returnedKey = apiService.setShipAPI(nation, shipType, ship, ship_id, Artillery, DiveBomber, Engine, Fighter, Suo, FlightControl, Hull, TorpedoBomber, Torpedoes, modules);
            Ship shipAPI = apiService.getUpgradeSkillStats(returnedKey, nation, shipType, ship, ship_id, Artillery, DiveBomber, Engine, Fighter, Suo, FlightControl, Hull, TorpedoBomber, Torpedoes, modules, upgradesSkills, adrenalineValue, false, true, isLive);
            model.addAttribute("shipAPI", shipAPI);

            if (StringUtils.isNotEmpty(s0) || StringUtils.isNotEmpty(s1) || StringUtils.isNotEmpty(s2) || StringUtils.isNotEmpty(s3)) {
                List<String> tempConsumables = new ArrayList<>();
                if (StringUtils.isNotEmpty(s0)) {
                    tempConsumables.add(s0);
                }
                if (StringUtils.isNotEmpty(s1)) {
                    tempConsumables.add(s1);
                }
                if (StringUtils.isNotEmpty(s2)) {
                    tempConsumables.add(s2);
                }
                if (StringUtils.isNotEmpty(s3)) {
                    tempConsumables.add(s3);
                }
                upgradesSkills.put("consumables", tempConsumables);
            }

            if (upgradesSkills != null)
            {
                if (upgradesSkills.get("skills") != null)
                {
                    for (HashMap skill : ((List<HashMap>) upgradesSkills.get("skills"))) {
                        if (skill.get("tier").equals("2") && skill.get("type_id").equals("6"))
                        {
                            model.addAttribute("adrenaline", true);
                            model.addAttribute("adrenalineValue", adrenalineValue);
                        }

                        if (skill.get("tier").equals("4") && skill.get("type_id").equals("2"))
                        {
                            model.addAttribute("IFHE", true);
                        }
                    }
                }
                model.addAttribute("consumables", upgradesSkills.get("consumables"));
            }

            model.addAttribute("upgradeCompare", upgradeCompare);
            if (upgradeCompare)
            {
                model.addAttribute("configurationAPI", apiService.getUpgradeSkillStats(returnedKey, nation, shipType, ship, ship_id, Artillery, DiveBomber, Engine, Fighter, Suo, FlightControl, Hull, TorpedoBomber, Torpedoes, modules, new HashMap<>(), 100, false, true, isLive));
            }

            model.addAttribute("stockCompare", stockCompare);
            if (stockCompare)
            {
                ModuleId moduleId = new ModuleId();
                String stockKey = apiService.setShipAPI(nation, shipType, ship, ship_id, "", "", "", "", "", "", "", "", "", new ArrayList<>());
                apiService.setModuleIds(shipHashMap.get(stockKey), moduleId);
                model.addAttribute("configurationAPI", apiService.getUpgradeSkillStats(stockKey, nation, shipType, ship, ship_id, moduleId.getArtillery_id(), moduleId.getDive_bomber_id(), moduleId.getEngine_id(), moduleId.getFighter_id(), moduleId.getFire_control_id(), moduleId.getFlight_control_id(), moduleId.getHull_id(), moduleId.getTorpedo_bomber_id(), moduleId.getTorpedoes_id(), new ArrayList<>(), new HashMap<>(), 100, true, true, isLive));
            }
        }

//        return "WarshipStats/shipAPIPage :: shipAPIData";
        return "FittingTool/ftAPIPage :: shipAPIData";
    }

//    @ResponseBody
//    @RequestMapping (value = "/gpService", method = RequestMethod.POST)
//    public ShipComponents shipGP
//    (
//            @RequestParam(required = false, defaultValue = "") String nation,
//            @RequestParam(required = false, defaultValue = "") String shipType,
//            @RequestParam(required = false, defaultValue = "") String ship,
//            @RequestParam(required = false, defaultValue = "") String ship_id,
//            @RequestParam(required = false, defaultValue = "") String Artillery,
//            @RequestParam(required = false, defaultValue = "") String DiveBomber,
//            @RequestParam(required = false, defaultValue = "") String Engine,
//            @RequestParam(required = false, defaultValue = "") String Fighter,
//            @RequestParam(required = false, defaultValue = "") String Suo,
//            @RequestParam(required = false, defaultValue = "") String FlightControl,
//            @RequestParam(required = false, defaultValue = "") String Hull,
//            @RequestParam(required = false, defaultValue = "") String TorpedoBomber,
//            @RequestParam(required = false, defaultValue = "") String Torpedoes,
//            @RequestBody(required = false) HashMap<String, List> upgradesSkills,
//            @RequestParam(required = false) List<String> modules
//    ) throws Exception
//    {
//        ship = URLDecoder.decode(ship, "UTF-8");
//
//        String key = "&ship_id=" + ship_id + "&artillery_id=" + Artillery + "&dive_bomber_id=" + DiveBomber + "&engine_id=" + Engine
//                + "&fighter_id=" + Fighter + "&fire_control_id=" + Suo + "&flight_control_id=" + FlightControl + "&hull_id=" + Hull + "&torpedo_bomber_id=" + TorpedoBomber + "&torpedoes_id=" + Torpedoes;
//
//        return apiService.getUpgradeSkillStats(key, nation, shipType, ship, ship_id, Artillery, DiveBomber, Engine, Fighter, Suo, FlightControl, Hull, TorpedoBomber, Torpedoes, modules, upgradesSkills, 100, false, true, isLive).getShipComponents();
//    }

    @RequestMapping (value = "/shipComponents", method = RequestMethod.POST)
    public String shipComponents(Model model,
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
                                 @RequestBody(required = false) HashMap<String, List> upgradesSkills,
                                 @RequestParam(required = false) List<String> modules
    ) throws Exception
    {
        ship = URLDecoder.decode(ship, "UTF-8");

        String key = "&ship_id=" + ship_id + "&artillery_id=" + Artillery + "&dive_bomber_id=" + DiveBomber + "&engine_id=" + Engine
            + "&fighter_id=" + Fighter + "&fire_control_id=" + Suo + "&flight_control_id=" + FlightControl + "&hull_id=" + Hull + "&torpedo_bomber_id=" + TorpedoBomber + "&torpedoes_id=" + Torpedoes;

        Ship shipAPI = apiService.getUpgradeSkillStats(key, nation, shipType, ship, ship_id, Artillery, DiveBomber, Engine, Fighter, Suo, FlightControl, Hull, TorpedoBomber, Torpedoes, modules, upgradesSkills, 100, false, false, isLive);

        model.addAttribute("shipAPI", shipAPI);

        if (shipAPI != null)
        {
            model.addAttribute("shipComponents", shipAPI.getShipComponents());
        }

        if (upgradesSkills != null) {
            model.addAttribute("consumables", upgradesSkills.get("consumables"));
        }

        return "WarshipStats/consumablesPage";
    }

    @RequestMapping (value = "/shipTree", method = RequestMethod.GET)
    public String shipTree(Model model)
    {
        model.addAttribute("serverParam", serverParamAddress);
        model.addAttribute("encyclopedia", data.get(serverParam).get("encyclopedia"));
        model.addAttribute("nations", data.get(serverParam).get("nations"));
        model.addAttribute("premiumTable", data.get(serverParam).get("premiumTable"));

        return "WarshipResearch/shipTree";
    }

    @ResponseBody
    @RequestMapping (value = "/shipTree", method = RequestMethod.POST)
    public long getXp(@RequestBody List<String> shipList)
    {
        logger.info("Calculating xp between " + shipList.get(0) + " and " + shipList.get(1));

        long xp = apiService.getXp(shipList, isLive);

        if (xp >= 0)
        {
            logger.info(String.format("%,d", xp)  + " xp required");
        }
        else
        {
            logger.info("Wrong path of research");
        }

        return xp;
    }

//    @RequestMapping (value = "/commandersRanks", method = RequestMethod.GET)
//    public String commanderRanks(Model model)
//    {
//        model.addAttribute("nations", data.get(serverParam).get("nations"));
//        model.addAttribute("encyclopedia", data.get("encyclopedia"));
//        model.addAttribute("commandersRanks", data.get(serverParam).get("commandersRanks"));
//
//        return "CommandersRanks/commandersRanks";
//    }

    @RequestMapping (value = "/shipStatComparison", method = RequestMethod.GET)
    public String shipStatComparisonTree(Model model,
                                         HttpServletRequest request,
                                         @RequestBody(required = false) List<String> shipList,
                                         @RequestParam(required = false) String ship1,
                                         @RequestParam(required = false) String ship2,
                                         @RequestParam(required = false) HashSet<String> modules1,
                                         @RequestParam(required = false) HashSet<String> modules2,
                                         @RequestParam(required = false) HashSet<String> consumables1,
                                         @RequestParam(required = false) HashSet<String> consumables2,
                                         @RequestParam(required = false) String upgradesSkills,
                                         @RequestParam(required = false) String upgrades1,
                                         @RequestParam(required = false) String upgrades2,
                                         @RequestParam(required = false) String skills,
                                         @RequestParam(required = false) HashSet<String> flags,
                                         @RequestParam(required = false) boolean camo,
                                         @RequestParam(required = false, defaultValue = "100") int adrenalineValue1,
                                         @RequestParam(required = false, defaultValue = "100") int adrenalineValue2) throws IOException
    {
        model.addAttribute("serverParam", serverParamAddress);
        model.addAttribute("nations", data.get(serverParam).get("nations"));
        model.addAttribute("encyclopedia", data.get(serverParam).get("encyclopedia"));
        model.addAttribute("skills", data.get(serverParam).get("skills"));
        model.addAttribute("exteriors", data.get(serverParam).get("exteriors"));
        model.addAttribute("premiumTable", data.get(serverParam).get("premiumTable"));
        model.addAttribute("rawShipData", data.get(serverParam).get("rawShipData"));

        if (StringUtils.isNotEmpty(ship1) && StringUtils.isNotEmpty(ship2))
        {
            ship1 = URLDecoder.decode(ship1, "UTF-8");
            ship2 = URLDecoder.decode(ship2, "UTF-8");

            logger.info("Loading " + ship1 + " and " + ship2 + " from /shipStatComparison?" + request.getQueryString());

            if (upgradesSkills != null && !upgradesSkills.contains(ship1) && !upgradesSkills.contains(ship2))
            {
                if (StringUtils.isNotEmpty(upgradesSkills))
                {
                    upgradesSkills = new String(Base64.getDecoder().decode(upgradesSkills));
                    upgradesSkills = URLDecoder.decode(upgradesSkills, "UTF-8");
                }
            }
            else
            {
                if (StringUtils.isNotEmpty(upgrades1))
                {
                    upgrades1 = new String(Base64.getDecoder().decode(upgrades1));
                    upgrades1 = URLDecoder.decode(upgrades1, "UTF-8");
                }

                if (StringUtils.isNotEmpty(upgrades2))
                {
                    upgrades2 = new String(Base64.getDecoder().decode(upgrades2));
                    upgrades2 = URLDecoder.decode(upgrades2, "UTF-8");
                }

                if (StringUtils.isNotEmpty(skills))
                {
                    skills = new String(Base64.getDecoder().decode(skills));
                    skills = URLDecoder.decode(skills, "UTF-8");
                }
            }

            TypeReference<List<HashMap>> typeRef1 = new TypeReference<List<HashMap>>() {};
            List<HashMap> USs = (upgradesSkills != null) ? mapper.readValue(upgradesSkills, typeRef1) : new ArrayList<>();

            TypeReference<HashMap> typeRef2 = new TypeReference<HashMap>() {};
            HashMap upgrades1Converted = (upgrades1 != null) ? mapper.readValue(upgrades1, typeRef2) : new HashMap();
            HashMap upgrades2Converted = (upgrades2 != null) ? mapper.readValue(upgrades2, typeRef2) : new HashMap();

            List<HashMap> skillsConverted = (skills != null ) ? mapper.readValue(skills, typeRef1) : new ArrayList<>();

            Warship warship1 = (Warship) data.get(serverParam).get("rawShipData").get(ship1);
            Warship warship2 = (Warship) data.get(serverParam).get("rawShipData").get(ship2);

            HashMap<String, List> upgradesSkills1 = new HashMap<>();
            HashMap<String, List> upgradesSkills2 = new HashMap<>();
            for (HashMap US : USs) {
                if (US.get("shipName").equals(ship1))
                {
                    upgradesSkills1 = US;
                }

                if (US.get("shipName").equals(ship2))
                {
                    upgradesSkills2 = US;
                }
            }

            model.addAttribute("url", "/shipStatComparison?" + request.getQueryString());
            model.addAttribute("warship1", warship1);
            model.addAttribute("ship1", ship1);
            model.addAttribute("warship2", warship2);
            model.addAttribute("ship2", ship2);
            model.addAttribute("modules1", modules1);
            model.addAttribute("modules2", modules2);

            if (upgradesSkills1.size() > 0 || upgradesSkills2.size() > 0)
            {
                model.addAttribute("upgrades1", upgradesSkills1.get("upgrades"));
                model.addAttribute("upgrades2", upgradesSkills2.get("upgrades"));
                model.addAttribute("flags", upgradesSkills1.get("flags"));
                model.addAttribute("camo", upgradesSkills1.get("camouflage").get(0));
                model.addAttribute("crewSkills", upgradesSkills1.get("skills"));
            }
            else
            {
                model.addAttribute("upgrades1", upgrades1Converted.get("upgrades"));
                model.addAttribute("upgrades2", upgrades2Converted.get("upgrades"));
                model.addAttribute("flags", flags);
                model.addAttribute("camo", camo);
                model.addAttribute("crewSkills", skillsConverted);
            }

            model.addAttribute("consumables1", consumables1);
            model.addAttribute("consumables2", consumables2);
            model.addAttribute("adrenalineValue1", adrenalineValue1);
            model.addAttribute("adrenalineValue2", adrenalineValue2);
        }

        return "WarshipComparison/shipStatComparisonTree";
    }

    @RequestMapping (value = "/shipStatSelection", method = { RequestMethod.GET, RequestMethod.POST })
    public String shipStatSelection(Model model,
                                    HttpServletRequest request,
                                    @RequestBody(required = false) List<String> shipList,
                                    @RequestParam(required = false) String ship1,
                                    @RequestParam(required = false) String ship2,
                                    @RequestParam(required = false) HashSet<String> modules1,
                                    @RequestParam(required = false) HashSet<String> modules2,
                                    @RequestParam(required = false) HashSet<String> consumables1,
                                    @RequestParam(required = false) HashSet<String> consumables2,
                                    @RequestParam(required = false) String upgradesSkills,
                                    @RequestParam(required = false) String upgrades1,
                                    @RequestParam(required = false) String upgrades2,
                                    @RequestParam(required = false) String skills,
                                    @RequestParam(required = false) HashSet<String> flags,
                                    @RequestParam(required = false) boolean camo,
                                    @RequestParam(required = false, defaultValue = "100") int adrenalineValue1,
                                    @RequestParam(required = false, defaultValue = "100") int adrenalineValue2) throws IOException
    {
        model.addAttribute("serverParam", serverParamAddress);
        model.addAttribute("encyclopedia", data.get(serverParam).get("encyclopedia"));
        if ((StringUtils.isNotEmpty(ship1) && StringUtils.isNotEmpty(ship2)) || !CollectionUtils.isEmpty(shipList))
        {
            if (StringUtils.isNotEmpty(ship1))
            {
                ship1 = URLDecoder.decode(ship1, "UTF-8");
            }

            if (StringUtils.isNotEmpty(ship2))
            {
                ship2 = URLDecoder.decode(ship2, "UTF-8");
            }

            if (request.getMethod().equalsIgnoreCase("post"))
            {
                logger.info("Loading " + shipList.get(0) + " and " + shipList.get(1));

                model.addAttribute("warship1", data.get(serverParam).get("rawShipData").get(shipList.get(0)));
                model.addAttribute("warship2", data.get(serverParam).get("rawShipData").get(shipList.get(1)));

                return "WarshipComparison/shipStatSelection :: warshipSelection";
            }
//            else
//            {
//                logger.info("Loading " + ship1 + " and " + ship2 + " from /shipStatSelection?" + request.getQueryString());
//
//                if (upgradesSkills != null && !upgradesSkills.contains(ship1) && !upgradesSkills.contains(ship2))
//                {
//                    if (StringUtils.isNotEmpty(upgradesSkills))
//                    {
//                        upgradesSkills = new String(Base64.getDecoder().decode(upgradesSkills));
//                        upgradesSkills = URLDecoder.decode(upgradesSkills, "UTF-8");
//                    }
//                }
//                else
//                {
//                    if (StringUtils.isNotEmpty(upgrades1))
//                    {
//                        upgrades1 = new String(Base64.getDecoder().decode(upgrades1));
//                        upgrades1 = URLDecoder.decode(upgrades1, "UTF-8");
//                    }
//
//                    if (StringUtils.isNotEmpty(upgrades2))
//                    {
//                        upgrades2 = new String(Base64.getDecoder().decode(upgrades2));
//                        upgrades2 = URLDecoder.decode(upgrades2, "UTF-8");
//                    }
//
//                    if (StringUtils.isNotEmpty(skills))
//                    {
//                        skills = new String(Base64.getDecoder().decode(skills));
//                        skills = URLDecoder.decode(skills, "UTF-8");
//                    }
//                }
//
//                TypeReference<List<HashMap>> typeRef1 = new TypeReference<List<HashMap>>() {};
//                List<HashMap> USs = (upgradesSkills != null) ? mapper.readValue(upgradesSkills, typeRef1) : new ArrayList<>();
//
//                TypeReference<HashMap> typeRef2 = new TypeReference<HashMap>() {};
//                HashMap upgrades1Converted = (upgrades1 != null) ? mapper.readValue(upgrades1, typeRef2) : new HashMap();
//                HashMap upgrades2Converted = (upgrades2 != null) ? mapper.readValue(upgrades2, typeRef2) : new HashMap();
//
//                List<HashMap> skillsConverted = (skills != null ) ? mapper.readValue(skills, typeRef1) : new ArrayList<>();
//
//                Warship warship1 = (Warship) data.get(serverParam).get("rawShipData").get(ship1);
//                Warship warship2 = (Warship) data.get(serverParam).get("rawShipData").get(ship2);
//
//                HashMap<String, List> upgradesSkills1 = new HashMap<>();
//                HashMap<String, List> upgradesSkills2 = new HashMap<>();
//                for (HashMap US : USs) {
//                    if (US.get("shipName").equals(ship1))
//                    {
//                        upgradesSkills1 = US;
//                    }
//
//                    if (US.get("shipName").equals(ship2))
//                    {
//                        upgradesSkills2 = US;
//                    }
//                }
//
//                redirectAttributes.addFlashAttribute("url", "/shipStatComparison?" + request.getQueryString());
//                redirectAttributes.addFlashAttribute("warship1", warship1);
//                redirectAttributes.addFlashAttribute("ship1", ship1);
//                redirectAttributes.addFlashAttribute("warship2", warship2);
//                redirectAttributes.addFlashAttribute("ship2", ship2);
//                redirectAttributes.addFlashAttribute("modules1", modules1);
//                redirectAttributes.addFlashAttribute("modules2", modules2);
//
//                if (upgradesSkills1.size() > 0 || upgradesSkills2.size() > 0)
//                {
//                    redirectAttributes.addFlashAttribute("upgrades1", upgradesSkills1.get("upgrades"));
//                    redirectAttributes.addFlashAttribute("upgrades2", upgradesSkills2.get("upgrades"));
//                    redirectAttributes.addFlashAttribute("flags", upgradesSkills1.get("flags"));
//                    redirectAttributes.addFlashAttribute("camo", upgradesSkills1.get("camouflage").get(0));
//                    redirectAttributes.addFlashAttribute("crewSkills", upgradesSkills1.get("skills"));
//                }
//                else
//                {
//                    redirectAttributes.addFlashAttribute("upgrades1", upgrades1Converted.get("upgrades"));
//                    redirectAttributes.addFlashAttribute("upgrades2", upgrades2Converted.get("upgrades"));
//                    redirectAttributes.addFlashAttribute("flags", flags);
//                    redirectAttributes.addFlashAttribute("camo", camo);
//                    redirectAttributes.addFlashAttribute("crewSkills", skillsConverted);
//                }
//
//                redirectAttributes.addFlashAttribute("consumables1", consumables1);
//                redirectAttributes.addFlashAttribute("consumables2", consumables2);
//                redirectAttributes.addFlashAttribute("adrenalineValue1", adrenalineValue1);
//                redirectAttributes.addFlashAttribute("adrenalineValue2", adrenalineValue2);
//            }
        }
        return "redirect:/shipStatComparison?" + request.getQueryString();
    }

    @RequestMapping (value = "/shipStatComparison", method = RequestMethod.POST)
    public String shipStatComparisonTable(Model model,
                                          @RequestParam(required = false, defaultValue = "") String nation1,
                                          @RequestParam(required = false, defaultValue = "") String shipType1,
                                          @RequestParam(required = false, defaultValue = "") String ship1,
                                          @RequestParam(required = false, defaultValue = "") String ship_id1,
                                          @RequestParam(required = false, defaultValue = "") String Artillery1,
                                          @RequestParam(required = false, defaultValue = "") String DiveBomber1,
                                          @RequestParam(required = false, defaultValue = "") String Engine1,
                                          @RequestParam(required = false, defaultValue = "") String Fighter1,
                                          @RequestParam(required = false, defaultValue = "") String Suo1,
                                          @RequestParam(required = false, defaultValue = "") String FlightControl1,
                                          @RequestParam(required = false, defaultValue = "") String Hull1,
                                          @RequestParam(required = false, defaultValue = "") String TorpedoBomber1,
                                          @RequestParam(required = false, defaultValue = "") String Torpedoes1,
                                          @RequestParam(required = false, defaultValue = "") String nation2,
                                          @RequestParam(required = false, defaultValue = "") String shipType2,
                                          @RequestParam(required = false, defaultValue = "") String ship2,
                                          @RequestParam(required = false, defaultValue = "") String ship_id2,
                                          @RequestParam(required = false, defaultValue = "") String Artillery2,
                                          @RequestParam(required = false, defaultValue = "") String DiveBomber2,
                                          @RequestParam(required = false, defaultValue = "") String Engine2,
                                          @RequestParam(required = false, defaultValue = "") String Fighter2,
                                          @RequestParam(required = false, defaultValue = "") String Suo2,
                                          @RequestParam(required = false, defaultValue = "") String FlightControl2,
                                          @RequestParam(required = false, defaultValue = "") String Hull2,
                                          @RequestParam(required = false, defaultValue = "") String TorpedoBomber2,
                                          @RequestParam(required = false, defaultValue = "") String Torpedoes2,
                                          @RequestBody(required = false) HashMap<String, HashMap> upgradesSkillsV2,
                                          @RequestParam(required = false, defaultValue = "100") int adrenalineValue1,
                                          @RequestParam(required = false, defaultValue = "100") int adrenalineValue2) throws Exception
    {
        ship1 = URLDecoder.decode(ship1, "UTF-8");
        ship2 = URLDecoder.decode(ship2, "UTF-8");

        HashMap<String, List> upgradesSkills1 = new HashMap<>();
        HashMap<String, List> upgradesSkills2 = new HashMap<>();

        if (((String) upgradesSkillsV2.get("upgradesSkills1").get("shipName")).equalsIgnoreCase(ship1))
        {
            upgradesSkills1 = upgradesSkillsV2.get("upgradesSkills1");
            upgradesSkills2 = upgradesSkillsV2.get("upgradesSkills2");
        }
        else if (((String) upgradesSkillsV2.get("upgradesSkills1").get("shipName")).equalsIgnoreCase(ship2))
        {
            upgradesSkills1 = upgradesSkillsV2.get("upgradesSkills2");
            upgradesSkills2 = upgradesSkillsV2.get("upgradesSkills1");
        }

        if (upgradesSkillsV2.get("skills").get("skills") != null)
        {
            ((List<HashMap>) upgradesSkillsV2.get("skills").get("skills")).forEach(skill ->
            {
                if (skill.get("tier").equals("2") && skill.get("type_id").equals("6"))
                {
                    model.addAttribute("adrenaline", true);
                    model.addAttribute("adrenalineValue1", adrenalineValue1);
                    model.addAttribute("adrenalineValue2", adrenalineValue2);
                }

                if (skill.get("tier").equals("4") && skill.get("type_id").equals("2"))
                {
                    model.addAttribute("IFHE", true);
                }
            });
        }

        upgradesSkills1.put("skills", (List<HashMap>) upgradesSkillsV2.get("skills").get("skills"));
        upgradesSkills1.put("flags", (List<String>) upgradesSkillsV2.get("flags").get("flags"));
        upgradesSkills1.put("camouflage", (List<Boolean>) upgradesSkillsV2.get("camouflage").get("camouflage"));

        upgradesSkills2.put("skills", (List<HashMap>) upgradesSkillsV2.get("skills").get("skills"));
        upgradesSkills2.put("flags", (List<String>) upgradesSkillsV2.get("flags").get("flags"));
        upgradesSkills2.put("camouflage", (List<Boolean>) upgradesSkillsV2.get("camouflage").get("camouflage"));

        logger.info("Ship Comparison - " + ship1 + " and " + ship2);

        String returnedKey1 = apiService.setShipAPI(nation1, shipType1, ship1, ship_id1, Artillery1, DiveBomber1, Engine1, Fighter1, Suo1, FlightControl1, Hull1, TorpedoBomber1, Torpedoes1, new ArrayList<>());
        Ship shipAPI1 = apiService.getUpgradeSkillStats(returnedKey1, nation1, shipType1, ship1, ship_id1, Artillery1, DiveBomber1, Engine1, Fighter1, Suo1, FlightControl1, Hull1, TorpedoBomber1, Torpedoes1, new ArrayList<>(), upgradesSkills1, adrenalineValue1, false, false, isLive);

        String returnedKey2 = apiService.setShipAPI(nation2, shipType2, ship2, ship_id2, Artillery2, DiveBomber2, Engine2, Fighter2, Suo2, FlightControl2, Hull2, TorpedoBomber2, Torpedoes2, new ArrayList<>());
        Ship shipAPI2 = apiService.getUpgradeSkillStats(returnedKey2, nation2, shipType2, ship2, ship_id2, Artillery2, DiveBomber2, Engine2, Fighter2, Suo2, FlightControl2, Hull2, TorpedoBomber2, Torpedoes2, new ArrayList<>(), upgradesSkills2, adrenalineValue2, false, false, isLive);

        model.addAttribute("warship1", data.get(serverParam).get("rawShipData").get(ship1));
        model.addAttribute("warship2", data.get(serverParam).get("rawShipData").get(ship2));
        model.addAttribute("encyclopedia", data.get(serverParam).get("encyclopedia"));

        if (shipAPI1 != null && shipAPI2 != null)
        {
            model.addAttribute("shipAPI1", shipAPI1);
            model.addAttribute("shipAPI2", shipAPI2);
        }
        return "WarshipComparison/shipStatComparisonStat :: shipAPIData";
    }

    @ResponseBody
    @RequestMapping (value = "/shortenUrl", method = RequestMethod.POST)
    public String getShortUrl(@RequestBody String longUrl) throws Exception
    {
        return apiService.shortenUrl(longUrl);
    }

    @RequestMapping (value = "/contact", method = RequestMethod.GET)
    public String getContact(Model model)
    {
        model.addAttribute("serverParam", serverParamAddress);
        return "contact";
    }

    @RequestMapping (value = "/contact", method = RequestMethod.POST)
    public void postEmail(@RequestBody EmailModel email) throws Exception
    {
        mailService.postEmail(email);
        discordWebhook.sendDiscordWebhookEmail(email);
    }
}