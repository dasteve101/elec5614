package physicalObjects;

/**
 * This class implements a pipe between two Connectable objects
 * The pipe has some max power to input into the pump.
 * It pumps water between two dams based on "watts" of power input
 * The pipe connects two Connectable objects together
 */
public class Pipe{
    private float max = 0;
    private float litresperwatt = 0;
    private boolean pumpBlown = false;
    private Dam uphill = null;
    private Dam downhill = null;
    
    
    /**
     * @param max - Max amount of power inputted
     * @param coeff - water = coeff*power
     * @param uphill - the Dam to pump to (if up = true)
     * @param downhill - the Dam to pump from (if up = true)
     */
    public Pipe(float max, float coeff, Dam uphill, Dam downhill){
		this.max = max;
		this.litresperwatt = coeff;
		this.uphill = uphill;
		this.downhill = downhill;
    }

    public float getMax(){
    	return max;
    }
    
    public Dam getUphill(){
    	return uphill;
    }
    
    public Dam getDownhill(){
    	return downhill;
    }
    
    public float getCoeff(){
    	return litresperwatt;
    }
    
    public float estimateLitres(float power){
    	if(power > max)
    		return max*litresperwatt;
    	return power*litresperwatt;
    }
    
    /**
     * @param watts - power to put in the pump
     * @param up - direction to pump
     * @return
     */
    public float pump(float watts, boolean up){
		if(watts > max || pumpBlown){
		    pumpBlown = true;
		    return 0;
		}
		float litres = watts*litresperwatt;
		if(up){
			// check that there is enough water to pump out
			litres = downhill.pumpOut(litres);
			uphill.waterIn(litres);
		}
		else{
			// check that there is enough water to pump out
			litres = uphill.pumpOut(litres);
			downhill.waterIn(litres);
		}
		return litres;
    }

}