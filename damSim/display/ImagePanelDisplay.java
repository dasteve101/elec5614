package display;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import controller.ControlRTS;

import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

import physicalObjects.*;

/**
 * Class used to create an image display of the Snowy Hydro Scheme. Use this to
 * graphically display the scheme.
 * 
 * @author Christopher
 * 
 */
public class ImagePanelDisplay {

	private static SnowyScheme observedScheme;
	private static ControlRTS control;

	/**
	 * Graphically display the Snowy Scheme using the .jpeg images contained in
	 * the package. This panel holds the Snowy Hydro Scheme diagram.
	 * 
	 * @author Christopher
	 * 
	 */
	public static class ImagePanel extends JPanel {

		/**
		 * The class member fields.
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
	 * Class used to manually alter attributes in the Snowy Hydro scheme. This
	 * class is used for testing purposes only.
	 * 
	 * @author Christopher
	 * 
	 */
	public static class SimulationController extends JPanel implements ActionListener {
		
		private static final String TEXT_FIELD_OBJECT = "text field object";
		private static final String DAM_OBJECT = "dam object";
		private static final String ACTION = "action";
		private static final long serialVersionUID = 8693432720183042365L;

		private JButton waterDemandButton;
		private JButton powerDemandButton;
		private JButton damRainLevelButton;
		private JTextField demandChange;
		private JButton randomButton;
		private JButton defaultButton;
		private JButton incrementButton;
		private JButton startButton;
		private ArrayList<JTextField> rainLevels = new ArrayList<JTextField>();
		private float powerDemand;
		private float waterDemand;

			// TODO - include randomisation option.
			// TODO - include 'default' setting.
		// TODO - include 'auto increment' button.

		/**
		 * Constructor method. This is used to create all the buttons and
		 * controls for the Snowy Scheme type observedScheme.
		 */
		public SimulationController() {
			// Create the dam panel segment of the simulator window.
			// Get the Dam List from the observed scheme.
			List<Dam> snowyDams = observedScheme.getDams();
			// Create a new GridLayout for the JPanel.
			setLayout(new GridLayout(0,3, 20, 20));
			// Iterate though the dam List and create a button panel for each
			// one.
			for (Dam dam : snowyDams) {
				// Add the panel to the instance JPanel.
				add(createDamButtonPanel(dam));
			}
			
			// Create JPanel to change the water and power demand of the entire
			// scheme.
			add(createDemandController());
		}

		/**
		 * Method to create a JPanel that allows the user to change the water
		 * and power demand of the entire scheme.
		 * 
		 * @return
		 */
		private JPanel createDemandController() {
			// Create the necessary components.
			JPanel demandPanel = new JPanel();		// Use the GridBagLayout manager for this.
			JLabel label = new JLabel("Demand Control");
			waterDemandButton = new JButton("Change Water Demand");
			powerDemandButton = new JButton("Change Power Demand");
			damRainLevelButton = new JButton("Set rain level (increment)");
			incrementButton = new JButton("Increment");
			startButton = new JButton("Start");
			demandChange = new JTextField("Enter float value here");
			// Add action listeners to the components.
			waterDemandButton.addActionListener(this);
			powerDemandButton.addActionListener(this);
			damRainLevelButton.addActionListener(this);
			incrementButton.addActionListener(this);
			startButton.addActionListener(this);
			// Add components to the JPanel.
			demandPanel.add(powerDemandButton, BorderLayout.WEST);
			demandPanel.add(waterDemandButton, BorderLayout.EAST);
			demandPanel.add(damRainLevelButton, BorderLayout.SOUTH);
			demandPanel.add(demandChange, BorderLayout.NORTH);
			demandPanel.add(label, BorderLayout.PAGE_START);
			demandPanel.add(incrementButton, BorderLayout.PAGE_END);
			demandPanel.add(startButton, BorderLayout.PAGE_END);
// TODO - fix border layout.
			return demandPanel;
		}

		/**
		 * Create a JPanel that monitors a single dam in the Snowy Hydro Scheme.
		 * 
		 * @return
		 */
		private JPanel createDamButtonPanel(Dam dam) {
			// Add components to the JPanel.
			JPanel buttonContainer = new JPanel(new BorderLayout());
			JLabel damName = new JLabel(dam.getName());
			JTextField rainLevel = new JTextField("0");
			// Add all the components to the JPanel container.
			buttonContainer.add(damName, BorderLayout.NORTH);
			buttonContainer.add(rainLevel, BorderLayout.SOUTH);
			buttonContainer.setSize(150, 100);
			// Add JTextField to an ArrayList.
			rainLevels.add(rainLevel);
// TODO - fix borderlayouts.
			return buttonContainer;
		}

		/**
		 * Function to test if the user input is a valid float value.
		 * 
		 * @param value
		 * @return
		 */
		private boolean validUserInput(String value) {
			final String Digits = "(\\p{Digit}+)";
			final String HexDigits = "(\\p{XDigit}+)";
			// an exponent is 'e' or 'E' followed by an optionally
			// signed decimal integer.
			final String Exp = "[eE][+-]?" + Digits;
			final String fpRegex = ("[\\x00-\\x20]*" + // Optional leading
														// "whitespace"
					"[+-]?(" + // Optional sign character
					"NaN|" + // "NaN" string
					"Infinity|" + // "Infinity" string

					// A decimal floating-point string representing a finite
					// positive
					// number without a leading sign has at most five basic
					// pieces:
					// Digits . Digits ExponentPart FloatTypeSuffix
					//
					// Since this method allows integer-only strings as input
					// in addition to strings of floating-point literals, the
					// two sub-patterns below are simplifications of the grammar
					// productions from the Java Language Specification, 2nd
					// edition, section 3.10.2.

					// Digits ._opt Digits_opt ExponentPart_opt
					// FloatTypeSuffix_opt
					"(((" + Digits + "(\\.)?(" + Digits + "?)(" + Exp + ")?)|" +

			// . Digits ExponentPart_opt FloatTypeSuffix_opt
					"(\\.(" + Digits + ")(" + Exp + ")?)|" +

					// Hexadecimal strings
					"((" +
					// 0[xX] HexDigits ._opt BinaryExponent FloatTypeSuffix_opt
					"(0[xX]" + HexDigits + "(\\.)?)|" +

					// 0[xX] HexDigits_opt . HexDigits BinaryExponent
					// FloatTypeSuffix_opt
					"(0[xX]" + HexDigits + "?(\\.)" + HexDigits + ")" +

					")[pP][+-]?" + Digits + "))" + "[fFdD]?))" + "[\\x00-\\x20]*");// Optional
																					// trailing
																					// "whitespace"

			return Pattern.matches(fpRegex, value);
		}

		/**
		 * Function that increments the observed hydro scheme.
		 */
		private void incrementTheHydroScheme() {
			ArrayList<Float> rainForDams = getTextFieldRainValues();
			// Debugging print functions.
			System.out.println("rainForDams List size:" + rainForDams.size());
			System.out.println("Dams List size:" + observedScheme.getDams().size());
			// Set the rain fall values in the scheme.
			try {
				observedScheme.setRainForDams(rainForDams);
			} catch (IncorrectLengthException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// TODO - determine how to use previous values. Take into account variables being used for the first time.
			// Increment the Hydro Scheme.
			try {
				observedScheme.increment(powerDemand, waterDemand);
			} catch (IncorrectLengthException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println("Good work dumbass!");
			}
		}

		/**
		 * Function that gets all the values from the JTextFields for rain levels.
		 * 
		 * @return
		 */
		private ArrayList<Float> getTextFieldRainValues() {
			ArrayList<Float> rainForDams = new ArrayList<Float>();
			for (JTextField rainLevel : rainLevels) {
				// Get the float value from the JTextField.
				float floatValue = 0;
				if (validUserInput(rainLevel.getText())) {
					// TODO - check for negative values.
					floatValue = Float.parseFloat(rainLevel.getText());
				} else {
					// ERROR
					System.out.println("\nInvalid Float Input value: assume value to be 0");
				}
				// Add the float value to the Float List.
				if (rainForDams.add(new Float(floatValue)))
					System.out.println("Good");
				else
					System.out.println("Get outta here!");
			}
			return rainForDams;
		}
		
		/**
		 * Event-handler for all the JButtons.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// FIXME - assume, for the mean time, that all action events are going to be JButtons.
			JButton buttonEvent = (JButton) e.getSource();
			// Check which action event was triggered.`
			if (buttonEvent == powerDemandButton) {
				String value = demandChange.getText();
				// Validate the input.
				if (validUserInput(value)) {
					// TODO - check for negative values.
					// Get the float value from the string.
					float floatValue = Float.parseFloat(value);
					powerDemand = floatValue;
					control.setPowerDemand(powerDemand);
					// Use the float value to change power demand.
					int numOfDams = observedScheme.getDams().size();
					System.out.println(value);
					// TODO - use the increment function here.

				} else {
					System.out.println("Fail buddy!");
					// Non-valid input.
				}
			} else if (buttonEvent == damRainLevelButton) {
				incrementTheHydroScheme();
			} else if (buttonEvent == waterDemandButton) {
				// TODO - determine whether this is ever going to be implemented or not.
				String value = demandChange.getText();
				float floatValue = Float.parseFloat(value);
				waterDemand = floatValue;
				control.setWaterDemand(waterDemand);
				System.out.println("Waterboarding!");
			} else if (buttonEvent == incrementButton) {
				// FIXME - make these common instructions more modular.
				// FIXME - Test variable.
				incrementTheHydroScheme();
			} else if (e.getSource() == startButton) {
				Thread simulation = new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							while (true) {
								incrementTheHydroScheme();
								Thread.sleep(1000);
							}
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				simulation.start();
			} else {
				System.out.println("Awkwards.");
			}
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
		// Create two JFrames (windows) for the image and the simulation
		// controls.
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
		
		control = null;
		try {
			// Start a new thread with the controller
			control = new ControlRTS(observedScheme);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		System.out.println("Controller initailized");

	}
}
