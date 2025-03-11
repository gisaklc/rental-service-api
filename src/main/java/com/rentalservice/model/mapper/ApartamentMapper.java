package com.rentalservice.model.mapper;


import com.rentalservice.dto.ApartamentDto;
import com.rentalservice.model.entity.Apartment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ApartamentMapper {

    Apartment toEntity(ApartamentDto dto);

    ApartamentDto toDto(Apartment apartment);

}
