package com.rentalservice.service;

import com.rentalservice.model.entity.Apartment;
import com.rentalservice.model.entity.ApartmentStatus;
import com.rentalservice.model.entity.Rental;
import com.rentalservice.model.mapper.RentalMapper;
import com.rentalservice.repository.ApartamentRepository;
import com.rentalservice.repository.PaymentRepository;
import com.rentalservice.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RentalServiceImpl implements RentalService{

    private final RentalRepository rentalRepository;
    private final PaymentRepository paymentRepository;
    private final RentalMapper rentalMapper;
    private final ApartamentRepository apartmentRepository;

    @Override
    public void saveRental(Rental rental) {

        rental.getPayments().forEach(payment -> {
            payment.setRental(rental);  // Associar o rental ao pagamento
            payment.setPaymentData(LocalDate.now());  // Alterar a data do pagamento
        });

        rentalRepository.save(rental);

        paymentRepository.saveAll(rental.getPayments());
        rental.getApartment().setStatus(ApartmentStatus.OCUPADO);
        apartmentRepository.save(rental.getApartment());
    //alterar o statuds do apartamento para alugagado
        log.info("Rental Salvo com Sucesso!");
    }
}
