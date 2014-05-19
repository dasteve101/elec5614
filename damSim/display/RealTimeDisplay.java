package display;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*; 

import physicalObjects.*;

/**
 * Class used to monitor the Snowy Mountain Scheme and provide a mechanism 
 * to shutdown the entire scheme if required.
 * 
 * @author christoper
 *
 */
public class RealTimeDisplay implements ActionListener {

	private SnowyScheme system;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}

	private class ButtonHandler implements ActionListener {
		/* TODO - finish this function */
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
					
		}
	}
	
	
	/**
	 * Constructor method for the class.
	 * 
	 * @param system - the Snowy Hydro Scheme that this GUI is supposed to monitor.
	 */
	public RealTimeDisplay(SnowyScheme scheme) {
		
	}
}
