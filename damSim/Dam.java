public class Dam implements Connectable{
    private int count = 0;
    private float capacity = 0;
    private float level = 0;
    private Connectable downstream;
    private boolean overflowed = false;
    
    public Dam(int count, float capacity, float initialLevel){
    	this.count = count;
    	this.capacity = capacity;
    	this.level = initialLevel;
    	this.downstream = null;
    	this.overflowed = false;
    }
    
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
	public void waterOut(float litres) {
		if(level >= litres){
			level -= litres;
			downstream.waterIn(litres);	
		}
		else{
			downstream.waterIn(level);
			level = 0;			
		}
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