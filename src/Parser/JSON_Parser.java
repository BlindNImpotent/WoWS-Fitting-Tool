package Parser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

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
	
	private List<String> NationList = new ArrayList<>();
	private String nation;
	private List<String> ShipTypeList = new ArrayList<>();
	
	private JSONObject modules_treeJSON;
	private List<String> modules_treeList = new ArrayList<>();
	private List<JSONObject> default_loadouts = new ArrayList<>();
	private List<JSONObject> upgradeModules = new ArrayList<>();
	
	private JSONObject ShipUpgradeInfoJSON;
	private List<String> ShipUpgradeInfoList = new ArrayList<>();
	private JSONObject GP_ArtilleryJSON;
	private JSONObject GP_TurretJSON;




	private List<Long> API_UpgradesIDList = new ArrayList<>();
	private JSONObject GP_UpgradesJSON;
	private List<String> UpgradesNameList = new ArrayList<>();
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
	private LinkedHashMap<String, JSONObject> mods1 = new LinkedHashMap<>();
	private LinkedHashMap<String, JSONObject> mods2 = new LinkedHashMap<>();
	private LinkedHashMap<String, JSONObject> mods3 = new LinkedHashMap<>();
	private LinkedHashMap<String, JSONObject> mods4 = new LinkedHashMap<>();
	private LinkedHashMap<String, JSONObject> mods5 = new LinkedHashMap<>();
	private LinkedHashMap<String, JSONObject> mods6 = new LinkedHashMap<>();
	private List<String> modSlot1Name = new ArrayList<>();
	private List<String> modSlot2Name = new ArrayList<>();
	private List<String> modSlot3Name = new ArrayList<>();
	private List<String> modSlot4Name = new ArrayList<>();
	private List<String> modSlot5Name = new ArrayList<>();
	private List<String> modSlot6Name = new ArrayList<>();
	
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
	
	private List<JSONObject> USA_AirCarrierJSONList = new ArrayList<>();
	private List<JSONObject> USA_BattleshipJSONList = new ArrayList<>();
	private List<JSONObject> USA_CruiserJSONList = new ArrayList<>();
	private List<JSONObject> USA_DestroyerJSONList = new ArrayList<>();
	
	private List<JSONObject> Japan_AirCarrierJSONList = new ArrayList<>();
	private List<JSONObject> Japan_BattleshipJSONList = new ArrayList<>();
	private List<JSONObject> Japan_CruiserJSONList = new ArrayList<>();
	private List<JSONObject> Japan_DestroyerJSONList = new ArrayList<>();
	
	private List<JSONObject> USSR_AirCarrierJSONList = new ArrayList<>();
	private List<JSONObject> USSR_BattleshipJSONList = new ArrayList<>();
	private List<JSONObject> USSR_CruiserJSONList = new ArrayList<>();
	private List<JSONObject> USSR_DestroyerJSONList = new ArrayList<>();
	
	private List<JSONObject> Germany_AirCarrierJSONList = new ArrayList<>();
	private List<JSONObject> Germany_BattleshipJSONList = new ArrayList<>();
	private List<JSONObject> Germany_CruiserJSONList = new ArrayList<>();
	private List<JSONObject> Germany_DestroyerJSONList = new ArrayList<>();
	
	private List<JSONObject> France_AirCarrierJSONList = new ArrayList<>();
	private List<JSONObject> France_BattleshipJSONList = new ArrayList<>();
	private List<JSONObject> France_CruiserJSONList = new ArrayList<>();
	private List<JSONObject> France_DestroyerJSONList = new ArrayList<>();
	
	private List<JSONObject> UK_AirCarrierJSONList = new ArrayList<>();
	private List<JSONObject> UK_BattleshipJSONList = new ArrayList<>();
	private List<JSONObject> UK_CruiserJSONList = new ArrayList<>();
	private List<JSONObject> UK_DestroyerJSONList = new ArrayList<>();
	
	private List<JSONObject> Poland_DestroyerJSONList = new ArrayList<>();
	
	private List<JSONObject> Pan_Asia_DestroyerJSONList = new ArrayList<>();
	
	private List<String> USA_AirCarrierNameList = new ArrayList<>();
	private List<String> USA_BattleshipNameList = new ArrayList<>();
	private List<String> USA_CruiserNameList = new ArrayList<>();
	private List<String> USA_DestroyerNameList = new ArrayList<>();
	
	private List<String> Japan_AirCarrierNameList = new ArrayList<>();
	private List<String> Japan_BattleshipNameList = new ArrayList<>();
	private List<String> Japan_CruiserNameList = new ArrayList<>();
	private List<String> Japan_DestroyerNameList = new ArrayList<>();
	
	private List<String> USSR_AirCarrierNameList = new ArrayList<>();
	private List<String> USSR_BattleshipNameList = new ArrayList<>();
	private List<String> USSR_CruiserNameList = new ArrayList<>();
	private List<String> USSR_DestroyerNameList = new ArrayList<>();
	
	private List<String> Germany_AirCarrierNameList = new ArrayList<>();
	private List<String> Germany_BattleshipNameList = new ArrayList<>();
	private List<String> Germany_CruiserNameList = new ArrayList<>();
	private List<String> Germany_DestroyerNameList = new ArrayList<>();
	
	private List<String> France_AirCarrierNameList = new ArrayList<>();
	private List<String> France_BattleshipNameList = new ArrayList<>();
	private List<String> France_CruiserNameList = new ArrayList<>();
	private List<String> France_DestroyerNameList = new ArrayList<>();
	
	private List<String> UK_AirCarrierNameList = new ArrayList<>();
	private List<String> UK_BattleshipNameList = new ArrayList<>();
	private List<String> UK_CruiserNameList = new ArrayList<>();
	private List<String> UK_DestroyerNameList = new ArrayList<>();
	
	private List<String> Poland_DestroyerNameList = new ArrayList<>();
	
	private List<String> Pan_Asia_DestroyerNameList = new ArrayList<>();
	
	
	public JSON_Parser(String aShipName) throws IOException, ParseException
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

		JSONObject API_suiJSON;
		
		for (int i = 0; i < modules_treeList.size(); i++)
		{
			API_suiJSON = (JSONObject) modules_treeJSON.get(modules_treeList.get(i));

			if (API_suiJSON.get("is_default").equals(true)) {
				default_loadouts.add(API_suiJSON);
			} else {
				upgradeModules.add(API_suiJSON);
			}

			String API_suiJSONName = (String) API_suiJSON.get("name");

			if (API_suiJSON.get("type").equals("Artillery"))
			{
				API_ArtilleryUpgradeJSONList.add(API_suiJSON);
				API_ArtilleryUpgradeNameList.add(API_suiJSONName);
				API_ArtilleryUpgradeJSONHashMap.put(API_suiJSONName, API_suiJSON);
			}
			else if (API_suiJSON.get("type").equals("Hull"))
			{
				API_HullUpgradeJSONList.add(API_suiJSON);
				API_HullUpgradeNameList.add(API_suiJSONName);
				API_HullUpgradeJSONHashMap.put(API_suiJSONName, API_suiJSON);
			}
			else if (API_suiJSON.get("type").equals("Engine"))
			{
				API_EngineUpgradeJSONList.add(API_suiJSON);
				API_EngineUpgradeNameList.add(API_suiJSONName);
				API_EngineUpgradeJSONHashMap.put(API_suiJSONName, API_suiJSON);
			}
			else if (API_suiJSON.get("type").equals("Suo"))
			{
				API_SuoUpgradeJSONList.add(API_suiJSON);
				API_SuoUpgradeNameList.add(API_suiJSONName);
				API_SuoUpgradeJSONHashMap.put(API_suiJSONName, API_suiJSON);
			}
			else if (API_suiJSON.get("type").equals("Torpedoes"))
			{
				API_TorpedoesUpgradeJSONList.add(API_suiJSON);
				API_TorpedoesUpgradeNameList.add(API_suiJSONName);
				API_TorpedoesUpgradeJSONHashMap.put(API_suiJSONName, API_suiJSON);
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
		
		USA_AirCarrierJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo((long) o2.get("tier"))));
		USA_BattleshipJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo((long) o2.get("tier"))));
		USA_CruiserJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo((long) o2.get("tier"))));
		USA_DestroyerJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo((long) o2.get("tier"))));
		
		USA_AirCarrierJSONList.forEach((USA_AirCarrier -> USA_AirCarrierNameList.add((String) USA_AirCarrier.get("name"))));
		USA_BattleshipJSONList.forEach((USA_Battleship -> USA_BattleshipNameList.add((String) USA_Battleship.get("name"))));
		USA_CruiserJSONList.forEach((USA_Cruiser -> USA_CruiserNameList.add((String) USA_Cruiser.get("name"))));
		USA_DestroyerJSONList.forEach((USA_Destroyer -> USA_DestroyerNameList.add((String) USA_Destroyer.get("name"))));
		
		//USSR
		USSR_AirCarrierJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		USSR_BattleshipJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		USSR_CruiserJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		USSR_DestroyerJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));				
		
		USSR_AirCarrierJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		USSR_BattleshipJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		USSR_CruiserJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		USSR_DestroyerJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		
		USSR_AirCarrierJSONList.forEach((USSR_AirCarrier -> USSR_AirCarrierNameList.add((String) USSR_AirCarrier.get("name"))));
		USSR_BattleshipJSONList.forEach((USSR_Battleship -> USSR_BattleshipNameList.add((String) USSR_Battleship.get("name"))));
		USSR_CruiserJSONList.forEach((USSR_Cruiser -> USSR_CruiserNameList.add((String) USSR_Cruiser.get("name"))));
		USSR_DestroyerJSONList.forEach((USSR_Destroyer -> USSR_DestroyerNameList.add((String) USSR_Destroyer.get("name"))));
		
		//Japan
		Japan_AirCarrierJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		Japan_BattleshipJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		Japan_CruiserJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		Japan_DestroyerJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));				
		
		Japan_AirCarrierJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		Japan_BattleshipJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		Japan_CruiserJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		Japan_DestroyerJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		
		Japan_AirCarrierJSONList.forEach((Japan_AirCarrier -> Japan_AirCarrierNameList.add((String) Japan_AirCarrier.get("name"))));
		Japan_BattleshipJSONList.forEach((Japan_Battleship -> Japan_BattleshipNameList.add((String) Japan_Battleship.get("name"))));
		Japan_CruiserJSONList.forEach((Japan_Cruiser -> Japan_CruiserNameList.add((String) Japan_Cruiser.get("name"))));
		Japan_DestroyerJSONList.forEach((Japan_Destroyer -> Japan_DestroyerNameList.add((String) Japan_Destroyer.get("name"))));
		
		//Germany
		Germany_AirCarrierJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		Germany_BattleshipJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		Germany_CruiserJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		Germany_DestroyerJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));				
		
		Germany_AirCarrierJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		Germany_BattleshipJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		Germany_CruiserJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		Germany_DestroyerJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		
		Germany_AirCarrierJSONList.forEach((Germany_AirCarrier -> Germany_AirCarrierNameList.add((String) Germany_AirCarrier.get("name"))));
		Germany_BattleshipJSONList.forEach((Germany_Battleship -> Germany_BattleshipNameList.add((String) Germany_Battleship.get("name"))));
		Germany_CruiserJSONList.forEach((Germany_Cruiser -> Germany_CruiserNameList.add((String) Germany_Cruiser.get("name"))));
		Germany_DestroyerJSONList.forEach((Germany_Destroyer -> Germany_DestroyerNameList.add((String) Germany_Destroyer.get("name"))));
				
		//France
		France_AirCarrierJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		France_BattleshipJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		France_CruiserJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		France_DestroyerJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));				
		
		France_AirCarrierJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		France_BattleshipJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		France_CruiserJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		France_DestroyerJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		
		France_AirCarrierJSONList.forEach((France_AirCarrier -> France_AirCarrierNameList.add((String) France_AirCarrier.get("name"))));
		France_BattleshipJSONList.forEach((France_Battleship -> France_BattleshipNameList.add((String) France_Battleship.get("name"))));
		France_CruiserJSONList.forEach((France_Cruiser -> France_CruiserNameList.add((String) France_Cruiser.get("name"))));
		France_DestroyerJSONList.forEach((France_Destroyer -> France_DestroyerNameList.add((String) France_Destroyer.get("name"))));

		//UK
		UK_AirCarrierJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		UK_BattleshipJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		UK_CruiserJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		UK_DestroyerJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));				
		
		UK_AirCarrierJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		UK_BattleshipJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		UK_CruiserJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		UK_DestroyerJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		
		UK_AirCarrierJSONList.forEach((UK_AirCarrier -> UK_AirCarrierNameList.add((String) UK_AirCarrier.get("name"))));
		UK_BattleshipJSONList.forEach((UK_Battleship -> UK_BattleshipNameList.add((String) UK_Battleship.get("name"))));
		UK_CruiserJSONList.forEach((UK_Cruiser -> UK_CruiserNameList.add((String) UK_Cruiser.get("name"))));
		UK_DestroyerJSONList.forEach((UK_Destroyer -> UK_DestroyerNameList.add((String) UK_Destroyer.get("name"))));
		
		//Poland
		Poland_DestroyerJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		Poland_DestroyerJSONList.forEach((Poland_Destroyer -> Poland_DestroyerNameList.add((String) Poland_Destroyer.get("name"))));

		//Pan_Asia
		Pan_Asia_DestroyerJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		Pan_Asia_DestroyerJSONList.forEach((Pan_Asia_Destroyer -> Pan_Asia_DestroyerNameList.add((String) Pan_Asia_Destroyer.get("name"))));
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

		modSlot1Name.add("None");
		modSlot1Name.addAll(mods1.keySet());
		modSlot2Name.add("None");
		modSlot2Name.addAll(mods1.keySet());
		modSlot3Name.add("None");
		modSlot3Name.addAll(mods1.keySet());
		modSlot4Name.add("None");
		modSlot4Name.addAll(mods1.keySet());
		modSlot5Name.add("None");
		modSlot5Name.addAll(mods1.keySet());
		modSlot6Name.add("None");
		modSlot6Name.addAll(mods1.keySet());
	}
	
	@SuppressWarnings("unchecked")
	private void setNationList()
	{
		JSONObject APINations = (JSONObject) APIParser.getAPIJSON().get("ship_nations");
		NationList.addAll(APINations.values());
		Collections.sort(NationList);

		nation = (String) APIParser.getShipJSON().get("nation");
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

	public void setTurretStats(String aTurret)
	{
		if (aTurret == null)
		{
			return;
		}

		JSONObject API_JSON = API_ArtilleryUpgradeJSONHashMap.get(aTurret);
		String API_module_id_str = null;
		String GP_turretKey = null;

		for (int i = 0; i < GPParser.getGameParamsKeySet().size(); i++)
		{
			API_module_id_str = (String) API_JSON.get("module_id_str");

			if (GPParser.getGameParamsKeySet().get(i).contains(API_module_id_str))
			{
				GP_turretKey = GPParser.getGameParamsKeySet().get(i);
			}
		}

		ShipUpgradeInfoJSON = (JSONObject) GPParser.getShipJSON().get("ShipUpgradeInfo");
		JSONObject module = (JSONObject) ShipUpgradeInfoJSON.get(GP_turretKey);
		JSONObject components = (JSONObject) module.get("components");
		JSONArray artillery = (JSONArray) components.get("artillery");

		GP_ArtilleryJSON = (JSONObject) GPParser.getShipJSON().get(artillery.get(artillery.size()-1));

		if (nation.equals("usa"))
		{
			GP_TurretJSON = (JSONObject) GP_ArtilleryJSON.get("HP_AGM_1");
		}
		else if (nation.equals("germany"))
		{
			GP_TurretJSON = (JSONObject) GP_ArtilleryJSON.get("HP_GGM_1");
		}
		else if (nation.equals("japan"))
		{
			GP_TurretJSON = (JSONObject) GP_ArtilleryJSON.get("HP_JGM_1");
		}
		else if (nation.equals("ussr"))
		{
			GP_TurretJSON = (JSONObject) GP_ArtilleryJSON.get("HP_RGM_1");
		}
		else if (nation.equals("uk"))
		{
			GP_TurretJSON = (JSONObject) GP_ArtilleryJSON.get("HP_BGM_1");
		}
		else if (nation.equals("poland"))
		{
			GP_TurretJSON = (JSONObject) GP_ArtilleryJSON.get("HP_WGM_2");
		}
		else if (nation.equals("pan_asia"))
		{
			GP_TurretJSON = (JSONObject) GP_ArtilleryJSON.get("HP_ZGM_1");
		}


	}


}
