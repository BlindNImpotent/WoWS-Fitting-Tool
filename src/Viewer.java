import java.awt.event.ActionListener;
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

/**
 * @author Aesis (BlindNImpotent)
 * View class.
 */
public class Viewer extends JFrame 
{
	private static final long serialVersionUID = -8693287232941908206L;
	private JPanel contentPane;
	private JButton searchButton;
	private JComboBox<String> mod1Box;
	private JComboBox<String> mod2Box;
	private JComboBox<String> mod3Box;
	private JComboBox<String> mod4Box;
	private JComboBox<String> mod5Box;
	private JComboBox<String> mod6Box;
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
	private JTextArea mainGunDispersionRange;	
	private JLabel lblMgDispersion;	
	private JTextArea AAFireAirDetection;	
	private JLabel lblAaFire;
	private JTextArea APShellSpeed;
	private JTextArea HEShellSpeed;
	private JLabel lblAPShell;
	private JLabel lblHEShell;
	private JTextArea secondaryMaxDist;
	private JLabel lblSecondary;
	private JTextArea HEShellBurnProb;
	private JCheckBox chckbxDemoExp;
	private JLabel lblFireProb;
	private JTextArea txtHEDmg;	
	private JTextArea txtrAPDmg;	
	private JLabel lblApDmg;	
	private JLabel lblHeDmg;	
	private JComboBox<String> comboBoxTorpedoList;
	private JComboBox<String> comboBoxRadarList;
	private JComboBox<String> comboBoxEngineList;
	private JComboBox<String> comboBoxHullList;
	private JComboBox<String> comboBoxTurretList;
	private JLabel lblTurret;
	private JLabel lblHull;
	private JLabel lblEngine;
	private JLabel lblRadar;
	private JLabel lblTorpedo;
	private JLabel lblNation_1;
	private JLabel lblShipType_1;
	private JLabel lblShipName;
	
	
	/**
	 * Creates the frame.
	 */
	public Viewer() {
		setResizable(false);
		setTitle("WoWS Ship Stats Calculator by Aesis (BlindNImpotent)");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		searchButton = new JButton("Search");
		searchButton.setBounds(355, 39, 78, 23);
		contentPane.add(searchButton);
		
		calculateButton = new JButton("Calculate");
		calculateButton.setBounds(535, 39, 97, 23);
		contentPane.add(calculateButton);
		
		mod1Box = new JComboBox<String>();
		mod1Box.setBounds(12, 137, 116, 21);
		contentPane.add(mod1Box);
		
		mod2Box = new JComboBox<String>();
		mod2Box.setBounds(12, 168, 116, 21);
		contentPane.add(mod2Box);
		
		mod3Box = new JComboBox<String>();
		mod3Box.setBounds(12, 199, 116, 21);
		contentPane.add(mod3Box);
		
		mod4Box = new JComboBox<String>();
		mod4Box.setBounds(12, 233, 116, 21);
		contentPane.add(mod4Box);
		
		mod5Box = new JComboBox<String>();
		mod5Box.setBounds(12, 264, 116, 21);
		contentPane.add(mod5Box);
		
		mod6Box = new JComboBox<String>();
		mod6Box.setBounds(12, 295, 116, 21);
		contentPane.add(mod6Box);
		
		rudder = new JTextArea();
		rudder.setEditable(false);
		rudder.setText("s");
		rudder.setBounds(218, 291, 70, 21);
		contentPane.add(rudder);
		
		tier = new JTextArea();
		tier.setEditable(false);
		tier.setBounds(218, 136, 70, 21);
		contentPane.add(tier);
		
		nation = new JTextArea();
		nation.setEditable(false);
		nation.setBounds(218, 167, 70, 21);
		contentPane.add(nation);
		
		shipType = new JTextArea();
		shipType.setEditable(false);
		shipType.setBounds(218, 198, 70, 21);
		contentPane.add(shipType);
		
		health = new JTextArea();
		health.setEditable(false);
		health.setText("HP");
		health.setBounds(218, 229, 70, 21);
		contentPane.add(health);
		
		speed = new JTextArea();
		speed.setEditable(false);
		speed.setText("kts");
		speed.setBounds(218, 260, 70, 21);
		contentPane.add(speed);
		
		MGRange = new JTextArea();
		MGRange.setEditable(false);
		MGRange.setText("km");
		MGRange.setBounds(380, 136, 70, 21);
		contentPane.add(MGRange);
		
		sConceal = new JTextArea();
		sConceal.setEditable(false);
		sConceal.setText("km");
		sConceal.setBounds(218, 322, 70, 21);
		contentPane.add(sConceal);
		
		lblTier = new JLabel("Tier");
		lblTier.setBounds(140, 140, 39, 15);
		contentPane.add(lblTier);
		
		lblNation = new JLabel("Nation");
		lblNation.setBounds(140, 171, 49, 15);
		contentPane.add(lblNation);
		
		lblShipType = new JLabel("Ship Type");
		lblShipType.setBounds(140, 202, 70, 15);
		contentPane.add(lblShipType);
		
		lblHealth = new JLabel("Health");
		lblHealth.setBounds(140, 233, 49, 15);
		contentPane.add(lblHealth);
		
		lblSpeed = new JLabel("Speed");
		lblSpeed.setBounds(138, 264, 49, 15);
		contentPane.add(lblSpeed);
		
		lblRudder = new JLabel("Rudder Shift");
		lblRudder.setBounds(138, 295, 78, 15);
		contentPane.add(lblRudder);
		
		lblSConceal = new JLabel("S Conceal");
		lblSConceal.setBounds(138, 326, 70, 15);
		contentPane.add(lblSConceal);
		
		lblMGRange = new JLabel("MG Range");
		lblMGRange.setBounds(300, 140, 68, 15);
		contentPane.add(lblMGRange);
		
		chckbxConcealment = new JCheckBox("Conc Exp");
		chckbxConcealment.setBounds(275, 642, 105, 23);
		contentPane.add(chckbxConcealment);
		
		chckbxSurvivability = new JCheckBox("Surv Exp");
		chckbxSurvivability.setBounds(199, 616, 78, 23);
		contentPane.add(chckbxSurvivability);
		
		chckbxAft = new JCheckBox("AFT");
		chckbxAft.setBounds(91, 616, 60, 23);
		contentPane.add(chckbxAft);

		chckbxExpertMarksman = new JCheckBox("Exp Mark");
		chckbxExpertMarksman.setBounds(0, 566, 80, 23);
		contentPane.add(chckbxExpertMarksman);
		
		txtrMgReload = new JTextArea();
		txtrMgReload.setEditable(false);
		txtrMgReload.setText("s");
		txtrMgReload.setBounds(380, 167, 70, 21);
		contentPane.add(txtrMgReload);
		
		lblMgReload = new JLabel("MG Reload");
		lblMgReload.setBounds(300, 171, 70, 15);
		contentPane.add(lblMgReload);
		
		txtrMgDegs = new JTextArea();
		txtrMgDegs.setEditable(false);
		txtrMgDegs.setText("deg/s");
		txtrMgDegs.setBounds(380, 198, 70, 21);
		contentPane.add(txtrMgDegs);
		
		lblMgDegs = new JLabel("MG Deg/s");
		lblMgDegs.setBounds(300, 202, 70, 15);
		contentPane.add(lblMgDegs);
		
		txtrMgTime = new JTextArea();
		txtrMgTime.setEditable(false);
		txtrMgTime.setText("s/180 deg");
		txtrMgTime.setBounds(380, 229, 70, 21);
		contentPane.add(txtrMgTime);
		
		lblMgTime = new JLabel("MG Rotation");
		lblMgTime.setBounds(300, 233, 70, 15);
		contentPane.add(lblMgTime);
		
		lblAConceal = new JLabel("A Conceal");
		lblAConceal.setBounds(138, 357, 70, 15);
		contentPane.add(lblAConceal);
		
		aConceal = new JTextArea();
		aConceal.setEditable(false);
		aConceal.setText("km");
		aConceal.setBounds(218, 353, 70, 21);
		contentPane.add(aConceal);
		
		chckbxConcealCamo = new JCheckBox("Conc Camo");
		chckbxConcealCamo.setToolTipText("-3% Surface Detection Camo");
		chckbxConcealCamo.setBounds(12, 322, 97, 23);
		contentPane.add(chckbxConcealCamo);
		
		chckbxBft = new JCheckBox("BFT");
		chckbxBft.setToolTipText("Basic Firing Training: \r\n-10% reload of main guns up to 139 mm, \r\n-10% reload of secondary guns, \r\n+10% dps to AA");
		chckbxBft.setBounds(91, 541, 60, 23);
		contentPane.add(chckbxBft);
		
		lblTorpRange = new JLabel("Torp Range");
		lblTorpRange.setBounds(300, 450, 68, 15);
		contentPane.add(lblTorpRange);
		
		lblTorpReload = new JLabel("Torp Reload");
		lblTorpReload.setBounds(300, 481, 70, 15);
		contentPane.add(lblTorpReload);
		
		torpRange = new JTextArea();
		torpRange.setEditable(false);
		torpRange.setText("km");
		torpRange.setBounds(380, 446, 70, 21);
		contentPane.add(torpRange);
		
		torpReload = new JTextArea();
		torpReload.setEditable(false);
		torpReload.setText("s");
		torpReload.setBounds(380, 477, 70, 21);
		contentPane.add(torpReload);
		
		chckbxTorpAccel = new JCheckBox("Torp Accel");
		chckbxTorpAccel.setBounds(91, 591, 97, 23);
		contentPane.add(chckbxTorpAccel);
		
		chckbxTorpArmExp = new JCheckBox("Torp Arm Exp");
		chckbxTorpArmExp.setBounds(91, 566, 105, 23);
		contentPane.add(chckbxTorpArmExp);
		
		comboBoxNationList = new JComboBox<String>();
		comboBoxNationList.setBounds(12, 40, 89, 21);
		contentPane.add(comboBoxNationList);
		
		comboBoxShipTypeList = new JComboBox<String>();
		comboBoxShipTypeList.setBounds(113, 40, 89, 21);
		contentPane.add(comboBoxShipTypeList);
		
		comboBoxShipNameList = new JComboBox<String>();
		comboBoxShipNameList.setMaximumRowCount(10);
		comboBoxShipNameList.setBounds(218, 40, 125, 21);
		contentPane.add(comboBoxShipNameList);
		
		lblTorpSpeed = new JLabel("Torp Speed");
		lblTorpSpeed.setBounds(300, 512, 70, 15);
		contentPane.add(lblTorpSpeed);
		
		torpSpeed = new JTextArea();
		torpSpeed.setEditable(false);
		torpSpeed.setText("kts");
		torpSpeed.setBounds(380, 508, 70, 21);
		contentPane.add(torpSpeed);
		
		chckbxBoS = new JCheckBox("BoS");
		chckbxBoS.setToolTipText("Basics of Survivability: \r\n-15% time of repair, fire extinguishing and recovery from flood");
		chckbxBoS.setBounds(199, 541, 49, 23);
		contentPane.add(chckbxBoS);
		
		burnTime = new JTextArea();
		burnTime.setEditable(false);
		burnTime.setText("s");
		burnTime.setBounds(218, 446, 70, 21);
		contentPane.add(burnTime);
		
		floodTime = new JTextArea();
		floodTime.setEditable(false);
		floodTime.setText("s");
		floodTime.setBounds(218, 477, 70, 21);
		contentPane.add(floodTime);
		
		lblBurnTime = new JLabel("Burn Time");
		lblBurnTime.setBounds(138, 450, 70, 15);
		contentPane.add(lblBurnTime);
		
		lblFloodTime = new JLabel("Flood Time");
		lblFloodTime.setBounds(138, 481, 78, 15);
		contentPane.add(lblFloodTime);
		
		aaFar = new JTextArea();
		aaFar.setEditable(false);
		aaFar.setText("km");
		aaFar.setBounds(542, 136, 70, 21);
		contentPane.add(aaFar);
		
		aaMedium = new JTextArea();
		aaMedium.setEditable(false);
		aaMedium.setText("km");
		aaMedium.setBounds(542, 198, 70, 21);
		contentPane.add(aaMedium);
		
		aaNear = new JTextArea();
		aaNear.setEditable(false);
		aaNear.setText("km");
		aaNear.setBounds(542, 260, 70, 21);
		contentPane.add(aaNear);
		
		lblAaFar = new JLabel("AA Far");
		lblAaFar.setBounds(462, 140, 68, 15);
		contentPane.add(lblAaFar);
		
		lblAaMedium = new JLabel("AA Medium");
		lblAaMedium.setBounds(462, 202, 68, 15);
		contentPane.add(lblAaMedium);
		
		lblAaNear = new JLabel("AA Near");
		lblAaNear.setBounds(462, 264, 68, 15);
		contentPane.add(lblAaNear);
		
		aaFarDPS = new JTextArea();
		aaFarDPS.setEditable(false);
		aaFarDPS.setText("dps");
		aaFarDPS.setBounds(542, 167, 70, 21);
		contentPane.add(aaFarDPS);
		
		aaMediumDPS = new JTextArea();
		aaMediumDPS.setEditable(false);
		aaMediumDPS.setText("dps");
		aaMediumDPS.setBounds(542, 229, 70, 21);
		contentPane.add(aaMediumDPS);
		
		aaNearDPS = new JTextArea();
		aaNearDPS.setEditable(false);
		aaNearDPS.setText("dps");
		aaNearDPS.setBounds(542, 291, 70, 21);
		contentPane.add(aaNearDPS);
		
		stealthFireRange = new JTextArea();
		stealthFireRange.setEditable(false);
		stealthFireRange.setText("km");
		stealthFireRange.setBounds(218, 384, 70, 21);
		contentPane.add(stealthFireRange);
		
		lblStealthFire = new JLabel("Stealth Fire");
		lblStealthFire.setBounds(138, 388, 70, 15);
		contentPane.add(lblStealthFire);
		
		mainGunDispersionRange = new JTextArea();
		mainGunDispersionRange.setEditable(false);
		mainGunDispersionRange.setText("m");
		mainGunDispersionRange.setBounds(380, 260, 70, 21);
		contentPane.add(mainGunDispersionRange);
		
		lblMgDispersion = new JLabel("MG Disperse");
		lblMgDispersion.setBounds(300, 264, 80, 15);
		contentPane.add(lblMgDispersion);
		
		AAFireAirDetection = new JTextArea();
		AAFireAirDetection.setText("km");
		AAFireAirDetection.setEditable(false);
		AAFireAirDetection.setBounds(218, 415, 70, 21);
		contentPane.add(AAFireAirDetection);
		
		lblAaFire = new JLabel("AA Fire");
		lblAaFire.setBounds(138, 419, 70, 15);
		contentPane.add(lblAaFire);
		
		APShellSpeed = new JTextArea();
		APShellSpeed.setText("m/s");
		APShellSpeed.setEditable(false);
		APShellSpeed.setBounds(380, 291, 70, 21);
		contentPane.add(APShellSpeed);
		
		HEShellSpeed = new JTextArea();
		HEShellSpeed.setText("m/s");
		HEShellSpeed.setEditable(false);
		HEShellSpeed.setBounds(380, 353, 70, 21);
		contentPane.add(HEShellSpeed);
		
		lblAPShell = new JLabel("AP Shell");
		lblAPShell.setBounds(300, 295, 70, 15);
		contentPane.add(lblAPShell);
		
		lblHEShell = new JLabel("HE Shell");
		lblHEShell.setBounds(300, 357, 70, 15);
		contentPane.add(lblHEShell);
		
		secondaryMaxDist = new JTextArea();
		secondaryMaxDist.setText("km");
		secondaryMaxDist.setEditable(false);
		secondaryMaxDist.setBounds(542, 322, 70, 21);
		contentPane.add(secondaryMaxDist);
		
		lblSecondary = new JLabel("Secondary");
		lblSecondary.setBounds(462, 326, 68, 15);
		contentPane.add(lblSecondary);
		
		HEShellBurnProb = new JTextArea();
		HEShellBurnProb.setText("%");
		HEShellBurnProb.setEditable(false);
		HEShellBurnProb.setBounds(380, 415, 70, 21);
		contentPane.add(HEShellBurnProb);
		
		chckbxDemoExp = new JCheckBox("Demo Exp");
		chckbxDemoExp.setBounds(0, 616, 87, 23);
		contentPane.add(chckbxDemoExp);
		
		lblFireProb = new JLabel("Fire Prob");
		lblFireProb.setBounds(300, 419, 70, 15);
		contentPane.add(lblFireProb);
		
		txtHEDmg = new JTextArea();
		txtHEDmg.setEditable(false);
		txtHEDmg.setBounds(380, 384, 70, 21);
		contentPane.add(txtHEDmg);
		
		txtrAPDmg = new JTextArea();
		txtrAPDmg.setEditable(false);
		txtrAPDmg.setBounds(380, 322, 70, 21);
		contentPane.add(txtrAPDmg);
		
		lblApDmg = new JLabel("AP DMG");
		lblApDmg.setBounds(300, 326, 70, 15);
		contentPane.add(lblApDmg);
		
		lblHeDmg = new JLabel("HE DMG");
		lblHeDmg.setBounds(300, 388, 70, 15);
		contentPane.add(lblHeDmg);
		
		comboBoxTorpedoList = new JComboBox<String>();
		comboBoxTorpedoList.setBounds(480, 96, 105, 21);
		contentPane.add(comboBoxTorpedoList);
		
		comboBoxRadarList = new JComboBox<String>();
		comboBoxRadarList.setBounds(363, 96, 105, 21);
		contentPane.add(comboBoxRadarList);
		
		comboBoxEngineList = new JComboBox<String>();
		comboBoxEngineList.setBounds(246, 96, 105, 21);
		contentPane.add(comboBoxEngineList);
		
		comboBoxHullList = new JComboBox<String>();
		comboBoxHullList.setBounds(129, 96, 105, 21);
		contentPane.add(comboBoxHullList);
		
		comboBoxTurretList = new JComboBox<String>();
		comboBoxTurretList.setBounds(12, 96, 105, 21);
		contentPane.add(comboBoxTurretList);
		
		lblTurret = new JLabel("Turret");
		lblTurret.setBounds(12, 71, 68, 15);
		contentPane.add(lblTurret);
		
		lblHull = new JLabel("Hull");
		lblHull.setBounds(129, 71, 68, 15);
		contentPane.add(lblHull);
		
		lblEngine = new JLabel("Engine");
		lblEngine.setBounds(246, 71, 68, 15);
		contentPane.add(lblEngine);
		
		lblRadar = new JLabel("Radar");
		lblRadar.setBounds(363, 71, 68, 15);
		contentPane.add(lblRadar);
		
		lblTorpedo = new JLabel("Torpedo");
		lblTorpedo.setBounds(480, 71, 68, 15);
		contentPane.add(lblTorpedo);
		
		lblNation_1 = new JLabel("Nation");
		lblNation_1.setBounds(12, 15, 68, 15);
		contentPane.add(lblNation_1);
		
		lblShipType_1 = new JLabel("Ship Type");
		lblShipType_1.setBounds(113, 15, 68, 15);
		contentPane.add(lblShipType_1);
		
		lblShipName = new JLabel("Ship Name");
		lblShipName.setBounds(218, 15, 68, 15);
		contentPane.add(lblShipName);
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
	public void setShipListener(ActionListener al) 
	{
		comboBoxNationList.addActionListener(al);
		comboBoxShipTypeList.addActionListener(al);
	    //comboBoxShipNameList.addActionListener(al);     
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
	
	public boolean getDemoExpSkill()
	{
		return chckbxDemoExp.isSelected();
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
	
	public void setAAFireAirDetection(double AAFireAirRange)
	{
		this.AAFireAirDetection.setText(String.valueOf(AAFireAirRange) + " km");
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
	
	public void setMGDispersion(double MGDispersion)
	{
		this.mainGunDispersionRange.setText(String.valueOf(MGDispersion) + " m");
	}
	
	public void setAPShellSpeed(double APShellSpeed)
	{
		this.APShellSpeed.setText(String.valueOf(APShellSpeed) + " m/s");
	}
	
	public void setAPShellDMG(double APShellDMG)
	{
		this.txtrAPDmg.setText(String.valueOf(APShellDMG));
	}
	
	public void setHEShellSpeed(double HEShellSpeed)
	{
		this.HEShellSpeed.setText(String.valueOf(HEShellSpeed) + " m/s");
	}
	
	public void setHEShellDMG(double HEShellDMG)
	{
		this.txtHEDmg.setText(String.valueOf(HEShellDMG));
	}
	
	public void setHEShellBurnProb(double HEShellBurnProb)
	{
		this.HEShellBurnProb.setText(String.valueOf(HEShellBurnProb) + " %");
	}
	
	public void setSecondaryMaxDist(double secondaryMaxDist)
	{
		this.secondaryMaxDist.setText(String.valueOf(secondaryMaxDist) + " km");
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
	public void setModuleBox1(List<String> aList)
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
	public void setModuleBox2(List<String> aList)
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
	public void setModuleBox3(List<String> aList)
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
	public void setModuleBox4(List<String> aList)
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
	public void setModuleBox5(List<String> aList)
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
	public void setModuleBox6(List<String> aList)
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
	
	public void setTurretBox(List<String> aList)
	{
		comboBoxTurretList.removeAllItems();
		for (int i = 0; i < aList.size(); i++)
		{
			comboBoxTurretList.addItem(aList.get(i));
		}		
	}

	public String getTurretBox()
	{
		if (comboBoxTurretList.getSelectedItem() != null)
		{
			return comboBoxTurretList.getSelectedItem().toString();
		}
		else
		{
			return null;
		}
	}	
	
	public void setHullBox(List<String> aList)
	{
		comboBoxHullList.removeAllItems();
		for (int i = 0; i < aList.size(); i++)
		{
			comboBoxHullList.addItem(aList.get(i));
		}
	}
	
	public String getHullBox()
	{
		return comboBoxHullList.getSelectedItem().toString();
	}
	
	public void setEngineBox(List<String> aList)
	{
		comboBoxEngineList.removeAllItems();
		for (int i = 0; i < aList.size(); i++)
		{
			comboBoxEngineList.addItem(aList.get(i));
		}
	}
	
	public String getEngineBox()
	{
		return comboBoxEngineList.getSelectedItem().toString();
	}
	
	public void setRadarBox(List<String> aList)
	{
		comboBoxRadarList.removeAllItems();
		for (int i = 0; i < aList.size(); i++)
		{
			comboBoxRadarList.addItem(aList.get(i));
		}
	}
	
	public String getRadarBox()
	{
		if (comboBoxRadarList.getSelectedItem() != null)
		{
			return comboBoxRadarList.getSelectedItem().toString();
		}
		else
		{
			return null;
		}
	}
		
	public void setTorpedoBox(List<String> aList)
	{
		comboBoxTorpedoList.removeAllItems();
		for (int i = 0; i < aList.size(); i++)
		{
			comboBoxTorpedoList.addItem(aList.get(i));
		}
	}
	
	public String getTorpedoBox()
	{
		if (comboBoxTorpedoList.getSelectedItem() != null)
		{
			return comboBoxTorpedoList.getSelectedItem().toString();
		}
		else
		{
			return null;
		}
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
		comboBoxNationList.addItem("United Kingdom");
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
		comboBoxShipTypeList.addItem("Premium");
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
	
	public void setUSABattleshipList() 
	{
		comboBoxShipNameList.removeAllItems();
		
		comboBoxShipNameList.addItem("South Carolina");
		comboBoxShipNameList.addItem("Wyoming");
		comboBoxShipNameList.addItem("New York");	
		comboBoxShipNameList.addItem("New Mexico");	
		comboBoxShipNameList.addItem("Colorado");	
		comboBoxShipNameList.addItem("North Carolina");	
		comboBoxShipNameList.addItem("Iowa");	
		comboBoxShipNameList.addItem("Montana");
	}
	
	public void setUSACVList()
	{
		comboBoxShipNameList.removeAllItems();
		
		comboBoxShipNameList.addItem("Langley");
		comboBoxShipNameList.addItem("Bogue");
		comboBoxShipNameList.addItem("Independence");
		comboBoxShipNameList.addItem("Ranger");
		comboBoxShipNameList.addItem("Lexington");
		comboBoxShipNameList.addItem("Essex");
		comboBoxShipNameList.addItem("Midway");
	}
	
	public void setUSADestroyerList()
	{
		comboBoxShipNameList.removeAllItems();
		
		comboBoxShipNameList.addItem("Sampson");
		comboBoxShipNameList.addItem("Wickes");
		comboBoxShipNameList.addItem("Clemson");
		comboBoxShipNameList.addItem("Nicholas");
		comboBoxShipNameList.addItem("Farragut");
		comboBoxShipNameList.addItem("Mahan");
		comboBoxShipNameList.addItem("Benson");
		comboBoxShipNameList.addItem("Fletcher");
		comboBoxShipNameList.addItem("Gearing");
	}
	
	public void setUSACruiserList()
	{
		comboBoxShipNameList.removeAllItems();
		
		comboBoxShipNameList.addItem("Erie");
		comboBoxShipNameList.addItem("Chester");
		comboBoxShipNameList.addItem("St_Louis");
		comboBoxShipNameList.addItem("Phoenix");
		comboBoxShipNameList.addItem("Omaha");
		comboBoxShipNameList.addItem("Cleveland");
		comboBoxShipNameList.addItem("Pensacola");
		comboBoxShipNameList.addItem("New Orlean");
		comboBoxShipNameList.addItem("Baltimore");
		comboBoxShipNameList.addItem("Des Moines");
	}
	
	public void setUSAPremiumList()
	{
		comboBoxShipNameList.removeAllItems();
		
		comboBoxShipNameList.addItem("Smith");
		comboBoxShipNameList.addItem("Sims");
		
		comboBoxShipNameList.addItem("Albany");
		comboBoxShipNameList.addItem("Marblehead");
		comboBoxShipNameList.addItem("Atlanta");
		comboBoxShipNameList.addItem("Indianapolis");
		
		comboBoxShipNameList.addItem("Arkansas");
		comboBoxShipNameList.addItem("Texas");
		
		comboBoxShipNameList.addItem("Saipan");
	}

	public void setJapanBattleshipList()
	{
		comboBoxShipNameList.removeAllItems();
		
		comboBoxShipNameList.addItem("Kawachi");
		comboBoxShipNameList.addItem("Myogi");
		comboBoxShipNameList.addItem("Kongo");
		comboBoxShipNameList.addItem("Fuso");
		comboBoxShipNameList.addItem("Nagato");
		comboBoxShipNameList.addItem("Amagi");
		comboBoxShipNameList.addItem("Izumo");
		comboBoxShipNameList.addItem("Yamato");
	}
	
	public void setJapanCVList()
	{
		comboBoxShipNameList.removeAllItems();
		
		comboBoxShipNameList.addItem("Hosho");
		comboBoxShipNameList.addItem("Zuiho");
		comboBoxShipNameList.addItem("Ryujo");
		comboBoxShipNameList.addItem("Hiryu");
		comboBoxShipNameList.addItem("Zuikaku");
		comboBoxShipNameList.addItem("Taiho");
		comboBoxShipNameList.addItem("Hakuryu");
	}

	public void setJapanDestroyerList()
	{
		comboBoxShipNameList.removeAllItems();
		
		comboBoxShipNameList.addItem("Umikaze");
		comboBoxShipNameList.addItem("Wakatake");
		comboBoxShipNameList.addItem("Isokaze");
		comboBoxShipNameList.addItem("Minekaze");
		comboBoxShipNameList.addItem("Mutsuki");
		comboBoxShipNameList.addItem("Hatsuharu");
		comboBoxShipNameList.addItem("Fubuki");
		comboBoxShipNameList.addItem("Kagero");
		comboBoxShipNameList.addItem("Shimakaze");
	}

	public void setJapanCruiserList()
	{
		comboBoxShipNameList.removeAllItems();
		
		comboBoxShipNameList.addItem("Hashidate");
		comboBoxShipNameList.addItem("Chikuma");
		comboBoxShipNameList.addItem("Tenryu");
		comboBoxShipNameList.addItem("Kuma");
		comboBoxShipNameList.addItem("Furutaka");
		comboBoxShipNameList.addItem("Aoba");
		comboBoxShipNameList.addItem("Myoko");
		comboBoxShipNameList.addItem("Mogami");
		comboBoxShipNameList.addItem("Ibuki");
		comboBoxShipNameList.addItem("Zao");
	}
	
	public void setJapanPremiumList()
	{
		comboBoxShipNameList.removeAllItems();
		
		comboBoxShipNameList.addItem("Tachibana");
		//comboBoxShipNameList.addItem("Fujin");
		comboBoxShipNameList.addItem("Kamikaze");
		
		comboBoxShipNameList.addItem("Katori");
		comboBoxShipNameList.addItem("Iwaki");
		comboBoxShipNameList.addItem("Yubari");
		comboBoxShipNameList.addItem("Tone");
		comboBoxShipNameList.addItem("Atago");
		
		comboBoxShipNameList.addItem("Mikasa");
		comboBoxShipNameList.addItem("Ishizuchi");
	}

	public void setRussiaDestroyerList()
	{		
		comboBoxShipNameList.removeAllItems();
		
		comboBoxShipNameList.addItem("Storojevoy");
		comboBoxShipNameList.addItem("Derzky");
		comboBoxShipNameList.addItem("Izyaslav");
		comboBoxShipNameList.addItem("Gnevny");
		comboBoxShipNameList.addItem("Ognevoy");
		comboBoxShipNameList.addItem("Kiev");
		comboBoxShipNameList.addItem("Tashkent");
		comboBoxShipNameList.addItem("Udaloy");
		comboBoxShipNameList.addItem("Khabarovsk");
	}
	
	public void setRussiaCruiserList()
	{
		comboBoxShipNameList.removeAllItems();
		
		comboBoxShipNameList.addItem("Orlan");
		comboBoxShipNameList.addItem("Novik");
		comboBoxShipNameList.addItem("Bogatyr");
		comboBoxShipNameList.addItem("Svetlana");
		comboBoxShipNameList.addItem("Kirov");
		comboBoxShipNameList.addItem("Budeny");
		comboBoxShipNameList.addItem("Shchors");
		comboBoxShipNameList.addItem("Chapaev");
		comboBoxShipNameList.addItem("Dmitry Donskoy");
		comboBoxShipNameList.addItem("Moskva");
	}
	
	public void setRussiaPremiumList()
	{
		comboBoxShipNameList.removeAllItems();
		
		comboBoxShipNameList.addItem("Gremyashchy");
		
		comboBoxShipNameList.addItem("Diana");
		comboBoxShipNameList.addItem("Aurora");
		comboBoxShipNameList.addItem("Murmansk");
		comboBoxShipNameList.addItem("Molotov");
		comboBoxShipNameList.addItem("Kutuzov");
		
		comboBoxShipNameList.addItem("Nikolay");
	}
	
	public void setGermanyCruiserList()
	{
		comboBoxShipNameList.removeAllItems();
		
		comboBoxShipNameList.addItem("Hermelin");
		comboBoxShipNameList.addItem("Drezden");
		comboBoxShipNameList.addItem("Kolberg");
		comboBoxShipNameList.addItem("Karlsruhe");
		comboBoxShipNameList.addItem("Konigsberg");
		comboBoxShipNameList.addItem("Nurnberg");
		comboBoxShipNameList.addItem("Yorck");
		comboBoxShipNameList.addItem("Hipper");
		comboBoxShipNameList.addItem("Roon");
		comboBoxShipNameList.addItem("Hindenburg");
	}
	
	public void setGermanyPremiumList()
	{
		comboBoxShipNameList.removeAllItems();		
		
		comboBoxShipNameList.addItem("Emden");
		
		comboBoxShipNameList.addItem("Tirpitz");
	}
	
	public void setPanAsiaPremiumList()
	{
		comboBoxShipNameList.removeAllItems();
		
		comboBoxShipNameList.addItem("Anshan");
		comboBoxShipNameList.addItem("LoYang");
	}
	
	public void setUKPremiumList()
	{
		comboBoxShipNameList.removeAllItems();
		
		comboBoxShipNameList.addItem("Campbeltown");
		
		comboBoxShipNameList.addItem("Warspite");
	}
	
	public void setPolandPremiumList()
	{
		comboBoxShipNameList.removeAllItems();
		
		comboBoxShipNameList.addItem("Blyskawica");
	}
	
	public void setNoneList()
	{
		comboBoxShipNameList.removeAllItems();
		
		comboBoxShipNameList.addItem("None");
	}
}
