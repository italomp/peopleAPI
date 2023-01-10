package com.api.peopleAPI.models;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

/**
 * @residentList is the list of people who have that address (there may be
 * different people in a family, with the same address).
 *
 * @mainResidentList is the list of people who have this address as their main address.
 *
 * The residentList should to contain the people of mainResidentList
 */
@Entity
@Table(name = "ADDRESS")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;
    @Column(name = "STREET")
    private String street;
    @Column(name = "NUMBER")
    private Integer number;
    @Column(name = "CEP")
    private String cep;
    @Column(name = "CITY")
    private String city;
    @ManyToMany(mappedBy = "addressList")
    private List<Person> residentList;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mainAddress", orphanRemoval = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Person> mainResidentList;

    public Address() {
    }

    public Address(long id, String street, Integer number, String cep, String city) {
        this.id = id;
        this.street = street;
        this.number = number;
        this.cep = cep;
        this.city = city;
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

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
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
}
