package WoWSSSC.service;

import Parser.API_Parser;
import Parser.GameParams_Parser;
import WoWSSSC.model.Upgrade;
import lombok.Data;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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

    private String nation;

    private String ship_id_str;

    private JSONObject modules_treeJSON;
    private List<String> modules_treeList;
    private List<JSONObject> default_loadouts;
    private List<JSONObject> upgradeModules;
    
    private List<Upgrade> API_ArtilleryUpgrade;
    private List<Upgrade> API_HullUpgrade;
    private List<Upgrade> API_EngineUpgrade;
    private List<Upgrade> API_RadarUpgrade;
    private List<Upgrade> API_TorpedoUpgrade;

//    private List<JSONObject> API_ArtilleryUpgradeJSONList;
//    private List<String> API_ArtilleryUpgradeNameList;
//    private HashMap<String, JSONObject> API_ArtilleryUpgradeJSONHashMap;
//
//    private List<JSONObject> API_HullUpgradeJSONList;
//    private List<String> API_HullUpgradeNameList;
//    private HashMap<String, JSONObject> API_HullUpgradeJSONHashMap;
//
//    private List<JSONObject> API_EngineUpgradeJSONList;
//    private List<String> API_EngineUpgradeNameList;
//    private HashMap<String, JSONObject> API_EngineUpgradeJSONHashMap;
//
//    private List<JSONObject> API_SuoUpgradeJSONList;
//    private List<String> API_SuoUpgradeNameList;
//    private HashMap<String, JSONObject> API_SuoUpgradeJSONHashMap;
//
//    private List<JSONObject> API_TorpedoesUpgradeJSONList;
//    private List<String> API_TorpedoesUpgradeNameList;
//    private HashMap<String, JSONObject> API_TorpedoesUpgradeJSONHashMap;

//    private JSONObject API_ArtilleryUpgradeJSON;
//    private JSONObject API_HullUpgradeJSON;
//    private JSONObject API_EngineUpgradeJSON;
//    private JSONObject API_RadarUpgradeJSON;
//    private JSONObject API_TorpedoUpgradeJSON;

    public void setShipJSON(String name) throws IOException, ParseException
    {
        apiParser = new API_Parser();
        gameParamsParser = new GameParams_Parser();

        GameParamsIndexHashMap = gameParamsParser.getGameParamsIndexHashMap();
        GameParamsNameHashMap = gameParamsParser.getGameParamsNameHashMap();

        apiShipJSON = apiParser.getAPIShipsHashMap().get(name);
        gpShipJSON = gameParamsParser.getGameParamsIndexHashMap().get(apiShipJSON.get("ship_id_str"));

        nation = (String) apiShipJSON.get("nation");

        ship_id_str = (String) apiShipJSON.get("ship_id_str");

        modules_treeList = new ArrayList<>();
        default_loadouts = new ArrayList<>();
        upgradeModules = new ArrayList<>();

//        API_ArtilleryUpgradeJSONList = new ArrayList<>();
//        API_ArtilleryUpgradeNameList = new ArrayList<>();
//        API_ArtilleryUpgradeJSONHashMap = new HashMap<>();
//        API_HullUpgradeJSONList = new ArrayList<>();
//        API_HullUpgradeNameList = new ArrayList<>();
//        API_HullUpgradeJSONHashMap = new HashMap<>();
//        API_EngineUpgradeJSONList = new ArrayList<>();
//        API_EngineUpgradeNameList = new ArrayList<>();
//        API_EngineUpgradeJSONHashMap = new HashMap<>();
//        API_SuoUpgradeJSONList = new ArrayList<>();
//        API_SuoUpgradeNameList = new ArrayList<>();
//        API_SuoUpgradeJSONHashMap = new HashMap<>();
//        API_TorpedoesUpgradeJSONList = new ArrayList<>();
//        API_TorpedoesUpgradeNameList = new ArrayList<>();
//        API_TorpedoesUpgradeJSONHashMap = new HashMap<>();

        API_ArtilleryUpgrade = new ArrayList<>();
        API_HullUpgrade = new ArrayList<>();
        API_EngineUpgrade = new ArrayList<>();
        API_RadarUpgrade = new ArrayList<>();
        API_TorpedoUpgrade = new ArrayList<>();
        
        setShipUpgradeModulesInfo();
    }

    public JSONObject getGameParamsIndexJSON(String index)
    {
        return GameParamsIndexHashMap.get(index);
    }

    public JSONObject getGameParamsNameJSON(String name)
    {
        return GameParamsNameHashMap.get(name);
    }

    private void setShipUpgradeModulesInfo()
    {
        modules_treeJSON = (JSONObject) apiShipJSON.get("modules_tree");

        modules_treeList.clear();
        modules_treeList.addAll(modules_treeJSON.keySet());

        JSONObject API_suiJSON;

        for (int i = 0; i < modules_treeList.size(); i++)
        {
            API_suiJSON = (JSONObject) modules_treeJSON.get(modules_treeList.get(i));

            String API_suiJSONName = (String) API_suiJSON.get("name");
            String API_suiJSONIndex = (String) API_suiJSON.get("module_id_str");

            if (API_suiJSON.get("type").equals("Artillery"))
            {
//                API_ArtilleryUpgradeJSONList.add(API_suiJSON);
//                API_ArtilleryUpgradeNameList.add(API_suiJSONName);
//                API_ArtilleryUpgradeJSONHashMap.put(API_suiJSONName, API_suiJSON);
                API_ArtilleryUpgrade.add(new Upgrade(API_suiJSONName, API_suiJSONIndex, API_suiJSON));
            }
            else if (API_suiJSON.get("type").equals("Hull"))
            {
//                API_HullUpgradeJSONList.add(API_suiJSON);
//                API_HullUpgradeNameList.add(API_suiJSONName);
//                API_HullUpgradeJSONHashMap.put(API_suiJSONName, API_suiJSON);
                API_HullUpgrade.add(new Upgrade(API_suiJSONName, API_suiJSONIndex, API_suiJSON));
            }
            else if (API_suiJSON.get("type").equals("Engine"))
            {
//                API_EngineUpgradeJSONList.add(API_suiJSON);
//                API_EngineUpgradeNameList.add(API_suiJSONName);
//                API_EngineUpgradeJSONHashMap.put(API_suiJSONName, API_suiJSON);
                API_EngineUpgrade.add(new Upgrade(API_suiJSONName, API_suiJSONIndex, API_suiJSON));
            }
            else if (API_suiJSON.get("type").equals("Suo"))
            {
//                API_SuoUpgradeJSONList.add(API_suiJSON);
//                API_SuoUpgradeNameList.add(API_suiJSONName);
//                API_SuoUpgradeJSONHashMap.put(API_suiJSONName, API_suiJSON);
                API_RadarUpgrade.add(new Upgrade(API_suiJSONName, API_suiJSONIndex, API_suiJSON));
            }
            else if (API_suiJSON.get("type").equals("Torpedoes"))
            {
//                API_TorpedoesUpgradeJSONList.add(API_suiJSON);
//                API_TorpedoesUpgradeNameList.add(API_suiJSONName);
//                API_TorpedoesUpgradeJSONHashMap.put(API_suiJSONName, API_suiJSON);
                API_TorpedoUpgrade.add(new Upgrade(API_suiJSONName, API_suiJSONIndex, API_suiJSON));
            }
        }

        API_ArtilleryUpgrade.sort((o1, o2) -> (o1.getName()).compareTo(o2.getName()));

        API_HullUpgrade.sort((o1, o2) -> (o1.getName()).compareTo(o2.getName()));

        API_EngineUpgrade.sort((o1, o2) -> (o1.getName()).compareTo(o2.getName()));

        API_RadarUpgrade.sort((o1, o2) -> (o1.getName()).compareTo(o2.getName()));

        API_TorpedoUpgrade.sort((o1, o2) -> (o1.getName()).compareTo(o2.getName()));

//        API_ArtilleryUpgradeJSON = new JSONObject(API_ArtilleryUpgradeJSONHashMap);
//        API_HullUpgradeJSON = new JSONObject(API_HullUpgradeJSONHashMap);
//        API_EngineUpgradeJSON = new JSONObject(API_EngineUpgradeJSONHashMap);
//        API_RadarUpgradeJSON = new JSONObject(API_SuoUpgradeJSONHashMap);
//        API_TorpedoUpgradeJSON = new JSONObject(API_TorpedoesUpgradeJSONHashMap);

//        Collections.sort(API_ArtilleryUpgradeNameList);
//        Collections.sort(API_HullUpgradeNameList);
//        Collections.sort(API_EngineUpgradeNameList);
//        Collections.sort(API_SuoUpgradeNameList);
//        Collections.sort(API_TorpedoesUpgradeNameList);
    }
}
