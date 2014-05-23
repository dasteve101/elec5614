package controller;

import physicalObjects.*;
import java.util.concurrent.SynchronousQueue;

public class DamThread implements Runnable {

	private Dam d;
	private Thread t;
	private SynchronousQueue<MessageToPass> messages;
	
	public DamThread(Dam d){
		this.d = d;
	}
	
	@Override
	public void run() {
		// TODO implement a server calculating estimates
		while(true){
			// Wait for message [expected inflow]
			MessageToPass recieved = messages.take();
			// Calculate recommended params and set in message
			
			// Send reply [capacity, recommended output (water), estimated power]
			try {
				messages.put(recieved);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public MessageToPass sendWithTimeout(){
		
	}
	
	public Thread init(){
		if(t == null){
			t = new Thread(this);
			t.start();
		}
		return t;
	}
	
	public static void main(String[] args){
		Dam d = new Dam("Test", 0, 0, null, 0, 0);
		DamThread dt = new DamThread(d);
		Thread t = dt.init();
		
	}

}
