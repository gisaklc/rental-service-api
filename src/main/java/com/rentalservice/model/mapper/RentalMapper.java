package com.rentalservice.model.mapper;

import com.rentalservice.dto.RentalDto;
import com.rentalservice.model.entity.Rental;
import com.rentalservice.repository.ApartamentRepository;
import com.rentalservice.repository.TenantRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class RentalMapper {
    @Autowired
    TenantRepository tenantRepository;

    @Autowired
    ApartamentRepository apartmentRepository;

    @Mapping(target = "tenant", expression = "java( tenantRepository.findById(dto.tenantId()).orElse(null) )") // Acesse tenantId() no DTO
    @Mapping(target = "apartment", expression = "java( apartmentRepository.findById(dto.apartmentId()).orElse(null) )")
    public abstract Rental toEntity(RentalDto dto);


}
