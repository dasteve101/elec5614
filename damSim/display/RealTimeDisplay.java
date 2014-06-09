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
import javax.swing.BorderFactory;
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
	private int counter = 10;
	private Timer timer;
	private JFrame realTimeMonitor;
	private damMonitor damPanel;
	private Runnable doWorkRunnable;
	private riverMonitor riverPanel;
	private pipeMonitor pipePanel;

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
			if(dam.getMaxWaterForPower() > 0)
				damName.setForeground(Color.RED);
			damName.setFont(new Font("Tahoma", Font.BOLD, 16));
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
		
		private Map<River, List<JLabel>> riverLabels;

		private riverMonitor() {
			// Get the dam List from the scheme and create an information panel
			// for each one.
			List<River> schemeRivers = system.getRivers();
			// Create a new GridLayout for the JPanel.
			setLayout(new GridLayout(0, 3, 20, 20));
			// Create new linked hash map.
			riverLabels = new LinkedHashMap<River, List<JLabel>>(schemeRivers.size());
			// Iterate through the Dam List and create an information panel for
			// each dam.
			for (River river: schemeRivers) {
				// Create arrayList of JLabels based on the dam information.
				List<JLabel> labels = createJLabels(river);
				// Add labels and dam to the LinkedHashMap.
				riverLabels.put(river, labels);
				// Add each information panel to the main JPanel.
				add(createRiverInformation(river, labels));
			}
		}

		/**
		 * Function to create a array list of JLabels for a particular dam.
		 * @param dam
		 * @return
		 */
		private List<JLabel> createJLabels(River river) {
			ArrayList<JLabel> labels = new ArrayList<JLabel>();
			// Create labels that will be used for each Dam field.
			JLabel riverName = new JLabel(river.getName());
			riverName.setFont(new Font("Tahoma", Font.BOLD, 16));
			riverName.setForeground(Color.BLUE);
			JLabel riverFlow = new JLabel("Flow: "
					+ Float.toString(river.getFlow()));
			JLabel riverMax = new JLabel("Max: "
					+ Float.toString(river.getMax()));
			JLabel riverMin = new JLabel("Min: "
					+ Float.toString(river.getMin()));
			JLabel riverLength = new JLabel("Length: "
					+ Float.toString(river.getLength()));
			// Set the alignment attributes for the JLabels.
			riverName.setAlignmentX(CENTER_ALIGNMENT);
			riverFlow.setAlignmentX(CENTER_ALIGNMENT);
			riverMax.setAlignmentX(CENTER_ALIGNMENT);
			riverMin.setAlignmentX(CENTER_ALIGNMENT);
			riverLength.setAlignmentX(CENTER_ALIGNMENT);
			// Add the JLabel elements to the Array List.
			labels.add(riverName);
			labels.add(riverFlow);
			labels.add(riverMax);
			labels.add(riverMin);
			labels.add(riverLength);

			return labels;
		}

		/**
		 * Method to create a JPanel that provides information about a dam.
		 * 
		 * @param dam
		 * @return
		 */
		private JPanel createRiverInformation(River river, List<JLabel> labels) {
			// Create the JPanel to hold all the information.
			JPanel riverInfo = new JPanel();
			// Set layout attributes for the JPanel.
			riverInfo.setLayout(new BoxLayout(riverInfo, BoxLayout.Y_AXIS));
			// Attach the JLabels to the JPanel.
			for (JLabel label : labels) {
				riverInfo.add(label);
			}

			return riverInfo;
		}
		
		/**
		 * Function that updates the JLabels in the dam monitor panel.
		 */
		 public void updateTextLabels() {
			 // Create place holders for the dam and JLabels list.
			 River river;
			 List<JLabel> labels;
			 // Cycle though all the dams and update their respective JLabels.
			 for (Map.Entry<River, List<JLabel>> entry : riverLabels.entrySet()) {
				 river = entry.getKey();
				 labels = entry.getValue();
				 labels.get(0).setText(river.getName());
				 labels.get(1).setText("Flow: "
							+ Float.toString(river.getFlow()));
				 labels.get(2).setText("Max: "
							+ Float.toString(river.getMax()));
				 labels.get(3).setText("Min: "
							+ Float.toString(river.getMin()));
				 labels.get(4).setText("Length: "
							+ Float.toString(river.getLength()));
			 }
		 }
	}
	
	/**
	 * 
	 * @author christopher
	 *
	 */
	private class pipeMonitor extends JPanel {
		
		private Map<Pipe, List<JLabel>> pipeLabels;

		private pipeMonitor() {
			// Get the dam List from the scheme and create an information panel
			// for each one.
			List<Pipe> schemePipes = system.getPipes();
			// Create a new GridLayout for the JPanel.
			setLayout(new GridLayout(0, 1, 20, 20));
			// Create new linked hash map.
			pipeLabels = new LinkedHashMap<Pipe, List<JLabel>>(schemePipes.size());
			// Iterate through the Dam List and create an information panel for
			// each dam.
			for (Pipe pipe: schemePipes) {
				// Create arrayList of JLabels based on the dam information.
				List<JLabel> labels = createJLabels(pipe);
				// Add labels and dam to the LinkedHashMap.
				pipeLabels.put(pipe, labels);
				// Add each information panel to the main JPanel.
				add(createPipeInformation(pipe, labels));
			}
		}

		/**
		 * Function to create a array list of JLabels for a particular dam.
		 * @param dam
		 * @return
		 */
		private List<JLabel> createJLabels(Pipe pipe) {
			ArrayList<JLabel> labels = new ArrayList<JLabel>();
			// Create labels that will be used for each Dam field.
			JLabel pipeName = new JLabel(pipe.getName());
			pipeName.setForeground(Color.GREEN);
			pipeName.setFont(new Font("Tahoma", Font.BOLD, 16));
			JLabel pipePower = new JLabel("Max Power: "
					+ Float.toString(pipe.getMaxPower()));
			JLabel pipeMaxWater = new JLabel("Max Water: "
					+ Float.toString(pipe.getMaxWater()));
			JLabel pipeUphill = new JLabel("Uphill: "
					+ (pipe.getUphill().getName()));
			JLabel pipeDownhill = new JLabel("Downhill: "
					+ (pipe.getDownhill().getName()));
			JLabel pipeCoeff = new JLabel("Coefficient: "
					+ Float.toString(pipe.getCoeff()));	
			// Set the alignment attributes for the JLabels.
			pipeName.setAlignmentX(CENTER_ALIGNMENT);
			pipePower.setAlignmentX(CENTER_ALIGNMENT);
			pipeMaxWater.setAlignmentX(CENTER_ALIGNMENT);
			pipeUphill.setAlignmentX(CENTER_ALIGNMENT);
			pipeDownhill.setAlignmentX(CENTER_ALIGNMENT);
			pipeCoeff.setAlignmentX(CENTER_ALIGNMENT);
			// Add the JLabel elements to the Array List.
			labels.add(pipeName);
			labels.add(pipePower);
			labels.add(pipeMaxWater);
			labels.add(pipeUphill);
			labels.add(pipeDownhill);
			labels.add(pipeCoeff);

			return labels;
		}

		/**
		 * Method to create a JPanel that provides information about a dam.
		 * 
		 * @param dam
		 * @return
		 */
		private JPanel createPipeInformation(Pipe pipe, List<JLabel> labels) {
			// Create the JPanel to hold all the information.
			JPanel pipeInfo = new JPanel();
			// Set layout attributes for the JPanel.
			pipeInfo.setLayout(new BoxLayout(pipeInfo, BoxLayout.Y_AXIS));
			// Attach the JLabels to the JPanel.
			for (JLabel label : labels) {
				pipeInfo.add(label);
			}

			return pipeInfo;
		}
		
		/**
		 * Function that updates the JLabels in the dam monitor panel.
		 */
		 public void updateTextLabels() {
			 // Create place holders for the dam and JLabels list.
			 Pipe pipe;
			 List<JLabel> labels;
			 // Cycle though all the dams and update their respective JLabels.
			 for (Map.Entry<Pipe, List<JLabel>> entry : pipeLabels.entrySet()) {
				 pipe = entry.getKey();
				 labels = entry.getValue();
				 labels.get(0).setText(pipe.getName());
				 labels.get(1).setText("Max Water: "
							+ Float.toString(pipe.getMaxWater()));
				 labels.get(2).setText("Max Water: "
							+ Float.toString(pipe.getMaxWater()));
				 labels.get(3).setText("Uphill: "
							+ (pipe.getUphill().getName()));
				 labels.get(4).setText("Downhill: "
							+ (pipe.getDownhill().getName()));
				 labels.get(5).setText("Coefficient: "
							+ Float.toString(pipe.getCoeff()));
			 }
		 }	
		
		
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
			} else {
				System.out.println("That fucked up.");
			}
		}

	}
	
    private class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
        	timer.stop();
        	SwingUtilities.invokeLater(doWorkRunnable);
        	timer.start();
        }
    }	
    
	/**
	 * Constructor method for the class.
	 * 
	 * @param system - the Snowy Hydro Scheme that this GUI is supposed to
	 *            monitor.
	 */
	public RealTimeDisplay(SnowyScheme scheme) {
		system = scheme;
		// Create the JFrame needed.
		realTimeMonitor = new JFrame("RTC Snowy Hydro");
		realTimeMonitor.setLayout(new FlowLayout());
		realTimeMonitor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Create the JPanels needed.
		damPanel = new damMonitor();
		riverPanel = new riverMonitor();
		pipePanel = new pipeMonitor();
		abortScheme abortPanel = new abortScheme();
		// Add borders to the different panels.
		damPanel.setBorder(BorderFactory.createTitledBorder("Dams"));
		riverPanel.setBorder(BorderFactory.createTitledBorder("Rivers"));
		pipePanel.setBorder(BorderFactory.createTitledBorder("Pipes"));
		// Add the JPanels to the JFrame.
		realTimeMonitor.add(damPanel);
		realTimeMonitor.add(riverPanel);
		realTimeMonitor.add(pipePanel);
		realTimeMonitor.add(abortPanel);
		realTimeMonitor.pack();
		realTimeMonitor.setVisible(true);

		// Refresh the monitors every second.
		// FIXME - need a method that is more efficient.
		doWorkRunnable = new Runnable() {
		    public void run() {
		    	// Update the dam panel.
		    	damPanel.updateTextLabels();
		    	damPanel.revalidate();
		    	damPanel.repaint();
		    	// Update the river panel.
		    	riverPanel.updateTextLabels();
		    	riverPanel.revalidate();
		    	riverPanel.repaint();
		    	// Update the pipe panel.
		    	pipePanel.updateTextLabels();
		    	pipePanel.revalidate();
		    	pipePanel.repaint();
		    }
		};
		SwingUtilities.invokeLater(doWorkRunnable);
		
		timer = new Timer(1000, new TimerListener());
		timer.start();	
		//the actionPerformed method in this class
	    //is called each time the Timer "goes off
	}
}
