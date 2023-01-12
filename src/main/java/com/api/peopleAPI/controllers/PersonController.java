package com.api.peopleAPI.controllers;

import com.api.peopleAPI.dtos.PersonDto;
import com.api.peopleAPI.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/person")
public class PersonController {
    @Autowired
    private PersonService personService;

    public PersonController(){}

    @PostMapping
    public ResponseEntity<HttpStatus> save(@RequestBody PersonDto dto){
        return new ResponseEntity<>(personService.savePerson(dto));
    }

    @PutMapping
    public ResponseEntity<HttpStatus> update(@RequestBody PersonDto dto){
        return new ResponseEntity<>(personService.update(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> getById(@PathVariable("id") long id){
        return new ResponseEntity<>(personService.getById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PersonDto>> getAll(){
        return new ResponseEntity<>(personService.getAll(), HttpStatus.OK);
    }
}
