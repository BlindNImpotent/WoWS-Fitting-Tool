import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
public class GameParamsParser 
{
	private JSONParser JSONParser = new JSONParser();
	private File GameParamsFile = new File("GameParams.json");
	private JSONObject GameParams;
	
	private List<String> ship_id_strList = new ArrayList<String>();
	private HashMap<String, JSONObject> shipsHashMap = new HashMap<String, JSONObject>();
	private JSONObject shipJSON;
	
	private String ship_id_str;

	public GameParamsParser(String aShip_id_str) throws FileNotFoundException, IOException, ParseException
	{
		GameParams = (JSONObject) JSONParser.parse(new FileReader(GameParamsFile));		
		
		setShipsHashMap();
		setShipJSON(aShip_id_str);
	}	
	
	@SuppressWarnings("unchecked")
	private void setShipsHashMap()
	{
		ship_id_strList.addAll(GameParams.keySet());
		
		for (int i = 0; i < ship_id_strList.size(); i++)
		{
			shipsHashMap.put(ship_id_strList.get(i), (JSONObject) GameParams.get(ship_id_strList.get(i)));
		}
	}	
	
	private void setShipJSON(String aShip_id_str)
	{
		for (int i = 0; i < ship_id_strList.size(); i++)
		{
			if (ship_id_strList.get(i).contains(aShip_id_str))
			{
				ship_id_str = ship_id_strList.get(i);
				shipJSON = shipsHashMap.get(ship_id_str);
				break;
			}			
		}		
	}
	

	
	
}
