package dice;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class ComboPopup extends JFrame{
	private SecureRandom random;
	
	private ButtonGroup bGroup;
	
	private JMenuBar menuBar;
	private JMenu fileMenu;
	
	private JMenuItem load;
	private JMenu diceMenu;
	private JMenuItem[] diceItems;
	private String[] diceNames = {"d4","d6","d8","d10","d12","d20"};
	
	private JMenuItem packer;
	
	private JButton add;
	private JButton edit;
	private JButton remove;
	private JButton roll;
	
	private JPanel outerComboArea;
	private JPanel comboArea;
	
	private JLabel dbName;
	private JLabel spacerLabel;
	
	private JScrollPane scroll;
	
	private JFileChooser chooser;
	
	private String path = "";
	
	private GridBagConstraints c = new GridBagConstraints();
	
	private EventHandler handler;
	public ComboPopup(){
		super("Combos");
		setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		c.fill = GridBagConstraints.HORIZONTAL;
		
		chooser = new JFileChooser();
		chooser.setFileFilter(new FileNameExtensionFilter("Combos" , "cbs"));
		
		radioElements = new ArrayList<JRadioButton>();
		metas = new ArrayList<MetaCombo>();
		
		try{
			random = SecureRandom.getInstanceStrong(); //We out here throwing mfin' DICE
		}catch(NoSuchAlgorithmException e){
			JOptionPane.showMessageDialog(null, "Failed strong instance.");
			random = new SecureRandom();
		}
/*----------------------------------
 * 			Menu Bar
 */
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		load = new JMenuItem("Load");
		fileMenu.add(load);
		
		diceMenu = new JMenu("Dice");
		menuBar.add(diceMenu);
		diceItems = new JMenuItem[6];
		for(int i = 0; i < diceNames.length; ++i){
			diceItems[i] = new JMenuItem(diceNames[i]);
			diceMenu.add(diceItems[i]);
		}
		
		packer = new JMenuItem("Pack");
		menuBar.add(packer);
/*----------------------------------
 * 			GUI Elements
 */
		outerComboArea = new JPanel();
		outerComboArea.setLayout(new GridBagLayout());
		outerComboArea.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		c.insets = new Insets(5, 5, 5, 5);
		add(outerComboArea, c);
		c.insets = new Insets(1, 1, 1, 1);
		
		add = new JButton("Add");
		add.setToolTipText("Add a new combo set");
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		outerComboArea.add(add, c);
		edit = new JButton("Edit");
		edit.setToolTipText("Edit the selected combo set");
		c.gridx = 1;
		c.gridy = 0;
		outerComboArea.add(edit, c);
		remove = new JButton("Remove");
		remove.setToolTipText("Remove the selected combo set");
		c.gridx = 2;
		c.gridy = 0;
		outerComboArea.add(remove, c);
		
		comboArea = new JPanel();
		comboArea.setLayout(new BoxLayout(comboArea, BoxLayout.PAGE_AXIS));
		comboArea.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 3;
		outerComboArea.add(comboArea, c);
		scroll = new JScrollPane(comboArea);
		outerComboArea.add(scroll, c);
		spacerLabel = new JLabel();
		spacerLabel.setPreferredSize(new Dimension(255,50));
		comboArea.add(spacerLabel);
		
		roll = new JButton("Roll!");
		roll.setToolTipText("Do a roll for the selected combo");
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 3;
		outerComboArea.add(roll, c);
		
		addCombos();
/*----------------------------------
 * 			Events
 */
		handler = new EventHandler();
		load.addActionListener(handler);
		for(int i = 0; i < diceItems.length; ++i){
			diceItems[i].addActionListener(handler);
		}
		packer.addActionListener(handler);
		
		add.addActionListener(handler);
		remove.addActionListener(handler);
		edit.addActionListener(handler);
		roll.addActionListener(handler);
	}
	private class EventHandler implements ActionListener{
		public void actionPerformed(ActionEvent event){
			
			/*-------------------------------------------------
			 * 		Start Event handling for the JMenuBar
			 */
			if(event.getSource()==load){
				if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
					path = chooser.getSelectedFile().getAbsolutePath();
					addCombos();
				}
			}else if(event.getSource()==diceItems[0]){						//	1d4
				JOptionPane.showMessageDialog(null, random.nextInt(4)+1);
			}else if(event.getSource()==diceItems[1]){						//	1d6
				JOptionPane.showMessageDialog(null, random.nextInt(6)+1);
			}else if(event.getSource()==diceItems[2]){						//	1d8
				JOptionPane.showMessageDialog(null, random.nextInt(8)+1);
			}else if(event.getSource()==diceItems[3]){						//	1d10
				JOptionPane.showMessageDialog(null, random.nextInt(10)+1);
			}else if(event.getSource()==diceItems[4]){						//	1d12
				JOptionPane.showMessageDialog(null, random.nextInt(12)+1);
			}else if(event.getSource()==diceItems[5]){						//	1d20
				JOptionPane.showMessageDialog(null, random.nextInt(20)+1);
			}else if(event.getSource()==packer){
				pack();
				
			/*-------------------------------------------------
			 * 		Start Event handling for the main portion
			 */
			}else if(event.getSource()==add){
				if(!path.isEmpty()){
					addCreate();
				}
			}else if(event.getSource()==edit){
				//Create an instance of CreatePopup using the constructor with MetaCombo argument.
			}else if(event.getSource()==remove){
				//Remove all between $name and /n/n in the file, then call addElements() again.
			}else if(event.getSource()==roll){
				for(int i = 0; i < radioElements.size(); ++i){
					if(radioElements.get(i).isSelected()){
						ResultsPopup rp = new ResultsPopup(metas.get(i).getCombos());
						rp.pack();
						rp.setVisible(true);
						rp.setResizable(true);
						break;
					}
				}
				
			/*-------------------------------------------------
			 * 		Start Event handling for create popup
			 */
			}else if(event.getSource()==createAddSet){
				//Probably just use a showInputDialog()
			}else if(event.getSource()==createEdit){
				//addCreate(MetaCombo);
			}else if(event.getSource()==createAddRoll){
				//Probably just use a showInputDialog()
			}else if(event.getSource()==createCancel){
				removeCreate();
			}else if(event.getSource()==createAccept){
				//Do something
				//JFileChooser to save it
				//removeCreate();
			}
		}
	}
	
/*----------------------------------
 * 			Create Popup
 */
	private boolean hasPanel = false;
	
	private JPanel outerCreatePanel;
	private JPanel createPanel;
	private JScrollPane createScroll;
	
	private JLabel createSpacerLabel;
	private JLabel createLabel;
	
	private JButton createAddSet;
	private JButton createAddRoll;
	private JButton createEdit;
	private JButton createAccept;
	private JButton createCancel;
	
	private ButtonGroup metaGroup;
	private ArrayList<ButtonGroup> innerGroups;
	private ArrayList<JRadioButton> metaRadioButtons;
	private ArrayList<ArrayList<JRadioButton>> innerRadioButtons;
	private void addCreate(){
		if(hasPanel){
			return;
		}
		hasPanel = true;
		
		c.insets = new Insets(40,1,1,1);
		outerCreatePanel = new JPanel();
		outerCreatePanel.setLayout(new GridBagLayout());
		outerCreatePanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 0;
		add(outerCreatePanel, c);
		c.insets = new Insets(1,1,1,1);
		
		createSpacerLabel = new JLabel();
		createSpacerLabel.setPreferredSize(new Dimension(100,10));
		outerCreatePanel.add(createSpacerLabel, c);
		
		createPanel = new JPanel();
		createPanel.setLayout(new BoxLayout(createPanel, BoxLayout.PAGE_AXIS));
		createPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		createPanel.setPreferredSize(new Dimension(280,50));
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 3;
		outerCreatePanel.add(createPanel, c);
		createScroll = new JScrollPane(createPanel);
		outerCreatePanel.add(createScroll, c);
		
		createLabel = new JLabel("New Combo: ");
		createPanel.add(createLabel);
		
		c.gridwidth = 1;
		c.gridy = 2;
		createAddSet = new JButton("Add Set");
		createAddSet.setToolTipText("Create a set of dice rolls that are grouped together");
		c.gridx = 0;//
		outerCreatePanel.add(createAddSet, c);
		createEdit = new JButton("Edit");
		createEdit.setToolTipText("Edit the selected roll/set");
		c.gridx = 1;
		outerCreatePanel.add(createEdit, c);
		createAddRoll = new JButton("Add Roll");
		createAddRoll.setToolTipText("Add a dice roll to the selected set");
		c.gridx = 2;
		outerCreatePanel.add(createAddRoll, c);
		
		c.gridwidth = 1;
		c.gridy = 3;
		createCancel = new JButton("Cancel");
		createCancel.setToolTipText("Cancel creation of this combo");
		c.gridx = 0;
		outerCreatePanel.add(createCancel, c);
		createAccept = new JButton("Accept");
		createAccept.setToolTipText("Finish creating this combo");
		c.gridx = 2;
		outerCreatePanel.add(createAccept, c);
		this.pack();
		
		createAddSet.addActionListener(handler);
		createEdit.addActionListener(handler);
		createAddRoll.addActionListener(handler);
		createCancel.addActionListener(handler);
		createAccept.addActionListener(handler);
	}
	private void addCreate(MetaCombo m){
		addCreate();
		
		//Then just use the argument to fill createPanel with JRadioButtons
		//I honestly keep getting confused about the "Combo >> MetaCombo >> ArrayList<MetaCombo>" situation but I think this is right now.
	}
	private void removeCreate(){
		hasPanel = false;
		createPanel.remove(createLabel);
		outerCreatePanel.remove(createScroll);
		outerCreatePanel.remove(createPanel);
		outerCreatePanel.remove(createAddSet);
		outerCreatePanel.remove(createAddRoll);
		outerCreatePanel.remove(createEdit);
		outerCreatePanel.remove(createAccept);
		outerCreatePanel.remove(createCancel);
		remove(outerCreatePanel);
		
		revalidate();
		repaint();
		pack();
	}
	
/*----------------------------------
 * 			Combos/Metas
 */
	private ArrayList<JRadioButton> radioElements;
	private ArrayList<MetaCombo> metas;
	
	private void addCombos(){	//Redo this so that things get passed as arguments, and this method can be used both by main and add
		if(path.equals("")){
			return;
		}else{
			comboArea.removeAll();
			try{
				BufferedReader reader = new BufferedReader(new FileReader(path));
				String line;
				String chainArr[];
				String comboArr[];
				int metaNum = 0;
				int comboNum = 0;
				line = reader.readLine();
				metas.add(new MetaCombo(line.replaceAll("\\$", "")));
				while((line = reader.readLine()) != null){
					if(line.equals("")){
						metaNum++;
						comboNum = 0;
					}else if(line.contains("$")){
						metas.add(new MetaCombo(line.replaceAll("\\$", "")));
					}else{	
						chainArr = line.split(";");
						comboArr = chainArr[0].split(",");
						metas.get(metaNum).addCombo(new Combo(comboArr[0],Integer.parseInt(comboArr[1]),Integer.parseInt(comboArr[2]),Integer.parseInt(comboArr[3]),Integer.parseInt(comboArr[4])));
						for(int i = 1; i < chainArr.length; ++i){
							comboArr = chainArr[i].split(",");
							metas.get(metaNum).addChain(comboNum,new Combo(comboArr[0],Integer.parseInt(comboArr[1]),Integer.parseInt(comboArr[2]),Integer.parseInt(comboArr[3]),Integer.parseInt(comboArr[4])));
						}
					}
					comboNum++;
				}
				reader.close();
			}catch(Exception e) {
				JOptionPane.showMessageDialog(null, "Error: "+e);
				return;
			}

			dbName = new JLabel(path);
			comboArea.add(dbName);
			bGroup = new ButtonGroup();
			for(int i = 0; i < metas.size(); ++i){
				radioElements.add(new JRadioButton(metas.get(i).getName()));
				bGroup.add(radioElements.get(i));
				comboArea.add(radioElements.get(i));
			}
			spacerLabel.setPreferredSize(new Dimension(275,20));
			comboArea.add(spacerLabel);
			this.pack();
		}
	}
	public static void run(){
		ComboPopup myInterface = new ComboPopup();
		myInterface.pack();
		myInterface.setVisible(true);
		myInterface.setResizable(true);
	}
}


/*
* SUGGESTED that they include a header like "Goblin: Punch"
	- Display them alphabetically
* I want to be able to graph the probability distribution of any dice combination.
*/
