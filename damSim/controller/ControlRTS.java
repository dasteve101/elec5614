package controller;

import java.util.ArrayList;
import java.util.List;

public class ControlRTS implements Runnable {
PowerDemandSensor pSensor;
List<DamSensor> damSensors;
MailBox send ;

public ControlRTS(PowerDemandSensor Psensor, List<DamSensor> dSensors,MailBox box){
	pSensor= Psensor;
	damSensors =dSensors;
	send = box;
	List<Float> waterForPower = new ArrayList();
for (int i = 0 ; i <=damSensors.size()-1; i ++){
		
	waterForPower.add((float) 0);
			
	}
	send.setWaterforPower(waterForPower);
	Thread myThread = new Thread(this);
	myThread.start();
}
private void decision (){
	List<Float> waterForPower = new ArrayList();
	float pdemand= pSensor.getPowerDemand();
	for (int i = 0 ; i <=damSensors.size()-1; i ++){
		
		if(pdemand>0){
			float waterToUse = Math.min(pdemand/damSensors.get(i).getWattsPerLitre(), damSensors.get(i).getMaxWaterForPower());
			pdemand-=waterToUse*damSensors.get(i).getWattsPerLitre();
			waterForPower.add(waterToUse);
					}
		else{
			waterForPower.add((float) 0);
		}
			
	}
	send.setWaterforPower(waterForPower);
	System.out.println("sending:"+waterForPower);
}
@Override
public void run() {
	// TODO Auto-generated method stub
	try {
	
	while (true){
		decision();
	    System.out.println("Making decision");
	    
		Thread.sleep(200);
	}
	}catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
