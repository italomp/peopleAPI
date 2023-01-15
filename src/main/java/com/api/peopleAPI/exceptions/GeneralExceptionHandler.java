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
    public ResponseEntity<CustomError> illegalArgumentExceptionHandler(Exception ex, WebRequest request){
        CustomError msgError = new CustomError(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(msgError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<CustomError> personNotFoundExceptionHandler(Exception ex, WebRequest request){
        CustomError msgError = new CustomError(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(msgError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<CustomError> addressNotFoundExceptionHandler(Exception ex, WebRequest request){
        CustomError msgError = new CustomError(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(msgError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AddressNotBelongingToThePersonException.class)
    public ResponseEntity<CustomError> addressNotBelongingToThePersonExceptionHandler(Exception ex, WebRequest request){
        CustomError msgError = new CustomError(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(msgError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomError> exceptionHandler(Exception ex, WebRequest request){
        CustomError msgError = new CustomError(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(msgError, HttpStatus.BAD_REQUEST);
    }
}
