import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DataEntryFormController {
	
	private SingleWeekOffering thisWeeksOffering;
	private DataEntryForm myDataEntryForm;
	
	public DataEntryFormController(DataEntryForm d) {
		super();
		this.myDataEntryForm = d;
		this.thisWeeksOffering = new SingleWeekOffering();
	}

	public void enterDataButtonPushed() {
		System.out.println("enterDataButton pushed event detected and handed to DataEntryFormController...");
		
		/*
		Donor currentDonor = new Donor();
		currentDonor.setLastName(myDataEntryForm.getLastNameField().getText());
		currentDonor.setFirstName(myDataEntryForm.getFirstNameField().getText());
		currentDonor.setEnvelopeNumber(myDataEntryForm.getEnvelopeField().getText());
		currentDonor.setAddress(myDataEntryForm.getAddressField().getText());
		currentDonor.setCity(myDataEntryForm.getCityField().getText());
		currentDonor.setState(myDataEntryForm.getStateField().getText());
		currentDonor.setZip(myDataEntryForm.getZipField().getText());
		
		Donation currentDonation = new Donation (currentDonor,
				myDataEntryForm.getCategoryField().getText(),
				myDataEntryForm.getDescriptionField().getText(),
				Integer.parseInt(myDataEntryForm.getAmountField().getText()));
		
		thisWeeksOffering.getOffering().add(currentDonation);
		*/
	}

}
