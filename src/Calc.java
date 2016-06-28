import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

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
	
	private double torpedoReload;
	private double torpedoRotation;
	private double maxTorpedoRange;
	private double torpedoSpeed;
	
	private double sConceal;
	private double aConceal;
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
			
	private JSONObject crew;
	private JSONObject skills;	
	
	public Calc(String ship) throws FileNotFoundException, IOException, ParseException
	{
		jsp = new JSParser(ship);
				
		tier = jsp.getTier();
		nation = jsp.getNation();
		shiptype = jsp.getShipType();
		health = jsp.getHealth();
		speed = jsp.getSpeed();
		rudderShift = jsp.getRudderShift();
		
		maxMainGunRange = jsp.getMaxMainGunRange() / 1000;
		mainGunReload = jsp.getMainGunReload();
		mainGunRotation = jsp.getMainGunRotation();		
		
		torpedoReload = jsp.getTorpedoReload();
		torpedoRotation = jsp.getTorpedoRotation();
		maxTorpedoRange = jsp.getMaxTorpedoRange();
		torpedoSpeed = jsp.getTorpedoSpeed();
		
		sConceal = jsp.getSConceal();
		aConceal = jsp.getAConceal();
		
		upgradeSlots = jsp.getModuleSlots();		
		
		MainTurretHP = jsp.getMainTurretHP();
		MainTurretAutoRepairTime = jsp.getMainTurretAutoRepairTime();
		
		TorpedoHP = jsp.getTorpedoHP();
		TorpedoAutoRepairTime = jsp.getTorpedoAutoRepairTime();
		
		forwardEngineUpTime = (double) jsp.getEngine().get("forwardEngineUpTime");
		backwardEngineUpTime = (double) jsp.getEngine().get("backwardEngineUpTime");
		engineRepairTime = jsp.getEngineAutoRepairTime();
		
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
		
		
		torpedoRotation = torpedoRotation * GTRotationSpeed;		
	}
	
	public void calcArtilleryPlottingRoomMod1US() //Slot 2
	{
		//"PCM028_FireControl_Mod_I_US"
		JSONObject APRM1 = (JSONObject) jsp.getGameParams().get("PCM028_FireControl_Mod_I_US");
		double GMMaxDist = (double) APRM1.get("GMMaxDist");
		double GSMaxDist = (double) APRM1.get("GSMaxDist");
		double GSIdealRadius = (double) APRM1.get("GSIdealRadius");
		
		maxMainGunRange = maxMainGunRange * GMMaxDist;
		
		

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
	
	public void calcAAGunsMod2() //Slot2
	{
		//"PCM011_AirDefense_Mod_II"
		JSONObject AAGM2 = (JSONObject) jsp.getGameParams().get("PCM011_AirDefense_Mod_II");
		double AAMaxDist = (double) AAGM2.get("AAMaxDist");
		
		
		
	}
	
	public void calcSecondaryBatteryMod2() //Slot2
	{
		//"PCM012_SecondaryGun_Mod_II"
		JSONObject SBM2 = (JSONObject) jsp.getGameParams().get("PCM012_SecondaryGun_Mod_II");
		double GSMaxDist = (double) SBM2.get("AAMaxDist"); 
		
		
	}
	
	public void calcArtilleryPlottingRoomMod2US() //Slot 3
	{
		//"PCM029_FireControl_Mod_II_US"
		JSONObject APR2 = (JSONObject) jsp.getGameParams().get("PCM029_FireControl_Mod_II_US");
		double GMIdealRadius = (double) APR2.get("GMIdealRadius");
		
		
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
	
	public void calcAAGunsMod3() //Slot 3
	{
		//"PCM018_AirDefense_Mod_III"
		JSONObject AAGM3 = (JSONObject) jsp.getGameParams().get("PCM018_AirDefense_Mod_III");
		double AAAuraBonus = (double) AAGM3.get("AAAura");
		
		
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
	
	public void calcAdvancedFiringTraining()
	{
		JSONObject AFT = (JSONObject) skills.get("AIGunsRangeModifier");
		double smallGunRangeCoefficient = (double) AFT.get("smallGunRangeCoefficient");
		
		if (jsp.getBarrelDiameter() < 0.139)
		{
			maxMainGunRange = maxMainGunRange * smallGunRangeCoefficient;
		}		
	}

	public void calcBasicFiringTraining()
	{
		JSONObject BFT = (JSONObject) skills.get("AIGunsEfficiencyModifier");
		double smallGunReloadCoefficient = (double) BFT.get("smallGunReloadCoefficient");
		
		if (jsp.getBarrelDiameter() < 0.139)
		{
			mainGunReload = mainGunReload * smallGunReloadCoefficient;
		}		
	}
	
	
	
	/**
	 * 
	 */
	public void calcExpertMarksman()
	{
		JSONObject EM = (JSONObject) skills.get("MainGunsRotationModifier");
		double bigGunBonus = (double) EM.get("bigGunBonus");
		double smallGunBonus = (double) EM.get("smallGunBonus");
		
		if (jsp.getBarrelDiameter() > 0.139)
		{
			mainGunRotation = mainGunRotation + bigGunBonus;
		}
		else
		{
			mainGunRotation = mainGunRotation + smallGunBonus;
		}
	}
	
	/**
	 * 
	 */
	public void calcConcealmentExpert()
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
	
	public void calcTorpedoArmamentExpertise()
	{
		JSONObject TorpedoReloadModifier = (JSONObject) skills.get("TorpedoReloadModifier");
		torpedoReload = torpedoReload * (double) TorpedoReloadModifier.get("launcherCoefficient");
	}
	
	public void calcTorpedoAcceleration() 
	{
		JSONObject TorpedoAcceleratorModifier = (JSONObject) skills.get("TorpedoAcceleratorModifier");		
		maxTorpedoRange = maxTorpedoRange * (double) TorpedoAcceleratorModifier.get("torpedoRangeCoefficient");
		torpedoSpeed = torpedoSpeed + (double) TorpedoAcceleratorModifier.get("torpedoSpeedBonus");
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
	
	
	
	public double getRudderShift()
	{
		rudderShift = Math.round(rudderShift * 100.0) / 100.0;
		return rudderShift;
	}
	
	public double getMaxMainGunRange()
	{
		maxMainGunRange = Math.round(maxMainGunRange * 100.0) / 100.0;
		return maxMainGunRange;
	}
	
	public double getMainGunReload()
	{
		mainGunReload = Math.round(mainGunReload * 100.0) / 100.0;
		return mainGunReload;
	}
	
	public double getMainGunRotation()
	{
		mainGunRotation = Math.round(mainGunRotation * 100.0) / 100.0;
		return mainGunRotation;
	}
	
	public double getMainGunRotationTime()
	{
		double rotationDeg = 0;
		if (getMainGunRotation() != 0 )
		{
			rotationDeg = Math.round(180.0 / getMainGunRotation());
		}
		return rotationDeg; 
	}
	
	public double getMaxTorpedoRange()
	{
		maxTorpedoRange = Math.round(maxTorpedoRange * 100.0) / 100.0;
		return maxTorpedoRange;
	}
	
	public double getTorpedoReload()
	{
		torpedoReload = Math.round(torpedoReload * 100.0) / 100.0;
		return torpedoReload;
	}
	
	public double getTorpedoRotation()
	{
		torpedoRotation = Math.round(torpedoRotation * 100.0) / 100.0;
		return torpedoRotation;
	}
	
	public double getTorpedoRotationTime()
	{
		double rotationDeg = 0;
		if (getTorpedoRotation() != 0)
		{
			rotationDeg = Math.round(180.0 / getTorpedoRotation());
		}
		return rotationDeg;
	}
	
	public double getTorpedoSpeed()
	{
		torpedoSpeed = Math.round(torpedoSpeed * 100.0) / 100.0;
		return torpedoSpeed;
	}
	
	public double getSConceal()
	{
		sConceal = Math.round(sConceal * 100.0) / 100.0;
		return sConceal;
	}
	
	public double getAConceal()
	{
		aConceal = Math.round(aConceal * 100.0) / 100.0;
		return aConceal;
	}
	
	public double getHealth()
	{		
		return health;
	}
	
	public long getTier()
	{
		return tier;
	}
	
	public String getNation()
	{
		return nation;
	}
	
	public String getShipType()
	{
		return shiptype;
	}
	
	public Object getSpeed()
	{
		return speed;
	}
	
	public JSParser getJSP()
	{
		return jsp;
	}
	
	public int getUpgradeSlots()
	{
		return upgradeSlots;
	}
	
	
	public double getBurnTime()
	{
		return burnTime;
	}
		
	public double getFloodTime()
	{
		return floodTime;
	}
	
	public double getEngineRepairTime()
	{
		return engineRepairTime;
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
	
	
	public List<String> getModule1()
	{
		List<String> mod1 = new ArrayList<String>();
		mod1.add("None");	
		
		for (int i = 0; i < jsp.getModule1().size(); i++)
		{			
			mod1.add(i+1, jsp.getModule1().get(i).substring(7, jsp.getModule1().get(i).length()));		
		}
		
		return mod1;
	}	
	
	public List<String> getModule2()
	{
		List<String> mod2 = new ArrayList<String>();
		mod2.add("None");	
		
		for (int i = 0; i < jsp.getModule2().size(); i++)
		{			
			mod2.add(i+1, jsp.getModule2().get(i).substring(7, jsp.getModule2().get(i).length()));		
		}
		
		return mod2;	
	}
	
	public List<String> getModule3()
	{
		List<String> mod3 = new ArrayList<String>();
		mod3.add("None");	
		
		for (int i = 0; i < jsp.getModule3().size(); i++)
		{			
			mod3.add(i+1, jsp.getModule3().get(i).substring(7, jsp.getModule3().get(i).length()));		
		}
		
		return mod3;	
	}
	public List<String> getModule4()
	{
		List<String> mod4 = new ArrayList<String>();
		mod4.add("None");	
		
		for (int i = 0; i < jsp.getModule4().size(); i++)
		{			
			mod4.add(i+1, jsp.getModule4().get(i).substring(7, jsp.getModule4().get(i).length()));		
		}
		
		return mod4;
	}
	public List<String> getModule5()
	{
		List<String> mod5 = new ArrayList<String>();
		mod5.add("None");	
		
		for (int i = 0; i < jsp.getModule5().size(); i++)
		{			
			mod5.add(i+1, jsp.getModule5().get(i).substring(7, jsp.getModule5().get(i).length()));		
		}
		
		return mod5;	
	}
	public List<String> getModule6()
	{
		List<String> mod6 = new ArrayList<String>();
		mod6.add("None");	
		
		for (int i = 0; i < jsp.getModule6().size(); i++)
		{			
			mod6.add(i+1, jsp.getModule6().get(i).substring(7, jsp.getModule6().get(i).length()));		
		}
		
		return mod6;
	}
	
	
	
	
	
	
	public void test()
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
