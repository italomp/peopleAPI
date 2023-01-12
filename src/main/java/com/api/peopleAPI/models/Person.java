package com.api.peopleAPI.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "PERSON")
public class Person implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;
    @Column(name = "NAME")
    @NotNull(message = "Person name can't be null")
    private String name;
    @Column(name = "BIRTH_DATE")
    @NotNull(message = "Person birth date can't be null")
    private LocalDate birthdate;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "PERSON_ADDRESS",
            joinColumns = @JoinColumn(name = "PERSON_ID"),
            inverseJoinColumns = @JoinColumn(name = "ADDRESS_ID"))
    private List<Address> alternativeAddressList;
    @ManyToOne
    @JoinColumn(name = "MAIN_ADDRESS_ID")
    private Address mainAddress;

    public Person() {
    }

    public Person(String name, LocalDate birthdate, Address mainAddress, List<Address> alternativeAddressList) {
        this.name = name;
        this.birthdate = birthdate;
        this.mainAddress = mainAddress;
        this.alternativeAddressList = alternativeAddressList;
    }

    public Person(long id, String name, LocalDate birthdate, Address mainAddress, List<Address> alternativeAddressList) {
        this.id = id;
        this.name = name;
        this.birthdate = birthdate;
        this.mainAddress = mainAddress;
        this.alternativeAddressList = alternativeAddressList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Address> getAlternativeAddressList() {
        return alternativeAddressList;
    }

    public void setAlternativeAddressList(List<Address> alternativeAddressList) {
        this.alternativeAddressList = alternativeAddressList;
    }

    public void addNewAddress(Address newAddress){
        this.alternativeAddressList.add(newAddress);
    }

    public Address getMainAddress() {
        return mainAddress;
    }

    public void setMainAddress(Address mainAddress) {
        this.mainAddress = mainAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return name.equals(person.name) && birthdate.equals(person.birthdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthdate);
    }
}
