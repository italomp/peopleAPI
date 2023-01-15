package com.api.peopleAPI.exceptions.address;

public class AddressNotFoundException extends RuntimeException{
    public AddressNotFoundException(String msg){
        super(msg);
    }
}
