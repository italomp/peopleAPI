package com.api.peopleAPI.controllers;

import com.api.peopleAPI.dtos.PersonDto;
import com.api.peopleAPI.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/person")
public class PersonController {
    @Autowired
    private PersonService personService;

    public PersonController(){}

    @PostMapping
    public ResponseEntity<HttpStatus> save(@RequestBody PersonDto personDTO){
        return new ResponseEntity<>(personService.savePerson(personDTO));
    }

    @PutMapping
    public ResponseEntity<HttpStatus> update(@RequestBody PersonDto personDto){
        return new ResponseEntity<>(personService.update(personDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> getById(@PathVariable("id") String id){
        return new ResponseEntity<>(personService.getById(id), HttpStatus.OK);
    }
}
