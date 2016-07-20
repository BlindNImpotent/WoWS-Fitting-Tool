package Parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import lombok.Data;

@Data
public class API_ShipNameList 
{
	private API_Parser APIParser;
	
	private List<JSONObject> USA_AirCarrierJSONList = new ArrayList<>();
	private List<JSONObject> USA_BattleshipJSONList = new ArrayList<>();
	private List<JSONObject> USA_CruiserJSONList = new ArrayList<>();
	private List<JSONObject> USA_DestroyerJSONList = new ArrayList<>();
	private List<JSONObject> USA_PremiumJSONList = new ArrayList<>();
	
	private List<JSONObject> Japan_AirCarrierJSONList = new ArrayList<>();
	private List<JSONObject> Japan_BattleshipJSONList = new ArrayList<>();
	private List<JSONObject> Japan_CruiserJSONList = new ArrayList<>();
	private List<JSONObject> Japan_DestroyerJSONList = new ArrayList<>();
	private List<JSONObject> Japan_PremiumJSONList = new ArrayList<>();
	
	private List<JSONObject> ARP_AirCarrierJSONList = new ArrayList<>();
	private List<JSONObject> ARP_BattleshipJSONList = new ArrayList<>();
	private List<JSONObject> ARP_CruiserJSONList = new ArrayList<>();
	private List<JSONObject> ARP_DestroyerJSONList = new ArrayList<>();
	private List<JSONObject> ARP_PremiumJSONList = new ArrayList<>();
	
	private List<JSONObject> USSR_AirCarrierJSONList = new ArrayList<>();
	private List<JSONObject> USSR_BattleshipJSONList = new ArrayList<>();
	private List<JSONObject> USSR_CruiserJSONList = new ArrayList<>();
	private List<JSONObject> USSR_DestroyerJSONList = new ArrayList<>();
	private List<JSONObject> USSR_PremiumJSONList = new ArrayList<>();
	
	private List<JSONObject> Germany_AirCarrierJSONList = new ArrayList<>();
	private List<JSONObject> Germany_BattleshipJSONList = new ArrayList<>();
	private List<JSONObject> Germany_CruiserJSONList = new ArrayList<>();
	private List<JSONObject> Germany_DestroyerJSONList = new ArrayList<>();
	private List<JSONObject> Germany_PremiumJSONList = new ArrayList<>();
	
	private List<JSONObject> France_AirCarrierJSONList = new ArrayList<>();
	private List<JSONObject> France_BattleshipJSONList = new ArrayList<>();
	private List<JSONObject> France_CruiserJSONList = new ArrayList<>();
	private List<JSONObject> France_DestroyerJSONList = new ArrayList<>();
	private List<JSONObject> France_PremiumJSONList = new ArrayList<>();
	
	private List<JSONObject> UK_AirCarrierJSONList = new ArrayList<>();
	private List<JSONObject> UK_BattleshipJSONList = new ArrayList<>();
	private List<JSONObject> UK_CruiserJSONList = new ArrayList<>();
	private List<JSONObject> UK_DestroyerJSONList = new ArrayList<>();
	private List<JSONObject> UK_PremiumJSONList = new ArrayList<>();
	
	private List<JSONObject> Poland_DestroyerJSONList = new ArrayList<>();
	private List<JSONObject> Poland_PremiumJSONList = new ArrayList<>();
	
	private List<JSONObject> Pan_Asia_DestroyerJSONList = new ArrayList<>();
	private List<JSONObject> Pan_Asia_PremiumJSONList = new ArrayList<>();
	
	private List<String> USA_AirCarrierNameList = new ArrayList<>();
	private List<String> USA_BattleshipNameList = new ArrayList<>();
	private List<String> USA_CruiserNameList = new ArrayList<>();
	private List<String> USA_DestroyerNameList = new ArrayList<>();
	private List<String> USA_PremiumNameList = new ArrayList<>();
	
	private List<String> Japan_AirCarrierNameList = new ArrayList<>();
	private List<String> Japan_BattleshipNameList = new ArrayList<>();
	private List<String> Japan_CruiserNameList = new ArrayList<>();
	private List<String> Japan_DestroyerNameList = new ArrayList<>();
	private List<String> Japan_PremiumNameList = new ArrayList<>();
	private List<String> Japan_ARPNameList = new ArrayList<>();
	
	private List<String> ARP_AirCarrierNameList = new ArrayList<>();
	private List<String> ARP_BattleshipNameList = new ArrayList<>();
	private List<String> ARP_CruiserNameList = new ArrayList<>();
	private List<String> ARP_DestroyerNameList = new ArrayList<>();
	private List<String> ARP_PremiumNameList = new ArrayList<>();
	private List<String> ARP_ARPNameList = new ArrayList<>();
	
	private List<String> USSR_AirCarrierNameList = new ArrayList<>();
	private List<String> USSR_BattleshipNameList = new ArrayList<>();
	private List<String> USSR_CruiserNameList = new ArrayList<>();
	private List<String> USSR_DestroyerNameList = new ArrayList<>();
	private List<String> USSR_PremiumNameList = new ArrayList<>();
		
	private List<String> Germany_AirCarrierNameList = new ArrayList<>();
	private List<String> Germany_BattleshipNameList = new ArrayList<>();
	private List<String> Germany_CruiserNameList = new ArrayList<>();
	private List<String> Germany_DestroyerNameList = new ArrayList<>();
	private List<String> Germany_PremiumNameList = new ArrayList<>();
	
	private List<String> France_AirCarrierNameList = new ArrayList<>();
	private List<String> France_BattleshipNameList = new ArrayList<>();
	private List<String> France_CruiserNameList = new ArrayList<>();
	private List<String> France_DestroyerNameList = new ArrayList<>();
	private List<String> France_PremiumNameList = new ArrayList<>();
	
	private List<String> UK_AirCarrierNameList = new ArrayList<>();
	private List<String> UK_BattleshipNameList = new ArrayList<>();
	private List<String> UK_CruiserNameList = new ArrayList<>();
	private List<String> UK_DestroyerNameList = new ArrayList<>();
	private List<String> UK_PremiumNameList = new ArrayList<>();
	
	private List<String> Poland_DestroyerNameList = new ArrayList<>();
	private List<String> Poland_PremiumNameList = new ArrayList<>();
	
	private List<String> Pan_Asia_DestroyerNameList = new ArrayList<>();
	private List<String> Pan_Asia_PremiumNameList = new ArrayList<>();

	public API_ShipNameList() throws IOException, ParseException
	{
		APIParser = new API_Parser();
		
		setShipsJSONAndName();
	}	
	
	private void setShipsJSONAndName()
	{
		List<JSONObject> APIJSONList = (List<JSONObject>) APIParser.getAPIShipsJSONList();
		JSONObject APIJSON;
		
		for (int i = 0; i < APIParser.getAPIShipsJSONList().size(); i++)
		{	
			APIJSON = (JSONObject) APIJSONList.get(i);
			
			if (APIJSON.get("nation").equals("usa"))
			{
				if ((boolean) APIJSON.get("is_premium") != true)
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
				else
				{
					USA_PremiumJSONList.add(APIJSON);
				}
			}
			else if (APIJSON.get("nation").equals("japan"))
			{
				if ((boolean) APIJSON.get("is_premium") != true)
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
				else
				{
					Japan_PremiumJSONList.add(APIJSON);
				}
			}
			else if (APIJSON.get("nation").equals("ussr"))
			{
				if ((boolean) APIJSON.get("is_premium") != true)
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
				else
				{
					USSR_PremiumJSONList.add(APIJSON);
				}
			}
			else if (APIJSON.get("nation").equals("germany"))
			{
				if ((boolean) APIJSON.get("is_premium") != true)
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
				else
				{
					Germany_PremiumJSONList.add(APIJSON);
				}
			}
			else if (APIJSON.get("nation").equals("uk"))
			{
				if ((boolean) APIJSON.get("is_premium") != true)
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
				else
				{
					UK_PremiumJSONList.add(APIJSON);
				}
			}
			else if (APIJSON.get("nation").equals("france"))
			{
				if ((boolean) APIJSON.get("is_premium") != true)
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
				else
				{
					France_PremiumJSONList.add(APIJSON);
				}
			}
			else if (APIJSON.get("nation").equals("poland"))
			{
				if ((boolean) APIJSON.get("is_premium") != true)
				{				
					if (APIJSON.get("type").equals("Destroyer"))
					{
						Poland_DestroyerJSONList.add(APIJSON);
					}
				}
				else
				{
					Poland_PremiumJSONList.add(APIJSON);
				}
			}
			else if (APIJSON.get("nation").equals("pan_asia"))
			{
				if ((boolean) APIJSON.get("is_premium") != true)
				{	
					if (APIJSON.get("type").equals("Destroyer"))
					{
						Pan_Asia_DestroyerJSONList.add(APIJSON);
					}
				}
				else
				{
					Pan_Asia_PremiumJSONList.add(APIJSON);
				}
			}			
		}
		
		//USA
		USA_AirCarrierJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		USA_BattleshipJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		USA_CruiserJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		USA_DestroyerJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));				
		USA_PremiumJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));				
		
		USA_AirCarrierJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo((long) o2.get("tier"))));
		USA_BattleshipJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo((long) o2.get("tier"))));
		USA_CruiserJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo((long) o2.get("tier"))));
		USA_DestroyerJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo((long) o2.get("tier"))));
		USA_PremiumJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo((long) o2.get("tier"))));
		
		USA_AirCarrierJSONList.forEach((USA_AirCarrier -> USA_AirCarrierNameList.add((String) USA_AirCarrier.get("name"))));
		USA_BattleshipJSONList.forEach((USA_Battleship -> USA_BattleshipNameList.add((String) USA_Battleship.get("name"))));
		USA_CruiserJSONList.forEach((USA_Cruiser -> USA_CruiserNameList.add((String) USA_Cruiser.get("name"))));
		USA_DestroyerJSONList.forEach((USA_Destroyer -> USA_DestroyerNameList.add((String) USA_Destroyer.get("name"))));
		USA_PremiumJSONList.forEach((USA_Premium -> USA_PremiumNameList.add((String) USA_Premium.get("name"))));
		
		//USSR
		USSR_AirCarrierJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		USSR_BattleshipJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		USSR_CruiserJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		USSR_DestroyerJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));				
		USSR_PremiumJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));				
		
		USSR_AirCarrierJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo((long) o2.get("tier"))));
		USSR_BattleshipJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo((long) o2.get("tier"))));
		USSR_CruiserJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo((long) o2.get("tier"))));
		USSR_DestroyerJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo((long) o2.get("tier"))));
		USSR_PremiumJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo((long) o2.get("tier"))));
		
		USSR_AirCarrierJSONList.forEach((USSR_AirCarrier -> USSR_AirCarrierNameList.add((String) USSR_AirCarrier.get("name"))));
		USSR_BattleshipJSONList.forEach((USSR_Battleship -> USSR_BattleshipNameList.add((String) USSR_Battleship.get("name"))));
		USSR_CruiserJSONList.forEach((USSR_Cruiser -> USSR_CruiserNameList.add((String) USSR_Cruiser.get("name"))));
		USSR_DestroyerJSONList.forEach((USSR_Destroyer -> USSR_DestroyerNameList.add((String) USSR_Destroyer.get("name"))));
		USSR_PremiumJSONList.forEach((USSR_Premium -> USSR_PremiumNameList.add((String) USSR_Premium.get("name"))));
		
		//Japan
		Japan_AirCarrierJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		Japan_BattleshipJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		Japan_CruiserJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		Japan_DestroyerJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));				
		Japan_PremiumJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));				
		
		Japan_AirCarrierJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo((long) o2.get("tier"))));
		Japan_BattleshipJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo((long) o2.get("tier"))));
		Japan_CruiserJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo((long) o2.get("tier"))));
		Japan_DestroyerJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo((long) o2.get("tier"))));
		Japan_PremiumJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo((long) o2.get("tier"))));
		
		Japan_AirCarrierJSONList.forEach((Japan_AirCarrier -> Japan_AirCarrierNameList.add((String) Japan_AirCarrier.get("name"))));
		Japan_BattleshipJSONList.forEach((Japan_Battleship -> Japan_BattleshipNameList.add((String) Japan_Battleship.get("name"))));
		Japan_CruiserJSONList.forEach((Japan_Cruiser -> Japan_CruiserNameList.add((String) Japan_Cruiser.get("name"))));
		Japan_DestroyerJSONList.forEach((Japan_Destroyer -> Japan_DestroyerNameList.add((String) Japan_Destroyer.get("name"))));
		Japan_PremiumJSONList.forEach((Japan_Premium -> Japan_PremiumNameList.add((String) Japan_Premium.get("name"))));

		//ARP
		ARP_AirCarrierJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		ARP_BattleshipJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		ARP_CruiserJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		ARP_DestroyerJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));				
		ARP_PremiumJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));				
		
		ARP_AirCarrierJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo((long) o2.get("tier"))));
		ARP_BattleshipJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo((long) o2.get("tier"))));
		ARP_CruiserJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo((long) o2.get("tier"))));
		ARP_DestroyerJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo((long) o2.get("tier"))));
		ARP_PremiumJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo((long) o2.get("tier"))));
		
		ARP_AirCarrierJSONList.forEach((ARP_AirCarrier -> ARP_AirCarrierNameList.add((String) ARP_AirCarrier.get("name"))));
		ARP_BattleshipJSONList.forEach((ARP_Battleship -> ARP_BattleshipNameList.add((String) ARP_Battleship.get("name"))));
		ARP_CruiserJSONList.forEach((ARP_Cruiser -> ARP_CruiserNameList.add((String) ARP_Cruiser.get("name"))));
		ARP_DestroyerJSONList.forEach((ARP_Destroyer -> ARP_DestroyerNameList.add((String) ARP_Destroyer.get("name"))));
		ARP_PremiumJSONList.forEach((ARP_Premium -> ARP_PremiumNameList.add((String) ARP_Premium.get("name"))));
		
		//Germany
		Germany_AirCarrierJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		Germany_BattleshipJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		Germany_CruiserJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		Germany_DestroyerJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));				
		Germany_PremiumJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));				
		
		Germany_AirCarrierJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo((long) o2.get("tier"))));
		Germany_BattleshipJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo((long) o2.get("tier"))));
		Germany_CruiserJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo((long) o2.get("tier"))));
		Germany_DestroyerJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo((long) o2.get("tier"))));
		Germany_PremiumJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo((long) o2.get("tier"))));
		
		Germany_AirCarrierJSONList.forEach((Germany_AirCarrier -> Germany_AirCarrierNameList.add((String) Germany_AirCarrier.get("name"))));
		Germany_BattleshipJSONList.forEach((Germany_Battleship -> Germany_BattleshipNameList.add((String) Germany_Battleship.get("name"))));
		Germany_CruiserJSONList.forEach((Germany_Cruiser -> Germany_CruiserNameList.add((String) Germany_Cruiser.get("name"))));
		Germany_DestroyerJSONList.forEach((Germany_Destroyer -> Germany_DestroyerNameList.add((String) Germany_Destroyer.get("name"))));
		Germany_PremiumJSONList.forEach((Germany_Premium -> Germany_PremiumNameList.add((String) Germany_Premium.get("name"))));
				
		//France
		France_AirCarrierJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		France_BattleshipJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		France_CruiserJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		France_DestroyerJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));				
		France_PremiumJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));				
		
		France_AirCarrierJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo((long) o2.get("tier"))));
		France_BattleshipJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo((long) o2.get("tier"))));
		France_CruiserJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo((long) o2.get("tier"))));
		France_DestroyerJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo((long) o2.get("tier"))));
		France_PremiumJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo((long) o2.get("tier"))));
		
		France_AirCarrierJSONList.forEach((France_AirCarrier -> France_AirCarrierNameList.add((String) France_AirCarrier.get("name"))));
		France_BattleshipJSONList.forEach((France_Battleship -> France_BattleshipNameList.add((String) France_Battleship.get("name"))));
		France_CruiserJSONList.forEach((France_Cruiser -> France_CruiserNameList.add((String) France_Cruiser.get("name"))));
		France_DestroyerJSONList.forEach((France_Destroyer -> France_DestroyerNameList.add((String) France_Destroyer.get("name"))));
		France_PremiumJSONList.forEach((France_Premium -> France_PremiumNameList.add((String) France_Premium.get("name"))));

		//UK
		UK_AirCarrierJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		UK_BattleshipJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		UK_CruiserJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));
		UK_DestroyerJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));				
		UK_PremiumJSONList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));				
		
		UK_AirCarrierJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo((long) o2.get("tier"))));
		UK_BattleshipJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo((long) o2.get("tier"))));
		UK_CruiserJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo((long) o2.get("tier"))));
		UK_DestroyerJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo((long) o2.get("tier"))));
		UK_PremiumJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo((long) o2.get("tier"))));
		
		UK_AirCarrierJSONList.forEach((UK_AirCarrier -> UK_AirCarrierNameList.add((String) UK_AirCarrier.get("name"))));
		UK_BattleshipJSONList.forEach((UK_Battleship -> UK_BattleshipNameList.add((String) UK_Battleship.get("name"))));
		UK_CruiserJSONList.forEach((UK_Cruiser -> UK_CruiserNameList.add((String) UK_Cruiser.get("name"))));
		UK_DestroyerJSONList.forEach((UK_Destroyer -> UK_DestroyerNameList.add((String) UK_Destroyer.get("name"))));
		UK_PremiumJSONList.forEach((UK_Premium -> UK_PremiumNameList.add((String) UK_Premium.get("name"))));
		
		//Poland
		Poland_DestroyerJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		Poland_PremiumJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		Poland_DestroyerJSONList.forEach((Poland_Destroyer -> Poland_DestroyerNameList.add((String) Poland_Destroyer.get("name"))));
		Poland_PremiumJSONList.forEach((Poland_Premium -> Poland_PremiumNameList.add((String) Poland_Premium.get("name"))));

		//Pan_Asia
		Pan_Asia_DestroyerJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		Pan_Asia_PremiumJSONList.sort((o1, o2) -> (Long.valueOf((long) o1.get("tier")).compareTo(Long.valueOf((long) o2.get("tier")))));
		Pan_Asia_DestroyerJSONList.forEach((Pan_Asia_Destroyer -> Pan_Asia_DestroyerNameList.add((String) Pan_Asia_Destroyer.get("name"))));
		Pan_Asia_PremiumJSONList.forEach((Pan_Asia_Premium -> Pan_Asia_PremiumNameList.add((String) Pan_Asia_Premium.get("name"))));
	}
	
	
	
}
