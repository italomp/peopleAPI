package com.api.peopleAPI.dtos;

import java.io.Serializable;

public class AddressDto implements Serializable {
    private long id;
    private String street;
    private Integer number;
    private Integer cep;
    private String city;

    public AddressDto(){}

    public AddressDto(String street, Integer number, Integer cep, String city) {
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

}
