package com.api.peopleAPI.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CustomError> transactionSystemExceptionHandler(Exception ex, WebRequest request){
        CustomError msgError = new CustomError(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(msgError, HttpStatus.BAD_REQUEST);
    }
}
