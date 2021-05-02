package com.github.springboard.exception;

public class NotFoundPostException extends RuntimeException {

    public NotFoundPostException() {
        super();
    }

    public NotFoundPostException(String message) {
        super(message);
    }

    public NotFoundPostException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundPostException(Throwable cause) {
        super(cause);
    }

    protected NotFoundPostException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
