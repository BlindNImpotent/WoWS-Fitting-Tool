package WoWSSSC.service;

import Parser.API_Parser;
import Parser.GameParams_Parser;
import WoWSSSC.model.*;
import lombok.Data;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Aesis on 2016-08-08.
 */
@Service
@Data
public class JSONService
{
    private API_Parser apiParser;
    private GameParams_Parser gameParamsParser;

    private HashMap<String, JSONObject> GameParamsIndexHashMap;
    private HashMap<String, JSONObject> GameParamsNameHashMap;

    private JSONObject apiShipJSON;
    private JSONObject gpShipJSON;

    private long tier;
    private String nation;
    private String shipType;

    private String ship_id_str;

    private JSONObject modules_treeJSON;
    private List<String> modules_treeList;
    private List<JSONObject> default_loadouts;
    private List<JSONObject> upgradeModules;
    
    private List<Module> API_ArtilleryModule;
    private List<Module> API_HullModule;
    private List<Module> API_EngineModule;
    private List<Module> API_RadarModule;
    private List<Module> API_TorpedoModule;

    private List<String> API_ArtilleryUpgradeIndexList;
    private List<String> API_HullUpgradeIndexList;
    private List<String> API_EngineUpgradeIndexList;
    private List<String> API_RadarUpgradeIndexList;
    private List<String> API_TorpedoUpgradeIndexList;

//    private List<JSONObject> API_ArtilleryUpgradeJSONList;
//    private List<String> API_ArtilleryUpgradeNameList;
    private HashMap<String, JSONObject> API_ArtilleryUpgradeJSONHashMap;
//
//    private List<JSONObject> API_HullUpgradeJSONList;
//    private List<String> API_HullUpgradeNameList;
    private HashMap<String, JSONObject> API_HullUpgradeJSONHashMap;
//
//    private List<JSONObject> API_EngineUpgradeJSONList;
//    private List<String> API_EngineUpgradeNameList;
    private HashMap<String, JSONObject> API_EngineUpgradeJSONHashMap;
//
//    private List<JSONObject> API_SuoUpgradeJSONList;
//    private List<String> API_SuoUpgradeNameList;
    private HashMap<String, JSONObject> API_SuoUpgradeJSONHashMap;
//
//    private List<JSONObject> API_TorpedoesUpgradeJSONList;
//    private List<String> API_TorpedoesUpgradeNameList;
    private HashMap<String, JSONObject> API_TorpedoesUpgradeJSONHashMap;

    private JSONObject API_ArtilleryUpgradeJSON;
    private JSONObject API_HullUpgradeJSON;
    private JSONObject API_EngineUpgradeJSON;
    private JSONObject API_RadarUpgradeJSON;
    private JSONObject API_TorpedoUpgradeJSON;

    private String imagesMedium;

    private List<Long> API_UpgradesIDList;
    private JSONObject GP_UpgradesJSON;
    private List<String> UpgradesNameList;
    private JSONObject ModernizationSlot1;
    private JSONObject ModernizationSlot2;
    private JSONObject ModernizationSlot3;
    private JSONObject ModernizationSlot4;
    private JSONObject ModernizationSlot5;
    private JSONObject ModernizationSlot6;
    private JSONArray ModernizationSlot1_mods;
    private JSONArray ModernizationSlot2_mods;
    private JSONArray ModernizationSlot3_mods;
    private JSONArray ModernizationSlot4_mods;
    private JSONArray ModernizationSlot5_mods;
    private JSONArray ModernizationSlot6_mods;
//    private List<String> modSlot1;
//    private List<String> modSlot2;
//    private List<String> modSlot3;
//    private List<String> modSlot4;
//    private List<String> modSlot5;
//    private List<String> modSlot6;
//    private List<String> modSlot1Images;
//    private List<String> modSlot2Images;
//    private List<String> modSlot3Images;
//    private List<String> modSlot4Images;
//    private List<String> modSlot5Images;
//    private List<String> modSlot6Images;
//    private LinkedHashMap<String, JSONObject> mods1;
//    private LinkedHashMap<String, JSONObject> mods2;
//    private LinkedHashMap<String, JSONObject> mods3;
//    private LinkedHashMap<String, JSONObject> mods4;
//    private LinkedHashMap<String, JSONObject> mods5;
//    private LinkedHashMap<String, JSONObject> mods6;
//    private List<String> modSlot1Name;
//    private List<String> modSlot2Name;
//    private List<String> modSlot3Name;
//    private List<String> modSlot4Name;
//    private List<String> modSlot5Name;
//    private List<String> modSlot6Name;

    private List<JSONArray> Abil0;
    private List<JSONArray> Abil1;
    private List<JSONArray> Abil2;
    private List<JSONArray> Abil3;

    private List<String> Ability0;
    private List<String> Ability1;
    private List<String> Ability2;
    private List<String> Ability3;

    private double afterBattleRepair;
    private double visibilityFactorPermaCamo;
    private double visibilityFactorByPlanePermaCamo;
    private double expFactorPermaCamo;

    private JSONObject permaflage;
    private HashMap<String, JSONObject> permaflageHashMap;

    private List<Flags> flagsList;
    
    private HashMap<String, JSONObject> API_FlightControlUpgradeJSONHashMap; 
    private List<String> API_FlightControlUpgradeIndexList;
    private List<Module> API_FlightControlModule;
    private JSONObject API_FlightControlUpgradeJSON;

    private HashMap<String, JSONObject> API_FighterUpgradeJSONHashMap;
    private List<String> API_FighterUpgradeIndexList;
    private List<Module> API_FighterModule;
    private JSONObject API_FighterUpgradeJSON;

    private HashMap<String, JSONObject> API_TorpedoBomberUpgradeJSONHashMap;
    private List<String> API_TorpedoBomberUpgradeIndexList;
    private List<Module> API_TorpedoBomberModule;
    private JSONObject API_TorpedoBomberUpgradeJSON;

    private HashMap<String, JSONObject> API_DiveBomberUpgradeJSONHashMap;
    private List<String> API_DiveBomberUpgradeIndexList;
    private List<Module> API_DiveBomberModule;
    private JSONObject API_DiveBomberUpgradeJSON;

    private List<Camouflage> camouflages;

    private List<String> camouflagesIdList;

    private List<String> flagsIdList;

    private List<Upgrade> upgrades1;
    private List<Upgrade> upgrades2;
    private List<Upgrade> upgrades3;
    private List<Upgrade> upgrades4;
    private List<Upgrade> upgrades5;
    private List<Upgrade> upgrades6;
    
    private List<Skills> skillsList;

//    @Cacheable("setShipJSON")
    public synchronized void setShipJSON(String name) throws IOException, ParseException
    {
        apiParser = new API_Parser();
        gameParamsParser = new GameParams_Parser();

        GameParamsIndexHashMap = gameParamsParser.getGameParamsIndexHashMap();
        GameParamsNameHashMap = gameParamsParser.getGameParamsNameHashMap();

        apiShipJSON = apiParser.getAPIShipsHashMap().get(name);
        gpShipJSON = gameParamsParser.getGameParamsIndexHashMap().get(apiShipJSON.get("ship_id_str"));

        nation = (String) apiShipJSON.get("nation");
        tier = (long) apiShipJSON.get("tier");
        shipType = (String) apiShipJSON.get("type");

        ship_id_str = (String) apiShipJSON.get("ship_id_str");

        JSONObject images = (JSONObject) apiShipJSON.get("images");
        imagesMedium = (String) images.get("medium");

        setShipUpgradeModulesInfo();
        setUpgrades();
        setConsumablesList();
        setPermaflage(name);
        setFlagsList();
        setCrewSkills();
    }

    public JSONObject getGameParamsIndexJSON(String index)
    {
        return GameParamsIndexHashMap.get(index);
    }

    public JSONObject getGameParamsNameJSON(String name)
    {
        return GameParamsNameHashMap.get(name);
    }

    public JSONObject getAPI_HullJSON(String module_id)
    {
        return (JSONObject) apiParser.getAPI_Modules_HullJSON().get(module_id);
    }

    private void setShipUpgradeModulesInfo()
    {
        modules_treeList = new ArrayList<>();
        default_loadouts = new ArrayList<>();
        upgradeModules = new ArrayList<>();

//        API_ArtilleryUpgradeJSONList = new ArrayList<>();
//        API_ArtilleryUpgradeNameList = new ArrayList<>();
        API_ArtilleryUpgradeJSONHashMap = new HashMap<>();
//        API_HullUpgradeJSONList = new ArrayList<>();
//        API_HullUpgradeNameList = new ArrayList<>();
        API_HullUpgradeJSONHashMap = new HashMap<>();
//        API_EngineUpgradeJSONList = new ArrayList<>();
//        API_EngineUpgradeNameList = new ArrayList<>();
        API_EngineUpgradeJSONHashMap = new HashMap<>();
//        API_SuoUpgradeJSONList = new ArrayList<>();
//        API_SuoUpgradeNameList = new ArrayList<>();
        API_SuoUpgradeJSONHashMap = new HashMap<>();
//        API_TorpedoesUpgradeJSONList = new ArrayList<>();
//        API_TorpedoesUpgradeNameList = new ArrayList<>();
        API_TorpedoesUpgradeJSONHashMap = new HashMap<>();

        API_ArtilleryModule = new ArrayList<>();
        API_HullModule = new ArrayList<>();
        API_EngineModule = new ArrayList<>();
        API_RadarModule = new ArrayList<>();
        API_TorpedoModule = new ArrayList<>();

        API_ArtilleryUpgradeIndexList = new ArrayList<>();
        API_HullUpgradeIndexList = new ArrayList<>();
        API_EngineUpgradeIndexList = new ArrayList<>();
        API_RadarUpgradeIndexList = new ArrayList<>();
        API_TorpedoUpgradeIndexList = new ArrayList<>();

        API_FlightControlUpgradeJSONHashMap = new HashMap<>();
        API_FlightControlUpgradeIndexList = new ArrayList<>();
        API_FlightControlModule = new ArrayList<>();

        API_FighterUpgradeJSONHashMap = new HashMap<>();
        API_FighterUpgradeIndexList = new ArrayList<>();
        API_FighterModule = new ArrayList<>();

        API_TorpedoBomberUpgradeJSONHashMap = new HashMap<>();
        API_TorpedoBomberUpgradeIndexList = new ArrayList<>();
        API_TorpedoBomberModule = new ArrayList<>();

        API_DiveBomberUpgradeJSONHashMap = new HashMap<>();
        API_DiveBomberUpgradeIndexList = new ArrayList<>();
        API_DiveBomberModule = new ArrayList<>();
        
        modules_treeJSON = (JSONObject) apiShipJSON.get("modules_tree");

        modules_treeList.clear();
        modules_treeList.addAll(modules_treeJSON.keySet());

        JSONObject API_suiJSON;

        for (int i = 0; i < modules_treeList.size(); i++)
        {
            API_suiJSON = (JSONObject) modules_treeJSON.get(modules_treeList.get(i));

            String API_suiJSONName = (String) API_suiJSON.get("name");
            String API_suiJSONIndex = (String) API_suiJSON.get("module_id_str");
            long API_suiJSONId = (long) API_suiJSON.get("module_id");
            boolean API_suiJSONIsDefault = (boolean) API_suiJSON.get("is_default");

            if (API_suiJSON.get("type").equals("Artillery"))
            {
//                API_ArtilleryUpgradeJSONList.add(API_suiJSON);
//                API_ArtilleryUpgradeNameList.add(API_suiJSONName);
                API_ArtilleryUpgradeJSONHashMap.put(API_suiJSONIndex, API_suiJSON);
                API_ArtilleryUpgradeIndexList.add(API_suiJSONIndex);
                API_ArtilleryModule.add(new Module(API_suiJSONName, API_suiJSONIndex, API_suiJSONId, API_suiJSONIsDefault, API_suiJSON));
            }
            else if (API_suiJSON.get("type").equals("Hull"))
            {
//                API_HullUpgradeJSONList.add(API_suiJSON);
//                API_HullUpgradeNameList.add(API_suiJSONName);
                API_HullUpgradeJSONHashMap.put(API_suiJSONIndex, API_suiJSON);
                API_HullUpgradeIndexList.add(API_suiJSONIndex);
                API_HullModule.add(new Module(API_suiJSONName, API_suiJSONIndex, API_suiJSONId, API_suiJSONIsDefault, API_suiJSON));
            }
            else if (API_suiJSON.get("type").equals("Engine"))
            {
//                API_EngineUpgradeJSONList.add(API_suiJSON);
//                API_EngineUpgradeNameList.add(API_suiJSONName);
                API_EngineUpgradeJSONHashMap.put(API_suiJSONIndex, API_suiJSON);
                API_EngineUpgradeIndexList.add(API_suiJSONIndex);
                API_EngineModule.add(new Module(API_suiJSONName, API_suiJSONIndex, API_suiJSONId, API_suiJSONIsDefault, API_suiJSON));
            }
            else if (API_suiJSON.get("type").equals("Suo"))
            {
//                API_SuoUpgradeJSONList.add(API_suiJSON);
//                API_SuoUpgradeNameList.add(API_suiJSONName);
                API_SuoUpgradeJSONHashMap.put(API_suiJSONIndex, API_suiJSON);
                API_RadarUpgradeIndexList.add(API_suiJSONIndex);
                API_RadarModule.add(new Module(API_suiJSONName, API_suiJSONIndex, API_suiJSONId, API_suiJSONIsDefault, API_suiJSON));
            }
            else if (API_suiJSON.get("type").equals("Torpedoes"))
            {
//                API_TorpedoesUpgradeJSONList.add(API_suiJSON);
//                API_TorpedoesUpgradeNameList.add(API_suiJSONName);
                API_TorpedoesUpgradeJSONHashMap.put(API_suiJSONIndex, API_suiJSON);
                API_TorpedoUpgradeIndexList.add(API_suiJSONIndex);
                API_TorpedoModule.add(new Module(API_suiJSONName, API_suiJSONIndex, API_suiJSONId, API_suiJSONIsDefault, API_suiJSON));
            }
            else if (API_suiJSON.get("type").equals("FlightControl"))
            {
                API_FlightControlUpgradeJSONHashMap.put(API_suiJSONIndex, API_suiJSON);
                API_FlightControlUpgradeIndexList.add(API_suiJSONIndex);
                API_FlightControlModule.add(new Module(API_suiJSONName, API_suiJSONIndex, API_suiJSONId, API_suiJSONIsDefault, API_suiJSON));
            }
            else if (API_suiJSON.get("type").equals("Fighter"))
            {
                API_FighterUpgradeJSONHashMap.put(API_suiJSONIndex, API_suiJSON);
                API_FighterUpgradeIndexList.add(API_suiJSONIndex);
                API_FighterModule.add(new Module(API_suiJSONName, API_suiJSONIndex, API_suiJSONId, API_suiJSONIsDefault, API_suiJSON));
            }
            else if (API_suiJSON.get("type").equals("TorpedoBomber"))
            {
                API_TorpedoBomberUpgradeJSONHashMap.put(API_suiJSONIndex, API_suiJSON);
                API_TorpedoBomberUpgradeIndexList.add(API_suiJSONIndex);
                API_TorpedoBomberModule.add(new Module(API_suiJSONName, API_suiJSONIndex, API_suiJSONId, API_suiJSONIsDefault, API_suiJSON));
            }
            else if (API_suiJSON.get("type").equals("DiveBomber"))
            {
                API_DiveBomberUpgradeJSONHashMap.put(API_suiJSONIndex, API_suiJSON);
                API_DiveBomberUpgradeIndexList.add(API_suiJSONIndex);
                API_DiveBomberModule.add(new Module(API_suiJSONName, API_suiJSONIndex, API_suiJSONId, API_suiJSONIsDefault, API_suiJSON));
            }

        }

        API_ArtilleryModule.sort((o1, o2) -> (o1.getName()).compareTo(o2.getName()));
        API_HullModule.sort((o1, o2) -> (o1.getName()).compareTo(o2.getName()));
        API_EngineModule.sort((o1, o2) -> (o1.getName()).compareTo(o2.getName()));
        API_RadarModule.sort((o1, o2) -> (o1.getName()).compareTo(o2.getName()));
        API_TorpedoModule.sort((o1, o2) -> (o1.getName()).compareTo(o2.getName()));
        API_FlightControlModule.sort((o1, o2) -> (o1.getName()).compareTo(o2.getName()));
        API_FighterModule.sort((o1, o2) -> (o1.getName()).compareTo(o2.getName()));
        API_TorpedoBomberModule.sort((o1, o2) -> (o1.getName()).compareTo(o2.getName()));
        API_DiveBomberModule.sort((o1, o2) -> (o1.getName()).compareTo(o2.getName()));

        API_ArtilleryModule.sort((o1, o2) -> (o2.isDefault() ? 1 : 0) - (o1.isDefault() ? 1 : 0));
        API_HullModule.sort((o1, o2) -> (o2.isDefault() ? 1 : 0) - (o1.isDefault() ? 1 : 0));
        API_EngineModule.sort((o1, o2) -> (o2.isDefault() ? 1 : 0) - (o1.isDefault() ? 1 : 0));
        API_RadarModule.sort((o1, o2) -> (o2.isDefault() ? 1 : 0) - (o1.isDefault() ? 1 : 0));
        API_TorpedoModule.sort((o1, o2) -> (o2.isDefault() ? 1 : 0) - (o1.isDefault() ? 1 : 0));
        API_FlightControlModule.sort((o1, o2) -> (o2.isDefault() ? 1 : 0) - (o1.isDefault() ? 1 : 0));
        API_FighterModule.sort((o1, o2) -> (o2.isDefault() ? 1 : 0) - (o1.isDefault() ? 1 : 0));
        API_TorpedoBomberModule.sort((o1, o2) -> (o2.isDefault() ? 1 : 0) - (o1.isDefault() ? 1 : 0));
        API_DiveBomberModule.sort((o1, o2) -> (o2.isDefault() ? 1 : 0) - (o1.isDefault() ? 1 : 0));
        
//        API_ArtilleryUpgrade.sort((o1, o2) -> ((int) o1.getModuleId()) - (int) (o2.getModuleId()));
//        API_HullUpgrade.sort((o1, o2) -> ((int) o1.getModuleId()) - ((int) o2.getModuleId()));
//        API_EngineUpgrade.sort((o1, o2) -> ((int) o1.getModuleId()) - ((int) o2.getModuleId()));
//        API_RadarUpgrade.sort((o1, o2) -> ((int) o1.getModuleId()) - ((int) o2.getModuleId()));
//        API_TorpedoUpgrade.sort((o1, o2) -> ((int) o1.getModuleId()) - ((int) o2.getModuleId()));
//        API_FlightControlUpgrade.sort((o1, o2) -> ((int) o1.getModuleId()) - ((int) o2.getModuleId()));
//        API_FighterUpgrade.sort((o1, o2) -> ((int) o1.getModuleId()) - ((int) o2.getModuleId()));
//        API_TorpedoBomberUpgrade.sort((o1, o2) -> ((int) o1.getModuleId()) - ((int) o2.getModuleId()));
//        API_DiveBomberUpgrade.sort((o1, o2) -> ((int) o1.getModuleId()) - ((int) o2.getModuleId()));
        
        
        API_ArtilleryUpgradeJSON = new JSONObject(API_ArtilleryUpgradeJSONHashMap);
        API_HullUpgradeJSON = new JSONObject(API_HullUpgradeJSONHashMap);
        API_EngineUpgradeJSON = new JSONObject(API_EngineUpgradeJSONHashMap);
        API_RadarUpgradeJSON = new JSONObject(API_SuoUpgradeJSONHashMap);
        API_TorpedoUpgradeJSON = new JSONObject(API_TorpedoesUpgradeJSONHashMap);
        API_FlightControlUpgradeJSON = new JSONObject(API_FlightControlUpgradeJSONHashMap);
        API_FighterUpgradeJSON = new JSONObject(API_FighterUpgradeJSONHashMap);
        API_TorpedoBomberUpgradeJSON = new JSONObject(API_TorpedoBomberUpgradeJSONHashMap);
        API_DiveBomberUpgradeJSON = new JSONObject(API_DiveBomberUpgradeJSONHashMap);
        
        
//        Collections.sort(API_ArtilleryUpgradeNameList);
//        Collections.sort(API_HullUpgradeNameList);
//        Collections.sort(API_EngineUpgradeNameList);
//        Collections.sort(API_SuoUpgradeNameList);
//        Collections.sort(API_TorpedoesUpgradeNameList);
    }

    @SuppressWarnings("unchecked")
    private void setUpgrades()
    {
        API_UpgradesIDList = new ArrayList<>();        
        UpgradesNameList = new ArrayList<>();
        ModernizationSlot1 = null;
        ModernizationSlot2 = null;
        ModernizationSlot3 = null;
        ModernizationSlot4 = null;
        ModernizationSlot5 = null;
        ModernizationSlot6 = null;
        ModernizationSlot1_mods = null;
        ModernizationSlot2_mods = null;
        ModernizationSlot3_mods = null;
        ModernizationSlot4_mods = null;
        ModernizationSlot5_mods = null;
        ModernizationSlot6_mods = null;

//        modSlot1 = new ArrayList<>();
//        modSlot2 = new ArrayList<>();
//        modSlot3 = new ArrayList<>();
//        modSlot4 = new ArrayList<>();
//        modSlot5 = new ArrayList<>();
//        modSlot6 = new ArrayList<>();
//        modSlot1Images = new ArrayList<>();
//        modSlot2Images = new ArrayList<>();
//        modSlot3Images = new ArrayList<>();
//        modSlot4Images = new ArrayList<>();
//        modSlot5Images = new ArrayList<>();
//        modSlot6Images = new ArrayList<>();
//        mods1 = new LinkedHashMap<>();
//        mods2 = new LinkedHashMap<>();
//        mods3 = new LinkedHashMap<>();
//        mods4 = new LinkedHashMap<>();
//        mods5 = new LinkedHashMap<>();
//        mods6 = new LinkedHashMap<>();
//        modSlot1Name = new ArrayList<>();
//        modSlot2Name = new ArrayList<>();
//        modSlot3Name = new ArrayList<>();
//        modSlot4Name = new ArrayList<>();
//        modSlot5Name = new ArrayList<>();
//        modSlot6Name = new ArrayList<>();

        upgrades1 = new ArrayList<>();
        upgrades2 = new ArrayList<>();
        upgrades3 = new ArrayList<>();
        upgrades4 = new ArrayList<>();
        upgrades5 = new ArrayList<>();
        upgrades6 = new ArrayList<>();
        
        JSONArray modules = (JSONArray) apiShipJSON.get("upgrades");
        API_UpgradesIDList.addAll(modules);

        GP_UpgradesJSON = (JSONObject) gpShipJSON.get("ShipModernization");

        if (GP_UpgradesJSON.get("ModernizationSlot1") != null)
        {
            ModernizationSlot1 = (JSONObject) GP_UpgradesJSON.get("ModernizationSlot1");
            ModernizationSlot1_mods = (JSONArray) ModernizationSlot1.get("mods");
//            modSlot1.add("None1");
        }

        if (GP_UpgradesJSON.get("ModernizationSlot2") != null) {
            ModernizationSlot2 = (JSONObject) GP_UpgradesJSON.get("ModernizationSlot2");
            ModernizationSlot2_mods = (JSONArray) ModernizationSlot2.get("mods");
//            modSlot2.add("None2");
        }

        if (GP_UpgradesJSON.get("ModernizationSlot3") != null) {
            ModernizationSlot3 = (JSONObject) GP_UpgradesJSON.get("ModernizationSlot3");
            ModernizationSlot3_mods = (JSONArray) ModernizationSlot3.get("mods");
//            modSlot3.add("None3");
        }

        if (GP_UpgradesJSON.get("ModernizationSlot4") != null) {
            ModernizationSlot4 = (JSONObject) GP_UpgradesJSON.get("ModernizationSlot4");
            ModernizationSlot4_mods = (JSONArray) ModernizationSlot4.get("mods");
//            modSlot4.add("None4");
        }

        if (GP_UpgradesJSON.get("ModernizationSlot5") != null) {
            ModernizationSlot5 = (JSONObject) GP_UpgradesJSON.get("ModernizationSlot5");
            ModernizationSlot5_mods = (JSONArray) ModernizationSlot5.get("mods");
//            modSlot5.add("None5");
        }

        if (GP_UpgradesJSON.get("ModernizationSlot6") != null) {
            ModernizationSlot6 = (JSONObject) GP_UpgradesJSON.get("ModernizationSlot6");
            ModernizationSlot6_mods = (JSONArray) ModernizationSlot6.get("mods");
//            modSlot6.add("None6");
        }

        JSONObject ship_modifications = (JSONObject) apiParser.getAPI_InfoEncyclopediaJSON().get("ship_modifications");

        if (ModernizationSlot1_mods != null)
        {
            for (int i = 0; i < ModernizationSlot1_mods.size(); i++) 
            {
                upgrades1.add(new Upgrade((String) ModernizationSlot1_mods.get(i), (String) ship_modifications.get(ModernizationSlot1_mods.get(i)), (Long) ModernizationSlot1.get("slot"), gameParamsParser.getGameParamsNameHashMap().get(ModernizationSlot1_mods.get(i)), ModernizationSlot1_mods.get(i) + ".png"));
            }
        }

        if (ModernizationSlot2_mods != null)
        {
            for (int i = 0; i < ModernizationSlot2_mods.size(); i++)
            {
                upgrades2.add(new Upgrade((String) ModernizationSlot2_mods.get(i), (String) ship_modifications.get(ModernizationSlot2_mods.get(i)), (Long) ModernizationSlot2.get("slot"), gameParamsParser.getGameParamsNameHashMap().get(ModernizationSlot2_mods.get(i)), ModernizationSlot2_mods.get(i) + ".png"));
            }
        }

        if (ModernizationSlot3_mods != null)
        {
            for (int i = 0; i < ModernizationSlot3_mods.size(); i++)
            {
                upgrades3.add(new Upgrade((String) ModernizationSlot3_mods.get(i), (String) ship_modifications.get(ModernizationSlot3_mods.get(i)), (Long) ModernizationSlot3.get("slot"), gameParamsParser.getGameParamsNameHashMap().get(ModernizationSlot3_mods.get(i)), ModernizationSlot3_mods.get(i) + ".png"));
            }
        }

        if (ModernizationSlot4_mods != null)
        {
            for (int i = 0; i < ModernizationSlot4_mods.size(); i++)
            {
                upgrades4.add(new Upgrade((String) ModernizationSlot4_mods.get(i), (String) ship_modifications.get(ModernizationSlot4_mods.get(i)), (Long) ModernizationSlot4.get("slot"), gameParamsParser.getGameParamsNameHashMap().get(ModernizationSlot4_mods.get(i)), ModernizationSlot4_mods.get(i) + ".png"));
            }
        }

        if (ModernizationSlot5_mods != null)
        {
            for (int i = 0; i < ModernizationSlot5_mods.size(); i++)
            {
                upgrades5.add(new Upgrade((String) ModernizationSlot5_mods.get(i), (String) ship_modifications.get(ModernizationSlot5_mods.get(i)), (Long) ModernizationSlot5.get("slot"), gameParamsParser.getGameParamsNameHashMap().get(ModernizationSlot5_mods.get(i)), ModernizationSlot5_mods.get(i) + ".png"));
            }
        }

        if (ModernizationSlot6_mods != null)
        {
            for (int i = 0; i < ModernizationSlot6_mods.size(); i++)
            {
                upgrades6.add(new Upgrade((String) ModernizationSlot6_mods.get(i), (String) ship_modifications.get(ModernizationSlot6_mods.get(i)), (Long) ModernizationSlot6.get("slot"), gameParamsParser.getGameParamsNameHashMap().get(ModernizationSlot6_mods.get(i)), ModernizationSlot6_mods.get(i) + ".png"));
            }
        }

//        if (ModernizationSlot1_mods != null) {
//            for (int i = 0; i < ModernizationSlot1_mods.size(); i++) {
//                mods1.put((String) ship_modifications.get(ModernizationSlot1_mods.get(i)), gameParamsParser.getGameParamsIndexHashMap().get(ModernizationSlot1_mods.get(i)));
//                modSlot1.add((String) ModernizationSlot1_mods.get(i));
//                modSlot1Images.add(ModernizationSlot1_mods.get(i) + ".png");
//            }
//        }
//
//        if (ModernizationSlot2_mods != null) {
//            for (int i = 0; i < ModernizationSlot2_mods.size(); i++) {
//                mods2.put((String) ship_modifications.get(ModernizationSlot2_mods.get(i)), gameParamsParser.getGameParamsIndexHashMap().get(ModernizationSlot2_mods.get(i)));
//                modSlot2.add((String) ModernizationSlot2_mods.get(i));
//            }
//        }
//
//        if (ModernizationSlot3_mods != null) {
//            for (int i = 0; i < ModernizationSlot3_mods.size(); i++) {
//                mods3.put((String) ship_modifications.get(ModernizationSlot3_mods.get(i)), gameParamsParser.getGameParamsIndexHashMap().get(ModernizationSlot3_mods.get(i)));
//                modSlot3.add((String) ModernizationSlot3_mods.get(i));
//            }
//        }
//
//        if (ModernizationSlot4_mods != null) {
//            for (int i = 0; i < ModernizationSlot4_mods.size(); i++) {
//                mods4.put((String) ship_modifications.get(ModernizationSlot4_mods.get(i)), gameParamsParser.getGameParamsIndexHashMap().get(ModernizationSlot4_mods.get(i)));
//                modSlot4.add((String) ModernizationSlot4_mods.get(i));
//            }
//        }
//
//        if (ModernizationSlot5_mods != null) {
//            for (int i = 0; i < ModernizationSlot5_mods.size(); i++) {
//                mods5.put((String) ship_modifications.get(ModernizationSlot5_mods.get(i)), gameParamsParser.getGameParamsIndexHashMap().get(ModernizationSlot5_mods.get(i)));
//                modSlot5.add((String) ModernizationSlot5_mods.get(i));
//            }
//        }
//
//        if (ModernizationSlot6_mods != null) {
//            for (int i = 0; i < ModernizationSlot6_mods.size(); i++) {
//                mods6.put((String) ship_modifications.get(ModernizationSlot6_mods.get(i)), gameParamsParser.getGameParamsIndexHashMap().get(ModernizationSlot6_mods.get(i)));
//                modSlot6.add((String) ModernizationSlot6_mods.get(i));
//            }
//        }
//
//        modSlot1Name.addAll(mods1.keySet());
//        modSlot2Name.addAll(mods2.keySet());
//        modSlot3Name.addAll(mods3.keySet());
//        modSlot4Name.addAll(mods4.keySet());
//        modSlot5Name.addAll(mods5.keySet());
//        modSlot6Name.addAll(mods6.keySet());
    }

    @SuppressWarnings("unchecked")
    private void setConsumablesList()
    {
        Abil0 = new ArrayList<>();
        Abil1 = new ArrayList<>();
        Abil2 = new ArrayList<>();
        Abil3 = new ArrayList<>();

//        Ability0 = new ArrayList<>();
//        Ability1 = new ArrayList<>();
//        Ability2 = new ArrayList<>();
//        Ability3 = new ArrayList<>();
        
        JSONObject ShipAbilities = (JSONObject) gpShipJSON.get("ShipAbilities");
        JSONObject AbilitySlot0 = (JSONObject) ShipAbilities.get("AbilitySlot0");
        JSONObject AbilitySlot1 = (JSONObject) ShipAbilities.get("AbilitySlot1");
        JSONObject AbilitySlot2 = (JSONObject) ShipAbilities.get("AbilitySlot2");
        JSONObject AbilitySlot3 = (JSONObject) ShipAbilities.get("AbilitySlot3");
        Abil0 = (JSONArray) AbilitySlot0.get("abils");
        Abil1 = (JSONArray) AbilitySlot1.get("abils");
        Abil2 = (JSONArray) AbilitySlot2.get("abils");
        Abil3 = (JSONArray) AbilitySlot3.get("abils");

//        for (int i = 0; i < Abil0.size(); i++) {
//            Ability0.add(Abil0.get(i).get(0).toString());
//        }
//
//        for (int i = 0; i < Abil1.size(); i++)
//        {
//            Ability1.add(Abil1.get(i).get(0).toString());
//        }
//
//        for (int i = 0; i < Abil2.size(); i++)
//        {
//            Ability2.add(Abil2.get(i).get(0).toString());
//        }
//
//        for (int i = 0; i < Abil3.size(); i++)
//        {
//            Ability3.add(Abil3.get(i).get(0).toString());
//        }
    }

    @SuppressWarnings("unchecked")
    private void setPermaflage(String name)
    {
        permaflageHashMap = new HashMap<>();
        camouflages = new ArrayList<>();
        camouflagesIdList = new ArrayList<>();

        JSONArray pf = (JSONArray) gpShipJSON.get("permoflages");
        JSONObject camouflage = apiParser.getAPI_Exterior_CamouflageJSON();
        JSONObject permoflage = apiParser.getAPI_Exterior_PermoflageJSON();

        List<JSONObject> camouList = new ArrayList<>();
        camouList.addAll(camouflage.values());
        camouList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));

        for (int i = 0; i < camouList.size(); i++)
        {
            JSONObject images = (JSONObject) camouList.get(i).get("image");
            String small = (String) images.get("small");
            small = small.replace("http://api.worldofwarships.com/static/1.8.3/wows/encyclopedia/camouflage/", "");
            small = small.replace(".png", "");
            camouflages.add(new Camouflage((String) camouList.get(i).get("name"), small));
            camouflagesIdList.add(small);
        }

        if (pf != null)
        {
            List<JSONObject> permoList = new ArrayList<>();
            permoList.addAll(permoflage.values());

            for (int i = 0; i < permoList.size(); i++)
            {
                String permoName = (String) permoList.get(i).get("name");
                if (permoName.contains(name))
                {
                    JSONObject images = (JSONObject) permoList.get(i).get("image");
                    String small = (String) images.get("small");
                    small = small.replace("http://api.worldofwarships.com/static/1.8.3/wows/encyclopedia/permoflage/", "");
                    small = small.replace(".png", "");
                    camouflages.add(new Camouflage((String) permoList.get(i).get("name"), small));
                    camouflagesIdList.add(small);
                }
            }
        }



//
//        for (int i = 0; i < camouList.size(); i++)
//        {
//            long camouID = (long) camouList.get(i).get("exterior_id");
//
//            for (int j = 0; j < gameParamsParser.getGameParamsValues().size(); j++)
//            {
//                if (gameParamsParser.getGameParamsValues().get(j).get("id").equals(camouID))
//                {
//                    permaflage.add((String) camouList.get(i).get("name"));
//                    permaflageHashMap.put((String) camouList.get(i).get("name"), gameParamsParser.getGameParamsValues().get(j));
//                    break;
//                }
//            }
//        }
//
//        if (pf != null)
//        {
//            for (int i = 0; i < pf.size(); i++)
//            {
//                JSONObject pfJSON = (JSONObject) gameParamsParser.getGameParams().get(pf.get(i));
//                long id = (long) pfJSON.get("id");
//
//                JSONObject permo = (JSONObject) permoflage.get(String.valueOf(id));
//
//                permaflage.add((String) permo.get("name"));
//                permaflageHashMap.put((String) permo.get("name"), pfJSON);
//            }
//        }
    }

//    public void setPermaflage2(String aPermaflage)
//    {
//        JSONObject pf = (JSONObject) permaflageHashMap.get(aPermaflage);
//
//        if (pf.get("afterBattleRepair") != null)
//        {
//            afterBattleRepair = (double) pf.get("afterBattleRepair");
//        }
//        else
//        {
//            afterBattleRepair = 1.00;
//        }
//
//        visibilityFactorPermaCamo = (double) pf.get("visibilityFactor");
//        visibilityFactorByPlanePermaCamo = (double) pf.get("visibilityFactorByPlane");
//
//        if (pf.get("expFactor") != null)
//        {
//            expFactorPermaCamo = (double) pf.get("expFactor") - 1;
//        }
//        else
//        {
//            expFactorPermaCamo = 0;
//        }
//    }

    @SuppressWarnings("unchecked")
    private void setFlagsList()
    {
        flagsList = new ArrayList<>();
        flagsIdList = new ArrayList<>();
        List<JSONObject> API_FlagsJSONList = new ArrayList<>();
        API_FlagsJSONList.addAll(apiParser.getAPI_Exterior_FlagsJSON().values());
        API_FlagsJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));

        for (int i = 0; i < API_FlagsJSONList.size(); i++)
        {
            JSONObject images = (JSONObject) API_FlagsJSONList.get(i).get("image");
            String small = (String) images.get("small");
            small = small.replace("http://api.worldofwarships.com/static/1.8.3/wows/encyclopedia/flags/", "");
            small = small.replace(".png", "");
            flagsList.add(new Flags((String) API_FlagsJSONList.get(i).get("name"), small));
            flagsIdList.add(small);
        }
        
//        FlagsJSONList = new ArrayList<>();
//
//        List<String> API_Flags_keySet = new ArrayList<>();
//        API_Flags_keySet.addAll(apiParser.getAPI_Exterior_FlagsJSON().keySet());
//
//        for (int i = 0; i < gameParamsParser.getGameParamsKeySet().size(); i++)
//        {
//            for (int j = 0; j < API_Flags_keySet.size(); j++)
//            {
//                String flagId = String.valueOf(gameParamsParser.getGameParamsValues().get(i).get("id"));
//                if (flagId.matches(API_Flags_keySet.get(j)))
//                {
//                    FlagsJSONList.add(GameParamsIndexHashMap.get(gameParamsParser.getGameParamsKeySet().get(i)));
//                }
//            }
//        }
    }
    
    private void setCrewSkills()
    {
        skillsList = new ArrayList<>();

        String crewCode = null;
        switch (nation)
        {
            case "usa":
                crewCode = "PAW001_DefaultCrew";
                break;
            case "japan":
                crewCode = "PJW001_DefaultCrew";
                break;
            case "ussr":
                crewCode = "PRW001_DefaultCrew";
                break;
            case "germany":
                crewCode = "PGW001_DefaultCrew";
                break;
            case "pan_asia":
                crewCode = "PZW001_DefaultCrew";
                break;
            case "poland":
                crewCode = "PWW001_DefaultCrew";
                break;
            case "uk":
                crewCode = "PBW001_DefaultCrew";
                break;
            case "france":
                crewCode = "PFW001_DefaultCrew";
            default:
                break;
        }

        List<JSONObject> API_SkillsJSONList = new ArrayList<>();
        API_SkillsJSONList.addAll(apiParser.getAPI_CommanderSkillsJSON().values());

        JSONObject crewJSON = getGameParamsNameJSON(crewCode);
        JSONObject skills = (JSONObject) crewJSON.get("Skills");

        for (int i = 0; i < API_SkillsJSONList.size(); i++)
        {
            String name = (String) API_SkillsJSONList.get(i).get("name");
            String small = (String) API_SkillsJSONList.get(i).get("icon");
            small = small.replace("http://api.worldofwarships.com/static/1.8.3/wows/encyclopedia/crew_perks/icon_perk_", "");
            small = small.replace(".png", "");

            skillsList.add(new Skills(small, name, (JSONObject) skills.get(small)));
        }
    }
}
