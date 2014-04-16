
public class Channel{
    private float hardMaxdown = 0;
    private float softMaxdown = 0;
    private float wattsperlitre = 0;
    private float litresperwatt = 0;
    private float maxUp = 0;
    private boolean pumpBlown = false;
    private boolean pipeBurst = false;

    public Channel(float hardMaxdown, float softMaxdown, float coeffDownstream, float coeffUpstream, float maxUp){
	this.hardMaxdown = hardMaxdown;
	this.softMaxdown = softMaxdown;
	this.wattsperlitre = coeffDownstream;
	this.litresperwatt = coeffUpstream;
	this.maxUp = maxUp;
    }

    public float pumpUp(float watts){
	if(watts > maxUp || pumpBlown){
	    pumpBlown = true;
	    return 0;
	}
	return watts*litresperwatt;
    }

    public float genDown(float litres){
	if(litres > hardMaxDown){
	    
	}
    }

}