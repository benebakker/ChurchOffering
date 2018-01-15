
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class FormController implements ActionListener {
	
	private JPanel form;
	
	private DefaultTableModel model;

	public FormController(JPanel f) {
		super();
		form = f;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("some button pressed");
	
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
