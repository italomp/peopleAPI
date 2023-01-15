package com.api.peopleAPI.repositories;

import com.api.peopleAPI.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query(value =
            "SELECT a.id, a.cep, a.city, a.number, a.street " +
            "FROM address a, person p " +
            "WHERE a.id = p.main_address_id AND p.id = :person_id " +
            "UNION " +
            "SELECT a.id, a.cep, a.city, a.number, a.street " +
            "FROM address a, person_address pa " +
            "WHERE a.id = pa.address_id AND pa.person_id = :person_id", nativeQuery = true)
    List<Address> findAllAddressOfPerson(@Param("person_id") long personId);
}
