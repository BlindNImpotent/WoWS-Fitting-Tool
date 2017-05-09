package WoWSSSC.controller;

import WoWSSSC.model.WoWSAPI.warships.Warship;
import WoWSSSC.model.gameparams.ShipComponents.ShipComponents;
import WoWSSSC.model.WoWSAPI.shipprofile.Ship;
import WoWSSSC.model.WoWSAPI.skills.CrewSkills;
import WoWSSSC.service.APIService;
import WoWSSSC.service.GPService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.*;

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

    @Autowired
    private HashMap<String, Ship> shipHashMap;

    private static final Logger logger = LoggerFactory.getLogger(APIController.class);

    ObjectMapper mapper = new ObjectMapper();

    private long cacheStart = 0;

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
    public String home(Model model)
    {
        model.addAttribute("notification", notification);
        model.addAttribute("encyclopedia", data.get("encyclopedia"));

        return "home";
    }

    @RequestMapping (value = "/WarshipStats", method = RequestMethod.GET)
    public String WarshipStats(Model model, @RequestParam(required = false, defaultValue="false") boolean mobile)
    {
        model.addAttribute("data", data);
        model.addAttribute("notification", notification);
        model.addAttribute("encyclopedia", data.get("encyclopedia"));

        if (mobile)
        {
            return "WarshipStats/warshipHomeMobile";
        }
        return "WarshipStats/warshipHome";
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
                    @RequestParam(required = false) HashSet<String> flags,
                    @RequestParam(required = false) HashSet<String> consumables,
                    @RequestParam(required = false) String skills,
                    @RequestParam(required = false, defaultValue = "100") int adrenalineValue,
                    @RequestParam(required = false) boolean camo,
                    @RequestParam(required = false, defaultValue = "false") boolean mobile
            ) throws IOException
    {
        model.addAttribute("notification", notification);
        model.addAttribute("encyclopedia", data.get("encyclopedia"));
        if (nation != null && shipType != null && ship != null)
        {
            if (request.getMethod().equalsIgnoreCase("post"))
            {
                logger.info("Loading " + nation + " " + shipType + " " + ship);
                model.addAttribute("warship", ((LinkedHashMap<String, LinkedHashMap>) data.get("nations").get(nation)).get(shipType).get(ship));

                return "WarshipStats/warshipPage :: warshipStats";
            }
            else
            {
                logger.info("Loading " + nation + " " + shipType + " " + ship + " from /warship?" + request.getQueryString());

                if (!skills.contains("tier") && !skills.contains("type_id") && !skills.contains("[]"))
                {
                    skills = new String(Base64.getDecoder().decode(skills));
                    skills = URLDecoder.decode(skills, "UTF-8");
                }

                HashSet<CrewSkills> crewSkills = skills != null ? mapper.readValue(skills, HashSet.class) : new HashSet<>();

                redirectAttributes.addFlashAttribute("url", "/warship?" + request.getQueryString());
                redirectAttributes.addFlashAttribute("modules", modules);
                redirectAttributes.addFlashAttribute("upgrades", upgrades);
                redirectAttributes.addFlashAttribute("adrenalineValue", adrenalineValue);
                redirectAttributes.addFlashAttribute("flags", flags);
                redirectAttributes.addFlashAttribute("consumables", consumables);
                redirectAttributes.addFlashAttribute("crewSkills", crewSkills);
                redirectAttributes.addFlashAttribute("camo", camo);
                redirectAttributes.addFlashAttribute("mobile", mobile);
                redirectAttributes.addFlashAttribute("warship", ((LinkedHashMap<String, LinkedHashMap>) data.get("nations").get(nation)).get(shipType).get(ship));
                redirectAttributes.addFlashAttribute("nation", nation).addFlashAttribute("shipType", shipType).addFlashAttribute("ship", ship);
            }
        }
        return "redirect:/WarshipStats?mobile=" + mobile;
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
                    @RequestBody(required = false) HashMap<String, List> upgradesSkills,
                    @RequestParam(required = false, defaultValue = "100") int adrenalineValue,
                    @RequestParam(required = false) List<String> modules,
                    @RequestParam(required = false, defaultValue = "false") boolean stockCompare,
                    @RequestParam(required = false, defaultValue = "false") boolean upgradeCompare,
                    @RequestParam(required = false, defaultValue = "false") boolean mobile
            ) throws Exception
    {
        if (!ship_id.equals(""))
        {
            logger.info("Ship API");

            if (System.currentTimeMillis() - cacheStart >= 60 * 60 * 1000)
            {
                apiService.cacheEvictShipHashMap();
                cacheStart = System.currentTimeMillis();
            }

            String returnedKey = apiService.setShipAPI(nation, shipType, ship, ship_id, Artillery, DiveBomber, Engine, Fighter, Suo, FlightControl, Hull, TorpedoBomber, Torpedoes, modules);
            Ship shipAPI = apiService.getUpgradeSkillStats(returnedKey, nation, shipType, ship, ship_id, Artillery, DiveBomber, Engine, Fighter, Suo, FlightControl, Hull, TorpedoBomber, Torpedoes, modules, upgradesSkills, adrenalineValue);
            model.addAttribute("shipAPI", shipAPI);

            if (upgradesSkills != null)
            {
                if (upgradesSkills.get("skills") != null)
                {
                    ((List<HashMap>) upgradesSkills.get("skills")).forEach(skill ->
                    {
                        if (skill.get("tier").equals("2") && skill.get("type_id").equals("6"))
                        {
                            model.addAttribute("adrenaline", true);
                            model.addAttribute("adrenalineValue", adrenalineValue);
                        }
                    });
                }
            }

            model.addAttribute("upgradeCompare", upgradeCompare);
            if (upgradeCompare)
            {
                model.addAttribute("configurationAPI", apiService.getUpgradeSkillStats(returnedKey, nation, shipType, ship, ship_id, Artillery, DiveBomber, Engine, Fighter, Suo, FlightControl, Hull, TorpedoBomber, Torpedoes, modules, new HashMap<>(), 100));
            }

            model.addAttribute("stockCompare", stockCompare);
            if (stockCompare)
            {
                String stockKey = apiService.setShipAPI(nation, shipType, ship, ship_id, "", "", "", "", "", "", "", "", "", new ArrayList<>());
                model.addAttribute("stockAPI", apiService.getUpgradeSkillStats(stockKey, nation, shipType, ship, ship_id, Artillery, DiveBomber, Engine, Fighter, Suo, FlightControl, Hull, TorpedoBomber, Torpedoes, modules, new HashMap<>(), 100));
            }
        }

        if (mobile)
        {
            return "WarshipStats/shipAPIPageMobile :: shipAPIData";
        }
        return "WarshipStats/shipAPIPage :: shipAPIData";
    }

    @ResponseBody
    @RequestMapping (value = "/gpService", method = RequestMethod.POST)
    public ShipComponents shipGP
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
            @RequestBody(required = false) HashMap<String, List> upgradesSkills,
            @RequestParam(required = false) List<String> modules
    ) throws Exception
    {
        String key = "&ship_id=" + ship_id + "&artillery_id=" + Artillery + "&dive_bomber_id=" + DiveBomber + "&engine_id=" + Engine
                + "&fighter_id=" + Fighter + "&fire_control_id=" + Suo + "&flight_control_id=" + FlightControl + "&hull_id=" + Hull + "&torpedo_bomber_id=" + TorpedoBomber + "&torpedoes_id=" + Torpedoes;

        return apiService.getUpgradeSkillStats(key, nation, shipType, ship, ship_id, Artillery, DiveBomber, Engine, Fighter, Suo, FlightControl, Hull, TorpedoBomber, Torpedoes, modules, upgradesSkills, 100).getShipComponents();
    }

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
        String key = "&ship_id=" + ship_id + "&artillery_id=" + Artillery + "&dive_bomber_id=" + DiveBomber + "&engine_id=" + Engine
            + "&fighter_id=" + Fighter + "&fire_control_id=" + Suo + "&flight_control_id=" + FlightControl + "&hull_id=" + Hull + "&torpedo_bomber_id=" + TorpedoBomber + "&torpedoes_id=" + Torpedoes;

        Ship shipAPI = apiService.getUpgradeSkillStats(key, nation, shipType, ship, ship_id, Artillery, DiveBomber, Engine, Fighter, Suo, FlightControl, Hull, TorpedoBomber, Torpedoes, modules, upgradesSkills, 100);

        model.addAttribute("shipAPI", shipAPI);

        if (shipAPI != null)
        {
            model.addAttribute("shipComponents", shipAPI.getShipComponents());
        }

        model.addAttribute("consumables", upgradesSkills.get("consumables"));

        return "WarshipStats/consumablesPage";
    }

    @RequestMapping (value = "/shipTree", method = RequestMethod.GET)
    public String shipTree(Model model, @RequestParam(required = false, defaultValue = "false") boolean mobile)
    {
        model.addAttribute("nations", data.get("nations"));
        model.addAttribute("premiumTable", data.get("premiumTable"));

        if (mobile)
        {
            return "WarshipResearch/shipTreeMobile";
        }
        return "WarshipResearch/shipTree";
    }

    @ResponseBody
    @RequestMapping (value = "/shipTree", method = RequestMethod.POST)
    public long getXp(@RequestBody List<String> shipList)
    {
        return apiService.getXp(shipList);
    }

    @RequestMapping (value = "/shipStatComparison", method = RequestMethod.GET)
    public String shipStatComparisonTree(Model model)
    {
        model.addAttribute("data", data);
        model.addAttribute("nations", data.get("nations"));
        model.addAttribute("premiumTable", data.get("premiumTable"));
        model.addAttribute("rawShipData", data.get("rawShipData"));

        return "WarshipComparison/shipStatComparisonTree";
    }

    @RequestMapping (value = "/shipStatSelection", method = { RequestMethod.GET, RequestMethod.POST })
    public String shipStatSelection(Model model, RedirectAttributes redirectAttributes,
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
        if ((StringUtils.isNotEmpty(ship1) && StringUtils.isNotEmpty(ship2)) || !CollectionUtils.isEmpty(shipList))
        {
            if (request.getMethod().equalsIgnoreCase("post"))
            {
                model.addAttribute("warship1", data.get("rawShipData").get(shipList.get(0)));
                model.addAttribute("warship2", data.get("rawShipData").get(shipList.get(1)));

                return "WarshipComparison/shipStatSelection :: warshipSelection";
            }
            else
            {
                logger.info("Loading Ship Comparison from /shipStatSelection?" + request.getQueryString());

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

                Warship warship1 = (Warship) data.get("rawShipData").get(ship1);
                Warship warship2 = (Warship) data.get("rawShipData").get(ship2);

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

                redirectAttributes.addFlashAttribute("url", "/shipStatComparison?" + request.getQueryString());
                redirectAttributes.addFlashAttribute("warship1", warship1);
                redirectAttributes.addFlashAttribute("ship1", ship1);
                redirectAttributes.addFlashAttribute("warship2", warship2);
                redirectAttributes.addFlashAttribute("ship2", ship2);
                redirectAttributes.addFlashAttribute("modules1", modules1);
                redirectAttributes.addFlashAttribute("modules2", modules2);

                if (upgradesSkills1.size() > 0 || upgradesSkills2.size() > 0)
                {
                    redirectAttributes.addFlashAttribute("upgrades1", upgradesSkills1.get("upgrades"));
                    redirectAttributes.addFlashAttribute("upgrades2", upgradesSkills2.get("upgrades"));
                    redirectAttributes.addFlashAttribute("flags", upgradesSkills1.get("flags"));
                    redirectAttributes.addFlashAttribute("camo", upgradesSkills1.get("camouflage").get(0));
                    redirectAttributes.addFlashAttribute("crewSkills", upgradesSkills1.get("skills"));
                }
                else
                {
                    redirectAttributes.addFlashAttribute("upgrades1", upgrades1Converted.get("upgrades"));
                    redirectAttributes.addFlashAttribute("upgrades2", upgrades2Converted.get("upgrades"));
                    redirectAttributes.addFlashAttribute("flags", flags);
                    redirectAttributes.addFlashAttribute("camo", camo);
                    redirectAttributes.addFlashAttribute("crewSkills", skillsConverted);
                }

                redirectAttributes.addFlashAttribute("consumables1", consumables1);
                redirectAttributes.addFlashAttribute("consumables2", consumables2);
                redirectAttributes.addFlashAttribute("adrenalineValue1", adrenalineValue1);
                redirectAttributes.addFlashAttribute("adrenalineValue2", adrenalineValue2);
            }
        }
        return "redirect:/shipStatComparison";
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
            });
        }

        upgradesSkills1.put("skills", (List<HashMap>) upgradesSkillsV2.get("skills").get("skills"));
        upgradesSkills1.put("flags", (List<String>) upgradesSkillsV2.get("flags").get("flags"));
        upgradesSkills1.put("camouflage", (List<Boolean>) upgradesSkillsV2.get("camouflage").get("camouflage"));

        upgradesSkills2.put("skills", (List<HashMap>) upgradesSkillsV2.get("skills").get("skills"));
        upgradesSkills2.put("flags", (List<String>) upgradesSkillsV2.get("flags").get("flags"));
        upgradesSkills2.put("camouflage", (List<Boolean>) upgradesSkillsV2.get("camouflage").get("camouflage"));

        logger.info("Ship Comparison");

        if (System.currentTimeMillis() - cacheStart >= 60 * 60 * 1000)
        {
            apiService.cacheEvictShipHashMap();
            cacheStart = System.currentTimeMillis();
        }

        String returnedKey1 = apiService.setShipAPI(nation1, shipType1, ship1, ship_id1, Artillery1, DiveBomber1, Engine1, Fighter1, Suo1, FlightControl1, Hull1, TorpedoBomber1, Torpedoes1, new ArrayList<>());
        Ship shipAPI1 = apiService.getUpgradeSkillStats(returnedKey1, nation1, shipType1, ship1, ship_id1, Artillery1, DiveBomber1, Engine1, Fighter1, Suo1, FlightControl1, Hull1, TorpedoBomber1, Torpedoes1, new ArrayList<>(), upgradesSkills1, adrenalineValue1);

        String returnedKey2 = apiService.setShipAPI(nation2, shipType2, ship2, ship_id2, Artillery2, DiveBomber2, Engine2, Fighter2, Suo2, FlightControl2, Hull2, TorpedoBomber2, Torpedoes2, new ArrayList<>());
        Ship shipAPI2 = apiService.getUpgradeSkillStats(returnedKey2, nation2, shipType2, ship2, ship_id2, Artillery2, DiveBomber2, Engine2, Fighter2, Suo2, FlightControl2, Hull2, TorpedoBomber2, Torpedoes2, new ArrayList<>(), upgradesSkills2, adrenalineValue2);

        if (shipAPI1 != null && shipAPI2 != null)
        {
            model.addAttribute("shipAPI1", shipAPI1);
            model.addAttribute("warship1", data.get("rawShipData").get(ship1));

            model.addAttribute("shipAPI2", shipAPI2);
            model.addAttribute("warship2", data.get("rawShipData").get(ship2));

            model.addAttribute("encyclopedia", data.get("encyclopedia"));
        }
        return "WarshipComparison/shipStatComparisonStat :: shipAPIData";
    }

    @ResponseBody
    @RequestMapping (value = "/shortenUrl", method = RequestMethod.POST)
    public String getShortUrl(@RequestBody String longUrl) throws Exception
    {
        return apiService.shortenUrl(longUrl);
    }
}