package com.api.peopleAPI.controllers;

import com.api.peopleAPI.dtos.AddressDto;
import com.api.peopleAPI.dtos.PersonDto;
import com.api.peopleAPI.services.AddressService;
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
    @Autowired
    private AddressService addressService;

    public PersonController(){}

    @PostMapping
    public ResponseEntity<HttpStatus> save(@RequestBody PersonDto dto){
        return new ResponseEntity<>(personService.savePerson(dto));
    }

    @PostMapping("/{id}")
    public ResponseEntity<HttpStatus> savePersonAddress(
            @PathVariable("id") long personId, @RequestBody AddressDto addressDto
    ){
        return new ResponseEntity<>(personService.savePersonAddress(personId, addressDto));
    }

    @PutMapping
    public ResponseEntity<HttpStatus> update(@RequestBody PersonDto dto){
        return new ResponseEntity<>(personService.update(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> setMainAddressOfPerson(
            @PathVariable("id") long personId, @RequestParam("address_id") long addressId
    ){
        return new ResponseEntity<>(personService.setMainAddressOfPerson(personId, addressId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> getById(@PathVariable("id") long id){
        return new ResponseEntity<>(personService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/address")
    public ResponseEntity<List<AddressDto>> getAllAddressOfPerson(@PathVariable("id") long personId){
        return new ResponseEntity<>(addressService.getAllAddressOfPerson(personId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PersonDto>> getAll(){
        return new ResponseEntity<>(personService.getAll(), HttpStatus.OK);
    }
}
