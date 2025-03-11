package com.rentalservice.model.mapper;

import com.rentalservice.dto.PhoneDto;
import com.rentalservice.model.entity.Phone;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PhoneMapper {

    @Mapping(target = "tenant", ignore = true) // Ignora o campo tenant durante o mapeamento do PhoneDto
    Phone toEntity(PhoneDto phoneDto);

    PhoneDto toDto(Phone phone);


}