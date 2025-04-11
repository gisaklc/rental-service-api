package com.rentalservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public interface RentalProjection {

    UUID getId();
    UUID getTenantId();
    UUID getApartmentId();
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate getEntryDate();
    BigDecimal getMonthlyRent();
    String getContractStatus();
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate getNextDueDate();

}
