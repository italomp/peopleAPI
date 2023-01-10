package com.api.peopleAPI;

import com.api.peopleAPI.dtos.PersonDto;
import com.api.peopleAPI.models.Address;
import com.api.peopleAPI.models.Person;
import com.api.peopleAPI.repositories.PersonRepository;
import com.api.peopleAPI.services.AddressService;
import com.api.peopleAPI.services.PersonService;
import com.api.peopleAPI.utils.PersonMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PersonServiceTest extends PeopleApiApplicationTests{
    @MockBean
    private PersonRepository personRepository;
    @MockBean
    private AddressService addressService;
    @Autowired
    @InjectMocks
    private PersonService personService;
    private PersonDto personDto;

    @Test
    public void registerPersonWithAllAttributes(){
        // Set person attributes
        String personName = "Italo Modesto Pereira";
        String personBirthDate = "1992-12-30";
        Address personMainAddress = new Address();
        List<Address> personAlternativeAddressList = new ArrayList<>();

        // Set address attributes
        String AddressStreet = "Rua 1";
        Integer AddressNumber = 11;
        Integer AddressCep = 58429120;
        String AddressCity = "Campina Grande";

        // Set personDto
        PersonDto personDto = new PersonDto(personName, personBirthDate,personMainAddress,personAlternativeAddressList);

        // Set Person
        Person person = PersonMapper.fromDtoToPerson(personDto);
        personMainAddress = new Address(AddressStreet, AddressNumber, AddressCep, AddressCity, person);
        personAlternativeAddressList = List.of(
                new Address(AddressStreet, AddressNumber + 1, AddressCep, AddressCity, person),
                new Address(AddressStreet, AddressNumber + 2, AddressCep, AddressCity, person)
        );
        person.setMainAddress(personMainAddress);
        person.setAlternativeAddressList(personAlternativeAddressList);

        personDto.setMainAddress(personMainAddress);
        personDto.setAlternativeAddressList(personAlternativeAddressList);

        // Set personRepository behaviors
        when(personRepository.save(ArgumentMatchers.eq(person))).thenReturn(person);
        when(personRepository.save(null)).thenThrow(new IllegalArgumentException());

        // Assertions
        assertEquals(HttpStatus.CREATED, personService.savePerson(personDto), "Person don't created");
        verify(personRepository, times(1)).save(person);
    }

    @Test
    public void registerPersonWithoutAddresses(){
        // Set person attributes
        String personName = "Italo Modesto Pereira";
        String personBirthDate = "1992-12-30";
        Address personMainAddress = null;
        List<Address> personAlternativeAddressList = null;

        // Set personDto
        PersonDto personDto = new PersonDto(personName, personBirthDate,personMainAddress,personAlternativeAddressList);

        // Set Person
        Person person = PersonMapper.fromDtoToPerson(personDto);

        // Set personRepository behaviors
        when(personRepository.save(ArgumentMatchers.eq(person))).thenReturn(person);
        when(personRepository.save(null)).thenThrow(new IllegalArgumentException());

        // Assertions
        assertEquals(HttpStatus.CREATED, personService.savePerson(personDto), "Person don't created");
        verify(personRepository, times(1)).save(person);
    }

    @Test
    public void throwExceptionWhenAttempRegisterPersonWithNullNameOrNullBirthDate(){
        // Set person attributes
        String personName = null;
        String personBirthDate = "1992-12-30";
        Address personMainAddress = null;
        List<Address> personAlternativeAddressList = null;

        // Set personDto
        PersonDto personDto = new PersonDto(personName, personBirthDate,personMainAddress,personAlternativeAddressList);

        // Set Person
        Person person = PersonMapper.fromDtoToPerson(personDto);

        // Set personRepository behaviors
        when(personRepository.save(ArgumentMatchers.eq(person))).thenReturn(person);
        when(personRepository.save(null)).thenThrow(new IllegalArgumentException());

        // Null name assertions
        assertThrows(
                IllegalArgumentException.class,
                () -> personService.savePerson(personDto),
                "Exception don't was thrown");
        verify(personRepository, times(0)).save(person);

        person.setName("Italo Modesto Pereira");
        person.setBirthDate(null);

        // Null birthdate Assertions
        assertThrows(
                IllegalArgumentException.class,
                () -> personService.savePerson(personDto),
                "Exception don't was thrown");
        verify(personRepository, times(0)).save(person);
    }

    @Test
    public void returnBadRequestWhenAttemptRegisterNullPerson(){
        PersonDto personDto = null;
        assertEquals(HttpStatus.BAD_REQUEST, personService.savePerson(personDto), "Exception don't was thrown");
    }
}
