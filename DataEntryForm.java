import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;

public class DataEntryForm extends JFrame {

	private JPanel contentPane;
	private JTextField lastNameField;
	private JTextField firstNameField;
	private JTextField envelopeField;
	private JTextField addressField;
	private JTextField cityField;
	private JComboBox<String> stateField;
	private JComboBox<String> categoryField;
	private JTextField descriptionField;
	private JTextField amountField;
	private JTextField zipField;
	private JComboBox<String> designationField;
	private JLabel nameInDBLabel;
	private JButton addNameToDBButton;
	
	private FormController checkLastNameBoxController; 
	private FormController actionController;
	
	private ArrayList<Donor> churchDB;
	
	public JButton getAddNameToDBButton() {
		return addNameToDBButton;
	}

	public void setAddNameToDBButton(JButton addNameToDBButton) {
		this.addNameToDBButton = addNameToDBButton;
	}

	public JLabel getNameInDBLabel() {
		return nameInDBLabel;
	}

	public void setNameInDBLabel(String s) {
		this.nameInDBLabel.setText(s);
	}
	
	/**
	 * Create the frame.  The data entry form constructor.
	 */
	public DataEntryForm() {
		
		loadChurchDB();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 620, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
// Labels setup -------------------------------------------------------
		int xLabel = 30;
		int labelWidth = 110;
		
		JLabel lastNameLabel = new JLabel("Last Name");
		lastNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lastNameLabel.setBounds(xLabel, 6, labelWidth, 16);
		contentPane.add(lastNameLabel);
		
		JLabel firstNameLabel = new JLabel("First Name");
		firstNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		firstNameLabel.setBounds(xLabel, 34, labelWidth, 16);
		contentPane.add(firstNameLabel);
		
		JLabel envelopeLabel = new JLabel("Envelope Number");
		envelopeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		envelopeLabel.setBounds(xLabel, 62, labelWidth, 16);
		contentPane.add(envelopeLabel);
		
		JLabel amountLabel = new JLabel("Amount");
		amountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		amountLabel.setBounds(xLabel, 290, labelWidth, 16);
		contentPane.add(amountLabel);
		
		JLabel addressLabel = new JLabel("Address");
		addressLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		addressLabel.setBounds(xLabel, 90, labelWidth, 16);
		contentPane.add(addressLabel);
		
		JLabel cityLabel = new JLabel("City");
		cityLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		cityLabel.setBounds(xLabel, 118, labelWidth, 16);
		contentPane.add(cityLabel);
		
		JLabel stateLabel = new JLabel("State");
		stateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		stateLabel.setBounds(xLabel, 146, labelWidth, 16);
		contentPane.add(stateLabel);
		
		JLabel zipLabel = new JLabel("Zip");
		zipLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		zipLabel.setBounds(xLabel, 174, labelWidth, 16);
		contentPane.add(zipLabel);
		
		JLabel categoryLabel = new JLabel("Category");
		categoryLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		categoryLabel.setBounds(xLabel, 202, labelWidth, 16);
		contentPane.add(categoryLabel);
		
		JLabel designationLabel = new JLabel("Designation");
		designationLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		designationLabel.setBounds(xLabel, 230, labelWidth, 16);
		contentPane.add(designationLabel);
		
		JLabel descriptionLabel = new JLabel("Description");
		descriptionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		descriptionLabel.setBounds(xLabel, 258, labelWidth, 16);
		contentPane.add(descriptionLabel);
		
// text / combobox setup ------------------------------------------------------	
		int width = 75;
		int xLeft = 155;
		int xRight = xLeft + width;
		
		actionController = new FormController(this);
		lastNameField = new JTextField();
		lastNameField.setBounds(xLeft, 1, xRight, 26);
		lastNameField.setActionCommand("lastname-event");
		lastNameField.addActionListener(actionController);
		contentPane.add(lastNameField);
		lastNameField.setColumns(10);
		
		firstNameField = new JTextField();
		firstNameField.setBounds(xLeft, 29, xRight, 26);
		contentPane.add(firstNameField);
		firstNameField.setColumns(10);
		
		envelopeField = new JTextField();
		envelopeField.setBounds(xLeft, 57, xRight, 26);
		envelopeField.setActionCommand("envelope-event");
		envelopeField.addActionListener(actionController);
		contentPane.add(envelopeField);
		envelopeField.setColumns(10);
		
		addressField = new JTextField();
		addressField.setBounds(xLeft, 85, xRight, 26);
		contentPane.add(addressField);
		addressField.setColumns(10);
		
		cityField = new JTextField();
		cityField.setBounds(xLeft, 113, xRight, 26);
		contentPane.add(cityField);
		cityField.setColumns(10);
		
		String[] states = new String[] {"  ","MA","AK","AL","AR","AZ","CA","CO","CT","DC","DE","FL","GA","GU","HI","IA","ID", "IL","IN",
				"KS","KY","LA","MA","MD","ME","MH","MI","MN","MO","MS","MT","NC","ND","NE","NH","NJ","NM","NV","NY", "OH","OK",
				"OR","PA","PR","PW","RI","SC","SD","TN","TX","UT","VA","VI","VT","WA","WI","WV","WY"};
		stateField = new JComboBox<String>();
		stateField.setEditable(true);
		stateField.setBounds(xLeft, 141, xRight, 26);
		for(String s:states) {
			stateField.addItem(s);
		}
		contentPane.add(stateField);
		
		zipField = new JTextField();
		zipField.setBounds(xLeft, 169, xRight, 26);
		contentPane.add(zipField);
		zipField.setColumns(10);
		
		String[] categories = new String[] {"","Cash","Check","EFT"};
		categoryField = new JComboBox<String>();
		categoryField.setBounds(xLeft, 197, xRight, 26);
		for(String s: categories) {
			categoryField.addItem(s);
		}
		contentPane.add(categoryField);
		
		String[] designations = new String[] {"","Plate","Envelope","Misc.","Designated"};
		designationField = new JComboBox<String>();
		designationField.setBounds(xLeft, 225, xRight, 26);
		for(String s: designations) {
			designationField.addItem(s);
		}
		contentPane.add(designationField);
		
		descriptionField = new JTextField();
		descriptionField.setBounds(xLeft, 253, xRight, 26);
		contentPane.add(descriptionField);
		descriptionField.setColumns(10);
		
		amountField = new JTextField();
		amountField.setBounds(xLeft, 285, xRight, 26);
		contentPane.add(amountField);
		amountField.setColumns(10);
		
		setFocusTraversalPolicy(new FocusTraversalOnArray(
                new Component[] { 
                	lastNameField, firstNameField, envelopeField, addressField, cityField, stateField, zipField , 
                	categoryField, designationField, descriptionField, amountField }));
		
// buttons setup --------------------------------------------------------------------	
		int xButton = 400;
		int widthButton = 200;
		
		FormController enterButtonDataController = new FormController(this);
		JButton enterDataButton = new JButton();
		enterDataButton.setText("Enter Data");
		enterDataButton.setBounds(xButton, 141, widthButton, 29);
		enterDataButton.setActionCommand("enter-data");
		enterDataButton.addActionListener(enterButtonDataController);
		contentPane.add(enterDataButton);
		
		FormController showButtonDataController = new FormController(this);
		JButton showDataButton = new JButton();
		showDataButton.setText("Show All Entries");
		showDataButton.setBounds(xButton, 169, widthButton, 29);
		showDataButton.setActionCommand("show-data");
		showDataButton.addActionListener(showButtonDataController);
		contentPane.add(showDataButton);
		
		FormController exportToExcelController = new FormController(this);
		JButton exportToExcelButton = new JButton("Create Report");
		exportToExcelButton.setBounds(xButton, 197, widthButton, 29);
		exportToExcelButton.setActionCommand("excel-report");
		exportToExcelButton.addActionListener(exportToExcelController);
		contentPane.add(exportToExcelButton);
		
		nameInDBLabel = new JLabel("");
		nameInDBLabel.setBounds(xButton + 5 , 6, widthButton - 10, 16);
		nameInDBLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(nameInDBLabel);
		
		FormController addNameToDBController = new FormController(this);
		addNameToDBButton = new JButton("Add Name to DataBase");
		addNameToDBButton.setBounds(xButton, 29, widthButton, 29);
		addNameToDBButton.setVisible(false);
		addNameToDBButton.setEnabled(false);
		addNameToDBButton.setActionCommand("add-name");
		addNameToDBButton.addActionListener(addNameToDBController);
		contentPane.add(addNameToDBButton);
		
		contentPane.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{lastNameLabel, 
				firstNameLabel, envelopeLabel, amountLabel, addressLabel, cityLabel, stateLabel, 
				zipLabel, categoryLabel, designationLabel, descriptionLabel, nameInDBLabel, 
				lastNameField, firstNameField, envelopeField, addressField, cityField, 
				stateField, zipField, categoryField, designationField, descriptionField, 
				amountField, enterDataButton, showDataButton, exportToExcelButton, addNameToDBButton}));
		
		setVisible(true);
	}
	
	public FormController getCheckLastNameBoxController() {
		return checkLastNameBoxController;
	}

	public void setCheckLastNameBoxController(FormController checkLastNameBoxController) {
		this.checkLastNameBoxController = checkLastNameBoxController;
	}

	public ArrayList<Donor> getChurchDB(){
		return churchDB;
	}

	public JTextField getLastNameField() {
		return lastNameField;
	}

	public void setLastNameField(JTextField lastNameField) {
		this.lastNameField = lastNameField;
	}

	public JTextField getFirstNameField() {
		return firstNameField;
	}

	public void setFirstNameField(String firstName) {
		this.firstNameField.setText(firstName);
	}

	public JTextField getEnvelopeField() {
		return envelopeField;
	}

	public void setEnvelopeField(String e) {
		this.envelopeField.setText(e);
	}

	public JTextField getAddressField() {
		return addressField;
	}

	public void setAddressField(String a) {
		this.addressField.setText(a);
	}

	public JTextField getCityField() {
		return cityField;
	}

	public void setCityField(String c) {
		this.cityField.setText(c);
	}

	public JComboBox getStateField() {
		return stateField;
	}

	public void setStateField(String s) {
		this.stateField.setSelectedItem(s);
	}

	public JComboBox getCategoryField() {
		return categoryField;
	}

	public void setCategoryField(String s) {
		this.categoryField.setSelectedItem(s);
	}

	public JComboBox getDesignationField() {
		return designationField;
	}

	public void setDesignationField(String s) {
		this.designationField.setSelectedItem(s);
	}
	
	public JTextField getDescriptionField() {
		return descriptionField;
	}

	public void setDescriptionField(JTextField descriptionField) {
		this.descriptionField = descriptionField;
	}

	public JTextField getAmountField() {
		return amountField;
	}

	public void setAmountField(JTextField amountField) {
		this.amountField = amountField;
	}

	public JTextField getZipField() {
		return zipField;
	}

	public void setZipField(String z) {
		this.zipField.setText(z);
	}
	
	
	public void alertMessage(String s) {
		//default title and icon
		JOptionPane.showMessageDialog(contentPane, s);
	}
	
	public void loadChurchDB() {
		
		churchDB = new ArrayList<Donor>();
		
		Donor d;
		String fn;
		String ln;
		String env;
		String ad;
		String ct;
		String st;
		String zp;
		
	    String churchDBfile = "churchDB1.csv";
	    String line = null;
	    try {
	    		FileReader fileReader = new FileReader(churchDBfile);

	        // Always wrap FileReader in BufferedReader.
	        BufferedReader bufferedReader = new BufferedReader(fileReader);
	        
	        while((line = bufferedReader.readLine()) != null) {
	        		d = new Donor();
	        		int commaPlace = line.indexOf(',');	
	        		try {
	        			env = line.substring(0, commaPlace);
	        		}
	        		catch (Exception e){
	        			env="0";
	        		}
	        		
	        		int nextCommaPlace = line.indexOf(',', commaPlace+1);
	        		ln = line.substring(commaPlace+1, nextCommaPlace);
	        		commaPlace=nextCommaPlace;
	        		nextCommaPlace = line.indexOf(',', commaPlace+1);
	        		fn = line.substring(commaPlace+1, nextCommaPlace);
	        		commaPlace=nextCommaPlace;
	        		nextCommaPlace = line.indexOf(',', commaPlace+1);
	        		ad = line.substring(commaPlace+1, nextCommaPlace);
	        		commaPlace=nextCommaPlace;
	        		nextCommaPlace = line.indexOf(',', commaPlace+1);
	        		ct = line.substring(commaPlace+1, nextCommaPlace);
	        		commaPlace=nextCommaPlace;
	        		nextCommaPlace = line.indexOf(',', commaPlace+1);
	        		st = line.substring(commaPlace+1, nextCommaPlace);
	        		zp = line.substring(nextCommaPlace+1);
	        		
	        		if(ln.compareTo("Last Name")==0) {
	        			System.out.println("configuring the first donor - removing csv file headers....");
	        			d.setEnvelopeNumber("");
		        		d.setFirstName("");
		        		d.setLastName("");
		        		d.setAddress("");
		        		d.setCity("");
		        		d.setState("");
		        		d.setZip("");
	        		}
		        	else {	
		        		d.setEnvelopeNumber(env);
		        		d.setFirstName(fn);
		        		d.setLastName(ln);
		        		d.setAddress(ad);
		        		d.setCity(ct);
		        		d.setState(st);
		        		d.setZip(zp);

		        	}
	        		churchDB.add(d);   		
	        }   

	        // Always close files.
	        bufferedReader.close();         
	    }
	    catch(FileNotFoundException ex) {
	    		System.out.println("Unable to open file '" + churchDBfile + "'");                
	    }
	    catch(IOException ex) {
	        System.out.println("Error reading file '" + churchDBfile + "'");
	    }	   
	}
}
