import java.awt.BorderLayout;
import java.awt.EventQueue;
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
	
	private DataEntryFormController myDataEntryFormController;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					System.out.println("starting the main() in the Data Entry Form Controller...");
					DataEntryForm myDataEntryForm = new DataEntryForm();
					myDataEntryForm.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 */
	public DataEntryForm() {
		
		System.out.println("launching / constructing the DataEntryForm...");
		myDataEntryFormController = new DataEntryFormController(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
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
		amountLabel.setBounds(72, 230, 61, 16);
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
		
		JLabel categoryLabel = new JLabel("Category");
		categoryLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		categoryLabel.setBounds(72, 174, 61, 16);
		contentPane.add(categoryLabel);
		
		JLabel descriptionLabel = new JLabel("Description");
		descriptionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		descriptionLabel.setBounds(44, 202, 89, 16);
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
		
		categoryField = new JTextField();
		categoryField.setBounds(155, 169, 130, 26);
		contentPane.add(categoryField);
		categoryField.setColumns(10);
		
		descriptionField = new JTextField();
		descriptionField.setBounds(155, 197, 130, 26);
		contentPane.add(descriptionField);
		descriptionField.setColumns(10);
		
		amountField = new JTextField();
		amountField.setBounds(155, 225, 130, 26);
		contentPane.add(amountField);
		amountField.setColumns(10);
		
		JButton enterDataButton = new JButton("Enter Data");
		enterDataButton.setBounds(297, 141, 134, 29);
		contentPane.add(enterDataButton);
		enterDataButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("action occurred on the enterDataButton...");
				System.out.println("trying to send message to the DataEntryFormController...");
			}
		});
		
		JButton showDataButton = new JButton("Show All Entries");
		showDataButton.setBounds(297, 169, 134, 29);
		contentPane.add(showDataButton);
	}
	
}
