package physicalObjects;

public class IncorrectLengthException extends Exception {

	public IncorrectLengthException(int expected, int actual){
		super("Expected length of " + expected + " but got a length of " + actual);
	}
	
}
