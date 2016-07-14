import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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
	private GameParamsParser GameParamsParser;
	
	private JSONObject modules_treeJSON;
	private List<String> modules_treeList = new ArrayList<String>();
	private List<JSONObject> default_loadouts = new ArrayList<JSONObject>();
	private List<JSONObject> shipUpgrades = new ArrayList<JSONObject>();
	
	private JSONObject ShipUpgradeInfoJSON;
	private List<String> shipUpgradeInfoList = new ArrayList<String>();

	
	public JSONParser(String aShipName) throws FileNotFoundException, IOException, ParseException
	{
		APIParser = new APIParser(aShipName);
		GameParamsParser = new GameParamsParser(APIParser.getShip_id_str());		
		
		setAPIShipUpgradeInfo();
		setGameParamsShipUpgradeInfo();
	}
	
	@SuppressWarnings("unchecked")
	private void setAPIShipUpgradeInfo()
	{
		modules_treeJSON = APIParser.getModules_tree();
		modules_treeList.addAll(modules_treeJSON.keySet());		
		
		JSONObject mtJSON;
		
		for (int i = 0; i < modules_treeList.size(); i++)
		{
			mtJSON = (JSONObject) APIParser.getModules_tree().get(modules_treeList.get(i));
			
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
	
	private void setGameParamsShipUpgradeInfo()
	{	
		ShipUpgradeInfoJSON = (JSONObject) GameParamsParser.getShipJSON().get("ShipUpgradeInfo");		
		for (int i = 0; i < modules_treeJSON.size(); i++)
		{
			modules_treeJSON.get(modules_treeList.get(i));
		
		}
		
	}
	
	
	
	
}
