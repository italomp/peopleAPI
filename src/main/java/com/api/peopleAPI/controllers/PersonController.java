package com.api.peopleAPI.controllers;

import com.api.peopleAPI.dtos.PersonDTO;
import com.api.peopleAPI.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/person")
public class PersonController {
    @Autowired
    private PersonService personService;

    public PersonController(){}

    @PostMapping
    public ResponseEntity<HttpStatus> save(@RequestBody PersonDTO personDTO){
        return new ResponseEntity<>(personService.savePerson(personDTO));
    }
}
