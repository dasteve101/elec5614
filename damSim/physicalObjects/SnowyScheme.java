package physicalObjects;

import java.util.ArrayList;
import java.util.Iterator;

/*
 * This class is used to hold all the objects
 */
public class SnowyScheme {
	private River ocean;
	private float powerOut;
	private Dam waterSupplyPoint;
	private ArrayList<Connectable> objList; 
	private float powerDemand;
	private float waterDemand;
	private float lewayInDemand;
	
	public SnowyScheme(Dam waterSupplyPoint){
		ocean = new River(-1, 0, 0, 0, 1);
		objList = new ArrayList<Connectable>();
		powerOut = 0;
		this.waterSupplyPoint = waterSupplyPoint;
		lewayInDemand = (float) 0.05; // +/- 5%
	}
	
	public Connectable getOcean(){
		return ocean;
	}
	
	public void setWaterSupply(Dam waterSupplyPoint){
		this.waterSupplyPoint = waterSupplyPoint;
	}
	
	public void addObject(Connectable c){
		objList.add(c);
	}
	
	public void addObjects(Connectable c[]){
		for(int i = 0; i < c.length; i++)
			objList.add(c[i]);
	}

	public ArrayList<Connectable> getObjects(){
		return objList;
	}
	
	private int countDams(){
		Iterator<Connectable> it = objList.iterator();
		int count = 0;
		while(it.hasNext()){
			Connectable c = it.next();
			if(c instanceof Dam)
				count++;
		}
		return count;
	}
	
	private int countPipes(){
		Iterator<Connectable> it = objList.iterator();
		int count = 0;
		while(it.hasNext()){
			Connectable c = it.next();
			if(c instanceof Pipe)
				count++;
		}
		return count;
	}
	
	public void rainfall(ArrayList<Float> rainInDams) throws Exception{
		if(countDams() != rainInDams.size())
			throw new Exception("Incorrect size of array");
		Iterator<Connectable> it = objList.iterator();
		Iterator<Float> r = rainInDams.iterator();
		while(it.hasNext()){
			Connectable c = it.next();
			if(c instanceof Dam)
				c.waterIn(r.next());
		}
	}
	
	public float generatePower(ArrayList<Float> waterOut) throws Exception{
		if(countDams() != waterOut.size())
			throw new Exception("Incorrect size of array");
		float power = 0;
		Iterator<Connectable> it = objList.iterator();
		Iterator<Float> w = waterOut.iterator();
		while(it.hasNext()){
			Connectable c = it.next();
			if(c instanceof Dam)
				power += ((Dam) c).genPower(w.next());
		}
		powerOut += power;
		return power;
	}
	
	public float waterOut(ArrayList<Float> waterOut) throws Exception{
		if(countDams() != waterOut.size())
			throw new Exception("Incorrect size of array");
		float water = 0;
		Iterator<Connectable> it = objList.iterator();
		Iterator<Float> w = waterOut.iterator();
		while(it.hasNext()){
			Connectable c = it.next();
			if(c instanceof Dam)
				water += c.waterOut(w.next());
		}
		return water;
	}
	
	private void timeStepRivers(){
		Iterator<Connectable> it = objList.iterator();
		while(it.hasNext()){
			Connectable c = it.next();
			if(c instanceof River)
				((River) c).timeStep();
		}
	}
	
	public void pumpPowers(ArrayList<Float> powerIn) throws Exception{
		if(countPipes() != powerIn.size())
			throw new Exception("Incorrect size of array");
		Iterator<Connectable> it = objList.iterator();
		Iterator<Float> w = powerIn.iterator();
		while(it.hasNext()){
			Connectable c = it.next();
			if(c instanceof Dam){
				float powerToPump = w.next();
				if(powerOut - powerToPump >= 0){
					c.waterOut(powerToPump);
					powerOut -= powerToPump;
				}
				else{
					c.waterOut(powerOut);
					powerOut = 0;
				}
			}
		}
	}
	
	public void increment(ArrayList<Float> rain, ArrayList<Float> waterForPower, ArrayList<Float> waterOut, ArrayList<Float> pumpPower) throws Exception{
		powerOut = 0;
		rainfall(rain);
		generatePower(waterForPower);
		waterOut(waterOut);
		timeStepRivers();
		pumpPowers(pumpPower);
		// Check demand of power and water are met
		if(powerOut < powerDemand*(1-lewayInDemand) || powerOut > powerDemand*(1+lewayInDemand) )
			System.out.println("Power demand not met: Needed " + powerDemand + " and got " + powerOut);
		if(waterSupplyPoint.getLevel() < waterDemand*(1-lewayInDemand))
			System.out.println("Water demand not met: Needed " + waterDemand + " and got " + waterOut);
		powerOut -= powerDemand;
		waterSupplyPoint.pumpOut(waterDemand);
	}
	
	// Check all connections and water flow
	public boolean validateModel(){
		return false;
	}
}
