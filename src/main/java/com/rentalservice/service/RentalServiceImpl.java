package com.rentalservice.service;

import com.rentalservice.dto.RentalProjection;
import com.rentalservice.dto.RentalSearchResponseDTO;
import com.rentalservice.model.entity.ApartmentStatus;
import com.rentalservice.model.entity.Payment;
import com.rentalservice.model.entity.PaymentStatus;
import com.rentalservice.model.entity.Rental;
import com.rentalservice.repository.ApartamentRepository;
import com.rentalservice.repository.PaymentRepository;
import com.rentalservice.repository.RentalRepository;
import com.rentalservice.repository.specs.RentalSpecs;
import com.rentalservice.validator.RentalValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import static com.rentalservice.repository.specs.RentalSpecs.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class RentalServiceImpl implements RentalService{

    private final RentalRepository rentalRepository;
    private final PaymentRepository paymentRepository;
    private final ApartamentRepository apartmentRepository;
    private final RentalValidator validator;


    @Override
    public void saveRental(Rental rental) {
        log.info("Iniciando processo de salvamento do aluguel...");
        validator.validar(rental);

        rental.getPayments().forEach(payment -> payment.setRental(rental)); //associando o rental


        rentalRepository.save(rental);

        var newPayments = generateRemainingPayments(rental);
        if (!newPayments.isEmpty()) {
            rental.getPayments().addAll(newPayments);
            paymentRepository.saveAll( rental.getPayments());
        }

        updateApartamentStatus(rental);

        log.info("Aluguel salvo com sucesso! Total de pagamentos registrados: {}", rental.getPayments().size());
    }



    private List<Payment> generateRemainingPayments(Rental rental) {
        var lastPayment = rental.getPayments().getLast().getReferenceMonth();
        var monthValue = lastPayment.getMonthValue();
        var dayValue = lastPayment.getDayOfMonth();
        var year = lastPayment.getYear();
        var remainingMonths = ( 12 - rental.getPayments().size() ) + 1;

        if (remainingMonths <= 0) {
            log.info("Nenhum pagamento pendente a ser gerado.");
            return List.of();
        }

        List<Payment> newPayments = new ArrayList<>();
        for (int i = 0; i < remainingMonths; i++) {
            monthValue = (monthValue % 12) + 1; // Se for 12, volta para 1 (janeiro)
            if (monthValue == 1) year++; // Se virou janeiro, incrementa o ano

            newPayments.add(Payment.builder()
                    .status(PaymentStatus.PENDENTE)
                    .referenceMonth(LocalDate.of(year, monthValue, dayValue))
                    .rental(rental)
                    .build());
        }
        return newPayments;
    }

    private void updateApartamentStatus(Rental rental) {
        rental.getApartment().setStatus(ApartmentStatus.OCUPADO);
        apartmentRepository.save(rental.getApartment());
    }

    @Override
    public Page<RentalProjection> findRentals(Date paymentDate,
                                              PaymentStatus status,
                                              UUID tenandId,
                                              Integer pagina,
                                              Integer tamanhoPagina) {


        Specification<Rental> specs = Specification.where((root, query, cb) -> cb.conjunction() );
        Pageable pageable = PageRequest.of(pagina, tamanhoPagina);
        if(status != null){

            List<Rental> rentals = rentalRepository.findRentalsByPaymentStatusAsEntity(status.name(), pageable);


            return rentalRepository.findRentalsByPaymentStatus(status.name(), pageable);

         //   specs = specs.and(byPaymentStatus(status));
        }


        return null;
    }

    @Transactional
    @Override
    public Page<Rental> searchRentals(String tenantName, String apartmentNumber, String contractStatus, int page, int size) {

        Specification<Rental> specs = Specification.where((root, query, cb) -> cb.conjunction() );

        if (tenantName != null && !tenantName.isEmpty()) {
            specs = specs.and(RentalSpecs.byTenantName(tenantName));
        }

        if (apartmentNumber != null && !apartmentNumber.isEmpty()) {
            specs = specs.and(RentalSpecs.byApartmentNumber(apartmentNumber));
        }

        if (contractStatus != null && !contractStatus.isEmpty()) {
            specs = specs.and(RentalSpecs.byContractStatus(contractStatus));
        }

        Pageable pageable = PageRequest.of(page, size);

        return rentalRepository.findAll(specs, pageable);
    }


}
