package com.rentalservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;


public record UserDto(
        @NotBlank(message = "campo obrigatorio")
        String name,
        @NotBlank(message = "campo obrigatorio")
        String password,
        @Email(message = "email inválido")
        String email,
        List<String> roles,
        Boolean active
) {
}
