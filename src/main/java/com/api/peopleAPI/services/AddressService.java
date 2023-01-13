package com.api.peopleAPI.services;

import com.api.peopleAPI.models.Address;
import com.api.peopleAPI.repositories.AddressRepository;
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
        try{
            return addressRepository.save(address);
        }catch(Exception e){
            e.printStackTrace();
            e.getCause().printStackTrace();
        }
        return null;
    }

    public List<Address> getAll(){
        return addressRepository.findAll();
    }

    public List<Address> getNonDuplicateAlternativeAddressList(
            Address mainAddress, List<Address> alternativeAddressList
    ){
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

    public Address getStoragedMainAddress(List<Address> existentAddressList, Address mainAddress){
        for(Address address: existentAddressList){
            if(address.equals(mainAddress)){
                return address;
            }
        }
        return null;
    }

    public List<Address> getStoragedAlternativeAddress(
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
}
