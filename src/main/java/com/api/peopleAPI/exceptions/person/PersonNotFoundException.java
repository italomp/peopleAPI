package com.api.peopleAPI.exceptions.person;

public class PersonNotFoundException extends RuntimeException{
    public PersonNotFoundException(String msg){
        super(msg);
    }
}
