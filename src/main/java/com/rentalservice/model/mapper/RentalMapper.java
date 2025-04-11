package com.rentalservice.model.mapper;

import com.rentalservice.dto.RentalDto;
import com.rentalservice.dto.RentalSearchResponseDTO;
import com.rentalservice.model.entity.Rental;
import com.rentalservice.repository.ApartamentRepository;
import com.rentalservice.repository.TenantRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {PaymentMapper.class, TenantMapper.class, ApartamentMapper.class})

public abstract class RentalMapper {
    @Autowired
    TenantRepository tenantRepository;

    @Autowired
    ApartamentRepository apartmentRepository;

    @Mapping(target = "tenant", expression = "java( tenantRepository.findById(dto.tenantId()).orElse(null) )") // Acesse tenantId() no DTO
    @Mapping(target = "apartment", expression = "java( apartmentRepository.findById(dto.apartmentId()).orElse(null) )")
    @Mapping(target = "payments", source = "payments") // O MapStruct usará PaymentMapper para mapear a lista
    public abstract Rental toEntity(RentalDto dto);

    @Mapping(target = "tenantName", source = "tenant.name")  // Mapeia corretamente o nome do locatário
    @Mapping(target = "apartmentNumber", source = "apartment.number")  // Mapeia o número do apartamento
    public abstract RentalSearchResponseDTO toDTO(Rental rental);

}
