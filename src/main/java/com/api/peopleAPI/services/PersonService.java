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
        existentMainAddressCheck(existentAddressList, person);
        existentAltertativeAddressCheck(existentAddressList, person);
    }

    public void nullAttributesCheck(Person dto){
        if(dto.getName() == null) {
            throw new IllegalArgumentException("Person name can't be null");
        }
        if(dto.getBirthdate() == null) {
            throw new IllegalArgumentException("Person birthdate can't be null");
        }
    }

    public void existentMainAddressCheck(List<Address> existentAddressList, Person person){
        Address mainAddress = addressService.getStoragedMainAddress(existentAddressList, person.getMainAddress());
        if(mainAddress != null){
            person.setMainAddress(mainAddress);
        }
    }

    public void existentAltertativeAddressCheck(List<Address> existentAddressList, Person person){
        List<Address> storagedAlternativeAddressList = addressService.getStoragedAlternativeAddress(
                existentAddressList, person.getAlternativeAddressList());
        for(Address personAlternativeAddress: person.getAlternativeAddressList()){
            if(!storagedAlternativeAddressList.contains(personAlternativeAddress)){
                storagedAlternativeAddressList.add(personAlternativeAddress);
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

        Person person = personRepository.findById(dto.getId())
                .orElseThrow(() -> new PersonNotFoundException("There isn't user saved with this ID"));

        Address mainAddress = AddressMapper.fromDtoToAddress(dto.getMainAddress(), person);
        List<Address> alternativeAddressList = AddressMapper.fromDtoListToAddressList(dto.getAlternativeAddressList(), person);

        person.setName(dto.getName());
        person.setBirthdate(dto.getBirthdate() != null ? LocalDate.parse(dto.getBirthdate()) : null);
        person.setMainAddress(mainAddress);
        person.setAlternativeAddressList(alternativeAddressList);
        saveNewPersonAddresses(person.getMainAddress(), person.getAlternativeAddressList()); // Poderia verificar se já existem
        nullAttributesCheck(person);
        personRepository.save(person);
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
