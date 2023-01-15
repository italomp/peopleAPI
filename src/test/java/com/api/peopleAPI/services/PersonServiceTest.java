package com.api.peopleAPI.services;

import com.api.peopleAPI.dtos.AddressDto;
import com.api.peopleAPI.dtos.PersonDto;
import com.api.peopleAPI.exceptions.person.PersonNotFoundException;
import com.api.peopleAPI.models.Address;
import com.api.peopleAPI.models.Person;
import com.api.peopleAPI.repositories.PersonRepository;
import com.api.peopleAPI.utils.AddressMapper;
import com.api.peopleAPI.utils.PersonMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PersonServiceTest{
    @MockBean
    private PersonRepository personRepository;
    @MockBean
    private AddressService addressService;
    @Autowired
    @InjectMocks
    private PersonService personService;

    @Test
    @DisplayName("Should save the person")
    public void savePersonTest(){
        String name = "Italo Modesto Pereira";
        String birthdate = "1992-12-30";
        String street = "Rua 1";
        Integer number = 11;
        Integer cep = 58429120;
        String city = "Campina Grande";
        AddressDto mainAddressDto = new AddressDto(street, number, cep, city);
        List<AddressDto> alternativeAddressDtoList = List.of(
                new AddressDto(street, number + 1, cep, city),
                new AddressDto(street, number + 2, cep, city)
        );
        PersonDto personDto = new PersonDto(name, birthdate, mainAddressDto, alternativeAddressDtoList);

        Person person = PersonMapper.fromDtoToPerson(personDto);
        Address mainAddress = AddressMapper.fromDtoToAddress(mainAddressDto, person);
        List<Address> alternativeAddressList = AddressMapper.fromDtoListToAddressList(alternativeAddressDtoList, person);
        person.setMainAddress(mainAddress);
        person.setAlternativeAddressList(alternativeAddressList);

        when(personRepository.save(ArgumentMatchers.eq(person))).thenReturn(person);
        when(personRepository.save(null)).thenThrow(new IllegalArgumentException());

        assertEquals(HttpStatus.CREATED, personService.savePerson(personDto), "Person don't created");
        verify(personRepository, times(1)).save(person);
    }

    @Test
    @DisplayName("Should save the person without his address")
    public void savePersonWithoutAddressesTest(){
        String personName = "Italo Modesto Pereira";
        String personBirthdate = "1992-12-30";
        AddressDto personMainAddress = null;
        List<AddressDto> personAlternativeAddressList = null;
        PersonDto personDto = new PersonDto(personName, personBirthdate,personMainAddress,personAlternativeAddressList);
        Person person = PersonMapper.fromDtoToPerson(personDto);

        when(personRepository.save(ArgumentMatchers.eq(person))).thenReturn(person);
        when(personRepository.save(null)).thenThrow(new IllegalArgumentException());

        assertEquals(HttpStatus.CREATED, personService.savePerson(personDto), "Person don't created");
        verify(personRepository, times(1)).save(person);
    }

    @Test
    @DisplayName("Should to throw exception when attempt save person with null name or birthdate")
    public void savePersonWithNullNameOrBirthdateTest(){
        String personName = null;
        String personBirthdate = "1992-12-30";
        AddressDto personMainAddress = null;
        List<AddressDto> personAlternativeAddressList = null;

        PersonDto personDto = new PersonDto(personName, personBirthdate,personMainAddress,personAlternativeAddressList);
        Person person = PersonMapper.fromDtoToPerson(personDto);

        when(personRepository.save(ArgumentMatchers.eq(person))).thenReturn(person);
        when(personRepository.save(null)).thenThrow(new IllegalArgumentException());

        assertThrows(
                IllegalArgumentException.class,
                () -> personService.savePerson(personDto),
                "Exception don't was thrown");
        verify(personRepository, times(0)).save(person);

        person.setName("Italo Modesto Pereira");
        person.setBirthdate(null);

        assertThrows(
                IllegalArgumentException.class,
                () -> personService.savePerson(personDto),
                "Exception don't was thrown");
        verify(personRepository, times(0)).save(person);
    }

    @Test
    @DisplayName("Should to return a bad request status code when attempt save a null person")
    public void saveNullPersonTest(){
        PersonDto personDto = null;
        assertEquals(HttpStatus.BAD_REQUEST, personService.savePerson(personDto), "Exception don't was thrown");
    }

    @Test
    @DisplayName("Should to update the person")
    public void updatePersonTest(){
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
    @DisplayName("Should to throw a exception when attempt update a person giving null name or birthdate")
    public void updatePersonGivingNullNameAndBirthdateTest(){
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
        verify(personRepository, times(1)).findById(personId);
        verify(personRepository, times(0)).save(updatedPerson);
    }

    @Test
    @DisplayName("Should to save the person with his null addresses")
    public void updatePersonGivingNullAddressesTest(){
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
    @DisplayName("Should to return a bad request status code when attempt update a person giving a null person")
    public void updatePersonGivingNullPersonTest(){
        assertEquals(HttpStatus.BAD_REQUEST, personService.update(null), "Bad request not returned");
    }

    @Test
    @DisplayName("Should to throw a exception when attempt update a person giving a invalid id")
    public void updatePersonGivingInvalidIdTest(){
        // Set person with valid id
        long validId = 1L;
        String personName = "Italo Modesto Pereira";
        String personBirthdate = "1992-12-30";
        AddressDto personMainAddress = new AddressDto();
        List<AddressDto> personAlternativeAddressList = new ArrayList<>();
        PersonDto savedPersonDto = new PersonDto(
                validId, personName, personBirthdate, personMainAddress, personAlternativeAddressList);
        Person savedPerson = PersonMapper.fromDtoToPerson(savedPersonDto);

        // Set person with invalid id
        long invalidId = 2L;
        PersonDto personWithInvalidIdDto = new PersonDto(
                invalidId, personName, personBirthdate, personMainAddress, personAlternativeAddressList
        );

        when(personRepository.findById(ArgumentMatchers.eq(validId))).thenReturn(Optional.of(savedPerson));
        when(personRepository.findById(ArgumentMatchers.eq(invalidId))).thenThrow(
                new PersonNotFoundException("There isn't user saved with entered ID"));

        assertEquals(HttpStatus.OK, personService.update(savedPersonDto), "Person not updated");
        assertThrows(
                PersonNotFoundException.class,
                () -> personService.update(personWithInvalidIdDto), "Exception not thrown");
        verify(personRepository, times(1)).save(savedPerson);
        verify(personRepository, times(1)).findById(validId);
        verify(personRepository, times(1)).findById(invalidId);
    }

    @Test
    @DisplayName("Should to return a person when giving a valid id and to throw exception when giving an invalid id")
    public void getPersonDtoById(){
        long validId = 1L;
        long invalidId = 2L;
        String personName = "Italo Modesto Pereira";
        LocalDate personBirthdate = LocalDate.parse("1992-12-30");
        Address personMainAddress = new Address();
        List<Address> personAlternativeAddressList = new ArrayList<>();

        Person person = new Person(
                validId, personName, personBirthdate,personMainAddress,personAlternativeAddressList);
        PersonDto personDto = PersonMapper.fromPersonToDto(person);

        when(personRepository.findById(ArgumentMatchers.eq(validId))).thenReturn(Optional.of(person));
        when(personRepository.findById(ArgumentMatchers.eq(invalidId))).thenThrow(
                new PersonNotFoundException("There isn't user saved with entered ID"));

        assertEquals(personDto, personService.getById(validId), "Person DTO was not returned");
        assertThrows(
                PersonNotFoundException.class,
                () -> personService.getById(invalidId),
                "Exception was not thrown");
        verify(personRepository, times(1)).findById(validId);
        verify(personRepository, times(1)).findById(invalidId);
    }

    @Test
    @DisplayName("Should return all people saved as DTO")
    public void getAllPersonDtoWhenTherIsSavedPersonInDatabaseTest(){
        Person person1 = new Person(1, "Italo", LocalDate.now(), null, null);
        Person person2 = new Person(2, "Thomas", LocalDate.now(), null, null);
        Person person3 = new Person(3, "Fatima", LocalDate.now(), null, null);
        PersonDto personDto1 = PersonMapper.fromPersonToDto(person1);
        PersonDto personDto2 = PersonMapper.fromPersonToDto(person2);
        PersonDto personDto3 = PersonMapper.fromPersonToDto(person3);
        List<Person> savedPersonList = new ArrayList<>(List.of(person1, person2, person3));
        List<PersonDto> savedPersonDtoList = new ArrayList<>(List.of(personDto1, personDto2, personDto3));

        when(personRepository.findAll()).thenReturn(savedPersonList);

        assertEquals(savedPersonDtoList, personService.getAll(), "A different list was returned");
        verify(personRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return an empty DTO list")
    public void getAllPersonDtoWhenTherIsNotSavedPersonInDatabaseTest(){
        List<Person> savedPersonList = new ArrayList<>();
        List<PersonDto> savedPersonDtoList = new ArrayList<>();

        when(personRepository.findAll()).thenReturn(savedPersonList);

        assertEquals(savedPersonDtoList, personService.getAll(), "A different list was returned");
        verify(personRepository, times(1)).findAll();
    }
}
