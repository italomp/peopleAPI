package com.api.peopleAPI;

import com.api.peopleAPI.dtos.PersonDto;
import com.api.peopleAPI.exceptions.PersonNotFoundException;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Test
    public void registerPersonWithAllAttributes(){
        // Set person attributes
        String personName = "Italo Modesto Pereira";
        String personBirthdate = "1992-12-30";
        Address personMainAddress = new Address();
        List<Address> personAlternativeAddressList = new ArrayList<>();

        // Set address attributes
        String AddressStreet = "Rua 1";
        Integer AddressNumber = 11;
        Integer AddressCep = 58429120;
        String AddressCity = "Campina Grande";

        // Set personDto
        PersonDto personDto = new PersonDto(personName, personBirthdate,personMainAddress,personAlternativeAddressList);

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
        String personBirthdate = "1992-12-30";
        Address personMainAddress = null;
        List<Address> personAlternativeAddressList = null;

        // Set personDto
        PersonDto personDto = new PersonDto(personName, personBirthdate,personMainAddress,personAlternativeAddressList);

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
        String personBirthdate = "1992-12-30";
        Address personMainAddress = null;
        List<Address> personAlternativeAddressList = null;

        // Set personDto
        PersonDto personDto = new PersonDto(personName, personBirthdate,personMainAddress,personAlternativeAddressList);

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
        person.setBirthdate(null);

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

    @Test
    public void updatePersonGivingAllAttributresCerrectly(){
        // Set saved person attributes
        long personId = 1L;
        String personName = "Italo Modesto Pereira";
        LocalDate personBirthdate = LocalDate.parse("1992-12-30");
        Address personMainAddress = new Address();
        List<Address> personAlternativeAddressList = new ArrayList<>();
        Person savedPerson = new Person(
                personId, personName, personBirthdate, personMainAddress, personAlternativeAddressList);

        // Set address attributes
        String AddressStreet = "Rua 1";
        Integer AddressNumber = 11;
        Integer AddressCep = 58429120;
        String AddressCity = "Campina Grande";
        personMainAddress = new Address(AddressStreet, AddressNumber, AddressCep, AddressCity, savedPerson);
        personAlternativeAddressList = List.of(
                new Address(AddressStreet, AddressNumber + 1, AddressCep, AddressCity, savedPerson),
                new Address(AddressStreet, AddressNumber + 2, AddressCep, AddressCity, savedPerson)
        );
        savedPerson.setMainAddress(personMainAddress);
        savedPerson.setAlternativeAddressList(personAlternativeAddressList);

        // Set updated person
        Person updatedPerson = new Person();
        updatedPerson.setId(savedPerson.getId());
        updatedPerson.setName("Italo Pereira");
        updatedPerson.setBirthdate(LocalDate.parse("1992-12-31"));
        updatedPerson.setMainAddress(
                new Address(AddressStreet, AddressNumber + 1, AddressCep, AddressCity, savedPerson));
        updatedPerson.setAlternativeAddressList(List.of(
                new Address(AddressStreet, AddressNumber + 2, AddressCep, AddressCity, savedPerson),
                new Address(AddressStreet, AddressNumber + 3, AddressCep, AddressCity, savedPerson)));

        PersonDto updatedPersonDto = PersonMapper.fromPersonToDto(updatedPerson);

        when(personRepository.findById(personId)).thenReturn(Optional.of(savedPerson));
        when(personRepository.save(ArgumentMatchers.eq(updatedPerson))).thenReturn(updatedPerson);

        assertEquals(HttpStatus.OK, personService.update(updatedPersonDto), "Person was not updated");
        verify(personRepository, times(1)).findById(personId);
        verify(personRepository, times(1)).save(updatedPerson);
    }

    @Test
    public void updatePersonGivingNullNameAndBirthdate(){
        // Set saved person attributes
        long personId = 1L;
        String personName = "Italo Modesto Pereira";
        LocalDate personBirthdate = LocalDate.parse("1992-12-30");
        Address personMainAddress = new Address();
        List<Address> personAlternativeAddressList = new ArrayList<>();
        Person savedPerson = new Person(
                personId, personName, personBirthdate, personMainAddress, personAlternativeAddressList);

        // Set address attributes
        String AddressStreet = "Rua 1";
        Integer AddressNumber = 11;
        Integer AddressCep = 58429120;
        String AddressCity = "Campina Grande";
        personMainAddress = new Address(AddressStreet, AddressNumber, AddressCep, AddressCity, savedPerson);
        personAlternativeAddressList = List.of(
                new Address(AddressStreet, AddressNumber + 1, AddressCep, AddressCity, savedPerson),
                new Address(AddressStreet, AddressNumber + 2, AddressCep, AddressCity, savedPerson)
        );
        savedPerson.setMainAddress(personMainAddress);
        savedPerson.setAlternativeAddressList(personAlternativeAddressList);

        // Set updated person
        Person updatedPerson = new Person();
        updatedPerson.setId(savedPerson.getId());
        updatedPerson.setName(null);
        updatedPerson.setBirthdate(null);
        updatedPerson.setMainAddress(
                new Address(AddressStreet, AddressNumber + 1, AddressCep, AddressCity, savedPerson));
        updatedPerson.setAlternativeAddressList(List.of(
                new Address(AddressStreet, AddressNumber + 2, AddressCep, AddressCity, savedPerson),
                new Address(AddressStreet, AddressNumber + 3, AddressCep, AddressCity, savedPerson)));

        PersonDto updatedPersonDto = PersonMapper.fromPersonToDto(updatedPerson);

        when(personRepository.findById(personId)).thenReturn(Optional.of(savedPerson));
        when(personRepository.save(ArgumentMatchers.eq(updatedPerson))).thenThrow(new IllegalArgumentException());

        assertThrows(
                IllegalArgumentException.class ,
                () -> personService.update(updatedPersonDto),
                "Exception was not thrown");
        verify(personRepository, times(0)).findById(personId);
        verify(personRepository, times(0)).save(updatedPerson);
    }

    @Test
    public void updatePersongivingNullAddresses(){
        // Set saved person attributes
        long personId = 1L;
        String personName = "Italo Modesto Pereira";
        LocalDate personBirthdate = LocalDate.parse("1992-12-30");
        Address personMainAddress = new Address();
        List<Address> personAlternativeAddressList = new ArrayList<>();
        Person savedPerson = new Person(
                personId, personName, personBirthdate, personMainAddress, personAlternativeAddressList);

        // Set address attributes
        String AddressStreet = "Rua 1";
        Integer AddressNumber = 11;
        Integer AddressCep = 58429120;
        String AddressCity = "Campina Grande";
        personMainAddress = new Address(AddressStreet, AddressNumber, AddressCep, AddressCity, savedPerson);
        personAlternativeAddressList = List.of(
                new Address(AddressStreet, AddressNumber + 1, AddressCep, AddressCity, savedPerson),
                new Address(AddressStreet, AddressNumber + 2, AddressCep, AddressCity, savedPerson)
        );
        savedPerson.setMainAddress(personMainAddress);
        savedPerson.setAlternativeAddressList(personAlternativeAddressList);

        // Set updated person
        Person updatedPerson = new Person();
        updatedPerson.setId(savedPerson.getId());
        updatedPerson.setName("Italo Pereira");
        updatedPerson.setBirthdate(LocalDate.parse("1992-12-31"));
        updatedPerson.setMainAddress(null);
        updatedPerson.setAlternativeAddressList(null);

        PersonDto updatedPersonDto = PersonMapper.fromPersonToDto(updatedPerson);

        when(personRepository.findById(personId)).thenReturn(Optional.of(savedPerson));
        when(personRepository.save(ArgumentMatchers.eq(updatedPerson))).thenReturn(updatedPerson);

        assertEquals(HttpStatus.OK, personService.update(updatedPersonDto), "Person was not updated");
        verify(personRepository, times(1)).findById(personId);
        verify(personRepository, times(1)).save(updatedPerson);
    }
    @Test
    public void returnBadRequestWhenUpdatePersonGivingNullPerson(){
        assertEquals(HttpStatus.BAD_REQUEST, personService.update(null), "Bad request not returned");
    }

    @Test
    public void throwsExceptionWhenUpdatePersonGivingInvalidId(){
        // Set valid person
        long personId = 1L;
        String personName = "Italo Modesto Pereira";
        String personBirthdate = "1992-12-30";
        Address personMainAddress = new Address();
        List<Address> personAlternativeAddressList = new ArrayList<>();
        PersonDto savedPersonDto = new PersonDto(
                personId, personName, personBirthdate, personMainAddress, personAlternativeAddressList);
        Person savedPerson = PersonMapper.fromDtoToPerson(savedPersonDto);

        // Set invalid person
        long invalidId = 2L;
        PersonDto personWithInvalidIdDto = new PersonDto(
                invalidId, personName, personBirthdate, personMainAddress, personAlternativeAddressList
        );

        when(personRepository.findById(ArgumentMatchers.eq(personId))).thenReturn(Optional.of(savedPerson));
        when(personRepository.findById(ArgumentMatchers.eq(invalidId))).thenThrow(
                new PersonNotFoundException("There isn't user saved with entered ID"));

        assertEquals(HttpStatus.OK, personService.update(savedPersonDto), "Person not updated");
        assertThrows(
                PersonNotFoundException.class,
                () -> personService.update(personWithInvalidIdDto), "Exception not thrown");
        verify(personRepository, times(1)).save(savedPerson);
    }

    @Test
    public void getPersonDtoById(){
        long personId = 1L;
        long invalidId = 2L;
        String personName = "Italo Modesto Pereira";
        LocalDate personBirthdate = LocalDate.parse("1992-12-30");
        Address personMainAddress = new Address();
        List<Address> personAlternativeAddressList = new ArrayList<>();

        Person person = new Person(
                personId, personName, personBirthdate,personMainAddress,personAlternativeAddressList);
        PersonDto personDto = PersonMapper.fromPersonToDto(person);

        when(personRepository.findById(ArgumentMatchers.eq(personId))).thenReturn(Optional.of(person));
        when(personRepository.findById(ArgumentMatchers.eq(invalidId))).thenThrow(
                new PersonNotFoundException("There isn't user saved with entered ID"));

        assertEquals(personDto, personService.getById(personId), "Person DTO was not returned");
        assertThrows(
                PersonNotFoundException.class,
                () -> personService.getById(invalidId),
                "Exception was not thrown");
        verify(personRepository, times(1)).findById(personId);
        verify(personRepository, times(1)).findById(invalidId);
    }
}
