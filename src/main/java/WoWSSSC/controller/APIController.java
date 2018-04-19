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
        
        if (StringUtils.isNotEmpty(nation) && StringUtils.isNotEmpty(shipType) && StringUtils.isNotEmpty(ship)) {
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

            if (StringUtils.isNotEmpty(skills) && !skills.contains("tier") && !skills.contains("type_id") && !skills.contains("[]")) {
                skills = new String(Base64.getDecoder().decode(skills));
                skills = URLDecoder.decode(skills, "UTF-8");
            }

            if (StringUtils.isNotEmpty(uSkills)) {
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

        return "FittingTool/ftHome";
    }

    @RequestMapping (value = "/warship", method = { RequestMethod.GET, RequestMethod.POST })
    public String getWarship(HttpServletRequest request,
                            Model model,
                            @RequestParam(required = false) String nation,
                            @RequestParam(required = false) String shipType,
                            @RequestParam(required = false) String ship,
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

                logger.info("Loading " + nation + " " + shipType + " " + ship);
                model.addAttribute("warship", ((LinkedHashMap<String, LinkedHashMap>) data.get(serverParam).get("nations").get(nation)).get(shipType).get(ship));
                model.addAttribute("commanders", ((LinkedHashMap<String, LinkedHashMap>) data.get(serverParam).get("commanders").get(nation)).keySet());

                commander = URLDecoder.decode(commander, "UTF-8");
                commander = commander.equalsIgnoreCase("Steven Seagal") ? "John Doe" : commander;
                model.addAttribute("sCommander", commander);

                return "FittingTool/ftShipSelect :: warshipStats";
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
                    @RequestParam(required = false, defaultValue = "") String flagN,
                    @RequestParam(required = false, defaultValue = "false") boolean camo,
                    @RequestParam(required = false, defaultValue = "false") boolean stockCompare,
                    @RequestParam(required = false, defaultValue = "false") boolean upgradeCompare,
                    @RequestBody(required = false) HashMap<String, List> upgradesSkills,
                    @RequestParam(required = false, defaultValue = "") String s0,
                    @RequestParam(required = false, defaultValue = "") String s1,
                    @RequestParam(required = false, defaultValue = "") String s2,
                    @RequestParam(required = false, defaultValue = "") String s3,
                    @RequestParam(required = false, defaultValue = "true") boolean isFT,
                    @RequestParam(required = false, defaultValue = "") String dataIndex
            ) throws Exception
    {
        if (!ship_id.equals("")) {
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

            if (upgradesSkills != null) {
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
            if (upgradeCompare) {
                model.addAttribute("configurationAPI", apiService.getUpgradeSkillStats(returnedKey, nation, shipType, ship, ship_id, Artillery, DiveBomber, Engine, Fighter, Suo, FlightControl, Hull, TorpedoBomber, Torpedoes, modules, new HashMap<>(), 100, false, true, isLive));
            }

            model.addAttribute("stockCompare", stockCompare);
            if (stockCompare) {
                ModuleId moduleId = new ModuleId();
                String stockKey = apiService.setShipAPI(nation, shipType, ship, ship_id, "", "", "", "", "", "", "", "", "", new ArrayList<>());
                apiService.setModuleIds(shipHashMap.get(stockKey), moduleId);
                model.addAttribute("configurationAPI", apiService.getUpgradeSkillStats(stockKey, nation, shipType, ship, ship_id, moduleId.getArtillery_id(), moduleId.getDive_bomber_id(), moduleId.getEngine_id(), moduleId.getFighter_id(), moduleId.getFire_control_id(), moduleId.getFlight_control_id(), moduleId.getHull_id(), moduleId.getTorpedo_bomber_id(), moduleId.getTorpedoes_id(), new ArrayList<>(), new HashMap<>(), 100, true, true, isLive));
            }
            model.addAttribute("dataIndex", dataIndex);
        }

        return isFT ? "FittingTool/ftAPIPage :: shipAPIData" : "ShipComparison/scAPIPage :: shipAPIData";
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

        if (xp >= 0) {
            logger.info(String.format("%,d", xp)  + " xp required");
        }
        else {
            logger.info("Wrong path of research");
        }

        return xp;
    }

    @RequestMapping (value = "/shipStatComparison", method = RequestMethod.GET)
    public String shipStatComparisonTree(Model model, HttpServletRequest request) throws IOException
    {
        model.addAttribute("serverParam", serverParamAddress);
        model.addAttribute("nations", data.get(serverParam).get("nations"));
        model.addAttribute("encyclopedia", data.get(serverParam).get("encyclopedia"));

        return "ShipComparison/scHome";
    }

    @RequestMapping (value = "/shipStatSelection", method = RequestMethod.GET)
    public String shipStatSelection(Model model, HttpServletRequest request) throws IOException
    {
        return "redirect:/shipStatComparison";
    }

    @RequestMapping (value = "/warshipSC", method = RequestMethod.POST)
    public String getWarshipShipComparison(HttpServletRequest request,
                                           Model model,
                                           @RequestParam(required = false) String nation,
                                           @RequestParam(required = false) String shipType,
                                           @RequestParam(required = false) String ship,
                                           @RequestParam(required = false) HashSet<String> modules,
                                           @RequestParam(required = false) HashSet<String> upgrades,
                                           @RequestParam(required = false) HashSet<String> flags,
                                           @RequestParam(required = false) HashSet<String> consumables,
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
                                           @RequestParam(required = false, defaultValue = "") String s3,
                                           @RequestParam(required = false, defaultValue = "") String dataIndex) throws IOException
    {
        if (nation != null && shipType != null && ship != null) {
            ship = URLDecoder.decode(ship, "UTF-8");
            logger.info("Loading " + nation + " " + shipType + " " + ship + " from Ship Comparison");

            model.addAttribute("serverParam", serverParamAddress);
            model.addAttribute("nations", data.get(serverParam).get("nations"));
            model.addAttribute("uniqueSkills", data.get(serverParam).get("uniqueSkills"));
            model.addAttribute("skills", data.get(serverParam).get("skills"));
            model.addAttribute("exteriors", data.get(serverParam).get("exteriors"));
            model.addAttribute("notification", notification);
            model.addAttribute("encyclopedia", data.get(serverParam).get("encyclopedia"));

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
            model.addAttribute("nation", nation).addAttribute("shipType", shipType).addAttribute("ship", ship);
            model.addAttribute("warship", warship);
            model.addAttribute("commanders", ((LinkedHashMap<String, LinkedHashMap>) data.get(serverParam).get("commanders").get(nation)).keySet());

            commander = URLDecoder.decode(commander, "UTF-8");
            commander = commander.equalsIgnoreCase("Steven Seagal") ? "John Doe" : commander;
            model.addAttribute("sCommander", commander);

            model.addAttribute("dataIndex", dataIndex);
        }
        return "ShipComparison/scShipSelect :: warshipStats";
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