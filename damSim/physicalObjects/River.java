package physicalObjects;

/*
 * This is the river object. Water flows into it from the dams
 * The river has a 'length' which is how far water flows
 * The flow is a moving average. There is a max (flood) and min (drought)
 * flow in the river. The river sums in 'tmpSum' the water in for that timestep
 * and adjusts the flow each time Step.
 */
public class River implements Connectable {
    private float max;         // Max L/s
    private float min;         // Min L/s
    private float flow;        // Litres/sec
    private Connectable out;    // Where the water goes
    private float length;      // Water travels 1 unit length/sec
    private float tmpSum = 0;  // Sum all the water for this timestep
    
    public River(float max, float min, float initialFlow, float length){
    	this.max = max;
    	this.min = min;
    	this.flow = initialFlow;
    	this.length = length;
    	out = null;
    }

    public River(float max, float min, float initialFlow, float length, Connectable out){
    	this.max = max;
    	this.min = min;
    	this.flow = initialFlow;
    	this.length = length;
    	this.out = out;
    }
    
    public float getFlow(){
    	return flow;
    }

    public boolean setFlow(float newFlow){
    	flow = newFlow;
    	if(flow > max || flow < min)
    		return false;
    	return 	true;
    }

    public boolean isInDrought(){
    	if( flow < min )
    		return true;
    	return false;
    }

    public boolean isInFlood(){
		if( flow > max )
		    return true;
		return false;
    }
    
    public void timeStep(){
    	this.waterOut(flow);
    	flow = (tmpSum + flow*(length - 1))/length;
    	tmpSum = 0;
    }

	@Override
	public void connectTo(Connectable downstream) {
		this.out = downstream;
	}

	@Override
	public Connectable getDownstream() {
		return this.out;
	}

	@Override
	public float waterOut(float litres) {
		if(out != null)
			out.waterIn(litres);
		return litres;
	}

	@Override
	public void waterIn(float litres) {
		tmpSum += litres;
	}
}