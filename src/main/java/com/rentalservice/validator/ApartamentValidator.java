package com.rentalservice.validator;

import com.rentalservice.exception.DuplicatedTupleException;
import com.rentalservice.exception.ValidacaoException;
import com.rentalservice.model.entity.Apartment;
import com.rentalservice.repository.ApartamentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApartamentValidator extends ValidadorBase<Apartment> {

    private final ApartamentRepository apartamentRepository;

    @Override
    public void validar(Apartment apartment) {

        if (apartment == null) {
            throw new ValidacaoException("Objeto não pode ser nulo");
        }

        if (apartment.getNumber() == null || apartment.getNumber().isEmpty()) {
            throw new ValidacaoException("O Número do apartamento não pode estar vazio");
        }

        if(existApartamentByNumer(apartment)){
            throw new DuplicatedTupleException("Já existe um Apartamento registrado para este número ");
        }
    }


    private boolean existApartamentByNumer(Apartment apartamentDto) {
        return apartamentRepository.existsByNumber(apartamentDto.getNumber());
    }
}
