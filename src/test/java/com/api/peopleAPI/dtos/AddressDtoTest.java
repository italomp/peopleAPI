package com.api.peopleAPI.dtos;

import com.api.peopleAPI.models.Address;
import com.api.peopleAPI.models.Person;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AddressDtoTest {
    @Test
    public void gettersAndSettersTest(){
        long id = 1L;
        String street = "Rua 10";
        int number = 10;
        int cep = 58429120;
        String city = "Campina Grande";

        AddressDto address = new AddressDto();
        address.setId(id);
        address.setStreet(street);
        address.setNumber(number);
        address.setCep(cep);
        address.setCity(city);

        assertEquals(id, address.getId(), "The attributes are different");
        assertEquals(street, address.getStreet(), "The attributes are different");
        assertEquals(number, address.getNumber(), "The attributes are different");
        assertEquals(cep, address.getCep(), "The attributes are different");
        assertEquals(city, address.getCity(), "The attributes are different");
    }
}
