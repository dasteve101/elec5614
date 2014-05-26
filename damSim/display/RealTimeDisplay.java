package display;

import java.awt.*;
import java.util.List;
import java.awt.event.*;
import java.util.*;

import javax.swing.*; 

import physicalObjects.*;

/**
 * Class used to monitor the Snowy Mountain Scheme and provide a mechanism 
 * to shutdown the entire scheme if required.
 * 
 * @author christopher
 *
 */
public class RealTimeDisplay {

	/**
	 * Member fields.
	 */
	private static SnowyScheme system;

	/**
	 * 
	 */
	private static final long serialVersionUID = 31428L;

	/**
	 * JPanel that displays all the dams in the scheme and the information
	 * relating to those dams.
	 * 
	 * @author christopher
	 *
	 */
	private class damMonitor extends JPanel {
		
		private damMonitor () {
			// Get the dam List from the scheme and create an information panel for each one.
			List<Dam> schemeDams = system.getDams();
			// Iterate through the Dam List and create an information panel for each dam.
			for (Dam dam: schemeDams) {
				// Add each information panel to the main JPanel.
				add(createDamInformation(dam));
			}
		}
		
		/**
		 * Method to create a JPanel that provides information about a dam.
		 * 
		 * @param dam
		 * @return
		 */
		private JPanel createDamInformation(Dam dam) {
			// Create labels that will be used for each Dam field.
			JLabel damName = new JLabel(dam.getName());
			JLabel damCapacity = new JLabel("Capacity: " + Float.toString(dam.getCapacity()));
			JLabel damLevel = new JLabel("Level: " + Float.toString(dam.getLevel()));
			JLabel damOverflowed = new JLabel("Overflowed: " + Boolean.toString(dam.getOverflowed()));
			// Create the JPanel to hold all the information.
			JPanel damInfo = new JPanel();
			// Attach the JLabels to the JPanel.
			damInfo.add(damName);
			damInfo.add(damCapacity);
			damInfo.add(damLevel);
			damInfo.add(damOverflowed);
			
			return damInfo;
		}
	}
	
	/**
	 * 
	 * @author christoper
	 *
	 */
	private class riverMonitor extends JPanel {
		
	}

	/**
	 * Constructor method for the class.
	 * 
	 * @param system - the Snowy Hydro Scheme that this GUI is supposed to monitor.
	 */
	public RealTimeDisplay(SnowyScheme scheme) {
		system = scheme;
		// Create the JFrame needed.
		JFrame realTimeMonitor = new JFrame("RTC Snowy Hydro");
		realTimeMonitor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Create the JPanels needed.
		damMonitor damPanel = new damMonitor();
// TODO - add equivalent methods for Rivers. connections etc.
		// Add the JPanels to the JFrame.
		realTimeMonitor.add(damPanel);
		realTimeMonitor.pack();
		realTimeMonitor.setVisible(true);
	}
}
