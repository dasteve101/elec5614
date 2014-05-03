package physicalObjects;

/*
 * This class implements a pipe between two Connectable objects
 * The pipe has some max power to input into the pump.
 * It pumps water between two dams based on "watts" of power input 
 */
public class Pipe{
    private float max = 0;
    private float litresperwatt = 0;
    private boolean pumpBlown = false;
    private Connectable uphill = null;
    private Connectable downhill = null;
    
    public Pipe(float max, float coeff, Connectable uphill, Connectable downhill){
		this.max = max;
		this.litresperwatt = coeff;
		this.uphill = uphill;
		this.downhill = downhill;
    }

    public float pump(float watts, boolean up){
		if(watts > max || pumpBlown){
		    pumpBlown = true;
		    return 0;
		}
		float litres = watts*litresperwatt;
		if(up){
			// check that there is enough water to pump out
			litres = downhill.waterOut(litres);
			uphill.waterIn(litres);
		}
		else{
			// check that there is enough water to pump out
			litres = uphill.waterOut(litres);
			downhill.waterIn(litres);
		}
		return litres;
    }

}