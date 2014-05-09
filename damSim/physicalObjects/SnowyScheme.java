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
    public void addDam (Dam dam){
    	dams.add(dam);
    }
    public void addRiver(River river){
    	rivers.add(river);
    }
    public void addPipe(Pipe pipe){
    	pipes.add(pipe);
    }

	
	// Extend to rivers too??
	/**
	 * This method puts the rain in the dams, rivers too?
	 * @param rainInDams
	 * @throws Exception
	 */
	public void rainfall(List<Float> rainInDams) throws Exception{
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
	public float generatePower(List<Float> waterOut) throws Exception{
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
	public float waterOut(List<Float> waterOut) throws Exception{
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
	public void pumpPowers(List<Float> powerIn) throws Exception{
		if(pipes.size() != powerIn.size())
			throw new Exception("Incorrect size of array");
		

		for(int i =0;i <=pipes.size()-1; i++){
				float powerToPump = powerIn.get(i);
				if(powerOut - powerToPump >= 0){
					//up???
					pipes.get(i).pump(powerToPump, true);
					powerOut -= powerToPump;
				}
				else{
					pipes.get(i).pump(powerToPump, true);
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
		return false;
	}
}
