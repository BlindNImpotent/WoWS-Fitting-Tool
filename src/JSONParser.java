import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import lombok.Data;

/**
 * 
 * @author Aesis (BlindNImpotent)
 *
 */
@Data
public class JSONParser 
{
	private APIParser APIParser;
	private GameParamsParser GPParser;
	
	private JSONObject modules_treeJSON;
	private List<String> modules_treeList = new ArrayList<String>();
	private List<JSONObject> default_loadouts = new ArrayList<JSONObject>();
	private List<JSONObject> shipUpgrades = new ArrayList<JSONObject>();
	
	private JSONObject ShipUpgradeInfoJSON;
	private List<String> ShipUpgradeInfoList = new ArrayList<String>();
	
	private List<JSONObject> API_ArtilleryUpgradeJSONList = new ArrayList<JSONObject>();
	private List<String> API_ArtilleryUpgradeNameList = new ArrayList<String>();
	private HashMap<String, JSONObject> GP_ArtilleryUpgradeJSONHashMap = new HashMap<String, JSONObject>();
	
	private List<JSONObject> API_HullUpgradeJSONList = new ArrayList<JSONObject>();
	private List<String> API_HullUpgradeNameList = new ArrayList<String>();
	private HashMap<String, JSONObject> GP_HullUpgradeJSONHashMap = new HashMap<String, JSONObject>();
	
	private List<JSONObject> API_EngineUpgradeJSONList = new ArrayList<JSONObject>();
	private List<String> API_EngineUpgradeNameList = new ArrayList<String>();
	private HashMap<String, JSONObject> GP_EngineUpgradeJSONHashMap = new HashMap<String, JSONObject>();
	
	private List<JSONObject> API_SuoUpgradeJSONList = new ArrayList<JSONObject>();
	private List<String> API_SuoUpgradeNameList = new ArrayList<String>();
	private HashMap<String, JSONObject> GP_SuoUpgradeJSONHashMap = new HashMap<String, JSONObject>();
	
	private List<JSONObject> API_TorpedoesUpgradeJSONList = new ArrayList<JSONObject>();
	private List<String> API_TorpedoesUpgradeNameList = new ArrayList<String>();
	private HashMap<String, JSONObject> GP_TorpedoesUpgradeJSONHashMap = new HashMap<String, JSONObject>();
	
	public JSONParser(String aShipName) throws FileNotFoundException, IOException, ParseException
	{
		APIParser = new APIParser(aShipName);
		GPParser = new GameParamsParser(APIParser.getShip_id_str());		
		
		setAPIShipUpgradeInfo();
		setShipUpgradeInfo();
	}
	
	@SuppressWarnings("unchecked")
	private void setAPIShipUpgradeInfo()
	{
		modules_treeJSON = APIParser.getModules_tree();
		modules_treeList.addAll(modules_treeJSON.keySet());		
		
		JSONObject mtJSON;
		
		for (int i = 0; i < modules_treeList.size(); i++)
		{
			mtJSON = (JSONObject) modules_treeJSON.get(modules_treeList.get(i));
			
			if (mtJSON.get("is_default").equals(true))
			{				
				default_loadouts.add(mtJSON);
			}
			else
			{
				shipUpgrades.add(mtJSON);
			}			
		}
	}
	
	@SuppressWarnings("unchecked")
	private void setShipUpgradeInfo()
	{	
		ShipUpgradeInfoJSON = (JSONObject) GPParser.getShipJSON().get("ShipUpgradeInfo");
		ShipUpgradeInfoList.addAll(ShipUpgradeInfoJSON.keySet());
				
		JSONObject API_suiJSON = null;
		JSONObject GP_suiJSON = null;
		
		for (int i = 0; i < modules_treeJSON.size(); i++)
		{
			API_suiJSON = (JSONObject) modules_treeJSON.get(modules_treeList.get(i));
			
			String API_suiJSONName = (String) API_suiJSON.get("name");
			String API_suiJSONID = (String) API_suiJSON.get("module_id_str");
			
			for (int j = 0; j < ShipUpgradeInfoJSON.size(); j++)
			{
				if (ShipUpgradeInfoList.get(j).contains(API_suiJSONID))
				{
					GP_suiJSON = (JSONObject) ShipUpgradeInfoJSON.get(ShipUpgradeInfoList.get(j));
				}
			}
			
			if (API_suiJSON.get("type").equals("Artillery"))
			{
				API_ArtilleryUpgradeJSONList.add(API_suiJSON);
				API_ArtilleryUpgradeNameList.add(API_suiJSONName);
				GP_ArtilleryUpgradeJSONHashMap.put(API_suiJSONName, GP_suiJSON);
			}
			else if (API_suiJSON.get("type").equals("Hull"))
			{
				API_HullUpgradeJSONList.add(API_suiJSON);
				API_HullUpgradeNameList.add(API_suiJSONName);
				GP_HullUpgradeJSONHashMap.put(API_suiJSONName, GP_suiJSON);
			}
			else if (API_suiJSON.get("type").equals("Engine"))
			{
				API_EngineUpgradeJSONList.add(API_suiJSON);
				API_EngineUpgradeNameList.add(API_suiJSONName);				
				GP_EngineUpgradeJSONHashMap.put(API_suiJSONName, GP_suiJSON);
			}
			else if (API_suiJSON.get("type").equals("Suo"))
			{
				API_SuoUpgradeJSONList.add(API_suiJSON);
				API_SuoUpgradeNameList.add(API_suiJSONName);
				GP_SuoUpgradeJSONHashMap.put(API_suiJSONName, GP_suiJSON);
			}
			else if (API_suiJSON.get("type").equals("Torpedoes"))
			{
				API_TorpedoesUpgradeJSONList.add(API_suiJSON);
				API_TorpedoesUpgradeNameList.add(API_suiJSONName);
				GP_TorpedoesUpgradeJSONHashMap.put(API_suiJSONName, GP_suiJSON);
			}						
		}
		
		Collections.sort(API_ArtilleryUpgradeNameList);
		Collections.sort(API_HullUpgradeNameList);
		Collections.sort(API_EngineUpgradeNameList);
		Collections.sort(API_SuoUpgradeNameList);
		Collections.sort(API_TorpedoesUpgradeNameList);
	}
	
	
	
	
}
