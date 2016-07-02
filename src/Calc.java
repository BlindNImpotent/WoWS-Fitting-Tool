import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

/**
 * @author Aesis (BlindNImpotent)
 * Calculates ship stats according to modules and skills.
 */
public class Calc 
{
	private JSParser jsp;
	private long tier;
	private String nation;
	private String shiptype;
	private double health;
	private Object speed;
	private double rudderShift;
	
	private double maxMainGunRange;
	private double mainGunReload;
	private double mainGunRotation;
	private double mainGunDispersionTangent;
	private double mainGunDispersionRange;
	private Object numBarrels;
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
	
	private double AAFarDPS;
	private double AAMediumDPS;
	private double AANearDPS;
			
	private JSONObject crew;
	private JSONObject skills;
	
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
	
	public Calc(String ship, String turret, String aHull, String engine, String radar, String torpedo) throws FileNotFoundException, IOException, ParseException
	{
		jsp = new JSParser(ship);
		
		jsp.setRadarStats2(radar);
		jsp.setTurretStats2(turret);
		jsp.setEngineStats2(engine);
		jsp.setHullStats2(aHull);
		jsp.setTorpedoStats2(torpedo);
		
		tier = jsp.getTier();
		nation = jsp.getNation();
		shiptype = jsp.getShipType();
		health = jsp.getHealth();
		speed = jsp.getSpeed();
		rudderShift = jsp.getRudderShift();
		
		maxMainGunRange = jsp.getMaxMainGunRange() / 1000;
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
		
		AAFarDPS = jsp.getAAFarDPS();
		AAMediumDPS = jsp.getAAMediumDPS(); 
		AANearDPS = jsp.getAANearDPS();
		
		burnTime = jsp.getBurnTime();
		floodTime = jsp.getFloodTime();
		
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
				
		long pow = (long) Math.pow(10, 2);
		sConceal = sConceal * pow;
		aConceal = aConceal * pow;
		
		sConceal = sConceal * (double) CSM1.get("visibilityDistCoeff");;
		aConceal = aConceal * (double) CSM1.get("visibilityDistCoeff");;
		
		sConceal = Math.round(sConceal);
		aConceal = Math.round(aConceal);
		
		sConceal = sConceal / pow;
		aConceal = aConceal / pow;
	}
	
	
	/**
	 * 
	 */
	public void calcSurvivabilityExpert() // Skill 4
	{
		JSONObject healthSkill = (JSONObject) skills.get("SurvivalModifier");
		
		health = health + jsp.getTier() * (long) healthSkill.get("healthPerLevel");	
	}
	
	/**
	 * 
	 */
	public void calcAdvancedFiringTraining() // Skill 4
	{
		JSONObject AFT = (JSONObject) skills.get("AIGunsRangeModifier");
		double smallGunRangeCoefficient = (double) AFT.get("smallGunRangeCoefficient");
		double airDefenceRangeCoefficient = (double) AFT.get("airDefenceRangeCoefficient");
		
		if (jsp.getBarrelDiameter() < 0.139)
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
	public void calcBasicFiringTraining() //Skill 1
	{
		JSONObject BFT = (JSONObject) skills.get("AIGunsEfficiencyModifier");
		double smallGunReloadCoefficient = (double) BFT.get("smallGunReloadCoefficient");
		double airDefenceEfficiencyCoefficient = (double) BFT.get("airDefenceEfficiencyCoefficient");
		
		if (jsp.getBarrelDiameter() < 0.139)
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
		
		if (jsp.getBarrelDiameter() > 0.140)
		{
			mainGunRotation = mainGunRotation + bigGunBonus;
		}
		else if (jsp.getBarrelDiameter() > 0 && jsp.getBarrelDiameter() < 0.140)
		{
			mainGunRotation = mainGunRotation + smallGunBonus;
		}
	}
	
	/**
	 * 
	 */
	public void calcConcealmentExpert() //Skill 5
	{		
		long pow = (long) Math.pow(10, 2);
		sConceal = sConceal * pow;
		aConceal = aConceal * pow;
						
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
		
		sConceal = Math.round(sConceal);
		aConceal = Math.round(aConceal);
		
		sConceal = sConceal / pow;
		aConceal = aConceal / pow;
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
	
	public void calcDemolitionExpert() //Skill 4
	{
		if (jsp.getBarrelDiameter() > 0)
		{
			JSONObject FireProbabilityModifier = (JSONObject) skills.get("FireProbabilityModifier");
			HEShellBurnProb = HEShellBurnProb + ((double) FireProbabilityModifier.get("probabilityBonus") * 100);
		}
	}
	
	public void calcConcealCamo()
	{		
		long pow = (long) Math.pow(10, 2);
		sConceal = sConceal * pow;
		aConceal = aConceal * pow;
		
		sConceal = sConceal * 0.97;
		aConceal = aConceal * 1.00;
		
		sConceal = Math.round(sConceal);
		aConceal = Math.round(aConceal);
		
		sConceal = sConceal / pow;
		aConceal = aConceal / pow;
	}
	
	/**
	public void calcHY()
	{
		
	}
	public void calcJC()
	{
		
	}
	public void calcNF()
	{
		
	}
	public void calcZulu()
	{
		
	}
	public void calcJY2()
	{
		
	}
	public void calcID()
	{
		
	}
	public void calcIY()
	{
		
	}
	public void calcNE7()
	{
		
	}
	public void calcZH()
	{
		
	}
	public void calcIB3()
	{
		
	}
	public void calcSM()
	{
		
	}
	public void calcVL()
	{
		
	}
	public void calcMY6()
	{
		
	}
	public void calcPP()
	{
		
	}
	public void calcIX()
	{
		
	}
	public void calcEqualSpeed()
	{
		
	}
	public void calcJW1()
	{
		
	}
	*/
	
	/**
	 * 
	 * @return
	 */
	public double getRudderShift()
	{
		rudderShift = Math.round(rudderShift * 100.0) / 100.0;
		return rudderShift;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getMaxMainGunRange()
	{
		maxMainGunRange = Math.round(maxMainGunRange * 100.0) / 100.0;
		return maxMainGunRange;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getMainGunReload()
	{
		mainGunReload = Math.round(mainGunReload * 100.0) / 100.0;
		return mainGunReload;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getMainGunRotation()
	{
		mainGunRotation = Math.round(mainGunRotation * 100.0) / 100.0;
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
			rotationDeg = Math.round(180.0 / getMainGunRotation());
		}
		return rotationDeg; 
	}
	
	public double getMainGunDispersionTangent()
	{
		return mainGunDispersionTangent;
	}
	
	public double getMainGunDispersionRange()
	{
		return Math.round(mainGunDispersionRange * 100.0) / 100.0;
	}
	
	public Object getNumBarrels()
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
		return Math.round(HEShellBurnProb * 100.0) / 100.0;
	}
	
	public double getSecondaryMaxDist()
	{
		secondaryMaxDist = Math.round(secondaryMaxDist * 100.0) / 100.0 / 1000;
		return secondaryMaxDist;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getMaxTorpedoRange()
	{
		maxTorpedoRange = Math.round(maxTorpedoRange * 100.0) / 100.0;
		return maxTorpedoRange;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getTorpedoReload()
	{
		torpedoReload = Math.round(torpedoReload * 100.0) / 100.0;
		return torpedoReload;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getTorpedoRotation()
	{
		torpedoRotation = Math.round(torpedoRotation * 100.0) / 100.0;
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
			rotationDeg = Math.round(180.0 / getTorpedoRotation());
		}
		return rotationDeg;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getTorpedoSpeed()
	{
		torpedoSpeed = Math.round(torpedoSpeed * 100.0) / 100.0;
		return torpedoSpeed;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getSConceal()
	{
		sConceal = Math.round(sConceal * 100.0) / 100.0;
		return sConceal;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getAConceal()
	{
		aConceal = Math.round(aConceal * 100.0) / 100.0;
		return aConceal;
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
	public long getTier()
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
	public Object getSpeed()
	{
		return speed;
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
		antiAirAuraDistanceFar = Math.round(antiAirAuraDistanceFar * 100.0) / 100.0;
		return antiAirAuraDistanceFar;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getAntiAirAuraDistanceMedium()
	{
		antiAirAuraDistanceMedium = Math.round(antiAirAuraDistanceMedium * 100.0) / 100.0;
		return antiAirAuraDistanceMedium;	
	}
	
	/**
	 * 
	 * @return
	 */
	public double getAntiAirAuraDistanceNear()
	{
		antiAirAuraDistanceNear = Math.round(antiAirAuraDistanceNear * 100.0) / 100.0;
		return antiAirAuraDistanceNear;	
	}
	
	/**
	 * 
	 * @return
	 */
	public double getAAFarDPS()
	{
		AAFarDPS = Math.round(AAFarDPS);
		return AAFarDPS;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getAAMediumDPS()
	{
		AAMediumDPS = Math.round(AAMediumDPS);
		return AAMediumDPS;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getAANearDPS()
	{
		AANearDPS = Math.round(AANearDPS);
		return AANearDPS;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getStealthFireSurfaceDetection()
	{
		stealthFireSurfaceDetection = Math.round(stealthFireSurfaceDetection);
		return stealthFireSurfaceDetection;
	}	
	
	public double getAAFireAirDetection()
	{
		return Math.round(AAFireAirDetection);
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
		
		for (int i = 0; i < jsp.getTorpedoList().size(); i++)
		{			
			torpedoList.add(i, jsp.getTorpedoList().get(i).substring(8));		
		}		
		
		return torpedoList;		
	}	
	
	/**
	 * Test method to print out ship stats on console.
	 */
	@SuppressWarnings("unused")
	private void test()
	{
		System.out.println("Tier: " + getTier());
		System.out.println("Nation: " + getNation());		
		System.out.println("Ship: " + getShipType());
		System.out.println("Health: " + getHealth());
		System.out.println("Speed: " + getSpeed() + " kts");
		System.out.println("Rudder: " + getRudderShift() + " s");
		System.out.println("MG Range: " + getMaxMainGunRange() + " m");
		System.out.println("MG Reload: " + getMainGunReload() + " s");
		System.out.println("MG Rotation: " + getMainGunRotation() + " deg / s");
		System.out.println("MG Rotation: " + getMainGunRotationTime() + " s / 180 deg");
		System.out.println("Torp Range: " + getMaxTorpedoRange() + " m");
		System.out.println("Torp Reload: " + getTorpedoReload() + " s");
		System.out.println("Torp Rotation: " + getTorpedoRotation() + " deg / s");
		System.out.println("Torp Rotation: " + getTorpedoRotationTime() + " s / 180 deg");		
		System.out.println("S Conceal: " + getSConceal() + " km");
		System.out.println("A Conceal: " + getAConceal() + " km");
		System.out.println("Upgrade slots: " + getUpgradeSlots());
		System.out.println("Engine repair time: " + getEngineRepairTime() + " s");
		System.out.println("Burn time: " + getBurnTime() + " s");
		System.out.println("Flood time: " + getFloodTime() + " s");
	}	
}
