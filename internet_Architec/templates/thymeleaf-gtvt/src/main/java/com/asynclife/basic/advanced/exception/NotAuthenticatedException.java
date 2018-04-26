package com.asynclife.basic.advanced.exception;

public class NotAuthenticatedException extends RuntimeException {

    private static final long serialVersionUID = 2079235381336055509L;

    public NotAuthenticatedException() {
        super();
    }

    public NotAuthenticatedException(final String message) {
        super(message);
    }

    public NotAuthenticatedException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
