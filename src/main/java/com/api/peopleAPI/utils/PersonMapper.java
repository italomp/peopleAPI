package com.api.peopleAPI.utils;

import com.api.peopleAPI.dtos.PersonDto;
import com.api.peopleAPI.models.Person;

import java.time.LocalDate;

public class PersonMapper {

    public static Person fromDtoToPerson(PersonDto dto){
        if(dto == null)
            return null;
        return new Person(
                dto.getName(),
                LocalDate.parse(dto.getBirthdate()),
                dto.getMainAddress(),
                dto.getAlternativeAddressList());
    }

    public static PersonDto fromPersonToDto(Person person){
        if(person == null){
            return null;
        }
        return new PersonDto(
                person.getId(),
                person.getName(),
                person.getBirthdate() != null ? person.getBirthdate().toString() : null,
                person.getMainAddress(),
                person.getAlternativeAddressList());
    }
}
