package WoWSSSC.service;

import WoWSSSC.model.WoWSAPI.consumables.Consumables;
import WoWSSSC.model.WoWSAPI.shipprofile.Ship;
import WoWSSSC.model.WoWSAPI.skills.CrewSkills;
import WoWSSSC.model.WoWSAPI.warships.Warship;
import WoWSSSC.model.WoWSAPI.warships.WarshipModulesTree;
import WoWSSSC.model.gameparams.commanders.GPCommander;
import WoWSSSC.model.gameparams.commanders.UniqueTemp;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;

import java.net.URLDecoder;
import java.util.*;

@Service
public class ParserService
{
    @Autowired
    private APIService apiService;

    @Autowired
    private HashMap<String, LinkedHashMap<String, LinkedHashMap>> data;

    private ObjectMapper mapper = new ObjectMapper();

    public HashSet<String> getModules(Warship warship, String moduleN)
    {
        HashSet<String> modules = null;
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
        return modules;
    }

    public HashSet<String> getUpgrades(Warship warship, String upgradeN)
    {
        HashSet<String> upgrades = null;
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
        return upgrades;
    }

    public HashSet<String> getFlags(String serverParam, String flagN)
    {
        HashSet<String> flags = null;
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
        return flags;
    }

    public HashSet<String> getConsumables(String s0, String s1, String s2, String s3)
    {
        HashSet<String> consumables = null;
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
        return consumables;
    }

    public HashSet<CrewSkills> getSkills(String skills, String skillN, HashSet<String> skillsH)
    {
        try {
            if (StringUtils.isNotEmpty(skills) && !skills.contains("tier") && !skills.contains("type_id") && !skills.contains("[]")) {
                skills = new String(Base64.getDecoder().decode(skills));
                skills = URLDecoder.decode(skills, "UTF-8");
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
            return crewSkills;
        }
        catch (Exception e) {
            return null;
        }
    }

    public HashSet<String> getUSkills(String serverParam, String nation, String commander, String uSkills, String uSkillN, HashSet<String> uSkillsH)
    {
        try {
            if (StringUtils.isNotEmpty(uSkills)) {
                uSkills = new String(Base64.getDecoder().decode(uSkills));
                uSkills = URLDecoder.decode(uSkills, "UTF-8");
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
            return crewUSkills;
        }
        catch (Exception e) {
            return null;
        }
    }

    public void setApiModules(Model model,
                              String serverParam,
                              String nation, String shipType, String ship, String ship_id, Warship warship,
                              String moduleN, List<String> modules, String Artillery, String DiveBomber, String Engine, String Fighter, String Suo, String FlightControl, String Hull, String TorpedoBomber, String Torpedoes,
                              HashMap<String, List> upgradesSkills, String upgradeN, String commander, String skillN, String uSkillN, boolean camo, String flagN, int adrenalineValue, int ar,
                              String s0, String s1, String s2, String s3, boolean upgradeCompare, String dataIndex, boolean isLive) throws Exception
    {
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

        String returnedKey = apiService.setShipAPI(nation, shipType, ship, ship_id, Artillery, DiveBomber, Engine, Fighter, Suo, FlightControl, Hull, TorpedoBomber, Torpedoes);
        Ship shipAPI = apiService.getUpgradeSkillStats(returnedKey, nation, shipType, ship, ship_id, Artillery, DiveBomber, Engine, Fighter, Suo, FlightControl, Hull, TorpedoBomber, Torpedoes, modules, upgradesSkills, adrenalineValue, false, true, isLive);
        model.addAttribute("shipAPI", shipAPI);

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

        model.addAttribute("upgradeCompare", upgradeCompare);
        if (upgradeCompare) {
            model.addAttribute("configurationAPI", apiService.getUpgradeSkillStats(returnedKey, nation, shipType, ship, ship_id, Artillery, DiveBomber, Engine, Fighter, Suo, FlightControl, Hull, TorpedoBomber, Torpedoes, modules, new HashMap<>(), 100, false, true, isLive));
        }

        model.addAttribute("stockCompare", false);
        model.addAttribute("dataIndex", dataIndex);
    }
}