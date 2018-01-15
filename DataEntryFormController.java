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

}
