package com.rentalservice.model.mapper;

import com.rentalservice.dto.UserDto;
import com.rentalservice.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "active", target = "active")
    User toEntity(UserDto dto);
}
