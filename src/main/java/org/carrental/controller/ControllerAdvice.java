package org.carrental.controller;

import org.carrental.exception.CarNotFoundException;
import org.carrental.exception.ValidationException;
import org.carrental.model.request.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException exception){
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());

        if (exception.getErrors() != null && !exception.getErrors().isEmpty()){
            errorResponse = new ErrorResponse("Validation failed", exception.getErrors());
        }


        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(CarNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(CarNotFoundException exception){
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());

        return ResponseEntity.status(404).body(errorResponse);
    }
}