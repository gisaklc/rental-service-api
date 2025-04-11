package com.rentalservice.validator;

import com.rentalservice.exception.DuplicatedTupleException;
import com.rentalservice.model.entity.ApartmentStatus;
import com.rentalservice.model.entity.Rental;
import com.rentalservice.repository.ApartamentRepository;
import com.rentalservice.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RentalValidator extends ValidadorBase<Rental> {

    private final RentalRepository rentalRepository;
    private final ApartamentRepository apartamentRepository;
    @Override
    public void validar(Rental rental) {

        if (rental.getTenant() == null) {
            throw new IllegalArgumentException("O locatário é obrigatório e não pode ser nulo.");
        }
        if (rental.getApartment() == null) {
            throw new IllegalArgumentException("O apartamento é obrigatório e não pode ser nulo.");
        }

        if(existActiveRental(rental)){
            throw new DuplicatedTupleException("Já existe um contrato ativo para este locatário!");
        }

        if(!rental.getApartment().getStatus().equals(ApartmentStatus.DISPONIVEL)){
            throw new DuplicatedTupleException("Este apartamento já está alugado!");
        }
    }

    private boolean existActiveRental(Rental rental) {
        return rentalRepository.existsActiveRentalByTenantId(rental.getTenant().getId());
    }

}
