
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

	private List<JSONObject> APIJSON = new ArrayList<JSONObject>();
	private JSONObject shipJSON;
	
	private String ship_id_str;
	
	public APIParser(String aShipName) throws FileNotFoundException, IOException, ParseException
	{
		API = (JSONObject) JSONParser.parse(new FileReader(APIFile));
		
		setShipJSON(aShipName);				
	}

	@SuppressWarnings("unchecked")
	private void setShipJSON(String aShipName)
	{				
		APIJSON.addAll(API.values());
			
		for (int i = 0; i < APIJSON.size(); i++)
		{
			if (APIJSON.get(i).containsValue(aShipName))
			{
				shipJSON = APIJSON.get(i);	
				ship_id_str = (String) shipJSON.get("ship_id_str");
				break;
			}	
		}
	}
}
