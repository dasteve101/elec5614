package tests;
import java.util.ArrayList;
import java.util.Arrays;

import physicalObjects.*;

public class runAll {

	public static void main(String[] args) throws Exception {
		/* Setup a basic dam scheme */
		River riverOut = new River(0, 1000, 0 , 100, 10); 						// River from dam to ocean
		Dam lastDam = new Dam(0, 10000, 2000, riverOut, (float) 0.3, 1000);		// lastDam connected to riverOut.

		River upHill = new River(1, 1000, 100 , 500, 10, lastDam);				// River with lastDam connected to the bottom of it.
		Dam higherDam = new Dam(1, 10000, 2000, upHill, (float) 0.3, 1000);		// higherDam is connected to the top of upHill.
		Pipe pipeBetween = new Pipe(300, (float) 0.5, higherDam, lastDam);		// Connect a pipe between higherDam and lastDam.
		SnowyScheme snowWhite = new SnowyScheme(lastDam);						// Create a pseudo Snowy Mountain Scheme.
		riverOut.connectTo(snowWhite.getOcean());								// Connect riverOut to the ocean.
		/* Add all the dam objects to the snowWhite scheme */
		snowWhite.addDam(higherDam);
		snowWhite.addDam(lastDam);
		snowWhite.addPipe(pipeBetween);
		snowWhite.addRiver(upHill);
		
		snowWhite.addPipe(pipeBetween);
		System.out.print("The model is ");
		
		if(snowWhite.validateModel())
			System.out.println("valid");
		else{
			System.out.println("invalid");
			return;
		}
      /* Simulate one time step of the Snowy Scheme using the increment method. */
		snowWhite.increment(Arrays.asList(1.0f,2.0f), Arrays.asList(900.0f,900.0f), Arrays.asList(100.0f,100.0f), Arrays.<Float>asList(),500);
		
		System.out.println(lastDam);
		System.out.println(upHill);
	}
}
