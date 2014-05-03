package physicalObjects;
/*
 * This file is used as a standard interface.
 * All overflow water must go somewhere and this specifies where
 */
public interface Connectable{
    
	// Where the water flows from this object
    void connectTo(Connectable downstream);

    // Get the downstream object
    Connectable getDownstream();
    
    // Send water downstream
    float waterOut(float litres);

    // Add water
    void waterIn(float litres);
}