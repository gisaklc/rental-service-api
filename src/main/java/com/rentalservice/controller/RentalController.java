package com.rentalservice.controller;

import com.rentalservice.dto.RentalDto;
import com.rentalservice.dto.RentalProjection;
import com.rentalservice.dto.RentalSearchResponseDTO;
import com.rentalservice.model.entity.ContractStatus;
import com.rentalservice.model.entity.PaymentStatus;
import com.rentalservice.model.entity.Rental;
import com.rentalservice.model.mapper.RentalMapper;
import com.rentalservice.service.RentalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

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

    @GetMapping
   // @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<Page<RentalProjection>> findRentals(
            @RequestParam(value = "paymentDate", required = false)
            Date paymentDate,
            @RequestParam(value = "status", required = false)
            PaymentStatus status,
            @RequestParam(value = "tenant-id", required = false)
            UUID tenantId,
            @RequestParam(value = "pagina", defaultValue = "0")
            Integer pagina,
            @RequestParam(value = "tamanho-pagina", defaultValue = "10")
            Integer tamanhoPagina
    ){
        Page<RentalProjection> paginaResultado = rentalService.findRentals(
                paymentDate, status, tenantId, pagina, tamanhoPagina);

        return ResponseEntity.ok(paginaResultado);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<RentalSearchResponseDTO>> searchRentals(
            @RequestParam(value = "tenant-name", required = false) String tenantName,
            @RequestParam(value = "apartment-number", required = false) String apartmentNumber,
            @RequestParam(value = "contract-status", required = false) ContractStatus contractStatus,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "tamanho-pagina", defaultValue = "2") int size) {

        Page<Rental> rentalPage = rentalService.searchRentals(tenantName, apartmentNumber, contractStatus.name(), page, size);

        Page<RentalSearchResponseDTO> rentalSearchResponseDTOPage = rentalPage.map(rentalMapper::toDTO);

        return ResponseEntity.ok(rentalSearchResponseDTOPage);
    }


}
