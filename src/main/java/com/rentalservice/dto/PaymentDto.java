package com.rentalservice.dto;

import com.rentalservice.model.entity.PaymentStatus;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PaymentDto (

    @NotNull(message = "O mês de referência é obrigatório")
    LocalDate referenceMonth,

    @NotNull(message = "A data do pagamento é obrigatória")
    LocalDate paymentDate,

    @NotNull(message = "O status do pagamento é obrigatório")
    PaymentStatus status,

    String paymentMethod

) {

    }
