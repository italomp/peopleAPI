package com.api.peopleAPI.exceptions;

public class PersonNotFoundException extends RuntimeException{
    public PersonNotFoundException(String msg){
        super(msg);
    }
}
