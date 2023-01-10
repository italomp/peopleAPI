package com.api.peopleAPI.services;

import com.api.peopleAPI.models.Address;
import com.api.peopleAPI.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    public AddressService(){}

    public Address save(Address address){
        return addressRepository.save(address);
    }
}
