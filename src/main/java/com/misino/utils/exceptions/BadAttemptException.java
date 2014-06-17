package com.misino.utils.exceptions;

public class BadAttemptException extends Exception {
    String messageKey;
    String logMessage;

    public BadAttemptException() {

    }

    public BadAttemptException(String messageKey, String logMessage) {
        super(messageKey);
    }
}
