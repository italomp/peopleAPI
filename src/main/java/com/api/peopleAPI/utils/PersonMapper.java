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
                LocalDate.parse(dto.getBirthDate()),
                dto.getMainAddress(),
                dto.getAlternativeAddressList());
    }
}
