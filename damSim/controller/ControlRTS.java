package controller;

import java.util.ArrayList;
import java.util.List;

import physicalObjects.*;

public class ControlRTS implements Runnable {
	
	private List<DamThread> damThreads;
	private SnowyScheme s;
	private DamThread waterSupply;
	private Thread t;
	private volatile boolean isRunning;
	private List<DamThread> rootDams;
	
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
		MessageToPass m = new MessageToPass(0);
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
			
			// Look at all the threads and fill in the values in the list
			for(DamThread d : rootDams){
				m.setInflow(0);
				try {
					m = d.sendWithTimeout(m);
					for(int i = 0; i < s.getDams().size(); i++){
						if(d.getDam().equals(s.getDams().get(i))){
							if(d.getDam().getMaxWaterForPower() < m.getWaterOut()){
								waterForPowerList.set(i, d.getDam().getMaxWaterForPower());
								waterOutList.set(i, m.getWaterOut() - d.getDam().getMaxWaterForPower());
							}
							else
								waterForPowerList.set(i, m.getWaterOut());
						}
					}
					// read response
				} catch (InterruptedException e) {
					e.printStackTrace();
					System.out.println("Could not read from sensor in dam " + d.getDam());
				}
			}
			
			// Logic and decision here
			
			// NB: a negative power to pump is interpreted as downhill and in litres
			
			try{
				// send the values
				s.setWaterOut(waterOutList);
				s.setWaterForPower(waterForPowerList);
				s.setPumpPowers(pumpPowerList);
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
