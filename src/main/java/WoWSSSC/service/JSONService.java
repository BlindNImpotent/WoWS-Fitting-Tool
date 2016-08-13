package WoWSSSC.service;

import Parser.API_Parser;
import Parser.GameParams_Parser;
import WoWSSSC.model.Upgrade;
import lombok.Data;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.cache.annotation.Cacheable;
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
    public API_Parser apiParser;
    public GameParams_Parser gameParamsParser;

    public HashMap<String, JSONObject> GameParamsIndexHashMap;
    public HashMap<String, JSONObject> GameParamsNameHashMap;

    public JSONObject apiShipJSON;
    public JSONObject gpShipJSON;

    public long tier;
    public String nation;
    public String shipType;

    public String ship_id_str;

    public JSONObject modules_treeJSON;
    public List<String> modules_treeList;
    public List<JSONObject> default_loadouts;
    public List<JSONObject> upgradeModules;
    
    public List<Upgrade> API_ArtilleryUpgrade;
    public List<Upgrade> API_HullUpgrade;
    public List<Upgrade> API_EngineUpgrade;
    public List<Upgrade> API_RadarUpgrade;
    public List<Upgrade> API_TorpedoUpgrade;

    public List<String> API_ArtilleryUpgradeIndexList;
    public List<String> API_HullUpgradeIndexList;
    public List<String> API_EngineUpgradeIndexList;
    public List<String> API_RadarUpgradeIndexList;
    public List<String> API_TorpedoUpgradeIndexList;

//    public List<JSONObject> API_ArtilleryUpgradeJSONList;
//    public List<String> API_ArtilleryUpgradeNameList;
    public HashMap<String, JSONObject> API_ArtilleryUpgradeJSONHashMap;
//
//    public List<JSONObject> API_HullUpgradeJSONList;
//    public List<String> API_HullUpgradeNameList;
    public HashMap<String, JSONObject> API_HullUpgradeJSONHashMap;
//
//    public List<JSONObject> API_EngineUpgradeJSONList;
//    public List<String> API_EngineUpgradeNameList;
    public HashMap<String, JSONObject> API_EngineUpgradeJSONHashMap;
//
//    public List<JSONObject> API_SuoUpgradeJSONList;
//    public List<String> API_SuoUpgradeNameList;
    public HashMap<String, JSONObject> API_SuoUpgradeJSONHashMap;
//
//    public List<JSONObject> API_TorpedoesUpgradeJSONList;
//    public List<String> API_TorpedoesUpgradeNameList;
    public HashMap<String, JSONObject> API_TorpedoesUpgradeJSONHashMap;

    public JSONObject API_ArtilleryUpgradeJSON;
    public JSONObject API_HullUpgradeJSON;
    public JSONObject API_EngineUpgradeJSON;
    public JSONObject API_RadarUpgradeJSON;
    public JSONObject API_TorpedoUpgradeJSON;

    public String imagesMedium;

    public List<Long> API_UpgradesIDList;
    public JSONObject GP_UpgradesJSON;
    public List<String> UpgradesNameList;
    public JSONObject ModernizationSlot1;
    public JSONObject ModernizationSlot2;
    public JSONObject ModernizationSlot3;
    public JSONObject ModernizationSlot4;
    public JSONObject ModernizationSlot5;
    public JSONObject ModernizationSlot6;
    public JSONArray ModernizationSlot1_mods;
    public JSONArray ModernizationSlot2_mods;
    public JSONArray ModernizationSlot3_mods;
    public JSONArray ModernizationSlot4_mods;
    public JSONArray ModernizationSlot5_mods;
    public JSONArray ModernizationSlot6_mods;
    public List<String> modSlot1;
    public List<String> modSlot2;
    public List<String> modSlot3;
    public List<String> modSlot4;
    public List<String> modSlot5;
    public List<String> modSlot6;
    public List<String> modSlot1Images;
    public List<String> modSlot2Images;
    public List<String> modSlot3Images;
    public List<String> modSlot4Images;
    public List<String> modSlot5Images;
    public List<String> modSlot6Images;
    public LinkedHashMap<String, JSONObject> mods1;
    public LinkedHashMap<String, JSONObject> mods2;
    public LinkedHashMap<String, JSONObject> mods3;
    public LinkedHashMap<String, JSONObject> mods4;
    public LinkedHashMap<String, JSONObject> mods5;
    public LinkedHashMap<String, JSONObject> mods6;
    public List<String> modSlot1Name;
    public List<String> modSlot2Name;
    public List<String> modSlot3Name;
    public List<String> modSlot4Name;
    public List<String> modSlot5Name;
    public List<String> modSlot6Name;

    public List<JSONArray> Abil0;
    public List<JSONArray> Abil1;
    public List<JSONArray> Abil2;
    public List<JSONArray> Abil3;

    public List<String> Ability0;
    public List<String> Ability1;
    public List<String> Ability2;
    public List<String> Ability3;

    public double afterBattleRepair;
    public double visibilityFactorPermaCamo;
    public double visibilityFactorByPlanePermaCamo;
    public double expFactorPermaCamo;

    public List<String> permaflage;
    public HashMap<String, JSONObject> permaflageHashMap;

    public List<JSONObject> FlagsJSONList;
    
    public HashMap<String, JSONObject> API_FlightControlUpgradeJSONHashMap; 
    public List<String> API_FlightControlUpgradeIndexList;
    public List<Upgrade> API_FlightControlUpgrade;
    public JSONObject API_FlightControlUpgradeJSON;

    public HashMap<String, JSONObject> API_FighterUpgradeJSONHashMap;
    public List<String> API_FighterUpgradeIndexList;
    public List<Upgrade> API_FighterUpgrade;
    public JSONObject API_FighterUpgradeJSON;

    public HashMap<String, JSONObject> API_TorpedoBomberUpgradeJSONHashMap;
    public List<String> API_TorpedoBomberUpgradeIndexList;
    public List<Upgrade> API_TorpedoBomberUpgrade;
    public JSONObject API_TorpedoBomberUpgradeJSON;

    public HashMap<String, JSONObject> API_DiveBomberUpgradeJSONHashMap;
    public List<String> API_DiveBomberUpgradeIndexList;
    public List<Upgrade> API_DiveBomberUpgrade;
    public JSONObject API_DiveBomberUpgradeJSON;
    

//    @Cacheable("setShipJSON")
    public void setShipJSON(String name) throws IOException, ParseException
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


    }

    public JSONObject getGameParamsIndexJSON(String index)
    {
        return GameParamsIndexHashMap.get(index);
    }

    public JSONObject getGameParamsNameJSON(String name)
    {
        return GameParamsNameHashMap.get(name);
    }

    public void setShipUpgradeModulesInfo()
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

        API_ArtilleryUpgrade = new ArrayList<>();
        API_HullUpgrade = new ArrayList<>();
        API_EngineUpgrade = new ArrayList<>();
        API_RadarUpgrade = new ArrayList<>();
        API_TorpedoUpgrade = new ArrayList<>();

        API_ArtilleryUpgradeIndexList = new ArrayList<>();
        API_HullUpgradeIndexList = new ArrayList<>();
        API_EngineUpgradeIndexList = new ArrayList<>();
        API_RadarUpgradeIndexList = new ArrayList<>();
        API_TorpedoUpgradeIndexList = new ArrayList<>();

        API_FlightControlUpgradeJSONHashMap = new HashMap<>();
        API_FlightControlUpgradeIndexList = new ArrayList<>();
        API_FlightControlUpgrade = new ArrayList<>();

        API_FighterUpgradeJSONHashMap = new HashMap<>();
        API_FighterUpgradeIndexList = new ArrayList<>();
        API_FighterUpgrade = new ArrayList<>();

        API_TorpedoBomberUpgradeJSONHashMap = new HashMap<>();
        API_TorpedoBomberUpgradeIndexList = new ArrayList<>();
        API_TorpedoBomberUpgrade = new ArrayList<>();

        API_DiveBomberUpgradeJSONHashMap = new HashMap<>();
        API_DiveBomberUpgradeIndexList = new ArrayList<>();
        API_DiveBomberUpgrade = new ArrayList<>();
        
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
                API_ArtilleryUpgrade.add(new Upgrade(API_suiJSONName, API_suiJSONIndex, API_suiJSONId, API_suiJSONIsDefault, API_suiJSON));
            }
            else if (API_suiJSON.get("type").equals("Hull"))
            {
//                API_HullUpgradeJSONList.add(API_suiJSON);
//                API_HullUpgradeNameList.add(API_suiJSONName);
                API_HullUpgradeJSONHashMap.put(API_suiJSONIndex, API_suiJSON);
                API_HullUpgradeIndexList.add(API_suiJSONIndex);
                API_HullUpgrade.add(new Upgrade(API_suiJSONName, API_suiJSONIndex, API_suiJSONId, API_suiJSONIsDefault, API_suiJSON));
            }
            else if (API_suiJSON.get("type").equals("Engine"))
            {
//                API_EngineUpgradeJSONList.add(API_suiJSON);
//                API_EngineUpgradeNameList.add(API_suiJSONName);
                API_EngineUpgradeJSONHashMap.put(API_suiJSONIndex, API_suiJSON);
                API_EngineUpgradeIndexList.add(API_suiJSONIndex);
                API_EngineUpgrade.add(new Upgrade(API_suiJSONName, API_suiJSONIndex, API_suiJSONId, API_suiJSONIsDefault, API_suiJSON));
            }
            else if (API_suiJSON.get("type").equals("Suo"))
            {
//                API_SuoUpgradeJSONList.add(API_suiJSON);
//                API_SuoUpgradeNameList.add(API_suiJSONName);
                API_SuoUpgradeJSONHashMap.put(API_suiJSONIndex, API_suiJSON);
                API_RadarUpgradeIndexList.add(API_suiJSONIndex);
                API_RadarUpgrade.add(new Upgrade(API_suiJSONName, API_suiJSONIndex, API_suiJSONId, API_suiJSONIsDefault, API_suiJSON));
            }
            else if (API_suiJSON.get("type").equals("Torpedoes"))
            {
//                API_TorpedoesUpgradeJSONList.add(API_suiJSON);
//                API_TorpedoesUpgradeNameList.add(API_suiJSONName);
                API_TorpedoesUpgradeJSONHashMap.put(API_suiJSONIndex, API_suiJSON);
                API_TorpedoUpgradeIndexList.add(API_suiJSONIndex);
                API_TorpedoUpgrade.add(new Upgrade(API_suiJSONName, API_suiJSONIndex, API_suiJSONId, API_suiJSONIsDefault, API_suiJSON));
            }
            else if (API_suiJSON.get("type").equals("FlightControl"))
            {
                API_FlightControlUpgradeJSONHashMap.put(API_suiJSONIndex, API_suiJSON);
                API_FlightControlUpgradeIndexList.add(API_suiJSONIndex);
                API_FlightControlUpgrade.add(new Upgrade(API_suiJSONName, API_suiJSONIndex, API_suiJSONId, API_suiJSONIsDefault, API_suiJSON));
            }
            else if (API_suiJSON.get("type").equals("Fighter"))
            {
                API_FighterUpgradeJSONHashMap.put(API_suiJSONIndex, API_suiJSON);
                API_FighterUpgradeIndexList.add(API_suiJSONIndex);
                API_FighterUpgrade.add(new Upgrade(API_suiJSONName, API_suiJSONIndex, API_suiJSONId, API_suiJSONIsDefault, API_suiJSON));
            }
            else if (API_suiJSON.get("type").equals("TorpedoBomber"))
            {
                API_TorpedoBomberUpgradeJSONHashMap.put(API_suiJSONIndex, API_suiJSON);
                API_TorpedoBomberUpgradeIndexList.add(API_suiJSONIndex);
                API_TorpedoBomberUpgrade.add(new Upgrade(API_suiJSONName, API_suiJSONIndex, API_suiJSONId, API_suiJSONIsDefault, API_suiJSON));
            }
            else if (API_suiJSON.get("type").equals("DiveBomber"))
            {
                API_DiveBomberUpgradeJSONHashMap.put(API_suiJSONIndex, API_suiJSON);
                API_DiveBomberUpgradeIndexList.add(API_suiJSONIndex);
                API_DiveBomberUpgrade.add(new Upgrade(API_suiJSONName, API_suiJSONIndex, API_suiJSONId, API_suiJSONIsDefault, API_suiJSON));
            }

        }

        API_ArtilleryUpgrade.sort((o1, o2) -> (o1.getName()).compareTo(o2.getName()));
        API_HullUpgrade.sort((o1, o2) -> (o1.getName()).compareTo(o2.getName()));
        API_EngineUpgrade.sort((o1, o2) -> (o1.getName()).compareTo(o2.getName()));
        API_RadarUpgrade.sort((o1, o2) -> (o1.getName()).compareTo(o2.getName()));
        API_TorpedoUpgrade.sort((o1, o2) -> (o1.getName()).compareTo(o2.getName()));
        API_FlightControlUpgrade.sort((o1, o2) -> (o1.getName()).compareTo(o2.getName()));
        API_FighterUpgrade.sort((o1, o2) -> (o1.getName()).compareTo(o2.getName()));
        API_TorpedoBomberUpgrade.sort((o1, o2) -> (o1.getName()).compareTo(o2.getName()));
        API_DiveBomberUpgrade.sort((o1, o2) -> (o1.getName()).compareTo(o2.getName()));

        API_ArtilleryUpgrade.sort((o1, o2) -> (o2.isDefault() ? 1 : 0) - (o1.isDefault() ? 1 : 0));
        API_HullUpgrade.sort((o1, o2) -> (o2.isDefault() ? 1 : 0) - (o1.isDefault() ? 1 : 0));
        API_EngineUpgrade.sort((o1, o2) -> (o2.isDefault() ? 1 : 0) - (o1.isDefault() ? 1 : 0));
        API_RadarUpgrade.sort((o1, o2) -> (o2.isDefault() ? 1 : 0) - (o1.isDefault() ? 1 : 0));
        API_TorpedoUpgrade.sort((o1, o2) -> (o2.isDefault() ? 1 : 0) - (o1.isDefault() ? 1 : 0));
        API_FlightControlUpgrade.sort((o1, o2) -> (o2.isDefault() ? 1 : 0) - (o1.isDefault() ? 1 : 0));
        API_FighterUpgrade.sort((o1, o2) -> (o2.isDefault() ? 1 : 0) - (o1.isDefault() ? 1 : 0));
        API_TorpedoBomberUpgrade.sort((o1, o2) -> (o2.isDefault() ? 1 : 0) - (o1.isDefault() ? 1 : 0));
        API_DiveBomberUpgrade.sort((o1, o2) -> (o2.isDefault() ? 1 : 0) - (o1.isDefault() ? 1 : 0));
        
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
    public void setUpgrades()
    {
        API_UpgradesIDList = new ArrayList<>();        
        UpgradesNameList = new ArrayList<>();
        ModernizationSlot1_mods = null;
        ModernizationSlot2_mods = null;
        ModernizationSlot3_mods = null;
        ModernizationSlot4_mods = null;
        ModernizationSlot5_mods = null;
        ModernizationSlot6_mods = null;

        modSlot1 = new ArrayList<>();
        modSlot2 = new ArrayList<>();
        modSlot3 = new ArrayList<>();
        modSlot4 = new ArrayList<>();
        modSlot5 = new ArrayList<>();
        modSlot6 = new ArrayList<>();
        modSlot1Images = new ArrayList<>();
        modSlot2Images = new ArrayList<>();
        modSlot3Images = new ArrayList<>();
        modSlot4Images = new ArrayList<>();
        modSlot5Images = new ArrayList<>();
        modSlot6Images = new ArrayList<>();
        mods1 = new LinkedHashMap<>();
        mods2 = new LinkedHashMap<>();
        mods3 = new LinkedHashMap<>();
        mods4 = new LinkedHashMap<>();
        mods5 = new LinkedHashMap<>();
        mods6 = new LinkedHashMap<>();
        modSlot1Name = new ArrayList<>();
        modSlot2Name = new ArrayList<>();
        modSlot3Name = new ArrayList<>();
        modSlot4Name = new ArrayList<>();
        modSlot5Name = new ArrayList<>();
        modSlot6Name = new ArrayList<>();

        
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

        if (ModernizationSlot1_mods != null) {
            for (int i = 0; i < ModernizationSlot1_mods.size(); i++) {
                mods1.put((String) ship_modifications.get(ModernizationSlot1_mods.get(i)), gameParamsParser.getGameParamsIndexHashMap().get(ModernizationSlot1_mods.get(i)));
                modSlot1.add((String) ModernizationSlot1_mods.get(i));
                modSlot1Images.add(ModernizationSlot1_mods.get(i) + ".png");
            }
        }

        if (ModernizationSlot2_mods != null) {
            for (int i = 0; i < ModernizationSlot2_mods.size(); i++) {
                mods2.put((String) ship_modifications.get(ModernizationSlot2_mods.get(i)), gameParamsParser.getGameParamsIndexHashMap().get(ModernizationSlot2_mods.get(i)));
                modSlot2.add((String) ModernizationSlot2_mods.get(i));
            }
        }

        if (ModernizationSlot3_mods != null) {
            for (int i = 0; i < ModernizationSlot3_mods.size(); i++) {
                mods3.put((String) ship_modifications.get(ModernizationSlot3_mods.get(i)), gameParamsParser.getGameParamsIndexHashMap().get(ModernizationSlot3_mods.get(i)));
                modSlot3.add((String) ModernizationSlot3_mods.get(i));
            }
        }

        if (ModernizationSlot4_mods != null) {
            for (int i = 0; i < ModernizationSlot4_mods.size(); i++) {
                mods4.put((String) ship_modifications.get(ModernizationSlot4_mods.get(i)), gameParamsParser.getGameParamsIndexHashMap().get(ModernizationSlot4_mods.get(i)));
                modSlot4.add((String) ModernizationSlot4_mods.get(i));
            }
        }

        if (ModernizationSlot5_mods != null) {
            for (int i = 0; i < ModernizationSlot5_mods.size(); i++) {
                mods5.put((String) ship_modifications.get(ModernizationSlot5_mods.get(i)), gameParamsParser.getGameParamsIndexHashMap().get(ModernizationSlot5_mods.get(i)));
                modSlot5.add((String) ModernizationSlot5_mods.get(i));
            }
        }

        if (ModernizationSlot6_mods != null) {
            for (int i = 0; i < ModernizationSlot6_mods.size(); i++) {
                mods6.put((String) ship_modifications.get(ModernizationSlot6_mods.get(i)), gameParamsParser.getGameParamsIndexHashMap().get(ModernizationSlot6_mods.get(i)));
                modSlot6.add((String) ModernizationSlot6_mods.get(i));
            }
        }

        modSlot1Name.addAll(mods1.keySet());
        modSlot2Name.addAll(mods2.keySet());
        modSlot3Name.addAll(mods3.keySet());
        modSlot4Name.addAll(mods4.keySet());
        modSlot5Name.addAll(mods5.keySet());
        modSlot6Name.addAll(mods6.keySet());
    }

    @SuppressWarnings("unchecked")
    public void setConsumablesList()
    {
        Abil0 = new ArrayList<>();
        Abil1 = new ArrayList<>();
        Abil2 = new ArrayList<>();
        Abil3 = new ArrayList<>();

        Ability0 = new ArrayList<>();
        Ability1 = new ArrayList<>();
        Ability2 = new ArrayList<>();
        Ability3 = new ArrayList<>();
        
        JSONObject ShipAbilities = (JSONObject) gpShipJSON.get("ShipAbilities");
        JSONObject AbilitySlot0 = (JSONObject) ShipAbilities.get("AbilitySlot0");
        JSONObject AbilitySlot1 = (JSONObject) ShipAbilities.get("AbilitySlot1");
        JSONObject AbilitySlot2 = (JSONObject) ShipAbilities.get("AbilitySlot2");
        JSONObject AbilitySlot3 = (JSONObject) ShipAbilities.get("AbilitySlot3");
        Abil0 = (JSONArray) AbilitySlot0.get("abils");
        Abil1 = (JSONArray) AbilitySlot1.get("abils");
        Abil2 = (JSONArray) AbilitySlot2.get("abils");
        Abil3 = (JSONArray) AbilitySlot3.get("abils");

        for (int i = 0; i < Abil0.size(); i++) {
            Ability0.add(Abil0.get(i).get(0).toString());
        }

        for (int i = 0; i < Abil1.size(); i++)
        {
            Ability1.add(Abil1.get(i).get(0).toString());
        }

        for (int i = 0; i < Abil2.size(); i++)
        {
            Ability2.add(Abil2.get(i).get(0).toString());
        }

        for (int i = 0; i < Abil3.size(); i++)
        {
            Ability3.add(Abil3.get(i).get(0).toString());
        }
    }

    @SuppressWarnings("unchecked")
    public void setPermaflage()
    {
        permaflage = new ArrayList<>();
        permaflageHashMap = new HashMap<>();

        JSONArray pf = (JSONArray) gpShipJSON.get("permoflages");
        JSONObject permoflage = apiParser.getAPI_Exterior_PermoflageJSON();
        JSONObject camouflage = apiParser.getAPI_Exterior_CamouflageJSON();

        List<JSONObject> camouList = new ArrayList<>();
        camouList.addAll(camouflage.values());
        camouList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));

        for (int i = 0; i < camouList.size(); i++)
        {
            long camouID = (long) camouList.get(i).get("exterior_id");

            for (int j = 0; j < gameParamsParser.getGameParamsValues().size(); j++)
            {
                if (gameParamsParser.getGameParamsValues().get(j).get("id").equals(camouID))
                {
                    permaflage.add((String) camouList.get(i).get("name"));
                    permaflageHashMap.put((String) camouList.get(i).get("name"), gameParamsParser.getGameParamsValues().get(j));
                    break;
                }
            }
        }

        if (pf != null)
        {
            for (int i = 0; i < pf.size(); i++)
            {
                JSONObject pfJSON = (JSONObject) gameParamsParser.getGameParams().get(pf.get(i));
                long id = (long) pfJSON.get("id");

                JSONObject permo = (JSONObject) permoflage.get(String.valueOf(id));

                permaflage.add((String) permo.get("name"));
                permaflageHashMap.put((String) permo.get("name"), pfJSON);
            }
        }
    }

    public void setPermaflage2(String aPermaflage)
    {
        JSONObject pf = (JSONObject) permaflageHashMap.get(aPermaflage);

        if (pf.get("afterBattleRepair") != null)
        {
            afterBattleRepair = (double) pf.get("afterBattleRepair");
        }
        else
        {
            afterBattleRepair = 1.00;
        }

        visibilityFactorPermaCamo = (double) pf.get("visibilityFactor");
        visibilityFactorByPlanePermaCamo = (double) pf.get("visibilityFactorByPlane");

        if (pf.get("expFactor") != null)
        {
            expFactorPermaCamo = (double) pf.get("expFactor") - 1;
        }
        else
        {
            expFactorPermaCamo = 0;
        }
    }

    @SuppressWarnings("unchecked")
    public void setFlagsList()
    {
        FlagsJSONList = new ArrayList<>();

        List<String> API_Flags_keySet = new ArrayList<>();
        API_Flags_keySet.addAll(apiParser.getAPI_Exterior_FlagsJSON().keySet());

        for (int i = 0; i < gameParamsParser.getGameParamsKeySet().size(); i++)
        {
            for (int j = 0; j < API_Flags_keySet.size(); j++)
            {
                String flagId = String.valueOf(gameParamsParser.getGameParamsValues().get(i).get("id"));
                if (flagId.matches(API_Flags_keySet.get(j)))
                {
                    FlagsJSONList.add(GameParamsIndexHashMap.get(gameParamsParser.getGameParamsKeySet().get(i)));
                }
            }
        }
    }
}
