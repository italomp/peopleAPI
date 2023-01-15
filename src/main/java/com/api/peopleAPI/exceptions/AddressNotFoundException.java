package com.api.peopleAPI.exceptions;

public class AddressNotFoundException extends RuntimeException{
    public AddressNotFoundException(String msg){
        super(msg);
    }
}
