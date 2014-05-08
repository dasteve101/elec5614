package tests;
import java.util.ArrayList;
import java.util.Arrays;

import physicalObjects.*;

public class runAll {

	public static void main(String[] args) throws Exception {
		River riverOut = new River(0, 1000, 0 , 100, 10); // River from dam to ocean
		Dam lastDam = new Dam(0, 10000, 2000, riverOut, (float) 0.3, 1000);

		River upHill = new River(1, 1000, 100 , 500, 10, null);
		Dam higherDam = new Dam(1, 10000, 2000, upHill, (float) 0.3, 1000);
		upHill.connectTo(lastDam);
		Pipe pipeBetween = new Pipe(300, (float) 0.5, higherDam, lastDam);
		SnowyScheme snowWhite = new SnowyScheme(lastDam);
		riverOut.connectTo(snowWhite.getOcean());
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
		snowWhite.increment(Arrays.asList(1.0f,2.0f), Arrays.asList(900.0f,900.0f), Arrays.asList(100.0f,100.0f), Arrays.<Float>asList(),500);
		
		System.out.println(lastDam);
		System.out.println(upHill);
	}
}
