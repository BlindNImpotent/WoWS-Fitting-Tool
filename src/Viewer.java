import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JCheckBox;

public class Viewer extends JFrame 
{
	private JPanel contentPane;
	private JTextField searchText;
	private JButton searchButton;
	private JComboBox mod1Box;
	private JComboBox mod2Box;
	private JComboBox mod3Box;
	private JComboBox mod4Box;
	private JComboBox mod5Box;
	private JComboBox mod6Box;
	private JButton calculateButton;
	private JTextArea rudder;
	private JTextArea tier;
	private JTextArea nation;
	private JTextArea shipType;
	private JTextArea health;
	private JTextArea speed;
	private JTextArea sConceal;
	private JTextArea MGRange;
	private JLabel lblMGRange;
	private JLabel lblTier;
	private JLabel lblNation;
	private JLabel lblShipType;
	private JLabel lblHealth;
	private JLabel lblSpeed;
	private JLabel lblRudder;
	private JLabel lblSConceal;
	private JCheckBox chckbxConcealment;
	private JCheckBox chckbxSurvivability;
	private JCheckBox chckbxAft;
	private JCheckBox chckbxExpertMarksman;
	private JTextArea txtrMgReload;
	private JLabel lblMgReload;
	private JTextArea txtrMgDegs;
	private JLabel lblMgDegs;
	private JTextArea txtrMgTime;
	private JLabel lblMgTime;
	private JLabel lblAConceal;
	private JTextArea aConceal;
	private JCheckBox chckbxConcealCamo;
	private JCheckBox chckbxBft;
	private JLabel lblTorpRange;
	private JLabel lblTorpReload;
	private JTextArea torpRange;
	private JTextArea torpReload;
	private JCheckBox chckbxTorpAccel;
	private JCheckBox chckbxTorpArmExp;
	private JComboBox<String> comboBoxNationList;	
	private JComboBox<String> comboBoxShipTypeList;	
	private JComboBox<String> comboBoxShipNameList;
	private JComboBox<String> comboBoxTierList;
	private JLabel lblTorpSpeed;
	private JTextArea torpSpeed;
	private JCheckBox chckbxBoS;
	private JTextArea burnTime;
	private JTextArea floodTime;
	private JLabel lblBurnTime;
	private JLabel lblFloodTime;
	private JTextArea aaFar;
	private JTextArea aaMedium;
	private JTextArea aaNear;
	private JLabel lblAaFar;
	private JLabel lblAaMedium;
	private JLabel lblAaNear;
	private JTextArea aaFarDPS;	
	private JTextArea aaMediumDPS;	
	private JTextArea aaNearDPS;
	private JTextArea stealthFireRange;
	private JLabel lblStealthFire;
	
	/**
	 * Creates the frame.
	 */
	public Viewer() {
		setTitle("WoWS Ship Stats Calculator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		searchText = new JTextField();
		searchText.setBounds(12, 10, 116, 21);
		contentPane.add(searchText);
		searchText.setColumns(10);
		
		searchButton = new JButton("Search");
		searchButton.setBounds(138, 9, 78, 23);
		contentPane.add(searchButton);
		
		calculateButton = new JButton("Calculate");
		calculateButton.setBounds(218, 9, 97, 23);
		contentPane.add(calculateButton);
		
		mod1Box = new JComboBox();
		mod1Box.setBounds(12, 74, 116, 21);
		contentPane.add(mod1Box);
		
		mod2Box = new JComboBox();
		mod2Box.setBounds(12, 105, 116, 21);
		contentPane.add(mod2Box);
		
		mod3Box = new JComboBox();
		mod3Box.setBounds(12, 136, 116, 21);
		contentPane.add(mod3Box);
		
		mod4Box = new JComboBox();
		mod4Box.setBounds(12, 167, 116, 21);
		contentPane.add(mod4Box);
		
		mod5Box = new JComboBox();
		mod5Box.setBounds(12, 198, 116, 21);
		contentPane.add(mod5Box);
		
		mod6Box = new JComboBox();
		mod6Box.setBounds(12, 229, 116, 21);
		contentPane.add(mod6Box);
		
		rudder = new JTextArea();
		rudder.setText("s");
		rudder.setBounds(218, 238, 70, 21);
		contentPane.add(rudder);
		
		tier = new JTextArea();
		tier.setBounds(218, 83, 70, 21);
		contentPane.add(tier);
		
		nation = new JTextArea();
		nation.setBounds(218, 114, 70, 21);
		contentPane.add(nation);
		
		shipType = new JTextArea();
		shipType.setBounds(218, 145, 70, 21);
		contentPane.add(shipType);
		
		health = new JTextArea();
		health.setText("HP");
		health.setBounds(218, 176, 70, 21);
		contentPane.add(health);
		
		speed = new JTextArea();
		speed.setText("kts");
		speed.setBounds(218, 207, 70, 21);
		contentPane.add(speed);
		
		MGRange = new JTextArea();
		MGRange.setText("km");
		MGRange.setBounds(380, 83, 70, 21);
		contentPane.add(MGRange);
		
		sConceal = new JTextArea();
		sConceal.setText("km");
		sConceal.setBounds(218, 269, 70, 21);
		contentPane.add(sConceal);
		
		lblTier = new JLabel("Tier");
		lblTier.setBounds(138, 87, 39, 15);
		contentPane.add(lblTier);
		
		lblNation = new JLabel("Nation");
		lblNation.setBounds(138, 118, 49, 15);
		contentPane.add(lblNation);
		
		lblShipType = new JLabel("Ship Type");
		lblShipType.setBounds(138, 149, 70, 15);
		contentPane.add(lblShipType);
		
		lblHealth = new JLabel("Health");
		lblHealth.setBounds(138, 180, 49, 15);
		contentPane.add(lblHealth);
		
		lblSpeed = new JLabel("Speed");
		lblSpeed.setBounds(138, 211, 49, 15);
		contentPane.add(lblSpeed);
		
		lblRudder = new JLabel("Rudder Shift");
		lblRudder.setBounds(138, 242, 78, 15);
		contentPane.add(lblRudder);
		
		lblSConceal = new JLabel("S Conceal");
		lblSConceal.setBounds(138, 273, 70, 15);
		contentPane.add(lblSConceal);
		
		lblMGRange = new JLabel("MG Range");
		lblMGRange.setBounds(300, 87, 68, 15);
		contentPane.add(lblMGRange);
		
		chckbxConcealment = new JCheckBox("Conc Exp");
		chckbxConcealment.setBounds(275, 532, 105, 23);
		contentPane.add(chckbxConcealment);
		
		chckbxSurvivability = new JCheckBox("Surv Exp");
		chckbxSurvivability.setBounds(199, 507, 78, 23);
		contentPane.add(chckbxSurvivability);
		
		chckbxAft = new JCheckBox("AFT");
		chckbxAft.setBounds(91, 507, 60, 23);
		contentPane.add(chckbxAft);

		chckbxExpertMarksman = new JCheckBox("Exp Mark");
		chckbxExpertMarksman.setBounds(12, 457, 80, 23);
		contentPane.add(chckbxExpertMarksman);
		
		txtrMgReload = new JTextArea();
		txtrMgReload.setText("s");
		txtrMgReload.setBounds(380, 114, 70, 21);
		contentPane.add(txtrMgReload);
		
		lblMgReload = new JLabel("MG Reload");
		lblMgReload.setBounds(300, 118, 70, 15);
		contentPane.add(lblMgReload);
		
		txtrMgDegs = new JTextArea();
		txtrMgDegs.setText("deg/s");
		txtrMgDegs.setBounds(380, 145, 70, 21);
		contentPane.add(txtrMgDegs);
		
		lblMgDegs = new JLabel("MG Deg/s");
		lblMgDegs.setBounds(300, 149, 70, 15);
		contentPane.add(lblMgDegs);
		
		txtrMgTime = new JTextArea();
		txtrMgTime.setText("s/180 deg");
		txtrMgTime.setBounds(380, 176, 70, 21);
		contentPane.add(txtrMgTime);
		
		lblMgTime = new JLabel("MG Rotation");
		lblMgTime.setBounds(300, 180, 70, 15);
		contentPane.add(lblMgTime);
		
		lblAConceal = new JLabel("A Conceal");
		lblAConceal.setBounds(138, 306, 70, 15);
		contentPane.add(lblAConceal);
		
		aConceal = new JTextArea();
		aConceal.setText("km");
		aConceal.setBounds(218, 300, 70, 21);
		contentPane.add(aConceal);
		
		chckbxConcealCamo = new JCheckBox("Conc Camo");
		chckbxConcealCamo.setBounds(12, 256, 97, 23);
		contentPane.add(chckbxConcealCamo);
		
		chckbxBft = new JCheckBox("BFT");
		chckbxBft.setBounds(91, 432, 60, 23);
		contentPane.add(chckbxBft);
		
		lblTorpRange = new JLabel("Torp Range");
		lblTorpRange.setBounds(300, 211, 68, 15);
		contentPane.add(lblTorpRange);
		
		lblTorpReload = new JLabel("Torp Reload");
		lblTorpReload.setBounds(300, 242, 70, 15);
		contentPane.add(lblTorpReload);
		
		torpRange = new JTextArea();
		torpRange.setText("km");
		torpRange.setBounds(380, 207, 70, 21);
		contentPane.add(torpRange);
		
		torpReload = new JTextArea();
		torpReload.setText("s");
		torpReload.setBounds(380, 238, 70, 21);
		contentPane.add(torpReload);
		
		chckbxTorpAccel = new JCheckBox("Torp Accel");
		chckbxTorpAccel.setBounds(91, 482, 97, 23);
		contentPane.add(chckbxTorpAccel);
		
		chckbxTorpArmExp = new JCheckBox("Torp Arm Exp");
		chckbxTorpArmExp.setBounds(91, 457, 105, 23);
		contentPane.add(chckbxTorpArmExp);
		
		comboBoxNationList = new JComboBox();
		comboBoxNationList.setBounds(12, 41, 89, 21);
		contentPane.add(comboBoxNationList);
		
		comboBoxShipTypeList = new JComboBox();
		comboBoxShipTypeList.setBounds(113, 41, 89, 21);
		contentPane.add(comboBoxShipTypeList);
		
		comboBoxShipNameList = new JComboBox();
		comboBoxShipNameList.setBounds(275, 42, 125, 21);
		contentPane.add(comboBoxShipNameList);
		
		comboBoxTierList = new JComboBox();
		comboBoxTierList.setBounds(218, 42, 45, 21);
		contentPane.add(comboBoxTierList);
		
		lblTorpSpeed = new JLabel("Torp Speed");
		lblTorpSpeed.setBounds(300, 273, 70, 15);
		contentPane.add(lblTorpSpeed);
		
		torpSpeed = new JTextArea();
		torpSpeed.setText("kts");
		torpSpeed.setBounds(380, 269, 70, 21);
		contentPane.add(torpSpeed);
		
		chckbxBoS = new JCheckBox("BoS");
		chckbxBoS.setBounds(199, 432, 49, 23);
		contentPane.add(chckbxBoS);
		
		burnTime = new JTextArea();
		burnTime.setText("s");
		burnTime.setBounds(380, 302, 70, 21);
		contentPane.add(burnTime);
		
		floodTime = new JTextArea();
		floodTime.setText("s");
		floodTime.setBounds(380, 333, 70, 21);
		contentPane.add(floodTime);
		
		lblBurnTime = new JLabel("Burn Time");
		lblBurnTime.setBounds(300, 306, 70, 15);
		contentPane.add(lblBurnTime);
		
		lblFloodTime = new JLabel("Flood Time");
		lblFloodTime.setBounds(300, 337, 78, 15);
		contentPane.add(lblFloodTime);
		
		aaFar = new JTextArea();
		aaFar.setText("km");
		aaFar.setBounds(542, 83, 70, 21);
		contentPane.add(aaFar);
		
		aaMedium = new JTextArea();
		aaMedium.setText("km");
		aaMedium.setBounds(542, 145, 70, 21);
		contentPane.add(aaMedium);
		
		aaNear = new JTextArea();
		aaNear.setText("km");
		aaNear.setBounds(542, 207, 70, 21);
		contentPane.add(aaNear);
		
		lblAaFar = new JLabel("AA Far");
		lblAaFar.setBounds(462, 87, 68, 15);
		contentPane.add(lblAaFar);
		
		lblAaMedium = new JLabel("AA Medium");
		lblAaMedium.setBounds(462, 149, 68, 15);
		contentPane.add(lblAaMedium);
		
		lblAaNear = new JLabel("AA Near");
		lblAaNear.setBounds(462, 211, 68, 15);
		contentPane.add(lblAaNear);
		
		aaFarDPS = new JTextArea();
		aaFarDPS.setText("dps");
		aaFarDPS.setBounds(542, 114, 70, 21);
		contentPane.add(aaFarDPS);
		
		aaMediumDPS = new JTextArea();
		aaMediumDPS.setText("dps");
		aaMediumDPS.setBounds(542, 176, 70, 21);
		contentPane.add(aaMediumDPS);
		
		aaNearDPS = new JTextArea();
		aaNearDPS.setText("dps");
		aaNearDPS.setBounds(542, 242, 70, 21);
		contentPane.add(aaNearDPS);
		
		stealthFireRange = new JTextArea();
		stealthFireRange.setText("km");
		stealthFireRange.setBounds(218, 331, 70, 21);
		contentPane.add(stealthFireRange);
		
		lblStealthFire = new JLabel("Stealth Fire");
		lblStealthFire.setBounds(138, 337, 70, 15);
		contentPane.add(lblStealthFire);
	}

	/**
	 * Sets Search listener.	
	 * @param al
	 */
	public void setSearchListener(ActionListener al)
	{
		searchButton.addActionListener(al);
	}
	
	/**
	 * Sets Calculate listener.
	 * @param al
	 */
	public void setCalculateListener(ActionListener al)
	{
		calculateButton.addActionListener(al);
	}
	
	/**
	public void setCheckBoxListener4(ActionListener al)
	{
		chckbxSurvivability.addActionListener(al);
		chckbxAft.addActionListener(al);
	}
	*/
	
	/**
	 * Sets ship name listener.
	 * @param al
	 */
	public void setShipNameListener(ActionListener al) 
	{
		comboBoxNationList.addActionListener(al);
		comboBoxShipTypeList.addActionListener(al);
		comboBoxTierList.addActionListener(al);
	    comboBoxShipNameList.addActionListener(al);     
	}
	
	/**
	 * Sets ship tier listener.
	 * @param al
	 */
	public void setShipTierListener(ActionListener al)
	{
		comboBoxTierList.addActionListener(al);
	}
	
	/**
	 * Returns AFT checkbox.
	 * @return
	 */
	public JCheckBox getAFTCheckbox()
	{
		return chckbxAft;
	}
	
	/**
	 * Returns Survivability checkbox.
	 * @return
	 */
	public JCheckBox getSurvivabilityCheckbox()
	{
		return chckbxSurvivability;
	}
	
	/**
	public void setAFTUnchecked()
	{
		chckbxAft.setSelected(false);
	}
	public void setSurvivabilityUnchecked()
	{
		chckbxSurvivability.setSelected(false);
	}	
	*/
	
	/**
	 * Returns whether Concealment Expert is selected.
	 * @return
	 */
	public boolean getConcealmentSkill()
	{
		return chckbxConcealment.isSelected();
	}
	
	/**
	 * Returns whether Survivability Expert is selected.
	 * @return
	 */
	public boolean getSurvivabilitySkill()
	{
		return chckbxSurvivability.isSelected();
	}
	
	/**
	 * Returns whether BFT is selected.
	 * @return
	 */
	public boolean getBFT()
	{
		return chckbxBft.isSelected();
	}
	
	/**
	 * Returns whether AFT is selected.
	 * @return
	 */
	public boolean getAFT()
	{
		return chckbxAft.isSelected();
	}
	
	/**
	 * Returns whether Torp Accel is selected.
	 * @return
	 */
	public boolean getTorpAccel()
	{
		return chckbxTorpAccel.isSelected();
	}
	
	/**
	 * Returns whether Torp Arm Exp is selected.
	 * @return
	 */
	public boolean getTorpArmExp()
	{
		return chckbxTorpArmExp.isSelected();
	}
	
	/**
	 * Returns whether Basics of Survivability is selected.
	 * @return
	 */
	public boolean getBoS()
	{
		return chckbxBoS.isSelected();
	}	
	
	/**
	 * Returns whether Expert Marksman is selected.
	 * @return
	 */
	public boolean getExpertMarksman()
	{
		return chckbxExpertMarksman.isSelected();
	}
	
	/**
	 * Returns whether concealment camo is selected.
	 * @return
	 */
	public boolean getConcealCamo()
	{
		return chckbxConcealCamo.isSelected();
	}
	
	/**
	 * Returns ship name from search box.
	 * @return
	 */
	public String getShipName()
	{
		return searchText.getText();
	}	
	
	/**
	 * Sets tier to returned tier.
	 * @param tier
	 */
	public void setTier(double tier)
	{
		this.tier.setText(String.valueOf(tier));
	}
	
	/**
	 * Sets nation to returned nation.
	 * @param nation
	 */
	public void setNation(String nation)
	{
		this.nation.setText(nation);
	}
	
	/**
	 * Sets ship type to returned ship type.
	 * @param shipType
	 */
	public void setShipType(String shipType)
	{
		this.shipType.setText(shipType);
	}
	
	/**
	 * Sets health to returned health.
	 * @param health
	 */
	public void setHealth(double health)
	{
		this.health.setText(String.valueOf(health));
	}
	
	/**
	 * Sets speed to returned speed.
	 * @param speed
	 */
	public void setSpeed(Object speed)
	{
		this.speed.setText(String.valueOf(speed) + " kts");
	}
	
	/**
	 * Sets rudder shift to returned rudder shift.
	 * @param rudderShift
	 */
	public void setRudderShift(double rudderShift)
	{
		this.rudder.setText(String.valueOf(rudderShift) + " s");
	}
	
	/**
	 * Sets surface concealment to returned surface concealment.
	 * @param sConceal
	 */
	public void setSConceal(double sConceal)
	{
		this.sConceal.setText(String.valueOf(sConceal) + " km");
	}
	
	/**
	 * Sets air concealment to returned air concealment.
	 * @param aConceal
	 */
	public void setAConceal(double aConceal)
	{
		this.aConceal.setText(String.valueOf(aConceal) + " km");
	}
	
	public void setStealthFireRange(double stealthFireRange)
	{
		this.stealthFireRange.setText(String.valueOf(stealthFireRange) + " km");
	}
	
	/**
	 * Sets main gun range to returned main gun range.
	 * @param MGRange
	 */
	public void setMGRange(double MGRange)
	{
		this.MGRange.setText(String.valueOf(MGRange) + " km");
	}
	
	/**
	 * Sets main gun reload to returned main gun reload.
	 * @param MGReload
	 */
	public void setMGReload(double MGReload)
	{
		this.txtrMgReload.setText(String.valueOf(MGReload) + " s");
	}
	
	/**
	 * Sets main gun rotation degree to returned main gun rotation degree.
	 * @param MGDegs
	 */
	public void setMGDegs(double MGDegs)
	{
		this.txtrMgDegs.setText(String.valueOf(MGDegs) + " deg/s");
	}
	
	/**
	 * Sets main gun rotation time to returned main gun rotation time.
	 * @param MGTime
	 */
	public void setMGTime(double MGTime)
	{
		this.txtrMgTime.setText(String.valueOf(MGTime) + " s");
	}
	
	/**
	 * Sets torp range to returned torp range.
	 * @param torpRange
	 */
	public void setTorpRange(double torpRange)
	{
		this.torpRange.setText(String.valueOf(torpRange) + " km");
	}
	
	/**
	 * Sets torp reload to returned torp reload.
	 * @param torpReload
	 */
	public void setTorpReload(double torpReload)
	{
		this.torpReload.setText(String.valueOf(torpReload) + " s");
	}
	
	/**
	 * Sets torp speed to returned torp speed.
	 * @param torpSpeed
	 */
	public void setTorpSpeed(double torpSpeed)
	{
		this.torpSpeed.setText(String.valueOf(torpSpeed) + " kts");
	}
	
	/**
	 * Sets AA far range to returned AA far range.
	 * @param aaFar
	 */
	public void setAARangeFar(double aaFar)
	{
		this.aaFar.setText(String.valueOf(aaFar) + " km");
	}
	
	/**
	 * Sets AA far dps to returned AA far dps.
	 * @param aaFarDPS
	 */
	public void setAAFarDPS(double aaFarDPS)
	{
		this.aaFarDPS.setText(String.valueOf(aaFarDPS) + " dps");
	}
	
	/**
	 * Sets AA medium range to returned AA medium range.
	 * @param aaMedium
	 */
	public void setAARangeMedium(double aaMedium)
	{
		this.aaMedium.setText(String.valueOf(aaMedium) + " km");
	}
	
	/**
	 * Sets AA medium dps to returned AA medium dps.
	 * @param aaMediumDPS
	 */
	public void setAAMediumDPS(double aaMediumDPS)
	{
		this.aaMediumDPS.setText(String.valueOf(aaMediumDPS) + " dps");
	}
	
	/**
	 * Sets AA near range to returned AA near range.
	 * @param aaNear
	 */
	public void setAARangeNear(double aaNear)
	{
		this.aaNear.setText(String.valueOf(aaNear) + " km");
	}
	
	/**
	 * Sets AA near dps to returned AA near dps.
	 * @param aaNearDPS
	 */
	public void setAANearDPS(double aaNearDPS)
	{
		this.aaNearDPS.setText(String.valueOf(aaNearDPS) + " dps");
	}
	
	/**
	 * Sets flood time to returned flood time.
	 * @param floodTime
	 */
	public void setFloodTime(double floodTime) 
	{
		this.floodTime.setText(String.valueOf(floodTime) + " s");
	}
	
	/**
	 * Sets burn time to returned burn time.
	 * @param burnTime
	 */
	public void setBurnTime(double burnTime) 
	{
		this.burnTime.setText(String.valueOf(burnTime) + " s");
	}
	
	/**
	 * Sets module box 1 to given list.
	 * @param aList
	 */
	public void setModuleBox1(List aList)
	{
		mod1Box.removeAllItems();
		for (int i = 0; i < aList.size(); i++)
		{
			mod1Box.addItem(aList.get(i));
		}
	}
	
	/**
	 * Returns selected item from module box 1.
	 * @return
	 */
	public String getModuleBox1()
	{
		return mod1Box.getSelectedItem().toString();		
	}
	
	/**
	 * Sets module box 2 to given list.
	 * @param aList
	 */
	public void setModuleBox2(List aList)
	{
		mod2Box.removeAllItems();
		for (int i = 0; i < aList.size(); i++)
		{
			mod2Box.addItem(aList.get(i));
		}
	}
	
	/**
	 * Returns selected item from module box 2.
	 * @return
	 */
	public String getModuleBox2()
	{
		return mod2Box.getSelectedItem().toString();
	}
	
	/**
	 * Sets module box 3 to given list.
	 * @param aList
	 */
	public void setModuleBox3(List aList)
	{
		mod3Box.removeAllItems();
		for (int i = 0; i < aList.size(); i++)
		{
			mod3Box.addItem(aList.get(i));
		}
	}
	
	/**
	 * Returns selected item from module box 3.
	 * @return
	 */
	public String getModuleBox3()
	{
		return mod3Box.getSelectedItem().toString();
	}
	
	/**
	 * Sets module box 4 to given list.
	 * @param aList
	 */
	public void setModuleBox4(List aList)
	{
		mod4Box.removeAllItems();
		for (int i = 0; i < aList.size(); i++)
		{
			mod4Box.addItem(aList.get(i));
		}
	}
	
	/**
	 * Returns selected item from module box 4.
	 * @return
	 */
	public String getModuleBox4()
	{
		return mod4Box.getSelectedItem().toString();
	}
	
	/**
	 * Sets module box 5 to given list.
	 * @param aList
	 */
	public void setModuleBox5(List aList)
	{
		mod5Box.removeAllItems();
		for (int i = 0; i < aList.size(); i++)
		{
			mod5Box.addItem(aList.get(i));
		}
	}
	
	/**
	 * Returns selected item from module box 5.
	 * @return
	 */
	public String getModuleBox5()
	{
		return mod5Box.getSelectedItem().toString();
	}
	
	/**
	 * Sets module box 6 to given list.
	 * @param aList
	 */
	public void setModuleBox6(List aList)
	{
		mod6Box.removeAllItems();
		for (int i = 0; i < aList.size(); i++)
		{
			mod6Box.addItem(aList.get(i));
		}
	}
	
	/**
	 * Returns selected item from module box 6.
	 * @return
	 */
	public String getModuleBox6()
	{		
		return mod6Box.getSelectedItem().toString();
	}
	
	/**
	 * Sets nation list.
	 */
	public void setNationList()
	{
		comboBoxNationList.removeAllItems();
		comboBoxNationList.addItem("USA");
		comboBoxNationList.addItem("Germany");
		comboBoxNationList.addItem("Japan");
		comboBoxNationList.addItem("Russia");
		comboBoxNationList.addItem("Great Britain");
		comboBoxNationList.addItem("Poland");
		comboBoxNationList.addItem("Pan Asia");
	}
	
	/**
	 * Sets ship type list.
	 */
	public void setShipTypeList()
	{
		comboBoxShipTypeList.removeAllItems();
		comboBoxShipTypeList.addItem("Battleship");
		comboBoxShipTypeList.addItem("CV");
		comboBoxShipTypeList.addItem("Cruiser");
		comboBoxShipTypeList.addItem("Destroyer");
		//comboBoxShipTypeList.addItem("Premium");
	}
	
	/**
	 * Sets tier list.
	 */
	public void setTierList()
	{
		if (!comboBoxShipTypeList.getSelectedItem().equals("Premium"))
		{
			comboBoxTierList.removeAllItems();
			comboBoxTierList.addItem("1");;
			comboBoxTierList.addItem("2");
			comboBoxTierList.addItem("3");
			comboBoxTierList.addItem("4");
			comboBoxTierList.addItem("5");
			comboBoxTierList.addItem("6");
			comboBoxTierList.addItem("7");
			comboBoxTierList.addItem("8");
			comboBoxTierList.addItem("9");
			comboBoxTierList.addItem("10");
		}
		else if (comboBoxShipTypeList.getSelectedItem().equals("Premium"))
		{
			comboBoxTierList.removeAllItems();
			comboBoxTierList.addItem("4");
			comboBoxTierList.addItem("5");

		}
	}

	public JComboBox<String> getNationListComboBox() 
	{
		return comboBoxNationList;
	}

	public JComboBox<String> getShipTypeListComboBox() 
	{	
		return comboBoxShipTypeList;
	}
	
	public JComboBox<String> getShipNameListComboBox()
	{
		return comboBoxShipNameList;
	}
	
	public JComboBox<String> getTierListComboBox() 
	{
		return comboBoxTierList;
	}	

	public void setUSABattleshipList(String tier) 
	{
		comboBoxShipNameList.removeAllItems();
		
		switch (tier)
		{
		case "3": comboBoxShipNameList.addItem("South Carolina");
			break;
		case "4": comboBoxShipNameList.addItem("Wyoming");
			break;
		case "5": comboBoxShipNameList.addItem("New York");	
			break;
		case "6": comboBoxShipNameList.addItem("New Mexico");	
			break;			
		case "7": comboBoxShipNameList.addItem("Colorado");	
			break;
		case "8": comboBoxShipNameList.addItem("North Carolina");	
			break;
		case "9": comboBoxShipNameList.addItem("Iowa");	
			break;
		case "10": comboBoxShipNameList.addItem("Montana");	
			break;			
		default: comboBoxShipNameList.addItem("None");
			break;
		}	
	}
	
	public void setUSACVList(String tier)
	{
		comboBoxShipNameList.removeAllItems();
		
		switch (tier)
		{
		case "4": comboBoxShipNameList.addItem("Langley");
			break;
		case "5": comboBoxShipNameList.addItem("Bogue");
			break;
		case "6": comboBoxShipNameList.addItem("Independence");
			break;
		case "7": comboBoxShipNameList.addItem("Ranger");
			break;
		case "8": comboBoxShipNameList.addItem("Lexington");
			break;
		case "9": comboBoxShipNameList.addItem("Essex");
			break;
		case "10": comboBoxShipNameList.addItem("Midway");
			break;
		default: comboBoxShipNameList.addItem("None");
			break;
		}
	}
	
	public void setUSADestroyerList(String tier)
	{
		comboBoxShipNameList.removeAllItems();
		
		switch (tier)
		{
		case "2": comboBoxShipNameList.addItem("Sampson");
			break;
		case "3": comboBoxShipNameList.addItem("Wickes");
			break;
		case "4": comboBoxShipNameList.addItem("Clemson");
			break;
		case "5": comboBoxShipNameList.addItem("Nicholas");
			break;
		case "6": comboBoxShipNameList.addItem("Farragut");
			break;
		case "7": comboBoxShipNameList.addItem("Mahan");
			break;
		case "8": comboBoxShipNameList.addItem("Benson");
			break;
		case "9": comboBoxShipNameList.addItem("Fletcher");
			break;
		case "10": comboBoxShipNameList.addItem("Gearing");
			break;
		default: comboBoxShipNameList.addItem("None");
			break;

		}
	}
	
	public void setUSACruiserList(String tier)
	{
		comboBoxShipNameList.removeAllItems();
		
		switch (tier)
		{
		case "1": comboBoxShipNameList.addItem("Erie");
			break;
		case "2": comboBoxShipNameList.addItem("Chester");
			break;
		case "3": comboBoxShipNameList.addItem("St_Louis");
			break;
		case "4": comboBoxShipNameList.addItem("Phoenix");
			break;
		case "5": comboBoxShipNameList.addItem("Omaha");
			break;
		case "6": comboBoxShipNameList.addItem("Cleveland");
			break;
		case "7": comboBoxShipNameList.addItem("Pensacola");
			break;
		case "8": comboBoxShipNameList.addItem("New Orlean");
			break;
		case "9": comboBoxShipNameList.addItem("Baltimore");
			break;
		case "10": comboBoxShipNameList.addItem("Des Moines");
			break;
		default: comboBoxShipNameList.addItem("None");
			break;
		}
	}

	public void setJapanBattleshipList(String tier)
	{
		comboBoxShipNameList.removeAllItems();
		
		switch (tier)
		{
		case "3": comboBoxShipNameList.addItem("Kawachi");
			break;
		case "4": comboBoxShipNameList.addItem("Myogi");
			break;
		case "5": comboBoxShipNameList.addItem("Kongo");
			break;
		case "6": comboBoxShipNameList.addItem("Fuso");
			break;
		case "7": comboBoxShipNameList.addItem("Nagato");
			break;
		case "8": comboBoxShipNameList.addItem("Amagi");
			break;
		case "9": comboBoxShipNameList.addItem("Izumo");
			break;
		case "10": comboBoxShipNameList.addItem("Yamato");
			break;
		default: comboBoxShipNameList.addItem("None");
			break;
		}
	}
	
	public void setJapanCVList(String tier)
	{
		comboBoxShipNameList.removeAllItems();
		
		switch (tier)
		{		
		case "4": comboBoxShipNameList.addItem("Hosho");
			break;
		case "5": comboBoxShipNameList.addItem("Zuiho");
			break;
		case "6": comboBoxShipNameList.addItem("Ryujo");
			break;
		case "7": comboBoxShipNameList.addItem("Hiryu");
			break;
		case "8": comboBoxShipNameList.addItem("Zuikaku");
			break;
		case "9": comboBoxShipNameList.addItem("Taiho");
			break;
		case "10": comboBoxShipNameList.addItem("Hakuryu");
			break;
		default: comboBoxShipNameList.addItem("None");
			break;
		}
	}

	public void setJapanDestroyerList(String tier)
	{
		comboBoxShipNameList.removeAllItems();
		
		switch (tier)
		{
		case "2": comboBoxShipNameList.addItem("Umikaze");
			break;
		case "3": comboBoxShipNameList.addItem("Wakatake");
			break;
		case "4": comboBoxShipNameList.addItem("Isokaze");
			break;
		case "5": comboBoxShipNameList.addItem("Minekaze");
			break;
		case "6": comboBoxShipNameList.addItem("Mutsuki");
			break;
		case "7": comboBoxShipNameList.addItem("Hatsuharu");
			break;
		case "8": comboBoxShipNameList.addItem("Fubuki");
			break;
		case "9": comboBoxShipNameList.addItem("Kagero");
			break;
		case "10": comboBoxShipNameList.addItem("Shimakaze");
			break;
		default: comboBoxShipNameList.addItem("None");
			break;
		}
	}

	public void setJapanCruiserList(String tier)
	{
		comboBoxShipNameList.removeAllItems();
		
		switch (tier)
		{
		case "1": comboBoxShipNameList.addItem("Hashidate");
			break;
		case "2": comboBoxShipNameList.addItem("Chikuma");
			break;
		case "3": comboBoxShipNameList.addItem("Tenryu");
			break;
		case "4": comboBoxShipNameList.addItem("Kuma");
			break;
		case "5": comboBoxShipNameList.addItem("Furutaka");
			break;
		case "6": comboBoxShipNameList.addItem("Aoba");
			break;
		case "7": comboBoxShipNameList.addItem("Myoko");
			break;
		case "8": comboBoxShipNameList.addItem("Mogami");
			break;
		case "9": comboBoxShipNameList.addItem("Ibuki");
			break;
		case "10": comboBoxShipNameList.addItem("Zao");
			break;
		default: comboBoxShipNameList.addItem("None");
			break;
		}
	}

	public void setRussiaDestroyerList(String tier)
	{		
		comboBoxShipNameList.removeAllItems();
	
		switch (tier)
		{
		case "2": comboBoxShipNameList.addItem("Storojevoy");
			break;
		case "3": comboBoxShipNameList.addItem("Derzky");
			break;
		case "4": comboBoxShipNameList.addItem("Izyaslav");
			break;
		case "5": comboBoxShipNameList.addItem("Gnevny");
			break;
		case "6": comboBoxShipNameList.addItem("Ognevoy");
			break;
		case "7": comboBoxShipNameList.addItem("Kiev");
			break;
		case "8": comboBoxShipNameList.addItem("Tashkent");
			break;
		case "9": comboBoxShipNameList.addItem("Udaloy");
			break;
		case "10": comboBoxShipNameList.addItem("Khabarovsk");
			break;
		default: comboBoxShipNameList.addItem("None");
			break;
		}
	}
	
	public void setRussiaCruiserList(String tier)
	{
		comboBoxShipNameList.removeAllItems();
		
		switch (tier)
		{
		case "1": comboBoxShipNameList.addItem("Orlan");
			break;
		case "2": comboBoxShipNameList.addItem("Novik");
			break;
		case "3": comboBoxShipNameList.addItem("Bogatyr");
			break;
		case "4": comboBoxShipNameList.addItem("Svetlana");
			break;
		case "5": comboBoxShipNameList.addItem("Kirov");
			break;
		case "6": comboBoxShipNameList.addItem("Budeny");
			break;
		case "7": comboBoxShipNameList.addItem("Shchors");
			break;
		case "8": comboBoxShipNameList.addItem("Chapaev");
			break;
		case "9": comboBoxShipNameList.addItem("Dmitry Donskoy");
			break;
		case "10": comboBoxShipNameList.addItem("Moskva");
			break;
		default: comboBoxShipNameList.addItem("None");
			break;
		}
	}
	
	public void setGermanyCruiserList(String tier)
	{
		comboBoxShipNameList.removeAllItems();
		
		switch (tier)
		{
		case "1": comboBoxShipNameList.addItem("Hermelin");
			break;
		case "2": comboBoxShipNameList.addItem("Drezden");
			break;
		case "3": comboBoxShipNameList.addItem("Kolberg");
			break;
		case "4": comboBoxShipNameList.addItem("Karlsruhe");
			break;
		case "5": comboBoxShipNameList.addItem("Konigsberg");
			break;
		case "6": comboBoxShipNameList.addItem("Nurnberg");
			break;
		case "7": comboBoxShipNameList.addItem("Yorck");
			break;
		case "8": comboBoxShipNameList.addItem("Hipper");
			break;
		case "9": comboBoxShipNameList.addItem("Roon");
			break;
		case "10": comboBoxShipNameList.addItem("Hindenburg");
			break;
		default: comboBoxShipNameList.addItem("None");
			break;
		}
	}
}
