

public class River{
    private float max;         // Max L/s
    private float min;         // Min L/s
    private float flow;        // Litres/sec
    private Destination out;   // Where the water goes

    public River(float max, float min, float initialFlow){
	this.max = max;
	this.min = min;
	this.flow = initialFlow;
    }

    public float getFlow(void){
	return flow;
    }

    public boolean setFlow(float newFlow){
	flow = newFlow;
	if(flow > max || flow < min)
	    return false;
	return true;
    }

    public boolean isInDrought(void){
	if( flow < min )
	    return true;
	return false;
    }

    public boolean isInFlood(void){
	if( flow > max )
	    return true;
	return false;
    }

    public boolean sendToDest(void){
	return out.inputWater(flow);
    }
}