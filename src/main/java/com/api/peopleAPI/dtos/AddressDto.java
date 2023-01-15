package com.api.peopleAPI.dtos;

import java.io.Serializable;
import java.util.Objects;

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

    public AddressDto(long id, String street, Integer number, Integer cep, String city) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressDto that = (AddressDto) o;
        return street.equals(that.street) && number.equals(that.number) && cep.equals(that.cep) && city.equals(that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, number, cep, city);
    }
}
