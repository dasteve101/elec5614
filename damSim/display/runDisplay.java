package display;

import javax.swing.SwingUtilities;

import physicalObjects.*;

/**
 * This class opens a GUI to display the objects
 */
public class runDisplay {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Gui(); // Let the constructor do the job
			}
		});
	}

}
