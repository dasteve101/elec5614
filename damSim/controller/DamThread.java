package controller;

import physicalObjects.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class DamThread implements Runnable {

	private Dam d;
	private List<Pipe> pipes;
	private Thread t;
	private SynchronousQueue<MessageToPass> messages;
	private volatile boolean isRunning;
	private List<DamThread> upstream;
	private List<DamThread> downstream;
	
	public DamThread(Dam d, List<Pipe> pipes){
		this.d = d;
		this.pipes = pipes;
		messages = new SynchronousQueue<MessageToPass>();
		isRunning = false;
		upstream = new ArrayList<DamThread>();
		downstream = new ArrayList<DamThread>();
	}
	
	public Dam getDam(){
		return d;
	}
	
	public List<Pipe> getPipes(){
		return pipes;
	}
	
	public void addUpstream(DamThread d){
		if(!upstream.contains(d))
			upstream.add(d);
	}
	
	public void addDownstream(DamThread d){
		if(!upstream.contains(d))
			upstream.add(d);
	}
	
	public List<DamThread> getUpstream(){
		return upstream;
	}
	
	public List<DamThread> getDownstream(){
		return downstream;
	}
	
	private float calculateOut(float min, float max, float delta, float k, float l){
		/*
		 * linear for points below, then inverse tan, then linear above
		 */
		if(l <= (0.5 - delta)){
			return (min/((float)0.5-delta))*l;
		}
		if(l >= (0.5 + delta)){
			return (min/((float)0.5-delta))*l + max - ((float)0.5+delta)*(min/((float)0.5-delta));
		}
		// Else within valid range so use inv tan
		float A = (float) ((max - min)/(2*Math.atan(delta/k)));
		float C = (max + min)/2;
		return (float) (A*Math.atan((l-0.5)/k) + C);
	}

	@Override
	public void run() {
		MessageToPass recieved;
		isRunning = true;
		float delta = (float) 0.45; // 0 < delta < 0.5
		float k = (float) 0.1; // Control the sharpness/flatness
		while(isRunning){
			try {
				// Wait for message [expected inflow]
				recieved = messages.take();
				MessageToPass response = new MessageToPass(0);
				// Calculate recommended params and set in message
				response.setCapacity(d.getCapacity());
				float recommendedOut = 0;
				float level = (d.getLevel() + response.getInflow())/d.getCapacity();
				if (d.getDownstream() instanceof River){
					// River downstream
					River downstream = ((River)d.getDownstream());
					recommendedOut = calculateOut(downstream.getMin(), downstream.getMax(), delta, k, level);
				}
				else{
					// This shouldn't happen but just in case ...
					Dam downstream = ((Dam)d.getDownstream());
					recommendedOut = calculateOut(0, (d.getCapacity() - d.getLevel())/100, delta, k, level);
				}
				if (recommendedOut < 0)
					recommendedOut = 0;
				float estimatedPower = 0;
				if(d.getMaxWaterForPower() > recommendedOut)
					estimatedPower = recommendedOut * d.getWattsPerLitre();
				else
					estimatedPower = d.getMaxWaterForPower()* d.getWattsPerLitre();
				response.setPowerOut(estimatedPower);
				response.setWaterOut(recommendedOut);
				// Send reply [capacity, recommended output (water), estimated power]
				messages.offer(response, 10, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void send(MessageToPass m) {
		messages.offer(m);
	}
	
	public MessageToPass waitForCompletion() throws InterruptedException {
		return messages.poll(10, TimeUnit.MILLISECONDS);
	}
	
	public Thread init(){
		if(t == null){
			t = new Thread(this);
			t.start();
		}
		return t;
	}
	
	public void stop(){
		isRunning = false;
		t.interrupt();
	}
}
