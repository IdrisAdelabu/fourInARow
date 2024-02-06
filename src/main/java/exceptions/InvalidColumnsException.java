package exceptions;

public class InvalidColumnsException extends RuntimeException{

	private static final long serialVersionUID = 8615181924030846908L;
	
	public InvalidColumnsException(String message) {
		super(message);
	}

}
