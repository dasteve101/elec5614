
public class Dam {
	private int count = 0;
	private float capacity = 0;
	private float level = 0;
	private River outputRiver;

	public Dam(int count, float capacity, float initialLevel, River myRiver) {
		this.count = count;
		this.capacity = capacity;
		this.level = initialLevel;
		this.outputRiver = myRiver;
	}

	public boolean inputWater(float waterIn) {
		if (level + waterIn < capacity) {
			level += waterIn;
			return true;
		} else {
			float excess = level + waterIn - capacity;
			// TODO: send the excess water down the river
			return false;
		}
	}

	public boolean outputWater(float waterOut) {
		if (level > waterOut) {
			level = level - waterOut;
			return true;
		} else {
			level = 0;
			// set Dry flag?
			return false;
		}
	}

	public float getLevel() {
		return level;
	}
//Return the capacity value of the specified Dam
	public float getCapacity() {
		return capacity;
	}

	public float getRemaining() {
		return (capacity - level);
	}

	public float getPercentage() {
		return (level / capacity) * 100;
	}

	public River getOutputRiver() {
		return outputRiver;
	}

}