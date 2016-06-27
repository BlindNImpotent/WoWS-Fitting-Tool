import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSParser 
{
	private JSONParser parser = new JSONParser();
	private File jsonFile = new File("GameParams.json");
	private JSONObject GameParams;
	private JSONObject shipJSON;
	private JSONObject shipModuleJSON;
	private JSONObject type;
	private JSONObject upgrade;	
	private JSONObject engineObj;

	private List<String> shipList = new ArrayList<String>();
	private List<String> shipUpgradeList = new ArrayList<String>();
	private List<String> moduleList = new ArrayList<String>();
	
	private List<String> modSlot1 = new ArrayList<String>();
	private List<String> modSlot2 = new ArrayList<String>();
	private List<String> modSlot3 = new ArrayList<String>();
	private List<String> modSlot4 = new ArrayList<String>();
	private List<String> modSlot5 = new ArrayList<String>();
	private List<String> modSlot6 = new ArrayList<String>();
	
	private List<String> crewList = new ArrayList<String>();
		
	private long tier;
	private String shipCode;	
	private String nation;
	private String shipType;
	private double maxHP;
	private double rudderShift;
	private Object speed;
	
	private double maxMainGunRange;
	private double mainGunRotation;
	private double mainGunReload;
	
	private double torpedoRotation;
	private double torpedoReload;
	private double maxTorpedoRange;
	
	private double sConceal;
	private double aConceal;
	private double maxDistCoef;
	private int moduleSlots;
	private double barrelDiameter;
	private double burnTime;
	private double floodTime;
	private long EngineAutoRepairTime;

	
	private double MainTurretHP;
	private double MainTurretAutoRepairTime;
	
	private double TorpedoHP;
	private double TorpedoAutoRepairTime;
	
	
	
	
	@SuppressWarnings("unchecked")
	public JSParser(String aShip) throws FileNotFoundException, IOException, ParseException
	{
		GameParams = (JSONObject) parser.parse(new FileReader(jsonFile));		

		String ship = aShip;
		
		if (ship.equals("Tirpitz"))
		{
			ship = ship.replaceAll("Tirpitz", "Tirpiz");
		}
		else if (ship.equals("South Carolina"))
		{
			ship = ship.replaceAll("South Carolina", "Michigan");
		}
		else if (ship.equals("Wyoming"))
		{
			ship = ship.replaceAll("Wyoming", "PASB004_Arkansas_1912");
		}
		else if (ship.equals("Arkansas"))
		{
			ship = ship.replaceAll("Arkansas", "PASB013_Arkansas_1912");
		}
		else if (ship.equals("St. Loius"))
		{
			ship = ship.replace("St. Louis", "PASC004_St_Louis_1906");
		}
		
		ship = ship.replaceAll(" ", "_");
		
		setShipList();
		setModuleList();		
		
		setShipCode(ship);
		shipJSON = (JSONObject) GameParams.get(shipCode);
		shipModuleJSON = (JSONObject) shipJSON.get("ShipModernization");
		type = (JSONObject) shipJSON.get("typeinfo");
		upgrade = (JSONObject) shipJSON.get("ShipUpgradeInfo");
		shipUpgradeList.addAll(upgrade.keySet());
		tier = (long) shipJSON.get("level");
		nation = (String) type.get("nation");
		shipType = (String) type.get("species");	
		
		setTurretStats();
		setEngineStats();
		setHullStats();
		setTorpedoStats();
		setModuleUpgradeList();		
		setCrewList();
	}
	
	/**
	 * Sets ship list.
	 */
	@SuppressWarnings("unchecked")
	private void setShipList()
	{
		shipList.addAll(GameParams.keySet());						
		for (int i = shipList.size() - 1; i >= 0; i--)
		{
			if (!shipList.get(i).toString().matches("(PASB).*") 
					&& !shipList.get(i).toString().matches("(PASC).*")
					&& !shipList.get(i).toString().matches("(PASD).*")
					&& !shipList.get(i).toString().matches("(PJSB).*")
					&& !shipList.get(i).toString().matches("(PJSC).*")
					&& !shipList.get(i).toString().matches("(PJSD).*")
					&& !shipList.get(i).toString().matches("(PRSB).*")
					&& !shipList.get(i).toString().matches("(PRSC).*")
					&& !shipList.get(i).toString().matches("(PRSD).*")
					&& !shipList.get(i).toString().matches("(PGSB).*")
					&& !shipList.get(i).toString().matches("(PGSC).*")
					&& !shipList.get(i).toString().matches("(PGSD).*")
					&& !shipList.get(i).toString().matches("(PWSD).*")
					&& !shipList.get(i).toString().matches("(PBSB).*")
					&& !shipList.get(i).toString().matches("(PZSD).*")
					|| shipList.get(i).toString().matches(".*(Halloween)")
					|| shipList.get(i).toString().matches(".*(_FALSE_).*")
					|| shipList.get(i).toString().matches(".*(_False_).*")
					)
			{
				shipList.remove(i);
			}
		}		
	}
	
	/**
	 * Sets ship code from name.
	 * @param shipName
	 */
	private void setShipCode(String aShipName)
	{
		String shipName = aShipName;
		shipName = shipName.substring(0, 1).toUpperCase() + shipName.substring(1);
		
		for (int i = 0; i < shipList.size(); i++)
		{
			if (shipList.get(i).contains(shipName))
			{
				shipCode = shipList.get(i).toString();
			}			
		}
	}
	
	/**
	 * Sets module list.
	 */
	@SuppressWarnings("unchecked")
	private void setModuleList()
	{
		moduleList.addAll(GameParams.keySet());
		for (int i = moduleList.size() - 1; i >= 0; i--)
		{
			if (!moduleList.get(i).toString().matches("(PCM).*"))
			{
				moduleList.remove(i);
			}			
		}
	}
	
	/**
	 * Sets module upgrade list per slot.
	 */
	@SuppressWarnings("unchecked")
	private void setModuleUpgradeList()
	{
		String modernizationSlot = "ModernizationSlot1";
		int number1 = 1;
		int number2 = 0;
				
		while (shipModuleJSON.get(modernizationSlot) != null)
		{			
			JSONObject mods1 = (JSONObject) shipModuleJSON.get(modernizationSlot);
			JSONArray mods2 = (JSONArray) mods1.get("mods");			
			
			switch (number1)
			{
				case 1: modSlot1.addAll(mods2);
						Collections.sort(modSlot1);
				//System.out.println(modSlot1.toString());
						break;
				case 2:	modSlot2.addAll(mods2);
						Collections.sort(modSlot2);
				//System.out.println(modSlot2.toString());
						break;
				case 3:	modSlot3.addAll(mods2);
						Collections.sort(modSlot3);
				//System.out.println(modSlot3.toString());
						break;
				case 4:	modSlot4.addAll(mods2);
						Collections.sort(modSlot4);
				//System.out.println(modSlot4.toString());
						break;
				case 5:	modSlot5.addAll(mods2);
						Collections.sort(modSlot5);
				//System.out.println(modSlot5.toString());
						break;
				case 6:	modSlot6.addAll(mods2);
						Collections.sort(modSlot6);
				//System.out.println(modSlot6.toString());
						break;
				default:
						break;
			}			
			
			number2 = number1 + 1;			
			modernizationSlot = modernizationSlot.replace(Integer.toString(number1), Integer.toString(number2));
			number1++;
		}

	}
	
	/**
	 * Sets crew list.
	 */
	@SuppressWarnings("unchecked")
	private void setCrewList()
	{		
		crewList.addAll(GameParams.keySet());
		for (int i = crewList.size() - 1; i >= 0; i--)
		{
			if (!crewList.get(i).toString().matches("P.*W001_DefaultCrew"))
			{
				crewList.remove(i);
			}			
		}
	}
	
	
	
	
	/**
	 * Returns GameParams data.
	 * @return
	 */
	public JSONObject getGameParams()
	{
		return GameParams;
	}
	
	/**
	 * Returns crew skills data.
	 * @return
	 */
	public List<String> getCrewList()
	{
		return crewList;
	}
	
	/**
	 * Returns tier of ship.
	 * @return tier Tier of ship.
	 */
	public long getTier()
	{
		return tier;
	}
	
	/**
	 * Returns nation of ship.
	 * @return nation Nation of ship.
	 */
	public String getNation()
	{
		return nation;				
	}
		
	/**
	 * Returns type of ship.
	 * @return shipType Type of ship.
	 */
	public String getShipType()
	{
		return shipType;
	}
	
	/**
	 * Returns maximum possible health.
	 * @return maxHP Maximum possible health.
	 */
	public double getHealth()
	{
		return maxHP;
	}
	
	/**
	 * Returns speed of ship.
	 * @return speed Speed of ship.
	 */
	public Object getSpeed()
	{
		return speed;
	}
	
	/**
	 * Returns rudder shift time.
	 * @return rudderShift Rudder shift time.
	 */
	public double getRudderShift()
	{
		return rudderShift;
	}
	
	/**
	 * Returns range of turret.
	 * @return distance Range of turret.
	 */
	public double getMaxMainGunRange()
	{
		return maxMainGunRange;
	}
	
	/**
	 * Returns rotation of turret.
	 * @return rotation Rotation of turret.
	 */
	public double getMainGunRotation()
	{
		return mainGunRotation;
	}
	
	/**
	 * Returns reload time of turret.
	 * @return reload Reload time of turret.
	 */
	public double getMainGunReload()
	{
		return mainGunReload;
	}
	
	/**
	 * Returns rotation time of torpedo.
	 * @return
	 */
	public double getTorpedoRotation()
	{
		return torpedoRotation;
	}
	
	/**
	 * Returns reload time of torpedo.
	 * @return
	 */
	public double getTorpedoReload()
	{
		return torpedoReload;
	}
	
	/**
	 * Returns max torpedo range.
	 * @return
	 */
	public double getMaxTorpedoRange()
	{
		return maxTorpedoRange;
	}
	
	/**
	 * Returns surface concealment.
	 * @return sConceal Surface concealment.
	 */
	public double getSConceal()
	{
		return sConceal;
	}
	
	/**
	 * Returns air concealment.
	 * @return aConceal Air concealment.
	 */
	public double getAConceal()
	{
		return aConceal;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getModuleSlots()
	{
		return moduleSlots;
	}	
	
	/**
	 * 
	 * @return
	 */
	public double getBarrelDiameter()
	{
		return barrelDiameter;
	}
	
	/**
	 * 
	 * @return
	 */
	public JSONObject getEngine()
	{
		return engineObj;
	}
	
	public double getBurnTime()
	{
		return burnTime;
	}
	
	public double getFloodTime()
	{
		return floodTime;
	}
	
	/**
	 * Returns engine repair time.
	 * @return
	 */
	public long getEngineAutoRepairTime()
	{
		return EngineAutoRepairTime;
	}
	
	public double getMainTurretHP()
	{
		return MainTurretHP;
	}
	
	public double getMainTurretAutoRepairTime()
	{
		return MainTurretAutoRepairTime;
	}
	
	public double getTorpedoHP()
	{
		return TorpedoHP;
	}
	
	public double getTorpedoAutoRepairTime()
	{
		return TorpedoAutoRepairTime;
	}
	
	
	public List<String> getModule1()
	{
		return modSlot1;
	}	
	public List<String> getModule2()
	{
		return modSlot2;
	}
	public List<String> getModule3()
	{
		return modSlot3;
	}
	public List<String> getModule4()
	{
		return modSlot4;
	}
	public List<String> getModule5()
	{
		return modSlot5;
	}
	public List<String> getModule6()
	{
		return modSlot6;
	}
	
	
	
	/**
	 * Returns list of upgrades.
	 * @return upgradeList List of upgrades.
	 */
	private List<String> shipUpgradeList()
	{
		return shipUpgradeList;		
	}
	
	/**
	 * Sets turret related stats.
	 */
	private void setTurretStats()
	{		
		List<String> turret = new ArrayList<String>(); 
		
		//Adds upgrades with specific (eg. PAUA) substring to turret list.
		for (String string : shipUpgradeList()) 
	    {
			if(string.matches("(?i)(P).*(UA).*"))
	        {
				turret.add(string);
	        }
	    }
		
		if (turret.size() == 0)
		{
			return;
		}
		//Sorts by descending order.
		Collections.sort(turret);
		
		Map<String, JSONObject> turret1 = new HashMap<>();
		
		//Adds JSONObject of specific turret upgrade to Map.
		for(int i = 0; i < turret.size(); i++)
		{
			turret1.put(turret.get(i), (JSONObject) upgrade.get(turret.get(i)));			
		}
		
		JSONArray turret2;
		JSONObject turret3;
		
		//Sets "components" JSONObject to turret3.
		turret3 = (JSONObject) turret1.get(turret.get(turret.size()-1)).get("components");
		//Sets "artillery" JSONArray to turret2.
		turret2 = (JSONArray) turret3.get("artillery");
		
		JSONObject tobj;
		JSONObject tobj2;
		JSONArray tobj3;
		
		//Sets the JSONObject of best turret upgrade to tobj.
		tobj = (JSONObject) shipJSON.get(turret2.get(0));
		
		//If nation is Germany, sets tobj2 with HP_GGM_1 JSONObject.
		if (getNation().equals("Germany"))
		{
			tobj2 = (JSONObject) tobj.get("HP_GGM_1");			
		}
		//Else if nation is USA, sets tobj2 with HP_AGM_1 JSONObject.
		else if (getNation().equals("USA"))
		{
			tobj2 = (JSONObject) tobj.get("HP_AGM_1");
		}
		//Else nation is Japan, sets tobj2 with HP_JGM_1 JSONObject.
		else if (getNation().equals("Japan"))
		{
			tobj2 = (JSONObject) tobj.get("HP_JGM_1");
		}
		else if (getNation().equals("Russia"))
		{
			tobj2 = (JSONObject) tobj.get("HP_RGM_1");
		}
		else if (getNation().equals("Pan_Asia"))
		{
			tobj2 = (JSONObject) tobj.get("HP_ZGM_1");
		}
		else if (getNation().equals("Poland"))
		{
			tobj2 = (JSONObject) tobj.get("HP_WGM_1");
		}
		else //if (getNation().equals("United_Kingdom"))
		{
			tobj2 = (JSONObject) tobj.get("HP_BGM_1");
		}
		
		barrelDiameter = (double) tobj2.get("barrelDiameter");
		
		tobj3 = (JSONArray) tobj2.get("rotationSpeed");
		//Sets horizontal rotation of turret.
		mainGunRotation = (double) tobj3.get(0);
		//Sets reload time of turret.
		mainGunReload = (double) tobj2.get("shotDelay");

		JSONObject HitLocationArtillery = (JSONObject) tobj2.get("HitLocationArtillery");
		
		MainTurretHP = (double) HitLocationArtillery.get("maxHP");
		MainTurretAutoRepairTime = (long) HitLocationArtillery.get("autoRepairTime");
		
		/**
		 * 
		 */
		List<String> radar = new ArrayList<String>(); 
		
		//Adds specific string (eg. PAUS) to radar list.
		for (String string : shipUpgradeList()) 
	    {
			if(string.matches("(?i)(P).*(US).*"))
			{				
				radar.add(string);
			}
	    }
		
		//If radar list is single then there're no upgrades.
		if (radar.size() == 1)
		{
			maxMainGunRange = (double) tobj.get("maxDist");
		}
		else
		{
			String radar2 = radar.get(1);
			JSONObject r2 = (JSONObject) upgrade.get(radar2);
			JSONObject r3 = (JSONObject) r2.get("components");
			JSONArray r4 = (JSONArray) r3.get("fireControl");			
			JSONObject jso4 = (JSONObject) shipJSON.get(r4.get(0));
			maxDistCoef = (double) jso4.get("maxDistCoef");
			
			maxMainGunRange = (double) tobj.get("maxDist") * maxDistCoef;
		}
	}
	
	/**
	 * Sets engine related stats.
	 */
	private void setEngineStats()
	{
		List<String> engine = new ArrayList<String>(); 
		for (String string : shipUpgradeList()) 
	    {
			if(string.matches("(?i)(P).*(UE).*"))
	        {
				engine.add(string);
	        }
	    }
		//Sorts by descending order.
		Collections.sort(engine);
		
		Map<String, JSONObject> engine1 = new HashMap<>();
		for(int i = 0; i < engine.size(); i++)
		{
			engine1.put(engine.get(i), (JSONObject) upgrade.get(engine.get(i)));			
		}		
		
		JSONObject engine2;
		String engine3;
		String engine4 = null;
		
		for (int i = 0; i < engine.size(); i++)
		{
			engine2 = engine1.get(engine.get(i).toString());
			engine3 = (String) engine2.get("prev");
			if (engine3.equals(""))
			{
				engine4 = engine.get(i).toString();
			}
			break;
		}
		if (engine1.size() > 1)
		{
			engine1.remove(engine4);
		}

		JSONArray engine5;
		JSONObject engine6;
		
		engine6 = (JSONObject) engine1.get(engine.get(engine.size()-1)).get("components");
		engine5 = (JSONArray) engine6.get("engine");
		
		engineObj = (JSONObject) shipJSON.get(engine5.get(0));
		JSONObject HitLocationEngine = (JSONObject) engineObj.get("HitLocationEngine");
		EngineAutoRepairTime = (long) HitLocationEngine.get("autoRepairTimeMin");
	}
	
	/**
	 * Sets hull related stats.
	 */
	private void setHullStats()
	{
		List<String> hull = new ArrayList<String>(); 
		
		for (String string : shipUpgradeList()) 
	    {
			if(string.matches("(?i)(P).*(UH).*"))
			{				
				hull.add(string);
			}
	    }
		Collections.sort(hull);

		Map<String, JSONObject> hull1 = new HashMap<>();
		
		for(int i = 0; i < hull.size(); i++)
		{
			hull1.put(hull.get(i), (JSONObject) upgrade.get(hull.get(i)));			
		}		

		JSONArray hull2;
		JSONObject hull3;
		hull3 = (JSONObject) hull1.get(hull.get(hull.size()-1)).get("components");
		hull2 = (JSONArray) hull3.get("hull");
		
		JSONObject hpobj;
		hpobj = (JSONObject) shipJSON.get(hull2.get(0));
		maxHP = (double) hpobj.get("health");
		rudderShift = (double) hpobj.get("rudderTime");
		speed = hpobj.get("maxSpeed");		
		sConceal = (double) hpobj.get("visibilityFactor");
		aConceal = (double) hpobj.get("visibilityFactorByPlane");
		
		//Burn time
		JSONArray burnNodes1 = (JSONArray) hpobj.get("burnNodes");
		JSONArray burnNodes2 = (JSONArray) burnNodes1.get(0);		
		burnTime = (double) burnNodes2.get(3);
		
		//Flood time
		JSONArray floodParams = (JSONArray) hpobj.get("floodParams");
		floodTime = (double) floodParams.get(2);
		
		/**
		 * Set upgrade slot numbers.
		 */
		JSONObject mSlots = (JSONObject) shipJSON.get("ShipModernization");
		moduleSlots = mSlots.size();		
	}		

	private void setTorpedoStats()
	{
		List<String> torpedo = new ArrayList<String>(); 
		for (String string : shipUpgradeList()) 
	    {
			if(string.matches("(?i)(P).*(UT).*"))
	        {
				torpedo.add(string);
	        }
	    }
		
		if (torpedo.size() == 0)
		{
			return;
		}
		//Sorts by descending order.
		Collections.sort(torpedo);
		
		Map<String, JSONObject> torp1 = new HashMap<>();
		
		for(int i = 0; i < torpedo.size(); i++)
		{
			torp1.put(torpedo.get(i), (JSONObject) upgrade.get(torpedo.get(i)));			
		}		

		JSONArray torp2;
		JSONObject torp3;
		torp3 = (JSONObject) torp1.get(torpedo.get(torpedo.size()-1)).get("components");
		torp2 = (JSONArray) torp3.get("torpedoes");
		
		JSONObject tobj;
		JSONObject tobj2;
		JSONArray tobj3;
		
		tobj = (JSONObject) shipJSON.get(torp2.get(0));
		
		//If nation is Germany, sets tobj2 with HP_GGM_1 JSONObject.
		if (getNation().equals("Germany"))
		{
			tobj2 = (JSONObject) tobj.get("HP_GGT_1");			
		}
		//Else if nation is USA, sets tobj2 with HP_AGM_1 JSONObject.
		else if (getNation().equals("USA"))
		{
			tobj2 = (JSONObject) tobj.get("HP_AGT_1");
		}
		//Else nation is Japan, sets tobj2 with HP_JGM_1 JSONObject.
		else if (getNation().equals("Japan"))
		{
			tobj2 = (JSONObject) tobj.get("HP_JGT_1");
		}
		else if (getNation().equals("Russia"))
		{
			tobj2 = (JSONObject) tobj.get("HP_RGT_1");
		}
		else if (getNation().equals("Pan_Asia"))
		{
			tobj2 = (JSONObject) tobj.get("HP_ZGT_1");
		}
		else if (getNation().equals("Poland"))
		{
			tobj2 = (JSONObject) tobj.get("HP_WGT_1");
		}
		else //if (getNation().equals("United_Kingdom"))
		{
			tobj2 = (JSONObject) tobj.get("HP_BGT_1");
		}
		
		tobj3 = (JSONArray) tobj2.get("rotationSpeed");
		
		torpedoRotation = (double) tobj3.get(0);
		torpedoReload = (double) tobj2.get("shotDelay");
		
		JSONObject HitLocationTorpedo = (JSONObject) tobj2.get("HitLocationTorpedo");
		
		MainTurretHP = (double) HitLocationTorpedo.get("maxHP");
		MainTurretAutoRepairTime = (long) HitLocationTorpedo.get("autoRepairTime");
		
		JSONArray ammoList = (JSONArray) tobj2.get("ammoList");
		String ammo = (String) ammoList.get(0);
		
		JSONObject ammoObj = (JSONObject) GameParams.get(ammo);
		maxTorpedoRange = (double) ammoObj.get("maxDist") * 30;		
	}





}
