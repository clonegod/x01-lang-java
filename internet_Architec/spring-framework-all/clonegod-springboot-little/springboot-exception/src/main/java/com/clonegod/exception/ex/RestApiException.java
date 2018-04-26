package com.clonegod.exception.ex;

public class RestApiException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4824631296079267756L;

	public RestApiException() {
		super();
	}

	public RestApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RestApiException(String message, Throwable cause) {
		super(message, cause);
	}

	public RestApiException(String message) {
		super(message);
	}

	public RestApiException(Throwable cause) {
		super(cause);
	}

	
}
