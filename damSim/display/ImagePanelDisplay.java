package display;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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
// TODO - Create Panel for changing the power and water demand for the entire scheme.
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
			// Create the dam panel segment of the simulator window.
			// Get the Dam List from the observed scheme.
			List<Dam> snowyDams = observedScheme.getDams();
			// Iterate though the dam List and create a button panel for each one.
			for (Dam dam: snowyDams) {
				// Add the panel to the instance JPanel.
				add(createDamButtonPanel(dam));
			}
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
			JPanel buttonContainer = new JPanel(new BorderLayout());
			JLabel damName = new JLabel(dam.getName());

			// Add ActionListeners to the buttons.
			damIncrement.addActionListener(this);
			damDecrement.addActionListener(this);
			// Add client properties to the buttons to identify them.
			damIncrement.putClientProperty("dam_object", dam);
			damIncrement.putClientProperty("operation", "Increment by 10");
			// Add all the components to the JPanel container.
			buttonContainer.add(damName, BorderLayout.NORTH);
			buttonContainer.add(damIncrement, BorderLayout.EAST);
			buttonContainer.add(damDecrement, BorderLayout.WEST);

			return buttonContainer;
		}

		/**
		 * Event-handler for all the JButtons.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			Dam dam;
			// FIXME - assume, for the mean time, that all action events are going to be JButtons.
			JButton buttonEvent = (JButton)e.getSource();
			// TODO - Create MACRO strings for these operation key-value pairs.
			if (buttonEvent.getClientProperty("operation") == "Increment by 10") {
				// Increment a dam by 10 via the SnowtScheme class.
				List<Float> rain = makeZeroes(14);
				rain.add(10.0f);
				try {
					observedScheme.increment(rain, makeZeroes(15), makeZeroes(15), makeZeroes(15), 500);
					
				} catch (IncorrectLengthException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				System.out.println("Awkwards.");
			}
			
		}
		
		static List<Float> makeZeroes(int count) {
			List<Float> zeroes = new ArrayList<Float>();
			for (int i = 0; i < count; i++) {
				zeroes.add(0.0f);
			}
			return zeroes;
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
		// End the thread with the close button is pressed.
		simulation.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Create an extended JPanel instance of the image panel.
		ImagePanel imageContent = new ImagePanel();
		// Create a JPanel instance of the simulation controller panel.
		SimulationController simulationContent = new SimulationController();
		// Attach the different JPanels to their respective JFrames (windows).
		image.setContentPane(imageContent);
		simulation.getContentPane().add(simulationContent);
		// Adjust the size of the frame.
		simulation.pack();
		// Make the two JFrames visible.
		simulation.setVisible(true);

	}
}
