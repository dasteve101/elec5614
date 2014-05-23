package controller;

public class MessageToPass {
	private float inflow;
	private float capacity;
	private float waterOut;
	private float powerOut;
	
	public MessageToPass(float inflow){
		this.inflow = inflow;
	}
	
	public float getInflow(){
		return inflow;
	}

	public float getCapacity() {
		return capacity;
	}

	public void setCapacity(float capacity) {
		this.capacity = capacity;
	}

	public float getWaterOut() {
		return waterOut;
	}

	public void setWaterOut(float waterOut) {
		this.waterOut = waterOut;
	}

	public float getPowerOut() {
		return powerOut;
	}

	public void setPowerOut(float powerOut) {
		this.powerOut = powerOut;
	}
	
}
