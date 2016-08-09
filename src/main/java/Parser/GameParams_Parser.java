package Parser;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.GZIPOutputStream;

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
	private File GameParamsFile = new File("src/main/java/GP_JSON/GameParams.json");
	private JSONObject GameParams;
	private HashMap<String, JSONObject> GameParamsIndexHashMap = new HashMap<>();
	private HashMap<String, JSONObject> GameParamsNameHashMap = new HashMap<>();

	private List<String> GameParamsKeySet = new ArrayList<String>();
	private List<JSONObject> GameParamsValues = new ArrayList<>();
	private JSONObject shipJSON;
	
	private String ship_id_str;

	@SuppressWarnings("unchecked")
	public GameParams_Parser() throws IOException, ParseException
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(GameParamsFile),"UTF8"));		
		GameParams = (JSONObject) JSONParser.parse(reader);
		GameParamsKeySet.addAll(GameParams.keySet());

		GameParamsValues.addAll(GameParams.values());

		setGameParamsIndexHashMap();
//		setShipJSON(aShip_id_str);
	}	
	
	private void setGameParamsIndexHashMap()
	{
		JSONObject json;
		for (int i = 0; i < GameParamsKeySet.size(); i++)
		{
			json = (JSONObject) GameParams.get(GameParamsKeySet.get(i));
			GameParamsIndexHashMap.put((String) json.get("index"), json);
			GameParamsNameHashMap.put((String) json.get("name"), json);
		}
	}
	
//	private void setShipJSON(String aShip_id_str)
//	{
//		ship_id_str = aShip_id_str;
//		shipJSON = GameParamsIndexHashMap.get(ship_id_str);
//	}
}
