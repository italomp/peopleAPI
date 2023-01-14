package com.api.peopleAPI.services;

import com.api.peopleAPI.dtos.PersonDto;
import com.api.peopleAPI.exceptions.PersonNotFoundException;
import com.api.peopleAPI.models.Address;
import com.api.peopleAPI.models.Person;
import com.api.peopleAPI.repositories.PersonRepository;
import com.api.peopleAPI.utils.AddressMapper;
import com.api.peopleAPI.utils.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        Person person = PersonMapper.fromDtoToPerson(dto);
        removeReceivedAddressDuplicate(person);
        saveChecks(person);
        saveNewPersonAddresses(person.getMainAddress(), person.getAlternativeAddressList());
        personRepository.save(person);
        return HttpStatus.CREATED;
    }

    public void saveChecks(Person person){
        List<Address> existentAddressList = addressService.getAll();
        nullAttributesCheck(person);
        if(existentAddressList.isEmpty()) return;
        setMainAddressOfPerson(existentAddressList, person);
        setAlternativeAddressOfPerson(existentAddressList, person);
    }

    public void updateChecks(Person person, Address mainAddress, List<Address> alternativeAddressList){
        List<Address> existentAddressList = addressService.getAll();
        nullAttributesCheck(person);
        if(existentAddressList.isEmpty()) return;
        setMainAddressOfPerson(existentAddressList, mainAddress, person);
        setAlternativeAddressOfPerson(existentAddressList, alternativeAddressList, person);
    }


    public void nullAttributesCheck(Person dto){
        if(dto.getName() == null) {
            throw new IllegalArgumentException("Person name can't be null");
        }
        if(dto.getBirthdate() == null) {
            throw new IllegalArgumentException("Person birthdate can't be null");
        }
    }

    // Check if the main address of PERSON exist in database and link it to the person
    public void setMainAddressOfPerson(List<Address> existentAddressList, Person person){
        Address mainAddress = addressService.getStoragedMainAddress(existentAddressList, person.getMainAddress());
        if(mainAddress != null){
            person.setMainAddress(mainAddress);
        }
    }

    // Check if a main address (without resident) exist in database and link it to the person
    public void setMainAddressOfPerson(List<Address> existentAddressList, Address mainAddress, Person person){
        mainAddress = addressService.getStoragedMainAddress(existentAddressList, mainAddress);
        if(mainAddress != null){
            person.setMainAddress(mainAddress);
        }
    }

    // Check if the alternative addresses of PERSON exist in database and link it to the person
    public void setAlternativeAddressOfPerson(List<Address> existentAddressList, Person person){
        List<Address> storagedAlternativeAddressList = addressService.getStoragedAlternativeAddress(
                existentAddressList, person.getAlternativeAddressList());
        for(Address personAlternativeAddress: person.getAlternativeAddressList()){
            if(!storagedAlternativeAddressList.contains(personAlternativeAddress)){
                storagedAlternativeAddressList.add(personAlternativeAddress);
            }
        }
        person.setAlternativeAddressList(storagedAlternativeAddressList);
    }

    // Check if the alternative addresses (without resident) exist in database and link it to the person
    public void setAlternativeAddressOfPerson(
            List<Address> existentAddressList, List<Address> alternativeAddress, Person person
    ){
        List<Address> storagedAlternativeAddressList = addressService.getStoragedAlternativeAddress(
                existentAddressList, alternativeAddress);
        for(Address address: alternativeAddress){
            if(!storagedAlternativeAddressList.contains(address)){
                storagedAlternativeAddressList.add(address);
            }
        }
        person.setAlternativeAddressList(storagedAlternativeAddressList);
    }

    public void saveNewPersonAddresses(Address mainAddres, List<Address> alternativeAddresses){
        if(mainAddres != null){
            addressService.save(mainAddres);
        }
        if(alternativeAddresses != null){
            for(int i = 0; i < alternativeAddresses.size(); i++){
                Address currAddress = alternativeAddresses.get(i);
                if(currAddress != null){
                    addressService.save(currAddress);
                }
            }
        }
    }

    public void removeReceivedAddressDuplicate(Person person){
        List<Address> alternativeAddressListWithoutDuplicate = addressService
                .getNonDuplicateAlternativeAddressList(person.getMainAddress(), person.getAlternativeAddressList());
        person.setAlternativeAddressList(alternativeAddressListWithoutDuplicate);
    }

    public HttpStatus update(PersonDto dto) {
        if(dto == null){
            return HttpStatus.BAD_REQUEST;
        }
        Person savedPerson = personRepository.findById(dto.getId())
                .orElseThrow(() -> new PersonNotFoundException("There isn't user saved with this ID"));
        Address newMainAddress = AddressMapper.fromDtoToAddress(dto.getMainAddress(), savedPerson);
        List<Address> newAlternativeAddressList = AddressMapper
                .fromDtoListToAddressList(dto.getAlternativeAddressList(), savedPerson);
        savedPerson.setName(dto.getName());
        savedPerson.setBirthdate(dto.getBirthdate() != null ? LocalDate.parse(dto.getBirthdate()) : null);
        updateChecks(savedPerson, newMainAddress, newAlternativeAddressList);
        removeReceivedAddressDuplicate(savedPerson);
        saveNewPersonAddresses(savedPerson.getMainAddress(), savedPerson.getAlternativeAddressList());
        personRepository.save(savedPerson);
        return HttpStatus.OK;
    }

    public PersonDto getById(long id) {
        Person person = personRepository.findById(id).orElseThrow(
                () ->new PersonNotFoundException("There isn't user saved with entered ID"));
        return PersonMapper.fromPersonToDto(person);
    }

    public List<PersonDto> getAll() {
        List<Person> personList = personRepository.findAll();
        List<PersonDto> personDtoList = new ArrayList<>();
        personList.forEach(person -> {
            PersonDto personDto = PersonMapper.fromPersonToDto(person);
            personDtoList.add(personDto);
        });
        return personDtoList;
    }
}
