package physicalObjects;

/*
 * This is the river object. Water flows into it from the dams
 * The river has a 'length' which is how far water flows
 * The flow is a moving average. There is a max (flood) and min (drought)
 * flow in the river. The river sums in 'tmpSum' the water in for that timestep
 * and adjusts the flow each time Step.
 */
/**
 * @author stephen
 *
 */
public class River extends Connectable {
	private int count;
    private float max;         // Max L/s
    private float min;         // Min L/s
    private float flow;        // Litres/sec
    private Connectable out;    // Where the water goes
    private float length;      // Water travels 1 unit length/sec
    private float tmpSum = 0;  // Sum all the water for this timestep
    
    /**
     * @param count
     * @param max
     * @param min
     * @param initialFlow
     * @param length
     */
    public River(int count, float max, float min, float initialFlow, float length){
    	this.count =  count;
    	this.max = max;
    	this.min = min;
    	this.flow = initialFlow;
    	this.length = length;
    	out = null;
    }

    /**
     * @param count
     * @param max
     * @param min
     * @param initialFlow
     * @param length
     * @param out
     */
    public River(int count, float max, float min, float initialFlow, float length, Connectable out){
    	this.count = count;
    	this.max = max;
    	this.min = min;
    	this.flow = initialFlow;
    	this.length = length;
    	this.out = out;
    }
    
    /**
     * @return
     */
    public float getFlow(){
    	return flow;
    }

    /**
     * @return
     */
    public boolean isInDrought(){
    	if( flow < min )
    		return true;
    	return false;
    }

    /**
     * @return
     */
    public boolean isInFlood(){
		if( flow > max )
		    return true;
		return false;
    }
    
    /**
     * 
     */
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
	
	public void printObj(){
		System.out.println("River " + count);
		System.out.println("Max:" + max);
		System.out.println("Min:" + min);
		System.out.println("Length:" + length);
		System.out.println("Flow:" + flow);
	}
}