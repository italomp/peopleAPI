package com.api.peopleAPI;

import com.api.peopleAPI.models.Address;
import com.api.peopleAPI.models.Person;
import com.api.peopleAPI.services.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AddressServiceTest extends PeopleApiApplicationTests{
    @Autowired
    private AddressService addressService;
    private Address mainAddress;
    private List<Address> alternativeAddressList;
    private List<Address> existentAddressList;
    private String street, city;
    private Integer number, cep;
    private Person person;

    @BeforeEach
    public void setMainAddress(){
        street = "Rua da paz";
        number = 10;
        cep = 58429120;
        city = "Campina Grande";
        person = null;
        this.mainAddress = new Address(street, number, cep, city, person);
    }

    @BeforeEach
    public void setAlternativeAddressList(){
        this.alternativeAddressList = new ArrayList<>();
    }

    @BeforeEach
    public void setExistentAddressList(){
        this.existentAddressList = new ArrayList<>();
    }

    @Test
    @DisplayName("Get non duplicate alternative address list when have no duplicate between main and alternative addresses")
    public void getNonDuplicateAlternativeAddressListTestWhenHaveNoDuplicate(){
        Integer expectedSizeList = 3;
        number++;
        for(int i = 0; i < 3; i++){
            alternativeAddressList.add(new Address(street, number + i, cep, city, person));
        }
        assertEquals(
                expectedSizeList,
                addressService.getNonDuplicateAlternativeAddressList(mainAddress, alternativeAddressList).size(),
                "Different size lists");
    }

    @Test
    @DisplayName("Get non duplicate alternative address list when have duplicate between main and alternative addresses")
    public void getNonDuplicateAlternativeAddressListTestWhenHaveDuplicateBetweenMainAndAlternativeAddresses(){
        Integer expectedSizeList = 2;
        for(int i = 0; i < 3; i++){
            alternativeAddressList.add(new Address(street, number + i, cep, city, person));
        }
        assertEquals(
                expectedSizeList,
                addressService.getNonDuplicateAlternativeAddressList(mainAddress, alternativeAddressList).size(),
                "Different size lists");
    }

    @Test
    @DisplayName("Get non duplicate alternative address list when have duplicate between alternative addresses")
    public void getNonDuplicateAlternativeAddressListTestWhenHaveDuplicateBetweenAlternativeAddresses(){
        Integer expectedSizeList = 3;
        number++;
        for(int i = 0; i < 3; i++){
            alternativeAddressList.add(new Address(street, number + i, cep, city, person));
        }
        alternativeAddressList.add(alternativeAddressList.get(0));
        assertEquals(
                expectedSizeList,
                addressService.getNonDuplicateAlternativeAddressList(mainAddress, alternativeAddressList).size(),
                "Different size lists");
    }

    @Test
    @DisplayName("Get non duplicate alternative address list when the method receive a null alternative address list")
    public void getNonDuplicateAlternativeAddressListTestGivingNullAlternativeAddressList(){
        Address mainAddress = null; //new Address(street, number, cep, city, person);
        List<Address> expectedList = new ArrayList<>();
        assertEquals(
                expectedList,
                addressService.getNonDuplicateAlternativeAddressList(mainAddress, alternativeAddressList),
                "An empty list wasn't returned");
        alternativeAddressList = null;
        mainAddress = new Address(street, number, cep, city, person);
        expectedList.add(mainAddress);
        assertEquals(
                expectedList,
                addressService.getNonDuplicateAlternativeAddressList(mainAddress, alternativeAddressList),
                "The expected list wasn't returned");
    }

    @Test
    @DisplayName("All cases of getAnEqualsSavedAddress method")
    public void getAnEqualsSavedAddressTest(){
        for(int i = 1; i < 4; i++){
            existentAddressList.add(new Address(street, number + i, cep, city, person));
        }
        assertNull(addressService.getAnEqualsSavedAddress(existentAddressList, mainAddress),
                "Null value wasn't returned");
        existentAddressList.add(mainAddress);
        assertEquals(mainAddress, addressService.getAnEqualsSavedAddress(existentAddressList, mainAddress),
                "Main address wasn't returned");
        assertNull(addressService.getAnEqualsSavedAddress(new ArrayList<>(), mainAddress),
                "Main address wasn't returned");
    }

    @Test
    @DisplayName("All cases of getSavedAlternativeAddressList")
    public void getSavedAlternativeAddressListTest(){
        for(int i = 1; i < 4; i++){
            alternativeAddressList.add(new Address(street, number + i, cep, city, person));
            existentAddressList.add(new Address(street, number + i, cep, city, person));
        }
        assertEquals(
                new ArrayList<Address>(),
                addressService.getSavedAlternativeAddressList(existentAddressList, new ArrayList<Address>()),
                "The returned list isn't empty");
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
}
