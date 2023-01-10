package com.api.peopleAPI.services;

import com.api.peopleAPI.dtos.AddressDTO;
import com.api.peopleAPI.dtos.PersonDTO;
import com.api.peopleAPI.exceptions.PersonNotFoundException;
import com.api.peopleAPI.models.Address;
import com.api.peopleAPI.models.Person;
import com.api.peopleAPI.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private AddressService addressService;

    public PersonService(){}

    public HttpStatus savePerson(PersonDTO dto){
        if(dto == null){
            return HttpStatus.BAD_REQUEST;
        }
        saveNewPersonAddresses(dto.getMainAddress(), dto.getAlternativeAddressList());
        Person newPerson = new Person(
                dto.getName(),
                LocalDate.parse(dto.getBirthDate()),
                dto.getMainAddress(),
                dto.getAlternativeAddressList());
        personRepository.save(newPerson);
        return HttpStatus.CREATED;
    }

    // Permite que endereços repetidos sejam salvos
    public void saveNewPersonAddresses(Address mainAddres, List<Address> alternativeAddresses){
        if(mainAddres != null){
            System.out.println(mainAddres.getStreet() + " - " + mainAddres.getNumber());
            addressService.save(mainAddres);
        }
        if(alternativeAddresses != null){
            alternativeAddresses.forEach(address -> {
                if(address != null) {
                    System.out.println(address.getStreet() + " - " + address.getNumber());
                    addressService.save(address);
                }
            });
        }
    }

    // Esse método implementará a funcionalidade de salvar um endereço para uma pessoa já cadastrada.
    public HttpStatus saveAddresForStoragedPerson(AddressDTO dto) throws PersonNotFoundException{
        if(dto == null){
            return HttpStatus.BAD_REQUEST;
        }
        Optional<Person> residentOpt = personRepository.findById(dto.getPersonId());
        if(residentOpt.isEmpty()){
            throw new PersonNotFoundException("There isn't user saved with entered ID");
        }
        Address newAddress = new Address(
                dto.getStreet(),
                dto.getNumber(),
                dto.getCep(),
                dto.getCity(),
                residentOpt.get());
        addressService.save(newAddress);
        return HttpStatus.CREATED;
    }
}
