package com.rentalservice.controller;

import com.rentalservice.dto.RentalDto;
import com.rentalservice.model.entity.Rental;
import com.rentalservice.model.mapper.RentalMapper;
import com.rentalservice.service.RentalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/rentals")
@RequiredArgsConstructor
@Slf4j
public class RentalController {


    private final RentalService rentalService;
    private final RentalMapper rentalMapper;

    @PostMapping
    public ResponseEntity<Object> saveRental(@RequestBody @Valid RentalDto rentalDto) {

        var rental = rentalMapper.toEntity(rentalDto);

        rentalService.saveRental(rental); // Exceção será tratada pelo ControllerAdvice

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
