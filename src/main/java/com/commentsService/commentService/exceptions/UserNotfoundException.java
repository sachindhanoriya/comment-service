package com.commentsService.commentService.exceptions;

public class UserNotfoundException extends RuntimeException {
    public UserNotfoundException(String message) {
        super(message);
    }

    public UserNotfoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
