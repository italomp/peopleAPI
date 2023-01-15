package com.api.peopleAPI.exceptions.address;

public class AddressNotBelongingToThePersonException extends RuntimeException {
    public AddressNotBelongingToThePersonException(String msg){
        super(msg);
    }
}
