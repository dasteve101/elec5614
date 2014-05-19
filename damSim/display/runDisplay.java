package display;

import javax.swing.SwingUtilities;

import physicalObjects.*;

/**
 * This class opens a GUI to display the objects
 */
public class runDisplay {
	
	/**
	 * Class method to create the Snowy Hydro scheme object. This is a direct copy
	 * from the snowySystem.java class, from package tests.
	 * 
	 * @return
	 */
	private static SnowyScheme constructSnowyScheme() {
		/* Setup a basic dam scheme */
		
		/*Dam setup 
		 * Used ML for water and MW for power, didn't add outlfow as it depends on the dam, coeff wasn't changed
		 */		
		/**dam
		 * @param count - Variable used as an identifier.
		 * @param capacity - The maximum water capacity of the dam.
		 * @param initialLevel - The water level of the dam starts with this value.
		 * @param downstream - Variable representing where water flows when the dam is overflowing.
		 * @param coeff - Coefficient of the power-water equation (used to determine power generated per litre of water).
		 * @param maxPwr - max power the generator in the dam can produce 
		 */
		
	    /** river
	     * @param count - An id
	     * @param max - A max value before flooding
	     * @param min - A min value before drought
	     * @param initialFlow - What the river starts at
	     * @param length - Length of the river
	     * @param out - Where the water flow goes.
	     */
		
		// Dams
		Dam blowering = new Dam(0, 1628000, 0, null , (float)0.3, 80); 
		Dam talbingo = new Dam(1, 921400, 0, null, (float)0.3, 1500); 		
		Dam tumut2 = new Dam(2, 2677, 0, null, (float)0.3, 286); 		
		Dam tumut1 = new Dam(3, 52793, 0, null, (float)0.3, 330); 		
		Dam guthega = new Dam(4, 1604, 0, null, (float)0.3, 60);
		Dam murray = new Dam(5, 2344, 0, null, (float)0.3, 1500);
		Dam jounama = new Dam(6, 43542, 0, null, (float)0.3, 0);
		Dam tooma = new Dam(7, 28124, 0, null, (float)0.3, 0);
		Dam happyJacks = new Dam(8, 271, 0, null, (float)0.3, 0);
		Dam tangara = new Dam(9, 254099, 0, null, (float)0.3, 0);
		Dam eucumbene = new Dam(10, 4798400, 0, null, (float)0.3, 0);
		Dam jindabyne = new Dam(11, 688287, 0, null, (float)0.3, 0);
		Dam islandBendDam = new Dam(12, 3084, 0, null, (float)0.3, 0);
		Dam geehi = new Dam(13, 21093, 0, null, (float)0.3, 0);
		Dam khancoban = new Dam(14, 26643, 0, null, (float)0.3, 0);

		SnowyScheme scheme = new SnowyScheme(murray);
		
		// Rivers
		River hightoBlowering = new River(6, 1000, 100 , 500, 10, scheme.getOcean()); // From blowering to ocean
		blowering.connectTo(hightoBlowering);
		River junamaToblowering = new River(0, 100, 0 , 500, 100, blowering);
		jounama.connectTo(junamaToblowering);
		River talbingoTojunama = new River(1, 100, 0 , 500, 100, jounama);
		talbingo.connectTo(talbingoTojunama);
		River tumutToTalbingo = new River(2, 100, 0 , 500, 100, talbingo);
		tumut2.connectTo(tumutToTalbingo);
		River tumutToTamut = new River(2, 100, 0 , 500, 100, tumut2);
		tumut1.connectTo(tumutToTamut);
		River toomaToTumut = new River(3, 100, 0, 50, 100, tumut1);
		tooma.connectTo(toomaToTumut);
		River happyJToTumut = new River(3, 100, 0, 50, 100, tumut1);
		happyJacks.connectTo(happyJToTumut);
		Pipe eucumbeneToHappyJ = new Pipe(0 ,0 , eucumbene, happyJacks);
		River tangaraToEucumbene = new River(4, 100, 0 , 50, 100, eucumbene);
		tangara.connectTo(tangaraToEucumbene);
		River eucumbeneToJindabyne = new River(4, 100, 0 , 50, 100, jindabyne);
		eucumbene.connectTo(eucumbeneToJindabyne);
		River snowyRiver = new River(4, 100, 0 , 50, 100, scheme.getOcean());
		jindabyne.connectTo(snowyRiver);
		Pipe jindabyneToIsland = new Pipe(0, 0, islandBendDam, jindabyne);
		Pipe eucumbeneToIsland = new Pipe(0, 0, islandBendDam, eucumbene);
		River guthegaToIsland = new River(4, 100, 0 , 50, 100, islandBendDam);
		guthega.connectTo(guthegaToIsland);
		River islandToGeehi = new River(5, 100, 0 , 50, 100, geehi);
		islandBendDam.connectTo(islandToGeehi);
		River geehiToMurray = new River(5, 100, 0 , 50, 100, murray);
		geehi.connectTo(geehiToMurray);
		River murrayToKhancoban = new River(5, 100, 0 , 50, 100, khancoban);
		murray.connectTo(murrayToKhancoban);
		River khancobanRiver = new River(5, 100, 0 , 50, 100, scheme.getOcean());
		khancoban.connectTo(khancobanRiver);

		return scheme;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Gui(); // Let the constructor do the job
			}
		});
		*/
		
		// Create an instance of the Snowy Scheme as pass it to the ImagePanelDisplay constructor
		// and the RealTimeDisplay constructor.
		SnowyScheme hydroScheme = constructSnowyScheme();
		ImagePanelDisplay schemeDisplay = new ImagePanelDisplay(hydroScheme);
	}

}
