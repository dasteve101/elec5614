package controller;

import physicalObjects.*;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class DamThread implements Runnable {

	private Dam d;
	private Thread t;
	private SynchronousQueue<MessageToPass> messages;
	private volatile boolean isRunning;
	
	public DamThread(Dam d){
		this.d = d;
		messages = new SynchronousQueue<MessageToPass>();
		isRunning = false;
	}
	
	public Dam getDam(){
		return d;
	}

	@Override
	public void run() {
		MessageToPass recieved;
		isRunning = true;
		while(isRunning){
			try {
				// Wait for message [expected inflow]
				recieved = messages.take();
				// Calculate recommended params and set in message
				recieved.setCapacity(d.getCapacity());
				float recommendedOut = 0;
				if (d.getDownstream() instanceof River){
					// River downstream
					River downstream = ((River)d.getDownstream());
					float averageFlow = (downstream.getMax() + downstream.getMin())/2;
					recommendedOut = averageFlow + recieved.getInflow() +
							((d.getPercentage() - 50)/50)*(downstream.getMax() - downstream.getMin());
				}
				else{
					Dam downstream = ((Dam)d.getDownstream());
					recommendedOut = d.getCapacity()*(d.getPercentage() - 50)/(5*(downstream.getPercentage() + 1));
				}
				if (recommendedOut < 0)
					recommendedOut = 0;
				float estimatedPower = 0;
				if(d.getMaxWaterForPower() > recommendedOut)
					estimatedPower = recommendedOut * d.getWattsPerLitre();
				else
					estimatedPower = d.getMaxWaterForPower()* d.getWattsPerLitre();
				recieved.setPowerOut(estimatedPower);
				recieved.setWaterOut(recommendedOut);
				// Send reply [capacity, recommended output (water), estimated power]
				messages.offer(recieved, 1, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public MessageToPass sendWithTimeout(MessageToPass m) throws InterruptedException {
		messages.offer(m);
		return messages.poll(1, TimeUnit.MILLISECONDS);
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
	
	public static void main(String[] args){
		Dam d = new Dam("Test", 0, 0, null, 0, 0);
		DamThread dt = new DamThread(d);
		Thread t = dt.init();
	}

}
