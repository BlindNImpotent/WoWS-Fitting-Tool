import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import org.json.simple.parser.ParseException;

/**
 * @author Aesis (BlindNImpotent)
 * Controller class.
 */
public class Controller 
{
	private static Viewer view;
	private static Model model;

	/**
	 * Constructor
	 */
	private Controller()
	{		
		model = new Model();
		view = new Viewer();
		view.setVisible(true);		
		view.setSearchListener(SearchListener);
		view.setCalculateListener(CalculateListener);
		
		view.setNationList();
		view.setShipTypeList();
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
				if (view.getShipName().equals(""))
				{
					answer = model.search(view.getShipNameListComboBox().getSelectedItem().toString());
				}
				else if (view.getShipName() != null)
				{
					answer = model.search(view.getShipName());
				}
			} 
			catch (IOException | ParseException e) 
			{			
				e.printStackTrace();
			}
			view.setTier(answer.getTier());
			view.setNation(answer.getNation());
			view.setShipType(answer.getShipType());
			view.setHealth(answer.getHealth());
			view.setSpeed(answer.getSpeed());
			view.setRudderShift(answer.getRudderShift());
			view.setSConceal(answer.getSConceal());
			view.setAConceal(answer.getAConceal());
			view.setStealthFireRange(answer.getSConceal() + answer.getStealthFireSurfaceDetection());
			view.setAAFireAirDetection(answer.getAConceal() + answer.getAAFireAirDetection());
			
			view.setMGRange(answer.getMaxMainGunRange());
			view.setMGReload(answer.getMainGunReload());
			view.setMGDegs(answer.getMainGunRotation());
			view.setMGTime(answer.getMainGunRotationTime());
			view.setMGDispersion(answer.getMainGunDispersionRange());
			view.setAPShellSpeed(answer.getAPShellSpeed());
			view.setHEShellSpeed(answer.getHEShellSpeed());
			view.setHEShellBurnProb(answer.getHEShellBurnProb());

			view.setSecondaryMaxDist(answer.getSecondaryMaxDist());
			
			view.setTorpRange(answer.getMaxTorpedoRange());
			view.setTorpReload(answer.getTorpedoReload());
			view.setTorpSpeed(answer.getTorpedoSpeed());
			
			view.setFloodTime(answer.getFloodTime());
			view.setBurnTime(answer.getBurnTime());
			
			view.setAARangeFar(answer.getAntiAirAuraDistanceFar());
			view.setAARangeMedium(answer.getAntiAirAuraDistanceMedium());
			view.setAARangeNear(answer.getAntiAirAuraDistanceNear());			
			
			view.setAAFarDPS(answer.getAAFarDPS());
			view.setAAMediumDPS(answer.getAAMediumDPS());
			view.setAANearDPS(answer.getAANearDPS());
			
			view.setModuleBox1(answer.getModule1());
			view.setModuleBox2(answer.getModule2());
			view.setModuleBox3(answer.getModule3());
			view.setModuleBox4(answer.getModule4());
			view.setModuleBox5(answer.getModule5());
			view.setModuleBox6(answer.getModule6());
		}		
	};


	ActionListener ShipNameList = new ActionListener()
	{
		public void actionPerformed(ActionEvent ae) 
		{	
			String nation = view.getNationListComboBox().getSelectedItem().toString();
			String type = view.getShipTypeListComboBox().getSelectedItem().toString();
			
			if (nation.equals("USA") && type.equals("Battleship"))
			{
				view.setUSABattleshipList();
			}
			else if (nation.equals("USA") && type.equals("Cruiser"))
			{
				view.setUSACruiserList();
			}	
			else if (nation.equals("USA") && type.equals("CV"))
			{
				view.setUSACVList();
			}
			else if (nation.equals("USA") && type.equals("Destroyer"))
			{
				view.setUSADestroyerList();
			}
			else if (nation.equals("USA") && type.equals("Premium"))
			{
				view.setUSAPremiumList();
			}
			else if (nation.equals("Russia") && type.equals("Destroyer"))
			{
				view.setRussiaDestroyerList();
			}
			else if (nation.equals("Russia") && type.equals("Cruiser"))
			{
				view.setRussiaCruiserList();
			}
			else if (nation.equals("Russia") && type.equals("Premium"))
			{
				view.setRussiaPremiumList();
			}
			else if (nation.equals("Japan") && type.equals("Battleship"))
			{
				view.setJapanBattleshipList();
			}
			else if (nation.equals("Japan") && type.equals("Cruiser"))
			{
				view.setJapanCruiserList();
			}	
			else if (nation.equals("Japan") && type.equals("CV"))
			{
				view.setJapanCVList();
			}
			else if (nation.equals("Japan") && type.equals("Destroyer"))
			{
				view.setJapanDestroyerList();
			}
			else if (nation.equals("Japan") && type.equals("Premium"))
			{
				view.setJapanPremiumList();
			}
			else if (nation.equals("Germany") && type.equals("Cruiser"))
			{
				view.setGermanyCruiserList();
			}			
			else if (nation.equals("Germany") && type.equals("Premium"))
			{
				view.setGermanyPremiumList();
			}
			else if (nation.equals("Pan Asia") && type.equals("Premium"))
			{
				view.setPanAsiaPremiumList();
			}
			else if (nation.equals("United Kingdom") && type.equals("Premium"))
			{
				view.setUKPremiumList();
			}
			else if (nation.equals("Poland") && type.equals("Premium"))
			{
				view.setPolandPremiumList();
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
			String mod1 = view.getModuleBox1();
			String mod2 = view.getModuleBox2();
			String mod3 = view.getModuleBox3();
			String mod4 = view.getModuleBox4();
			String mod5 = view.getModuleBox5();
			String mod6 = view.getModuleBox6();
			boolean conceal = view.getConcealmentSkill();
			boolean survivability = view.getSurvivabilitySkill();
			boolean AFT = view.getAFT();
			boolean EM = view.getExpertMarksman();
			boolean concealCamo = view.getConcealCamo();
			boolean BFT = view.getBFT();
			boolean TAE = view.getTorpArmExp();
			boolean TA = view.getTorpAccel();
			boolean BoS = view.getBoS();
			boolean DE = view.getDemoExpSkill();

			try 
			{
				if (view.getShipName().equals(""))
				{
					answer = model.calculate(view.getShipNameListComboBox().getSelectedItem().toString(), 
							mod1, mod2, mod3, mod4, mod5, mod6,
							BFT, BoS,
							EM, TAE,
							TA,
							DE, AFT, survivability,
							conceal,							  
							concealCamo
							);				
				}
				else if (view.getShipName() != null)
				{
					answer = model.calculate(view.getShipName(), 
							mod1, mod2, mod3, mod4, mod5, mod6,
							BFT, BoS,
							EM, TAE,
							TA,
							DE, AFT, survivability,
							conceal,							  
							concealCamo
							);				
					}				
			} 
			catch (IOException | ParseException e) 
			{			
				e.printStackTrace();
			}
			view.setHealth(answer.getHealth());
			view.setSpeed(answer.getSpeed());
			view.setRudderShift(answer.getRudderShift());
			view.setSConceal(answer.getSConceal());
			view.setAConceal(answer.getAConceal());
			view.setStealthFireRange(answer.getSConceal() + answer.getStealthFireSurfaceDetection());
			view.setAAFireAirDetection(answer.getAConceal() + answer.getAAFireAirDetection());
			
			view.setMGRange(answer.getMaxMainGunRange());
			view.setMGReload(answer.getMainGunReload());
			view.setMGDegs(answer.getMainGunRotation());
			view.setMGTime(answer.getMainGunRotationTime());
			view.setMGDispersion(answer.getMainGunDispersionRange());
			
			view.setHEShellBurnProb(answer.getHEShellBurnProb());
			
			view.setSecondaryMaxDist(answer.getSecondaryMaxDist());
			
			view.setFloodTime(answer.getFloodTime());
			view.setBurnTime(answer.getBurnTime());
			
			view.setTorpRange(answer.getMaxTorpedoRange());
			view.setTorpReload(answer.getTorpedoReload());
			view.setTorpSpeed(answer.getTorpedoSpeed());
			
			view.setAARangeFar(answer.getAntiAirAuraDistanceFar());
			view.setAARangeMedium(answer.getAntiAirAuraDistanceMedium());
			view.setAARangeNear(answer.getAntiAirAuraDistanceNear());	

			view.setAAFarDPS(answer.getAAFarDPS());
			view.setAAMediumDPS(answer.getAAMediumDPS());
			view.setAANearDPS(answer.getAANearDPS());
		}		
	};
	
	/**
	private class CheckboxListener4 implements ActionListener
	{
		boolean SurvivabilityNotChecked = true;
		boolean AFTNotChecked = true;

		public CheckboxListener4()
		{
			view.setCheckBoxListener4(this);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) 
		{	
			if (view.getSurvivabilityCheckbox().isSelected() == SurvivabilityNotChecked)
			{				
				view.setAFTUnchecked();	
				SurvivabilityNotChecked = false;
				AFTNotChecked = true;
			}
			else if (view.getAFTCheckbox().isSelected() == AFTNotChecked)
			{
				view.setSurvivabilityUnchecked();
				AFTNotChecked = false;
				SurvivabilityNotChecked = true;
			}
		}
	}
	*/
	
	/**
	 * Main method.
	 * @param args
	 */
	public static void main(String[] args)
	{	
		new Controller();
	}	
}
