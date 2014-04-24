
public class River implements Connectable, Incrementable {
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
    	flow *= (length - 1)/length;
    	flow += tmpSum;
    	tmpSum = 0;
    	this.waterOut(flow);
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
	public void waterOut(float litres) {
		out.waterIn(litres);
	}

	@Override
	public void waterIn(float litres) {
		tmpSum += litres;
	}
}