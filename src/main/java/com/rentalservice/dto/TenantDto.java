package com.rentalservice.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TenantDto {

    private UUID id;
    @Size(min = 2, max = 100, message = "campo fora do tamanho padrao")
    private String name;
    @NotBlank(message="campo obrigatório")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos numéricos")
    private String cpf;
    @NotBlank(message = "campo obrigatório")
    @Email(message = "e-mail inválido")
    private String email;
    @NotNull(message="campo obrigatório")
    private LocalDate birthDate;
    @NotEmpty(message = "campo obrigatório")
    private List<PhoneDto> phones;

}
