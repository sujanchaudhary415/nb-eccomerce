package com.eccomerce.nb_eccomerce.repository;

import com.eccomerce.nb_eccomerce.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address,Long> {
    Optional<Address> findByAddressIdAndUserUserId(
            Long addressId,
            Long userId
    );
    List<Address> findByUserUserId(Long userId);
}
