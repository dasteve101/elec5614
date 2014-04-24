
/*
 * This file is used as a standard interface.
 * All overflow water must go somewhere and this is where
 */
public interface Connectable{
    
	// Where the water flows from this object
    void connectTo(Connectable downstream);

    // Get the downstream object
    Connectable getDownstream();
    
    // Send water downstream
    void waterOut(float litres);

    // Add water
    void waterIn(float litres);
}