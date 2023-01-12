package com.api.peopleAPI.utils;

import com.api.peopleAPI.dtos.AddressDto;
import com.api.peopleAPI.models.Address;
import com.api.peopleAPI.models.Person;

import java.util.ArrayList;
import java.util.List;

public class AddressMapper {
    public static Address fromDtoToAddress(AddressDto addressDto, Person resident){
        if(addressDto == null){
            return null;
        }
        return new Address(
                addressDto.getStreet(),
                addressDto.getNumber(),
                addressDto.getCep(),
                addressDto.getCity(),
                resident);
    }

    public static AddressDto fromAddressToDto(Address address){
        if(address == null){
            return null;
        }
        return new AddressDto(
                address.getStreet(),
                address.getNumber(),
                address.getCep(),
                address.getCity());
    }

    public static List<Address> fromDtoListToAddressList(List<AddressDto> addressDtoList, Person resident){
        if(addressDtoList == null){
            return null;
        }
        List<Address> addressList = new ArrayList<>();
        addressDtoList.forEach(addressDto -> {
            Address address = new Address(
                    addressDto.getStreet(),
                    addressDto.getNumber(),
                    addressDto.getCep(),
                    addressDto.getCity(),
                    resident);
            addressList.add(address);
        });
        return addressList;
    }

    public static List<AddressDto> fromAddressListToDtoList(List<Address> addressList){
        if(addressList == null){
            return null;
        }
        List<AddressDto> addressDtoList = new ArrayList<>();
        addressList.forEach(address -> {
            AddressDto dto = new AddressDto(
                    address.getStreet(),
                    address.getNumber(),
                    address.getCep(),
                    address.getCity());
            addressDtoList.add(dto);
        });
        return addressDtoList;
    }
}
