package display;

import java.util.ArrayList;
import javax.swing.SwingUtilities;
import physicalObjects.*;
import controller.*;

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
		Dam blowering = new Dam("Blowering", 1628000, 1628000/2, null , (float)0.3, 80); 
		Dam talbingo = new Dam("Talbingo", 921400, 921400/2, null, (float)0.3, 1500); 		
		Dam tumut2 = new Dam("Tumut 2", 2677, 2677/2, null, (float)0.3, 286); 		
		Dam tumut1 = new Dam("Tumut 1", 52793, 52793/2, null, (float)0.3, 330); 		
		Dam guthega = new Dam("Guthega", 1604, 1604/2, null, (float)0.3, 60);
		Dam murray = new Dam("Murray", 2344, 2344/2, null, (float)0.3, 1500);
		Dam jounama = new Dam("Jounama", 43542, 43542/2, null, (float)0.3, 0);
		Dam tooma = new Dam("Tooma", 28124, 28124/2, null, (float)0.3, 0);
		Dam happyJacks = new Dam("Happy Jacks", 271, 271/2, null, (float)0.3, 0);
		Dam tangara = new Dam("Tangara", 254099, 254099/2, null, (float)0.3, 0);
		Dam eucumbene = new Dam("Eucumbene", 4798400, 4798400/2, null, (float)0.3, 0);
		Dam jindabyne = new Dam("Jindabyne", 688287, 688287/2, null, (float)0.3, 0);
		Dam islandBendDam = new Dam("Island Bend", 3084, 3084/2, null, (float)0.3, 0);
		Dam geehi = new Dam("Geehi", 21093, 21093/2, null, (float)0.3, 0);
		Dam khancoban = new Dam("Khancoban", 26643, 26643/2, null, (float)0.3, 0);

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
		River junamaToblowering = new River("Junama To Blowering", 100, 0 , 500, (float) 17.857, blowering);
		jounama.connectTo(junamaToblowering);
		scheme.addRiver(junamaToblowering);
		River talbingoTojunama = new River("Talbingo to Junama", 100, 0 , 500, (float) 8.923, jounama);
		talbingo.connectTo(talbingoTojunama);
		scheme.addRiver(talbingoTojunama);
		River tumutToTalbingo = new River("Tumut to Talbingo", 100, 0 , 500, (float) 29.762, talbingo);
		tumut2.connectTo(tumutToTalbingo);
		scheme.addRiver(tumutToTalbingo);
		River tumutToTamut = new River("Tumut 1 to Tumut 2", 100, 0 , 500, (float) 8.929, tumut2);
		tumut1.connectTo(tumutToTamut);
		scheme.addRiver(tumutToTamut);
		River toomaToTumut = new River("Tooma to Tumut 1", 100, 0, 50, (float) 14.881, tumut1);
		tooma.connectTo(toomaToTumut);
		scheme.addRiver(toomaToTumut);
		River happyJToTumut = new River("Happy Jacks to Tumut 1", 100, 0, 50, (float) 14.881, tumut1);
		happyJacks.connectTo(happyJToTumut);
		scheme.addRiver(happyJToTumut);
		Pipe eucumbeneToHappyJ = new Pipe("Eucumbene to Happy Jacks", 0 , 0, 0, eucumbene, happyJacks);
		scheme.addPipe(eucumbeneToHappyJ);
		River tangaraToEucumbene = new River("Tangara to Eucumbene", 100, 0 , 50, (float) 35.714, eucumbene);
		tangara.connectTo(tangaraToEucumbene);
		scheme.addRiver(tangaraToEucumbene);
		River eucumbeneToJindabyne = new River("Eucumbene To Jinabyne", 100, 0 , 50, (float) 327.382, jindabyne);
		eucumbene.connectTo(eucumbeneToJindabyne);
		scheme.addRiver(eucumbeneToJindabyne);
		River snowyRiver = new River("Snowy River", 100, 0 , 50, 100, scheme.getOcean());
		jindabyne.connectTo(snowyRiver);
		scheme.addRiver(snowyRiver);
		Pipe jindabyneToIsland = new Pipe("JindaByne to Island Bend", 1, 0, 0, islandBendDam, jindabyne);
		scheme.addPipe(jindabyneToIsland);
		Pipe eucumbeneToIsland = new Pipe("Eucumbene to Island", 2, 0, 0, islandBendDam, eucumbene);
		scheme.addPipe(eucumbeneToIsland);
		River guthegaToIsland = new River("Guthega to Island Bend", 100, 0 , 50, (float) 17.857, islandBendDam);
		guthega.connectTo(guthegaToIsland);
		scheme.addRiver(guthegaToIsland);
		River islandToGeehi = new River("Island Bend to Geehi", 100, 0 , 50, (float) 16.071, geehi);
		islandBendDam.connectTo(islandToGeehi);
		scheme.addRiver(islandToGeehi);
		River geehiToMurray = new River("Geehi to Murray", 100, 0 , 50, (float) 14.881, murray);
		geehi.connectTo(geehiToMurray);
		scheme.addRiver(geehiToMurray);
		River murrayToKhancoban = new River("Murray to Khancoban", 100, 0 , 50, (float) 10.119, khancoban);
		murray.connectTo(murrayToKhancoban);
		scheme.addRiver(murrayToKhancoban);
		River khancobanRiver = new River("Khancoban to Ocean", 100, 0 , 50, 100, scheme.getOcean());
		khancoban.connectTo(khancobanRiver);
		scheme.addRiver(khancobanRiver);

		System.out.print("The model is ");
		
		if(scheme.validateModel())
			System.out.println("valid");
		else{
			System.out.println("invalid");
		}
		
		return scheme;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		SnowyScheme hydroScheme = constructSnowyScheme();
		ControlRTS control = null;
		try {
			// Start a new thread with the controller
			control = new ControlRTS(hydroScheme);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		System.out.println("Controller initailized");

		ImagePanelDisplay schemeDisplay = new ImagePanelDisplay(hydroScheme);
		// put control in here???
		// NB: control.stop() to kill all the threads
		RealTimeDisplay schemeMonitor = new RealTimeDisplay(hydroScheme, control);
	}

}
