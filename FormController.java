
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class FormController implements ActionListener {
	
	private DataEntryForm form;
	private static ArrayList<Donation> offering = new ArrayList<Donation>();

	public FormController(DataEntryForm f) {
		super();
		form = f;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("event occured - " + e.getActionCommand());
		if(e.getActionCommand().compareTo("enter-data")==0) {
			Donation d = getFormData();
			addDonationToOffering(d);
			resetForm();
		}
		if(e.getActionCommand().compareTo("show-data")==0) {
			System.out.println("in the show-data event block...");
			showAllEntries();
		}
		if(e.getActionCommand().compareTo("lastname-event")==0) {
			String s = (String)form.getLastNameField().getSelectedItem();
			if(checkForUniqueLastName(s))
				fillInDataUsingLastName(s);
			else	
				updateLastNameComboBox(s);
		}
		if(e.getActionCommand().compareTo("envelope-event")==0) {
			String en = (String)form.getEnvelopeField().getText();
			System.out.println(en);
			boolean envelopeExists=false;
			
			System.out.println(form.getChurchDB().size());
			
			for(Donor d1: form.getChurchDB()) {
				
				System.out.println(d1.getEnvelopeNumber());
				if(d1.getEnvelopeNumber()!=null) 
					if (d1.getEnvelopeNumber().compareTo(en)==0)
						envelopeExists=true;
			}
			if(envelopeExists) {
				fillInDataUsingEnvelopeNumber(en);
			}
			else {
				//default title and icon
				form.alertMessage("Envelope number does not exist.");
			}	
		}
	}
	
	public void addDonationToOffering(Donation d) {
		
		offering.add(d);
		System.out.println("the number of donations is " + offering.size());
		
	}
	
	private void resetForm() {
		form.getLastNameField().setSelectedIndex(1);
		form.getFirstNameField().setText("");
		form.getEnvelopeField().setText("");
		form.getAddressField().setText("");
		form.getCityField().setText("");
		form.getZipField().setText("");
		form.getStateField().setSelectedIndex(0);
		form.getCategoryField().setSelectedIndex(0);
		form.getDesignationField().setSelectedIndex(0);
		form.getDescriptionField().setText("");
		form.getAmountField().setText("");
	}
	
	public String nullToEmptyString(String s) {
		if (s==null)
			return "";
		else 
			return s;
	}
	
	public Integer valueOf(String s) {
		if (s==null  || s.compareTo("")==0)
			return 0;
		else 
			return Integer.parseInt(s);
	}
	
	public Donation getFormData() {

		Donor d = new Donor(
				nullToEmptyString((String)form.getLastNameField().getSelectedItem()),
				nullToEmptyString(form.getFirstNameField().getText()),
				nullToEmptyString(form.getEnvelopeField().getText()),
				nullToEmptyString(form.getAddressField().getText()),
				nullToEmptyString(form.getCityField().getText()),
				nullToEmptyString((String)form.getStateField().getSelectedItem()),
				nullToEmptyString(form.getZipField().getText())
				);
		
		Donation s = new Donation(
				d,
				nullToEmptyString((String)form.getCategoryField().getSelectedItem()),
				nullToEmptyString((String)form.getDesignationField().getSelectedItem()),
				nullToEmptyString(form.getDescriptionField().getText()),
				valueOf(form.getAmountField().getText())
				);
	
		return s;
	}
	
	private void fillInDataUsingLastName(String s) {
		for(Donor d: form.getChurchDB()) {
			if (d.getLastName().compareToIgnoreCase(s)==0) {
				form.setFirstNameField(d.getFirstName());
				form.setEnvelopeField(d.getEnvelopeNumber());
				form.setAddressField(d.getAddress());
				form.setCityField(d.getCity());
				form.setStateField(d.getState());
				form.setZipField(d.getZip());
				return;
			}
		}
			
	}

	private void fillInDataUsingEnvelopeNumber(String s) {
		for(Donor d: form.getChurchDB()) {
			if (d.getEnvelopeNumber().compareToIgnoreCase(s)==0) {
				form.setFirstNameField(d.getFirstName());
				form.getLastNameField().setSelectedItem(d.getLastName());
				form.setAddressField(d.getAddress());
				form.setCityField(d.getCity());
				form.setStateField(d.getState());
				form.setZipField(d.getZip());
				return;
			}
		}
			
	}
	
	private boolean checkForUniqueLastName(String s) {
		int matches=0;
		for(Donor d: form.getChurchDB()) {
			if(d.getLastName().compareToIgnoreCase(s)==0) {
				matches++;
			}
		}
		return (matches==1);
	}
	
	private void updateLastNameComboBox(String s) {
		form.getLastNameField().removeAllItems();
		form.getLastNameField().addItem(s);
		for(Donor d: form.getChurchDB()) {
			if(d.getLastName().length()>=s.length()) {
				if (d.getLastName().substring(0, s.length()).compareToIgnoreCase(s)==0){
					form.getLastNameField().addItem(d.getLastName());
				}
			}
		}	
	}

	private void showAllEntries(){
		
		System.out.println("in the show all entries block...number of entries in offering is " + offering.size());
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Object columnNames[] = { "Last Name", "First Name", "Envelope Number", "Amount",
				"Designation","Category","Description","Address","City","State","Zip"};

		Object rowData[][] = new Object [offering.size()][columnNames.length];

		int i=0;
		for(Donation d: offering) {
			System.out.println("adding item "+i);
			rowData[i][0] = d.getDonor().getLastName();
			rowData[i][1] = d.getDonor().getFirstName();
			rowData[i][2] = d.getDonor().getEnvelopeNumber();
			rowData[i][3] = d.getAmount();
			rowData[i][4] = d.getDesignation();
			rowData[i][5] = d.getCategory();
			rowData[i][6] = d.getDescription();
			rowData[i][7] = d.getDonor().getAddress();
			rowData[i][8] = d.getDonor().getCity();
			rowData[i][9] = d.getDonor().getState();
			rowData[i][10] = d.getDonor().getZip();

			i++;
		}

		JTable table = new JTable(rowData, columnNames);

		//table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
	
		System.out.println("preferred width is " + table.getColumnModel().getColumn(1).getPreferredWidth());
		System.out.println("max width is " + table.getColumnModel().getColumn(1).getMaxWidth());
		System.out.println("min width is " + table.getColumnModel().getColumn(1).getMinWidth());
		
		//table.getColumnModel().getColumn(1).setMinWidth(10);
		//table.getColumnModel().getColumn(1).setMaxWidth(10);
		//table.getColumnModel().getColumn(1).setPreferredWidth(10);
		
		JScrollPane scrollPane = new JScrollPane(table);
		frame.add(scrollPane, BorderLayout.CENTER);
		frame.setSize(1200, 400);
		
		frame.setVisible(true);
	}
}
