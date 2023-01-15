package com.api.peopleAPI.models;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @residentList is the list of people who have the address (there may be
 * different people in a family, with the same address) minus the main residents.
 *
 * @mainResidentList is the list of people who have this address as their main address.
 */
@Entity
@Table(name = "ADDRESS")
public class Address implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;
    @Column(name = "STREET")
    private String street;
    @Column(name = "NUMBER")
    private Integer number;
    @Column(name = "CEP")
    private Integer cep;
    @Column(name = "CITY")
    private String city;
    @ManyToMany(mappedBy = "alternativeAddressList")
    private List<Person> residentList;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mainAddress", orphanRemoval = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Person> mainResidentList;

    public Address() {
        residentList = new ArrayList<>();
        mainResidentList = new ArrayList<>();
    }

    public Address(String street, Integer number, Integer cep, String city, Person person) {
        this.street = street;
        this.number = number;
        this.cep = cep;
        this.city = city;
        residentList = new ArrayList<>();
        mainResidentList = new ArrayList<>();
        addResident(person);
    }

    public Address(long id, String street, Integer number, Integer cep, String city, Person person) {
        this.id = id;
        this.street = street;
        this.number = number;
        this.cep = cep;
        this.city = city;
        residentList = new ArrayList<>();
        mainResidentList = new ArrayList<>();
        addResident(person);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getCep() {
        return cep;
    }

    public void setCep(Integer cep) {
        this.cep = cep;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Person> getResidentList() {
        return residentList;
    }

    public void setResidentList(List<Person> residentList) {
        this.residentList = residentList;
    }

    public List<Person> getMainResidentList() {
        return mainResidentList;
    }

    public void setMainResidentList(List<Person> mainResidentList) {
        this.mainResidentList = mainResidentList;
    }

    public void addResident(Person person) {
        if(person == null) return;
        if(person.getMainAddress() == null){
            mainResidentList.add(person);
        }
        else{
            residentList.add(person);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return street.equals(address.street) && number.equals(address.number) &&
                cep.equals(address.cep) && city.equals(address.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, number, cep, city);
    }
}
