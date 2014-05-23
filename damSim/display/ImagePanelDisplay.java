package display;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.util.*;
import java.util.List;

import physicalObjects.*;

/**
 * Class used to create an image display of the Snowy Hydro Scheme.
 * Use this to graphically display the scheme.
 * 
 * @author Christopher
 *
 */
public class ImagePanelDisplay {
	
	private static SnowyScheme observedScheme;

	/**
	 * Graphically display the Snowy Scheme using the .jpeg images contained in the package.
	 * This panel holds the Snowy Hydro Scheme diagram.
	 * 
	 * @author Christopher
	 *
	 */
	public static class ImagePanel extends JPanel {

		/**
		 *	The class member fields.
		 */
		private static final long serialVersionUID = -3613761596634571654L;

		/**
		 * Method to display the image of the Snowy Hydro scheme,
		 */
		public void displayImage() {

			
		}
		
		/* TODO - finish this function */
		/* TODO - determine how the diagram is going to be constructed */
	}
	
	/**
	 * Class used to manually alter attributes in the Snowy Hydro scheme.
	 * This class is used for testing purposes only.
	 * 
	 * @author Christopher
	 *
	 */
	public static class SimulationController extends JPanel implements ActionListener {

		/**
		 * 
		 */
		private static final long serialVersionUID = 8693432720183042365L;

		/**
		 * Constructor method.
		 * This is used to create all the buttons and controls for the Snowy Scheme
		 * type observedScheme.
		 */
		public SimulationController() {
			// TODO - determine what order buttons and sub-panels will be created locate appropriate methods.
			// Create the dam panel segment of the simulator window.
			createDamWindowSegment();
		}
		
		/**
		 * Create a JPanel that contains all the dams in the Snowy Hydro scheme.
		 * A JPanel with a collection of buttons controlling each dam.
		 * 
		 * @return
		 */
		private JPanel createDamWindowSegment() {
			JPanel damContentPane;
			// Create a JPanel of buttons for each dam in the Snowy Hydro Scheme.
			List<Dam> snowyDams = observedScheme.getDams();
			// Iterate though the dam List and create a button panel for each one.
			for (Dam dam: snowyDams) {
				damContentPane = createDamButtonPanel(dam);
				// Add the panel to the instance JPanel.
				add(damContentPane);
			}
			return null;
			// TODO Auto-generated method stub
		}

		/**
		 * Create a JPanel that monitors a single dam in the Snowy Hydro Scheme.
		 * 
		 * @return
		 */
		private JPanel createDamButtonPanel(Dam dam) {
			// Add two buttons (increment and decrement) to a dam JPanel.
			JButton damIncrement = new JButton("+ 10");
			JButton damDecrement = new JButton("- 10");
			
			
			return null;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	
	/**
	 * Getter method for the observedScheme field.
	 * 
	 * @return observedScheme - The hydro scheme that the GUI is modelling.
	 */
	public SnowyScheme getSnowyScheme() {
		return observedScheme;
	}

	/**
	 * Constructor method for the class.
	 */
	public ImagePanelDisplay(SnowyScheme scheme) {
		observedScheme = scheme;
		// Create two JFrames (windows) for the image and the simulation controls.
		JFrame image = new JFrame("Image Window");
		JFrame simulation = new JFrame("Simulation Window");
		// Create an extended JPanel instance of the image panel.
		ImagePanel imageContent = new ImagePanel();
		// Create a JPanel instance of the simulation controller panel.
		SimulationController simulationContent = new SimulationController();
		// Attach the different JPanels to their respective JFrames (windows).
		image.setContentPane(imageContent);
		simulation.setContentPane(simulationContent);
		// Make the two JFrames visible.

	}

}
