package com.rentalservice.validator;

import com.rentalservice.exception.ValidacaoException;

import java.time.LocalDate;
import java.util.List;

public class DateValidator {

    public static void validateDate(LocalDate date) {
        if (date == null) {
            throw new ValidacaoException("Data não pode ser nula.");
        }

        // Validar se a data é válida (não antes de 1900 e não no futuro)
        if (date.isBefore(LocalDate.of(1900, 1, 1)) || date.isAfter(LocalDate.now())) {
            throw new ValidacaoException("Data inválida: " + date);
        }
    }

    public static void validateDateList(List<LocalDate> dates) {
        if (dates == null || dates.isEmpty()) {
            throw new ValidacaoException("A lista de datas não pode ser vazia.");
        }

        for (LocalDate date : dates) {
            validateDate(date); // reutilizando a validação de data
        }
    }

}
