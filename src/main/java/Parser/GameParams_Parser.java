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
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * 
 * @author Aesis (BlindNImpotent)
 *
 */
@Data
public class GameParams_Parser 
{
	private JSONParser JSONParser;
	private Resource GameParamsFile = new ClassPathResource("static/json/GP_JSON/GameParams.json");
	private JSONObject GameParams;
	private HashMap<String, JSONObject> GameParamsIndexHashMap;
	private HashMap<String, JSONObject> GameParamsNameHashMap;

	private List<String> GameParamsKeySet;
	private List<JSONObject> GameParamsValues;
	private JSONObject shipJSON;
	
	private String ship_id_str;

	@SuppressWarnings("unchecked")
	public GameParams_Parser() throws IOException, ParseException
	{
		JSONParser = new JSONParser();
		GameParamsIndexHashMap = new HashMap<>();
		GameParamsNameHashMap = new HashMap<>();
		GameParamsKeySet = new ArrayList<>();
		GameParamsValues = new ArrayList<>();

		BufferedReader reader = new BufferedReader(new InputStreamReader(GameParamsFile.getInputStream(),"UTF8"));
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
