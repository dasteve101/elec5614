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
     * Create a Pipe between two dams
     * @param max - Max amount of power inputed
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

    /**
     * @return max power that can be input
     */
    public float getMax(){
    	return max;
    }
    
    /**
     * @return uphill Dam
     */
    public Dam getUphill(){
    	return uphill;
    }
    
    /**
     * Get the Dam downhill
     * @return Downhill dam
     */
    public Dam getDownhill(){
    	return downhill;
    }
    
    /**
     * @return litres/watt
     */
    public float getCoeff(){
    	return litresperwatt;
    }
    
    /**
     * Estimate the litres sent with that power
     * @param power 
     * @return
     */
    public float estimateLitres(float power){
    	if(power > max)
    		return max*litresperwatt;
    	return power*litresperwatt;
    }
    
    /**
     * Pump water from downhill dam to uphill if up = true
     * else pump from uphill to downhill
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