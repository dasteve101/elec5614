package tests;
import java.util.ArrayList;
import java.util.Arrays;
import physicalObjects.*;

public class snowySystem {

	public static void main(String[] args) throws Exception {
		/* Setup a basic dam scheme */
		
		/*Dam setup 
		 * Used ML for water and MW for power, didn't add outlfow as it depends on the dam, coeff wasn't changed
		 * River setup
		 * Length added in metres for rivers EXCEPT for the ones to ocean
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
		River hightoBlowering = new River(0, 1000, 100 , 500, 10, scheme.getOcean()); // From blowering to ocean
		blowering.connectTo(hightoBlowering);
		River junamaToblowering = new River(1, 100, 0 , 500, 17857, blowering);
		jounama.connectTo(junamaToblowering);
		River talbingoTojunama = new River(2, 100, 0 , 500, 8923, jounama);
		talbingo.connectTo(talbingoTojunama);
		River tumutToTalbingo = new River(3, 100, 0 , 500, 29762, talbingo);
		tumut2.connectTo(tumutToTalbingo);
		River tumutToTamut = new River(4, 100, 0 , 500, 8929, tumut2);
		tumut1.connectTo(tumutToTamut);
		River toomaToTumut = new River(5, 100, 0, 50, 14881, tumut1);
		tooma.connectTo(toomaToTumut);
		River happyJToTumut = new River(6, 100, 0, 50, 14881, tumut1);
		happyJacks.connectTo(happyJToTumut);
		Pipe eucumbeneToHappyJ = new Pipe(0 ,0 , eucumbene, happyJacks);
		River tangaraToEucumbene = new River(7, 100, 0 , 50, 35714, eucumbene);
		tangara.connectTo(tangaraToEucumbene);
		River eucumbeneToJindabyne = new River(8, 100, 0 , 50, 327382, jindabyne);
		eucumbene.connectTo(eucumbeneToJindabyne);
		River snowyRiver = new River(9, 100, 0 , 50, 100, scheme.getOcean());
		jindabyne.connectTo(snowyRiver);
		Pipe jindabyneToIsland = new Pipe(1, 0, islandBendDam, jindabyne);
		Pipe eucumbeneToIsland = new Pipe(2, 0, islandBendDam, eucumbene);
		River guthegaToIsland = new River(10, 100, 0 , 50, 17857, islandBendDam);
		guthega.connectTo(guthegaToIsland);
		River islandToGeehi = new River(11, 100, 0 , 50, 16071, geehi);
		islandBendDam.connectTo(islandToGeehi);
		River geehiToMurray = new River(12, 100, 0 , 50, 14881, murray);
		geehi.connectTo(geehiToMurray);
		River murrayToKhancoban = new River(13, 100, 0 , 50, 10119, khancoban);
		murray.connectTo(murrayToKhancoban);
		River khancobanRiver = new River(14, 100, 0 , 50, 100, scheme.getOcean());
		khancoban.connectTo(khancobanRiver);

		System.out.print("The model is ");
		
		if(scheme.validateModel())
			System.out.println("valid");
		else{
			System.out.println("invalid");
			return;
		}
      /* Simulate one time step of the Snowy Scheme using the increment method. 
		snowWhite.increment(Arrays.asList(1.0f,2.0f), Arrays.asList(900.0f,900.0f), Arrays.asList(100.0f,100.0f), Arrays.<Float>asList(),500);
		
		System.out.println(lastDam);
		System.out.println(upHill);
		*/

	}
}
