package com.api.peopleAPI.models;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PersonTest {
    @Test
    public void gettersAndSettersTest(){
        long id = 1L;
        String name = "Italo Modesto Pereira";
        LocalDate birthdate = LocalDate.parse("1992-12-30");
        Address mainAddress = new Address();
        List<Address> alternativeAddressList = new ArrayList<>();
        Person person = new Person();

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

    @Test
    public void addAddressTest(){
        String street = "Rua 1";
        Integer number = 11;
        Integer cep = 58429120;
        String city = "Campina Grande";
        Person person = new Person();
        Address address1 = new Address(street, number, cep, city, person);
        Address address2 = new Address(street, number + 1, cep, city, person);

        person.addAddress(address1);
        person.addAddress(address2);

        assertEquals(address1, person.getMainAddress(), "Address1 isn't the main address");
        assertEquals(address2, person.getAlternativeAddressList().get(0), "Address2 isn't an alternative address");
    }
}
