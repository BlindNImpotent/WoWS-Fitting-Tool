import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

/**
 * @author Aesis (BlindNImpotent)
 * Calculates ship stats according to modules and skills.
 */
public class Calc 
{
	private JSParser jsp;
	private int tier;
	private String nation;
	private String shiptype;
	private long maxRepairCost;
	private double health;
	private double speed;
	private double turningRadius;
	private int horsePower;
	private double rudderShift;
	
	private double maxMainGunRange;
	private double sigmaCount;
	private double mainGunReload;
	private double mainGunRotation;
	private double mainGunDispersionTangent;
	private double mainGunDispersionRange;
	private int numBarrels;
	private int numTurrets;
	private double APShellSpeed;
	private double APShellDMG;
	private double HEShellSpeed;
	private double HEShellDMG;
	private double HEShellBurnProb;
	
	private double secondaryMaxDist;
	
	private double torpedoReload;
	private double torpedoRotation;
	private double maxTorpedoRange;
	private double torpedoSpeed;
	private double torpedoVisibilityFactor;
	
	private double sConceal;
	private double aConceal;
	private double stealthFireSurfaceDetection;
	private double AAFireAirDetection;
	
	private int upgradeSlots;
	
	private double MainTurretHP;
	private double MainTurretAutoRepairTime;
		
	private double TorpedoHP;
	private double TorpedoAutoRepairTime;
	
	private double forwardEngineUpTime;
	private double backwardEngineUpTime;
	private double engineRepairTime;
	
	private double burnTime;
	private double floodTime;
	
	private double antiAirAuraDistanceFar;
	private double antiAirAuraDistanceMedium;
	private double antiAirAuraDistanceNear;
	
	private double AAFarBarrelDiameter;
	
	private double AAFarDPS;
	private double AAMediumDPS;
	private double AANearDPS;
			
	private JSONObject crew;
	private JSONObject skills;
	
	private List<String> flags = new ArrayList<String>();
	
	private int count2;
	private int count3;
	private int count4;
	
	private double reloadTime1;
	private double reloadTime2;
	private double reloadTime3;
	private double reloadTime4;
	
	private double expFactor;
	private double captainExpFactor;
	private static double PremAcc = 1.50;
	
	/**
	 * Constructor to set ship stats.
	 * @param ship Ship name
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */
	public Calc(String ship) throws FileNotFoundException, IOException, ParseException
	{
		jsp = new JSParser(ship);
	}
	
	public Calc(String ship, String turret, String aHull, String engine, String radar, String torpedo,
			String consume1, String consume2, String consume3, String consume4) 
			throws FileNotFoundException, IOException, ParseException
	{
		jsp = new JSParser(ship);
		
		jsp.setRadarStats2(radar);
		jsp.setTurretStats2(turret);
		jsp.setEngineStats2(engine);
		jsp.setHullStats2(aHull);
		jsp.setTorpedoStats2(torpedo);
		
		setConsume1(consume1);
		setConsume2(consume2);
		setConsume3(consume3);
		setConsume4(consume4);
		
		tier = jsp.getTier();
		nation = jsp.getNation();
		shiptype = jsp.getShipType();
		maxRepairCost = jsp.getMaxRepairCost();
		health = jsp.getHealth();
		speed = jsp.getSpeed();
		turningRadius = jsp.getTurningRadius();
		horsePower = jsp.getHorsePower();
		rudderShift = jsp.getRudderShift();
		
		maxMainGunRange = jsp.getMaxMainGunRange() / 1000;
		sigmaCount = jsp.getSigmaCount();
		mainGunReload = jsp.getMainGunReload();
		mainGunRotation = jsp.getMainGunRotation();		
		mainGunDispersionTangent = jsp.getMainGunDispersion();
		mainGunDispersionRange = maxMainGunRange * mainGunDispersionTangent * 2 * 1000;
		numBarrels = jsp.getNumBarrels();
		numTurrets = jsp.getNumTurrets();
		APShellSpeed = jsp.getAPShellSpeed();
		APShellDMG = jsp.getAPShellDMG();
		HEShellSpeed = jsp.getHEShellSpeed();
		HEShellDMG = jsp.getHEShellDMG();
		HEShellBurnProb = jsp.getHEShellBurnProb() * 100;
		
		secondaryMaxDist = jsp.getSecondaryMaxDist();
		
		torpedoReload = jsp.getTorpedoReload();
		torpedoRotation = jsp.getTorpedoRotation();
		maxTorpedoRange = jsp.getMaxTorpedoRange();
		torpedoSpeed = jsp.getTorpedoSpeed();
		torpedoVisibilityFactor = jsp.getTorpedoVisibilityFactor();
		
		sConceal = jsp.getSConceal();
		aConceal = jsp.getAConceal();
		stealthFireSurfaceDetection = jsp.getStealthFireSurfaceDetection();
		AAFireAirDetection = jsp.getAAFireAirDetection();
		
		upgradeSlots = jsp.getModuleSlots();		
		
		MainTurretHP = jsp.getMainTurretHP();
		MainTurretAutoRepairTime = jsp.getMainTurretAutoRepairTime();
		
		TorpedoHP = jsp.getTorpedoHP();
		TorpedoAutoRepairTime = jsp.getTorpedoAutoRepairTime();
		
		forwardEngineUpTime = (double) jsp.getEngine().get("forwardEngineUpTime");
		backwardEngineUpTime = (double) jsp.getEngine().get("backwardEngineUpTime");
		engineRepairTime = jsp.getEngineAutoRepairTime();
		
		antiAirAuraDistanceFar = jsp.getAntiAirAuraDistanceFar();
		antiAirAuraDistanceMedium = jsp.getAntiAirAuraDistanceMedium();
		antiAirAuraDistanceNear = jsp.getAntiAirAuraDistanceNear();
		
		AAFarBarrelDiameter = jsp.getAAFarBarrelDiameter();
		
		AAFarDPS = jsp.getAAFarDPS();
		AAMediumDPS = jsp.getAAMediumDPS(); 
		AANearDPS = jsp.getAANearDPS();
		
		burnTime = jsp.getBurnTime();
		floodTime = jsp.getFloodTime();
		
		flags = jsp.getFlagList();
		setSkills();
	}

	public void calcMainArmamentsMod1()  //Slot 1
	{
	    //"PCM030_MainWeapon_Mod_I"
		JSONObject MAM1 = (JSONObject) jsp.getGameParams().get("PCM030_MainWeapon_Mod_I"); 
		double GMCritProb = (double) MAM1.get("GMCritProb"); 
		double GMMaxHP = (double) MAM1.get("GMMaxHP");
		double GMRepairTime = (double) MAM1.get("GMRepairTime");
		double GTCritProb = (double) MAM1.get("GTCritProb");
		double GTMaxHP = (double) MAM1.get("GTMaxHP");
		double GTRepairTime = (double) MAM1.get("GTRepairTime");
		
		MainTurretHP = MainTurretHP * GMMaxHP;
		MainTurretAutoRepairTime = MainTurretAutoRepairTime * GMRepairTime;
		
		TorpedoHP = TorpedoHP * GTMaxHP;
		TorpedoAutoRepairTime = TorpedoAutoRepairTime * GTRepairTime;
		
	}
	
	public void calcAuxiliaryArmamentsMod1() //Slot 1
	{
		//"PCM031_SecondaryWeapon_Mod_I"
		JSONObject AAM1 = (JSONObject) jsp.getGameParams().get("PCM031_SecondaryWeapon_Mod_I"); 
		double ADMaxHP = (double) AAM1.get("ADMaxHP");
		double GSMaxHP = (double) AAM1.get("GSMaxHP");
		
		
		
		
	}
	
	public void calcMagazineMod1() //Slot 1
	{
		//"PCM032_PowderMagazine_Mod_I"
		JSONObject MM1 = (JSONObject) jsp.getGameParams().get("PCM032_PowderMagazine_Mod_I");		
		double PMDetonationProb = (double) MM1.get("PMDetonationProb");
		
		
		
	}
	
	public void calcAimingSystemsModification1() // Slot 2
	{
		//"PCM033_Guidance_Mod_I"
		JSONObject ASM1 = (JSONObject) jsp.getGameParams().get("PCM033_Guidance_Mod_I");		
		double GMIdealRadius = (double) ASM1.get("GMIdealRadius");
		double GSIdealRadius = (double) ASM1.get("GSIdealRadius");
		double GSMaxDist = (double) ASM1.get("GSMaxDist");
		double GTRotationSpeed = (double) ASM1.get("GTRotationSpeed");
		
		mainGunDispersionRange = mainGunDispersionRange * GMIdealRadius;
		torpedoRotation = torpedoRotation * GTRotationSpeed;
		secondaryMaxDist = secondaryMaxDist * GSMaxDist;
	}
	
	public void calcArtilleryPlottingRoomMod1US() //Slot 2
	{
		//"PCM028_FireControl_Mod_I_US"
		JSONObject APRM1 = (JSONObject) jsp.getGameParams().get("PCM028_FireControl_Mod_I_US");
		double GMMaxDist = (double) APRM1.get("GMMaxDist");
		double GSMaxDist = (double) APRM1.get("GSMaxDist");
		double GSIdealRadius = (double) APRM1.get("GSIdealRadius");
		
		maxMainGunRange = maxMainGunRange * GMMaxDist;
		secondaryMaxDist = secondaryMaxDist * GSMaxDist;
		

	}
	
	/**
	 * 
	 */
	public void calcMainBatteryMod2() //Slot 2
	{
		//"PCM006_MainGun_Mod_II"
		JSONObject MBM2 = (JSONObject) jsp.getGameParams().get("PCM006_MainGun_Mod_II");
		double GMRotationSpeed = (double) MBM2.get("GMRotationSpeed");
		double GMShotDelay = (double) MBM2.get("GMShotDelay");
		
		mainGunRotation = mainGunRotation * GMRotationSpeed; 
		mainGunReload = mainGunReload * GMShotDelay;	
	}
	
	/**
	 * 
	 */
	public void calcAAGunsMod2() //Slot2
	{
		//"PCM011_AirDefense_Mod_II"
		JSONObject AAGM2 = (JSONObject) jsp.getGameParams().get("PCM011_AirDefense_Mod_II");
		double AAMaxDist = (double) AAGM2.get("AAMaxDist");
		
		antiAirAuraDistanceFar = antiAirAuraDistanceFar * AAMaxDist;
		antiAirAuraDistanceMedium = antiAirAuraDistanceMedium * AAMaxDist;
		antiAirAuraDistanceNear = antiAirAuraDistanceNear * AAMaxDist;
		
	}
	
	public void calcSecondaryBatteryMod2() //Slot2
	{
		//"PCM012_SecondaryGun_Mod_II"
		JSONObject SBM2 = (JSONObject) jsp.getGameParams().get("PCM012_SecondaryGun_Mod_II");
		double GSMaxDist = (double) SBM2.get("GSMaxDist");
		double GSIdealRadius = (double) SBM2.get("GSIdealRadius");
		
		secondaryMaxDist = secondaryMaxDist * GSMaxDist;
		
	}
	
	public void calcArtilleryPlottingRoomMod2US() //Slot 3
	{
		//"PCM029_FireControl_Mod_II_US"
		JSONObject APR2 = (JSONObject) jsp.getGameParams().get("PCM029_FireControl_Mod_II_US");
		double GMIdealRadius = (double) APR2.get("GMIdealRadius");
		
		mainGunDispersionRange = mainGunDispersionRange * GMIdealRadius;
	}
	
	/**
	 * 
	 */
	public void calcGunFireControlSystemMod2() //Slot 3
	{
		//PCM015_FireControl_Mod_II
		JSONObject GFCSM2 = (JSONObject) jsp.getGameParams().get("PCM015_FireControl_Mod_II");
		double GMMaxDist = (double) GFCSM2.get("GMMaxDist");
		
		maxMainGunRange = maxMainGunRange * GMMaxDist;
	}
	
	/**
	 * 
	 */
	public void calcMainBatteryMod3() //Slot 3
	{
		//"PCM013_MainGun_Mod_III"
		JSONObject MBM3 = (JSONObject) jsp.getGameParams().get("PCM013_MainGun_Mod_III");
		double GMRotationSpeed = (double) MBM3.get("GMRotationSpeed");
		double GMShotDelay = (double) MBM3.get("GMShotDelay");
				
		mainGunReload = mainGunReload * GMShotDelay;
		mainGunRotation = mainGunRotation * GMRotationSpeed;
	}
	
	/**
	 * 
	 */
	public void calcAAGunsMod3() //Slot 3
	{
		//"PCM018_AirDefense_Mod_III"
		JSONObject AAGM3 = (JSONObject) jsp.getGameParams().get("PCM018_AirDefense_Mod_III");
		double AAAuraBonus = (double) AAGM3.get("AAAura");
		
		AAFarDPS = AAFarDPS * AAAuraBonus;
		AAMediumDPS = AAMediumDPS * AAAuraBonus;
		AANearDPS = AANearDPS * AAAuraBonus;
	}
	
	public void calcSecondaryBatteryMod3() //Slot 3
	{
		//"PCM019_SecondaryGun_Mod_III"
		JSONObject SBM3 = (JSONObject) jsp.getGameParams().get("PCM019_SecondaryGun_Mod_III");
		double GSShotDelay = (double) SBM3.get("GSShotDelay");
		
		
		
		
	}
	
	public void calcDamageControlSystemMod1() //Slot 4
	{
		//"PCM020_DamageControl_Mod_I"
		JSONObject DCSM1 = (JSONObject) jsp.getGameParams().get("PCM020_DamageControl_Mod_I");
		double burnProb = (double) DCSM1.get("burnProb");
		double floodProb = (double) DCSM1.get("floodProb");
		
		
		
		
	}
	
	public void calcPropulsionMod1() //Slot 4
	{
		//"PCM021_Engine_Mod_I"
		JSONObject PM1 = (JSONObject) jsp.getGameParams().get("PCM021_Engine_Mod_I");
		
		double engineCritProb = (double) PM1.get("engineCritProb");
		double engineRepairTimeBonus = (double) PM1.get("engineRepairTime");
		
		engineRepairTime = engineRepairTime * engineRepairTimeBonus;
		
	}
	
	public void calcSteeringGearsMod1() //Slot 4
	{
		//"PCM022_SteeringGear_Mod_I"
		JSONObject SGM1 = (JSONObject) jsp.getGameParams().get("PCM022_SteeringGear_Mod_I");
		double SGCritProb = (double) SGM1.get("SGCritProb");
		double SGRepairTime = (double) SGM1.get("SGRepairTime");
		
		
	}
	
	/**
	 * 
	 */
	public void calcDamageControlSystemMod2() //Slot 5
	{
		//"PCM023_DamageControl_Mod_II"
		JSONObject DCSM2 = (JSONObject) jsp.getGameParams().get("PCM023_DamageControl_Mod_II");
		double burnTimeBonus = (double) DCSM2.get("burnTime");
		double floodTimeBonus = (double) DCSM2.get("floodTime");
		
		burnTime = burnTime * burnTimeBonus;
		floodTime = floodTime * floodTimeBonus;
	}
	
	public void calcPropulsionMod2() //Slot 5
	{
		//"PCM024_Engine_Mod_II"
		JSONObject PM2 = (JSONObject) jsp.getGameParams().get("PCM024_Engine_Mod_II");
		double engineBackwardForsageMaxSpeed = (double) PM2.get("engineBackwardForsageMaxSpeed");
		double engineBackwardUpTime = (double) PM2.get("engineBackwardUpTime");
		double engineForwardForsageMaxSpeed = (double) PM2.get("engineForwardForsageMaxSpeed");
		double engineForwardUpTime = (double) PM2.get("engineForwardUpTime");
		
		forwardEngineUpTime = forwardEngineUpTime * engineForwardUpTime; 
		backwardEngineUpTime = backwardEngineUpTime * engineBackwardUpTime;
		
		
	}
	
	/**
	 * 
	 */
	public void calcSteeringGearsMod2() //Slot 5
	{
		//"PCM025_SteeringGear_Mod_II"
		JSONObject SGM2 = (JSONObject) jsp.getGameParams().get("PCM025_SteeringGear_Mod_II");
		double SGRudderTime = (double) SGM2.get("SGRudderTime");
		
		rudderShift = rudderShift * SGRudderTime;
	}
	
	public void calcTargetAcquisitionSystemMod1() //Slot 6
	{
		//"PCM026_LookoutStation_Mod_I"
		JSONObject TASM1 = (JSONObject) jsp.getGameParams().get("PCM026_LookoutStation_Mod_I");
		double visionDistCoeff = (double) TASM1.get("visionDistCoeff");
		double visionTorpedoCoeff = (double) TASM1.get("visionTorpedoCoeff");
		double visionXRayShipCoeff = (double) TASM1.get("visionXRayShipCoeff");
		
		
	}
	
	/**
	 * 
	 */
	public void calcConcealSystemMod1() //Slot 6
	{
		//"PCM027_ConcealmentMeasures_Mod_I"
		JSONObject CSM1 = (JSONObject) jsp.getGameParams().get("PCM027_ConcealmentMeasures_Mod_I");
				
		sConceal = sConceal * (double) CSM1.get("visibilityDistCoeff");
		aConceal = aConceal * (double) CSM1.get("visibilityDistCoeff");
	}
	
	
	/**
	 * 
	 */
	public void calcBasicFiringTraining() //Skill 1
	{
		JSONObject BFT = (JSONObject) skills.get("AIGunsEfficiencyModifier");
		double smallGunReloadCoefficient = (double) BFT.get("smallGunReloadCoefficient");
		double airDefenceEfficiencyCoefficient = (double) BFT.get("airDefenceEfficiencyCoefficient");
		
		if (jsp.getTurretBarrelDiameter() < 0.139)
		{
			mainGunReload = mainGunReload * smallGunReloadCoefficient;
		}	
		
		AAFarDPS = AAFarDPS * airDefenceEfficiencyCoefficient;
		AAMediumDPS = AAMediumDPS * airDefenceEfficiencyCoefficient;
		AANearDPS = AANearDPS * airDefenceEfficiencyCoefficient;		
	}
	
	/**
	 * 
	 */
	public void calcBasicsOfSurvivability() //Skill 1
	{
		JSONObject BoS = (JSONObject) skills.get("AutoRepairModifier");
		double critTimeCoefficient = (double) BoS.get("critTimeCoefficient");
		
		floodTime = floodTime * critTimeCoefficient;
		burnTime = burnTime * critTimeCoefficient;		
	}	
	
	/**
	 * 
	 */
	public void calcExpertMarksman() //Skill 2
	{
		JSONObject EM = (JSONObject) skills.get("MainGunsRotationModifier");
		double bigGunBonus = (double) EM.get("bigGunBonus");
		double smallGunBonus = (double) EM.get("smallGunBonus");
		
		if (jsp.getTurretBarrelDiameter() > 0.140)
		{
			mainGunRotation = mainGunRotation + bigGunBonus;
		}
		else if (jsp.getTurretBarrelDiameter() > 0 && jsp.getTurretBarrelDiameter() < 0.140)
		{
			mainGunRotation = mainGunRotation + smallGunBonus;
		}
	}
	
	/**
	 * 
	 */
	public void calcTorpedoArmamentExpertise() //Skill 2
	{
		JSONObject TorpedoReloadModifier = (JSONObject) skills.get("TorpedoReloadModifier");
		torpedoReload = torpedoReload * (double) TorpedoReloadModifier.get("launcherCoefficient");
	}
	
	/**
	 * 
	 */
	public void calcTorpedoAcceleration() //Skill 3
	{
		if (jsp.getTorpedoSpeed() > 0)
		{
			JSONObject TorpedoAcceleratorModifier = (JSONObject) skills.get("TorpedoAcceleratorModifier");		
			maxTorpedoRange = maxTorpedoRange * (double) TorpedoAcceleratorModifier.get("torpedoRangeCoefficient");
			torpedoSpeed = torpedoSpeed + (double) TorpedoAcceleratorModifier.get("torpedoSpeedBonus");
		}
	}
	
	public void calcHighAlert() // Skill 3
	{
		if (reloadTime1 > 0)
		{
			JSONObject EmergencyTeamCooldownModifier = (JSONObject) skills.get("EmergencyTeamCooldownModifier");
			reloadTime1 = reloadTime1 * (double) EmergencyTeamCooldownModifier.get("reloadCoefficient");
		}
	}
	
	public void calcSuperintendent() // Skill 3
	{
		count2 = count2 + 1;
		count3 = count3 + 1;
		count4 = count4 + 1;
	}
	
	public void calcDemolitionExpert() //Skill 4
	{
		if (jsp.getTurretBarrelDiameter() > 0)
		{
			JSONObject FireProbabilityModifier = (JSONObject) skills.get("FireProbabilityModifier");
			HEShellBurnProb = HEShellBurnProb + ((double) FireProbabilityModifier.get("probabilityBonus") * 100);
		}
	}
	
	
	/**
	 * 
	 */
	public void calcAdvancedFiringTraining() // Skill 4
	{
		JSONObject AFT = (JSONObject) skills.get("AIGunsRangeModifier");
		double smallGunRangeCoefficient = (double) AFT.get("smallGunRangeCoefficient");
		double airDefenceRangeCoefficient = (double) AFT.get("airDefenceRangeCoefficient");
		
		if (jsp.getTurretBarrelDiameter() < 0.139)
		{
			maxMainGunRange = maxMainGunRange * smallGunRangeCoefficient;
		}
		
		antiAirAuraDistanceFar = antiAirAuraDistanceFar * airDefenceRangeCoefficient;
		antiAirAuraDistanceMedium = antiAirAuraDistanceMedium * airDefenceRangeCoefficient;
		antiAirAuraDistanceNear = antiAirAuraDistanceNear * airDefenceRangeCoefficient;		
		
		secondaryMaxDist = secondaryMaxDist * smallGunRangeCoefficient;
	}

	/**
	 * 
	 */
	public void calcSurvivabilityExpert() // Skill 4
	{
		JSONObject healthSkill = (JSONObject) skills.get("SurvivalModifier");
		
		health = health + jsp.getTier() * (long) healthSkill.get("healthPerLevel");	
	}
	
	public void calcMFCAA() // Skill 4
	{
		JSONObject CentralAirDefenceModifier = (JSONObject) skills.get("CentralAirDefenceModifier");
		
		if (AAFarBarrelDiameter > 0.085)
		{
			AAFarDPS = AAFarDPS * (double) CentralAirDefenceModifier.get("airDefenceSelectedTargetCoefficient");
		}
	}
	
	/**
	 * 
	 */
	public void calcConcealmentExpert() //Skill 5
	{							
		JSONObject vModifier = (JSONObject) skills.get("VisibilityModifier");
		
		if (jsp.getShipType().equals("Battleship"))
		{
			sConceal = sConceal * (double) vModifier.get("battleshipCoefficient");
			aConceal = aConceal * (double) vModifier.get("battleshipCoefficient");
		}
		else if (jsp.getShipType().equals("Cruiser"))
		{
			sConceal = sConceal * (double) vModifier.get("cruiserCoefficient");
			aConceal = aConceal * (double) vModifier.get("cruiserCoefficient");
		}
		else if (jsp.getShipType().equals("Destroyer"))
		{
			sConceal = sConceal * (double) vModifier.get("destroyerCoefficient");
			aConceal = aConceal * (double) vModifier.get("destroyerCoefficient");
		}
		else
		{
			sConceal = sConceal * (double) vModifier.get("aircraftCarrierCoefficient");
			aConceal = aConceal * (double) vModifier.get("aircraftCarrierCoefficient");
		}
	}
	
	public void calcJoAT() // Skill 5
	{
		JSONObject AllSkillsCooldownModifier = (JSONObject) skills.get("AllSkillsCooldownModifier");
		double reloadCoefficient = (double) AllSkillsCooldownModifier.get("reloadCoefficient"); 
				
		reloadTime1 = reloadTime1 * reloadCoefficient;
		reloadTime2 = reloadTime2 * reloadCoefficient;
		reloadTime3 = reloadTime3 * reloadCoefficient;
		reloadTime4 = reloadTime4 * reloadCoefficient;
	}
	
	
	
	public void calcType1Camo()
	{	
		sConceal = sConceal * 0.97;
		aConceal = aConceal * 1.00;
	}
	
	public void calcType3Camo()
	{	
		sConceal = sConceal * 0.97;
		aConceal = aConceal * 1.00;
		expFactor = 100;
		captainExpFactor = 100;
	}
	
	public void calcType5Camo()
	{	
		sConceal = sConceal * 0.97;
		aConceal = aConceal * 1.00;
	}
	
	public void calcType6Camo()
	{	
		sConceal = sConceal * 0.97;
		aConceal = aConceal * 1.00;
		expFactor = 100;
		captainExpFactor = 100;
	}
	
	public double getExpFactor()
	{
		return expFactor;
	}
	
	public double getCaptainExpFactor()
	{
		return captainExpFactor;
	}
	
	public void setPermaflage2(String aPermaflage)
	{
		jsp.setPermaflage2(aPermaflage);
	}
	
	public void calcPermaflage()
	{
		sConceal = sConceal * jsp.getVisibilityFactorPermaCamo();
		aConceal = aConceal * jsp.getVisibilityFactorByPlanePermaCamo();
		
		maxRepairCost = (long) (maxRepairCost * jsp.getAfterBattleRrepair());
		expFactor = jsp.getExpFactorPermaCamo() * 100;
		captainExpFactor = jsp.getExpFactorPermaCamo() * 100;
	}	
	
	public void calcHY()
	{
		
	}
	public void calcJC()
	{
		
	}
	public void calcNF()
	{
		JSONObject flag = null; 
		
		for (int i = 0; i < flags.size(); i++)
		{
			if (flags.get(i).contains("NF"))
			{
				flag = (JSONObject) jsp.getGameParams().get(flags.get(i));
			}
		}
		
		reloadTime1 = reloadTime1 * (double) flag.get("abilReloadTimeFactor");
		reloadTime2 = reloadTime2 * (double) flag.get("abilReloadTimeFactor");
		reloadTime3 = reloadTime3 * (double) flag.get("abilReloadTimeFactor");
		reloadTime4 = reloadTime4 * (double) flag.get("abilReloadTimeFactor");
	}
	public void calcZulu()
	{
		
	}
	public void calcJY2()
	{
		JSONObject flag = null; 
		
		for (int i = 0; i < flags.size(); i++)
		{
			if (flags.get(i).contains("JY"))
			{
				flag = (JSONObject) jsp.getGameParams().get(flags.get(i));
			}
		}
		
		floodTime = floodTime * (double) flag.get("floodTime");		
	}
	public void calcID()
	{
		
	}
	public void calcIY()
	{
		JSONObject flag = null; 
		
		for (int i = 0; i < flags.size(); i++)
		{
			if (flags.get(i).contains("IY"))
			{
				flag = (JSONObject) jsp.getGameParams().get(flags.get(i));
			}
		}
		
		burnTime = burnTime * (double) flag.get("burnTime");
	}
	public void calcNE7()
	{
		JSONObject flag = null; 
		
		for (int i = 0; i < flags.size(); i++)
		{
			if (flags.get(i).contains("NE7"))
			{
				flag = (JSONObject) jsp.getGameParams().get(flags.get(i));
			}
		}
		
		AAFarDPS = AAFarDPS * (double) flag.get("AAPassiveAura");
		AAMediumDPS = AAMediumDPS * (double) flag.get("AAPassiveAura");
		AANearDPS = AANearDPS * (double) flag.get("AAPassiveAura");
	}
	public void calcZH()
	{
		JSONObject flag = null; 

		for (int i = 0; i < flags.size(); i++)
		{
			if (flags.get(i).contains("ZH"))
			{
				flag = (JSONObject) jsp.getGameParams().get(flags.get(i));
			}
		}
		
		double captainExpBonus = (double) flag.get("crewExpFactor") * 100 - 100;
		
		captainExpFactor = captainExpFactor + captainExpBonus;
	}
	public void calcIB3()
	{
		JSONObject flag = null; 
		
		for (int i = 0; i < flags.size(); i++)
		{
			if (flags.get(i).contains("IB3"))
			{
				flag = (JSONObject) jsp.getGameParams().get(flags.get(i));
			}
		}
		
		maxRepairCost = (long) (maxRepairCost * (double) flag.get("afterBattleRepair"));		
	}
	public void calcSM()
	{
		JSONObject flag = null; 
		
		for (int i = 0; i < flags.size(); i++)
		{
			if (flags.get(i).contains("SM"))
			{
				flag = (JSONObject) jsp.getGameParams().get(flags.get(i));
			}
		}
		
		speed = speed * (double) flag.get("speedCoef");		
	}
	public void calcVL()
	{
		JSONObject flag = null; 
		
		for (int i = 0; i < flags.size(); i++)
		{
			if (flags.get(i).contains("VL"))
			{
				flag = (JSONObject) jsp.getGameParams().get(flags.get(i));
			}
		}
		
		if (jsp.getTurretBarrelDiameter() < 0.160)
		{
			HEShellBurnProb = HEShellBurnProb + ((double) flag.get("burnChanceFactorSmall") * 100 - 100);
		}
		else if (jsp.getTurretBarrelDiameter() > 0.160)
		{
			HEShellBurnProb = HEShellBurnProb + ((double) flag.get("burnChanceFactorBig") * 100 - 100);
		}
	}
	public void calcMY6()
	{
		JSONObject flag = null; 
		
		for (int i = 0; i < flags.size(); i++)
		{
			if (flags.get(i).contains("MY6"))
			{
				flag = (JSONObject) jsp.getGameParams().get(flags.get(i));
			}
		}
		
		secondaryMaxDist = secondaryMaxDist * (double) flag.get("GSMaxDist");
	}
	public void calcPP()
	{
		
	}
	public void calcIX()
	{
		JSONObject flag = null; 
		
		for (int i = 0; i < flags.size(); i++)
		{
			if (flags.get(i).contains("IX"))
			{
				flag = (JSONObject) jsp.getGameParams().get(flags.get(i));
			}
		}
		
		if (jsp.getTurretBarrelDiameter() < 0.160)
		{
			HEShellBurnProb = HEShellBurnProb + ((double) flag.get("burnChanceFactorSmall") * 100 - 100);
		}
		else if (jsp.getTurretBarrelDiameter() > 0.160)
		{
			HEShellBurnProb = HEShellBurnProb + ((double) flag.get("burnChanceFactorBig") * 100 - 100);
		}		
	}
	public void calcESCL()
	{
		JSONObject flag = null; 
		
		for (int i = 0; i < flags.size(); i++)
		{
			if (flags.get(i).contains("EqualSpeed"))
			{
				flag = (JSONObject) jsp.getGameParams().get(flags.get(i));
			}
		}
		
		double expBonus = (double) flag.get("expFactor") * 100 - 100;
		
		expFactor = expFactor + expBonus;
		captainExpFactor = captainExpFactor + expBonus;
	}
	public void calcJW1()
	{
		
	}
	
	
	public void calcManualAA()
	{
		AAFarDPS = AAFarDPS * 1.3;
		AAMediumDPS = AAMediumDPS * 1.3;
		AANearDPS = AANearDPS * 1.3;
	}
	
	
	public void calcPremAcc()
	{
		expFactor = expFactor * PremAcc;
		captainExpFactor = captainExpFactor * PremAcc;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getRudderShift()
	{
		return rudderShift;
	}
	
	public double getTurretBarrelDiameter()
	{
		return jsp.getTurretBarrelDiameter() * 1000;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getMaxMainGunRange()
	{
		return maxMainGunRange;
	}
	
	public double getSigmaCount()
	{
		return sigmaCount;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getMainGunReload()
	{
		return mainGunReload;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getMainGunRotation()
	{
		return mainGunRotation;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getMainGunRotationTime()
	{
		double rotationDeg = 0;
		if (getMainGunRotation() != 0 )
		{
			rotationDeg = 180.0 / getMainGunRotation();
		}
		return rotationDeg; 
	}
	
	public double getMainGunDispersionTangent()
	{
		return mainGunDispersionTangent;
	}
	
	public double getMainGunDispersionRange()
	{
		return mainGunDispersionRange;
	}
	
	public int getNumBarrels()
	{
		return numBarrels;
	}
	
	public int getNumTurrets()
	{
		return numTurrets;
	}
	
	public double getAPShellSpeed()
	{
		return APShellSpeed;
	}
	
	public double getAPShellDMG()
	{
		return APShellDMG;
	}
	
	public double getHEShellSpeed()
	{
		return HEShellSpeed;
	}
	
	public double getHEShellDMG()
	{
		return HEShellDMG;
	}
	
	public double getHEShellBurnProb()
	{
		return HEShellBurnProb;
	}
	
	public double getSecondaryMaxDist()
	{
		secondaryMaxDist = secondaryMaxDist / 1000;
		return secondaryMaxDist;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getMaxTorpedoRange()
	{
		return maxTorpedoRange;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getTorpedoReload()
	{
		return torpedoReload;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getTorpedoRotation()
	{
		return torpedoRotation;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getTorpedoRotationTime()
	{
		double rotationDeg = 0;
		if (getTorpedoRotation() != 0)
		{
			rotationDeg = 180.0 / getTorpedoRotation();
		}
		return rotationDeg;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getTorpedoSpeed()
	{
		return torpedoSpeed;
	}
	
	public double getTorpedoVisibilityFactor()
	{
		return torpedoVisibilityFactor;
	}
	
	public double getTorpDiameter()
	{
		return jsp.getTorpDiameter() * 1000;
	}
	
	public int getTorpTubes()
	{
		return jsp.getTorpTubes();
	}
	
	public int getTorpTurrets()
	{
		return jsp.getTorpTurrets();
	}
	
	/**
	 * 
	 * @return
	 */
	public double getSConceal()
	{
		return sConceal;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getAConceal()
	{
		return aConceal;
	}
	
	public long getMaxRepairCost()
	{
		return maxRepairCost;
	}	
	
	/**
	 * 
	 * @return
	 */
	public double getHealth()
	{		
		return health;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getTier()
	{
		return tier;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getNation()
	{
		return nation;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getShipType()
	{
		return shiptype;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getSpeed()
	{
		return speed;
	}
	
	public double getTurningRadius()
	{
		return turningRadius;
	}
	
	public int getHorsePower()
	{
		return horsePower;
	}
	
	/**
	 * 
	 * @return
	 */
	public JSParser getJSP()
	{
		return jsp;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getUpgradeSlots()
	{
		return upgradeSlots;
	}	
	
	/**
	 * 
	 * @return
	 */
	public double getBurnTime()
	{
		return burnTime;
	}
		
	/**
	 * 
	 * @return
	 */
	public double getFloodTime()
	{
		return floodTime;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getEngineRepairTime()
	{
		return engineRepairTime;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getAntiAirAuraDistanceFar()
	{
		return antiAirAuraDistanceFar;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getAntiAirAuraDistanceMedium()
	{
		return antiAirAuraDistanceMedium;	
	}
	
	/**
	 * 
	 * @return
	 */
	public double getAntiAirAuraDistanceNear()
	{
		return antiAirAuraDistanceNear;	
	}
	
	public double getAAFarBarrelDiameter()
	{
		return AAFarBarrelDiameter;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getAAFarDPS()
	{
		return AAFarDPS;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getAAMediumDPS()
	{
		return AAMediumDPS;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getAANearDPS()
	{
		return AANearDPS;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getStealthFireSurfaceDetection()
	{
		return stealthFireSurfaceDetection;
	}	
	
	public double getAAFireAirDetection()
	{
		return AAFireAirDetection;
	}
	
	/**
	 * Sets crew code according to nation.
	 * @return
	 */
	private String getCrewCode()
	{		
		String crewNation = null;
		
		switch (jsp.getNation())
		{
			case "USA":	
				crewNation = "PAW001_DefaultCrew";
				break;
			case "Japan": 
				crewNation = "PJW001_DefaultCrew";
				break;
			case "Russia":	
				crewNation = "PRW001_DefaultCrew";
				break;
			case "Germany":	
				crewNation = "PGW001_DefaultCrew";
				break;
			case "Pan_Asia":	
				crewNation = "PZW001_DefaultCrew";
				break;
			case "Poland":	
				crewNation = "PWW001_DefaultCrew";
				break;		
			case "United_Kingdom":	
				crewNation = "PBW001_DefaultCrew";
				break;
			case "France":	
				crewNation = "PFW001_DefaultCrew";
			default:
				break;
		}
		return crewNation;
	}
	
	/**
	 * Sets skill data of crew nation.
	 */
	private void setSkills()
	{
		crew = (JSONObject) jsp.getGameParams().get(getCrewCode());
		skills = (JSONObject) crew.get("Skills");
	}
	
	public List<String> getFlagList()
	{
		List<String> flagList = new ArrayList<String>();
		flagList.add("None");	
		
		for (int i = 0; i < jsp.getFlagList().size(); i++)
		{			
			flagList.add(i+1, jsp.getFlagList().get(i).substring(8, 11));
		}
		
		return flagList;
	}	
	
	/**
	 * 
	 * @return
	 */
	public List<String> getModule1()
	{
		List<String> mod1 = new ArrayList<String>();
		mod1.add("None");	
		
		for (int i = 0; i < jsp.getModule1().size(); i++)
		{			
			mod1.add(i+1, jsp.getModule1().get(i).substring(7));		
		}
		
		return mod1;
	}	
	
	/**
	 * 
	 * @return
	 */
	public List<String> getModule2()
	{
		List<String> mod2 = new ArrayList<String>();
		mod2.add("None");	
		
		for (int i = 0; i < jsp.getModule2().size(); i++)
		{			
			mod2.add(i+1, jsp.getModule2().get(i).substring(7));		
		}
		
		return mod2;	
	}
	
	/**
	 * 
	 * @return
	 */
	public List<String> getModule3()
	{
		List<String> mod3 = new ArrayList<String>();
		mod3.add("None");	
		
		for (int i = 0; i < jsp.getModule3().size(); i++)
		{			
			mod3.add(i+1, jsp.getModule3().get(i).substring(7));		
		}
		
		return mod3;	
	}
	
	/**
	 * 
	 * @return
	 */
	public List<String> getModule4()
	{
		List<String> mod4 = new ArrayList<String>();
		mod4.add("None");	
		
		for (int i = 0; i < jsp.getModule4().size(); i++)
		{			
			mod4.add(i+1, jsp.getModule4().get(i).substring(7));		
		}
		
		return mod4;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<String> getModule5()
	{
		List<String> mod5 = new ArrayList<String>();
		mod5.add("None");	
		
		for (int i = 0; i < jsp.getModule5().size(); i++)
		{			
			mod5.add(i+1, jsp.getModule5().get(i).substring(7));		
		}
		
		return mod5;	
	}
	
	/**
	 * 
	 * @return
	 */
	public List<String> getModule6()
	{
		List<String> mod6 = new ArrayList<String>();
		mod6.add("None");	
		
		for (int i = 0; i < jsp.getModule6().size(); i++)
		{			
			mod6.add(i+1, jsp.getModule6().get(i).substring(7));		
		}
		
		return mod6;
	}	
	
	public List<String> getConsumable1()
	{		
		List<String> Ability0 = new ArrayList<String>();
		Ability0.add("None");
		
		for (int i = 0; i < jsp.getAbility0().size(); i++)
		{
			Ability0.add(i+1, jsp.getAbility0().get(i));
		}
		
		return Ability0;
	}
	
	public void setConsume1(String consumable)
	{
		JSONArray a1;
		
		for (int i = 0; i < jsp.getAbil0().size(); i++)
		{
			a1 = (JSONArray) jsp.getAbil0().get(i);
			if (a1.get(0).toString().contains(consumable))
			{
				JSONObject a2 = (JSONObject) jsp.getGameParams().get(consumable);
				JSONObject a3 = (JSONObject) a2.get(a1.get(1));
				reloadTime1 = (double) a3.get("reloadTime");
			}
		}
	}
	
	public List<String> getConsumable2()
	{
		List<String> Ability1 = new ArrayList<String>();
		Ability1.add("None");
		
		for (int i = 0; i < jsp.getAbility1().size(); i++)
		{
			Ability1.add(i+1, jsp.getAbility1().get(i));
		}
				
		return Ability1;
	}
	
	public void setConsume2(String consumable)
	{
		JSONArray a1;
		
		for (int i = 0; i < jsp.getAbil1().size(); i++)
		{
			a1 = (JSONArray) jsp.getAbil1().get(i);
			if (a1.get(0).toString().contains(consumable))
			{
				JSONObject a2 = (JSONObject) jsp.getGameParams().get(consumable);
				JSONObject a3 = (JSONObject) a2.get(a1.get(1));
				count2 = count2 + (int) (long) a3.get("numConsumables");
				reloadTime2 = (double) a3.get("reloadTime");
			}
		}		
	}
	
	public List<String> getConsumable3()
	{
		List<String> Ability2 = new ArrayList<String>();
		Ability2.add("None");
		
		for (int i = 0; i < jsp.getAbility2().size(); i++)
		{
			Ability2.add(i+1, jsp.getAbility2().get(i));
		}
		return Ability2;
	}
	
	public void setConsume3(String consumable)
	{
		JSONArray a1;
		
		for (int i = 0; i < jsp.getAbil2().size(); i++)
		{
			a1 = (JSONArray) jsp.getAbil2().get(i);
			if (a1.get(0).toString().contains(consumable))
			{
				JSONObject a2 = (JSONObject) jsp.getGameParams().get(consumable);
				JSONObject a3 = (JSONObject) a2.get(a1.get(1));
				count3 = count3 + (int) (long) a3.get("numConsumables");
				reloadTime3 = (double) a3.get("reloadTime");
			}
		}
	}
	
	public List<String> getConsumable4()
	{
		List<String> Ability3 = new ArrayList<String>();
		Ability3.add("None");
		
		for (int i = 0; i < jsp.getAbility3().size(); i++)
		{
			Ability3.add(i+1, jsp.getAbility3().get(i));
		}
		return Ability3;
	}
	
	public void setConsume4(String consumable)
	{
		JSONArray a1;
		
		for (int i = 0; i < jsp.getAbil3().size(); i++)
		{
			a1 = (JSONArray) jsp.getAbil3().get(i);
			if (a1.get(0).toString().contains(consumable))
			{
				JSONObject a2 = (JSONObject) jsp.getGameParams().get(consumable);
				JSONObject a3 = (JSONObject) a2.get(a1.get(1));
				count4 = count4 + (int) (long) a3.get("numConsumables");
				reloadTime4 = (double) a3.get("reloadTime");
			}
		}
	}
	
	public int getConsume2Count()
	{
		return count2;
	}
	
	public int getConsume3Count()
	{
		return count3;
	}
	
	public int getConsume4Count()
	{
		return count4;
	}
	
	public double getConsume1Reload()
	{
		return reloadTime1;
	}
	
	public double getConsume2Reload()
	{
		return reloadTime2;
	}
	
	public double getConsume3Reload()
	{
		return reloadTime3;
	}
	
	public double getConsume4Reload()
	{
		return reloadTime4;
	}
	
	
	public List<String> getPermaflage()
	{	
		return jsp.getPermaflage();
	}
	

	public List<String> getTurretList()
	{
		List<String> turretList = new ArrayList<String>();
		
		
		for (int i = 0; i < jsp.getTurretList().size(); i++)
		{
			turretList.add(i, jsp.getTurretList().get(i).substring(8));
		}
		return turretList;
	}
	
	
	public List<String> getHullList()
	{
		List<String> hullList = new ArrayList<String>();
		
		for (int i = 0; i < jsp.getHullList().size(); i++)
		{
			hullList.add(i, jsp.getHullList().get(i).substring(8));
		}
		return hullList;
	}
	
	
	public List<String> getEngineList()
	{
		List<String> engineList = new ArrayList<String>();		
		
		for (int i = 0; i < jsp.getEngineList().size(); i++)
		{			
			engineList.add(i, jsp.getEngineList().get(i).substring(8));		
		}			
		return engineList;
	}
	
	public List<String> getRadarList()
	{
		List<String> radarList = new ArrayList<String>();		
		
		for (int i = 0; i < jsp.getRadarList().size(); i++)
		{			
			radarList.add(i, jsp.getRadarList().get(i).substring(4));		
		}			
		return radarList;
	}
	
	public List<String> getTorpedoList()
	{
		List<String> torpedoList = new ArrayList<String>();
		
		if (jsp.getTorpedoList().size() == 0)
		{
			torpedoList.add("None");
			return torpedoList;
		}		
		
		else
		{
			for (int i = 0; i < jsp.getTorpedoList().size(); i++)
			{			
				torpedoList.add(i, jsp.getTorpedoList().get(i).substring(8));		
			}		
		
			return torpedoList;
		}
	}
}
