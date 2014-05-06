package physicalObjects;
/*
 * This file is used as a standard interface.
 * All overflow water must go somewhere and this specifies where
 */
/**
 * @author stephen
 *
 */
public interface Connectable{
    
    /**
     * Where the water flows from this object
     * @param downstream
     */
    void connectTo(Connectable downstream);

    /**
     * Get the downstream object
     * @return
     */
    Connectable getDownstream();
    
    /**
     * Send water downstream
     * @param litres
     * @return
     */
    float waterOut(float litres);
    
    /**
     * Add water
     * @param litres
     */
    void waterIn(float litres);
    
    /**
     * 
     */
    void printObj();
}