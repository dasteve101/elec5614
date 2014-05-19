package display;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import physicalObjects.*;

/**
 * Class used to create an image display of the Snowy Hydro Scheme.
 * Use this to graphically display the scheme.
 * 
 * @author christoper
 *
 */
public class ImagePanelDisplay {
	
	private static SnowyScheme observedScheme;

	/**
	 * Graphically display the Snowy Scheme using the .jpeg images contained in the package.
	 * This panel holds the Snowy Hydro Scheme diagram.
	 * 
	 * @author christoper
	 *
	 */
	public static class ImagePanel extends JPanel {

		/**
		 *	The class member fields.
		 */
		private static final long serialVersionUID = -3613761596634571654L;

		
		
		/* TODO - finish this function */
		/* TODO - determine how the diagram is going to be constructed */
		/*private BufferedImage image;

		public ImagePanel(String fileName) throws IOException {
			image = ImageIO.read(new File(fileName));
			setOpaque(true);
		}
		
		@Override
	    public Dimension getPreferredSize()
	    {
	        return (new Dimension(image.getWidth(), image.getHeight()));
	    }

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(image, 0, 0, null); // see javadoc for more info on the
											// parameters
		}*/
	}
	
	/**
	 * Class used to manually alter attributes in the Snowy Hydro scheme.
	 * This class is used for testing purposes only.
	 * 
	 * @author christoper
	 *
	 */
	public static class SimulationController extends JPanel implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private static class ButtonHandler implements ActionListener {
/* TODO - finish this function */
		// TODO - see if this class is really necessary. Might be removed for optimisation purposes.
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
