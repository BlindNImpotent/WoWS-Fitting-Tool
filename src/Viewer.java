import java.awt.event.ActionListener;
import java.text.NumberFormat;
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
	private JTextArea shipType;
	private JTextArea health;
	private JTextArea speed;
	private JTextArea turningRadius;	
	private JTextArea sConceal;
	private JTextArea MGRange;
	private JLabel lblMGRange;
	private JLabel lblTier;
	private JLabel lblShipType;
	private JLabel lblHealth;
	private JLabel lblSpeed;
	private JLabel lblTurnRadius;
	private JLabel lblRudder;
	private JLabel lblSConceal;
	
	private JCheckBox chckbxConcealment;
	private JCheckBox chckbxSurvivability;
	private JCheckBox chckbxAft;
	private JCheckBox chckbxExpertMarksman;
	private JCheckBox chckbxHighAlert;
	private JCheckBox chckbxBft;
	private JCheckBox chckbxTorpAccel;
	private JCheckBox chckbxTorpArmExp;
	private JCheckBox chckbxBoS;
	private JCheckBox chckbxDemoExp;
	private JCheckBox chckbxSuperintendent;

	private JTextArea txtrMgReload;
	private JLabel lblMgReload;
	private JTextArea txtrMgDegs;
	private JLabel lblMgDegs;
	private JTextArea txtrMgTime;
	private JLabel lblMgTime;
	private JLabel lblAConceal;
	private JTextArea aConceal;
	
	private JLabel lblTorpRange;
	private JLabel lblTorpReload;
	private JTextArea torpRange;
	private JTextArea torpReload;
	
	private JComboBox<String> comboBoxNationList;	
	private JComboBox<String> comboBoxShipTypeList;	
	private JComboBox<String> comboBoxShipNameList;
	private JLabel lblTorpSpeed;
	private JTextArea torpSpeed;
	
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
	private JTextArea textNumBarrels;
	private JLabel labelBarrels;
	
	private JComboBox<String> comboBoxConsumable1;
	private JComboBox<String> comboBoxConsumable2;
	private JComboBox<String> comboBoxConsumable3;
	private JComboBox<String> comboBoxConsumable4;
	private JLabel lblConsumable1;
	private JLabel lblConsumable2;
	private JLabel lblConsumable3;
	private JLabel lblConsumable4;
	private JLabel lblModule1;
	private JLabel lblModule2;
	private JLabel lblModule3;
	private JLabel lblModule4;
	private JLabel lblModule5;
	private JLabel lblModule6;
	private JTextArea consume4Number;
	private JTextArea consume3Number;
	private JTextArea consume2Number;

	private JTextArea horsePower;	
	private JLabel lblHorsePower;
	private JTextArea turretBarrelDiameter;
	private JLabel lblBarrelDiameter;
	private JTextArea textNumTubes;
	private JTextArea tubeDiameter;
	private JLabel lblTubes;
	private JLabel labelTubeDiameter;
	
	private JCheckBox chckbxJY2;	
	private JCheckBox chckbxIY;	
	private JCheckBox chckbxNE7;	
	private JCheckBox chckbxSM;	
	private JCheckBox chckbxVL;	
	private JCheckBox chckbxMY6;	
	private JCheckBox chckbxIX;
	private JCheckBox chckbxNF;
	private JCheckBox chckbxZH;
	private JCheckBox chckbxESCL;
	private JCheckBox chckbxIB3;
	
	private JTextArea torpDetection;
	private JLabel lblTorpDetection;
	
	private JTextArea sigmaCount;
	private JLabel lblSigmaCount;
	
	private JTextArea maxRepairCost;	
	private JLabel lblMaxRepairCost;
	
	private JComboBox<String> comboBoxCamouflage;	
	private JLabel lblCamouflage;
	
	private JTextArea consume4Reload;	
	private JTextArea consume3Reload;	
	private JTextArea consume2Reload;	
	private JTextArea consume1Reload;	
	private JLabel lblReload1;	
	private JLabel lblReload2;	
	private JLabel lblReload3;	
	private JLabel lblReload4;
	
	private JTextArea expFactor;
	private JLabel lblExpFactor;
	private JTextArea captainExpFactor;	
	private JLabel lblCaptainXp;
	private JCheckBox chckbxPremAcc;
	private JCheckBox chckbxMFCAA;
	private JCheckBox chckbxManualAA;
	private JCheckBox chckbxJoAT;
	
	
	
	/**
	 * Creates the frame.
	 */
	public Viewer() {
		setResizable(false);
		setTitle("WoWS Ship Stats Calculator by Aesis (BlindNImpotent)");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 750);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		searchButton = new JButton("Search");
		searchButton.setBounds(355, 14, 78, 23);
		contentPane.add(searchButton);
		
		calculateButton = new JButton("Calculate");
		calculateButton.setBounds(679, 14, 97, 23);
		contentPane.add(calculateButton);
		
		mod1Box = new JComboBox<String>();
		mod1Box.setBounds(12, 153, 116, 21);
		contentPane.add(mod1Box);
		
		mod2Box = new JComboBox<String>();
		mod2Box.setBounds(12, 199, 116, 21);
		contentPane.add(mod2Box);
		
		mod3Box = new JComboBox<String>();
		mod3Box.setBounds(12, 244, 116, 21);
		contentPane.add(mod3Box);
		
		mod4Box = new JComboBox<String>();
		mod4Box.setBounds(12, 292, 116, 21);
		contentPane.add(mod4Box);
		
		mod5Box = new JComboBox<String>();
		mod5Box.setBounds(12, 337, 116, 21);
		contentPane.add(mod5Box);
		
		mod6Box = new JComboBox<String>();
		mod6Box.setBounds(12, 385, 116, 21);
		contentPane.add(mod6Box);
		
		rudder = new JTextArea();
		rudder.setEditable(false);
		rudder.setText("s");
		rudder.setBounds(218, 353, 70, 21);
		contentPane.add(rudder);
		
		tier = new JTextArea();
		tier.setEditable(false);
		tier.setBounds(218, 136, 70, 21);
		contentPane.add(tier);
		
		shipType = new JTextArea();
		shipType.setEditable(false);
		shipType.setBounds(218, 167, 70, 21);
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
		MGRange.setBounds(380, 198, 70, 21);
		contentPane.add(MGRange);
		
		sConceal = new JTextArea();
		sConceal.setEditable(false);
		sConceal.setText("km");
		sConceal.setBounds(218, 384, 70, 21);
		contentPane.add(sConceal);
		
		lblTier = new JLabel("Tier");
		lblTier.setBounds(140, 140, 39, 15);
		contentPane.add(lblTier);
		
		lblShipType = new JLabel("Ship Type");
		lblShipType.setBounds(140, 171, 70, 15);
		contentPane.add(lblShipType);
		
		lblHealth = new JLabel("Health");
		lblHealth.setBounds(140, 233, 49, 15);
		contentPane.add(lblHealth);
		
		lblSpeed = new JLabel("Speed");
		lblSpeed.setBounds(140, 264, 49, 15);
		contentPane.add(lblSpeed);
		
		lblRudder = new JLabel("Rudder Shift");
		lblRudder.setBounds(140, 357, 78, 15);
		contentPane.add(lblRudder);
		
		lblSConceal = new JLabel("S Conceal");
		lblSConceal.setBounds(140, 388, 70, 15);
		contentPane.add(lblSConceal);
		
		lblMGRange = new JLabel("Max Range");
		lblMGRange.setBounds(300, 202, 68, 15);
		contentPane.add(lblMGRange);
		
		chckbxConcealment = new JCheckBox("Conc Exp");
		chckbxConcealment.setBounds(300, 698, 89, 23);
		contentPane.add(chckbxConcealment);
		
		chckbxSurvivability = new JCheckBox("Surv Exp");
		chckbxSurvivability.setBounds(199, 674, 78, 23);
		contentPane.add(chckbxSurvivability);
		
		chckbxAft = new JCheckBox("AFT");
		chckbxAft.setBounds(91, 674, 60, 23);
		contentPane.add(chckbxAft);

		chckbxExpertMarksman = new JCheckBox("Exp Mark");
		chckbxExpertMarksman.setBounds(0, 624, 80, 23);
		contentPane.add(chckbxExpertMarksman);
		
		txtrMgReload = new JTextArea();
		txtrMgReload.setEditable(false);
		txtrMgReload.setText("s");
		txtrMgReload.setBounds(380, 229, 70, 21);
		contentPane.add(txtrMgReload);
		
		lblMgReload = new JLabel("Reload");
		lblMgReload.setBounds(300, 233, 70, 15);
		contentPane.add(lblMgReload);
		
		txtrMgDegs = new JTextArea();
		txtrMgDegs.setEditable(false);
		txtrMgDegs.setText("deg/s");
		txtrMgDegs.setBounds(380, 260, 70, 21);
		contentPane.add(txtrMgDegs);
		
		lblMgDegs = new JLabel("Rotation");
		lblMgDegs.setBounds(300, 264, 70, 15);
		contentPane.add(lblMgDegs);
		
		txtrMgTime = new JTextArea();
		txtrMgTime.setEditable(false);
		txtrMgTime.setText("s/180 deg");
		txtrMgTime.setBounds(380, 291, 70, 21);
		contentPane.add(txtrMgTime);
		
		lblMgTime = new JLabel("Rotation");
		lblMgTime.setBounds(300, 295, 70, 15);
		contentPane.add(lblMgTime);
		
		lblAConceal = new JLabel("A Conceal");
		lblAConceal.setBounds(140, 419, 70, 15);
		contentPane.add(lblAConceal);
		
		aConceal = new JTextArea();
		aConceal.setEditable(false);
		aConceal.setText("km");
		aConceal.setBounds(218, 415, 70, 21);
		contentPane.add(aConceal);
		
		chckbxBft = new JCheckBox("BFT");
		chckbxBft.setToolTipText("Basic Firing Training: \r\n-10% reload of main guns up to 139 mm, \r\n-10% reload of secondary guns, \r\n+10% dps to AA");
		chckbxBft.setBounds(91, 599, 60, 23);
		contentPane.add(chckbxBft);
		
		lblTorpRange = new JLabel("Torp Range");
		lblTorpRange.setBounds(624, 202, 68, 15);
		contentPane.add(lblTorpRange);
		
		lblTorpReload = new JLabel("Torp Reload");
		lblTorpReload.setBounds(624, 233, 70, 15);
		contentPane.add(lblTorpReload);
		
		torpRange = new JTextArea();
		torpRange.setEditable(false);
		torpRange.setText("km");
		torpRange.setBounds(706, 198, 70, 21);
		contentPane.add(torpRange);
		
		torpReload = new JTextArea();
		torpReload.setEditable(false);
		torpReload.setText("s");
		torpReload.setBounds(706, 229, 70, 21);
		contentPane.add(torpReload);
		
		chckbxTorpAccel = new JCheckBox("Torp Accel");
		chckbxTorpAccel.setBounds(92, 649, 97, 23);
		contentPane.add(chckbxTorpAccel);
		
		chckbxTorpArmExp = new JCheckBox("Torp Arm Exp");
		chckbxTorpArmExp.setBounds(91, 624, 105, 23);
		contentPane.add(chckbxTorpArmExp);
		
		comboBoxNationList = new JComboBox<String>();
		comboBoxNationList.setBounds(12, 14, 89, 21);
		contentPane.add(comboBoxNationList);
		
		comboBoxShipTypeList = new JComboBox<String>();
		comboBoxShipTypeList.setBounds(113, 15, 89, 21);
		contentPane.add(comboBoxShipTypeList);
		
		comboBoxShipNameList = new JComboBox<String>();
		comboBoxShipNameList.setMaximumRowCount(10);
		comboBoxShipNameList.setBounds(218, 15, 125, 21);
		contentPane.add(comboBoxShipNameList);
		
		lblTorpSpeed = new JLabel("Torp Speed");
		lblTorpSpeed.setBounds(624, 264, 70, 15);
		contentPane.add(lblTorpSpeed);
		
		torpSpeed = new JTextArea();
		torpSpeed.setEditable(false);
		torpSpeed.setText("kts");
		torpSpeed.setBounds(706, 260, 70, 21);
		contentPane.add(torpSpeed);
		
		chckbxBoS = new JCheckBox("BoS");
		chckbxBoS.setToolTipText("Basics of Survivability: \r\n-15% time of repair, fire extinguishing and recovery from flood");
		chckbxBoS.setBounds(199, 599, 49, 23);
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
		lblBurnTime.setBounds(140, 450, 70, 15);
		contentPane.add(lblBurnTime);
		
		lblFloodTime = new JLabel("Flood Time");
		lblFloodTime.setBounds(140, 481, 70, 15);
		contentPane.add(lblFloodTime);
		
		aaFar = new JTextArea();
		aaFar.setEditable(false);
		aaFar.setText("km");
		aaFar.setBounds(542, 167, 70, 21);
		contentPane.add(aaFar);
		
		aaMedium = new JTextArea();
		aaMedium.setEditable(false);
		aaMedium.setText("km");
		aaMedium.setBounds(542, 229, 70, 21);
		contentPane.add(aaMedium);
		
		aaNear = new JTextArea();
		aaNear.setEditable(false);
		aaNear.setText("km");
		aaNear.setBounds(542, 291, 70, 21);
		contentPane.add(aaNear);
		
		lblAaFar = new JLabel("AA Far");
		lblAaFar.setBounds(462, 171, 49, 15);
		contentPane.add(lblAaFar);
		
		lblAaMedium = new JLabel("AA Medium");
		lblAaMedium.setBounds(462, 233, 68, 15);
		contentPane.add(lblAaMedium);
		
		lblAaNear = new JLabel("AA Near");
		lblAaNear.setBounds(462, 295, 68, 15);
		contentPane.add(lblAaNear);
		
		aaFarDPS = new JTextArea();
		aaFarDPS.setEditable(false);
		aaFarDPS.setText("dps");
		aaFarDPS.setBounds(542, 198, 70, 21);
		contentPane.add(aaFarDPS);
		
		aaMediumDPS = new JTextArea();
		aaMediumDPS.setEditable(false);
		aaMediumDPS.setText("dps");
		aaMediumDPS.setBounds(542, 260, 70, 21);
		contentPane.add(aaMediumDPS);
		
		aaNearDPS = new JTextArea();
		aaNearDPS.setEditable(false);
		aaNearDPS.setText("dps");
		aaNearDPS.setBounds(542, 322, 70, 21);
		contentPane.add(aaNearDPS);
		
		stealthFireRange = new JTextArea();
		stealthFireRange.setEditable(false);
		stealthFireRange.setText("km");
		stealthFireRange.setBounds(380, 384, 70, 21);
		contentPane.add(stealthFireRange);
		
		lblStealthFire = new JLabel("Stealth MG");
		lblStealthFire.setBounds(300, 388, 70, 15);
		contentPane.add(lblStealthFire);
		
		mainGunDispersionRange = new JTextArea();
		mainGunDispersionRange.setEditable(false);
		mainGunDispersionRange.setText("m");
		mainGunDispersionRange.setBounds(380, 322, 70, 21);
		contentPane.add(mainGunDispersionRange);
		
		lblMgDispersion = new JLabel("Dispersion");
		lblMgDispersion.setBounds(300, 326, 80, 15);
		contentPane.add(lblMgDispersion);
		
		AAFireAirDetection = new JTextArea();
		AAFireAirDetection.setText("km");
		AAFireAirDetection.setEditable(false);
		AAFireAirDetection.setBounds(542, 353, 70, 21);
		contentPane.add(AAFireAirDetection);
		
		lblAaFire = new JLabel("Stealth AA");
		lblAaFire.setBounds(462, 357, 70, 15);
		contentPane.add(lblAaFire);
		
		APShellSpeed = new JTextArea();
		APShellSpeed.setText("m/s");
		APShellSpeed.setEditable(false);
		APShellSpeed.setBounds(380, 415, 70, 21);
		contentPane.add(APShellSpeed);
		
		HEShellSpeed = new JTextArea();
		HEShellSpeed.setText("m/s");
		HEShellSpeed.setEditable(false);
		HEShellSpeed.setBounds(380, 474, 70, 21);
		contentPane.add(HEShellSpeed);
		
		lblAPShell = new JLabel("AP Speed");
		lblAPShell.setBounds(300, 419, 70, 15);
		contentPane.add(lblAPShell);
		
		lblHEShell = new JLabel("HE Speed");
		lblHEShell.setBounds(300, 478, 70, 15);
		contentPane.add(lblHEShell);
		
		secondaryMaxDist = new JTextArea();
		secondaryMaxDist.setText("km");
		secondaryMaxDist.setEditable(false);
		secondaryMaxDist.setBounds(542, 415, 70, 21);
		contentPane.add(secondaryMaxDist);
		
		lblSecondary = new JLabel("Secondary");
		lblSecondary.setBounds(462, 419, 68, 15);
		contentPane.add(lblSecondary);
		
		HEShellBurnProb = new JTextArea();
		HEShellBurnProb.setText("%");
		HEShellBurnProb.setEditable(false);
		HEShellBurnProb.setBounds(380, 536, 70, 21);
		contentPane.add(HEShellBurnProb);
		
		chckbxDemoExp = new JCheckBox("Demo Exp");
		chckbxDemoExp.setBounds(0, 674, 87, 23);
		contentPane.add(chckbxDemoExp);
		
		lblFireProb = new JLabel("HE Fire");
		lblFireProb.setBounds(300, 540, 60, 15);
		contentPane.add(lblFireProb);
		
		txtHEDmg = new JTextArea();
		txtHEDmg.setEditable(false);
		txtHEDmg.setBounds(380, 505, 70, 21);
		contentPane.add(txtHEDmg);
		
		txtrAPDmg = new JTextArea();
		txtrAPDmg.setEditable(false);
		txtrAPDmg.setBounds(380, 446, 70, 21);
		contentPane.add(txtrAPDmg);
		
		lblApDmg = new JLabel("AP DMG");
		lblApDmg.setBounds(300, 450, 60, 15);
		contentPane.add(lblApDmg);
		
		lblHeDmg = new JLabel("HE DMG");
		lblHeDmg.setBounds(300, 510, 55, 15);
		contentPane.add(lblHeDmg);
		
		comboBoxTorpedoList = new JComboBox<String>();
		comboBoxTorpedoList.setBounds(636, 57, 140, 21);
		contentPane.add(comboBoxTorpedoList);
		
		comboBoxRadarList = new JComboBox<String>();
		comboBoxRadarList.setBounds(479, 57, 140, 21);
		contentPane.add(comboBoxRadarList);
		
		comboBoxEngineList = new JComboBox<String>();
		comboBoxEngineList.setBounds(323, 57, 140, 21);
		contentPane.add(comboBoxEngineList);
		
		comboBoxHullList = new JComboBox<String>();
		comboBoxHullList.setBounds(168, 57, 140, 21);
		contentPane.add(comboBoxHullList);
		
		comboBoxTurretList = new JComboBox<String>();
		comboBoxTurretList.setBounds(12, 57, 140, 21);
		contentPane.add(comboBoxTurretList);
		
		lblTurret = new JLabel("Turret");
		lblTurret.setBounds(12, 43, 68, 15);
		contentPane.add(lblTurret);
		
		lblHull = new JLabel("Hull");
		lblHull.setBounds(168, 43, 39, 15);
		contentPane.add(lblHull);
		
		lblEngine = new JLabel("Engine");
		lblEngine.setBounds(323, 43, 68, 15);
		contentPane.add(lblEngine);
		
		lblRadar = new JLabel("Radar");
		lblRadar.setBounds(479, 44, 68, 15);
		contentPane.add(lblRadar);
		
		lblTorpedo = new JLabel("Torpedo");
		lblTorpedo.setBounds(636, 43, 68, 15);
		contentPane.add(lblTorpedo);
		
		lblNation_1 = new JLabel("Nation");
		lblNation_1.setBounds(13, 0, 68, 15);
		contentPane.add(lblNation_1);
		
		lblShipType_1 = new JLabel("Ship Type");
		lblShipType_1.setBounds(114, 0, 68, 15);
		contentPane.add(lblShipType_1);
		
		lblShipName = new JLabel("Ship Name");
		lblShipName.setBounds(219, 0, 68, 15);
		contentPane.add(lblShipName);
		
		textNumBarrels = new JTextArea();
		textNumBarrels.setEditable(false);
		textNumBarrels.setBounds(380, 136, 70, 21);
		contentPane.add(textNumBarrels);
		
		labelBarrels = new JLabel("Barrels");
		labelBarrels.setBounds(300, 140, 70, 15);
		contentPane.add(labelBarrels);
		
		comboBoxConsumable1 = new JComboBox<String>();
		comboBoxConsumable1.setBounds(12, 106, 105, 21);
		contentPane.add(comboBoxConsumable1);
		
		lblConsumable1 = new JLabel("Consumable 1");
		lblConsumable1.setBounds(12, 91, 95, 15);
		contentPane.add(lblConsumable1);
		
		comboBoxConsumable2 = new JComboBox<String>();
		comboBoxConsumable2.setBounds(180, 106, 105, 21);
		contentPane.add(comboBoxConsumable2);
		
		comboBoxConsumable3 = new JComboBox<String>();
		comboBoxConsumable3.setBounds(382, 106, 105, 21);
		contentPane.add(comboBoxConsumable3);
		
		comboBoxConsumable4 = new JComboBox<String>();
		comboBoxConsumable4.setBounds(587, 105, 105, 21);
		contentPane.add(comboBoxConsumable4);
		
		lblConsumable2 = new JLabel("Consumable 2");
		lblConsumable2.setBounds(180, 91, 87, 15);
		contentPane.add(lblConsumable2);
		
		lblConsumable3 = new JLabel("Consumable 3");
		lblConsumable3.setBounds(383, 91, 87, 15);
		contentPane.add(lblConsumable3);
		
		lblConsumable4 = new JLabel("Consumable 4");
		lblConsumable4.setBounds(587, 91, 87, 15);
		contentPane.add(lblConsumable4);
		
		lblModule1 = new JLabel("Module 1");
		lblModule1.setBounds(12, 138, 80, 15);
		contentPane.add(lblModule1);
		
		lblModule2 = new JLabel("Module 2");
		lblModule2.setBounds(12, 184, 80, 15);
		contentPane.add(lblModule2);
		
		lblModule3 = new JLabel("Module 3");
		lblModule3.setBounds(12, 229, 80, 15);
		contentPane.add(lblModule3);
		
		lblModule4 = new JLabel("Module 4");
		lblModule4.setBounds(12, 277, 80, 15);
		contentPane.add(lblModule4);
		
		lblModule5 = new JLabel("Module 5");
		lblModule5.setBounds(12, 322, 80, 15);
		contentPane.add(lblModule5);
		
		lblModule6 = new JLabel("Module 6");
		lblModule6.setBounds(12, 370, 80, 15);
		contentPane.add(lblModule6);
		
		consume4Number = new JTextArea();
		consume4Number.setEditable(false);
		consume4Number.setBounds(700, 105, 31, 21);
		contentPane.add(consume4Number);
		
		consume3Number = new JTextArea();
		consume3Number.setEditable(false);
		consume3Number.setBounds(499, 105, 31, 21);
		contentPane.add(consume3Number);
		
		consume2Number = new JTextArea();
		consume2Number.setEditable(false);
		consume2Number.setBounds(291, 105, 31, 21);
		contentPane.add(consume2Number);
		
		chckbxSuperintendent = new JCheckBox("Superinten");
		chckbxSuperintendent.setBounds(479, 649, 89, 23);
		contentPane.add(chckbxSuperintendent);
		
		horsePower = new JTextArea();
		horsePower.setEditable(false);
		horsePower.setBounds(218, 322, 70, 21);
		contentPane.add(horsePower);
		
		lblHorsePower = new JLabel("Horse Power");
		lblHorsePower.setBounds(140, 326, 78, 15);
		contentPane.add(lblHorsePower);
		
		turretBarrelDiameter = new JTextArea();
		turretBarrelDiameter.setText("mm");
		turretBarrelDiameter.setEditable(false);
		turretBarrelDiameter.setBounds(380, 167, 70, 21);
		contentPane.add(turretBarrelDiameter);
		
		lblBarrelDiameter = new JLabel("Barrel Size");
		lblBarrelDiameter.setBounds(300, 171, 70, 15);
		contentPane.add(lblBarrelDiameter);
		
		textNumTubes = new JTextArea();
		textNumTubes.setEditable(false);
		textNumTubes.setBounds(706, 136, 70, 21);
		contentPane.add(textNumTubes);
		
		tubeDiameter = new JTextArea();
		tubeDiameter.setText("mm");
		tubeDiameter.setEditable(false);
		tubeDiameter.setBounds(706, 167, 70, 21);
		contentPane.add(tubeDiameter);
		
		lblTubes = new JLabel("Torp Tubes");
		lblTubes.setBounds(624, 140, 70, 15);
		contentPane.add(lblTubes);
		
		labelTubeDiameter = new JLabel("Torp Size");
		labelTubeDiameter.setBounds(624, 171, 70, 15);
		contentPane.add(labelTubeDiameter);
		
		chckbxJY2 = new JCheckBox("JY2");
		chckbxJY2.setToolTipText("-20% flood recovery time");
		chckbxJY2.setBounds(552, 474, 60, 23);
		contentPane.add(chckbxJY2);
		
		chckbxIY = new JCheckBox("IY");
		chckbxIY.setToolTipText("-20% fire extinguishing time");
		chckbxIY.setBounds(671, 474, 55, 23);
		contentPane.add(chckbxIY);
		
		chckbxNE7 = new JCheckBox("NE7");
		chckbxNE7.setToolTipText("+10% AA dps");
		chckbxNE7.setBounds(730, 474, 55, 23);
		contentPane.add(chckbxNE7);
		
		chckbxSM = new JCheckBox("SM");
		chckbxSM.setToolTipText("+5% max ship speed");
		chckbxSM.setBounds(671, 505, 55, 23);
		contentPane.add(chckbxSM);
		
		chckbxVL = new JCheckBox("VL");
		chckbxVL.setToolTipText("+1% chance of fire for 160+ mm, +0.5% fire for <160 mm");
		chckbxVL.setBounds(730, 505, 55, 23);
		contentPane.add(chckbxVL);
		
		chckbxMY6 = new JCheckBox("MY6");
		chckbxMY6.setToolTipText("+5% secondary range");
		chckbxMY6.setBounds(552, 536, 60, 23);
		contentPane.add(chckbxMY6);
		
		chckbxIX = new JCheckBox("IX");
		chckbxIX.setToolTipText("+1% chance of fire for 160+ mm, +0.5% fire for <160 mm");
		chckbxIX.setBounds(671, 536, 55, 23);
		contentPane.add(chckbxIX);
		
		torpDetection = new JTextArea();
		torpDetection.setText("km");
		torpDetection.setEditable(false);
		torpDetection.setBounds(706, 291, 70, 21);
		contentPane.add(torpDetection);
		
		lblTorpDetection = new JLabel("Torp Detect");
		lblTorpDetection.setBounds(624, 295, 70, 15);
		contentPane.add(lblTorpDetection);
		
		sigmaCount = new JTextArea();
		sigmaCount.setEditable(false);
		sigmaCount.setBounds(380, 353, 70, 21);
		contentPane.add(sigmaCount);
		
		lblSigmaCount = new JLabel("Sigma");
		lblSigmaCount.setBounds(300, 357, 60, 15);
		contentPane.add(lblSigmaCount);
		
		maxRepairCost = new JTextArea();
		maxRepairCost.setEditable(false);
		maxRepairCost.setBounds(218, 198, 70, 21);
		contentPane.add(maxRepairCost);
		
		lblMaxRepairCost = new JLabel("Max Repair");
		lblMaxRepairCost.setBounds(140, 202, 70, 15);
		contentPane.add(lblMaxRepairCost);
		
		comboBoxCamouflage = new JComboBox<String>();
		comboBoxCamouflage.setBounds(12, 431, 116, 21);
		contentPane.add(comboBoxCamouflage);
		
		lblCamouflage = new JLabel("Camouflage");
		lblCamouflage.setBounds(12, 416, 78, 15);
		contentPane.add(lblCamouflage);
		
		consume4Reload = new JTextArea();
		consume4Reload.setEditable(false);
		consume4Reload.setBounds(737, 105, 39, 21);
		contentPane.add(consume4Reload);
		
		consume3Reload = new JTextArea();
		consume3Reload.setEditable(false);
		consume3Reload.setBounds(536, 105, 39, 21);
		contentPane.add(consume3Reload);

		consume2Reload = new JTextArea();
		consume2Reload.setEditable(false);
		consume2Reload.setBounds(329, 105, 39, 21);
		contentPane.add(consume2Reload);
		
		consume1Reload = new JTextArea();
		consume1Reload.setEditable(false);
		consume1Reload.setBounds(130, 105, 39, 21);
		contentPane.add(consume1Reload);
		
		lblReload1 = new JLabel("Reload");
		lblReload1.setBounds(129, 91, 39, 15);
		contentPane.add(lblReload1);
		
		lblReload2 = new JLabel("Reload");
		lblReload2.setBounds(329, 91, 39, 15);
		contentPane.add(lblReload2);
		
		lblReload3 = new JLabel("Reload");
		lblReload3.setBounds(536, 91, 39, 15);
		contentPane.add(lblReload3);
		
		lblReload4 = new JLabel("Reload");
		lblReload4.setBounds(737, 91, 39, 15);
		contentPane.add(lblReload4);
		
		expFactor = new JTextArea();
		expFactor.setText("%");
		expFactor.setEditable(false);
		expFactor.setBounds(12, 493, 70, 21);
		contentPane.add(expFactor);
		
		lblExpFactor = new JLabel("XP");
		lblExpFactor.setBounds(12, 478, 70, 15);
		contentPane.add(lblExpFactor);
		
		captainExpFactor = new JTextArea();
		captainExpFactor.setText("%");
		captainExpFactor.setEditable(false);
		captainExpFactor.setBounds(12, 540, 70, 21);
		contentPane.add(captainExpFactor);
		
		lblCaptainXp = new JLabel("Captain XP");
		lblCaptainXp.setBounds(12, 524, 70, 15);
		contentPane.add(lblCaptainXp);
		
		chckbxNF = new JCheckBox("NF");
		chckbxNF.setToolTipText("-5% consumables reload");
		chckbxNF.setBounds(671, 446, 55, 23);
		contentPane.add(chckbxNF);
		
		chckbxZH = new JCheckBox("ZH");
		chckbxZH.setToolTipText("+50% captain xp");
		chckbxZH.setBounds(552, 505, 60, 23);
		contentPane.add(chckbxZH);
		
		chckbxESCL = new JCheckBox("ESCL");
		chckbxESCL.setToolTipText("+50% xp");
		chckbxESCL.setBounds(730, 536, 60, 23);
		contentPane.add(chckbxESCL);
		
		chckbxPremAcc = new JCheckBox("Prem Acc");
		chckbxPremAcc.setBounds(12, 454, 87, 23);
		contentPane.add(chckbxPremAcc);
		
		chckbxIB3 = new JCheckBox("IB3");
		chckbxIB3.setToolTipText("-10% repair cost");
		chckbxIB3.setBounds(612, 505, 55, 23);
		contentPane.add(chckbxIB3);
		
		chckbxHighAlert = new JCheckBox("High Alert");
		chckbxHighAlert.setBounds(199, 649, 97, 23);
		contentPane.add(chckbxHighAlert);
		
		chckbxMFCAA = new JCheckBox("MFC AA");
		chckbxMFCAA.setToolTipText("+100% AA DPS on guns exceeding 85 mm");
		chckbxMFCAA.setBounds(479, 674, 89, 23);
		contentPane.add(chckbxMFCAA);
		
		chckbxManualAA = new JCheckBox("Manual AA");
		chckbxManualAA.setBounds(525, 136, 87, 23);
		contentPane.add(chckbxManualAA);
		
		chckbxJoAT = new JCheckBox("JoAT");
		chckbxJoAT.setToolTipText("-15% reload of all consumables");
		chckbxJoAT.setBounds(479, 698, 89, 23);
		contentPane.add(chckbxJoAT);
		
		turningRadius = new JTextArea();
		turningRadius.setText("m");
		turningRadius.setEditable(false);
		turningRadius.setBounds(218, 291, 70, 21);
		contentPane.add(turningRadius);
		
		lblTurnRadius = new JLabel("Turn Radius");
		lblTurnRadius.setBounds(140, 295, 78, 15);
		contentPane.add(lblTurnRadius);
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
	 * Sets ship name listener.
	 * @param al
	 */
	public void setShipListener(ActionListener al) 
	{
		comboBoxNationList.addActionListener(al);
		comboBoxShipTypeList.addActionListener(al);
	    //comboBoxShipNameList.addActionListener(al);     
	}
	
	
	public boolean getManualAA()
	{
		return chckbxManualAA.isSelected();
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
	 * Returns whether Torp Arm Exp is selected.
	 * @return
	 */
	public boolean getTorpArmExp()
	{
		return chckbxTorpArmExp.isSelected();
	}
	
	/**
	 * Returns whether Torp Accel is selected.
	 * @return
	 */
	public boolean getTorpAccel()
	{
		return chckbxTorpAccel.isSelected();
	}
	
	public boolean getHighAlert()
	{
		return chckbxHighAlert.isSelected();
	}
	
	public boolean getSuperintendentSkill()
	{
		return chckbxSuperintendent.isSelected();
	}
	
	public boolean getDemoExpSkill()
	{
		return chckbxDemoExp.isSelected();
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
	 * Returns whether Survivability Expert is selected.
	 * @return
	 */
	public boolean getSurvivabilitySkill()
	{
		return chckbxSurvivability.isSelected();
	}
	
	public boolean getMFCAA()
	{
		return chckbxMFCAA.isSelected();
	}
	
	/**
	 * Returns whether Concealment Expert is selected.
	 * @return
	 */
	public boolean getConcealmentSkill()
	{
		return chckbxConcealment.isSelected();
	}	
	
	public boolean getJoAT()
	{
		return chckbxJoAT.isSelected();
	}
	
	
	public boolean getJY2()
	{
		return chckbxJY2.isSelected();
	}
	
	public boolean getIY()
	{
		return chckbxIY.isSelected();
	}
	
	public boolean getIX()
	{
		return chckbxIX.isSelected();
	}
	
	public boolean getNE7()
	{
		return chckbxNE7.isSelected();
	}
	
	public boolean getSM()
	{
		return chckbxSM.isSelected();
	}
	
	public boolean getVL()
	{
		return chckbxVL.isSelected();
	}
	
	public boolean getMY6()
	{
		return chckbxMY6.isSelected();
	}
	
	public boolean getNF()
	{
		return chckbxNF.isSelected();
	}
	
	public boolean getZH()
	{
		return chckbxZH.isSelected();
	}
	
	public boolean getESCL()
	{
		return chckbxESCL.isSelected();
	}
	
	public boolean getIB3()
	{
		return chckbxIB3.isSelected();
	}
	
	public boolean getPremAcc()
	{
		return chckbxPremAcc.isSelected();
	}
	
	/**
	 * Sets tier to returned tier.
	 * @param tier
	 */
	public void setTier(double tier)
	{
		this.tier.setText(String.format("%.0f", tier));
	}
	
	/**
	 * Sets ship type to returned ship type.
	 * @param shipType
	 */
	public void setShipType(String shipType)
	{
		this.shipType.setText(shipType);
	}
	
	public void setMaxRepairCost(long maxRepairCost)
	{		
		this.maxRepairCost.setText(String.valueOf(NumberFormat.getIntegerInstance().format(maxRepairCost)) + " cr");
	}
	
	/**
	 * Sets health to returned health.
	 * @param health
	 */
	public void setHealth(double health)
	{
		this.health.setText(String.valueOf(NumberFormat.getIntegerInstance().format(health)));
	}
	
	/**
	 * Sets speed to returned speed.
	 * @param speed
	 */
	public void setSpeed(double speed)
	{
		this.speed.setText(String.format("%.1f", speed) + " kts");
	}
	
	public void setTurningRadius(double turningRadius)
	{
		this.turningRadius.setText(String.format(NumberFormat.getIntegerInstance().format(turningRadius)) + " m");
	}
	
	public void setHorsePower(int horsePower)
	{
		this.horsePower.setText(String.valueOf(NumberFormat.getIntegerInstance().format(horsePower)) + " hp");
	}
	
	/**
	 * Sets rudder shift to returned rudder shift.
	 * @param rudderShift
	 */
	public void setRudderShift(double rudderShift)
	{
		this.rudder.setText(String.format("%.2f", rudderShift) + " s");
	}
	
	/**
	 * Sets surface concealment to returned surface concealment.
	 * @param sConceal
	 */
	public void setSConceal(double sConceal)
	{
		this.sConceal.setText(String.format("%.2f", sConceal) + " km");
	}
	
	/**
	 * Sets air concealment to returned air concealment.
	 * @param aConceal
	 */
	public void setAConceal(double aConceal)
	{
		this.aConceal.setText(String.format("%.2f", aConceal) + " km");
	}
	
	public void setStealthFireRange(double stealthFireRange)
	{
		this.stealthFireRange.setText(String.format("%.2f", stealthFireRange) + " km");
	}
	
	public void setAAFireAirDetection(double AAFireAirRange)
	{
		this.AAFireAirDetection.setText(String.format("%.2f", AAFireAirRange) + " km");
	}
	
	/**
	 * Sets main gun range to returned main gun range.
	 * @param MGRange
	 */
	public void setMGRange(double MGRange)
	{
		this.MGRange.setText(String.format("%.2f", MGRange) + " km");
	}
	
	/**
	 * Sets main gun reload to returned main gun reload.
	 * @param MGReload
	 */
	public void setMGReload(double MGReload)
	{
		this.txtrMgReload.setText(String.format("%.2f", MGReload) + " s");
	}
	
	/**
	 * Sets main gun rotation degree to returned main gun rotation degree.
	 * @param MGDegs
	 */
	public void setMGDegs(double MGDegs)
	{
		this.txtrMgDegs.setText(String.format("%.1f", MGDegs) + " deg/s");
	}
	
	/**
	 * Sets main gun rotation time to returned main gun rotation time.
	 * @param MGTime
	 */
	public void setMGTime(double MGTime)
	{
		this.txtrMgTime.setText(String.format("%.2f", MGTime) + " s");
	}
	
	public void setMGDispersion(double MGDispersion)
	{
		this.mainGunDispersionRange.setText(String.format("%.0f", MGDispersion) + " m");
	}
	
	public void setSigmaCount(double sigmaCount)
	{
		this.sigmaCount.setText(String.format("%.1f", sigmaCount));
	}
	
	public void setAPShellSpeed(double APShellSpeed)
	{
		this.APShellSpeed.setText(String.format("%.0f", APShellSpeed) + " m/s");
	}
	
	public void setAPShellDMG(double APShellDMG)
	{
		this.txtrAPDmg.setText(String.valueOf(NumberFormat.getIntegerInstance().format(APShellDMG)));
	}
	
	public void setHEShellSpeed(double HEShellSpeed)
	{
		this.HEShellSpeed.setText(String.format("%.0f", HEShellSpeed) + " m/s");
	}
	
	public void setHEShellDMG(double HEShellDMG)
	{
		this.txtHEDmg.setText(String.valueOf(NumberFormat.getIntegerInstance().format(HEShellDMG)));
	}
	
	public void setHEShellBurnProb(double HEShellBurnProb)
	{
		this.HEShellBurnProb.setText(String.format("%.1f", HEShellBurnProb) + " %");
	}
	
	public void setNumBarrels(int numTurrets, int numBarrels)
	{
		this.textNumBarrels.setText(String.valueOf(numTurrets) + " X " + String.valueOf(numBarrels));
	}
	
	public void setTurretBarrelDiameter(double barrelDiameter)
	{
		this.turretBarrelDiameter.setText(String.format("%.0f", barrelDiameter) + " mm");
	}
	
	public void setSecondaryMaxDist(double secondaryMaxDist)
	{
		this.secondaryMaxDist.setText(String.format("%.2f", secondaryMaxDist) + " km");
	}
	
	/**
	 * Sets torp range to returned torp range.
	 * @param torpRange
	 */
	public void setTorpRange(double torpRange)
	{
		this.torpRange.setText(String.format("%.2f", torpRange) + " km");
	}
	
	/**
	 * Sets torp reload to returned torp reload.
	 * @param torpReload
	 */
	public void setTorpReload(double torpReload)
	{
		this.torpReload.setText(String.format("%.2f", torpReload) + " s");
	}
	
	/**
	 * Sets torp speed to returned torp speed.
	 * @param torpSpeed
	 */
	public void setTorpSpeed(double torpSpeed)
	{
		this.torpSpeed.setText(String.format("%.1f", torpSpeed) + " kts");
	}
	
	public void setTorpedoVisibilityFactor(double torpedoVisibilityFactor)
	{
		this.torpDetection.setText(String.valueOf(torpedoVisibilityFactor) + " km");
	}
	
	public void setTorpDiameter(double torpDiameter)
	{
		this.tubeDiameter.setText(String.format("%.0f", torpDiameter) + " mm");
	}
			
	public void setTubeCount(int torpTurretCount, int tubeCount)
	{
		this.textNumTubes.setText(String.valueOf(torpTurretCount) + " X " + String.valueOf(tubeCount));
	}
	
	
	/**
	 * Sets AA far range to returned AA far range.
	 * @param aaFar
	 */
	public void setAARangeFar(double aaFar)
	{
		this.aaFar.setText(String.format("%.1f", aaFar) + " km");
	}
	
	/**
	 * Sets AA far dps to returned AA far dps.
	 * @param aaFarDPS
	 */
	public void setAAFarDPS(double aaFarDPS)
	{
		this.aaFarDPS.setText(String.format("%.1f", aaFarDPS) + " dps");
	}
	
	/**
	 * Sets AA medium range to returned AA medium range.
	 * @param aaMedium
	 */
	public void setAARangeMedium(double aaMedium)
	{
		this.aaMedium.setText(String.format("%.1f", aaMedium) + " km");
	}
	
	/**
	 * Sets AA medium dps to returned AA medium dps.
	 * @param aaMediumDPS
	 */
	public void setAAMediumDPS(double aaMediumDPS)
	{
		this.aaMediumDPS.setText(String.format("%.1f", aaMediumDPS) + " dps");
	}
	
	/**
	 * Sets AA near range to returned AA near range.
	 * @param aaNear
	 */
	public void setAARangeNear(double aaNear)
	{
		this.aaNear.setText(String.format("%.1f", aaNear) + " km");
	}
	
	/**
	 * Sets AA near dps to returned AA near dps.
	 * @param aaNearDPS
	 */
	public void setAANearDPS(double aaNearDPS)
	{
		this.aaNearDPS.setText(String.format("%.1f", aaNearDPS) + " dps");
	}
	
	/**
	 * Sets flood time to returned flood time.
	 * @param floodTime
	 */
	public void setFloodTime(double floodTime) 
	{
		this.floodTime.setText(String.format("%.1f", floodTime) + " s");
	}
	
	public void setExpFactor(double expFactor)
	{
		this.expFactor.setText(String.format("%.0f", expFactor) + " %");
	}
	
	public void setCaptainExpFactor(double captainExpFactor)
	{
		this.captainExpFactor.setText(String.format("%.0f", captainExpFactor) + " %");
	}
	
	/**
	 * Sets burn time to returned burn time.
	 * @param burnTime
	 */
	public void setBurnTime(double burnTime) 
	{
		this.burnTime.setText(String.format("%.1f", burnTime) + " s");
	}
	
	public void setConsume1Reload(double consume1Reload)
	{
		if (consume1Reload == 0)
		{
			this.consume1Reload.setText("None");
		}
		else
		{
			this.consume1Reload.setText(String.format("%.0f", consume1Reload) + " s");
		}
	}
	
	public void setConsume2Reload(double consume2Reload)
	{
		if (consume2Reload == 0)
		{
			this.consume2Reload.setText("None");
		}
		else
		{
			this.consume2Reload.setText(String.format("%.0f", consume2Reload) + " s");
		}
	}
	
	public void setConsume3Reload(double consume3Reload)
	{	
		if (consume3Reload == 0)
		{
			this.consume3Reload.setText("None");
		}
		else
		{
			this.consume3Reload.setText(String.format("%.0f", consume3Reload) + " s");
		}
	}
	
	public void setConsume4Reload(double consume4Reload)
	{
		if (consume4Reload == 0)
		{
			this.consume4Reload.setText("None");
		}
		else
		{
			this.consume4Reload.setText(String.format("%.0f", consume4Reload) + " s");
		}
	}
	
	public void setConsume2Count(int consume2Count)
	{
		if (!comboBoxConsumable2.getSelectedItem().toString().equals("None") && (consume2Count == -1 || consume2Count == 0))
		{
			this.consume2Number.setText("Inf");
		}
		else if (comboBoxConsumable2.getSelectedItem().toString().equals("None") && (consume2Count == 1 || consume2Count == 0)) 
		{
			this.consume2Number.setText("None");
		}
		else
		{
			this.consume2Number.setText(String.valueOf(consume2Count));
		}
	}
	
	public void setConsume3Count(int consume3Count)
	{
		if (comboBoxConsumable3.getSelectedItem().toString().equals("None") && (consume3Count == 1 || consume3Count == 0)) 
		{
			this.consume3Number.setText("None");
		}
		else
		{
			this.consume3Number.setText(String.valueOf(consume3Count));
		}
	}
	
	public void setConsume4Count(int consume4Count)
	{
		if (comboBoxConsumable4.getSelectedItem().toString().equals("None") && (consume4Count == 1 || consume4Count == 0)) 
		{
			this.consume4Number.setText("None");
		}
		else
		{
			this.consume4Number.setText(String.valueOf(consume4Count));
		}
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
	
	public void setCamouflageBox(List<String> aList)
	{
		comboBoxCamouflage.removeAllItems();
		comboBoxCamouflage.addItem("None");
		
		if (aList != null)
		{
			for (int i = 0; i < aList.size(); i++)
			{
				comboBoxCamouflage.addItem(aList.get(i));
			}
		}
		
		comboBoxCamouflage.addItem("Type 1");
		comboBoxCamouflage.addItem("Type 2");
		comboBoxCamouflage.addItem("Type 3");
		comboBoxCamouflage.addItem("Type 5");
		comboBoxCamouflage.addItem("Type 6");
		
	}
	
	public String getCamouflageBox()
	{
		return comboBoxCamouflage.getSelectedItem().toString();
	}
	
	
	
	public void setConsumable1(List<String> aList)
	{
		comboBoxConsumable1.removeAllItems();
		for (int i = 0; i < aList.size(); i++)
		{
			comboBoxConsumable1.addItem(aList.get(i));;
		}
	}
	
	public String getConsumable1()
	{
		return comboBoxConsumable1.getSelectedItem().toString();
	}
	
	public void setConsumable2(List<String> aList)
	{
		comboBoxConsumable2.removeAllItems();
		for (int i = 0; i < aList.size(); i++)
		{
			comboBoxConsumable2.addItem(aList.get(i));;
		}
	}
	
	public String getConsumable2()
	{
		return comboBoxConsumable2.getSelectedItem().toString();
	}
	
	public void setConsumable3(List<String> aList)
	{
		comboBoxConsumable3.removeAllItems();
		for (int i = 0; i < aList.size(); i++)
		{
			comboBoxConsumable3.addItem(aList.get(i));;
		}
	}
	
	public String getConsumable3()
	{
		return comboBoxConsumable3.getSelectedItem().toString();
	}
	
	public void setConsumable4(List<String> aList)
	{
		comboBoxConsumable4.removeAllItems();
		for (int i = 0; i < aList.size(); i++)
		{
			comboBoxConsumable4.addItem(aList.get(i));;
		}
	}

	public String getConsumable4()
	{
		return comboBoxConsumable4.getSelectedItem().toString();
	}
	
	
	public String getShipNameBox()
	{
		if (comboBoxShipNameList.getSelectedItem() != null)
		{
			return comboBoxShipNameList.getSelectedItem().toString();
		}
		else
		{
			return null;
		}
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
		comboBoxNationList.addItem("");
		comboBoxNationList.addItem("Germany");
		comboBoxNationList.addItem("Japan");
		comboBoxNationList.addItem("Russia");
		comboBoxNationList.addItem("USA");
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
		comboBoxShipTypeList.addItem("");
		comboBoxShipTypeList.addItem("AirCarrier");
		comboBoxShipTypeList.addItem("Battleship");
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
		
		//AirCarrier		
		comboBoxShipNameList.addItem("Saipan");
		
		//Battleship
		comboBoxShipNameList.addItem("Arkansas");
		comboBoxShipNameList.addItem("Texas");
		comboBoxShipNameList.addItem("Arizona");
		
		//Cruiser
		comboBoxShipNameList.addItem("Albany");
		comboBoxShipNameList.addItem("Marblehead");
		comboBoxShipNameList.addItem("Marblehead L");
		comboBoxShipNameList.addItem("Atlanta");
		comboBoxShipNameList.addItem("Flint");
		comboBoxShipNameList.addItem("Indianapolis");
		
		//Destroyer
		comboBoxShipNameList.addItem("Smith");
		comboBoxShipNameList.addItem("Sims");
		comboBoxShipNameList.addItem("Black");		
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
		//Battleship
		comboBoxShipNameList.addItem("Mikasa");
		comboBoxShipNameList.addItem("Ishizuchi");
		
		//Cruiser
		comboBoxShipNameList.addItem("Katori");
		comboBoxShipNameList.addItem("Iwaki");
		comboBoxShipNameList.addItem("Yubari");
		comboBoxShipNameList.addItem("Tone");
		comboBoxShipNameList.addItem("Atago");
		
		//Destroyer
		comboBoxShipNameList.addItem("Tachibana");
		comboBoxShipNameList.addItem("Tachibana L");
		comboBoxShipNameList.addItem("Fujin");
		comboBoxShipNameList.addItem("Kamikaze");
		comboBoxShipNameList.addItem("Kamikaze R");		
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
		
		//Battleship
		comboBoxShipNameList.addItem("Nikolay");
						
		//Cruiser
		comboBoxShipNameList.addItem("Diana");
		comboBoxShipNameList.addItem("Diana L");
		comboBoxShipNameList.addItem("Aurora");
		comboBoxShipNameList.addItem("Krasni Krym");
		comboBoxShipNameList.addItem("Murmansk");
		comboBoxShipNameList.addItem("Molotov");
		comboBoxShipNameList.addItem("Kutuzov");
		
		//Destroyer
		comboBoxShipNameList.addItem("Gremyashchy");
				
	}
	
	public void setGermanyBattleshipList()
	{
		comboBoxShipNameList.removeAllItems();
		
		comboBoxShipNameList.addItem("Nassau");
		comboBoxShipNameList.addItem("Kaiser");
		comboBoxShipNameList.addItem("Koenig");
		comboBoxShipNameList.addItem("Bayern");
		comboBoxShipNameList.addItem("Gneisenau");
		comboBoxShipNameList.addItem("Bismarck");
		comboBoxShipNameList.addItem("Friedrich_der_Grosse");
		comboBoxShipNameList.addItem("Grossdeutschland");
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
		
		//Battleship
		comboBoxShipNameList.addItem("Scharnhorst");
		comboBoxShipNameList.addItem("Tirpitz");
		
		//Cruiser
		comboBoxShipNameList.addItem("Emden");
	}
	
	public void setPanAsiaPremiumList()
	{
		comboBoxShipNameList.removeAllItems();
		
		//Destroyer
		comboBoxShipNameList.addItem("Anshan");
		comboBoxShipNameList.addItem("LoYang");
	}
	
	public void setUKPremiumList()
	{
		comboBoxShipNameList.removeAllItems();
		
		//Battleship
		comboBoxShipNameList.addItem("Warspite");
		
		//Destroyer
		comboBoxShipNameList.addItem("Campbeltown");			
	}
	
	public void setPolandPremiumList()
	{
		comboBoxShipNameList.removeAllItems();
		
		//Destroyer
		comboBoxShipNameList.addItem("Blyskawica");
	}
	
	public void setNoneList()
	{
		comboBoxShipNameList.removeAllItems();
		
		comboBoxShipNameList.addItem("None");
	}
}
