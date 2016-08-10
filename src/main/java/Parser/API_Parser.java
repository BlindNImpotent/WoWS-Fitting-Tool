package Parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * 
 * @author Aesis (BlindNImpotent)
 *
 */
@Data
public class API_Parser
{
	private JSONParser JSONParser = new JSONParser();

	private Resource API_CommanderSkills = new ClassPathResource("static/json/API_JSON/CommanderSkills.json");
	private Resource API_Exterior_Camouflage = new ClassPathResource("static/json/API_JSON/Exterior_Camouflage.json");
	private Resource API_Exterior_Permoflage = new ClassPathResource("static/json/API_JSON/Exterior_Permoflage.json");
	private Resource API_Exterior_Flags = new ClassPathResource("static/json/API_JSON/Exterior_Flags.json");
	private Resource API_InfoEncyclopedia = new ClassPathResource("static/json/API_JSON/InfoEncyclopedia.json");
	private Resource API_Modules_Artillery = new ClassPathResource("static/json/API_JSON/Modules_Artillery.json");
	private Resource API_Modules_Engine = new ClassPathResource("static/json/API_JSON/Modules_Engine.json");
	private Resource API_Modules_Hull = new ClassPathResource("static/json/API_JSON/Modules_Hull.json");
	private Resource API_Modules_Radar = new ClassPathResource("static/json/API_JSON/Modules_Radar.json");
	private Resource API_Modules_Torpedoes = new ClassPathResource("static/json/API_JSON/Modules_Torpedoes.json");
	private Resource API_Upgrades = new ClassPathResource("static/json/API_JSON/Upgrades.json");
	private Resource API_Warships = new ClassPathResource("static/json/API_JSON/Warships.json");
	
	private JSONObject API_CommanderSkillsJSON;
	private JSONObject API_Exterior_CamouflageJSON;
	private JSONObject API_Exterior_PermoflageJSON;
	private JSONObject API_Exterior_FlagsJSON;
	private JSONObject API_InfoEncyclopediaJSON;
	private JSONObject API_Modules_ArtilleryJSON;
	private JSONObject API_Modules_EngineJSON;
	private JSONObject API_Modules_HullJSON;
	private JSONObject API_Modules_RadarJSON;
	private JSONObject API_Modules_TorpedoesJSON;
	private JSONObject API_UpgradesJSON;
	private JSONObject API_WarshipsJSON;
		
	private List<JSONObject> APIShipsJSONList = new ArrayList<>();

	private HashMap<String, JSONObject> APIShipsHashMap = new HashMap<>();

	private JSONObject shipJSON;
	
	private String ship_id_str;

	private String shipSmallImage;
	private String shipContour;

	private JSONObject modules_treeJSON;
	private List<String> modules_treeList = new ArrayList<>();
	private List<JSONObject> API_ArtilleryUpgradeJSONList = new ArrayList<>();
	private List<String> API_ArtilleryUpgradeNameList = new ArrayList<>();
	private HashMap<String, JSONObject> API_ArtilleryUpgradeJSONHashMap = new HashMap<>();

	private List<JSONObject> API_HullUpgradeJSONList = new ArrayList<>();
	private List<String> API_HullUpgradeNameList = new ArrayList<>();
	private HashMap<String, JSONObject> API_HullUpgradeJSONHashMap = new HashMap<>();

	private List<JSONObject> API_EngineUpgradeJSONList = new ArrayList<>();
	private List<String> API_EngineUpgradeNameList = new ArrayList<>();
	private HashMap<String, JSONObject> API_EngineUpgradeJSONHashMap = new HashMap<>();

	private List<JSONObject> API_SuoUpgradeJSONList = new ArrayList<>();
	private List<String> API_SuoUpgradeNameList = new ArrayList<>();
	private HashMap<String, JSONObject> API_SuoUpgradeJSONHashMap = new HashMap<>();

	private List<JSONObject> API_TorpedoesUpgradeJSONList = new ArrayList<>();
	private List<String> API_TorpedoesUpgradeNameList = new ArrayList<>();
	private HashMap<String, JSONObject> API_TorpedoesUpgradeJSONHashMap = new HashMap<>();

	private JSONObject API_ArtilleryUpgradeJSON;
	private JSONObject API_HullUpgradeJSON;
	private JSONObject API_EngineUpgradeJSON;
	private JSONObject API_RadarUpgradeJSON;
	private JSONObject API_TorpedoUpgradeJSON;

	public API_Parser() throws IOException, ParseException
	{
		setup();
	}

	@SuppressWarnings("unchecked")
	private void setup() throws IOException, ParseException
	{
		BufferedReader reader ;

		reader = new BufferedReader(new InputStreamReader(API_CommanderSkills.getInputStream(),"UTF8"));
		API_CommanderSkillsJSON = (JSONObject) JSONParser.parse(reader);
		API_CommanderSkillsJSON = (JSONObject) API_CommanderSkillsJSON.get("data");
		
		reader = new BufferedReader(new InputStreamReader(API_Exterior_Camouflage.getInputStream(),"UTF8"));
		API_Exterior_CamouflageJSON = (JSONObject) JSONParser.parse(reader);
		API_Exterior_CamouflageJSON = (JSONObject) API_Exterior_CamouflageJSON.get("data");
		
		reader = new BufferedReader(new InputStreamReader(API_Exterior_Permoflage.getInputStream(),"UTF8"));
		API_Exterior_PermoflageJSON = (JSONObject) JSONParser.parse(reader);
		API_Exterior_PermoflageJSON = (JSONObject) API_Exterior_PermoflageJSON.get("data");
		
		reader = new BufferedReader(new InputStreamReader(API_Exterior_Flags.getInputStream(),"UTF8"));
		API_Exterior_FlagsJSON = (JSONObject) JSONParser.parse(reader);
		API_Exterior_FlagsJSON = (JSONObject) API_Exterior_FlagsJSON.get("data");
		
		reader = new BufferedReader(new InputStreamReader(API_InfoEncyclopedia.getInputStream(),"UTF8"));
		API_InfoEncyclopediaJSON = (JSONObject) JSONParser.parse(reader);
		API_InfoEncyclopediaJSON = (JSONObject) API_InfoEncyclopediaJSON.get("data");
		
		reader = new BufferedReader(new InputStreamReader(API_Modules_Artillery.getInputStream(),"UTF8"));
		API_Modules_ArtilleryJSON = (JSONObject) JSONParser.parse(reader);
		API_Modules_ArtilleryJSON = (JSONObject) API_Modules_ArtilleryJSON.get("data");
		
		reader = new BufferedReader(new InputStreamReader(API_Modules_Engine.getInputStream(), "UTF8"));
		API_Modules_EngineJSON = (JSONObject) JSONParser.parse(reader);
		API_Modules_EngineJSON = (JSONObject) API_Modules_EngineJSON.get("data");
		
		reader = new BufferedReader(new InputStreamReader(API_Modules_Hull.getInputStream(),"UTF8"));
		API_Modules_HullJSON = (JSONObject) JSONParser.parse(reader);
		API_Modules_HullJSON = (JSONObject) API_Modules_HullJSON.get("data");

		reader = new BufferedReader(new InputStreamReader(API_Modules_Radar.getInputStream(),"UTF8"));
		API_Modules_RadarJSON = (JSONObject) JSONParser.parse(reader);
		API_Modules_RadarJSON = (JSONObject) API_Modules_RadarJSON.get("data");
		
		reader = new BufferedReader(new InputStreamReader(API_Modules_Torpedoes.getInputStream(),"UTF8"));
		API_Modules_TorpedoesJSON = (JSONObject) JSONParser.parse(reader);
		API_Modules_TorpedoesJSON = (JSONObject) API_Modules_TorpedoesJSON.get("data");
		
		reader = new BufferedReader(new InputStreamReader(API_Upgrades.getInputStream(),"UTF8"));
		API_UpgradesJSON = (JSONObject) JSONParser.parse(reader);
		API_UpgradesJSON = (JSONObject) API_UpgradesJSON.get("data");
		
		reader = new BufferedReader(new InputStreamReader(API_Warships.getInputStream(),"UTF8"));
		API_WarshipsJSON = (JSONObject) JSONParser.parse(reader);
		API_WarshipsJSON = (JSONObject) API_WarshipsJSON.get("data");
		
		APIShipsJSONList.addAll(API_WarshipsJSON.values());

		for (JSONObject shipJSON : APIShipsJSONList)
		{
			APIShipsHashMap.put((String) shipJSON.get("name"), shipJSON);
		}
	}
//	private void setShipUpgradeModulesInfo()
//	{
//		modules_treeJSON = (JSONObject) getShipJSON().get("modules_tree");
//		modules_treeList.addAll(modules_treeJSON.keySet());
//
//		JSONObject API_suiJSON;
//
//		for (int i = 0; i < modules_treeList.size(); i++)
//		{
//			API_suiJSON = (JSONObject) modules_treeJSON.get(modules_treeList.get(i));
//
//			String API_suiJSONName = (String) API_suiJSON.get("name");
//
//			if (API_suiJSON.get("type").equals("Artillery"))
//			{
//				API_ArtilleryUpgradeJSONList.add(API_suiJSON);
//				API_ArtilleryUpgradeNameList.add(API_suiJSONName);
//				API_ArtilleryUpgradeJSONHashMap.put(API_suiJSONName, API_suiJSON);
//			}
//			else if (API_suiJSON.get("type").equals("Hull"))
//			{
//				API_HullUpgradeJSONList.add(API_suiJSON);
//				API_HullUpgradeNameList.add(API_suiJSONName);
//				API_HullUpgradeJSONHashMap.put(API_suiJSONName, API_suiJSON);
//			}
//			else if (API_suiJSON.get("type").equals("Engine"))
//			{
//				API_EngineUpgradeJSONList.add(API_suiJSON);
//				API_EngineUpgradeNameList.add(API_suiJSONName);
//				API_EngineUpgradeJSONHashMap.put(API_suiJSONName, API_suiJSON);
//			}
//			else if (API_suiJSON.get("type").equals("Suo"))
//			{
//				API_SuoUpgradeJSONList.add(API_suiJSON);
//				API_SuoUpgradeNameList.add(API_suiJSONName);
//				API_SuoUpgradeJSONHashMap.put(API_suiJSONName, API_suiJSON);
//			}
//			else if (API_suiJSON.get("type").equals("Torpedoes"))
//			{
//				API_TorpedoesUpgradeJSONList.add(API_suiJSON);
//				API_TorpedoesUpgradeNameList.add(API_suiJSONName);
//				API_TorpedoesUpgradeJSONHashMap.put(API_suiJSONName, API_suiJSON);
//			}
//		}
//
//		API_ArtilleryUpgradeJSON = new JSONObject(API_ArtilleryUpgradeJSONHashMap);
//		API_HullUpgradeJSON = new JSONObject(API_HullUpgradeJSONHashMap);
//		API_EngineUpgradeJSON = new JSONObject(API_EngineUpgradeJSONHashMap);
//		API_RadarUpgradeJSON = new JSONObject(API_SuoUpgradeJSONHashMap);
//		API_TorpedoUpgradeJSON = new JSONObject(API_TorpedoesUpgradeJSONHashMap);
//
//		Collections.sort(API_ArtilleryUpgradeNameList);
//		Collections.sort(API_HullUpgradeNameList);
//		Collections.sort(API_EngineUpgradeNameList);
//		Collections.sort(API_SuoUpgradeNameList);
//		Collections.sort(API_TorpedoesUpgradeNameList);
//	}

//	public void setShipJSON(String aShipName)
//	{
//		JSONObject images;
//
//		shipJSON = APIShipsHashMap.get(aShipName);
//		ship_id_str = (String) shipJSON.get("ship_id_str");
//
//		images = (JSONObject) shipJSON.get("images");
//		shipSmallImage = (String) images.get("small");
//		shipContour = (String) images.get("contour");
//
//		for (int i = 0; i < APIShipsJSONList.size(); i++)
//		{
//			if (APIShipsJSONList.get(i).containsValue(aShipName))
//			{
//				shipJSON = APIShipsJSONList.get(i);
//				ship_id_str = (String) shipJSON.get("ship_id_str");
//
//				images = (JSONObject) shipJSON.get("images");
//				shipSmallImage = (String) images.get("small");
//				shipContour = (String) images.get("contour");
//				break;
//			}
//		}
//	}
}
