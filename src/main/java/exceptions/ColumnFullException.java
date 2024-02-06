package exceptions;

public class ColumnFullException extends RuntimeException{

	private static final long serialVersionUID = -6881886435550267719L;
	
	public ColumnFullException(String message) {
		super(message);
	}
}
