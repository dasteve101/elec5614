package display;

import java.awt.*;
import java.util.List;
import java.util.Observable;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

import javax.swing.*; 

import controller.ControlRTS;

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
	private ControlRTS control;
	
	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 31428L;

	/**
	 * JPanel that displays all the dams in the scheme and the information
	 * relating to those dams.
	 * 
	 * @author christopher
	 *
	 */
	private class damMonitor extends JPanel {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 8893953223112938031L;

		private damMonitor () {
			// Get the dam List from the scheme and create an information panel for each one.
			List<Dam> schemeDams = system.getDams();
			// Create a new GridLayout for the JPanel.
			setLayout(new GridLayout(0, 3, 20, 20));
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
			// Set the alignment attributes for the JLabels.
			damName.setAlignmentX(CENTER_ALIGNMENT);
			damCapacity.setAlignmentX(CENTER_ALIGNMENT);
			damLevel.setAlignmentX(CENTER_ALIGNMENT);
			damOverflowed.setAlignmentX(CENTER_ALIGNMENT);
			// Create the JPanel to hold all the information.
			JPanel damInfo = new JPanel();
			// Set loyout attributes for the JPanel.
			damInfo.setLayout(new BoxLayout(damInfo, BoxLayout.Y_AXIS));
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

		/**
		 * 
		 */
		private static final long serialVersionUID = 7277111165113017112L;
		
	}
	
	private class abortScheme extends JPanel implements ActionListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = -6454401343894962013L;
		private JButton abortButton;
	
		private abortScheme() {
			abortButton = new JButton("ABORT!");
			
			abortButton.addActionListener(this);
			add(abortButton);

		}
	// TODO - implement run continuously and by one increment buttons.	
		// TODO - Create JPanel to manually input rain levels into each dam.
		// TODO - Create JButton to create random distribution for rain levels.
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO - complete this function.
			if (e.getSource() == abortButton) {
				System.out.println("That seemed to work.");
			} else
				System.out.println("That fucked up.");
		}
		
	}

	/**
	 * Constructor method for the class.
	 * 
	 * @param system - the Snowy Hydro Scheme that this GUI is supposed to monitor.
	 */
	public RealTimeDisplay(SnowyScheme scheme, ControlRTS control) {
		system = scheme;
		this.control = control;
		// Create the JFrame needed.
		JFrame realTimeMonitor = new JFrame("RTC Snowy Hydro");
		realTimeMonitor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Create the JPanels needed.
		damMonitor damPanel = new damMonitor();
		abortScheme abortPanel = new abortScheme();
// TODO - add equivalent methods for Rivers. connections etc.
		// Add the JPanels to the JFrame.
		realTimeMonitor.add(damPanel, BorderLayout.PAGE_START);
		realTimeMonitor.add(abortPanel, BorderLayout.PAGE_END);
		realTimeMonitor.pack();
		realTimeMonitor.setVisible(true);
	}

	

	
}
