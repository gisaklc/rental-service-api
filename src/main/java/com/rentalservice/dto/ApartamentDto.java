package com.rentalservice.dto;

import jakarta.validation.constraints.NotBlank;


public record ApartamentDto(
        @NotBlank(message = "campo obrigatório")
        String number,

        @NotBlank(message = "campo obrigatório")
        String status,

        String description

) {
}

