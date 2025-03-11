package com.rentalservice.repository;

import com.rentalservice.model.entity.Apartment;
import com.rentalservice.model.entity.ApartmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ApartamentRepository extends JpaRepository<Apartment, UUID> {

    boolean existsByNumber(String number);

    List<Apartment> findByStatus(ApartmentStatus status);
}
