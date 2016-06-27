import java.awt.BorderLayout;
import java.awt.EventQueue;
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

public class Viewer extends JFrame {

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
	private JCheckBox chckbxTorpArmaExp;
	
	/**
	 * Create the frame.
	 */
	public Viewer() {
		setTitle("WoWS Ship Stats Calculator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 500);
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
		mod1Box.setBounds(12, 41, 116, 21);
		contentPane.add(mod1Box);
		
		mod2Box = new JComboBox();
		mod2Box.setBounds(12, 72, 116, 21);
		contentPane.add(mod2Box);
		
		mod3Box = new JComboBox();
		mod3Box.setBounds(12, 103, 116, 21);
		contentPane.add(mod3Box);
		
		mod4Box = new JComboBox();
		mod4Box.setBounds(12, 134, 116, 21);
		contentPane.add(mod4Box);
		
		mod5Box = new JComboBox();
		mod5Box.setBounds(12, 164, 116, 21);
		contentPane.add(mod5Box);
		
		mod6Box = new JComboBox();
		mod6Box.setBounds(12, 195, 116, 21);
		contentPane.add(mod6Box);
		
		rudder = new JTextArea();
		rudder.setText("s");
		rudder.setBounds(218, 194, 70, 21);
		contentPane.add(rudder);
		
		tier = new JTextArea();
		tier.setBounds(218, 40, 70, 21);
		contentPane.add(tier);
		
		nation = new JTextArea();
		nation.setBounds(218, 71, 70, 21);
		contentPane.add(nation);
		
		shipType = new JTextArea();
		shipType.setBounds(218, 102, 70, 21);
		contentPane.add(shipType);
		
		health = new JTextArea();
		health.setText("HP");
		health.setBounds(218, 133, 70, 21);
		contentPane.add(health);
		
		speed = new JTextArea();
		speed.setText("kts");
		speed.setBounds(218, 163, 70, 21);
		contentPane.add(speed);
		
		MGRange = new JTextArea();
		MGRange.setText("km");
		MGRange.setBounds(380, 40, 70, 21);
		contentPane.add(MGRange);
		
		sConceal = new JTextArea();
		sConceal.setText("km");
		sConceal.setBounds(218, 225, 70, 21);
		contentPane.add(sConceal);
		
		lblTier = new JLabel("Tier");
		lblTier.setBounds(138, 42, 80, 15);
		contentPane.add(lblTier);
		
		lblNation = new JLabel("Nation");
		lblNation.setBounds(138, 75, 80, 15);
		contentPane.add(lblNation);
		
		lblShipType = new JLabel("Ship Type");
		lblShipType.setBounds(138, 106, 80, 15);
		contentPane.add(lblShipType);
		
		lblHealth = new JLabel("Health");
		lblHealth.setBounds(138, 137, 80, 15);
		contentPane.add(lblHealth);
		
		lblSpeed = new JLabel("Speed");
		lblSpeed.setBounds(138, 167, 80, 15);
		contentPane.add(lblSpeed);
		
		lblRudder = new JLabel("Rudder Shift");
		lblRudder.setBounds(136, 198, 80, 15);
		contentPane.add(lblRudder);
		
		lblSConceal = new JLabel("S Conceal");
		lblSConceal.setBounds(136, 229, 80, 15);
		contentPane.add(lblSConceal);
		
		lblMGRange = new JLabel("MG Range");
		lblMGRange.setBounds(300, 44, 68, 15);
		contentPane.add(lblMGRange);
		
		chckbxConcealment = new JCheckBox("Concealment");
		chckbxConcealment.setBounds(210, 427, 105, 23);
		contentPane.add(chckbxConcealment);
		
		chckbxSurvivability = new JCheckBox("Survivability");
		chckbxSurvivability.setBounds(210, 402, 93, 23);
		contentPane.add(chckbxSurvivability);
		
		chckbxAft = new JCheckBox("AFT");
		chckbxAft.setBounds(101, 402, 49, 23);
		contentPane.add(chckbxAft);

		chckbxExpertMarksman = new JCheckBox("Exp Marks");
		chckbxExpertMarksman.setBounds(8, 352, 93, 23);
		contentPane.add(chckbxExpertMarksman);
		
		txtrMgReload = new JTextArea();
		txtrMgReload.setText("s");
		txtrMgReload.setBounds(380, 71, 70, 21);
		contentPane.add(txtrMgReload);
		
		lblMgReload = new JLabel("MG Reload");
		lblMgReload.setBounds(300, 75, 70, 15);
		contentPane.add(lblMgReload);
		
		txtrMgDegs = new JTextArea();
		txtrMgDegs.setText("deg/s");
		txtrMgDegs.setBounds(380, 102, 70, 21);
		contentPane.add(txtrMgDegs);
		
		lblMgDegs = new JLabel("MG Deg/s");
		lblMgDegs.setBounds(300, 106, 70, 15);
		contentPane.add(lblMgDegs);
		
		txtrMgTime = new JTextArea();
		txtrMgTime.setText("s/180 deg");
		txtrMgTime.setBounds(380, 133, 70, 21);
		contentPane.add(txtrMgTime);
		
		lblMgTime = new JLabel("MG Rotation");
		lblMgTime.setBounds(300, 137, 70, 15);
		contentPane.add(lblMgTime);
		
		lblAConceal = new JLabel("A Conceal");
		lblAConceal.setBounds(138, 260, 80, 15);
		contentPane.add(lblAConceal);
		
		aConceal = new JTextArea();
		aConceal.setText("km");
		aConceal.setBounds(218, 256, 70, 21);
		contentPane.add(aConceal);
		
		chckbxConcealCamo = new JCheckBox("Conceal Camo");
		chckbxConcealCamo.setBounds(12, 225, 115, 23);
		contentPane.add(chckbxConcealCamo);
		
		chckbxBft = new JCheckBox("BFT");
		chckbxBft.setBounds(101, 329, 49, 23);
		contentPane.add(chckbxBft);
		
		lblTorpRange = new JLabel("Torp Range");
		lblTorpRange.setBounds(300, 167, 68, 15);
		contentPane.add(lblTorpRange);
		
		lblTorpReload = new JLabel("Torp Reload");
		lblTorpReload.setBounds(298, 198, 70, 15);
		contentPane.add(lblTorpReload);
		
		torpRange = new JTextArea();
		torpRange.setText("km");
		torpRange.setBounds(380, 163, 70, 21);
		contentPane.add(torpRange);
		
		torpReload = new JTextArea();
		torpReload.setText("s");
		torpReload.setBounds(380, 194, 70, 21);
		contentPane.add(torpReload);
		
		chckbxTorpAccel = new JCheckBox("Torp Accel");
		chckbxTorpAccel.setBounds(101, 377, 115, 23);
		contentPane.add(chckbxTorpAccel);
		
		chckbxTorpArmaExp = new JCheckBox("Torp Arma Exp");
		chckbxTorpArmaExp.setBounds(101, 352, 115, 23);
		contentPane.add(chckbxTorpArmaExp);
	}

	
	

	public void setSearchListener(ActionListener al)
	{
		searchButton.addActionListener(al);
	}
	
	public void setCalculateListener(ActionListener al)
	{
		calculateButton.addActionListener(al);
	}
	
	public void setCheckBoxListener4(ActionListener al)
	{
		chckbxSurvivability.addActionListener(al);
		chckbxAft.addActionListener(al);
	}
	
	/**
	public void setShipNameListener(ActionListener al) 
	{
	    comboBoxNationList.addActionListener(al);
	    comboBoxShipTypeList.addActionListener(al);
	    comboBoxShipNameList.addActionListener(al);     
	}
	*/ 
	public JCheckBox getAFTCheckbox()
	{
		return chckbxAft;
	}
	public JCheckBox getSurvivabilityCheckbox()
	{
		return chckbxSurvivability;
	}
	
	public void setAFTUnchecked()
	{
		chckbxAft.setSelected(false);
	}
	public void setSurvivabilityUnchecked()
	{
		chckbxSurvivability.setSelected(false);
	}	
	
	
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
	
	public boolean getTorpAccel()
	{
		return chckbxTorpAccel.isSelected();
	}
	
	public boolean getTorpArmaExp()
	{
		return chckbxTorpArmaExp.isSelected();
	}
	
	
	
	/**
	 * Returns whether Expert Marksman is selected.
	 * @return
	 */
	public boolean getExpertMarksman()
	{
		return chckbxExpertMarksman.isSelected();
	}
	
	public boolean getConcealCamo()
	{
		return chckbxConcealCamo.isSelected();
	}
	
	
	public String getShipName()
	{
		return searchText.getText();
	}	
	
	public void setTier(double tier)
	{
		this.tier.setText(String.valueOf(tier));
	}
	
	public void setNation(String nation)
	{
		this.nation.setText(nation);
	}
	
	public void setShipType(String shipType)
	{
		this.shipType.setText(shipType);
	}
	
	public void setHealth(double health)
	{
		this.health.setText(String.valueOf(health));
	}
	
	public void setSpeed(Object speed)
	{
		this.speed.setText(String.valueOf(speed) + " kts");
	}
	
	public void setRudderShift(double rudderShift)
	{
		this.rudder.setText(String.valueOf(rudderShift) + " s");
	}
	
	public void setSConceal(double sConceal)
	{
		this.sConceal.setText(String.valueOf(sConceal) + " km");
	}
	
	public void setAConceal(double aConceal)
	{
		this.aConceal.setText(String.valueOf(aConceal) + " km");
	}
	
	public void setMGRange(double MGRange)
	{
		this.MGRange.setText(String.valueOf(MGRange) + " km");
	}
	
	public void setMGReload(double MGReload)
	{
		this.txtrMgReload.setText(String.valueOf(MGReload) + " s");
	}
	
	public void setMGDegs(double MGDegs)
	{
		this.txtrMgDegs.setText(String.valueOf(MGDegs) + " deg/s");
	}
	
	public void setMGTime(double MGTime)
	{
		this.txtrMgTime.setText(String.valueOf(MGTime) + " s");
	}
	
	public void setTorpRange(double torpRange)
	{
		this.torpRange.setText(String.valueOf(torpRange));
	}
	
	public void setTorpReload(double torpReload)
	{
		this.torpReload.setText(String.valueOf(torpReload));
	}
	
	
	public void setModuleBox1(List aList)
	{
		mod1Box.removeAllItems();
		for (int i = 0; i < aList.size(); i++)
		{
			mod1Box.addItem(aList.get(i));
		}
	}
	
	public String getModuleBox1()
	{
		return mod1Box.getSelectedItem().toString();		
	}
	
	public void setModuleBox2(List aList)
	{
		mod2Box.removeAllItems();
		for (int i = 0; i < aList.size(); i++)
		{
			mod2Box.addItem(aList.get(i));
		}
	}
	
	public String getModuleBox2()
	{
		return mod2Box.getSelectedItem().toString();
	}
	
	public void setModuleBox3(List aList)
	{
		mod3Box.removeAllItems();
		for (int i = 0; i < aList.size(); i++)
		{
			mod3Box.addItem(aList.get(i));
		}
	}
	
	public String getModuleBox3()
	{
		return mod3Box.getSelectedItem().toString();
	}
	
	public void setModuleBox4(List aList)
	{
		mod4Box.removeAllItems();
		for (int i = 0; i < aList.size(); i++)
		{
			mod4Box.addItem(aList.get(i));
		}
	}
	
	public String getModuleBox4()
	{
		return mod4Box.getSelectedItem().toString();
	}
	
	public void setModuleBox5(List aList)
	{
		mod5Box.removeAllItems();
		for (int i = 0; i < aList.size(); i++)
		{
			mod5Box.addItem(aList.get(i));
		}
	}
	
	public String getModuleBox5()
	{
		return mod5Box.getSelectedItem().toString();
	}
	
	public void setModuleBox6(List aList)
	{
		mod6Box.removeAllItems();
		for (int i = 0; i < aList.size(); i++)
		{
			mod6Box.addItem(aList.get(i));
		}
	}
	
	public String getModuleBox6()
	{
		
		return mod6Box.getSelectedItem().toString();
	}

	/**
	public JComboBox getNationListComboBox() 
	{	
		return comboBoxNationList;
	}

	public void setNationList(List<String> aList) 
	{
		comboBoxNationList.removeAllItems();
		for (int i = 0; i < aList.size(); i++)
		{
			comboBoxNationList.addItem(aList.get(i));
		}
	}

	public void setShipTypeList(List<String> aList) 
	{
		comboBoxShipTypeList.removeAllItems();
		for (int i = 0; i < aList.size(); i++)
		{
			comboBoxShipTypeList.addItem(aList.get(i));
		}		
	}

	public JComboBox getShipTypeListComboBox() 
	{		
		return comboBoxShipTypeList;
	}

	public void setShipNameList(List<String> aList) 
	{
		comboBoxShipNameList.removeAllItems();
		for (int i = 0; i < aList.size(); i++)
		{
			comboBoxShipNameList.addItem(aList.get(i));
		}
	}
	*/
}
