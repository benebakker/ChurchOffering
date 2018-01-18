import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class DataEntryForm extends JFrame {

	private JPanel contentPane;
	private JComboBox<String> lastNameField;
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

	private FormController checkLastNameBoxController; 
	private FormController envelopeFieldController;
	
	private ArrayList<Donor> churchDB;
	
	/**
	 * Create the frame.  The data entry form constructor.
	 */
	public DataEntryForm() {
		
		loadChurchDB();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
// Labels setup -------------------------------------------------------		
		JLabel lastNameLabel = new JLabel("Last Name");
		lastNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lastNameLabel.setBounds(44, 6, 89, 16);
		contentPane.add(lastNameLabel);
		
		JLabel firstNameLabel = new JLabel("First Name");
		firstNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		firstNameLabel.setBounds(44, 34, 89, 16);
		contentPane.add(firstNameLabel);
		
		JLabel envelopeLabel = new JLabel("Envelope Number");
		envelopeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		envelopeLabel.setBounds(16, 62, 117, 16);
		contentPane.add(envelopeLabel);
		
		JLabel amountLabel = new JLabel("Amount");
		amountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		amountLabel.setBounds(72, 290, 61, 16);
		contentPane.add(amountLabel);
		
		JLabel addressLabel = new JLabel("Address");
		addressLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		addressLabel.setBounds(72, 90, 61, 16);
		contentPane.add(addressLabel);
		
		JLabel cityLabel = new JLabel("City");
		cityLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		cityLabel.setBounds(72, 118, 61, 16);
		contentPane.add(cityLabel);
		
		JLabel stateLabel = new JLabel("State");
		stateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		stateLabel.setBounds(72, 146, 61, 16);
		contentPane.add(stateLabel);
		
		JLabel zipLabel = new JLabel("Zip");
		zipLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		zipLabel.setBounds(72, 174, 61, 16);
		contentPane.add(zipLabel);
		
		JLabel categoryLabel = new JLabel("Category");
		categoryLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		categoryLabel.setBounds(72, 202, 61, 16);
		contentPane.add(categoryLabel);
		
		JLabel designationLabel = new JLabel("Designation");
		designationLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		designationLabel.setBounds(44, 230, 89, 16);
		contentPane.add(designationLabel);
		
		JLabel descriptionLabel = new JLabel("Description");
		descriptionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		descriptionLabel.setBounds(44, 258, 89, 16);
		contentPane.add(descriptionLabel);
		
		nameInDBLabel = new JLabel("");
		nameInDBLabel.setBounds(297, 6, 134, 16);
		contentPane.add(nameInDBLabel);
		
// text / combobox setup ------------------------------------------------------		
		checkLastNameBoxController = new FormController(this);
		lastNameField = new JComboBox<String>();
		lastNameField.setEditable(true);
		lastNameField.setActionCommand("lastname-event-on");
		lastNameField.setBounds(155, 1, 130, 26);
		for(Donor d:churchDB) {
			lastNameField.addItem(d.getLastName());
		}
		lastNameField.addActionListener(checkLastNameBoxController);
		contentPane.add(lastNameField);
		
		firstNameField = new JTextField();
		firstNameField.setBounds(155, 29, 130, 26);
		contentPane.add(firstNameField);
		firstNameField.setColumns(10);
		
		envelopeFieldController = new FormController(this);
		envelopeField = new JTextField();
		envelopeField.setBounds(155, 57, 130, 26);
		envelopeField.setActionCommand("envelope-event");
		envelopeField.addActionListener(envelopeFieldController);
		contentPane.add(envelopeField);
		envelopeField.setColumns(10);
		
		addressField = new JTextField();
		addressField.setBounds(155, 85, 130, 26);
		contentPane.add(addressField);
		addressField.setColumns(10);
		
		cityField = new JTextField();
		cityField.setBounds(155, 113, 130, 26);
		contentPane.add(cityField);
		cityField.setColumns(10);
		
		String[] states = new String[] {"  ","MA","AK","AL","AR","AZ","CA","CO","CT","DC","DE","FL","GA","GU","HI","IA","ID", "IL","IN",
				"KS","KY","LA","MA","MD","ME","MH","MI","MN","MO","MS","MT","NC","ND","NE","NH","NJ","NM","NV","NY", "OH","OK",
				"OR","PA","PR","PW","RI","SC","SD","TN","TX","UT","VA","VI","VT","WA","WI","WV","WY"};
		stateField = new JComboBox<String>();
		stateField.setEditable(true);
		stateField.setBounds(155, 141, 130, 26);
		for(String s:states) {
			stateField.addItem(s);
		}
		contentPane.add(stateField);
		
		zipField = new JTextField();
		zipField.setBounds(155, 169, 130, 26);
		contentPane.add(zipField);
		zipField.setColumns(10);
		
		String[] categories = new String[] {"","Cash","Check","EFT"};
		categoryField = new JComboBox<String>();
		categoryField.setBounds(155, 197, 130, 26);
		for(String s: categories) {
			categoryField.addItem(s);
		}
		contentPane.add(categoryField);
		
		String[] designations = new String[] {"","Plate","Envelope","Misc.","Designated"};
		designationField = new JComboBox<String>();
		designationField.setBounds(155, 225, 130, 26);
		for(String s: designations) {
			designationField.addItem(s);
		}
		contentPane.add(designationField);

		
		descriptionField = new JTextField();
		descriptionField.setBounds(155, 253, 130, 26);
		contentPane.add(descriptionField);
		descriptionField.setColumns(10);
		
		amountField = new JTextField();
		amountField.setBounds(155, 285, 130, 26);
		contentPane.add(amountField);
		amountField.setColumns(10);
		
// buttons setup --------------------------------------------------------------------		
		FormController enterButtonDataController = new FormController(this);
		JButton enterDataButton = new JButton();
		enterDataButton.setText("Enter Data");
		enterDataButton.setBounds(297, 141, 134, 29);
		enterDataButton.setActionCommand("enter-data");
		enterDataButton.addActionListener(enterButtonDataController);
		contentPane.add(enterDataButton);
		
		FormController showButtonDataController = new FormController(this);
		JButton showDataButton = new JButton();
		showDataButton.setText("Show All Entries");
		showDataButton.setBounds(297, 169, 134, 29);
		showDataButton.setActionCommand("show-data");
		showDataButton.addActionListener(showButtonDataController);
		contentPane.add(showDataButton);
		
		FormController exportToExcelController = new FormController(this);
		JButton exportToExcelButton = new JButton("Create Report");
		exportToExcelButton.setBounds(297, 197, 134, 29);
		exportToExcelButton.setActionCommand("excel-report");
		exportToExcelButton.addActionListener(exportToExcelController);
		contentPane.add(exportToExcelButton);
		
		FormController addNameToDBController = new FormController(this);
		addNameToDBButton = new JButton("Add Name to DataBase");
		addNameToDBButton.setBounds(307, 29, 117, 29);
		addNameToDBButton.setVisible(false);
		addNameToDBButton.setEnabled(false);
		addNameToDBButton.setActionCommand("add-name");
		addNameToDBButton.addActionListener(addNameToDBController);
		contentPane.add(addNameToDBButton);
	
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

	public JComboBox getLastNameField() {
		return lastNameField;
	}

	public void setLastNameField(JComboBox lastNameField) {
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
