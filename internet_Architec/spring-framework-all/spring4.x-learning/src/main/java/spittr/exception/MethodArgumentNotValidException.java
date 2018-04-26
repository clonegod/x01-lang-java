package spittr.exception;

public class MethodArgumentNotValidException extends RuntimeException {

	/**
	 * @ControllerAdvice with some central exception handling for my api. 
	 * One of the exceptions it handles is the MethodArgumentNotValidException 
	 * 	which gets thrown when a request method parameter annotated with @Valid, fails validation.
	 */
	private static final long serialVersionUID = 2088421579093053802L;

	public MethodArgumentNotValidException(String message, Throwable cause) {
		super(message, cause);
	}

	public MethodArgumentNotValidException(String message) {
		super(message);
	}

	
}
