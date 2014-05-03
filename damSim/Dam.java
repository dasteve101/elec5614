/*
 * This is a dam object.
 * It stores 'capacity' water, it has 'level' of water currently
 * The count is for an identifier
 * It must have a downstream where water goes when it overflows
 */
public class Dam implements Connectable{
    private int count = 0;
    private float capacity = 0;
    private float level = 0;
    private Connectable downstream;
    private boolean overflowed = false;
     
    public Dam(int count, float capacity, float initialLevel, Connectable downstream){
    	this.count = count;
    	this.capacity = capacity;
    	this.level = initialLevel;
    	this.downstream = downstream;
    	this.overflowed = false;
    }	

    public int getID(){
    	return count;
    }
    
    public float getLevel(){
    	return level;
    }

    public float getCapacity(){
    	return capacity;
    }

    public float getRemaining(){
    	return (capacity - level);
    }

    public float getPercentage(){
    	return (level/capacity)*100;
    }
    
    // Get the amount of water overflowed from the dam
    public boolean getOverflowed(){
    	return overflowed;
    }
    
    public void resetOverflowed(){
    	overflowed = false;
    }
    
    public boolean isDry(){
    	return (level == 0);
    }

	@Override
	public void connectTo(Connectable downstream) {
		this.downstream = downstream;
	}

	@Override
	public Connectable getDownstream() {
		return this.downstream;
	}

	@Override
	public float waterOut(float litres) {
		if(level >= litres){
			level -= litres;
			return litres;	
		}
		litres = level;
		level = 0;
		return litres;
	}

	@Override
	public void waterIn(float litres) {
		if((level + litres) <= capacity)
			level += litres;
		else{
			float excess = (level + litres) - capacity;
			level = capacity;
			downstream.waterIn(excess);
			this.overflowed = true;
		}
	}
}