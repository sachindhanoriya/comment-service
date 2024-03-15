package com.commentsService.commentService.exceptions;

public class CommentNotfoundException extends RuntimeException {
    public CommentNotfoundException(String message) {
        super(message);
    }

    public CommentNotfoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
