

public interface Connectable{
    
    void connectTo(Connectable downstream);

    Connectable getDownstream(void);

}