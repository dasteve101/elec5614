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
		Dam blowering = new Dam("Blowering", 1628000, 0, null , (float)0.3, 80); 
		Dam talbingo = new Dam("Talbingo", 921400, 0, null, (float)0.3, 1500); 		
		Dam tumut2 = new Dam("Tumut 2", 2677, 0, null, (float)0.3, 286); 		
		Dam tumut1 = new Dam("Tumut 1", 52793, 0, null, (float)0.3, 330); 		
		Dam guthega = new Dam("Guthega", 1604, 0, null, (float)0.3, 60);
		Dam murray = new Dam("Murray", 2344, 0, null, (float)0.3, 1500);
		Dam jounama = new Dam("Jounama", 43542, 0, null, (float)0.3, 0);
		Dam tooma = new Dam("Tooma", 28124, 0, null, (float)0.3, 0);
		Dam happyJacks = new Dam("Happy Jacks", 271, 0, null, (float)0.3, 0);
		Dam tangara = new Dam("Tangara", 254099, 0, null, (float)0.3, 0);
		Dam eucumbene = new Dam("Eucumbene", 4798400, 0, null, (float)0.3, 0);
		Dam jindabyne = new Dam("Jindabyne", 688287, 0, null, (float)0.3, 0);
		Dam islandBendDam = new Dam("Island Bend", 3084, 0, null, (float)0.3, 0);
		Dam geehi = new Dam("Geehi", 21093, 0, null, (float)0.3, 0);
		Dam khancoban = new Dam("Khancoban", 26643, 0, null, (float)0.3, 0);

		SnowyScheme scheme = new SnowyScheme(murray);
		scheme.addDam(blowering);
		scheme.addDam(talbingo);
		scheme.addDam(tumut2);
		scheme.addDam(tumut1);
		scheme.addDam(guthega);
		scheme.addDam(murray);
		scheme.addDam(jounama);
		scheme.addDam(tooma);
		scheme.addDam(happyJacks);
		scheme.addDam(tangara);
		scheme.addDam(eucumbene);
		scheme.addDam(jindabyne);
		scheme.addDam(islandBendDam);
		scheme.addDam(geehi);
		scheme.addDam(khancoban);
		
		// Rivers
		River hightoBlowering = new River("Blowering to Ocean", 1000, 100 , 500, 10, scheme.getOcean()); // From blowering to ocean
		blowering.connectTo(hightoBlowering);
		scheme.addRiver(hightoBlowering);
		River junamaToblowering = new River("Junama To Blowering", 100, 0 , 500, 17857, blowering);
		jounama.connectTo(junamaToblowering);
		scheme.addRiver(junamaToblowering);
		River talbingoTojunama = new River("Talbingo to Junama", 100, 0 , 500, 8923, jounama);
		talbingo.connectTo(talbingoTojunama);
		scheme.addRiver(talbingoTojunama);
		River tumutToTalbingo = new River("Tumut to Talbingo", 100, 0 , 500, 29762, talbingo);
		tumut2.connectTo(tumutToTalbingo);
		scheme.addRiver(tumutToTalbingo);
		River tumutToTamut = new River("Tumut 1 to Tumut 2", 100, 0 , 500, 8929, tumut2);
		tumut1.connectTo(tumutToTamut);
		scheme.addRiver(tumutToTamut);
		River toomaToTumut = new River("Tooma to Tumut 1", 100, 0, 50, 14881, tumut1);
		tooma.connectTo(toomaToTumut);
		scheme.addRiver(toomaToTumut);
		River happyJToTumut = new River("Happy Jacks to Tumut 1", 100, 0, 50, 14881, tumut1);
		happyJacks.connectTo(happyJToTumut);
		scheme.addRiver(happyJToTumut);
		Pipe eucumbeneToHappyJ = new Pipe(0 ,0 , eucumbene, happyJacks);
		scheme.addPipe(eucumbeneToHappyJ);
		River tangaraToEucumbene = new River("Tangara to Eucumbene", 100, 0 , 50, 35714, eucumbene);
		tangara.connectTo(tangaraToEucumbene);
		scheme.addRiver(tangaraToEucumbene);
		River eucumbeneToJindabyne = new River("Eucumbene To Jinabyne", 100, 0 , 50, 327382, jindabyne);
		eucumbene.connectTo(eucumbeneToJindabyne);
		scheme.addRiver(eucumbeneToJindabyne);
		River snowyRiver = new River("Snowy River", 100, 0 , 50, 100, scheme.getOcean());
		jindabyne.connectTo(snowyRiver);
		scheme.addRiver(snowyRiver);
		Pipe jindabyneToIsland = new Pipe(1, 0, islandBendDam, jindabyne);
		scheme.addPipe(jindabyneToIsland);
		Pipe eucumbeneToIsland = new Pipe(2, 0, islandBendDam, eucumbene);
		scheme.addPipe(eucumbeneToIsland);
		River guthegaToIsland = new River("Guthega to Island Bend", 100, 0 , 50, 17857, islandBendDam);
		guthega.connectTo(guthegaToIsland);
		scheme.addRiver(guthegaToIsland);
		River islandToGeehi = new River("Island Bend to Geehi", 100, 0 , 50, 16071, geehi);
		islandBendDam.connectTo(islandToGeehi);
		scheme.addRiver(islandToGeehi);
		River geehiToMurray = new River("Geehi to Murray", 100, 0 , 50, 14881, murray);
		geehi.connectTo(geehiToMurray);
		scheme.addRiver(geehiToMurray);
		River murrayToKhancoban = new River("Murray to Khancoban", 100, 0 , 50, 10119, khancoban);
		murray.connectTo(murrayToKhancoban);
		scheme.addRiver(murrayToKhancoban);
		River khancobanRiver = new River("Khancoban to Ocean", 100, 0 , 50, 100, scheme.getOcean());
		khancoban.connectTo(khancobanRiver);
		scheme.addRiver(khancobanRiver);
		
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
