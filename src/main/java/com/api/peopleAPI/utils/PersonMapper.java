package com.api.peopleAPI.utils;

import com.api.peopleAPI.dtos.AddressDto;
import com.api.peopleAPI.dtos.PersonDto;
import com.api.peopleAPI.models.Address;
import com.api.peopleAPI.models.Person;

import java.time.LocalDate;
import java.util.List;

public class PersonMapper {

    public static Person fromDtoToPerson(PersonDto dto){
        if(dto == null) {
            return null;
        }
        Person person = new Person(
                dto.getName(),
                LocalDate.parse(dto.getBirthdate()),
                null ,
                null);
        Address mainAddress = AddressMapper.fromDtoToAddress(dto.getMainAddress(), person);
        List<Address> alternativeAddressList = AddressMapper.fromDtoListToAddressList(dto.getAlternativeAddressList(), person);
        person.setMainAddress(mainAddress);
        person.setAlternativeAddressList(alternativeAddressList);
        return person;
    }

    public static PersonDto fromPersonToDto(Person person){
        if(person == null){
            return null;
        }
        AddressDto mainAddressDto = AddressMapper.fromAddressToDto(person.getMainAddress());
        List<AddressDto> alternativeAddressDto = AddressMapper.fromAddressListToDtoList(person.getAlternativeAddressList());
        return new PersonDto(
                person.getId(),
                person.getName(),
                person.getBirthdate() != null ? person.getBirthdate().toString() : null,
                mainAddressDto,
                alternativeAddressDto);
    }
}
