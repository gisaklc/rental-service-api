package com.rentalservice.service;


import com.rentalservice.dto.RentalProjection;
import com.rentalservice.model.entity.PaymentStatus;
import com.rentalservice.model.entity.Rental;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.UUID;

public interface RentalService {

    void saveRental(Rental rental);

    Page<RentalProjection> findRentals(Date paymentDate,
                                       PaymentStatus status,
                                       UUID tenantId,
                                       Integer pagina,
                                       Integer tamanhoPagina);


    Page<Rental> searchRentals(String tenantName, String apartmentNumber, String contractStatus, int page, int size) ;


}
