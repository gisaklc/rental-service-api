package com.rentalservice.service;

import com.rentalservice.model.entity.Apartment;
import com.rentalservice.model.entity.ApartmentStatus;
import com.rentalservice.repository.ApartamentRepository;
import com.rentalservice.validator.ApartamentValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApartmentServiceImpl implements ApartamentService{

    private final ApartamentRepository apartamentRepository;

    private final ApartamentValidator apartamentValidator;

    @Override
    public void saveApartament(Apartment apartment) {

        apartamentValidator.validar(apartment);

        apartamentRepository.save(apartment);
    }

    @Override
    public List<Apartment> findApartmentByStatus(ApartmentStatus status) {
        return apartamentRepository.findByStatus(status);
    }
}
