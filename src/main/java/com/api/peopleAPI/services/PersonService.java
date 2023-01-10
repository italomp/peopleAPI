package com.api.peopleAPI.services;

import com.api.peopleAPI.dtos.AddressDto;
import com.api.peopleAPI.dtos.PersonDto;
import com.api.peopleAPI.exceptions.PersonNotFoundException;
import com.api.peopleAPI.models.Address;
import com.api.peopleAPI.models.Person;
import com.api.peopleAPI.repositories.PersonRepository;
import com.api.peopleAPI.utils.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private AddressService addressService;

    public PersonService(){}

    public HttpStatus savePerson(PersonDto dto){
        if(dto == null){
            return HttpStatus.BAD_REQUEST;
        }
        checkNullAttributes(dto);
        saveNewPersonAddresses(dto.getMainAddress(), dto.getAlternativeAddressList());
        Person newPerson = PersonMapper.fromDtoToPerson(dto);
        personRepository.save(newPerson);
        return HttpStatus.CREATED;
    }

    public void checkNullAttributes(PersonDto dto){
        if(dto.getName() == null)
            throw new IllegalArgumentException("Person name can't be null");
        if(dto.getBirthDate() == null)
            throw new IllegalArgumentException("Person birthdate can't be null");
    }

    // Permite que endereços repetidos sejam salvos
    public void saveNewPersonAddresses(Address mainAddres, List<Address> alternativeAddresses){
        if(mainAddres != null){
            addressService.save(mainAddres);
        }
        if(alternativeAddresses != null){
            alternativeAddresses.forEach(address -> {
                if(address != null) {
                    addressService.save(address);
                }
            });
        }
    }

    // Esse método implementará a funcionalidade de salvar um endereço para uma pessoa já cadastrada.
    public HttpStatus saveAddresForStoragedPerson(AddressDto dto) throws PersonNotFoundException{
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
