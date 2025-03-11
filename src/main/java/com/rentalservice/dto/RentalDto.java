package com.rentalservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record RentalDto(

        @NotNull(message = "Data de entrada é obrigatória")
        LocalDate entryDate,

        @NotNull(message = "ID do Inquilino é obrigatório")
        UUID tenantId,

        @NotNull(message = "ID do apartamento é obrigatório")
        UUID apartmentId,

        @NotNull(message = "Valor do aluguel é obrigatório")
        BigDecimal monthlyRent,

        @NotNull(message = "Status do contrato é obrigatório")
        String contractStatus,

        @NotNull(message = "Meses de pagamento são obrigatórios")
        @Size(min = 1, message = "Pelo menos um mês de pagamento deve ser selecionado")
        List<PaymentDto> payments

) {

}
