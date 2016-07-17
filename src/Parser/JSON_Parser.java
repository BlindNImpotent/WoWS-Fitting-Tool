package Parser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import lombok.Data;

/**
 * 
 * @author Aesis (BlindNImpotent)
 *
 */
@Data
public class JSON_Parser 
{
	private API_Parser APIParser;
	private GameParams_Parser GPParser;

	private JSONObject GameParams;
	
	private List<String> NationList = new ArrayList<>();
	private String nation;
	private List<String> ShipTypeList = new ArrayList<>();
	
	private JSONObject modules_treeJSON;
	private List<String> modules_treeList = new ArrayList<>();
	private List<JSONObject> default_loadouts = new ArrayList<>();
	private List<JSONObject> upgradeModules = new ArrayList<>();
	
	private JSONObject ShipUpgradeInfoJSON;
	private List<String> ShipUpgradeInfoList = new ArrayList<>();

	private int tier;
	private String shipType;

	private JSONObject GP_ArtilleryJSON;
	private JSONObject GP_TurretJSON;
	private double maxMainGunRange;
	private double maxDistCoef;
	private double sigmaCount;
	private int numBarrels;
	private int numTurrets;
	private double turretBarrelDiameter;
	private double APShellSpeed;
	private double APShellDMG;
	private double HEShellSpeed;
	private double HEShellDMG;
	private double HEShellBurnProb;
	private double mainGunRotation;
	private double mainGunReload;
	private double mainGunDispersionTangent;

	private JSONObject GP_HullJSON;
	private long maxRepairCost;
	private double maxHP;
	private double rudderShift;
	private double speed;
	private double turningRadius;
	private JSONObject engineObj;
	private double speedCoef;
	private double sConceal;
	private double aConceal;
	private double stealthFireSurfaceDetection;
	private double AAFireAirDetection;
	private double burnTime;
	private double floodTime;
	private double antiAirAuraDistanceFar;
	private double AAFarDPS;
	private double AAFarBarrelDiameter;
	private double secondaryMaxDist;
	private double antiAirAuraDistanceMedium;
	private double AAMediumDPS;
	private double antiAirAuraDistanceNear;
	private double AANearDPS;

	private int horsePower;

	private double torpDiameter;
	private int numTubes;
	private int numTorpTurrets;
	private double torpedoRotation;
	private double torpedoReload;
	private double maxTorpedoRange;
	private double torpedoSpeed;
	private double torpedoVisibilityFactor;

	private List<JSONArray> Abil0 = new ArrayList<JSONArray>();
	private List<JSONArray> Abil1 = new ArrayList<JSONArray>();
	private List<JSONArray> Abil2 = new ArrayList<JSONArray>();
	private List<JSONArray> Abil3 = new ArrayList<JSONArray>();

	private List<String> Ability0 = new ArrayList<String>();
	private List<String> Ability1 = new ArrayList<String>();
	private List<String> Ability2 = new ArrayList<String>();
	private List<String> Ability3 = new ArrayList<String>();

	private double afterBattleRepair;
	private double visibilityFactorPermaCamo;
	private double visibilityFactorByPlanePermaCamo;
	private double expFactorPermaCamo;


	private List<Long> API_UpgradesIDList = new ArrayList<>();
	private JSONObject GP_UpgradesJSON;
	private List<String> UpgradesNameList = new ArrayList<>();
	private JSONObject ModernizationSlot1;
	private JSONObject ModernizationSlot2;
	private JSONObject ModernizationSlot3;
	private JSONObject ModernizationSlot4;
	private JSONObject ModernizationSlot5;
	private JSONObject ModernizationSlot6;
	private JSONArray ModernizationSlot1_mods;
	private JSONArray ModernizationSlot2_mods;
	private JSONArray ModernizationSlot3_mods;
	private JSONArray ModernizationSlot4_mods;
	private JSONArray ModernizationSlot5_mods;
	private JSONArray ModernizationSlot6_mods;
	private List<String> modSlot1 = new ArrayList<>();
	private List<String> modSlot2 = new ArrayList<>();
	private List<String> modSlot3 = new ArrayList<>();
	private List<String> modSlot4 = new ArrayList<>();
	private List<String> modSlot5 = new ArrayList<>();
	private List<String> modSlot6 = new ArrayList<>();
	private LinkedHashMap<String, JSONObject> mods1 = new LinkedHashMap<>();
	private LinkedHashMap<String, JSONObject> mods2 = new LinkedHashMap<>();
	private LinkedHashMap<String, JSONObject> mods3 = new LinkedHashMap<>();
	private LinkedHashMap<String, JSONObject> mods4 = new LinkedHashMap<>();
	private LinkedHashMap<String, JSONObject> mods5 = new LinkedHashMap<>();
	private LinkedHashMap<String, JSONObject> mods6 = new LinkedHashMap<>();
	private List<String> modSlot1Name = new ArrayList<>();
	private List<String> modSlot2Name = new ArrayList<>();
	private List<String> modSlot3Name = new ArrayList<>();
	private List<String> modSlot4Name = new ArrayList<>();
	private List<String> modSlot5Name = new ArrayList<>();
	private List<String> modSlot6Name = new ArrayList<>();
	
	private List<String> permaflage = new ArrayList<>();
	private HashMap<String, JSONObject> permaflageHashMap = new HashMap<>();	
	
	private List<JSONObject> CamouflageJSONList = new ArrayList<>();
	private List<String> CamouflageNameList = new ArrayList<>();	
	
	private List<JSONObject> API_ArtilleryUpgradeJSONList = new ArrayList<>();
	private List<String> API_ArtilleryUpgradeNameList = new ArrayList<>();
	private HashMap<String, JSONObject> API_ArtilleryUpgradeJSONHashMap = new HashMap<>();
	
	private List<JSONObject> API_HullUpgradeJSONList = new ArrayList<>();
	private List<String> API_HullUpgradeNameList = new ArrayList<>();
	private HashMap<String, JSONObject> API_HullUpgradeJSONHashMap = new HashMap<>();
	
	private List<JSONObject> API_EngineUpgradeJSONList = new ArrayList<>();
	private List<String> API_EngineUpgradeNameList = new ArrayList<>();
	private HashMap<String, JSONObject> API_EngineUpgradeJSONHashMap = new HashMap<>();
	
	private List<JSONObject> API_SuoUpgradeJSONList = new ArrayList<>();
	private List<String> API_SuoUpgradeNameList = new ArrayList<>();
	private HashMap<String, JSONObject> API_SuoUpgradeJSONHashMap = new HashMap<>();
	
	private List<JSONObject> API_TorpedoesUpgradeJSONList = new ArrayList<>();
	private List<String> API_TorpedoesUpgradeNameList = new ArrayList<>();
	private HashMap<String, JSONObject> API_TorpedoesUpgradeJSONHashMap = new HashMap<>();
	
	
	
	public JSON_Parser(String aShipName) throws IOException, ParseException
	{
		APIParser = new API_Parser();
		APIParser.setShipJSON(aShipName);
		GPParser = new GameParams_Parser(APIParser.getShip_id_str());
		ShipUpgradeInfoJSON = (JSONObject) GPParser.getShipJSON().get("ShipUpgradeInfo");

		GameParams = GPParser.getGameParams();

		setShipUpgradeModulesInfo();		
		//setShipsJSONAndName();
		setUpgrades();
		setNationList();
		setShipTypeList();
		setPermaflage();
		//setCamouflage();
		setConsumablesList();

		if (GPParser.getShipJSON().get("level") instanceof Long)
		{
			tier = (int) (long) GPParser.getShipJSON().get("level");
		}
		else if (GPParser.getShipJSON().get("level") instanceof Double)
		{
			tier = (int) (double) GPParser.getShipJSON().get("level");
		}
		else
		{
			tier = (int) GPParser.getShipJSON().get("level");
		}

		JSONObject type = (JSONObject) GPParser.getShipJSON().get("typeinfo");
		shipType = (String) type.get("species");

	}
	
	@SuppressWarnings("unchecked")
	private void setShipUpgradeModulesInfo()
	{	
		modules_treeJSON = (JSONObject) APIParser.getShipJSON().get("modules_tree");
		modules_treeList.addAll(modules_treeJSON.keySet());

		JSONObject API_suiJSON;
		
		for (int i = 0; i < modules_treeList.size(); i++)
		{
			API_suiJSON = (JSONObject) modules_treeJSON.get(modules_treeList.get(i));

			if (API_suiJSON.get("is_default").equals(true)) {
				default_loadouts.add(API_suiJSON);
			} else {
				upgradeModules.add(API_suiJSON);
			}

			String API_suiJSONName = (String) API_suiJSON.get("name");

			if (API_suiJSON.get("type").equals("Artillery"))
			{
				API_ArtilleryUpgradeJSONList.add(API_suiJSON);
				API_ArtilleryUpgradeNameList.add(API_suiJSONName);
				API_ArtilleryUpgradeJSONHashMap.put(API_suiJSONName, API_suiJSON);
			}
			else if (API_suiJSON.get("type").equals("Hull"))
			{
				API_HullUpgradeJSONList.add(API_suiJSON);
				API_HullUpgradeNameList.add(API_suiJSONName);
				API_HullUpgradeJSONHashMap.put(API_suiJSONName, API_suiJSON);
			}
			else if (API_suiJSON.get("type").equals("Engine"))
			{
				API_EngineUpgradeJSONList.add(API_suiJSON);
				API_EngineUpgradeNameList.add(API_suiJSONName);
				API_EngineUpgradeJSONHashMap.put(API_suiJSONName, API_suiJSON);
			}
			else if (API_suiJSON.get("type").equals("Suo"))
			{
				API_SuoUpgradeJSONList.add(API_suiJSON);
				API_SuoUpgradeNameList.add(API_suiJSONName);
				API_SuoUpgradeJSONHashMap.put(API_suiJSONName, API_suiJSON);
			}
			else if (API_suiJSON.get("type").equals("Torpedoes"))
			{
				API_TorpedoesUpgradeJSONList.add(API_suiJSON);
				API_TorpedoesUpgradeNameList.add(API_suiJSONName);
				API_TorpedoesUpgradeJSONHashMap.put(API_suiJSONName, API_suiJSON);
			}
		}

		Collections.sort(API_ArtilleryUpgradeNameList);
		Collections.sort(API_HullUpgradeNameList);
		Collections.sort(API_EngineUpgradeNameList);
		Collections.sort(API_SuoUpgradeNameList);
		Collections.sort(API_TorpedoesUpgradeNameList);
	}
	
	
	
	@SuppressWarnings("unchecked")
	private void setUpgrades()
	{
		JSONArray modules = (JSONArray) APIParser.getShipJSON().get("upgrades"); 
		API_UpgradesIDList.addAll(modules);

		GP_UpgradesJSON = (JSONObject) GPParser.getShipJSON().get("ShipModernization");

		if (GP_UpgradesJSON.get("ModernizationSlot1") != null)
		{
			ModernizationSlot1 = (JSONObject) GP_UpgradesJSON.get("ModernizationSlot1");
			ModernizationSlot1_mods = (JSONArray) ModernizationSlot1.get("mods");
		}

		if (GP_UpgradesJSON.get("ModernizationSlot2") != null) {
			ModernizationSlot2 = (JSONObject) GP_UpgradesJSON.get("ModernizationSlot2");
			ModernizationSlot2_mods = (JSONArray) ModernizationSlot2.get("mods");
		}

		if (GP_UpgradesJSON.get("ModernizationSlot3") != null) {
			ModernizationSlot3 = (JSONObject) GP_UpgradesJSON.get("ModernizationSlot3");
			ModernizationSlot3_mods = (JSONArray) ModernizationSlot3.get("mods");
		}

		if (GP_UpgradesJSON.get("ModernizationSlot4") != null) {
			ModernizationSlot4 = (JSONObject) GP_UpgradesJSON.get("ModernizationSlot4");
			ModernizationSlot4_mods = (JSONArray) ModernizationSlot4.get("mods");
		}

		if (GP_UpgradesJSON.get("ModernizationSlot5") != null) {
			ModernizationSlot5 = (JSONObject) GP_UpgradesJSON.get("ModernizationSlot5");
			ModernizationSlot5_mods = (JSONArray) ModernizationSlot5.get("mods");
		}

		if (GP_UpgradesJSON.get("ModernizationSlot6") != null) {
			ModernizationSlot6 = (JSONObject) GP_UpgradesJSON.get("ModernizationSlot6");
			ModernizationSlot6_mods = (JSONArray) ModernizationSlot6.get("mods");
		}

		JSONObject ship_modifications = (JSONObject) APIParser.getAPI_InfoEncyclopediaJSON().get("ship_modifications");

		if (ModernizationSlot1_mods != null) {
			for (int i = 0; i < ModernizationSlot1_mods.size(); i++) {
				mods1.put((String) ship_modifications.get(ModernizationSlot1_mods.get(i)), GPParser.getGameParamsHashMap().get(ModernizationSlot1_mods.get(i)));
				modSlot1.add((String) ModernizationSlot1_mods.get(i));
			}
		}

		if (ModernizationSlot2_mods != null) {
			for (int i = 0; i < ModernizationSlot2_mods.size(); i++) {
				mods2.put((String) ship_modifications.get(ModernizationSlot2_mods.get(i)), GPParser.getGameParamsHashMap().get(ModernizationSlot2_mods.get(i)));
				modSlot2.add((String) ModernizationSlot2_mods.get(i));
			}
		}

		if (ModernizationSlot3_mods != null) {
			for (int i = 0; i < ModernizationSlot3_mods.size(); i++) {
				mods3.put((String) ship_modifications.get(ModernizationSlot3_mods.get(i)), GPParser.getGameParamsHashMap().get(ModernizationSlot3_mods.get(i)));
				modSlot3.add((String) ModernizationSlot3_mods.get(i));
			}
		}

		if (ModernizationSlot4_mods != null) {
			for (int i = 0; i < ModernizationSlot4_mods.size(); i++) {
				mods4.put((String) ship_modifications.get(ModernizationSlot4_mods.get(i)), GPParser.getGameParamsHashMap().get(ModernizationSlot4_mods.get(i)));
				modSlot4.add((String) ModernizationSlot4_mods.get(i));
			}
		}

		if (ModernizationSlot5_mods != null) {
			for (int i = 0; i < ModernizationSlot5_mods.size(); i++) {
				mods5.put((String) ship_modifications.get(ModernizationSlot5_mods.get(i)), GPParser.getGameParamsHashMap().get(ModernizationSlot5_mods.get(i)));
				modSlot5.add((String) ModernizationSlot5_mods.get(i));
			}
		}

		if (ModernizationSlot6_mods != null) {
			for (int i = 0; i < ModernizationSlot6_mods.size(); i++) {
				mods6.put((String) ship_modifications.get(ModernizationSlot6_mods.get(i)), GPParser.getGameParamsHashMap().get(ModernizationSlot6_mods.get(i)));
				modSlot6.add((String) ModernizationSlot6_mods.get(i));
			}
		}


		modSlot1Name.addAll(mods1.keySet());
		modSlot2Name.addAll(mods2.keySet());
		modSlot3Name.addAll(mods3.keySet());
		modSlot4Name.addAll(mods4.keySet());
		modSlot5Name.addAll(mods5.keySet());
		modSlot6Name.addAll(mods6.keySet());
	}
	
	@SuppressWarnings("unchecked")
	private void setNationList()
	{
		JSONObject APINations = (JSONObject) APIParser.getAPI_InfoEncyclopediaJSON().get("ship_nations");
		NationList.addAll(APINations.values());
		Collections.sort(NationList);

		nation = (String) APIParser.getShipJSON().get("nation");
	}
	
	@SuppressWarnings("unchecked")
	private void setShipTypeList()
	{
		JSONObject APIShipType = (JSONObject) APIParser.getAPI_InfoEncyclopediaJSON().get("ship_types");
		ShipTypeList.addAll(APIShipType.keySet());
		Collections.sort(ShipTypeList);
	}

	@SuppressWarnings("unchecked")
	public void setTurretStats(String aTurret)
	{
		if (aTurret == null)
		{
			return;
		}

		JSONObject API_JSON = API_ArtilleryUpgradeJSONHashMap.get(aTurret);
		String API_module_id_str;
		String GP_turretKey = null;

		for (int i = 0; i < GPParser.getGameParamsKeySet().size(); i++)
		{
			API_module_id_str = (String) API_JSON.get("module_id_str");

			if (GPParser.getGameParamsKeySet().get(i).contains(API_module_id_str))
			{
				GP_turretKey = GPParser.getGameParamsKeySet().get(i);
			}
		}

		JSONObject module = (JSONObject) ShipUpgradeInfoJSON.get(GP_turretKey);
		JSONObject components = (JSONObject) module.get("components");
		JSONArray artillery = (JSONArray) components.get("artillery");

		GP_ArtilleryJSON = (JSONObject) GPParser.getShipJSON().get(artillery.get(artillery.size()-1));

		if (nation.equals("usa"))
		{
			GP_TurretJSON = (JSONObject) GP_ArtilleryJSON.get("HP_AGM_1");
		}
		else if (nation.equals("germany"))
		{
			GP_TurretJSON = (JSONObject) GP_ArtilleryJSON.get("HP_GGM_1");
		}
		else if (nation.equals("japan"))
		{
			GP_TurretJSON = (JSONObject) GP_ArtilleryJSON.get("HP_JGM_1");
		}
		else if (nation.equals("ussr"))
		{
			GP_TurretJSON = (JSONObject) GP_ArtilleryJSON.get("HP_RGM_1");
		}
		else if (nation.equals("uk"))
		{
			GP_TurretJSON = (JSONObject) GP_ArtilleryJSON.get("HP_BGM_1");
		}
		else if (nation.equals("poland"))
		{
			GP_TurretJSON = (JSONObject) GP_ArtilleryJSON.get("HP_WGM_2");
		}
		else if (nation.equals("pan_asia"))
		{
			GP_TurretJSON = (JSONObject) GP_ArtilleryJSON.get("HP_ZGM_1");
		}
		
		maxMainGunRange = (double) GP_ArtilleryJSON.get("maxDist") * maxDistCoef;
		
		sigmaCount = (double) GP_ArtilleryJSON.get("sigmaCount");
		
		if (GP_TurretJSON.get("numBarrels") instanceof Double)
		{
			numBarrels = (int) (double) GP_TurretJSON.get("numBarrels");
		}
		else if (GP_TurretJSON.get("numBarrels") instanceof Long)
		{
			numBarrels = (int) (long) GP_TurretJSON.get("numBarrels");
		}
		else
		{
			numBarrels = (int) GP_TurretJSON.get("numBarrels");
		}
		
		List<String> temp = new ArrayList<String>();
		temp.addAll(GP_ArtilleryJSON.keySet());
		for (int i = temp.size() - 1; i >= 0; i--)
		{
			if (!temp.get(i).contains("HP_"))
			{
				temp.remove(i);
			}				
		}						
		numTurrets = temp.size();			
		
		turretBarrelDiameter = (double) GP_TurretJSON.get("barrelDiameter");
		
		JSONArray ammoList = (JSONArray) GP_TurretJSON.get("ammoList");
		Collections.sort(ammoList);
					
		JSONObject APShell = null;
		JSONObject HEShell = null;
		
		for (int i = 0; i < ammoList.size(); i++)
		{
			if (   ammoList.get(i).toString().contains("_AP_") 
				|| ammoList.get(i).toString().contains("_B")
										
					)
			{
				APShell = (JSONObject) GPParser.getGameParams().get(ammoList.get(i));	
			}
		}
		for (int i = 0; i < ammoList.size(); i++)
		{
			if (	ammoList.get(i).toString().contains("_HE_") 
				 || ammoList.get(i).toString().contains("_OF")
					)
			{
				HEShell = (JSONObject) GPParser.getGameParams().get(ammoList.get(i));
			}
		}
		
		JSONArray tobj3;
		
		APShellSpeed = (double) APShell.get("bulletSpeed");
		APShellDMG = (double) APShell.get("alphaDamage");
		HEShellSpeed = (double) HEShell.get("bulletSpeed");
		HEShellDMG = (double) HEShell.get("alphaDamage");
		HEShellBurnProb = (double) HEShell.get("burnProb");
		
		tobj3 = (JSONArray) GP_TurretJSON.get("rotationSpeed");
		mainGunRotation = (double) tobj3.get(0);
		mainGunReload = (double) GP_TurretJSON.get("shotDelay");

		if (GP_TurretJSON.get("idealRadius") instanceof Double)
		{
			mainGunDispersionTangent = (double) GP_TurretJSON.get("idealRadius");
			mainGunDispersionTangent = Math.toRadians(mainGunDispersionTangent * 0.03);
			mainGunDispersionTangent = Math.tan(mainGunDispersionTangent);
		}
		else if (GP_TurretJSON.get("idealRadius") instanceof Long)
		{
			mainGunDispersionTangent = (long) GP_TurretJSON.get("idealRadius");
			mainGunDispersionTangent = Math.toRadians(mainGunDispersionTangent * 0.03);
			mainGunDispersionTangent = Math.tan(mainGunDispersionTangent);
		}
	}

	public void setHullStats(String aHull)
	{
		if (aHull == null)
		{
			return;
		}

		JSONObject API_JSON = API_HullUpgradeJSONHashMap.get(aHull);
		String API_module_id_str;
		String GP_hullKey = null;

		for (int i = 0; i < GPParser.getGameParamsKeySet().size(); i++)
		{
			API_module_id_str = (String) API_JSON.get("module_id_str");

			if (GPParser.getGameParamsKeySet().get(i).contains(API_module_id_str))
			{
				GP_hullKey = GPParser.getGameParamsKeySet().get(i);
			}
		}

		JSONObject module = (JSONObject) ShipUpgradeInfoJSON.get(GP_hullKey);
		JSONObject components = (JSONObject) module.get("components");
		JSONArray hull = (JSONArray) components.get("hull");

		int position = 0;

		for (int i = 0; i < API_HullUpgradeNameList.size(); i++)
		{
			if (API_HullUpgradeNameList.get(i).contains(aHull))
			{
				position = i;
			}
		}

		GP_HullJSON = (JSONObject) GPParser.getShipJSON().get(hull.get(hull.size()-1));

		maxRepairCost = (long) GP_HullJSON.get("maxRepairCost");

		maxHP = (double) GP_HullJSON.get("health");
		rudderShift = (double) GP_HullJSON.get("rudderTime") / 1.3;

		if (GP_HullJSON.get("maxSpeed") instanceof Double)
		{
			speed = (double) GP_HullJSON.get("maxSpeed") * speedCoef;
		}
		else if (GP_HullJSON.get("maxSpeed") instanceof Long)
		{
			speed = (double) (long) GP_HullJSON.get("maxSpeed") * speedCoef;
		}
		
		turningRadius = (double) GP_HullJSON.get("turningRadius");
		
		sConceal = (double) GP_HullJSON.get("visibilityFactor");
		aConceal = (double) GP_HullJSON.get("visibilityFactorByPlane");
		stealthFireSurfaceDetection = (double) GP_HullJSON.get("visibilityCoefGK");
		AAFireAirDetection = (double) GP_HullJSON.get("visibilityCoefATBAByPlane");

		JSONArray burnNodes1 = (JSONArray) GP_HullJSON.get("burnNodes");
		JSONArray burnNodes2 = (JSONArray) burnNodes1.get(0);
		burnTime = (double) burnNodes2.get(3);

		JSONArray floodParams = (JSONArray) GP_HullJSON.get("floodParams");
		floodTime = (double) floodParams.get(2);

		//If main turret's barrel diameter is less than 140 mm, turret has AuraFar by default.
		if (turretBarrelDiameter < 0.140 && GP_TurretJSON != null)
		{
			antiAirAuraDistanceFar = (double) GP_TurretJSON.get("antiAirAuraDistance") * 0.03;

			JSONObject AuraFar = (JSONObject) GP_TurretJSON.get("AuraFar");
			if (AuraFar != null)
			{
				JSONArray guns = (JSONArray) AuraFar.get("guns");

				AAFarDPS = (double) GP_TurretJSON.get("antiAirAuraStrength") * guns.size() * 100;
			}

			AAFarBarrelDiameter = turretBarrelDiameter;
		}
		else if (turretBarrelDiameter >= 0.140 || GP_TurretJSON == null)
		{
			List<String> ATBAList = new ArrayList<String>();

			ATBAList.addAll(GPParser.getShipJSON().keySet());
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
					String ATBAName = ATBAList.get(0);
					ATBA = (JSONObject) GPParser.getShipJSON().get(ATBAName);
					secondaryMaxDist = (double) ATBA.get("maxDist");
				}
				else if (ATBAList.size() <= position)
				{
					String ATBAName = ATBAList.get(ATBAList.size()-1);
					ATBA = (JSONObject) GPParser.getShipJSON().get(ATBAName);
					secondaryMaxDist = (double) ATBA.get("maxDist");
				}
				else
				{
					String ATBAName = ATBAList.get(position);
					ATBA = (JSONObject) GPParser.getShipJSON().get(ATBAName);
					secondaryMaxDist = (double) ATBA.get("maxDist");
				}

				//AA Aura Far
				if (ATBA.get("AuraFar") != null)
				{
					JSONObject AuraFar = (JSONObject) ATBA.get("AuraFar");
					JSONArray guns = (JSONArray) AuraFar.get("guns");
					String AAFarGunString = guns.get(0).toString();
					JSONObject AAFarGun = (JSONObject) ATBA.get(AAFarGunString);

					AAFarBarrelDiameter = (double) AAFarGun.get("barrelDiameter");

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
					AAFarDPS = AAFarDPS * 100;
				}
			}
		}

		List<String> AAMedium = new ArrayList<String>();

		AAMedium.addAll(GPParser.getShipJSON().keySet());
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
			JSONObject AirDefenseMedium = (JSONObject) GPParser.getShipJSON().get(AirDefenseNameMedium);
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
				AAMediumDPS = AAMediumDPS * 100;
			}

			List<String> AANear = new ArrayList<String>();

			AANear.addAll(GPParser.getShipJSON().keySet());
			for (int i = AANear.size() - 1; i >= 0; i--)
			{
				if (!AANear.get(i).toString().matches(".*(AirDefense).*"))
				{
					AANear.remove(i);
				}
			}
			Collections.sort(AANear);
			String AirDefenseNameNear = AANear.get(position);
			JSONObject AirDefenseNear = (JSONObject) GPParser.getShipJSON().get(AirDefenseNameNear);
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
				AANearDPS = AANearDPS * 100;
			}
			else if (AirDefenseMedium.get("AuraMedium1") != null)
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
					AANearDPS = AANearDPS * 100;
				}
			}
		}
	}

	public void setEngineStats(String anEngine)
	{
		if (anEngine == null)
		{
			return;
		}

		JSONObject API_JSON = API_EngineUpgradeJSONHashMap.get(anEngine);
		String API_module_id_str;
		String GP_engineKey = null;

		for (int i = 0; i < GPParser.getGameParamsKeySet().size(); i++)
		{
			API_module_id_str = (String) API_JSON.get("module_id_str");

			if (GPParser.getGameParamsKeySet().get(i).contains(API_module_id_str))
			{
				GP_engineKey = GPParser.getGameParamsKeySet().get(i);
			}
		}

		JSONObject module = (JSONObject) ShipUpgradeInfoJSON.get(GP_engineKey);
		JSONObject components = (JSONObject) module.get("components");
		JSONArray engine = (JSONArray) components.get("engine");

		engineObj = (JSONObject) GPParser.getShipJSON().get(engine.get(0));

		speedCoef = 1 + (double) engineObj.get("speedCoef");

		if (engineObj.get("histEnginePower") instanceof Double)
		{
			horsePower = (int) (double) engineObj.get("histEnginePower");
		}
		else if (engineObj.get("histEnginePower") instanceof Long)
		{
			horsePower = (int) (long) engineObj.get("histEnginePower");
		}
		else
		{
			horsePower = (int) engineObj.get("histEnginePower");
		}
	}

	public void setRadarStats(String aRadar)
	{
		if (aRadar == null)
		{
			return;
		}

		JSONObject API_JSON = API_SuoUpgradeJSONHashMap.get(aRadar);
		String API_module_id_str;
		String GP_radarKey = null;

		for (int i = 0; i < GPParser.getGameParamsKeySet().size(); i++)
		{
			API_module_id_str = (String) API_JSON.get("module_id_str");

			if (GPParser.getGameParamsKeySet().get(i).contains(API_module_id_str))
			{
				GP_radarKey = GPParser.getGameParamsKeySet().get(i);
			}
		}

		JSONObject module = (JSONObject) ShipUpgradeInfoJSON.get(GP_radarKey);
		JSONObject components = (JSONObject) module.get("components");
		JSONArray radar = (JSONArray) components.get("fireControl");

		JSONObject jso4 = (JSONObject) GPParser.getShipJSON().get(radar.get(0));
		maxDistCoef = (double) jso4.get("maxDistCoef");
	}

	public void setTorpedoStats(String aTorpedo)
	{
		if (aTorpedo == null || aTorpedo.equals("None"))
		{
			return;
		}

		JSONObject API_JSON = API_TorpedoesUpgradeJSONHashMap.get(aTorpedo);
		String API_module_id_str;
		String GP_torpedoKey = null;

		for (int i = 0; i < GPParser.getGameParamsKeySet().size(); i++)
		{
			API_module_id_str = (String) API_JSON.get("module_id_str");

			if (GPParser.getGameParamsKeySet().get(i).contains(API_module_id_str))
			{
				GP_torpedoKey = GPParser.getGameParamsKeySet().get(i);
			}
		}

		JSONObject module = (JSONObject) ShipUpgradeInfoJSON.get(GP_torpedoKey);
		JSONObject components = (JSONObject) module.get("components");
		JSONArray torpedo = (JSONArray) components.get("torpedoes");

		JSONObject tobj = null;
		JSONObject tobj2 = null;
		JSONArray tobj3 = null;

		if (torpedo != null)
		{
			tobj = (JSONObject) GPParser.getShipJSON().get(torpedo.get(0));
		}
		else
		{
			return;
		}

		if (getNation().equals("germany"))
		{
			tobj2 = (JSONObject) tobj.get("HP_GGT_1");
		}
		else if (getNation().equals("usa"))
		{
			tobj2 = (JSONObject) tobj.get("HP_AGT_1");
		}
		else if (getNation().equals("japan"))
		{
			if (APIParser.getShip_id_str().contains("PJSC008"))
			{
				tobj2 = (JSONObject) tobj.get("HP_JGT_3");
			}
			else
			{
				tobj2 = (JSONObject) tobj.get("HP_JGT_1");
			}
		}
		else if (getNation().equals("ussr"))
		{
			tobj2 = (JSONObject) tobj.get("HP_RGT_1");
		}
		else if (getNation().equals("pan_asia"))
		{
			tobj2 = (JSONObject) tobj.get("HP_ZGT_1");
		}
		else if (getNation().equals("poland"))
		{
			tobj2 = (JSONObject) tobj.get("HP_WGT_1");
		}
		else if (getNation().equals("uk"))
		{
			tobj2 = (JSONObject) tobj.get("HP_BGT_1");
		}

		torpDiameter = (double) tobj2.get("barrelDiameter");

		if (tobj2.get("numBarrels") instanceof Long)
		{
			numTubes = (int) (long) tobj2.get("numBarrels");
		}
		else if (tobj2.get("numBarrels") instanceof Double)
		{
			numTubes = (int) (double) tobj2.get("numBarrels");
		}
		else
		{
			numTubes = (int) tobj2.get("numBarrels");
		}

		numTorpTurrets = tobj.size();

		tobj3 = (JSONArray) tobj2.get("rotationSpeed");

		torpedoRotation = (double) tobj3.get(0);
		torpedoReload = (double) tobj2.get("shotDelay");

		JSONArray ammoList = (JSONArray) tobj2.get("ammoList");
		String ammo = (String) ammoList.get(0);

		JSONObject ammoObj = (JSONObject) GPParser.getGameParams().get(ammo);
		maxTorpedoRange = (double) ammoObj.get("maxDist") * 0.03;
		torpedoSpeed = (double) ammoObj.get("speed");
		torpedoVisibilityFactor = (double) ammoObj.get("visibilityFactor");
	}

	@SuppressWarnings("unchecked")
	private void setConsumablesList()
	{
		JSONObject ShipAbilities = (JSONObject) GPParser.getShipJSON().get("ShipAbilities");
		JSONObject AbilitySlot0 = (JSONObject) ShipAbilities.get("AbilitySlot0");
		JSONObject AbilitySlot1 = (JSONObject) ShipAbilities.get("AbilitySlot1");
		JSONObject AbilitySlot2 = (JSONObject) ShipAbilities.get("AbilitySlot2");
		JSONObject AbilitySlot3 = (JSONObject) ShipAbilities.get("AbilitySlot3");
		Abil0 = (JSONArray) AbilitySlot0.get("abils");
		Abil1 = (JSONArray) AbilitySlot1.get("abils");
		Abil2 = (JSONArray) AbilitySlot2.get("abils");
		Abil3 = (JSONArray) AbilitySlot3.get("abils");

		for (int i = 0; i < Abil0.size(); i++) {
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
	
	@SuppressWarnings("unchecked")
	private void setPermaflage()
	{
		JSONArray pf = (JSONArray) GPParser.getShipJSON().get("permoflages");
		JSONObject permoflage = (JSONObject) APIParser.getAPI_Exterior_PermoflageJSON();
		JSONObject camouflage = (JSONObject) APIParser.getAPI_Exterior_CamouflageJSON();
		
		List<JSONObject> camouList = new ArrayList<>();
		camouList.addAll(camouflage.values());
		camouList.sort((o1, o2) -> ((String) o1.get("name")).compareTo((String) o2.get("name")));		
		
		for (int i = 0; i < camouList.size(); i++)
		{
			long camouID = (long) camouList.get(i).get("exterior_id");
						
			for (int j = 0; j < GPParser.getGameParamsValues().size(); j++)
			{				
				if (GPParser.getGameParamsValues().get(j).get("id").equals(camouID))
				{					
					permaflage.add((String) camouList.get(i).get("name"));
					permaflageHashMap.put((String) camouList.get(i).get("name"), GPParser.getGameParamsValues().get(j));
					break;
				}
			}			
		}		
				
		if (pf != null)
		{
			for (int i = 0; i < pf.size(); i++)
			{
				JSONObject pfJSON = (JSONObject) GPParser.getGameParams().get(pf.get(i));
				long id = (long) pfJSON.get("id");
				
				JSONObject permo = (JSONObject) permoflage.get(String.valueOf(id));				
				
				permaflage.add((String) permo.get("name"));
				permaflageHashMap.put((String) permo.get("name"), pfJSON);
			}
		}	
	}
	
	public void setPermaflage2(String aPermaflage)
	{
		JSONObject pf = (JSONObject) permaflageHashMap.get(aPermaflage);		
		
		if (pf.get("afterBattleRepair") != null)
		{
			afterBattleRepair = (double) pf.get("afterBattleRepair");
		}
		else
		{
			afterBattleRepair = 1.00;
		}
		
		visibilityFactorPermaCamo = (double) pf.get("visibilityFactor");
		visibilityFactorByPlanePermaCamo = (double) pf.get("visibilityFactorByPlane");
		
		if (pf.get("expFactor") != null)
		{
			expFactorPermaCamo = (double) pf.get("expFactor") - 1;
		}
		else
		{
			expFactorPermaCamo = 0;
		}		
	}
}