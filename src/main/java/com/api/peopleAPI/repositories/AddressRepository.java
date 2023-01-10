package com.api.peopleAPI.repositories;

import com.api.peopleAPI.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
