package com.api.peopleAPI.exceptions;

public class AddressNotBelongingToThePersonException extends RuntimeException {
    public AddressNotBelongingToThePersonException(String msg){
        super(msg);
    }
}
