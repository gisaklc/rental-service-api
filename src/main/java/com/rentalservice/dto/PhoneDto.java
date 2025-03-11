package com.rentalservice.dto;


import lombok.Data;

import java.util.UUID;

@Data
public class PhoneDto {

    private UUID id;
    private String ddd;
    private String phoneNumber;

}
