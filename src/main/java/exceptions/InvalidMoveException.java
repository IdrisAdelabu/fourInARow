package exceptions;

public class InvalidMoveException extends RuntimeException {

	private static final long serialVersionUID = -1563043495797214749L;
	
	public InvalidMoveException(String message) {
		super(message);
	}

}
