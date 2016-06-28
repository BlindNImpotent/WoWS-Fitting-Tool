import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import org.json.simple.parser.ParseException;


public class Controller 
{
	private static Viewer view;
	private static Model model;

	private Controller()
	{		
		model = new Model();
		view = new Viewer();
		view.setVisible(true);		
		view.setSearchListener(SearchListener);
		view.setCalculateListener(CalculateListener);
		//view.setCheckBoxListener4(new CheckboxListener4());
		
		view.setNationList();
		view.setShipTypeList();
		view.setTierList();
		//view.setShipTierListener(ShipTypeList);
		view.setShipNameListener(ShipNameList);

	}
	
	public void setModuleBox1()
	{	
		
	}
	
	ActionListener SearchListener = new ActionListener()
	{		
		public void actionPerformed(ActionEvent ae)
		{
			Calc answer = null;
			try 
			{				
				//answer = model.search(view.getShipName());
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
			
			view.setMGRange(answer.getMaxMainGunRange());
			view.setMGReload(answer.getMainGunReload());
			view.setMGDegs(answer.getMainGunRotation());
			view.setMGTime(answer.getMainGunRotationTime());
			
			view.setTorpRange(answer.getMaxTorpedoRange());
			view.setTorpReload(answer.getTorpedoReload());
			view.setTorpSpeed(answer.getTorpedoSpeed());
			
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
			String tier = view.getTierListComboBox().getSelectedItem().toString();		
			
			if (nation.equals("USA") && type.equals("Battleship"))
			{
				view.setUSABattleshipList(tier);
			}
			else if (nation.equals("USA") && type.equals("Cruiser"))
			{
				view.setUSACruiserList(tier);
			}	
			else if (nation.equals("USA") && type.equals("CV"))
			{
				view.setUSACVList(tier);
			}
			else if (nation.equals("USA") && type.equals("Destroyer"))
			{
				view.setUSADestroyerList(tier);
			}
			else if (nation.equals("Russia") && type.equals("Destroyer"))
			{
				view.setRussiaDestroyerList(tier);
			}
			else if (nation.equals("Russia") && type.equals("Cruiser"))
			{
				view.setRussiaCruiserList(tier);
			}
			else if (nation.equals("Japan") && type.equals("Battleship"))
			{
				view.setJapanBattleshipList(tier);
			}
			else if (nation.equals("Japan") && type.equals("Cruiser"))
			{
				view.setJapanCruiserList(tier);
			}	
			else if (nation.equals("Japan") && type.equals("CV"))
			{
				view.setJapanCVList(tier);
			}
			else if (nation.equals("Japan") && type.equals("Destroyer"))
			{
				view.setJapanDestroyerList(tier);
			}
			else if (nation.equals("Germany") && type.equals("Cruiser"))
			{
				view.setGermanyCruiserList(tier);
			}
			
		}		
	};
	
	ActionListener ShipTypeList = new ActionListener()
	{
		public void actionPerformed(ActionEvent ae)
		{
			String type = view.getShipTypeListComboBox().getSelectedItem().toString();
			
			if (type.equals("Premium"))
			{
				view.setTierList();
			}			
		}
	};

		
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
			boolean TAE = view.getTorpArmaExp();
			boolean TA = view.getTorpAccel();
			
			try 
			{
				if (view.getShipName().equals(""))
				{
					answer = model.calculate(view.getShipNameListComboBox().getSelectedItem().toString(), 
							mod1, mod2, mod3, mod4, mod5, mod6, 
							conceal, survivability, AFT, EM, BFT, TAE, TA,  
							concealCamo);				
				}
				else if (view.getShipName() != null)
				{
					answer = model.calculate(view.getShipName(), 
							mod1, mod2, mod3, mod4, mod5, mod6, 
							conceal, survivability, AFT, EM, BFT, TAE, TA,  
							concealCamo);				
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
			
			view.setMGRange(answer.getMaxMainGunRange());
			view.setMGReload(answer.getMainGunReload());
			view.setMGDegs(answer.getMainGunRotation());
			view.setMGTime(answer.getMainGunRotationTime());
			
			view.setTorpRange(answer.getMaxTorpedoRange());
			view.setTorpReload(answer.getTorpedoReload());
			view.setTorpSpeed(answer.getTorpedoSpeed());

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
	
	public static void main(String[] args)
	{	
		new Controller();
	}	
}
