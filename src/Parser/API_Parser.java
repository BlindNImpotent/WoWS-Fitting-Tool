package Parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
public class API_Parser 
{
	private JSONParser JSONParser = new JSONParser();
	private File APIFile = new File("API.json");
	private JSONObject APIJSON;	
	private JSONObject APIShipsJSON;
		
	private List<JSONObject> APIShipsJSONList = new ArrayList<JSONObject>();
	private JSONObject shipJSON;
	
	private String ship_id_str;
	
	public API_Parser(String aShipName) throws FileNotFoundException, IOException, ParseException
	{
		APIJSON = (JSONObject) JSONParser.parse(new FileReader(APIFile));
		APIShipsJSON = (JSONObject) APIJSON.get("ships");
		
		setShipJSON(aShipName);	
	}

	@SuppressWarnings("unchecked")
	private void setShipJSON(String aShipName)
	{				
		APIShipsJSONList.addAll(APIShipsJSON.values());
			
		for (int i = 0; i < APIShipsJSONList.size(); i++)
		{
			if (APIShipsJSONList.get(i).containsValue(aShipName))
			{
				shipJSON = APIShipsJSONList.get(i);	
				ship_id_str = (String) shipJSON.get("ship_id_str");
				break;
			}	
		}
	}
}
