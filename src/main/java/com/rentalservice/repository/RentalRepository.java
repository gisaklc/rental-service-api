package com.rentalservice.repository;

import com.rentalservice.model.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RentalRepository  extends JpaRepository<Rental, UUID> {
}
