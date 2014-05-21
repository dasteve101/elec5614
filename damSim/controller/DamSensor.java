package controller;

import physicalObjects.Dam;

public class DamSensor {
Dam dam;	
public DamSensor(Dam dam){
	this.dam= dam;
}
public float getMaxWaterForPower(){
	return dam.getMaxWaterForPower();
	
}
}
