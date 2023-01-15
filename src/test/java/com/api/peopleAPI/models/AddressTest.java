package com.api.peopleAPI.models;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AddressTest {
    @Test
    public void gettersAndSettersTest(){
        long id = 1L;
        String street = "Rua 10";
        int number = 10;
        int cep = 58429120;
        String city = "Campina Grande";
        Person resident1 = new Person("João", LocalDate.now(), null, null);
        Person resident12 = new Person("José", LocalDate.now(), null, null);
        Person mainResident1 = new Person("Maria", LocalDate.now(), null, null);
        Person mainResident2 = new Person("Amanda", LocalDate.now(), null, null);

        List<Person> residentList = new ArrayList<>(List.of(resident1, resident12));
        List<Person> mainResidentList = new ArrayList<>(List.of(mainResident1, mainResident2));

        Address address = new Address();
        address.setId(id);
        address.setStreet(street);
        address.setNumber(number);
        address.setCep(cep);
        address.setCity(city);
        address.setResidentList(residentList);
        address.setMainResidentList(mainResidentList);

        assertEquals(id, address.getId(), "The attributes are different");
        assertEquals(street, address.getStreet(), "The attributes are different");
        assertEquals(number, address.getNumber(), "The attributes are different");
        assertEquals(cep, address.getCep(), "The attributes are different");
        assertEquals(city, address.getCity(), "The attributes are different");
        assertEquals(residentList, address.getResidentList(), "The attributes are different");
        assertEquals(mainResidentList, address.getMainResidentList(), "The attributes are different");
    }

    @Test
    public void addResidentTest(){
        Person person1 = new Person("João", LocalDate.now(), null, null);
        Person person2 = new Person("João", LocalDate.now(), new Address(), null);

        Address address1 = new Address();

        // Giving null person
        address1.addResident(null);
        assertEquals(
                0,
                address1.getResidentList().size(),
                "The residentList size is different do the expected");
        assertEquals(
                0,
                address1.getMainResidentList().size(),
                "The mainResidentList size is different do the expected");

        // Ginving person who haven't main address
        address1.addResident(person1);
        assertEquals(person1, address1.getMainResidentList().get(0), "The person1 wasn't returned");

        // Ginving person who haven main address
        address1.addResident(person2);
        assertEquals(person2, address1.getResidentList().get(0), "The person2 wasn't returned");
        assertEquals(person1, address1.getMainResidentList().get(0), "The person1 wasn't returned");
    }
}
