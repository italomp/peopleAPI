package com.api.peopleAPI;

import com.api.peopleAPI.models.Address;
import com.api.peopleAPI.models.Person;
import com.api.peopleAPI.services.AddressService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AddressServiceTest extends PeopleApiApplicationTests{
    @Autowired
    private AddressService addressService;

    @Test
    public void getNonDuplicateAlternativeAddressListTest(){
        String street = "Rua da paz";
        Integer number = 10;
        Integer cep = 58429120;
        String city = "Campina Grande";
        Person person = null;
        List<Address> alternativeAddressList = new ArrayList<>();
        List<Address> existentAddressList = new ArrayList<>();
        for(int i = 1; i < 4; i++){
            alternativeAddressList.add(new Address(street, number + i, cep, city, person));
            existentAddressList.add(new Address(street, number + i, cep, city, person));
        }

        assertEquals(
                existentAddressList,
                addressService.getSavedAlternativeAddressList(existentAddressList, alternativeAddressList),
                "The returned list is different from alternativeAddresList");
        alternativeAddressList.add(new Address(street, number + 4, cep, city, person));
        alternativeAddressList.add(new Address(street, number + 5, cep, city, person));
        assertEquals(
                existentAddressList,
                addressService.getSavedAlternativeAddressList(existentAddressList, alternativeAddressList),
                "The returned list is different from alternativeAddresList");
        alternativeAddressList.remove(4);
        alternativeAddressList.remove(3);
        alternativeAddressList.remove(2);
        assertEquals(
                alternativeAddressList,
                addressService.getSavedAlternativeAddressList(existentAddressList, alternativeAddressList),
                "The returned list is different from alternativeAddresList");
    }

    @Test
    public void getAnEqualsSavedAddressTest(){
        String street = "Rua da paz";
        Integer number = 10;
        Integer cep = 58429120;
        String city = "Campina Grande";
        Person person = null;
        List<Address> existentAddressList = new ArrayList<>();
        Address mainAddress = new Address(street, number, cep, city, person);

        for(int i = 1; i < 4; i++){
            existentAddressList.add(new Address(street, number + i, cep, city, person));
        }

        assertNull(addressService.getAnEqualsSavedAddress(existentAddressList, mainAddress),
                "Null value wasn't returned");
        existentAddressList.add(mainAddress);
        assertEquals(mainAddress, addressService.getAnEqualsSavedAddress(existentAddressList, mainAddress),
                "Main address wasn't returned");
    }

    @Test
    public void getSavedAlternativeAddressListTest(){
        String street = "Rua da paz";
        Integer number = 10;
        Integer cep = 58429120;
        String city = "Campina Grande";
        Person person = null;
        List<Address> existentAddressList = new ArrayList<>();
        List<Address> alternativeAddressList = new ArrayList<>();;
        int listSizeExpect = 0;

        alternativeAddressList.add(new Address(street, number, cep, city, person));
        for(int i = 1; i < 4; i++){
            existentAddressList.add(new Address(street, number + i, cep, city, person));
        }

        assertEquals(
                listSizeExpect,
                addressService.getSavedAlternativeAddressList(existentAddressList, null).size(),
                "Empty list wasn't returned");
        assertEquals(
                listSizeExpect,
                addressService.getSavedAlternativeAddressList(existentAddressList, alternativeAddressList).size(),
                "Empty list wasn't returned");
        alternativeAddressList.add(existentAddressList.get(0));
        assertEquals(
                ++listSizeExpect,
                addressService.getSavedAlternativeAddressList(existentAddressList, alternativeAddressList).size(),
                "Empty list wasn't returned");
        alternativeAddressList.add(existentAddressList.get(1));
        assertEquals(
                ++listSizeExpect,
                addressService.getSavedAlternativeAddressList(existentAddressList, alternativeAddressList).size(),
                "Empty list wasn't returned");
    }
}
