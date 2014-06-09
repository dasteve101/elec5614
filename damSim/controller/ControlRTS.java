package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import physicalObjects.*;

public class ControlRTS implements Runnable {
	
	private List<DamThread> damThreads;
	private SnowyScheme s;
	private DamThread waterSupply;
	private Thread t;
	private volatile boolean isRunning;
	private volatile float powerDemand;
	private volatile float waterDemand;
	private List<DamThread> rootDams;
	private volatile boolean initialized;
	private volatile String messageToBeDisplayed;
	
	public ControlRTS(SnowyScheme s) throws Exception{
		if(!s.validateModel())
			throw new Exception("This model is invalid! Cannot control invalid model");
		this.s = s;
		startDamThreads();
		if(t == null){
			t = new Thread(this);
			isRunning = false;
			t.start();
		}
		initialized = false;
		while(!initialized);
	}
	
	public void setPowerDemand(float p){
		powerDemand = p;
	}
	
	public void setWaterDemand(float w){
		waterDemand = w;
	}
	
	public String getMessage(){
		String tmp = messageToBeDisplayed;
		messageToBeDisplayed = null;
		return tmp;
	}
	
	private void startDamThreads(){
		damThreads = new ArrayList<DamThread>();
		rootDams = new ArrayList<DamThread>();
		for(Dam d : s.getDams()){
			List<Pipe> pipes = new ArrayList<Pipe>();
			for(Pipe p : s.getPipes()){
				if(p.getDownhill().equals(d) || p.getUphill().equals(d))
					pipes.add(p);
			}
			DamThread t = new DamThread(d, pipes);
			damThreads.add(t);
			boolean root = true;
			for(Dam d2 : s.getDams()){
				if(d2.getDownstream().getDownstream().equals(d)){
					root = false;
					break;
				}
			}
			if(root)
				rootDams.add(t);
			if(d.equals(s.getWaterSupply()))
				waterSupply = damThreads.get(damThreads.size() - 1);
		}
		for(DamThread d : damThreads){
			for(DamThread d2: damThreads){
				if(d.getDam().getDownstream().getDownstream().equals(d2.getDam())){
					d.addDownstream(d2);
					d2.addUpstream(d);
				}
			}
		}
		for(Pipe p : s.getPipes()){
			DamThread uphill = null;
			DamThread downhill = null;
			for(DamThread d : damThreads){
				if(d.getDam().equals(p.getUphill()))
					uphill = d;
				if(d.getDam().equals(p.getDownhill()))
					downhill = d;
				if(uphill != null && downhill != null)
					break;
			}
			uphill.addDownstream(downhill);
			downhill.addUpstream(uphill);
		}
		for(DamThread d : damThreads)
			d.init();
	}
	
	@Override
	public void run() {
		List<Float> waterForPowerList;
		List<Float> waterOutList;
		List<Float> pumpPowerList;
		//MessageToPass m = new MessageToPass(0);
		isRunning = true;
		while(isRunning){
			// Create a new set of lists so that the simulation doesn't use it until its ready
			waterForPowerList = new ArrayList<Float>();
			waterOutList = new ArrayList<Float>();
			pumpPowerList = new ArrayList<Float>();
			
			for(int i = 0; i < s.getDams().size(); i++){
				waterForPowerList.add((float) 0);
				waterOutList.add((float) 0);
			}
			for(int i = 0; i < s.getPipes().size(); i++){
				pumpPowerList.add((float) 0);
			}
			float predictedPower = 0;
			// Look at all the threads and fill in the values in the list
			for(DamThread d : damThreads){
				float inflow = 0; 
				for(DamThread up : d.getUpstream()){
					if(up.getDam().getDownstream().getDownstream().equals(d.getDam())){
						Connectable down = up.getDam().getDownstream();
						if(down instanceof River){
							inflow += ((River) down).getFlow();
						}
					}
					else{
						// Only other upstream can be a pipe
						Pipe betweenPipe = null;
						int index = -1;
						for(Pipe p : d.getPipes()){
							if(p.getUphill().equals(up.getDam())){
								betweenPipe = p;
								break;
							}
						}
						for(int i = 0; i < s.getPipes().size(); i++){
							if(betweenPipe.equals(s.getPipes().get(i))){
								index = i;
								break;
							}
						}
						System.out.println(up.getDam());
						System.out.println(up.getDam().getPercentage());
						System.out.println(d.getDam());
						System.out.println(d.getDam().getPercentage());						
						if(up.getDam().getPercentage() > (d.getDam().getPercentage() + 20)){
							// release water downhill so -ve
							if(up.getDam().getPercentage() > (d.getDam().getPercentage() + 50))
								pumpPowerList.set(index, -betweenPipe.getMaxWater());
							else{
								float diff = up.getDam().getPercentage() - d.getDam().getPercentage() - (float) 20;
								float waterToRel =  - (float) ((betweenPipe.getMaxWater()/30)*diff);
								pumpPowerList.set(index, waterToRel);
							}
						}
						if(up.getDam().getPercentage() < (d.getDam().getPercentage() - 20)){
							if(up.getDam().getPercentage() < (d.getDam().getPercentage() - 70))
								pumpPowerList.set(index, betweenPipe.getMaxPower());
							else{
								float diff =  - up.getDam().getPercentage() + d.getDam().getPercentage() - (float) 20;
								float powerToPump = (float) ((betweenPipe.getMaxWater()/50)*diff);
								pumpPowerList.set(index, powerToPump);
							}
						}
						
					}
				}
				MessageToPass m = new MessageToPass(0);
				m.setInflow(inflow);
				d.send(m);
			}
			
			// Collect responses from all dams
			Map<DamThread, MessageToPass> responses = new HashMap<DamThread, MessageToPass>();
			for(DamThread d : damThreads){
				try {
					responses.put(d, d.waitForCompletion());
				} catch (InterruptedException e) {
					e.printStackTrace();
					System.out.println("Could not read from sensor in dam " + d.getDam());
				}
			}
			
			for(DamThread d : damThreads){
				for(int i = 0; i < s.getDams().size(); i++){
					if(d.getDam().equals(s.getDams().get(i))){
						MessageToPass response = responses.get(d);
						if (response == null) {
							// This dam did not respond in time.
							continue;
						}
						if(d.getDam().getMaxWaterForPower() < response.getWaterOut()){
							waterForPowerList.set(i, d.getDam().getMaxWaterForPower());
							waterOutList.set(i, response.getWaterOut() - d.getDam().getMaxWaterForPower());
						}
						else
							waterForPowerList.set(i, response.getWaterOut());
						predictedPower += waterForPowerList.get(i)*d.getDam().getWattsPerLitre();
					}
				}
			}
			
			// need to look at upstream/downstream needs more or less water
			// Need to use pipes
			
			
			
			//	need to look at power/water demand
			if(waterSupply.getDam().getCapacity() < waterDemand){
				for(DamThread up: waterSupply.getUpstream()){
					// increase upstream supply by 25%
					if(up.getDam().getDownstream().getDownstream().equals(waterSupply.getDam())){
						for(int i = 0; i < s.getDams().size(); i++){
							if(up.getDam().equals(s.getDams().get(i))){
								waterOutList.set(i, (float) (waterOutList.get(i)*1.25));
								break;
							}
						}
					}
				}
				for(Pipe p : waterSupply.getPipes()){
					int index = 0;
					for(int i = 0; i < s.getPipes().size(); i++){
						if(s.getPipes().get(i).equals(p)){
							index = i;
							break;
						}
					}
					if(p.getUphill().equals(waterSupply.getDam())){
						if(pumpPowerList.get(index) <= 0)
							pumpPowerList.set(index, pumpPowerList.get(index) + p.getMaxPower());
						else
							pumpPowerList.set(index, p.getMaxPower());
					}
					else{
						if(pumpPowerList.get(index) >= 0)
							pumpPowerList.set(index, pumpPowerList.get(index) - p.getMaxWater());
						else
							pumpPowerList.set(index, -p.getMaxWater());
					}
				}
			}
			for(Float pumpP : pumpPowerList){
				if(pumpP > 0)
					predictedPower -= pumpP; 
			}
			if(predictedPower < powerDemand){
				messageToBeDisplayed = "Power Low: warning";
				// calculate power that can gain
				float extraPower = 0;
				ArrayList<Integer> powerGen = new ArrayList<Integer>();
				for(int i = 0; i < s.getDams().size(); i++){
					Dam currDam = s.getDams().get(i);
					float tmp =  currDam.getWattsPerLitre()*(currDam.getMaxWaterForPower()-waterForPowerList.get(i));
					if(tmp > 0){
						extraPower += tmp;
						powerGen.add(i);
					}
				}
				float diff = extraPower + predictedPower - powerDemand;
				if(diff < 0){
					messageToBeDisplayed += "\nCannot meet demand";
					diff = 0;
				}
				extraPower -= diff;
				// amount to increase power in each
				
				while(extraPower > 0){
					// Keep increasing evenly until the extra power is met
					int stationsRemaining = powerGen.size();
					int removeIndex = -1;
					for(int i : powerGen){
						Dam currDam = s.getDams().get(i);
						float amountToIncrease = extraPower/stationsRemaining;
						float percentageIncrease = 1 + (float) (amountToIncrease/waterForPowerList.get(i));
						if(waterForPowerList.get(i) < currDam.getMaxWaterForPower()){
							if(waterForPowerList.get(i)*percentageIncrease < currDam.getMaxWaterForPower()){
								extraPower -= (float) (waterForPowerList.get(i)*
										percentageIncrease)*currDam.getWattsPerLitre();
								waterForPowerList.set(i,(float) (waterForPowerList.get(i)*percentageIncrease));
							}
							else{
								extraPower -= (float) (currDam.getMaxWaterForPower()-waterForPowerList.get(i))*
										percentageIncrease*currDam.getWattsPerLitre();
								waterForPowerList.set(i,(float) currDam.getMaxWaterForPower());
								removeIndex = i;
							}
						}
					}
					if(removeIndex != -1){
						powerGen.remove(removeIndex);
					}
				}
				System.out.println(messageToBeDisplayed);
			}
			try{
				// send the values
				s.setWaterOut(waterOutList);
				s.setWaterForPower(waterForPowerList);
				s.setPumpPowers(pumpPowerList);
				initialized = true;
			} catch( IncorrectLengthException e){
				e.printStackTrace();
				System.out.println("Invalid list lengths: must restart damThreads");
				for(DamThread d : damThreads)
					d.stop();
				startDamThreads();
			}
		}
	}
	
	public void stop(){
		// kill all damThreads and self
		isRunning = false;
		t.interrupt();
		for(DamThread d : damThreads)
			d.stop();
	}
}
