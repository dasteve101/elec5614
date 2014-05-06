package physicalObjects;

/*
 * This is a dam object.
 * It stores 'capacity' water, it has 'level' of water currently
 * The count is for an identifier
 * It must have a downstream where water goes when it overflows
 * Should be generalised to more than one river out?
 */
public class Dam extends Connectable{
    private int count = 0;
    private float capacity = 0;
    private float level = 0;
    private Connectable downstream;
    private boolean overflowed = false;
    private float wattsperlitre;
    private float maxWaterForPwr;
     
    /**
     * @param count
     * @param capacity
     * @param initialLevel
     * @param downstream
     * @param coeff
     * @param maxPwr
     */
    public Dam(int count, float capacity, float initialLevel, Connectable downstream, float coeff, float maxPwr){
    	this.count = count;
    	this.capacity = capacity;
    	this.level = initialLevel;
    	this.downstream = downstream;
    	this.overflowed = false;
    	this.wattsperlitre = coeff;
    	this.maxWaterForPwr = maxPwr;
    }	

    /**
     * @return
     */
    public int getID(){
    	return count;
    }
    
    /**
     * @return
     */
    public float getLevel(){
    	return level;
    }

    /**
     * @return
     */
    public float getCapacity(){
    	return capacity;
    }

    /**
     * @return
     */
    public float getRemaining(){
    	return (capacity - level);
    }

    /**
     * @return
     */
    public float getPercentage(){
    	return (level/capacity)*100;
    }
    
    // Get the amount of water overflowed from the dam
    /**
     * @return
     */
    public boolean getOverflowed(){
    	return overflowed;
    }
    
    /**
     * 
     */
    protected void resetOverflowed(){
    	overflowed = false;
    }
    
    /**
     * @return
     */
    public boolean isDry(){
    	return (level == 0);
    }
    
    /**
     * @param litres
     * @return
     */
    protected float genPower(float litres){
    	litres = this.waterOut(litres);
    	if(maxWaterForPwr >= litres)
    		return this.wattsperlitre*litres;
    	return this.wattsperlitre*maxWaterForPwr;
    }

    /**
     * @param litres
     * @return
     */
    protected float pumpOut(float litres){
    	if(level >= litres){
			level -= litres;
			return litres;	
		}
		litres = level;
		level = 0;
		return litres;
    }
    
	public void connectTo(Connectable downstream) {
		this.downstream = downstream;
	}

	public Connectable getDownstream() {
		return this.downstream;
	}

	protected float waterOut(float litres) {
		if(level >= litres){
			level -= litres;
			downstream.waterIn(litres);
			return litres;	
		}
		litres = level;
		level = 0;
		downstream.waterIn(litres);
		return litres;
	}

	protected void waterIn(float litres) {
		if((level + litres) <= capacity)
			level += litres;
		else{
			float excess = (level + litres) - capacity;
			level = capacity;
			downstream.waterIn(excess);
			this.overflowed = true;
		}
	}
	
	@Override
	public void printObj(){
		System.out.println("Dam " + count);
		System.out.println("Capacity:" + capacity);
		System.out.println("Level:" + level);
		System.out.println("Overflowed:" + overflowed);
	}
}