package com.api.peopleAPI.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private LocalDate birthDate;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "PERSON_ADDRESS",
            joinColumns = @JoinColumn(name = "PERSON_ID"),
            inverseJoinColumns = @JoinColumn(name = "ADDRESS_ID"))
    private List<Address> addressList;

    public Person() {
    }

    public Person(String name, LocalDate birthDate) {
        this.name = name;
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public long getId() {
        return id;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void addNewAddress(Address newAddress){
        this.addressList.add(newAddress);
    }
}
