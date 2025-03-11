package com.rentalservice.model.mapper;

import com.rentalservice.dto.TenantDto;
import com.rentalservice.model.entity.Tenant;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TenantMapper  {

    @Mapping(target = "phones", source = "phones")
    Tenant toEntity(TenantDto dto);

    TenantDto toDto(Tenant tenant);

    @AfterMapping
    default void setTenantInPhones(@MappingTarget Tenant tenant) {
        if (tenant.getPhones() != null) {
            tenant.getPhones().forEach(phone -> phone.setTenant(tenant));
        }
    }
}
