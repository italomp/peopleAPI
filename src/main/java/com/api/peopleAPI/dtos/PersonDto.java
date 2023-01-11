package com.api.peopleAPI.dtos;

import com.api.peopleAPI.models.Address;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class PersonDto implements Serializable {
    private long id;
    private String name;
    private String birthdate;
    private List<Address> alternativeAddressList;
    private Address mainAddress;

    public PersonDto() {
    }

    public PersonDto(String name, String birthdate, Address mainAddress, List<Address> alternativeAddressList) {
        this.name = name;
        this.birthdate = birthdate;
        this.mainAddress = mainAddress;
        this.alternativeAddressList = alternativeAddressList;
    }

    public PersonDto(long id, String name, String birthdate, Address mainAddress, List<Address> alternativeAddressList) {
        this.id = id;
        this.name = name;
        this.birthdate = birthdate;
        this.mainAddress = mainAddress;
        this.alternativeAddressList = alternativeAddressList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public List<Address> getAlternativeAddressList() {
        return alternativeAddressList;
    }

    public void setAlternativeAddressList(List<Address> addressList) {
        this.alternativeAddressList = addressList;
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
        PersonDto personDto = (PersonDto) o;
        return id == personDto.id && name.equals(personDto.name) && birthdate.equals(personDto.birthdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthdate);
    }
}
