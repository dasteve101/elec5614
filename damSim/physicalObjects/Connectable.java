package physicalObjects;
/*
 * This file is used as a standard interface.
 * All overflow water must go somewhere and this specifies where
 */
/**
 * 
 *
 */
public abstract class Connectable{
    
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
     * @param litres
     */
    protected abstract void waterIn(float litres);
    
    /**
     * @param litres
     * @return
     */
    protected abstract float waterOut(float litres);
    
    /**
     * 
     */
    public abstract void printObj();
}