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
    private API_Parser apiParser;
    private GameParams_Parser gameParamsParser;

    private HashMap<String, JSONObject> GameParamsIndexHashMap;
    private HashMap<String, JSONObject> GameParamsNameHashMap;

    private JSONObject apiShipJSON;
    private JSONObject gpShipJSON;

    private int tier;
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
    private List<String> modSlot1;
    private List<String> modSlot2;
    private List<String> modSlot3;
    private List<String> modSlot4;
    private List<String> modSlot5;
    private List<String> modSlot6;
    private LinkedHashMap<String, JSONObject> mods1;
    private LinkedHashMap<String, JSONObject> mods2;
    private LinkedHashMap<String, JSONObject> mods3;
    private LinkedHashMap<String, JSONObject> mods4;
    private LinkedHashMap<String, JSONObject> mods5;
    private LinkedHashMap<String, JSONObject> mods6;
    private List<String> modSlot1Name;
    private List<String> modSlot2Name;
    private List<String> modSlot3Name;
    private List<String> modSlot4Name;
    private List<String> modSlot5Name;
    private List<String> modSlot6Name;

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

    private List<String> permaflage;
    private HashMap<String, JSONObject> permaflageHashMap;

    private List<JSONObject> FlagsJSONList;

    @Cacheable("setShipJSON")
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

        JSONObject images = (JSONObject) apiShipJSON.get("images");
        imagesMedium = (String) images.get("medium");
        
        setShipUpgradeModulesInfo();
        setUpgrades();
        setConsumablesList();
        setPermaflage();
        setFlagsList();
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
                API_ArtilleryUpgradeJSONHashMap.put(API_suiJSONIndex, API_suiJSON);
                API_ArtilleryUpgradeIndexList.add(API_suiJSONIndex);
                API_ArtilleryUpgrade.add(new Upgrade(API_suiJSONName, API_suiJSONIndex, API_suiJSON));
            }
            else if (API_suiJSON.get("type").equals("Hull"))
            {
//                API_HullUpgradeJSONList.add(API_suiJSON);
//                API_HullUpgradeNameList.add(API_suiJSONName);
                API_HullUpgradeJSONHashMap.put(API_suiJSONIndex, API_suiJSON);
                API_HullUpgradeIndexList.add(API_suiJSONIndex);
                API_HullUpgrade.add(new Upgrade(API_suiJSONName, API_suiJSONIndex, API_suiJSON));
            }
            else if (API_suiJSON.get("type").equals("Engine"))
            {
//                API_EngineUpgradeJSONList.add(API_suiJSON);
//                API_EngineUpgradeNameList.add(API_suiJSONName);
                API_EngineUpgradeJSONHashMap.put(API_suiJSONIndex, API_suiJSON);
                API_EngineUpgradeIndexList.add(API_suiJSONIndex);
                API_EngineUpgrade.add(new Upgrade(API_suiJSONName, API_suiJSONIndex, API_suiJSON));
            }
            else if (API_suiJSON.get("type").equals("Suo"))
            {
//                API_SuoUpgradeJSONList.add(API_suiJSON);
//                API_SuoUpgradeNameList.add(API_suiJSONName);
                API_SuoUpgradeJSONHashMap.put(API_suiJSONIndex, API_suiJSON);
                API_RadarUpgradeIndexList.add(API_suiJSONIndex);
                API_RadarUpgrade.add(new Upgrade(API_suiJSONName, API_suiJSONIndex, API_suiJSON));
            }
            else if (API_suiJSON.get("type").equals("Torpedoes"))
            {
//                API_TorpedoesUpgradeJSONList.add(API_suiJSON);
//                API_TorpedoesUpgradeNameList.add(API_suiJSONName);
                API_TorpedoesUpgradeJSONHashMap.put(API_suiJSONIndex, API_suiJSON);
                API_TorpedoUpgradeIndexList.add(API_suiJSONIndex);
                API_TorpedoUpgrade.add(new Upgrade(API_suiJSONName, API_suiJSONIndex, API_suiJSON));
            }
        }

        API_ArtilleryUpgrade.sort((o1, o2) -> (o1.getName()).compareTo(o2.getName()));
        API_HullUpgrade.sort((o1, o2) -> (o1.getName()).compareTo(o2.getName()));
        API_EngineUpgrade.sort((o1, o2) -> (o1.getName()).compareTo(o2.getName()));
        API_RadarUpgrade.sort((o1, o2) -> (o1.getName()).compareTo(o2.getName()));
        API_TorpedoUpgrade.sort((o1, o2) -> (o1.getName()).compareTo(o2.getName()));

        API_ArtilleryUpgradeJSON = new JSONObject(API_ArtilleryUpgradeJSONHashMap);
        API_HullUpgradeJSON = new JSONObject(API_HullUpgradeJSONHashMap);
        API_EngineUpgradeJSON = new JSONObject(API_EngineUpgradeJSONHashMap);
        API_RadarUpgradeJSON = new JSONObject(API_SuoUpgradeJSONHashMap);
        API_TorpedoUpgradeJSON = new JSONObject(API_TorpedoesUpgradeJSONHashMap);

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
        modSlot1 = new ArrayList<>();
        modSlot2 = new ArrayList<>();
        modSlot3 = new ArrayList<>();
        modSlot4 = new ArrayList<>();
        modSlot5 = new ArrayList<>();
        modSlot6 = new ArrayList<>();
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
        }

        if (GP_UpgradesJSON.get("ModernizationSlot2") != null) {
            ModernizationSlot2 = (JSONObject) GP_UpgradesJSON.get("ModernizationSlot2");
            ModernizationSlot2_mods = (JSONArray) ModernizationSlot2.get("mods");
        }

        if (GP_UpgradesJSON.get("ModernizationSlot3") != null) {
            ModernizationSlot3 = (JSONObject) GP_UpgradesJSON.get("ModernizationSlot3");
            ModernizationSlot3_mods = (JSONArray) ModernizationSlot3.get("mods");
        }

        if (GP_UpgradesJSON.get("ModernizationSlot4") != null) {
            ModernizationSlot4 = (JSONObject) GP_UpgradesJSON.get("ModernizationSlot4");
            ModernizationSlot4_mods = (JSONArray) ModernizationSlot4.get("mods");
        }

        if (GP_UpgradesJSON.get("ModernizationSlot5") != null) {
            ModernizationSlot5 = (JSONObject) GP_UpgradesJSON.get("ModernizationSlot5");
            ModernizationSlot5_mods = (JSONArray) ModernizationSlot5.get("mods");
        }

        if (GP_UpgradesJSON.get("ModernizationSlot6") != null) {
            ModernizationSlot6 = (JSONObject) GP_UpgradesJSON.get("ModernizationSlot6");
            ModernizationSlot6_mods = (JSONArray) ModernizationSlot6.get("mods");
        }

        JSONObject ship_modifications = (JSONObject) apiParser.getAPI_InfoEncyclopediaJSON().get("ship_modifications");

        if (ModernizationSlot1_mods != null) {
            for (int i = 0; i < ModernizationSlot1_mods.size(); i++) {
                mods1.put((String) ship_modifications.get(ModernizationSlot1_mods.get(i)), gameParamsParser.getGameParamsIndexHashMap().get(ModernizationSlot1_mods.get(i)));
                modSlot1.add((String) ModernizationSlot1_mods.get(i));
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
    private void setConsumablesList()
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
    private void setPermaflage()
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
    private void setFlagsList()
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
