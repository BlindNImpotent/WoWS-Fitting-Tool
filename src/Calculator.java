import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.parser.ParseException;

/**
 * @author Aesis (BlindNImpotent)
 * Sends calculation info to Calc class.
 */
public class Calculator 
{
	private Calc calc;
	
	public Calc calculate(
			String aShip, String turret, String hull, String engine, String radar, String torpedo,
			String mod1, String mod2, String mod3, String mod4, String mod5, String mod6,
			String consume1, String consume2, String consume3, String consume4,
			boolean BFT, boolean BoS,
			boolean EM, boolean TAE,
			boolean TA, boolean SI, 
			boolean DE, boolean AFT, boolean survivability,
			boolean conceal,
			String camouflage,
			boolean JY2, boolean IY, boolean IX, boolean NE7, boolean SM, boolean VL, boolean MY6, boolean NF, boolean ZH, boolean ESCL, boolean IB3,
			boolean PremAcc
			) throws FileNotFoundException, IOException, ParseException
	{
		calc = upgrades(aShip, turret, hull, engine,radar, torpedo, consume1, consume2, consume3, consume4);
		
		if (calc.getModule1() != null)
		{
			if (mod1.equals("MainWeapon_Mod_I"))
			{
				calc.calcMainArmamentsMod1();
			}
			else if (mod1.equals("SecondaryWeapon_Mod_I"))
			{
				calc.calcAuxiliaryArmamentsMod1();
			}
			else if (mod1.equals("PowderMagazine_Mod_I"))
			{
				calc.calcMagazineMod1();
			}
			else if (mod1.equals("MainGun_Mod_II"))
			{
				calc.calcMainBatteryMod2();
			}
			else if (mod1.equals("AirDefense_Mod_II"))
			{
				calc.calcAAGunsMod2();
			}
			else if (mod1.equals("SecondaryGun_Mod_II"))
			{
				calc.calcSecondaryBatteryMod2();
			}
			else if (mod1.equals("FireControl_Mod_I_US"))
			{
				calc.calcArtilleryPlottingRoomMod1US();
			}
			else if (mod1.equals("Guidance_Mod_I"))
			{
				calc.calcAimingSystemsModification1();
			}
			else if (mod1.equals("MainGun_Mod_III"))
			{
				calc.calcMainBatteryMod3();
			}
			else if (mod1.equals("AirDefense_Mod_III"))
			{
				calc.calcAAGunsMod3();
			}
			else if (mod1.equals("SecondaryGun_Mod_III"))
			{
				calc.calcSecondaryBatteryMod3();
			}
			else if (mod1.equals("FireControl_Mod_II_US"))
			{
				calc.calcArtilleryPlottingRoomMod2US();
			}
			else if (mod1.equals("FireControl_Mod_II"))
			{
				calc.calcGunFireControlSystemMod2();
			}
			else if (mod1.equals("Torpedo_Mod_III"))
			{
				
			}
			else if (mod1.equals("DamageControl_Mod_I"))
			{
				calc.calcDamageControlSystemMod1();
			}
			else if (mod1.equals("Engine_Mod_I"))
			{
				calc.calcPropulsionMod1();
			}
			else if (mod1.equals("SteeringGear_Mod_I"))
			{
				calc.calcSteeringGearsMod1();
			}
			else if (mod1.equals("DamageControl_Mod_II"))
			{
				calc.calcDamageControlSystemMod2();
			}
			else if (mod1.equals("Engine_Mod_II"))
			{
				calc.calcPropulsionMod2();
			}
			else if (mod1.equals("SteeringGear_Mod_II"))
			{
				calc.calcSteeringGearsMod2();
			}
			else if (mod1.equals("LookoutStation_Mod_I"))
			{
				calc.calcTargetAcquisitionSystemMod1();
			}
			else if (mod1.equals("ConcealmentMeasures_Mod_I"))
			{
				calc.calcConcealSystemMod1();
			}
		}
		if (calc.getModule2() != null)
		{
			if (mod2.equals("MainGun_Mod_II"))
			{
				calc.calcMainBatteryMod2();
			}
			else if (mod2.equals("AirDefense_Mod_II"))
			{
				calc.calcAAGunsMod2();
			}
			else if (mod2.equals("SecondaryGun_Mod_II"))
			{
				calc.calcSecondaryBatteryMod2();
			}
			else if (mod2.equals("FireControl_Mod_I_US"))
			{
				calc.calcArtilleryPlottingRoomMod1US();
			}
			else if (mod2.equals("Guidance_Mod_I"))
			{
				calc.calcAimingSystemsModification1();
			}
			else if (mod2.equals("MainGun_Mod_III"))
			{
				calc.calcMainBatteryMod3();
			}
			else if (mod2.equals("AirDefense_Mod_III"))
			{
				calc.calcAAGunsMod3();
			}
			else if (mod2.equals("SecondaryGun_Mod_III"))
			{
				calc.calcSecondaryBatteryMod3();
			}
			else if (mod2.equals("FireControl_Mod_II_US"))
			{
				calc.calcArtilleryPlottingRoomMod2US();
			}
			else if (mod2.equals("FireControl_Mod_II"))
			{
				calc.calcGunFireControlSystemMod2();
			}
			else if (mod2.equals("Torpedo_Mod_III"))
			{
				
			}
			else if (mod2.equals("DamageControl_Mod_I"))
			{
				calc.calcDamageControlSystemMod1();
			}
			else if (mod2.equals("Engine_Mod_I"))
			{
				calc.calcPropulsionMod1();
			}
			else if (mod2.equals("SteeringGear_Mod_I"))
			{
				calc.calcSteeringGearsMod1();
			}
			else if (mod2.equals("DamageControl_Mod_II"))
			{
				calc.calcDamageControlSystemMod2();
			}
			else if (mod2.equals("Engine_Mod_II"))
			{
				calc.calcPropulsionMod2();
			}
			else if (mod2.equals("SteeringGear_Mod_II"))
			{
				calc.calcSteeringGearsMod2();
			}
			else if (mod2.equals("LookoutStation_Mod_I"))
			{
				calc.calcTargetAcquisitionSystemMod1();
			}
			else if (mod2.equals("ConcealmentMeasures_Mod_I"))
			{
				calc.calcConcealSystemMod1();
			}
		}
		if (calc.getModule3() != null)
		{
			if (mod3.equals("MainGun_Mod_III"))
			{
				calc.calcMainBatteryMod3();
			}
			else if (mod3.equals("AirDefense_Mod_III"))
			{
				calc.calcAAGunsMod3();
			}
			else if (mod3.equals("SecondaryGun_Mod_III"))
			{
				calc.calcSecondaryBatteryMod3();
			}
			else if (mod3.equals("FireControl_Mod_II_US"))
			{
				calc.calcArtilleryPlottingRoomMod2US();
			}
			else if (mod3.equals("FireControl_Mod_II"))
			{
				calc.calcGunFireControlSystemMod2();
			}
			else if (mod3.equals("Torpedo_Mod_III"))
			{
				
			}
			else if (mod3.equals("DamageControl_Mod_I"))
			{
				calc.calcDamageControlSystemMod1();
			}
			else if (mod3.equals("Engine_Mod_I"))
			{
				calc.calcPropulsionMod1();
			}
			else if (mod3.equals("SteeringGear_Mod_I"))
			{
				calc.calcSteeringGearsMod1();
			}
			else if (mod3.equals("DamageControl_Mod_II"))
			{
				calc.calcDamageControlSystemMod2();
			}
			else if (mod3.equals("Engine_Mod_II"))
			{
				calc.calcPropulsionMod2();
			}
			else if (mod3.equals("SteeringGear_Mod_II"))
			{
				calc.calcSteeringGearsMod2();
			}
			else if (mod3.equals("LookoutStation_Mod_I"))
			{
				calc.calcTargetAcquisitionSystemMod1();
			}
			else if (mod3.equals("ConcealmentMeasures_Mod_I"))
			{
				calc.calcConcealSystemMod1();
			}
		}
		if (calc.getModule4() != null)
		{
			if (mod4.equals("DamageControl_Mod_I"))
			{
				calc.calcDamageControlSystemMod1();
			}
			else if (mod4.equals("Engine_Mod_I"))
			{
				calc.calcPropulsionMod1();
			}
			else if (mod4.equals("SteeringGear_Mod_I"))
			{
				calc.calcSteeringGearsMod1();
			}
			else if (mod4.equals("DamageControl_Mod_II"))
			{
				calc.calcDamageControlSystemMod2();
			}
			else if (mod4.equals("Engine_Mod_II"))
			{
				calc.calcPropulsionMod2();
			}
			else if (mod4.equals("SteeringGear_Mod_II"))
			{
				calc.calcSteeringGearsMod2();
			}
			else if (mod4.equals("LookoutStation_Mod_I"))
			{
				calc.calcTargetAcquisitionSystemMod1();
			}
			else if (mod4.equals("ConcealmentMeasures_Mod_I"))
			{
				calc.calcConcealSystemMod1();
			}
		}
		if (calc.getModule5() != null)
		{
			if (mod5.equals("DamageControl_Mod_II"))
			{
				calc.calcDamageControlSystemMod2();
			}
			else if (mod5.equals("Engine_Mod_II"))
			{
				calc.calcPropulsionMod2();
			}
			else if (mod5.equals("SteeringGear_Mod_II"))
			{
				calc.calcSteeringGearsMod2();
			}
			else if (mod5.equals("LookoutStation_Mod_I"))
			{
				calc.calcTargetAcquisitionSystemMod1();
			}
			else if (mod5.equals("ConcealmentMeasures_Mod_I"))
			{
				calc.calcConcealSystemMod1();
			}
		}
		if (calc.getModule6() != null)
		{
			if (mod6.equals("LookoutStation_Mod_I"))
			{
				calc.calcTargetAcquisitionSystemMod1();
			}
			else if (mod6.equals("ConcealmentMeasures_Mod_I"))
			{
				calc.calcConcealSystemMod1();
			}
		}
		
		if (conceal == true)
		{
			calc.calcConcealmentExpert();
		}		
		if (survivability == true)
		{
			calc.calcSurvivabilityExpert();
		}
		if (BFT == true)
		{
			calc.calcBasicFiringTraining();
		}
		if (TAE == true)
		{
			calc.calcTorpedoArmamentExpertise();
		}
		if (AFT == true)
		{
			calc.calcAdvancedFiringTraining();
		}
		if (EM == true)
		{
			calc.calcExpertMarksman();
		}
		if (TA == true)
		{
			calc.calcTorpedoAcceleration();
		}
		if (BoS == true)
		{
			calc.calcBasicsOfSurvivability();
		}
		if (DE == true)
		{
			calc.calcDemolitionExpert();
		}
		if (SI == true)
		{
			calc.calcSuperintendent();
		}
		
		if (!camouflage.equals("None"))
		{
			if (camouflage.equals("Type 1"))
			{
				calc.calcType1Camo();
			}
			else if (camouflage.equals("Type 2"))
			{
				
			}
			else if (camouflage.equals("Type 3"))
			{
				calc.calcType3Camo();
			}
			else if (camouflage.equals("Type 5"))
			{
				calc.calcType5Camo();
			}
			else if (camouflage.equals("Type 6"))
			{
				calc.calcType6Camo();
			}
			else
			{
				calc.setPermaflage2(camouflage);
				calc.calcPermaflage();
			}
			
		}		
		
		if (JY2 == true)
		{
			calc.calcJY2();
		}
		if (IY == true)
		{
			calc.calcIY();
		}
		if (IX == true)
		{
			calc.calcIX();
		}
		if (NE7 == true)
		{
			calc.calcNE7();
		}
		if (SM == true)
		{
			calc.calcSM();
		}
		if (VL == true)
		{
			calc.calcVL();
		}
		if (MY6 == true)
		{
			calc.calcMY6();
		}
		if (NF == true)
		{
			calc.calcNF();
		}
		if (ZH == true)
		{
			calc.calcZH();
		}
		if (ESCL == true)
		{
			calc.calcESCL();
		}
		if (IB3 == true)
		{
			calc.calcIB3();
		}

		if (PremAcc == true)
		{
			calc.calcPremAcc();
		}
		
		return calc;
	}
	
	public Calc search(String aShip) throws FileNotFoundException, IOException, ParseException 
	{		
		calc = new Calc(aShip);
		return calc;
	}
	
	public Calc upgrades(String aShip, String turret, String hull, String engine, String radar, String torpedo,
						String consume1, String consume2, String consume3, String consume4) 
			throws FileNotFoundException, IOException, ParseException
	{
		calc = new Calc(aShip, turret, hull, engine, radar, torpedo, consume1, consume2, consume3, consume4);
		return calc;
	}
}
