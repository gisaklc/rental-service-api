package com.rentalservice.exception;

import com.rentalservice.dto.ErroCampo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {
    private String error;
    private String message;
    private int status;
    private List<ErroCampo> erros;
}
