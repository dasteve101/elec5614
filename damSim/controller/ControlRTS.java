package controller;

import java.util.ArrayList;
import java.util.List;

import physicalObjects.*;

public class ControlRTS implements Runnable {
/*
	PowerDemandSensor pSensor;
	List<DamSensor> damSensors;
	MailBox send;

	public ControlRTS(PowerDemandSensor Psensor, List<DamSensor> dSensors,
			MailBox box) {
		pSensor = Psensor;
		damSensors = dSensors;
		send = box;
		List<Float> waterForPower = new ArrayList();
		for (int i = 0; i <= damSensors.size() - 1; i++) {
			waterForPower.add((float) 0);
		}
		send.setWaterforPower(waterForPower);
		Thread myThread = new Thread(this);
		myThread.start();
	}

	private void decision() {
		List<Float> waterForPower = new ArrayList();
		float pdemand = pSensor.getPowerDemand();
		for (int i = 0; i <= damSensors.size() - 1; i++) {
			if (pdemand > 0) {
				float waterToUse = Math.min(pdemand
						/ damSensors.get(i).getWattsPerLitre(),
						damSensors.get(i).getMaxWaterForPower());
				pdemand -= waterToUse * damSensors.get(i).getWattsPerLitre();
				waterForPower.add(waterToUse);
			} else {
				waterForPower.add((float) 0);
			}
		}
		send.setWaterforPower(waterForPower);
		System.out.println("sending:" + waterForPower);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {

			while (true) {
				decision();
				System.out.println("Making decision");

				Thread.sleep(200);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
 */

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
			// set downstream and upstream for belief prop
		}
		for(DamThread d : damThreads)
			d.init();
	}
	
	private MessageToPass beliefProp(DamThread t, MessageToPass m){
		
		
		
		t.getDam();
		
		return m;
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
			
			// Look at all the threads and fill in the values in the list
			for(DamThread d : rootDams){
				m.setInflow(0);
				
				try {
					m = d.sendWithTimeout(m);
					beliefProp(d,m);
					// read response
				} catch (InterruptedException e) {
					e.printStackTrace();
					System.out.println("Could not read from sensor in dam " + d.getDam());
				}
			}
			
			// Logic and decision here
			
			// Maybe should do belief propagation???
			// Let the dams send messages to each other and converge to solution
			
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
