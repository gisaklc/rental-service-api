package com.rentalservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rentalservice.model.entity.PaymentStatus;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PaymentDto (

    @NotNull(message = "O mês de referência é obrigatório")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate referenceMonth,

    @NotNull(message = "A data do pagamento é obrigatória")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate paymentDate,

    @NotNull(message = "O status do pagamento é obrigatório")
    PaymentStatus status,

    String paymentMethod

) {

    }
