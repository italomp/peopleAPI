package com.api.peopleAPI.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    @JsonBackReference
    private List<Person> residentList;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mainAddress", orphanRemoval = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Person> mainResidentList;

    public Address() {
    }

    public Address(String street, Integer number, Integer cep, String city, Person person) {
        this.street = street;
        this.number = number;
        this.cep = cep;
        this.city = city;
        residentList = new ArrayList<>();
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

    public void addResident(Person person) {
        if(person.getMainAddress() == null){
            mainResidentList.add(person);
        }
        else{
            residentList.add(person);
        }
    }
}
