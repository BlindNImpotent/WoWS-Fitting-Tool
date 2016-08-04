package JAR;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import Parser.API_NameLists;

import org.json.simple.parser.ParseException;

/**
 * @author Aesis (BlindNImpotent)
 * Controller class.
 */
public class Controller 
{
	private static Viewer view;
	private static Model model;

	private static API_NameLists API_NameLists;
	
	/**
	 * Constructor
	 * @throws ParseException 
	 * @throws IOException 
	 */
	private Controller() throws IOException, ParseException
	{		
		model = new Model();
		view = new Viewer();
		API_NameLists = new API_NameLists();
		
		view.setVisible(true);		
		view.setSearchListener(SearchListener);
		view.setCalculateListener(CalculateListener);
		
		view.setNationList(API_NameLists.getNationNameList());
		view.setShipTypeList(API_NameLists.getShipTypeNameList());
		view.setShipListener(ShipNameList);		
		

	}
	
	/**
	 * Listens for Search button then searches for ship name.
	 */
	ActionListener SearchListener = new ActionListener()
	{		
		public void actionPerformed(ActionEvent ae)
		{			
			Calc answer = null;
			try 
			{					
				answer = model.search(view.getShipNameListComboBox().getSelectedItem().toString());
			} 
			catch (IOException | ParseException e) 
			{			
				e.printStackTrace();
			}
			
			view.setModuleBox1(answer.getModule1());
			view.setModuleBox2(answer.getModule2());
			view.setModuleBox3(answer.getModule3());
			view.setModuleBox4(answer.getModule4());
			view.setModuleBox5(answer.getModule5());
			view.setModuleBox6(answer.getModule6());
			view.setCamouflageBox(answer.getPermaflage());
			view.setConsumable1(answer.getConsumable1());
			view.setConsumable2(answer.getConsumable2());
			view.setConsumable3(answer.getConsumable3());
			view.setConsumable4(answer.getConsumable4());
			view.setTurretBox(answer.getTurretList());
			view.setHullBox(answer.getHullList());
			view.setEngineBox(answer.getEngineList());
			view.setRadarBox(answer.getRadarList());
			view.setTorpedoBox(answer.getTorpedoList());			
			
			
			
			view.setExpFactor(answer.getExpFactor());
			view.setCaptainExpFactor(answer.getCaptainExpFactor());
			
			view.setConsume1Count(answer.getConsume1Count());
			view.setConsume2Count(answer.getConsume2Count());
			view.setConsume3Count(answer.getConsume3Count());
			view.setConsume4Count(answer.getConsume4Count());
			view.setConsume1Reload(answer.getConsume1Reload());
			view.setConsume2Reload(answer.getConsume2Reload());
			view.setConsume3Reload(answer.getConsume3Reload());
			view.setConsume4Reload(answer.getConsume4Reload());
			
			view.setTier(answer.getTier());
			view.setShipType(answer.getShipType());
			view.setMaxRepairCost(answer.getMaxRepairCost());
			view.setHealth(answer.getHealth());
			view.setSpeed(answer.getSpeed());
			view.setRudderShift(answer.getRudderShift());
			view.setTurningRadius(answer.getTurningRadius());
			view.setSConceal(answer.getSConceal());
			view.setAConceal(answer.getAConceal());
			view.setStealthFireRange(answer.getSConceal() + answer.getStealthFireSurfaceDetection());
			view.setAAFireAirDetection(answer.getAConceal() + answer.getAAFireAirDetection());
			
			view.setMGRange(answer.getMaxMainGunRange());
			view.setMGReload(answer.getMainGunReload());
			view.setMGDegs(answer.getMainGunRotation());
			view.setMGTime(answer.getMainGunRotationTime());
			view.setMGDispersion(answer.getMainGunDispersionRange());
			view.setSigmaCount(answer.getSigmaCount());
			view.setAPShellSpeed(answer.getAPShellSpeed());
			view.setAPShellDMG(answer.getAPShellDMG());
			view.setHEShellSpeed(answer.getHEShellSpeed());
			view.setHEShellDMG(answer.getHEShellDMG());
			view.setHEShellBurnProb(answer.getHEShellBurnProb());
			view.setNumBarrels(answer.getNumTurrets(), answer.getNumBarrels());
			
			view.setSecondaryMaxDist(answer.getSecondaryMaxDist());
			
			view.setTorpDiameter(answer.getTorpDiameter());
			view.setTubeCount(answer.getTorpTurrets(), answer.getTorpTubes());
			view.setTorpRange(answer.getMaxTorpedoRange());
			view.setTorpReload(answer.getTorpedoReload());
			view.setTorpSpeed(answer.getTorpedoSpeed());
			view.setTorpedoVisibilityFactor(answer.getTorpedoVisibilityFactor());
			
			view.setFloodTime(answer.getFloodTime());
			view.setBurnTime(answer.getBurnTime());
			
			view.setAARangeFar(answer.getAntiAirAuraDistanceFar());
			view.setAARangeMedium(answer.getAntiAirAuraDistanceMedium());
			view.setAARangeNear(answer.getAntiAirAuraDistanceNear());			
			
			view.setAAFarDPS(answer.getAAFarDPS());
			view.setAAMediumDPS(answer.getAAMediumDPS());
			view.setAANearDPS(answer.getAANearDPS());
		}		
	};


	ActionListener ShipNameList = new ActionListener()
	{
		public void actionPerformed(ActionEvent ae) 
		{	
			String nation = view.getNationListComboBox().getSelectedItem().toString();
			String type = view.getShipTypeListComboBox().getSelectedItem().toString();
			
			if (nation.equals("U.S.A.") && type.equals("Battleship"))
			{
				view.setShipNameList(API_NameLists.getUSA_BattleshipNameList());
			}
			else if (nation.equals("U.S.A.") && type.equals("Cruiser"))
			{
				view.setShipNameList(API_NameLists.getUSA_CruiserNameList());
			}	
			else if (nation.equals("U.S.A.") && type.equals("AirCarrier"))
			{
				view.setShipNameList(API_NameLists.getUSA_AirCarrierNameList());
			}
			else if (nation.equals("U.S.A.") && type.equals("Destroyer"))
			{
				view.setShipNameList(API_NameLists.getUSA_DestroyerNameList());
			}
			else if (nation.equals("U.S.A.") && type.equals("Premium"))
			{
				view.setShipNameList(API_NameLists.getUSA_PremiumNameList());
			}
			else if (nation.equals("U.S.S.R.") && type.equals("Destroyer"))
			{
				view.setShipNameList(API_NameLists.getUSSR_DestroyerNameList());
			}
			else if (nation.equals("U.S.S.R.") && type.equals("Cruiser"))
			{
				view.setShipNameList(API_NameLists.getUSSR_CruiserNameList());
			}
			else if (nation.equals("U.S.S.R.") && type.equals("Premium"))
			{
				view.setShipNameList(API_NameLists.getUSSR_PremiumNameList());
			}
			else if (nation.equals("Japan") && type.equals("Battleship"))
			{
				view.setShipNameList(API_NameLists.getJapan_BattleshipNameList());
			}
			else if (nation.equals("Japan") && type.equals("Cruiser"))
			{
				view.setShipNameList(API_NameLists.getJapan_CruiserNameList());
			}	
			else if (nation.equals("Japan") && type.equals("AirCarrier"))
			{
				view.setShipNameList(API_NameLists.getJapan_AirCarrierNameList());
			}
			else if (nation.equals("Japan") && type.equals("Destroyer"))
			{
				view.setShipNameList(API_NameLists.getJapan_DestroyerNameList());
			}
			else if (nation.equals("Japan") && type.equals("Premium"))
			{
				view.setShipNameList(API_NameLists.getJapan_PremiumNameList());
			}
			else if (nation.equals("Germany") && type.equals("Battleship"))
			{
				view.setShipNameList(API_NameLists.getGermany_BattleshipNameList());
			}
			else if (nation.equals("Germany") && type.equals("Cruiser"))
			{
				view.setShipNameList(API_NameLists.getGermany_CruiserNameList());
			}
			else if (nation.equals("Germany") && type.equals("Premium"))
			{
				view.setShipNameList(API_NameLists.getGermany_PremiumNameList());
			}
			else if (nation.equals("France") && type.equals("Battleship"))
			{
				view.setShipNameList(API_NameLists.getFrance_BattleshipNameList());
			}
			else if (nation.equals("France") && type.equals("Cruiser"))
			{
				view.setShipNameList(API_NameLists.getFrance_CruiserNameList());
			}
			else if (nation.equals("France") && type.equals("Premium"))
			{
				view.setShipNameList(API_NameLists.getFrance_PremiumNameList());
			}
			else if (nation.equals("Pan-Asia") && type.equals("Premium"))
			{
				view.setShipNameList(API_NameLists.getPan_Asia_PremiumNameList());
			}
			else if (nation.equals("U.K.") && type.equals("Premium"))
			{
				view.setShipNameList(API_NameLists.getUK_PremiumNameList());
			}
			else if (nation.equals("Poland") && type.equals("Premium"))
			{
				view.setShipNameList(API_NameLists.getPoland_PremiumNameList());
			}
			else
			{
				view.setNoneList();
			}			
		}		
	};
	
	/**
	 * Listens for Calculate button then calculates ship stats.
	 */
	ActionListener CalculateListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent ae)
		{
			Calc answer = null;
			String aShip = view.getShipNameBox();
			String turret = view.getTurretBox();
			String hull = view.getHullBox();
			String engine = view.getEngineBox();
			String radar = view.getRadarBox();
			String torpedo = view.getTorpedoBox();
			
			String mod1 = view.getModuleBox1();
			String mod2 = view.getModuleBox2();
			String mod3 = view.getModuleBox3();
			String mod4 = view.getModuleBox4();
			String mod5 = view.getModuleBox5();
			String mod6 = view.getModuleBox6();
			
			String consume1 = view.getConsumable1();
			String consume2 = view.getConsumable2();
			String consume3 = view.getConsumable3();
			String consume4 = view.getConsumable4();
			
			String camouflage = view.getCamouflageBox();
						
			boolean BFT = view.getBFT();
			boolean BoS = view.getBoS();
			
			boolean EM = view.getExpertMarksman();
			boolean TAE = view.getTorpArmExp();
			
			boolean TA = view.getTorpAccel();
			boolean HA = view.getHighAlert();
			boolean SI = view.getSuperintendentSkill();
			
			boolean DE = view.getDemoExpSkill();
			boolean AFT = view.getAFT();
			boolean survivability = view.getSurvivabilitySkill();
			boolean MFCAA = view.getMFCAA();
			
			boolean conceal = view.getConcealmentSkill();
			boolean JoAT = view.getJoAT();
			
			boolean JY2 = view.getJY2();
			boolean IY = view.getIY();
			boolean IX = view.getIX();
			boolean NE7 = view.getNE7();
			boolean SM = view.getSM();
			boolean VL = view.getVL();
			boolean MY6 = view.getMY6();
			boolean NF = view.getNF();
			boolean ZH = view.getZH();
			boolean ESCL = view.getESCL();
			boolean IB3 = view.getIB3();
			
			boolean ManualAA = view.getManualAA();
			
			boolean PremAcc = view.getPremAcc();

			try 
			{
					answer = model.calculate(
							aShip, turret, hull, engine,radar, torpedo, 
							mod1, mod2, mod3, mod4, mod5, mod6,
							consume1, consume2, consume3, consume4,
							BFT, BoS,
							EM, TAE,
							TA, HA, SI,
							DE, AFT, survivability, MFCAA,
							conceal, JoAT,
							camouflage,
							JY2, IY, IX, NE7, SM, VL, MY6, NF, ZH, ESCL, IB3,
							ManualAA,
							PremAcc
							);								
			} 
			catch (IOException | ParseException e) 
			{			
				e.printStackTrace();
			}
			
			view.setExpFactor(answer.getExpFactor());
			view.setCaptainExpFactor(answer.getCaptainExpFactor());
			
			view.setConsume1Count(answer.getConsume1Count());
			view.setConsume2Count(answer.getConsume2Count());
			view.setConsume3Count(answer.getConsume3Count());
			view.setConsume4Count(answer.getConsume4Count());
			view.setConsume1Reload(answer.getConsume1Reload());
			view.setConsume2Reload(answer.getConsume2Reload());
			view.setConsume3Reload(answer.getConsume3Reload());
			view.setConsume4Reload(answer.getConsume4Reload());
			
			view.setTier(answer.getTier());
			view.setShipType(answer.getShipType());
			view.setMaxRepairCost(answer.getMaxRepairCost());
			view.setHealth(answer.getHealth());
			view.setSpeed(answer.getSpeed());
			view.setRudderShift(answer.getRudderShift());
			view.setTurningRadius(answer.getTurningRadius());
			view.setSConceal(answer.getSConceal());
			view.setAConceal(answer.getAConceal());
			view.setStealthFireRange(answer.getSConceal() + answer.getStealthFireSurfaceDetection());
			view.setAAFireAirDetection(answer.getAConceal() + answer.getAAFireAirDetection());
			
			view.setMGRange(answer.getMaxMainGunRange());
			view.setMGReload(answer.getMainGunReload());
			view.setMGDegs(answer.getMainGunRotation());
			view.setMGTime(answer.getMainGunRotationTime());
			view.setMGDispersion(answer.getMainGunDispersionRange());
			view.setSigmaCount(answer.getSigmaCount());
			view.setAPShellSpeed(answer.getAPShellSpeed());
			view.setAPShellDMG(answer.getAPShellDMG());
			view.setHEShellSpeed(answer.getHEShellSpeed());
			view.setHEShellDMG(answer.getHEShellDMG());
			view.setHEShellBurnProb(answer.getHEShellBurnProb());
			view.setNumBarrels(answer.getNumTurrets(), answer.getNumBarrels());
			
			view.setSecondaryMaxDist(answer.getSecondaryMaxDist());
			
			view.setTorpDiameter(answer.getTorpDiameter());
			view.setTubeCount(answer.getTorpTurrets(), answer.getTorpTubes());
			view.setTorpRange(answer.getMaxTorpedoRange());
			view.setTorpReload(answer.getTorpedoReload());
			view.setTorpSpeed(answer.getTorpedoSpeed());
			view.setTorpedoVisibilityFactor(answer.getTorpedoVisibilityFactor());
			
			view.setFloodTime(answer.getFloodTime());
			view.setBurnTime(answer.getBurnTime());
			
			view.setAARangeFar(answer.getAntiAirAuraDistanceFar());
			view.setAARangeMedium(answer.getAntiAirAuraDistanceMedium());
			view.setAARangeNear(answer.getAntiAirAuraDistanceNear());			
			
			view.setAAFarDPS(answer.getAAFarDPS());
			view.setAAMediumDPS(answer.getAAMediumDPS());
			view.setAANearDPS(answer.getAANearDPS());	
		}		
	};
	
	/**
	 * Main method.
	 * @param args
	 * @throws ParseException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, ParseException
	{	
		new Controller();
	}	
}
