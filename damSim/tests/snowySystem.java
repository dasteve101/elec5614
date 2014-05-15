package tests;
import java.util.ArrayList;
import java.util.Arrays;

import physicalObjects.*;

public class snowySystem {

	public static void main(String[] args) throws Exception {
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
		
		
		/*			//dam to ocean river
		River riverOut = new River(7, 1000, 0 , 100, 10);
	
		//Starting River
		River hightoBlowering = new River(6, 1000, 100 , 500, 10, blowering);
		
		
		Dam blowering = new Dam(0, 1628000, 0, hightoBlowering , (float)0.3, 80); 
		Dam talbingo = new Dam(1, 921400, 0, , (float)0.3, 1500); 		
		Dam tumut2 = new Dam(2, 2677, 0, , (float)0.3, 286); 		
		Dam tumut1 = new Dam(3, 52793, 0, , (float)0.3, 330); 		
		Dam guthega = new Dam(4, 1604, 0, , (float)0.3, 60);
		Dam murray2 = new Dam(5, 2344, 0, , (float)0.3, 550);
		Dam murray1 = new Dam(6, 2344, 0, , (float)0.3, 950);  //a power station with no dam :(
		
		/* Setup a basic river scheme */		

	
		//basic setup
		///////////////////////I STILL HAVE TO ADD RIVER LENGTH
		/*
		River bloweringRes = new River(0, 100, 0 , blowering, 100, talbingo);
		River talbingoRes = new River(1, 100, 0 , talbingo, 100, tumut2);
		River tumutRiver = new River(2, 100, 0 , tumut2, 100, tumut1);
		River haupttunnelEucumbene = new River(3, 100, 0, tumut1, 100, guthega);
		River haupttunnelSnowy = new River(4, 100, 0 , guthega, 100, murray1);
		River murrayRiver = new River(5, 100, 0 , muray1, 100, murray2);
		


		
		
		
		SnowyScheme snowWhite = new SnowyScheme(lastDam);
		
		riverOut.connectTo(snowWhite.getOcean());	// Connect riverOut to the ocean.
		*/
		/* Add all the dam objects to the snowWhite scheme */
		/*
		snowWhite.addDam(blowering);
		snowWhite.addDam(talbingo);
		snowWhite.addDam(tumut2);
		snowWhite.addDam(tumut1);
		snowWhite.addDam(guthega);
		snowWhite.addDam(murray2);
		snowWhite.addDam(murray1);

		snowWhite.addRiver(hightoBlowering);
		snowWhite.addRiver(riverOut);
		snowWhite.addRiver(bloweringRes);
		snowWhite.addRiver(talbingoRes);
		snowWhite.addRiver(tumutRiver);
		snowWhite.addRiver(haupttunnelEucumbene);
		snowWhite.addRiver(haupttunnelSnowy);
		snowWhite.addRiver(murrayRiver);


		snowWhite.addRiver(riverOut);

		System.out.print("The model is ");
		
		if(snowWhite.validateModel())
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
