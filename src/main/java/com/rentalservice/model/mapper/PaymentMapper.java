package com.rentalservice.model.mapper;

import com.rentalservice.dto.PaymentDto;
import com.rentalservice.model.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    @Mapping(target = "paymentDate", source = "paymentDate")
    Payment toEntity(PaymentDto dto);

    List<Payment> toEntity(List<PaymentDto> dtoList);
}
