
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class FormController implements ActionListener {
	
	private JTextField textField = new JTextField();
	private DefaultTableModel model;

	public FormController(JTextField t) {
		super();
		textField = t;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	
	}

}
