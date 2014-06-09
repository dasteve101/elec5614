package physicalObjects;

/**
 * This class implements a pipe between two Connectable objects
 * The pipe has some max power to input into the pump.
 * It pumps water between two dams based on "watts" of power input
 * The pipe connects two Connectable objects together
 */
public class Pipe{
    private float maxPower = 0;
    private float maxWater = 0;
    private float litresperwatt = 0;
    private boolean pumpBlown = false;
    private Dam uphill = null;
    private Dam downhill = null;
    private String name;
    
    /**
     * Create a Pipe between two dams
     * @param name - Name of pipe
     * @param maxPwr - Max amount of power inputed uphill
     * @param maxWtr - Max amount of water downhill
     * @param coeff - water = coeff*power
     * @param uphill - the Dam to pump to (if up = true)
     * @param downhill - the Dam to pump from (if up = true)
     */
    public Pipe(String name, float maxPwr, float maxWtr, float coeff, Dam uphill, Dam downhill){
    	this.name = name;
		this.maxPower = maxPwr;
		this.maxWater = maxWtr;
		this.litresperwatt = coeff;
		this.uphill = uphill;
		this.downhill = downhill;
    }

    /**
     * The max power that can be used for pumping
     * @return max power that can be input
     */
    public float getMaxPower(){
    	return maxPower;
    }
    
    /**
     * The max water that can be released for downhill
     * @return max water that can be released
     */
    public float getMaxWater(){
    	return maxWater;
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
    	if(power > maxPower)
    		return maxPower*litresperwatt;
    	return power*litresperwatt;
    }
    
    /**
     * Pump water from downhill dam to uphill if up = true
     * else release water from uphill to downhill
     * @param wattsOrLitres - power to put in the pump if up = true
     	otherwise the amount of litres to release downhill
     * @param up - direction to pump
     * @return
     */
    protected float pump(float wattsOrLitres, boolean up){
		if((up && pumpBlown) || (up && wattsOrLitres > maxPower)){
		    pumpBlown = true;
		    return 0;
		}
		float litres = wattsOrLitres;
		if(up){
			litres = wattsOrLitres*litresperwatt;
			// check that there is enough water to pump out			
			litres = downhill.pumpOut(litres);
			uphill.waterIn(litres);
		}
		else{
			// check that there is enough water to release
			litres = uphill.pumpOut(litres);
			downhill.waterIn(litres);
		}
		return litres;
    }
    
    public String getName() {
		return name;
	}

	public String toString(){
    	String s = "Pipe " + name + "\n";
    	s += "From " + uphill.getName() + "\n";
    	s += "To " + downhill.getName() + "\n";
    	s += "Max power" + maxPower + "\n";
    	s += "Litres/Watt" + litresperwatt + "\n";
    	s += "Max water" + maxWater + "\n";
    	return s;
    }
}