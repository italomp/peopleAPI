package com.api.peopleAPI.dtos;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PersonDtoTest {
    @Test
    public void gettersAndSettersTest(){
        long id = 1L;
        String name = "Italo Modesto Pereira";
        String birthdate = "1992-12-30";
        AddressDto mainAddress = new AddressDto();
        List<AddressDto> alternativeAddressList = new ArrayList<>();
        PersonDto person = new PersonDto();

        person.setId(id);
        person.setName(name);
        person.setBirthdate(birthdate);
        person.setMainAddress(mainAddress);
        person.setAlternativeAddressList(alternativeAddressList);

        assertEquals(id, person.getId(), "The attributes are different");
        assertEquals(name, person.getName(), "The attributes are different");
        assertEquals(birthdate, person.getBirthdate(), "The attributes are different");
        assertEquals(mainAddress, person.getMainAddress(), "The attributes are different");
        assertEquals(alternativeAddressList, person.getAlternativeAddressList(), "The attributes are different");
    }
}
