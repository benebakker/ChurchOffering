import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import org.eclipse.wb.swing.FocusTraversalOnArray;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Row;

@SuppressWarnings("serial")
public class DataEntryForm extends JFrame {

	private JPanel contentPane;

	private JLabel lastNameLabel;
	private JLabel firstNameLabel;
	private JLabel envelopeLabel;
	private JLabel amountLabel;
	private JLabel addressLabel;
	private JLabel cityLabel;
	private JLabel stateLabel;
	private JLabel zipLabel;
	private JLabel categoryLabel;
	private JLabel designationLabel;
	private JLabel descriptionLabel;

	private JTextField lastNameField;
	private JTextField firstNameField;
	private JTextField envelopeField;
	private JTextField addressField;
	private JTextField cityField;
	private JComboBox<String> stateField;
	private JComboBox<String> categoryField;
	private JComboBox<String> descriptionField;
	private JTextField amountField;
	private JTextField zipField;
	private JComboBox<String> designationField;
	private JLabel nameInDBLabel;

	private JButton addNameToDBButton;
	private JButton enterDataButton;
	private JButton showDataButton;

	public FormController actionController;

	private ArrayList<Donor> churchDB;
	LocalDate localDate;
	DateTimeFormatter dtf;

	public DataEntryForm() {

		int xLeft = 100, width = 650;
		int yTop = 100, height = 500;
		
		loadChurchDB();
		setupMainPanel(xLeft, width, yTop, height);
		addChurchImage();
		setupLabels(xLeft, width, yTop, height);
		setupEntryFields(xLeft, width, yTop, height);
		setupButtons();

		actionController = new FormController(this);
		setupActionControllers();

		contentPane.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{lastNameLabel, 
				firstNameLabel, envelopeLabel, amountLabel, addressLabel, cityLabel, stateLabel, 
				zipLabel, categoryLabel, designationLabel, descriptionLabel, nameInDBLabel, 
				lastNameField, firstNameField, envelopeField, addressField, cityField, 
				stateField, zipField, categoryField, designationField, descriptionField, 
				amountField, enterDataButton, showDataButton, addNameToDBButton}));

		setVisible(true);
	}


	private void setupMainPanel(int xLeft, int width, int yTop, int heigth) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(xLeft, yTop, width, heigth);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(62, 100, 124));
		setContentPane(contentPane);
		contentPane.setLayout(null);	
	}

	private void setupLabels(int xLeft,int width, int yTop , int heigth) {

		int fh = heigth;
		int x = 30, y = fh/23;
		int labelWidth = 110, labelHeight = fh/23;
		int dy = 2*labelHeight;
		
		dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		localDate = LocalDate.now();
		JLabel dateLabel = new JLabel();
		dateLabel = setupLabel(dateLabel, dtf.format(localDate) , 440,100, 100, 30);

		lastNameLabel = setupLabel(lastNameLabel, "Last Name", x, y, labelWidth, labelHeight);
		firstNameLabel = setupLabel(firstNameLabel, "First Name", x, y+1*dy, labelWidth, labelHeight);
		envelopeLabel = setupLabel(envelopeLabel, "Envelope Number", x, y+2*dy, labelWidth, labelHeight);
		addressLabel = setupLabel(addressLabel, "Address", x, y+3*dy, labelWidth, labelHeight);
		cityLabel = setupLabel(cityLabel, "City", x, y+4*dy, labelWidth, labelHeight);
		stateLabel = setupLabel(stateLabel, "State", x, y+5*dy, labelWidth, labelHeight);
		zipLabel = setupLabel(zipLabel, "Zip", x, y+6*dy, labelWidth, labelHeight);
		categoryLabel = setupLabel(categoryLabel, "Category", x, y+7*dy, labelWidth, labelHeight);
		designationLabel = setupLabel(designationLabel, "Designation", x, y+8*dy, labelWidth, labelHeight);
		descriptionLabel = setupLabel(descriptionLabel, "Description", x, y+9*dy, labelWidth, labelHeight);	
		amountLabel = setupLabel(amountLabel, "Amount", x, y+10*dy, labelWidth, labelHeight);
	}

	private JLabel setupLabel(JLabel label , String labelText, int xcoord, int ycoord, int width, int height) {
		label = new JLabel(labelText);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setBounds(xcoord, ycoord, width, height);
		label.setForeground(Color.white);
		contentPane.add(label);
		return label;
	}

	private void setupEntryFields(int xLeft,int Xwidth, int yTop , int fh) {
		
		int x = 170, y = fh/23;
		int width = 200, height = fh/23;
		int dy = 2*height;
		int columns = 10;
		
		lastNameField = setupTextField(x, y, width, height, columns); 
		firstNameField = setupTextField(x, y + 1*dy , width, height, columns); 
		envelopeField = setupTextField(x, y + 2*dy , width, height, columns);
		addressField = setupTextField(x, y + 3*dy , width, height, columns);
		cityField = setupTextField(x, y + 4*dy , width, height, columns);

		String[] states = new String[] {"  ","MA","AK","AL","AR","AZ","CA","CO","CT","DC","DE","FL","GA","GU","HI","IA","ID", "IL","IN",
				"KS","KY","LA","MA","MD","ME","MH","MI","MN","MO","MS","MT","NC","ND","NE","NH","NJ","NM","NV","NY", "OH","OK",
				"OR","PA","PR","PW","RI","SC","SD","TN","TX","UT","VA","VI","VT","WA","WI","WV","WY"};
		stateField = setupComboBox(states, x, y + 5*dy , width, height, columns);
		zipField = setupTextField(x, y + 6*dy , width, height, columns);

		String[] categories = new String[] {"","Cash","Check","EFT"};
		categoryField = setupComboBox(categories, x, y + 7*dy , width, height, columns);

		String[] designations = new String[] {"","Plate","Envelope","Misc.","Designated"};
		designationField = setupComboBox(designations, x, y + 8*dy , width, height, columns);
		String[] descriptions = new String[] {""};
		descriptionField = setupComboBox(descriptions, x, y + 9*dy , width, height, columns);
		amountField = setupTextField(x, y + 10*dy , width, height, columns);
		
		
		

		setFocusTraversalPolicy(new FocusTraversalOnArray(
				new Component[] { lastNameField, firstNameField, envelopeField, addressField, cityField, stateField, 
						zipField, categoryField, designationField, descriptionField, amountField }));
	}
	
	public void clearForm() {
		getLastNameField().setText("");
		getFirstNameField().setText("");
		getEnvelopeField().setText("");
		getAddressField().setText("");
		getCityField().setText("");
		getStateField().setSelectedIndex(0);
		getZipField().setText("");
		getCategoryField().setSelectedIndex(0);
		getDescriptionField().setSelectedIndex(0);
		getDesignationField().setSelectedIndex(0);
		getAmountField().setText("");
	}

	private JTextField setupTextField(int x, int y, int w, int h, int columns) {	
		JTextField jt = new JTextField();
		jt.setBounds(x, y, w, h);
		contentPane.add(jt);
		jt.setColumns(10);
		return jt;
	}

	private JComboBox<String> setupComboBox(String[] array, int x, int y, int w, int h, int columns) {	
		JComboBox<String> jc = new JComboBox<String>();
		jc.setEditable(true);
		jc.setBounds(x, y, w, h);
		for(String s:array) {
			jc.addItem(s);
		}
		contentPane.add(jc);
		return jc;
	}

	private void setupButtons() {
		int xButton = 400;
		int widthButton = 200;

		enterDataButton = setupButton("Enter Data", xButton, 169, widthButton, 29);
		showDataButton = setupButton("Show All Entries", xButton, 199, widthButton, 29);
	}

	private JButton setupButton(String title, int xButton, int yButton, int widthButton, int heightButton) {
		
		JButton b = new JButton();
		b.setText(title);
		b.setBounds(xButton, yButton, widthButton, heightButton);
		contentPane.add(b);
		return b;
	}

	private void setupActionControllers() {

		lastNameField.addActionListener(actionController);		
		lastNameField.setActionCommand("lastname-event");

		envelopeField.addActionListener(actionController);
		envelopeField.setActionCommand("envelope-event");
		
		enterDataButton.addActionListener(actionController);
		enterDataButton.setActionCommand("enter-data");
		
		showDataButton.addActionListener(actionController);
		showDataButton.setActionCommand("show-data");
	}

	private void addChurchImage() {
		ImageIcon churchIcon = new ImageIcon("FBC-website-header_logo.png");
		Image image = churchIcon.getImage();
		JLabel churchLogo = new JLabel(new ImageIcon(getScaledImage(image, 220,70)));		
		churchLogo.setHorizontalAlignment(SwingConstants.RIGHT);
		churchLogo.setBounds(390, 6, 220, 70);
		contentPane.add(churchLogo);
	}

	private Image getScaledImage(Image srcImg, int w, int h){
		BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = resizedImg.createGraphics();

		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, w, h, null);
		g2.dispose();

		return resizedImg;
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

	public JComboBox<String> getStateField() {
		return stateField;
	}

	public void setStateField(String s) {
		this.stateField.setSelectedItem(s);
	}

	public JComboBox<String> getCategoryField() {
		return categoryField;
	}

	public void setCategoryField(String s) {
		this.categoryField.setSelectedItem(s);
	}

	public JComboBox<String> getDesignationField() {
		return designationField;
	}

	public void setDesignationField(String s) {
		this.designationField.setSelectedItem(s);
	}

	public JComboBox<String> getDescriptionField() {
		return descriptionField;
	}

	public void setDescriptionField(JComboBox<String> descriptionField) {
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

		// this site was helpful - https://gist.github.com/madan712/3912272

		try {

			InputStream ExcelFileToRead = new FileInputStream("churchDB.xls");
			HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

			HSSFSheet sheet=wb.getSheetAt(0);
			HSSFRow row; 
			HSSFCell cell;

			Iterator<Row> rows = sheet.rowIterator();
			row=(HSSFRow) rows.next();
			
			while (rows.hasNext())
			{	
				
				row=(HSSFRow) rows.next();
				//Iterator<Cell> cells = row.cellIterator();
				d = new Donor();
	
				cell = row.getCell(0);		
				if (cell!=null) {
					System.out.print(cell);
					env = cell.getStringCellValue().toString();
				}else
					env = "";

				cell = row.getCell(1);
				if (cell!=null) {
					//System.out.print(cell);
					ln=cell.getStringCellValue();
				}
				else
					ln="";

				cell = row.getCell(2);
				if (cell!=null) {
					//System.out.print(cell);
					fn = cell.getStringCellValue();
				}else
					fn="";

				cell = row.getCell(3);
				if (cell!=null) {
					//System.out.print(cell);
					ad = cell.getStringCellValue();
				}else
					ad="";

				cell = row.getCell(4);
				if (cell!=null) {
					//System.out.print(cell);
					ct = cell.getStringCellValue();
				}
				else
					ct="";

				cell = row.getCell(5);
				if (cell!=null) {
					//System.out.print(cell);
					st = cell.getStringCellValue();
				}
				else
					st="";

				cell = row.getCell(6);
				if (cell!=null) {
					//System.out.print(cell);
					zp = cell.getStringCellValue();
				}
				else
					zp="";

				//System.out.println();

				d.setEnvelopeNumber(env);
				d.setFirstName(fn);
				d.setLastName(ln);
				d.setAddress(ad);
				d.setCity(ct);
				d.setState(st);
				d.setZip(zp);

				churchDB.add(d);
				
			}

			ExcelFileToRead.close();
			wb.close();
		}
		catch(IOException ex) {
			System.out.println("Error reading file '");
		} 
		
	}
}
