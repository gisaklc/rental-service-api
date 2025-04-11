package com.rentalservice.validator;

import com.rentalservice.exception.DuplicatedTupleException;
import com.rentalservice.exception.ValidacaoException;
import com.rentalservice.model.entity.Apartment;
import com.rentalservice.repository.ApartamentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
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


    public boolean existApartamentByNumer(Apartment apartamentDto) {

        log.info(STR."Numero do Apto a ser consultado: \{apartamentDto.getNumber()}");

        return apartamentRepository.existsByNumber(apartamentDto.getNumber());
    }
}
