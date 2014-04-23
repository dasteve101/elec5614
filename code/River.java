
public class River {
	private float max; // Max L/s
	private float min; // Min L/s
	private float flow; // Litres/sec
	private Destination out; // Where the water goes

	public River(float max, float min, float initialFlow) {
		this.max = max;
		this.min = min;
		this.flow = initialFlow;
	}

	public float getFlow() {
		return flow;
	}

	public boolean setFlow(float newFlow) {
		flow = newFlow;
		if (flow > max || flow < min)
			return false;
		return true;
	}

	public boolean isInDrought() {
		if (flow < min)
			return true;
		return false;
	}

	public boolean isInFlood() {
		if (flow > max)
			return true;
		return false;
	}

	public boolean sendToDest() {
		return out.inputWater(flow);
	}
}