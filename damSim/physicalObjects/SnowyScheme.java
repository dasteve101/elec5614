package physicalObjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.TreeSet;

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
	// private float powerDemand;
	private float waterDemand;
	private float lewayInDemand;

	/**
	 * @param waterSupplyPoint - The dam object that is used to determine
	 *            whether or not water demand is met.
	 */
	public SnowyScheme(Dam waterSupplyPoint) {
		ocean = new River("Ocean", 0, 0, 0, 1);
		dams = new ArrayList<Dam>();
		rivers = new ArrayList<River>();
		pipes = new ArrayList<Pipe>();
		powerOut = 0;
		this.waterSupplyPoint = waterSupplyPoint;
		lewayInDemand = (float) 0.05; // +/- 5%
	}

	/**
	 * @return The ocean connected to this scheme.
	 */
	public Connectable getOcean() {
		return ocean;
	}

	/**
	 * @param waterSupplyPoint - the dam that the town water supply is from
	 */
	public void setWaterSupply(Dam waterSupplyPoint) {
		this.waterSupplyPoint = waterSupplyPoint;
	}

	/**
	 * @param dam - dam to add to object
	 */
	public void addDam(Dam dam) {
		if(!dams.contains(dam))
			dams.add(dam);
	}

	/**
	 * @return - dams stored in this object
	 */
	public List<Dam> getDams() {
		return dams;
	}

	/**
	 * @param river - river to add to object
	 */
	public void addRiver(River river) {
		if(!rivers.contains(river))
			rivers.add(river);
	}

	/**
	 * @return - rivers stored in this object
	 */
	public List<River> getRivers() {
		return rivers;
	}

	/**
	 * @param pipe - pipe to add to object
	 */
	public void addPipe(Pipe pipe) {
		if(!pipes.contains(pipe))
			pipes.add(pipe);
	}

	/**
	 * @return - pipes stored in this object
	 */
	public List<Pipe> getPipes() {
		return pipes;
	}

	// TODO - Extend to rivers too??
	/**
	 * This method puts the rain in the dams, rivers too?
	 * 
	 * @param rainInDams - rainfall in each dam
	 * @throws IncorrectLengthException
	 */
	protected void rainfall(List<Float> rainInDams) throws IncorrectLengthException {
		if (dams.size() != rainInDams.size())
			throw new IncorrectLengthException(dams.size(), rainInDams.size());
		for (int i = 0; i <= dams.size() - 1; i++) {
			dams.get(i).waterIn(rainInDams.get(i));
		}
	}

	/**
	 * @param waterOut - water that was released to generate power
	 * @return - total power generated
	 * @throws IncorrectLengthException
	 */
	protected float generatePower(List<Float> waterOut) throws IncorrectLengthException {
		if (dams.size() != waterOut.size())
			throw new IncorrectLengthException(dams.size(), waterOut.size());
		float power = 0;

		for (int i = 0; i <= dams.size() - 1; i++) {
			power += dams.get(i).genPower(waterOut.get(i));
		}
		powerOut += power;
		return power;
	}

	/**
	 * @param waterOut - water to release from each of the dams
	 * @return - the amount of water that was released
	 * @throws IncorrectLengthException
	 */
	protected float waterOut(List<Float> waterOut) throws IncorrectLengthException {
		if (dams.size() != waterOut.size())
			throw new IncorrectLengthException(dams.size(), waterOut.size());
		float water = 0;

		for (int i = 0; i <= dams.size() - 1; i++) {
			water += dams.get(i).waterOut(waterOut.get(i));
		}
		return water;
	}

	/**
	 * 
	 */
	private void timeStepRivers() {
		for (int i = 0; i <= rivers.size() - 1; i++) {
			rivers.get(i).timeStep();
		}
	}

	/**
	 * @param powerIn - power for each of the pipes
	 * @throws IncorrectLengthException
	 */
	protected void pumpPowers(List<Float> powerIn) throws IncorrectLengthException {
		if (pipes.size() != powerIn.size())
			throw new IncorrectLengthException(pipes.size(), powerIn.size());

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
	 * @param rain - list of the size of dams, rainfall in each
	 * @param waterForPower - list of the size of dams, water to let out for power in each
	 * @param waterOut - list of the size of dams, water to let out in each
	 * @param pumpPower - power for all the pumps
	 * @throws IncorrectLengthException
	 */
	public void increment(List<Float> rain, List<Float> waterForPower,
			List<Float> waterOut, List<Float> pumpPower, float powerDemand)
			throws IncorrectLengthException {
		powerOut = 0;
		rainfall(rain);
		generatePower(waterForPower);
		waterOut(waterOut);
		timeStepRivers();
		// pumpPowers(pumpPower);
		// Check demand of power and water are met
		if(powerOut < powerDemand*(1-lewayInDemand) || powerOut > powerDemand*(1+lewayInDemand) )
			System.out.println("Power demand not met: Needed " + powerDemand + "+/-" + (lewayInDemand*100) + "% and got " + powerOut);
		if(waterSupplyPoint.getLevel() < waterDemand*(1-lewayInDemand))
			System.out.println("Water demand not met: Needed " + waterDemand + "+/-" + (lewayInDemand*100) + "% and got " + waterOut);
		powerOut -= powerDemand;
		waterSupplyPoint.pumpOut(waterDemand);
	}

	/**
	 * Check all connections and water flow
	 * @return - valid/invalid model
	 */
	public boolean validateModel() {
		/*
		 * Invalid Models (Water does not end up at ocean) Dam does not have
		 * output Check none are connected to null Pipes connect one object only
		 * River does not go to dam or ocean Levels all start > 0 Min < max
		 * always any others?
		 */
		if (waterSupplyPoint == null || !dams.contains(waterSupplyPoint))
			return false;
		for (int i = 0; i < dams.size() - 1; i++) {
			if (dams.get(i).getDownstream() == null)
				return false;
			if (dams.get(i).getLevel() < 0
					|| dams.get(i).getLevel() > dams.get(i).getCapacity())
				return false;
			if (dams.get(i).getMaxWaterForPower() < 0)
				return false;
			if (dams.get(i).getWattsPerLitre() < 0)
				return false;
		}
		for (int i = 0; i < rivers.size() - 1; i++) {
			if (rivers.get(i).getDownstream() == null)
				return false;
			if (rivers.get(i).getMax() < rivers.get(i).getMin())
				return false;
			if (rivers.get(i).getLength() < 1)
				return false;
			if (rivers.get(i).getFlow() < 0)
				return false;
		}
		for (int i = 0; i < pipes.size() - 1; i++) {
			if (pipes.get(i).getDownhill() == null
					|| pipes.get(i).getUphill() == null)
				return false;
			if (pipes.get(i).getCoeff() < 0 || pipes.get(i).getMax() < 0)
				return false;
		}
		TreeSet<Connectable> completedIndexs = new TreeSet<Connectable>();
		for(int i = 0; i < dams.size() - 1; i++){
			completedIndexs = recursiveDFS(dams.get(i), completedIndexs, null);
			if(!completedIndexs.contains(dams.get(i)))
				return false;
		}
		for(int i = 0; i < rivers.size() - 1; i++){
			completedIndexs = recursiveDFS(rivers.get(i), completedIndexs, null);
			if(!completedIndexs.contains(rivers.get(i)))
				return false;
		}
		// All conditions have been checked, must be valid
		return true;
	}
	
	/**
	 * This performs a recursive DFS on the nodes to check all 'roads lead to Rome/Ocean'
	 * It uses a TreeSet to sort the objects
	 * @param damOrRiver - The object to check
	 * @param completedIndexs - A list of already check objects
	 * @param tmpList - A list of objects checked in this iteration
	 * @return
	 */
	private TreeSet<Connectable> recursiveDFS(Connectable damOrRiver, TreeSet<Connectable> completedIndexs, TreeSet<Connectable> tmpList){
		if(completedIndexs.contains(damOrRiver)){
			if(tmpList != null)
				completedIndexs.addAll(tmpList);
			return completedIndexs; // If it has already been solved, stop
		}
		if(damOrRiver.getDownstream() == null)
			return completedIndexs;
		// If it is trivial stop
		if(damOrRiver.getDownstream().equals(getOcean())){
			completedIndexs.add(damOrRiver);
			if(tmpList != null)
				completedIndexs.addAll(tmpList);
			return completedIndexs;
		}
		// If this is the first, create the list and check the next one
		if(tmpList == null){
			tmpList = new TreeSet<Connectable>();
			tmpList.add(damOrRiver);
			return recursiveDFS(damOrRiver.getDownstream(), completedIndexs, tmpList);
		}
		// tmpList is not null
		// If damOrRiver has already been checked we can stop
		if(tmpList.contains(damOrRiver))
			return completedIndexs; // There is a loop somewhere so not valid
		// Not the first and hasn't been checked
		tmpList.add(damOrRiver);
		return recursiveDFS(damOrRiver.getDownstream(), completedIndexs, tmpList);
	}

	public List<Connectable> getRiversAndDams() {
		List<Connectable> damsNRivers = new ArrayList<Connectable>();
		damsNRivers.addAll(dams);
		damsNRivers.addAll(rivers);
		return damsNRivers;
	}
}
