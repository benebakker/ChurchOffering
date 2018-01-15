
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;

public class DataEntryForm extends JFrame {

	private JPanel contentPane;
	private JTextField lastNameField;
	private JTextField firstNameField;
	private JTextField envelopeField;
	private JTextField addressField;
	private JTextField cityField;
	private JTextField stateField;
	private JTextField categoryField;
	private JTextField descriptionField;
	private JTextField amountField;
	private JTextField zipField;

	/**
	 * Create the frame.  The data entry form constructor.
	 */
	public DataEntryForm() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
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
		amountLabel.setBounds(72, 258, 61, 16);
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
		
		JLabel descriptionLabel = new JLabel("Description");
		descriptionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		descriptionLabel.setBounds(44, 230, 89, 16);
		contentPane.add(descriptionLabel);
		
		lastNameField = new JTextField();
		lastNameField.setBounds(155, 1, 130, 26);
		contentPane.add(lastNameField);
		lastNameField.setColumns(10);
		
		firstNameField = new JTextField();
		firstNameField.setBounds(155, 29, 130, 26);
		contentPane.add(firstNameField);
		firstNameField.setColumns(10);
		
		envelopeField = new JTextField();
		envelopeField.setBounds(155, 57, 130, 26);
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
		
		stateField = new JTextField();
		stateField.setBounds(155, 141, 130, 26);
		contentPane.add(stateField);
		stateField.setColumns(10);
		
		zipField = new JTextField();
		zipField.setBounds(155, 169, 130, 26);
		contentPane.add(zipField);
		zipField.setColumns(10);
		
		categoryField = new JTextField();
		categoryField.setBounds(155, 197, 130, 26);
		contentPane.add(categoryField);
		categoryField.setColumns(10);
		
		descriptionField = new JTextField();
		descriptionField.setBounds(155, 225, 130, 26);
		contentPane.add(descriptionField);
		descriptionField.setColumns(10);
		
		amountField = new JTextField();
		amountField.setBounds(155, 251, 130, 26);
		contentPane.add(amountField);
		amountField.setColumns(10);
		
		FormController enterButtonDataController = new FormController(lastNameField);
		JButton enterDataButton = new JButton();
		enterDataButton.setText("Enter Data");
		enterDataButton.setBounds(297, 141, 134, 29);
		enterDataButton.addActionListener(enterButtonDataController);
		contentPane.add(enterDataButton);
		
		JButton showDataButton = new JButton();
		showDataButton.setText("Show All Entries");
		showDataButton.setBounds(297, 169, 134, 29);
		contentPane.add(showDataButton);
		
		setVisible(true);
	}
	
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
}
