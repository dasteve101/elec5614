package physicalObjects;
/*
 * This file is used as a standard interface.
 * All overflow water must go somewhere and this specifies where
 */
/**
 * @author stephen
 */
public abstract class Connectable implements Comparable<Connectable>{

	private String name;
	
	public Connectable(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
    /**
     * Where the water flows from this object
     * @param downstream
     */
    public abstract void connectTo(Connectable downstream);

    /**
     * Get the downstream object
     * @return
     */
    public abstract Connectable getDownstream();
    
    /**
     * Send water downstream
     * @param litres
     * @return
     */
    protected abstract float waterOut(float litres);
    
    /**
     * Add water
     * @param litres
     */
    protected abstract void waterIn(float litres);
    
    /**
     * @return 
     */
    public abstract String toString();
    
	public int compareTo(Connectable arg0) {
    	return this.hashCode() - arg0.hashCode();
	}
}