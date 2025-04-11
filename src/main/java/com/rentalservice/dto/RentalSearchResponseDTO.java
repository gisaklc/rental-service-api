package com.rentalservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rentalservice.model.entity.ContractStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record RentalSearchResponseDTO(
        UUID id,
        String apartmentNumber,
        String tenantName,
        ContractStatus contractStatus,
        BigDecimal monthlyRent,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate entryDate
) {}