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
		
		
		thisWeeksOffering.getOffering().add(currentDonation);
		*/
	}

	/*
			System.out.println("action occurred on the enterDataButton...");
			Donor d = new Donor(
					lastNameField.getText(),
					firstNameField.getText(),
					envelopeField.getText(),
					addressField.getText(),
					cityField.getText(),
					stateField.getText(),
					zipField.getText());
					
			Donation currentDonation = new Donation (
					d,
					categoryField.getText(),
					descriptionField.getText(),
					Integer.parseInt(amountField.getText()));
			
			
		}	
	}
	
	*/

}
