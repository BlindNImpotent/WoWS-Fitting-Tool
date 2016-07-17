package Parser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
public class GameParams_Parser 
{
	private JSONParser JSONParser = new JSONParser();
	private File GameParamsFile = new File("GP_JSON/GameParams.json");
	private JSONObject GameParams;
	private HashMap<String, JSONObject> GameParamsHashMap = new HashMap<String, JSONObject>();
	
	private List<String> GameParamsKeySet = new ArrayList<String>();
	private List<JSONObject> GameParamsValues = new ArrayList<>();
	private JSONObject shipJSON;
	
	private String ship_id_str;

	@SuppressWarnings("unchecked")
	public GameParams_Parser(String aShip_id_str) throws FileNotFoundException, IOException, ParseException
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(GameParamsFile),"UTF8"));		
		GameParams = (JSONObject) JSONParser.parse(reader);
		GameParamsKeySet.addAll(GameParams.keySet());
		
		GameParamsValues.addAll(GameParams.values());
		
		setGameParamsHashMap();
		setShipJSON(aShip_id_str);
	}	
	
	private void setGameParamsHashMap()
	{
		for (int i = 0; i < GameParamsKeySet.size(); i++)
		{
			GameParamsHashMap.put(GameParamsKeySet.get(i), (JSONObject) GameParams.get(GameParamsKeySet.get(i)));
		}
	}
	
	private void setShipJSON(String aShip_id_str)	
	{
		for (int i = 0; i < GameParamsKeySet.size(); i++)
		{			
			if (GameParamsKeySet.get(i).contains(aShip_id_str))
			{
				ship_id_str = GameParamsKeySet.get(i);
				shipJSON = (JSONObject) GameParamsHashMap.get(ship_id_str);				
				break;
			}			
		}		
	}	
}
