package physicalObjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

/**
 * This class is used to hold all the objects
 */
public class SnowyScheme {
	private River ocean;
	private float powerOut;
	private Dam waterSupplyPoint;
	private List<Dam> dams;
	private List<River> rivers;
	private List<Pipe> pipes;
	//private float powerDemand;
	private float waterDemand;
	private float lewayInDemand;
	
	/**
	 * @param waterSupplyPoint
	 */
	public SnowyScheme(Dam waterSupplyPoint){
		ocean = new River(-1, 0, 0, 0, 1);
		dams = new ArrayList<Dam>();
		rivers = new ArrayList<River>();
		pipes = new ArrayList<Pipe>();
		powerOut = 0;
		this.waterSupplyPoint = waterSupplyPoint;
		lewayInDemand = (float) 0.05; // +/- 5%
	}
	
	/**
	 * @return
	 */
	public Connectable getOcean(){
		return ocean;
	}
	
	/**
	 * @param waterSupplyPoint
	 */
	public void setWaterSupply(Dam waterSupplyPoint){
		this.waterSupplyPoint = waterSupplyPoint;
	}
	
    /**
     * @param dam - dam to add to object
     */
    public void addDam (Dam dam){
    	dams.add(dam);
    }
    
    /**
     * @return - dams stored in this object
     */
    public List<Dam> getDams(){
    	return dams;
    }
    
    /**
     * @param river - river to add to object
     */
    public void addRiver(River river){
    	rivers.add(river);
    }
    
    /**
     * @return - rivers stored in this object
     */
    public List<River> getRivers(){
    	return rivers;
    }
    
    /**
     * @param pipe - pipe to add to object
     */
    public void addPipe(Pipe pipe){
    	pipes.add(pipe);
    }

    /**
     * @return - pipes stored in this object
     */
    public List<Pipe> getPipes(){
    	return pipes;
    }
	
	// Extend to rivers too??
	/**
	 * This method puts the rain in the dams, rivers too?
	 * @param rainInDams
	 * @throws Exception
	 */
	protected void rainfall(List<Float> rainInDams) throws Exception{
		if(dams.size() != rainInDams.size())
			throw new Exception("Incorrect size of array");
		for(int i =0 ; i <dams.size()-1;i++){
			dams.get(i).waterIn(rainInDams.get(i));
		
		}

	}
	
	/**
	 * @param waterOut
	 * @return
	 * @throws Exception
	 */
	protected float generatePower(List<Float> waterOut) throws Exception{
		if(dams.size() != waterOut.size())
			throw new Exception("Incorrect size of array");
		float power = 0;
		
	
        for(int i= 0; i <=dams.size()-1;i++){
				power += dams.get(i).genPower(waterOut.get(i));
		}
		powerOut += power;
		return power;
	}
	
	/**
	 * @param waterOut
	 * @return
	 * @throws Exception
	 */
	protected float waterOut(List<Float> waterOut) throws Exception{
		if(dams.size() != waterOut.size())
			throw new Exception("Incorrect size of array");
		float water = 0;
		
	
		for(int i =0; i <=dams.size()-1;i ++){
				water += dams.get(i).waterOut(waterOut.get(i));
		}
		return water;
	}
	
	/**
	 * 
	 */
	private void timeStepRivers(){
		for(int i =0; i <=rivers.size()-1; i++){
				rivers.get(i).timeStep();
		}
	}
	
	/**
	 * @param powerIn
	 * @throws Exception
	 */
	protected void pumpPowers(List<Float> powerIn) throws Exception{
		if(pipes.size() != powerIn.size())
			throw new Exception("Incorrect size of array");
		

		for(int i =0;i <=dams.size()-1; i++){
				float powerToPump = powerIn.get(i);
				if(powerOut - powerToPump >= 0){
					dams.get(i).waterOut(powerToPump);
					powerOut -= powerToPump;
				}
				else{
					dams.get(i).waterOut(powerOut);
					powerOut = 0;
				}
			}
		}
	
	
	/**
	 * @param rain
	 * @param waterForPower
	 * @param waterOut
	 * @param pumpPower
	 * @throws Exception
	 */
	public void increment(List<Float> rain, List<Float> waterForPower, List<Float> waterOut, List<Float> pumpPower, float powerDemand) throws Exception{
		powerOut = 0;
		rainfall(rain);
		generatePower(waterForPower);
		waterOut(waterOut);
		timeStepRivers();
		//pumpPowers(pumpPower);
		// Check demand of power and water are met
		if(powerOut < powerDemand*(1-lewayInDemand) || powerOut > powerDemand*(1+lewayInDemand) )
			System.out.println("Power demand not met: Needed " + powerDemand + " and got " + powerOut);
		if(waterSupplyPoint.getLevel() < waterDemand*(1-lewayInDemand))
			System.out.println("Water demand not met: Needed " + waterDemand + " and got " + waterOut);
		powerOut -= powerDemand;
		waterSupplyPoint.pumpOut(waterDemand);
	}
	
	/**
	 * Check all connections and water flow
	 * Recursive?
	 * @return
	 */
	public boolean validateModel(){
		/*
		 *  Invalid Models (Water does not end up at ocean)
		 *  Dam does not have output
		 *  Check none are connected to null
		 *  Pipes connect one object only
		 *  River does not go to dam or ocean
		 *  Levels all start > 0
		 *  Min < max always
		 *  any others?
		 */
		if(waterSupplyPoint == null || !dams.contains(waterSupplyPoint))
			return false;
		for(int i = 0; i < dams.size()-1; i++){
			if(dams.get(i).getDownstream() == null)
				return false;
			if(dams.get(i).getLevel() < 0 || dams.get(i).getLevel() > dams.get(i).getCapacity())
				return false;
			if(dams.get(i).getMaxWaterForPower() < 0)
				return false;
			if(dams.get(i).getWattsPerLitre() < 0)
				return false;
		}
		for(int i = 0; i < rivers.size() - 1; i++){
			if(rivers.get(i).getDownstream() == null)
				return false;
			if(rivers.get(i).getMax() < rivers.get(i).getMin())
				return false;
			if(rivers.get(i).getLength() < 1)
				return false;
			if(rivers.get(i).getFlow() < 0)
				return false;
		}
		for(int i = 0; i < pipes.size() - 1; i++){
			if(pipes.get(i).getDownhill() == null || pipes.get(i).getUphill() == null)
				return false;
			if(pipes.get(i).getCoeff() < 0 || pipes.get(i).getMax() < 0)
				return false;
		}
		
		return true;
	}
}
