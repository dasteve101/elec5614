package controller;

import physicalObjects.SnowyScheme;

public class PowerDemandSensor {
	SnowyScheme snow;
	public PowerDemandSensor(SnowyScheme scheme) {
	snow = scheme;
	}
	public float  getPowerDemand(){
		return snow.getPowerDemand();	
		}
	
}
