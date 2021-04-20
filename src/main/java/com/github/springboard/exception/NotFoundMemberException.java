package com.github.springboard.exception;

public class NotFoundMemberException extends RuntimeException {

    public NotFoundMemberException() {
        super();
    }

    public NotFoundMemberException(String s) {
    }

    public NotFoundMemberException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundMemberException(Throwable cause) {
        super(cause);
    }

    protected NotFoundMemberException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
