package exceptions;

public class InvalidRowsException extends RuntimeException{

	private static final long serialVersionUID = -465548456100058938L;
	
	public InvalidRowsException(String message) {
		super(message);
	}

}
