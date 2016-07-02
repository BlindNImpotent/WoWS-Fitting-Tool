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

/**
 * @author Aesis (BlindNImpotent)
 * Parses data in GameParams.json. 
 */
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
	
	private List<String> flagList = new ArrayList<String>();
		
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
	private double mainGunDispersionTangent;	
	private double APShellSpeed;
	private double APShellDMG;
	private double HEShellSpeed;
	private double HEShellDMG;
	private double HEShellBurnProb;
	
	private double secondaryMaxDist;
	
	private double torpedoRotation;
	private double torpedoReload;
	private double maxTorpedoRange;
	private double torpedoSpeed;
	
	private double sConceal;
	private double aConceal;
	private double stealthFireSurfaceDetection;
	private double AAFireAirDetection;
	
	private double maxDistCoef;
	private int moduleSlots;
	private double barrelDiameter;
	private Object numBarrels;
	private int numTurrets;
	private double burnTime;
	private double floodTime;
	private long EngineAutoRepairTime;
	private double speedCoef;
	
	private double MainTurretHP;
	private double MainTurretAutoRepairTime;
	
	private double TorpedoHP;
	private double TorpedoAutoRepairTime;
	
	private double antiAirAuraDistanceFar;
	private double antiAirAuraDistanceMedium;
	private double antiAirAuraDistanceNear;
	
	private double AAFarDPS;
	private double AAMediumDPS;
	private double AANearDPS;
	
	private List<String> turretList = new ArrayList<String>(); 
	private List<String> torpedoList = new ArrayList<String>();
	private List<String> radarList = new ArrayList<String>();
	private List<String> engineList = new ArrayList<String>(); 
	private List<String> hullList = new ArrayList<String>(); 
	
	private List<JSONArray> Abil0 = new ArrayList<JSONArray>();
	private List<JSONArray> Abil1 = new ArrayList<JSONArray>();
	private List<JSONArray> Abil2 = new ArrayList<JSONArray>();
	private List<JSONArray> Abil3 = new ArrayList<JSONArray>();
	
	private List<String> Ability0 = new ArrayList<String>();
	private List<String> Ability1 = new ArrayList<String>();
	private List<String> Ability2 = new ArrayList<String>();
	private List<String> Ability3 = new ArrayList<String>();
	
	private JSONObject tobj2;
	private JSONObject tobj;
	
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
			ship = ship.replaceAll("South Carolina", "PASB001_Michigan_1916");
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
		else if (ship.equals("Nicholas"))
		{
			ship = ship.replace("Nicholas", "Leader");
		}
		else if (ship.equals("Tenryu"))
		{
			ship = ship.replace("Tenryu", "Tatsuta");
		}
		else if (ship.equals("Aurora"))
		{
			ship = ship.replace("Aurora", "Avrora");
		}	
		else if (ship.equals("Minekaze"))
		{
			ship = ship.replace("Minekaze", "Minekadze");
		}
		else if (ship.equals("Marblehead L"))
		{
			ship = ship.replace("Marblehead L", "PASC045_Marblehead_1924_Asus");
		}
		else if (ship.equals("Fujin"))
		{
			ship = ship.replace("Fujin", "PJSD017_Kamikaze_1930");
		}
		else if (ship.equals("Kamikaze"))
		{
			ship = ship.replace("Kamikaze", "PJSD025_True_Kamikaze");
		}
		else if (ship.equals("Kamikaze R"))
		{
			ship = ship.replace("Kamikaze R", "PJSD026_Camo_Kamikaze");
		}
		else if (ship.equals("Tachibana L"))
		{
			ship = ship.replace("Tachibana L", "PJSD014_Tachibana_1912_Asus");
		}
		else if (ship.equals("Diana L"))
		{
			ship = ship.replace("Diana L", "PRSC010_Diana_1905_Asus");
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
		setHullStats();
		setEngineStats();
		setRadarStats();
		setTorpedoStats();
		
		setModuleUpgradeList();		
		setConsumablesList();		
		
		setCrewList();
		setFlagList();
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
			if (
					   !shipList.get(i).toString().matches("(PASA).*")
					&& !shipList.get(i).toString().matches("(PASB).*") 
					&& !shipList.get(i).toString().matches("(PASC).*")
					&& !shipList.get(i).toString().matches("(PASD).*")					
					&& !shipList.get(i).toString().matches("(PBSA).*")
					&& !shipList.get(i).toString().matches("(PBSB).*")
					&& !shipList.get(i).toString().matches("(PBSC).*")
					&& !shipList.get(i).toString().matches("(PBSD).*")
					&& !shipList.get(i).toString().matches("(PGSA).*")
					&& !shipList.get(i).toString().matches("(PGSB).*")
					&& !shipList.get(i).toString().matches("(PGSC).*")
					&& !shipList.get(i).toString().matches("(PGSD).*")
					&& !shipList.get(i).toString().matches("(PJSA).*")
					&& !shipList.get(i).toString().matches("(PJSB).*")
					&& !shipList.get(i).toString().matches("(PJSC).*")
					&& !shipList.get(i).toString().matches("(PJSD).*")
					&& !shipList.get(i).toString().matches("(PRSA).*")
					&& !shipList.get(i).toString().matches("(PRSB).*")
					&& !shipList.get(i).toString().matches("(PRSC).*")
					&& !shipList.get(i).toString().matches("(PRSD).*")
					&& !shipList.get(i).toString().matches("(PWSD).*")
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
	 * Sets ship code from input name.
	 * @param shipName Ship name
	 */
	private void setShipCode(String aShipName)
	{
		String shipName = aShipName;
		
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
	
	@SuppressWarnings("unchecked")
	private void setFlagList()
	{
		flagList.addAll(GameParams.keySet());
		for (int i = flagList.size() -1; i >= 0; i--)
		{
			if (!flagList.get(i).toString().matches(".*SignalFlag"))
			{
				flagList.remove(i);
			}	
		}
	}
	
	/**
	 * Returns GameParams data.
	 * @return GameParams GameParams data
	 */
	public JSONObject getGameParams()
	{
		return GameParams;
	}
	
	/**
	 * Returns crew skills data.
	 * @return crewList Crew list
	 */
	public List<String> getCrewList()
	{
		return crewList;
	}
	
	public List<String> getFlagList()
	{
		return flagList;
	}
	
	public List<String> getTurretList()
	{
		return turretList;
	}
	
	public List<String> getHullList()
	{
		return hullList;
	}
	
	public List<String> getEngineList()
	{
		return engineList;
	}
	
	public List<String> getRadarList()
	{
		return radarList;
	}
	
	public List<String> getTorpedoList()
	{	
		return torpedoList;
	}
	
	/**
	 * Returns tier of ship.
	 * @return tier Tier of ship
	 */
	public long getTier()
	{
		return tier;
	}
	
	/**
	 * Returns nation of ship.
	 * @return nation Nation of ship
	 */
	public String getNation()
	{
		return nation;				
	}
		
	/**
	 * Returns type of ship.
	 * @return shipType Type of ship
	 */
	public String getShipType()
	{
		return shipType;
	}
	
	/**
	 * Returns maximum possible health.
	 * @return maxHP Maximum possible health
	 */
	public double getHealth()
	{
		return maxHP;
	}
	
	/**
	 * Returns speed of ship.
	 * @return speed Speed of ship
	 */
	public Object getSpeed()
	{
		return speed;
	}
	
	/**
	 * Returns rudder shift time.
	 * @return rudderShift Rudder shift time
	 */
	public double getRudderShift()
	{
		return rudderShift;
	}
	
	/**
	 * Returns range of turret.
	 * @return maxMainGunRange Range of main gun
	 */
	public double getMaxMainGunRange()
	{
		return maxMainGunRange;
	}
	
	/**
	 * Returns rotation of turret.
	 * @return mainGunRotation Rotation of main turret
	 */
	public double getMainGunRotation()
	{
		return mainGunRotation;
	}
	
	/**
	 * Returns reload time of turret.
	 * @return mainGunReload Reload time of main gun
	 */
	public double getMainGunReload()
	{
		return mainGunReload;
	}
	
	/**
	 * Returns main gun dispersion.
	 * @return mainGunDispersion Main gun dispersion
	 */
	public double getMainGunDispersion()
	{
		return mainGunDispersionTangent;
	}
	
	/**
	 * Returns AP shell speed.
	 * @return APShellSpeed AP shell speed
	 */
	public double getAPShellSpeed()
	{
		return APShellSpeed;
	}
	
	public double getAPShellDMG()
	{
		return APShellDMG;
	}
	
	/**
	 * Returns HE shell speed.
	 * @return HEShellSpeed HE shell speed
	 */
	public double getHEShellSpeed()
	{
		return HEShellSpeed;
	}
	
	public double getHEShellDMG()
	{
		return HEShellDMG;
	}
	
	/**
	 * Returns HE shell burn probability.
	 * @return HEShellBurnProb HE shell burn probability
	 */
	public double getHEShellBurnProb()
	{
		return HEShellBurnProb;
	}
	
	/**
	 * Returns secondary max distance.
	 * @return secondaryMaxDist Secondary max distance
	 */
	public double getSecondaryMaxDist()
	{
		return secondaryMaxDist;
	}
	
	/**
	 * Returns rotation time of torpedo.
	 * @return torpedoRotation Rotation of torpedo tube
	 */
	public double getTorpedoRotation()
	{
		return torpedoRotation;
	}
	
	/**
	 * Returns reload time of torpedo.
	 * @return torpedoReload Reload time of torpedo tube
	 */
	public double getTorpedoReload()
	{
		return torpedoReload;
	}
	
	/**
	 * Returns max torpedo range.
	 * @return maxTorpedoRange Max torpedo range
	 */
	public double getMaxTorpedoRange()
	{
		return maxTorpedoRange;
	}
	
	/**
	 * Returns torpedo speed.
	 * @return torpedoSpeed Torpedo speed
	 */
	public double getTorpedoSpeed()
	{
		return torpedoSpeed;
	}
	
	/**
	 * Returns surface concealment.
	 * @return sConceal Surface concealment
	 */
	public double getSConceal()
	{
		return sConceal;
	}
	
	/**
	 * Returns air concealment.
	 * @return aConceal Air concealment
	 */
	public double getAConceal()
	{
		return aConceal;
	}
	
	/**
	 * Returns AA Aura Far distance.
	 * @return antiAirAuraDistanceFar AA Aura Far distance
	 */
	public double getAntiAirAuraDistanceFar()
	{
		return antiAirAuraDistanceFar;
	}
	
	/**
	 * Returns AA Aura Medium distance.
	 * @return antiAirAuraDistanceMedium AA Aura Medium distance
	 */
	public double getAntiAirAuraDistanceMedium()
	{
		return antiAirAuraDistanceMedium;
	}
	
	/**
	 * Returns AA Aura Near distance.
	 * @return antiAirAuraDistanceNear AA Aura Near distance
	 */
	public double getAntiAirAuraDistanceNear()
	{
		return antiAirAuraDistanceNear;
	}
	
	
	
	/**
	 * Returns number of module slots.
	 * @return moduleSlots Number of module slots
	 */
	public int getModuleSlots()
	{
		return moduleSlots;
	}	
	
	/**
	 * Returns barrel diameter in meter.
	 * @return barrelDiameter Barrel diameter in meter
	 */
	public double getBarrelDiameter()
	{
		return barrelDiameter;
	}	
	
	public Object getNumBarrels()
	{
		return numBarrels;
	}
	
	public int getNumTurrets()
	{
		return numTurrets;
	}
	
	/**
	 * Returns engine JSONObject
	 * @return engineObj engine JSONObject
	 */
	public JSONObject getEngine()
	{
		return engineObj;
	}
	
	/**
	 * Returns burn time.
	 * @return burnTime Burn time
	 */
	public double getBurnTime()
	{
		return burnTime;
	}
	
	/**
	 * Returns flood time.
	 * @return floodTime Flood time
	 */
	public double getFloodTime()
	{
		return floodTime;
	}
	
	/**
	 * Returns engine repair time.
	 * @return EngineAutoRepairTime Engine repair time
	 */
	public long getEngineAutoRepairTime()
	{
		return EngineAutoRepairTime;
	}
	
	/**
	 * Returns main turret HP
	 * @return MainTurretHP Main turret HP
	 */
	public double getMainTurretHP()
	{
		return MainTurretHP;
	}
	
	/**
	 * Returns main turret repair time.
	 * @return MainTurretAutoRepairTime Main turret repair time
	 */
	public double getMainTurretAutoRepairTime()
	{
		return MainTurretAutoRepairTime;
	}
	
	/**
	 * Returns torpedo tube HP.
	 * @return TorpedoHP Torpedo tube HP
	 */
	public double getTorpedoHP()
	{
		return TorpedoHP;
	}
	
	/**
	 * Returns torpedo repair time.
	 * @return TorpedoAutoRepairTime Torpedo repair time
	 */
	public double getTorpedoAutoRepairTime()
	{
		return TorpedoAutoRepairTime;
	}
	
	/**
	 * Returns AA Aura Far DPS.
	 * @return AAFarDPS AA Aura Far DPS
	 */
	public double getAAFarDPS()
	{
		return AAFarDPS;
	}
	
	/**
	 * Returns AA Aura Medium DPS.
	 * @return AAMediumDPS AA Aura Medium DPS
	 */
	public double getAAMediumDPS()
	{
		return AAMediumDPS;
	}
	
	/**
	 * Returns AA Aura Near DPS.
	 * @return AANearDPS AA Aura Near DPS
	 */
	public double getAANearDPS()
	{
		return AANearDPS;
	}
	
	/**
	 * Returns stealth fire detection range.
	 * @return stealthFireSurfaceDetection Stealth fire detection range
	 */
	public double getStealthFireSurfaceDetection()
	{
		return stealthFireSurfaceDetection;
	}
	
	/**
	 * Returns AA fire detection range.
	 * @return AAFireAirDetection AA fire detection range
	 */
	public double getAAFireAirDetection()
	{
		return AAFireAirDetection;
	}
	
	public double getMaxDistCoef()
	{
		return maxDistCoef;
	}	
	
	/**
	 * Returns list of module slot 1.
	 * @return modSlot1 List of module slot 1
	 */
	public List<String> getModule1()
	{
		return modSlot1;
	}	
	
	/**
	 * Returns list of module slot 2.
	 * @return modSlot2 List of module slot 2
	 */
	public List<String> getModule2()
	{
		return modSlot2;
	}
	
	/**
	 * Returns list of module slot 3.
	 * @return modSlot3 List of module slot 3
	 */
	public List<String> getModule3()
	{
		return modSlot3;
	}
	
	/**
	 * Returns list of module slot 4.
	 * @return modSlot4 List of module slot 4
	 */
	public List<String> getModule4()
	{
		return modSlot4;
	}
	
	/**
	 * Returns list of module slot 5.
	 * @return modSlot5 List of module slot 5
	 */
	public List<String> getModule5()
	{
		return modSlot5;
	}
	
	/**
	 * Returns list of module slot 6.
	 * @return modSlot6 List of module slot 6
	 */
	public List<String> getModule6()
	{
		return modSlot6;
	}	

	public JSONArray getAbil0()
	{
		return (JSONArray) Abil0;
	}
	
	public JSONArray getAbil1()
	{
		return (JSONArray) Abil1;
	}
	
	public JSONArray getAbil2()
	{
		return (JSONArray) Abil2;
	}
	
	public JSONArray getAbil3()
	{
		return (JSONArray) Abil3;
	}
	
	public List<String> getAbility0()
	{
		return Ability0;
	}

	public List<String> getAbility1()
	{
		return Ability1;
	}

	public List<String> getAbility2()
	{
		return Ability2;
	}

	public List<String> getAbility3()
	{
		return Ability3;
	}
	
	/**
	 * Returns list of upgrades.
	 * @return upgradeList List of upgrades
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
		for (String string : shipUpgradeList()) 
	    {
			if(string.matches("(?i)(P).*(UA).*"))
	        {
				turretList.add(string);
	        }
	    }
		Collections.sort(turretList);
	}

	public void setTurretStats2(String aTurret)
	{	
		if (aTurret == null)
		{
			return;
		}
		
		
		Map<String, JSONObject> turret1 = new HashMap<>();
		
		for(int i = 0; i < turretList.size(); i++)
		{
			turret1.put(turretList.get(i), (JSONObject) upgrade.get(turretList.get(i)));			
		}
		
		JSONArray turret2 = null;
		JSONObject turret3 = null;
		JSONArray tobj3 = null;
		
		if (turretList.size() != 0)
		{
			for (int i = 0; i < turret1.size(); i++)
			{
				if (turretList.get(i).toString().contains(aTurret))
				{
					turret3 = (JSONObject) turret1.get(turretList.get(i)).get("components");
					turret2 = (JSONArray) turret3.get("artillery");
				}
			}			
			
			tobj = (JSONObject) shipJSON.get(turret2.get(0));
			
			if (getNation().equals("Germany"))
			{
				tobj2 = (JSONObject) tobj.get("HP_GGM_1");
			}
			else if (getNation().equals("USA"))
			{
				tobj2 = (JSONObject) tobj.get("HP_AGM_1");
			}
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
			else if (getNation().equals("United_Kingdom"))
			{
				tobj2 = (JSONObject) tobj.get("HP_BGM_1");
			}
					
			maxMainGunRange = (double) tobj.get("maxDist") * maxDistCoef;
			numBarrels = (Object) tobj2.get("numBarrels");
			 
			List<String> temp = new ArrayList<String>();
			temp.addAll(tobj.keySet());
			for (int i = temp.size() - 1; i >= 0; i--)
			{
				if (!temp.get(i).contains("HP_"))
				{
					temp.remove(i);
				}				
			}						
			numTurrets = temp.size();			
			
			barrelDiameter = (double) tobj2.get("barrelDiameter");
			
			JSONArray ammoList = (JSONArray) tobj2.get("ammoList");
			Collections.sort(ammoList);
						
			JSONObject APShell = null;
			JSONObject HEShell = null;
			
			for (int i = 0; i < ammoList.size(); i++)
			{
				if (   ammoList.get(i).toString().contains("_AP_") 
					|| ammoList.get(i).toString().contains("_B")
											
						)
				{
					APShell = (JSONObject) GameParams.get(ammoList.get(i));	
				}
			}
			for (int i = 0; i < ammoList.size(); i++)
			{
				if (	ammoList.get(i).toString().contains("_HE_") 
					 || ammoList.get(i).toString().contains("_OF")
						)
				{
					HEShell = (JSONObject) GameParams.get(ammoList.get(i));
				}
			}
						
			
			APShellSpeed = (double) APShell.get("bulletSpeed");
			APShellDMG = (double) APShell.get("alphaDamage");
			HEShellSpeed = (double) HEShell.get("bulletSpeed");
			HEShellDMG = (double) HEShell.get("alphaDamage");
			HEShellBurnProb = (double) HEShell.get("burnProb");
			
			tobj3 = (JSONArray) tobj2.get("rotationSpeed");
			mainGunRotation = (double) tobj3.get(0);
			mainGunReload = (double) tobj2.get("shotDelay");

			if (tobj2.get("idealRadius") instanceof Double)
			{
				mainGunDispersionTangent = (double) tobj2.get("idealRadius");
				mainGunDispersionTangent = Math.toRadians(mainGunDispersionTangent * 0.03);
				mainGunDispersionTangent = Math.tan(mainGunDispersionTangent);
			}
			else if (tobj2.get("idealRadius") instanceof Long)
			{
				mainGunDispersionTangent = (long) tobj2.get("idealRadius");
				mainGunDispersionTangent = Math.toRadians(mainGunDispersionTangent * 0.03);
				mainGunDispersionTangent = Math.tan(mainGunDispersionTangent);
			}
		}
		
		if (tobj2 != null)
		{
			JSONObject HitLocationArtillery = (JSONObject) tobj2.get("HitLocationArtillery");
			MainTurretHP = (double) HitLocationArtillery.get("maxHP");
			MainTurretAutoRepairTime = (long) HitLocationArtillery.get("autoRepairTime");
		}		
	}
	
	private void setHullStats()
	{		
		for (String string : shipUpgradeList()) 
	    {
			if(string.matches("(?i)(P).*(UH).*"))
			{				
				hullList.add(string);
			}
	    }
		Collections.sort(hullList);
	}	
	
	public void setHullStats2(String aHull)
	{
		Map<String, JSONObject> hull1 = new HashMap<>();
		int position = 0;		
		
		for(int i = 0; i < hullList.size(); i++)
		{
			hull1.put(hullList.get(i), (JSONObject) upgrade.get(hullList.get(i)));			
		}		

		JSONArray hull2 = null;
		JSONObject hull3;
		
		for (int i = 0; i < hull1.size(); i++)
		{
			if (hullList.get(i).toString().contains(aHull))
			{
				hull3 = (JSONObject) hull1.get(hullList.get(i)).get("components");
				hull2 = (JSONArray) hull3.get("hull");
				position = i;
			}
		}		
		
		JSONObject hpobj;
		hpobj = (JSONObject) shipJSON.get(hull2.get(0));
		maxHP = (double) hpobj.get("health");
		rudderShift = (double) hpobj.get("rudderTime");
		if (hpobj.get("maxSpeed") instanceof Double)
		{
			speed = (double) hpobj.get("maxSpeed") * speedCoef;
		}
		else if (hpobj.get("maxSpeed") instanceof Long)
		{
			speed = (long) hpobj.get("maxSpeed") * speedCoef;
		}
		
		sConceal = (double) hpobj.get("visibilityFactor");
		aConceal = (double) hpobj.get("visibilityFactorByPlane");
		stealthFireSurfaceDetection = (double) hpobj.get("visibilityCoefGK");
		AAFireAirDetection = (double) hpobj.get("visibilityCoefATBAByPlane");
		
		JSONArray burnNodes1 = (JSONArray) hpobj.get("burnNodes");
		JSONArray burnNodes2 = (JSONArray) burnNodes1.get(0);		
		burnTime = (double) burnNodes2.get(3);
		
		JSONArray floodParams = (JSONArray) hpobj.get("floodParams");
		floodTime = (double) floodParams.get(2);
		
		//If main turret's barrel diameter is less than 140 mm, turret has AuraFar by default.
		if (barrelDiameter < 0.140 && tobj2 != null)
		{
			antiAirAuraDistanceFar = (double) tobj2.get("antiAirAuraDistance") * 0.03;
			
			JSONObject AuraFar = (JSONObject) tobj.get("AuraFar");
			if (AuraFar != null)
			{
				JSONArray guns = (JSONArray) AuraFar.get("guns");			
						
				AAFarDPS = (double) tobj2.get("antiAirAuraStrength") * guns.size() * 100;
			}
		}
		else if (barrelDiameter >= 0.140 || tobj2 == null)
		{
			List<String> ATBAList = new ArrayList<String>();
			
			ATBAList.addAll(shipJSON.keySet());
			for (int i = ATBAList.size() - 1; i >= 0; i--)
			{
				if (!ATBAList.get(i).toString().matches(".*(ATBA).*"))
				{
					ATBAList.remove(i);
				}
			}
			Collections.sort(ATBAList);
			if (ATBAList.size() != 0)
			{		
				JSONObject ATBA;
				if (ATBAList.size() == 1)
				{
					String ATBAName = ATBAList.get(ATBAList.size()-1);
					ATBA = (JSONObject) shipJSON.get(ATBAName);
					secondaryMaxDist = (double) ATBA.get("maxDist");
				}
				else
				{
					String ATBAName = ATBAList.get(position);
					ATBA = (JSONObject) shipJSON.get(ATBAName);
					secondaryMaxDist = (double) ATBA.get("maxDist");
				}
			
				//AA Aura Far
				if (ATBA.get("AuraFar") != null)
				{
					JSONObject AuraFar = (JSONObject) ATBA.get("AuraFar");
					JSONArray guns = (JSONArray) AuraFar.get("guns");
					String AAFarGunString = guns.get(0).toString();
					JSONObject AAFarGun = (JSONObject) ATBA.get(AAFarGunString);
					antiAirAuraDistanceFar = (double) AAFarGun.get("antiAirAuraDistance") * 0.03;
					
					int count = 0;
					while (count < guns.size())
					{
						String tempStr = (String) guns.get(count);
						JSONObject tempObj = (JSONObject) ATBA.get(tempStr);				
						double dps = (double) tempObj.get("antiAirAuraStrength");
						
						AAFarDPS = AAFarDPS + dps;
						
						count++;
					}
					AAFarDPS = Math.round(AAFarDPS * 100);
				}
			}
		}
		
		List<String> AAMedium = new ArrayList<String>();
		
		AAMedium.addAll(shipJSON.keySet());
		for (int i = AAMedium.size() - 1; i >= 0; i--)
		{
			if (!AAMedium.get(i).toString().matches(".*(AirDefense).*"))
			{
				AAMedium.remove(i);
			}
		}
		Collections.sort(AAMedium);
		
		if (AAMedium.size() != 0)
		{
			String AirDefenseNameMedium = AAMedium.get(position);
			JSONObject AirDefenseMedium = (JSONObject) shipJSON.get(AirDefenseNameMedium);
			if (AirDefenseMedium.get("AuraMedium") != null)
			{
				JSONObject AuraMedium = null;
				if (AirDefenseMedium.get("AuraMedium1") != null)
				{
					AuraMedium = (JSONObject) AirDefenseMedium.get("AuraMedium1");
				}
				else
				{
					AuraMedium = (JSONObject) AirDefenseMedium.get("AuraMedium"); 
				}
				
				JSONArray guns = (JSONArray) AuraMedium.get("guns");
				String AAMediumGunString = guns.get(0).toString();
				JSONObject AAMediumGun = (JSONObject) AirDefenseMedium.get(AAMediumGunString);
				antiAirAuraDistanceMedium = (double) AAMediumGun.get("antiAirAuraDistance") * 0.03;
				
				int count = 0;
				while (count < guns.size())
				{
					String tempStr = (String) guns.get(count);
					JSONObject tempObj = (JSONObject) AirDefenseMedium.get(tempStr);				
					double dps = (double) tempObj.get("antiAirAuraStrength");
					
					AAMediumDPS = AAMediumDPS + dps;
					
					count++;
				}
				AAMediumDPS = Math.round(AAMediumDPS * 100);
			}
			
			List<String> AANear = new ArrayList<String>();
			
			AANear.addAll(shipJSON.keySet());
			for (int i = AANear.size() - 1; i >= 0; i--)
			{
				if (!AANear.get(i).toString().matches(".*(AirDefense).*"))
				{
					AANear.remove(i);
				}
			}
			Collections.sort(AANear);
			String AirDefenseNameNear = AANear.get(position);
			JSONObject AirDefenseNear = (JSONObject) shipJSON.get(AirDefenseNameNear);
			if (AirDefenseNear.get("AuraNear") != null)
			{
				JSONObject AuraNear = (JSONObject) AirDefenseNear.get("AuraNear");
				JSONArray guns = (JSONArray) AuraNear.get("guns");
				String AANearGunString = guns.get(0).toString();
				JSONObject AANearGun = (JSONObject) AirDefenseNear.get(AANearGunString);
				antiAirAuraDistanceNear = (double) AANearGun.get("antiAirAuraDistance") * 0.03;
				
				int count = 0;
				while (count < guns.size())
				{
					String tempStr = (String) guns.get(count);
					JSONObject tempObj = (JSONObject) AirDefenseNear.get(tempStr);				
					double dps = (double) tempObj.get("antiAirAuraStrength");
					
					AANearDPS = AANearDPS + dps;
					
					count++;
				}
				AANearDPS = Math.round(AANearDPS * 100);
			}
			else
			{
				JSONObject AuraMedium = (JSONObject) AirDefenseMedium.get("AuraMedium");
				if (AuraMedium != null)
				{
					JSONArray guns = (JSONArray) AuraMedium.get("guns");
					String AANearGunString = guns.get(0).toString();
					JSONObject AANearGun = (JSONObject) AirDefenseNear.get(AANearGunString);
					antiAirAuraDistanceNear = (double) AANearGun.get("antiAirAuraDistance") * 0.03;
					
					int count = 0;
					while (count < guns.size())
					{
						String tempStr = (String) guns.get(count);
						JSONObject tempObj = (JSONObject) AirDefenseNear.get(tempStr);				
						double dps = (double) tempObj.get("antiAirAuraStrength");
						
						AANearDPS = AANearDPS + dps;
						
						count++;
					}
					AANearDPS = Math.round(AANearDPS * 100);
				}
			}		
		}		
	}
	
	private void setEngineStats()
	{
		for (String string : shipUpgradeList()) 
	    {
			if(string.matches(".*(_Engine_).*") || string.matches(".*(_ENG_).*") || string.matches("(?i)(P).*(UE).*"))
	        {
				engineList.add(string);
	        }
	    }		

		Collections.sort(engineList);		
	}
	
	public void setEngineStats2(String anEngine)
	{
		Map<String, JSONObject> engine1 = new HashMap<>();
		for(int i = 0; i < engineList.size(); i++)
		{
			engine1.put(engineList.get(i), (JSONObject) upgrade.get(engineList.get(i)));			
		}		

		JSONArray engine2 = null;
		JSONObject engine3 = null;
		
		for (int i = 0; i < engine1.size(); i++)
		{
			if (engineList.get(i).toString().contains(anEngine))
			{
				engine3 = (JSONObject) engine1.get(engineList.get(i)).get("components");
				engine2 = (JSONArray) engine3.get("engine");
			}
		}
			
		engineObj = (JSONObject) shipJSON.get(engine2.get(0));
		
		speedCoef = 1 + (double) engineObj.get("speedCoef");
		Object histEnginePower = (Object) engineObj.get("histEnginePower");
		JSONObject HitLocationEngine = (JSONObject) engineObj.get("HitLocationEngine");
		EngineAutoRepairTime = (long) HitLocationEngine.get("autoRepairTimeMin");
		
	}
	
	private void setRadarStats()
	{
		for (String string : shipUpgradeList()) 
	    {
			if(string.matches(".*(_Suo)") || string.matches(".*(_SUO)") || string.matches("(?i)(P).*(US).*"))
			{				
				radarList.add(string);
			}
	    }
		Collections.sort(radarList);
	}
	
	public void setRadarStats2(String radar)
	{		
		if (radar == null)
		{
			return;
		}
		String radar2 = null;
		for (int i = 0; i < radarList.size(); i++)
		{
			if (radarList.get(i).toString().contains(radar))
			{
				radar2 = radarList.get(i);
			}
		}
		
		JSONObject r2 = (JSONObject) upgrade.get(radar2);
		JSONObject r3 = (JSONObject) r2.get("components");
		JSONArray r4 = (JSONArray) r3.get("fireControl");
		JSONObject jso4 = (JSONObject) shipJSON.get(r4.get(0));
		maxDistCoef = (double) jso4.get("maxDistCoef");
	}
	
	private void setTorpedoStats()
	{
		for (String string : shipUpgradeList()) 
	    {
			if(string.matches(".*(_Torp).*") || string.matches(".*(_TORP).*") || string.matches("(?i)(P).*(UT).*"))
	        {
				torpedoList.add(string);
	        }
	    }
		
		if (torpedoList.size() == 0)
		{
			return;
		}

		Collections.sort(torpedoList);		
	}
	
	public void setTorpedoStats2(String aTorpedo)
	{
		Map<String, JSONObject> torp1 = new HashMap<>();
		
		for(int i = 0; i < torpedoList.size(); i++)
		{
			torp1.put(torpedoList.get(i), (JSONObject) upgrade.get(torpedoList.get(i)));			
		}		

		JSONArray torp2 = null;
		JSONObject torp3 = null;
		
		for (int i = 0; i < torp1.size(); i++)
		{
			if (torpedoList.get(i).toString().contains(aTorpedo))
			{
				torp3 = (JSONObject) torp1.get(torpedoList.get(i)).get("components");
				torp2 = (JSONArray) torp3.get("torpedoes");
			}
		}
		
		JSONObject tobj = null;
		JSONObject tobj2 = null;
		JSONArray tobj3 = null;
		
		if (torp2 != null)
		{
			tobj = (JSONObject) shipJSON.get(torp2.get(0));
		}
		else
		{
			return;
		}
		
		if (getNation().equals("Germany"))
		{
			tobj2 = (JSONObject) tobj.get("HP_GGT_1");			
		}
		else if (getNation().equals("USA"))
		{
			tobj2 = (JSONObject) tobj.get("HP_AGT_1");
		}
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
		else if (getNation().equals("United_Kingdom"))
		{
			tobj2 = (JSONObject) tobj.get("HP_BGT_1");
		}
		
		tobj3 = (JSONArray) tobj2.get("rotationSpeed");
		
		torpedoRotation = (double) tobj3.get(0);
		torpedoReload = (double) tobj2.get("shotDelay");
		
		JSONObject HitLocationTorpedo = (JSONObject) tobj2.get("HitLocationTorpedo");
		
		TorpedoHP = (double) HitLocationTorpedo.get("maxHP");
		TorpedoAutoRepairTime = (long) HitLocationTorpedo.get("autoRepairTime");
		
		JSONArray ammoList = (JSONArray) tobj2.get("ammoList");
		String ammo = (String) ammoList.get(0);
		
		JSONObject ammoObj = (JSONObject) GameParams.get(ammo);		
		maxTorpedoRange = (double) ammoObj.get("maxDist") * 0.03;
		torpedoSpeed = (double) ammoObj.get("speed");
	}

	private void setModuleSlots()
	{		
		/**
		 * Set upgrade slot numbers.
		 */
		JSONObject mSlots = (JSONObject) shipJSON.get("ShipModernization");
		moduleSlots = mSlots.size();	
	}
	
	@SuppressWarnings("unchecked")
	private void setConsumablesList()
	{
		JSONObject ShipAbilities = (JSONObject) shipJSON.get("ShipAbilities");
		JSONObject AbilitySlot0 = (JSONObject) ShipAbilities.get("AbilitySlot0");
		JSONObject AbilitySlot1 = (JSONObject) ShipAbilities.get("AbilitySlot1");
		JSONObject AbilitySlot2 = (JSONObject) ShipAbilities.get("AbilitySlot2");
		JSONObject AbilitySlot3 = (JSONObject) ShipAbilities.get("AbilitySlot3");
		Abil0 = (JSONArray) AbilitySlot0.get("abils");
		Abil1 = (JSONArray) AbilitySlot1.get("abils");
		Abil2 = (JSONArray) AbilitySlot2.get("abils");
		Abil3 = (JSONArray) AbilitySlot3.get("abils");
		
		for (int i = 0; i < Abil0.size(); i++)
		{
			Ability0.add(Abil0.get(i).get(0).toString());
		}
		
		for (int i = 0; i < Abil1.size(); i++)
		{
			Ability1.add(Abil1.get(i).get(0).toString());
		}
		
		for (int i = 0; i < Abil2.size(); i++)
		{
			Ability2.add(Abil2.get(i).get(0).toString());
		}
		
		for (int i = 0; i < Abil3.size(); i++)
		{
			Ability3.add(Abil3.get(i).get(0).toString());
		}
	}
}
