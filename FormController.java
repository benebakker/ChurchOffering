
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FormController implements ActionListener {
	
	private DataEntryForm form;
	private ArrayList<Donor> churchDB;
	private ArrayList<Donation> offering = new ArrayList<Donation>();

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
	
	private void getChurchData() {

		churchDB = new ArrayList<Donor>();
		
		Donor p;
		String fn;
		String ln;
		String env;
		String address;
		String town;
		String state;
		String zip;
		
		// The name of the file to open.
	    String churchDBfile = "churchDB1.csv";

	    // This will reference one line at a time
	    String line = null;

	    try {
	    		// FileReader reads text files in the default encoding.
	    		FileReader fileReader = new FileReader(churchDBfile);

	        // Always wrap FileReader in BufferedReader.
	        BufferedReader bufferedReader = new BufferedReader(fileReader);

	        while((line = bufferedReader.readLine()) != null) {
	        		p = new Donor();
	        		//System.out.println(line);
	        		int commaPlace = line.indexOf(',');
	        		
	        		try {
	        			env = line.substring(0, commaPlace);
	        		}
	        		catch (Exception e){
	        			env="0";
	        			System.out.println(env);
	        		}
	        		
	        		int nextCommaPlace = line.indexOf(',', commaPlace+1);
	        		ln = line.substring(commaPlace+1, nextCommaPlace);
	        		fn = line.substring(nextCommaPlace+1);
	        		
	        		p.setEnvelopeNumber(env);
	        		p.setFirstName(fn);
	        		p.setLastName(ln);
	        		
	        		churchDB.add(p);       		
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
