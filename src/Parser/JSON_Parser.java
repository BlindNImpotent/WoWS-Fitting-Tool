package Parser;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import lombok.Data;

/**
 * 
 * @author Aesis (BlindNImpotent)
 *
 */
@Data
public class JSON_Parser 
{
	private API_Parser APIParser;
	private GameParams_Parser GPParser;
	
	private List<String> NationList = new ArrayList<String>();
	private List<String> ShipTypeList = new ArrayList<String>();
	
	private JSONObject modules_treeJSON;
	private List<String> modules_treeList = new ArrayList<String>();
	private List<JSONObject> default_loadouts = new ArrayList<JSONObject>();
	private List<JSONObject> upgradeModules = new ArrayList<JSONObject>();
	
	private JSONObject ShipUpgradeInfoJSON;
	private List<String> ShipUpgradeInfoList = new ArrayList<String>();
	
	private List<Long> API_UpgradesIDList = new ArrayList<Long>();
	private JSONObject GP_UpgradesJSON;
	private List<String> UpgradesNameList = new ArrayList<String>();
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
	private List<String> modSlot1 = new ArrayList<>();
	private List<String> modSlot2 = new ArrayList<>();
	private List<String> modSlot3 = new ArrayList<>();
	private List<String> modSlot4 = new ArrayList<>();
	private List<String> modSlot5 = new ArrayList<>();
	private List<String> modSlot6 = new ArrayList<>();
	private LinkedHashMap<String, JSONObject> mods1 = new LinkedHashMap<String, JSONObject>();
	private LinkedHashMap<String, JSONObject> mods2 = new LinkedHashMap<String, JSONObject>();
	private LinkedHashMap<String, JSONObject> mods3 = new LinkedHashMap<String, JSONObject>();
	private LinkedHashMap<String, JSONObject> mods4 = new LinkedHashMap<String, JSONObject>();
	private LinkedHashMap<String, JSONObject> mods5 = new LinkedHashMap<String, JSONObject>();
	private LinkedHashMap<String, JSONObject> mods6 = new LinkedHashMap<String, JSONObject>();
	
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
	
	private List<JSONObject> USA_AirCarrierJSONList = new ArrayList<JSONObject>();
	private List<JSONObject> USA_BattleshipJSONList = new ArrayList<JSONObject>();
	private List<JSONObject> USA_CruiserJSONList = new ArrayList<JSONObject>();
	private List<JSONObject> USA_DestroyerJSONList = new ArrayList<JSONObject>();
	
	private List<JSONObject> Japan_AirCarrierJSONList = new ArrayList<JSONObject>();
	private List<JSONObject> Japan_BattleshipJSONList = new ArrayList<JSONObject>();
	private List<JSONObject> Japan_CruiserJSONList = new ArrayList<JSONObject>();
	private List<JSONObject> Japan_DestroyerJSONList = new ArrayList<JSONObject>();
	
	private List<JSONObject> USSR_AirCarrierJSONList = new ArrayList<JSONObject>();
	private List<JSONObject> USSR_BattleshipJSONList = new ArrayList<JSONObject>();
	private List<JSONObject> USSR_CruiserJSONList = new ArrayList<JSONObject>();
	private List<JSONObject> USSR_DestroyerJSONList = new ArrayList<JSONObject>();
	
	private List<JSONObject> Germany_AirCarrierJSONList = new ArrayList<JSONObject>();
	private List<JSONObject> Germany_BattleshipJSONList = new ArrayList<JSONObject>();
	private List<JSONObject> Germany_CruiserJSONList = new ArrayList<JSONObject>();
	private List<JSONObject> Germany_DestroyerJSONList = new ArrayList<JSONObject>();
	
	private List<JSONObject> France_AirCarrierJSONList = new ArrayList<JSONObject>();
	private List<JSONObject> France_BattleshipJSONList = new ArrayList<JSONObject>();
	private List<JSONObject> France_CruiserJSONList = new ArrayList<JSONObject>();
	private List<JSONObject> France_DestroyerJSONList = new ArrayList<JSONObject>();
	
	private List<JSONObject> UK_AirCarrierJSONList = new ArrayList<JSONObject>();
	private List<JSONObject> UK_BattleshipJSONList = new ArrayList<JSONObject>();
	private List<JSONObject> UK_CruiserJSONList = new ArrayList<JSONObject>();
	private List<JSONObject> UK_DestroyerJSONList = new ArrayList<JSONObject>();
	
	private List<JSONObject> Poland_DestroyerJSONList = new ArrayList<JSONObject>();
	
	private List<JSONObject> Pan_Asia_DestroyerJSONList = new ArrayList<JSONObject>();
	
	private List<String> USA_AirCarrierNameList = new ArrayList<String>();
	private List<String> USA_BattleshipNameList = new ArrayList<String>();
	private List<String> USA_CruiserNameList = new ArrayList<String>();
	private List<String> USA_DestroyerNameList = new ArrayList<String>();
	
	private List<String> Japan_AirCarrierNameList = new ArrayList<String>();
	private List<String> Japan_BattleshipNameList = new ArrayList<String>();
	private List<String> Japan_CruiserNameList = new ArrayList<String>();
	private List<String> Japan_DestroyerNameList = new ArrayList<String>();
	
	private List<String> USSR_AirCarrierNameList = new ArrayList<String>();
	private List<String> USSR_BattleshipNameList = new ArrayList<String>();
	private List<String> USSR_CruiserNameList = new ArrayList<String>();
	private List<String> USSR_DestroyerNameList = new ArrayList<String>();
	
	private List<String> Germany_AirCarrierNameList = new ArrayList<String>();
	private List<String> Germany_BattleshipNameList = new ArrayList<String>();
	private List<String> Germany_CruiserNameList = new ArrayList<String>();
	private List<String> Germany_DestroyerNameList = new ArrayList<String>();
	
	private List<String> France_AirCarrierNameList = new ArrayList<String>();
	private List<String> France_BattleshipNameList = new ArrayList<String>();
	private List<String> France_CruiserNameList = new ArrayList<String>();
	private List<String> France_DestroyerNameList = new ArrayList<String>();
	
	private List<String> UK_AirCarrierNameList = new ArrayList<String>();
	private List<String> UK_BattleshipNameList = new ArrayList<String>();
	private List<String> UK_CruiserNameList = new ArrayList<String>();
	private List<String> UK_DestroyerNameList = new ArrayList<String>();
	
	private List<String> Poland_DestroyerNameList = new ArrayList<String>();
	
	private List<String> Pan_Asia_DestroyerNameList = new ArrayList<String>();
	
	
	public JSON_Parser(String aShipName) throws FileNotFoundException, IOException, ParseException
	{
		APIParser = new API_Parser(aShipName);
		GPParser = new GameParams_Parser(APIParser.getShip_id_str());
		
		setShipUpgradeModulesInfo();		
		setShipsJSONAndName();
		setUpgrades();
		setNationList();
		setShipTypeList();
		setCamouflageAndFlags();
	}
	
	@SuppressWarnings("unchecked")
	private void setShipUpgradeModulesInfo()
	{	
		modules_treeJSON = (JSONObject) APIParser.getShipJSON().get("modules_tree");
		modules_treeList.addAll(modules_treeJSON.keySet());
		
		JSONObject API_modules;
		
		for (int i = 0; i < modules_treeList.size(); i++)
		{
			API_modules = (JSONObject) modules_treeJSON.get(modules_treeList.get(i));
			
			if (API_modules.get("is_default").equals(true))
			{
				default_loadouts.add(API_modules);
			}
			else
			{
				upgradeModules.add(API_modules);
			}			
		}
		
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
	
	private void setShipsJSONAndName()
	{
		List<JSONObject> APIJSONList = (List<JSONObject>) APIParser.getAPIShipJSONList();
		JSONObject APIJSON;
		
		for (int i = 0; i < APIParser.getAPIShipJSONList().size(); i++)
		{	
			APIJSON = (JSONObject) APIJSONList.get(i);
			
			if (APIJSON.get("nation").equals("usa"))
			{
				if (APIJSON.get("type").equals("AirCarrier"))
				{
					USA_AirCarrierJSONList.add(APIJSON);
				}
				else if (APIJSON.get("type").equals("Battleship"))
				{
					USA_BattleshipJSONList.add(APIJSON);
				}
				else if (APIJSON.get("type").equals("Cruiser"))
				{
					USA_CruiserJSONList.add(APIJSON);
				}
				else if (APIJSON.get("type").equals("Destroyer"))
				{
					USA_DestroyerJSONList.add(APIJSON);
				}
			}
			else if (APIJSON.get("nation").equals("japan"))
			{
				if (APIJSON.get("type").equals("AirCarrier"))
				{
					Japan_AirCarrierJSONList.add(APIJSON);
				}
				else if (APIJSON.get("type").equals("Battleship"))
				{
					Japan_BattleshipJSONList.add(APIJSON);
				}
				else if (APIJSON.get("type").equals("Cruiser"))
				{
					Japan_CruiserJSONList.add(APIJSON);
				}
				else if (APIJSON.get("type").equals("Destroyer"))
				{
					Japan_DestroyerJSONList.add(APIJSON);
				}
			}
			else if (APIJSON.get("nation").equals("ussr"))
			{
				if (APIJSON.get("type").equals("AirCarrier"))
				{
					USSR_AirCarrierJSONList.add(APIJSON);
				}
				else if (APIJSON.get("type").equals("Battleship"))
				{
					USSR_BattleshipJSONList.add(APIJSON);
				}
				else if (APIJSON.get("type").equals("Cruiser"))
				{
					USSR_CruiserJSONList.add(APIJSON);
				}
				else if (APIJSON.get("type").equals("Destroyer"))
				{
					USSR_DestroyerJSONList.add(APIJSON);
				}
			}
			else if (APIJSON.get("nation").equals("germany"))
			{
				if (APIJSON.get("type").equals("AirCarrier"))
				{
					Germany_AirCarrierJSONList.add(APIJSON);
				}
				else if (APIJSON.get("type").equals("Battleship"))
				{
					Germany_BattleshipJSONList.add(APIJSON);
				}
				else if (APIJSON.get("type").equals("Cruiser"))
				{
					Germany_CruiserJSONList.add(APIJSON);
				}
				else if (APIJSON.get("type").equals("Destroyer"))
				{
					Germany_DestroyerJSONList.add(APIJSON);
				}
			}
			else if (APIJSON.get("nation").equals("uk"))
			{
				if (APIJSON.get("type").equals("AirCarrier"))
				{
					UK_AirCarrierJSONList.add(APIJSON);
				}
				else if (APIJSON.get("type").equals("Battleship"))
				{
					UK_BattleshipJSONList.add(APIJSON);
				}
				else if (APIJSON.get("type").equals("Cruiser"))
				{
					UK_CruiserJSONList.add(APIJSON);
				}
				else if (APIJSON.get("type").equals("Destroyer"))
				{
					UK_DestroyerJSONList.add(APIJSON);
				}
			}
			else if (APIJSON.get("nation").equals("france"))
			{
				if (APIJSON.get("type").equals("AirCarrier"))
				{
					France_AirCarrierJSONList.add(APIJSON);
				}
				else if (APIJSON.get("type").equals("Battleship"))
				{
					France_BattleshipJSONList.add(APIJSON);
				}
				else if (APIJSON.get("type").equals("Cruiser"))
				{
					France_CruiserJSONList.add(APIJSON);
				}
				else if (APIJSON.get("type").equals("Destroyer"))
				{
					France_DestroyerJSONList.add(APIJSON);
				}
			}
			else if (APIJSON.get("nation").equals("poland"))
			{
				if (APIJSON.get("type").equals("Destroyer"))
				{
					Poland_DestroyerJSONList.add(APIJSON);
				}
			}
			else if (APIJSON.get("nation").equals("pan_asia"))
			{
				if (APIJSON.get("type").equals("Destroyer"))
				{
					Poland_DestroyerJSONList.add(APIJSON);
				}
			}			
		}
		
		//USA
		USA_AirCarrierJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		USA_BattleshipJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		USA_CruiserJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		USA_DestroyerJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));				
		
		USA_AirCarrierJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		USA_BattleshipJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		USA_CruiserJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		USA_DestroyerJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		
		USA_AirCarrierJSONList.stream().forEach((USA_AirCarrier -> USA_AirCarrierNameList.add((String) USA_AirCarrier.get("name"))));
		USA_BattleshipJSONList.stream().forEach((USA_Battleship -> USA_BattleshipNameList.add((String) USA_Battleship.get("name"))));
		USA_CruiserJSONList.stream().forEach((USA_Cruiser -> USA_CruiserNameList.add((String) USA_Cruiser.get("name"))));
		USA_DestroyerJSONList.stream().forEach((USA_Destroyer -> USA_DestroyerNameList.add((String) USA_Destroyer.get("name"))));
		
		//USSR
		USSR_AirCarrierJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		USSR_BattleshipJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		USSR_CruiserJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		USSR_DestroyerJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));				
		
		USSR_AirCarrierJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		USSR_BattleshipJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		USSR_CruiserJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		USSR_DestroyerJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		
		USSR_AirCarrierJSONList.stream().forEach((USSR_AirCarrier -> USSR_AirCarrierNameList.add((String) USSR_AirCarrier.get("name"))));
		USSR_BattleshipJSONList.stream().forEach((USSR_Battleship -> USSR_BattleshipNameList.add((String) USSR_Battleship.get("name"))));
		USSR_CruiserJSONList.stream().forEach((USSR_Cruiser -> USSR_CruiserNameList.add((String) USSR_Cruiser.get("name"))));
		USSR_DestroyerJSONList.stream().forEach((USSR_Destroyer -> USSR_DestroyerNameList.add((String) USSR_Destroyer.get("name"))));
		
		//Japan
		Japan_AirCarrierJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		Japan_BattleshipJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		Japan_CruiserJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		Japan_DestroyerJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));				
		
		Japan_AirCarrierJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		Japan_BattleshipJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		Japan_CruiserJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		Japan_DestroyerJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		
		Japan_AirCarrierJSONList.stream().forEach((Japan_AirCarrier -> Japan_AirCarrierNameList.add((String) Japan_AirCarrier.get("name"))));
		Japan_BattleshipJSONList.stream().forEach((Japan_Battleship -> Japan_BattleshipNameList.add((String) Japan_Battleship.get("name"))));
		Japan_CruiserJSONList.stream().forEach((Japan_Cruiser -> Japan_CruiserNameList.add((String) Japan_Cruiser.get("name"))));
		Japan_DestroyerJSONList.stream().forEach((Japan_Destroyer -> Japan_DestroyerNameList.add((String) Japan_Destroyer.get("name"))));
		
		//Germany
		Germany_AirCarrierJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		Germany_BattleshipJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		Germany_CruiserJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		Germany_DestroyerJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));				
		
		Germany_AirCarrierJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		Germany_BattleshipJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		Germany_CruiserJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		Germany_DestroyerJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		
		Germany_AirCarrierJSONList.stream().forEach((Germany_AirCarrier -> Germany_AirCarrierNameList.add((String) Germany_AirCarrier.get("name"))));
		Germany_BattleshipJSONList.stream().forEach((Germany_Battleship -> Germany_BattleshipNameList.add((String) Germany_Battleship.get("name"))));
		Germany_CruiserJSONList.stream().forEach((Germany_Cruiser -> Germany_CruiserNameList.add((String) Germany_Cruiser.get("name"))));
		Germany_DestroyerJSONList.stream().forEach((Germany_Destroyer -> Germany_DestroyerNameList.add((String) Germany_Destroyer.get("name"))));
				
		//France
		France_AirCarrierJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		France_BattleshipJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		France_CruiserJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		France_DestroyerJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));				
		
		France_AirCarrierJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		France_BattleshipJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		France_CruiserJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		France_DestroyerJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		
		France_AirCarrierJSONList.stream().forEach((France_AirCarrier -> France_AirCarrierNameList.add((String) France_AirCarrier.get("name"))));
		France_BattleshipJSONList.stream().forEach((France_Battleship -> France_BattleshipNameList.add((String) France_Battleship.get("name"))));
		France_CruiserJSONList.stream().forEach((France_Cruiser -> France_CruiserNameList.add((String) France_Cruiser.get("name"))));
		France_DestroyerJSONList.stream().forEach((France_Destroyer -> France_DestroyerNameList.add((String) France_Destroyer.get("name"))));

		//UK
		UK_AirCarrierJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		UK_BattleshipJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		UK_CruiserJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		UK_DestroyerJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));				
		
		UK_AirCarrierJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		UK_BattleshipJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		UK_CruiserJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		UK_DestroyerJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		
		UK_AirCarrierJSONList.stream().forEach((UK_AirCarrier -> UK_AirCarrierNameList.add((String) UK_AirCarrier.get("name"))));
		UK_BattleshipJSONList.stream().forEach((UK_Battleship -> UK_BattleshipNameList.add((String) UK_Battleship.get("name"))));
		UK_CruiserJSONList.stream().forEach((UK_Cruiser -> UK_CruiserNameList.add((String) UK_Cruiser.get("name"))));
		UK_DestroyerJSONList.stream().forEach((UK_Destroyer -> UK_DestroyerNameList.add((String) UK_Destroyer.get("name"))));
		
		//Poland
		Poland_DestroyerJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		
		//Pan_Asia
		Pan_Asia_DestroyerJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));		
	}
	
	@SuppressWarnings("unchecked")
	private void setUpgrades()
	{
		JSONArray modules = (JSONArray) APIParser.getShipJSON().get("upgrades"); 
		API_UpgradesIDList.addAll(modules);
		GP_UpgradesJSON = (JSONObject) GPParser.getShipJSON().get("ShipModernization");
		
		ModernizationSlot1 = (JSONObject) GP_UpgradesJSON.get("ModernizationSlot1");
		ModernizationSlot1_mods = (JSONArray) ModernizationSlot1.get("mods");
		
		ModernizationSlot2 = (JSONObject) GP_UpgradesJSON.get("ModernizationSlot2");
		ModernizationSlot2_mods = (JSONArray) ModernizationSlot2.get("mods");
		
		ModernizationSlot3 = (JSONObject) GP_UpgradesJSON.get("ModernizationSlot3");
		ModernizationSlot3_mods = (JSONArray) ModernizationSlot3.get("mods");
		
		ModernizationSlot4 = (JSONObject) GP_UpgradesJSON.get("ModernizationSlot4");
		ModernizationSlot4_mods = (JSONArray) ModernizationSlot4.get("mods");
		
		ModernizationSlot5 = (JSONObject) GP_UpgradesJSON.get("ModernizationSlot5");
		ModernizationSlot5_mods = (JSONArray) ModernizationSlot5.get("mods");
		
		ModernizationSlot6 = (JSONObject) GP_UpgradesJSON.get("ModernizationSlot6");
		ModernizationSlot6_mods = (JSONArray) ModernizationSlot6.get("mods");
		
		JSONObject ship_modifications = (JSONObject) APIParser.getAPIJSON().get("ship_modifications");

		if (ModernizationSlot1_mods != null) {
			for (int i = 0; i < ModernizationSlot1_mods.size(); i++) {
				mods1.put((String) ship_modifications.get(ModernizationSlot1_mods.get(i)), GPParser.getGameParamsHashMap().get(ModernizationSlot1_mods.get(i)));
				modSlot1.add((String) ModernizationSlot1_mods.get(i));
			}
		}

		if (ModernizationSlot2_mods != null) {
			for (int i = 0; i < ModernizationSlot2_mods.size(); i++) {
				mods2.put((String) ship_modifications.get(ModernizationSlot2_mods.get(i)), GPParser.getGameParamsHashMap().get(ModernizationSlot2_mods.get(i)));
				modSlot2.add((String) ModernizationSlot2_mods.get(i));
			}
		}

		if (ModernizationSlot3_mods != null) {
			for (int i = 0; i < ModernizationSlot3_mods.size(); i++) {
				mods3.put((String) ship_modifications.get(ModernizationSlot3_mods.get(i)), GPParser.getGameParamsHashMap().get(ModernizationSlot3_mods.get(i)));
				modSlot3.add((String) ModernizationSlot3_mods.get(i));
			}
		}

		if (ModernizationSlot4_mods != null) {
			for (int i = 0; i < ModernizationSlot4_mods.size(); i++) {
				mods4.put((String) ship_modifications.get(ModernizationSlot4_mods.get(i)), GPParser.getGameParamsHashMap().get(ModernizationSlot4_mods.get(i)));
				modSlot4.add((String) ModernizationSlot4_mods.get(i));
			}
		}

		if (ModernizationSlot5_mods != null) {
			for (int i = 0; i < ModernizationSlot5_mods.size(); i++) {
				mods5.put((String) ship_modifications.get(ModernizationSlot5_mods.get(i)), GPParser.getGameParamsHashMap().get(ModernizationSlot5_mods.get(i)));
				modSlot5.add((String) ModernizationSlot5_mods.get(i));
			}
		}

		if (ModernizationSlot6_mods != null) {
			for (int i = 0; i < ModernizationSlot6_mods.size(); i++) {
				mods6.put((String) ship_modifications.get(ModernizationSlot6_mods.get(i)), GPParser.getGameParamsHashMap().get(ModernizationSlot6_mods.get(i)));
				modSlot6.add((String) ModernizationSlot6_mods.get(i));
			}
		}
	}	
	
	@SuppressWarnings("unchecked")
	private void setNationList()
	{
		JSONObject APINations = (JSONObject) APIParser.getAPIJSON().get("ship_nations");
		NationList.addAll(APINations.values());
		Collections.sort(NationList);
	}
	
	@SuppressWarnings("unchecked")
	private void setShipTypeList()
	{
		JSONObject APIShipType = (JSONObject) APIParser.getAPIJSON().get("ship_types");
		ShipTypeList.addAll(APIShipType.keySet());
		Collections.sort(ShipTypeList);
	}
	
	private void setCamouflageAndFlags()
	{
		
	}
}
