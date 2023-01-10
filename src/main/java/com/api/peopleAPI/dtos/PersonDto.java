package com.api.peopleAPI.dtos;

import com.api.peopleAPI.models.Address;

import java.io.Serializable;
import java.util.List;

public class PersonDto implements Serializable {
    private long id;
    private String name;
    private String birthDate;
    private List<Address> alternativeAddressList;
    private Address mainAddress;

    public PersonDto() {
    }

    public PersonDto(String name, String birthDate, Address mainAddress, List<Address> alternativeAddressList) {
        this.name = name;
        this.birthDate = birthDate;
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
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
}
