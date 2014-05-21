package controller;

import java.util.ArrayList;
import java.util.List;

public class ControlRTS {
PowerDemandSensor pSensor;
List<DamSensor> damSensors;
public ControlRTS(PowerDemandSensor Psensor, List<DamSensor> dSensors){
	pSensor= Psensor;
	damSensors =dSensors;
	
}
public void decision (){
	List<Float> waterForPower = new ArrayList();
	float pdemand= pSensor.getPowerDemand();
	for (int i = 0 ; i <=damSensors.size(); i ++){
		
		if(pdemand>0){
			float waterToUse = Math.min(pdemand, damSensors.get(i).getMaxWaterForPower());
			pdemand-=waterToUse;
			waterForPower.add(waterToUse);
					}
		else{
			waterForPower.add((float) 0);
		}
			
	}
}
}
