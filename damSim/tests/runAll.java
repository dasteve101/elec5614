package tests;
import physicalObjects.*;

public class runAll {

	public static void main(String[] args) {
		River riverOut = new River(0, 1000, 0 , 100, 10); // River from dam to ocean
		Dam lastDam = new Dam(0, 10000, 2000, riverOut);
		while(!lastDam.getOverflowed()){
			lastDam.waterIn(100);
			riverOut.timeStep();
			lastDam.printObj();
			riverOut.printObj();
		}
		River upHill = new River(1, 1000, 100 , 500, 10, lastDam);
		Dam higherDam = new Dam(1, 10000, 2000, upHill);
		for(int i = 0; i < 100; i++){
			// update Dams first
			higherDam.waterIn(100);
			lastDam.waterIn(10);
			lastDam.waterOut(50);
			higherDam.waterOut(50);
			// Update Rivers
			upHill.timeStep();
			riverOut.timeStep();
			// Update pipes if any
			higherDam.printObj();
			upHill.printObj();
			lastDam.printObj();
			riverOut.printObj();
		}
	}

}
