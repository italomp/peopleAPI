package com.api.peopleAPI.services;

import com.api.peopleAPI.dtos.AddressDto;
import com.api.peopleAPI.models.Address;
import com.api.peopleAPI.repositories.AddressRepository;
import com.api.peopleAPI.utils.AddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    public AddressService(){}

    public Address save(Address address){
        return addressRepository.save(address);
    }

    public List<Address> getAll(){
        return addressRepository.findAll();
    }

    public List<Address> getNonDuplicateAlternativeAddressList(
            Address mainAddress, List<Address> alternativeAddressList
    ){
        if(alternativeAddressList == null){
            List<Address> resultList = mainAddress == null ? new ArrayList<>() : new ArrayList<>(List.of(mainAddress));
            return resultList;
        }

        List<Address> copyOfAlternativeAddressList = new ArrayList<>();
        copyOfAlternativeAddressList.addAll(alternativeAddressList);
        List<Integer> duplicateAddressIndexes = new ArrayList<>();

        // Remove a possible alternative address who is equals the mainAddress
        if(copyOfAlternativeAddressList.contains(mainAddress)){
            for(int i = 0; i < copyOfAlternativeAddressList.size(); i++){
                Address currAddress = copyOfAlternativeAddressList.get(i);
                if(currAddress.equals(mainAddress)){
                    duplicateAddressIndexes.add(i);
                }
            }
            // Removing from end to start
            for(int i = duplicateAddressIndexes.size() - 1; i >= 0; i--){
                int index = duplicateAddressIndexes.get(i);
                copyOfAlternativeAddressList.remove(index);
            }
        }

        // Remove duplicate between alternative addresses
        Set<Address> alternativeAddressSet = Set.copyOf(copyOfAlternativeAddressList);
        return new ArrayList<>(alternativeAddressSet);
    }

    public Address getAnEqualsSavedAddress(List<Address> existentAddressList, Address mainAddress){
        for(Address address: existentAddressList){
            if(address.equals(mainAddress)){
                return address;
            }
        }
        return null;
    }

    public List<Address> getSavedAlternativeAddressList(
            List<Address> existentAddressList, List<Address> alternativeAddressList
    ){
        List<Address> result = new ArrayList<>();
        if(alternativeAddressList == null){
            return result;
        }
        for(Address alternativeAddress: alternativeAddressList){
            for(Address existentAddress: existentAddressList){
                if(existentAddress.equals(alternativeAddress)){
                    result.add(existentAddress);
                }
            }
        }
        return result;
    }

    public List<AddressDto> getAllAddressOfPerson(long personId) {
        List<Address> addresOfPerson = addressRepository.findAllAddressOfPerson(personId);
        return AddressMapper.fromAddressListToDtoList(addresOfPerson);
    }
}
