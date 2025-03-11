package com.rentalservice.service;

import com.rentalservice.model.entity.Apartment;
import com.rentalservice.model.entity.ApartmentStatus;

import java.util.List;

public interface ApartamentService {

     void saveApartament(Apartment apartament);

     List<Apartment> findApartmentByStatus(ApartmentStatus status);
}
