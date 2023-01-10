package com.api.peopleAPI.models;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "PERSON")
public class Person implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "BIRTH_DATE")
    private LocalDate birthDate;
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

    public Person(String name, LocalDate birthDate, Address mainAddress, List<Address> alternativeAddressList) {
        this.name = name;
        this.birthDate = birthDate;
        this.mainAddress = mainAddress;
        this.alternativeAddressList = alternativeAddressList;
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
}
