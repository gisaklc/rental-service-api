package com.rentalservice.controller;

import com.rentalservice.model.entity.Client;
import com.rentalservice.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/clients")
@RequiredArgsConstructor
@Slf4j
public class ClientController {

    private final ClientService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public void save(@RequestBody Client client){
        log.info("Registrando novo Client: {} com scope: {} ", client.getClientId(), client.getScope());
        service.save(client);
    }
}
