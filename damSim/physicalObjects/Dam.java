package physicalObjects;

/*
 * This is a dam object.
 * It stores 'capacity' water, it has 'level' of water currently in the dam
 * The count is for an identifier
 * It must have a downstream where water goes when it overflows
 * Should be generalised to more than one river out?
 */
public class Dam extends Connectable {
	private int count = 0;
	private float capacity = 0;
	private float level = 0;
	private Connectable downstream;
	private boolean overflowed = false;
	private float wattsperlitre;
	private float maxWaterForPwr;

	/**
	 * @param count - Variable used as an identifier.
	 * @param capacity - The maximum water capacity of the dam.
	 * @param initialLevel - The water level of the dam starts with this value.
	 * @param downstream - Variable representing where water flows when the dam is overflowing.
	 * @param coeff - Coefficient of the power-water equation (used to determine power generated per litre of water).
	 * @param maxPwr - max power the generator in the dam can produce 
	 */
	public Dam(int count, float capacity, float initialLevel,
			Connectable downstream, float coeff, float maxPwr) {
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
	public int getID() {
		return count;
	}

	/**
	 * @return
	 */
	public float getLevel() {
		return level;
	}

	/**
	 * @return
	 */
	public float getCapacity() {
		return capacity;
	}

	/**
	 * @return
	 */
	public float getRemaining() {
		return (capacity - level);
	}

	/**
	 * @return
	 */
	public float getPercentage() {
		return (level / capacity) * 100;
	}

	/**
	 * Get the amount of water overflowed from the dam.
	 * 
	 * @return
	 */
	public boolean getOverflowed() {
		return overflowed;
	}

	/**
     * 
     */
	protected void resetOverflowed() {
		overflowed = false;
	}

	/**
	 * Determine if the dam is dry.
	 * 
	 * @return
	 */
	public boolean isDry() {
		return (level == 0);
	}
	
	public float getMaxWaterForPower(){
		return maxWaterForPwr;
	}
	
	public float getWattsPerLitre(){
		return wattsperlitre;
	}

	/**
	 * Generate power based on the amount of litres released.
	 * 
	 * @param litres - Amount of water to be released.
	 * @return
	 */
	protected float genPower(float litres) {
		litres = this.waterOut(litres); // Determine how many litres can be
										// released (either litres or the amount
										// left in the dam).
		if (maxWaterForPwr >= litres) 	// If the maximum amount of water that can
										// be released for power generation is
										// not exceeded,
			return this.wattsperlitre * litres; // then return the power
												// generated from releasing
												// "litres" amount of water.
		return this.wattsperlitre * maxWaterForPwr; // Otherwise, return the
													// power generated from
													// releasing the maximum
													// amount of water.
	}

	/**
	 * Release water from the dam. Amount release is based on litres.
	 * 
	 * @param litres
	 * @return
	 */
	protected float pumpOut(float litres) {
		if (level >= litres) { 	// If the current level of the dam is greater
								// than or equal to the amount to be released,
			level -= litres; // then decrease the level of the dam by litres,
			return litres; // and return the amount of water released.
		}
		litres = level; // Otherwise, the remaining amount of water is released
						// (i.e. the value of level)
		level = 0; // Set the level to 0 (i.e. empty dam).
		return litres; 	// Return the amount of water released (i.e. the value of
						// level).
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
	protected float waterOut(float litres) {
		if (level >= litres) { // If the level of the dam is greater than or equal to the amount that is to be released,
			level -= litres; // then decrease the level of the dam by 'litres',
			downstream.waterIn(litres); // send the water down stream via the connector,
			return litres; // and return the amount of water released.
		}
		litres = level;		// Otherwise, release the remaining amount of water found in the dam,
		level = 0;			// set the level of the dam to 0,
		downstream.waterIn(litres); // send the water down stream via the connector,
		return litres;		// and return the amount of water that was released.
	}
	
	/**
	 * @param litres - Amount of water to be stored in the dam.
	 */
	@Override
	protected void waterIn(float litres) {
		if ((level + litres) <= capacity)	// If the sum of the water sent into the dam and the level of the dam is not greater than the capacity of the dam,
			level += litres;				// then the level of the dam is increased by the value of 'litres'.
		else {											// Otherwise,
			float excess = (level + litres) - capacity;	// determine the amount of excess water,
			level = capacity;							// set the dam level to capacity,
			downstream.waterIn(excess);					// send the excess water down stream via the connector,
			this.overflowed = true;						// and indicate that the dam overflowed.
		}
	}

	@Override
	public String toString() {
		String val = "Dam " + count;
		val += "\nCapacity:" + capacity;
		val += "\nLevel:" + level;
		val += "\nOverflowed:" + overflowed;
		return val;
	}
}