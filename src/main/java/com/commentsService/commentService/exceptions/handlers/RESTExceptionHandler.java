package com.commentsService.commentService.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.commentsService.commentService.controllers.errorResponse.ErrorResponse;
import com.commentsService.commentService.exceptions.CommentNotfoundException;
import com.commentsService.commentService.exceptions.InvalidIdFormatException;
import com.commentsService.commentService.exceptions.UserNotfoundException;

@ControllerAdvice
public class RESTExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(InvalidIdFormatException e) {
        ErrorResponse error = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(CommentNotfoundException e) {
        ErrorResponse error = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(UserNotfoundException e) {
        ErrorResponse error = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorResponse error = new ErrorResponse();
        error.setMessage(e.getMessage());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
