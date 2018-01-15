/*
 * This is a runner class.  It starts the application.
 */

import javax.swing.SwingUtilities;

public class ChurchOfferingRunner {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					System.out.println("starting the main() in the Data Entry Form Controller...");
					createAndShowGUI();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private static void createAndShowGUI() throws Exception{
		new DataEntryForm();
	}
	
}
