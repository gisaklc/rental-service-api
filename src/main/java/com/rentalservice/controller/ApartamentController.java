package com.rentalservice.controller;

import com.rentalservice.dto.ApartamentDto;
import com.rentalservice.model.entity.Apartment;
import com.rentalservice.model.entity.ApartmentStatus;
import com.rentalservice.model.mapper.ApartamentMapper;
import com.rentalservice.service.ApartamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/apartaments")
@RequiredArgsConstructor
public class ApartamentController implements GenericController {

    private final ApartamentService apartamentService;
    private final ApartamentMapper mapper;

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<Void> saveApartament(@RequestBody ApartamentDto apartamentDto) {
        Apartment apartment = mapper.toEntity(apartamentDto);

        apartamentService.saveApartament(apartment); // Exceção será tratada pelo ControllerAdvice

        var url = gerarHeaderLocation(apartment.getId());
        return ResponseEntity.created(url).build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ApartamentDto>> findApartmentByStatus(@PathVariable("status") String status) {
        ApartmentStatus apartmentStatus = ApartmentStatus.valueOf(status.toUpperCase());
        List<ApartamentDto> apartamentDtoList = apartamentService.findApartmentByStatus(apartmentStatus)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(apartamentDtoList);
    }
}
