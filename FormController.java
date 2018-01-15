
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class FormController implements ActionListener {
	
	private DataEntryForm form;
	
	private DefaultTableModel model;

	public FormController(DataEntryForm f) {
		super();
		form = f;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("some button pressed" + e);
		if(e.getActionCommand().compareTo("enter-data")==0) {
			System.out.println("the enter data button was pressed");
			form.getLastNameField().setText("hello");
		}
		
	
	}
	/*
	class buttonActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
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
