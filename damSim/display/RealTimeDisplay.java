package display;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

import javax.swing.*;
import javax.swing.Timer;

import controller.ControlRTS;
import physicalObjects.*;

/**
 * Class used to monitor the Snowy Mountain Scheme and provide a mechanism to
 * shutdown the entire scheme if required.
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
	private int counter = 10;
	private Timer timer;
	private JFrame realTimeMonitor;
	private damMonitor damPanel;
	private Runnable doWorkRunnable;

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
		
		private Map<Dam, List<JLabel>> damLabels;

		private damMonitor() {
			// Get the dam List from the scheme and create an information panel
			// for each one.
			List<Dam> schemeDams = system.getDams();
			// Create a new GridLayout for the JPanel.
			setLayout(new GridLayout(0, 3, 20, 20));
			// Create new linked hash map.
			damLabels = new LinkedHashMap<Dam, List<JLabel>>(schemeDams.size());
			// Iterate through the Dam List and create an information panel for
			// each dam.
			for (Dam dam : schemeDams) {
				// Create arrayList of JLabels based on the dam information.
				List<JLabel> labels = createJLabels(dam);
				// Add labels and dam to the LinkedHashMap.
				damLabels.put(dam, labels);
				// Add each information panel to the main JPanel.
				add(createDamInformation(dam, labels));
			}
		}

		/**
		 * Function to create a array list of JLabels for a particular dam.
		 * @param dam
		 * @return
		 */
		private List<JLabel> createJLabels(Dam dam) {
			ArrayList<JLabel> labels = new ArrayList<JLabel>();
			// Create labels that will be used for each Dam field.
			JLabel damName = new JLabel(dam.getName());
			JLabel damCapacity = new JLabel("Capacity: "
					+ Float.toString(dam.getCapacity()));
			JLabel damLevel = new JLabel("Level: "
					+ Float.toString(dam.getLevel()));
			JLabel damOverflowed = new JLabel("Overflowed: "
					+ Boolean.toString(dam.getOverflowed()));
			// Set the alignment attributes for the JLabels.
			damName.setAlignmentX(CENTER_ALIGNMENT);
			damCapacity.setAlignmentX(CENTER_ALIGNMENT);
			damLevel.setAlignmentX(CENTER_ALIGNMENT);
			damOverflowed.setAlignmentX(CENTER_ALIGNMENT);
			// Add the JLabel elements to the Array List.
			labels.add(damName);
			labels.add(damCapacity);
			labels.add(damLevel);
			labels.add(damOverflowed);

			return labels;
		}

		/**
		 * Method to create a JPanel that provides information about a dam.
		 * 
		 * @param dam
		 * @return
		 */
		private JPanel createDamInformation(Dam dam, List<JLabel> labels) {
			// Create the JPanel to hold all the information.
			JPanel damInfo = new JPanel();
			// Set layout attributes for the JPanel.
			damInfo.setLayout(new BoxLayout(damInfo, BoxLayout.Y_AXIS));
			// Attach the JLabels to the JPanel.
			for (JLabel label : labels) {
				damInfo.add(label);
			}

			return damInfo;
		}
		
		/**
		 * Function that updates the JLabels in the dam monitor panel.
		 */
		 public void updateTextLabels() {
			 // Create place holders for the dam and JLabels list.
			 Dam dam;
			 List<JLabel> labels;
			 // Cycle though all the dams and update their respective JLabels.
			 for (Map.Entry<Dam, List<JLabel>> entry : damLabels.entrySet()) {
				 dam = entry.getKey();
				 labels = entry.getValue();
				 labels.get(0).setText(dam.getName());
				 labels.get(1).setText("Capacity: "
							+ Float.toString(dam.getCapacity()));
				labels.get(2).setText("Level: "
							+ Float.toString(dam.getLevel()));
				labels.get(3).setText("Overflowed: "
							+ Boolean.toString(dam.getOverflowed()));
			 }
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
		private JButton startButton;
		private JButton abortButton;

		private abortScheme() {
			startButton = new JButton("Start");
			abortButton = new JButton("ABORT!");

			startButton.addActionListener(this);
			abortButton.addActionListener(this);
			add(abortButton);
			add(startButton);

		}

		// TODO - implement run continuously and by one increment buttons.
		// TODO - Create JPanel to manually input rain levels into each dam.
		// TODO - Create JButton to create random distribution for rain levels.

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO - complete this function.
			if (e.getSource() == startButton) {
				Thread simulation = new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							while (true) {
								system.increment(100);
								realTimeMonitor.remove(damPanel);
								
								damPanel = new damMonitor();
								realTimeMonitor.add(damPanel, BorderLayout.PAGE_START);
								realTimeMonitor.revalidate();
								Thread.sleep(1000);
							}
						} catch (IncorrectLengthException | InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				simulation.start();
			} else if (e.getSource() == abortButton) {
				System.out.println("That seemed to work.");
			} else {
				System.out.println("That fucked up.");
			}
		}

	}

	/**
	 * Constructor method for the class.
	 * 
	 * @param system - the Snowy Hydro Scheme that this GUI is supposed to
	 *            monitor.
	 */
	public RealTimeDisplay(SnowyScheme scheme, ControlRTS control) {
		system = scheme;
		this.control = control;
		// Create the JFrame needed.
		realTimeMonitor = new JFrame("RTC Snowy Hydro");
		realTimeMonitor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Create the JPanels needed.
		damPanel = new damMonitor();
		abortScheme abortPanel = new abortScheme();
		// TODO - add equivalent methods for Rivers. connections etc.
		// Add the JPanels to the JFrame.
		realTimeMonitor.add(damPanel, BorderLayout.PAGE_START);
		realTimeMonitor.add(abortPanel, BorderLayout.PAGE_END);
		realTimeMonitor.pack();
		realTimeMonitor.setVisible(true);

		// Refresh the monitors every second.
		// FIXME - need a method that is more efficient.
		doWorkRunnable = new Runnable() {
		    public void run() {
		    	damPanel.updateTextLabels();
		    	damPanel.revalidate();
		    	damPanel.repaint();
		    }
		};
		SwingUtilities.invokeLater(doWorkRunnable);
		
		timer = new Timer(1000, new TimerListener());
		timer.start();	
		//the actionPerformed method in this class
	    //is called each time the Timer "goes off
		
	}

    private class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
        	timer.stop();
        	SwingUtilities.invokeLater(doWorkRunnable);
        	timer.start();
        }
    }

}
