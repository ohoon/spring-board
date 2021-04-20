package com.github.springboard.exception;

public class DuplicateMemberException extends RuntimeException {

    public DuplicateMemberException() {
        super();
    }

    public DuplicateMemberException(String s) {
    }

    public DuplicateMemberException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateMemberException(Throwable cause) {
        super(cause);
    }

    protected DuplicateMemberException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
