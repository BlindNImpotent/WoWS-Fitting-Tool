
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import lombok.Data;

/**
 * 
 * @author Aesis (BlindNImpotent)
 *
 */
@Data
public class APIParser 
{
	private JSONParser JSONParser = new JSONParser();
	private File APIFile = new File("API.json");
	private JSONObject API;	

	private List<String> idList = new ArrayList<String>();
	private HashMap<String, JSONObject> APIHashMap = new HashMap<String, JSONObject>();
	private JSONObject shipJSON;
	
	//Inside shipJSON
	private long tier;
	private String description;
	private long price_gold;
	private long ship_id;
	private String name;
	private JSONObject modules;
	private JSONObject modules_tree;
	private String nation;
	private boolean is_premium;
	private String ship_id_str;
	private long price_credit;
	private JSONObject default_profile;
	private JSONArray upgrades;
	private JSONObject images;
	private JSONObject next_ships;
	private String type;
	private long mod_slots;
	
	//Inside modules
	private JSONArray modules_engine;
	private JSONArray modules_artillery;
	private JSONArray modules_fighter;
	private JSONArray modules_hull;
	private JSONArray modules_torpedo_bomber;
	private JSONArray modules_torpedoes;
	private JSONArray modules_fire_control;
	private JSONArray modules_flight_control;
	private JSONArray modules_dive_bomber;
	
	//Inside modules_tree
	private List<String> modules_tree_idList = new ArrayList<String>();
	private HashMap<String, JSONObject> modules_tree_idHashMap = new HashMap<String, JSONObject>();
	private JSONObject modules_tree_id;	
	
	//Inside modules_tree_id
	private String modules_tree_id_name;
	private boolean modules_tree_id_next_modules;
	private boolean modules_tree_id_is_default;
	private long modules_tree_id_price_xp;
	private long modules_tree_id_price_credit;
	private long modules_tree_id_module_id;
	private boolean modules_tree_id_next_ships;
	private String modules_tree_id_type;
	private String modules_tree_id_module_id_str;
	
	//Inside default_profile
	private JSONObject default_profile_engine;
	private JSONObject default_profile_artillery;
	private JSONObject default_profile_anti_aircraft;	
	private JSONObject default_profile_mobility;
	private JSONObject default_profile_hull;
	private JSONObject default_profile_atbas;
	private JSONObject default_profile_torpedo_bomber;
	private JSONObject default_profile_torpedoes;
	private JSONObject default_profile_fire_control;
	private JSONObject default_profile_atba;
	private JSONObject default_profile_fighters;
	private JSONObject default_profile_weaponry;
	private JSONObject default_profile_flight_control;
	private JSONObject default_profile_concealment;
	private JSONObject default_profile_armour;
	private JSONObject default_profile_dive_bomber;	
	
	public APIParser(String aShipName) throws FileNotFoundException, IOException, ParseException
	{
		API = (JSONObject) JSONParser.parse(new FileReader(APIFile));
		
		setShipsHashMap();
		setShipJSON(aShipName);		
		
		tier = (long) shipJSON.get("tier");
		description = (String) shipJSON.get("description");
		price_gold = (long) shipJSON.get("price_gold");
		ship_id = (long) shipJSON.get("ship_id");
		name = (String) shipJSON.get("name");
		modules = (JSONObject) shipJSON.get("modules");
		modules_tree = (JSONObject) shipJSON.get("modules_tree");
		nation = (String) shipJSON.get("nation");
		is_premium = (boolean) shipJSON.get("is_premium");
		ship_id_str = (String) shipJSON.get("ship_id_str");
		price_credit = (long) shipJSON.get("price_credit");
		default_profile = (JSONObject) shipJSON.get("default_profile");
		upgrades = (JSONArray) shipJSON.get("upgrades");
		images = (JSONObject) shipJSON.get("images");
		next_ships = (JSONObject) shipJSON.get("next_ships");
		type = (String) shipJSON.get("type");
		mod_slots = (long) shipJSON.get("mod_slots");
		
		setModules_tree_idHashMap();
		
	}
	
	@SuppressWarnings("unchecked")
	private void setShipsHashMap()
	{
		idList.addAll(API.keySet());
		
		for (int i = 0; i < idList.size(); i++)
		{
			APIHashMap.put(idList.get(i), (JSONObject) API.get(idList.get(i)));
		}
	}
	
	private void setShipJSON(String aShipName)
	{
		List<JSONObject> shipsJSONList = new ArrayList<JSONObject>();
		shipsJSONList.addAll(APIHashMap.values());
				
		for (int i = 0; i < shipsJSONList.size(); i++)
		{			
			if (shipsJSONList.get(i).containsValue(aShipName))
			{
				shipJSON = shipsJSONList.get(i);
				break;
			}			
		}
	}

	@SuppressWarnings("unchecked")
	private void setModules_tree_idHashMap()
	{
		modules_tree_idList.addAll(modules_tree.keySet());
		 
		for (int i = 0; i < modules_tree_idList.size(); i++)
		{
			modules_tree_idHashMap.put(modules_tree_idList.get(i), (JSONObject) modules_tree.get(modules_tree_idList.get(i)));
		}
	}
}
