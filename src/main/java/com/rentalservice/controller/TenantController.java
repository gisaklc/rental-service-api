package com.rentalservice.controller;

import com.rentalservice.dto.TenantDto;
import com.rentalservice.model.entity.Phone;
import com.rentalservice.model.entity.Tenant;
import com.rentalservice.model.mapper.TenantMapper;
import com.rentalservice.service.TenantServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/v1/tenants")
@RequiredArgsConstructor
public class TenantController implements GenericController {


    private final TenantServiceImpl tenantService;
    private final TenantMapper mapper;


    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Void> saveTenant(@RequestBody @Valid TenantDto tenantDto, Authentication authentication) {
        Tenant tenant = mapper.toEntity(tenantDto);
        tenantService.saveTenant(tenant);
        var url = gerarHeaderLocation(tenant.getId());
        return ResponseEntity.created(url).build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Object> updateTenant(@PathVariable("id") String id, @RequestBody @Valid TenantDto tenantDTO) {
        var tenantId = UUID.fromString(id);
        Optional <Tenant> tenantOptional = tenantService.obterPorId(tenantId);

        if (tenantOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var tenant = tenantOptional.get();

        updateTenantFromDto(tenantDTO, tenant);

        tenantService.updateTenant(tenant);

        return ResponseEntity.noContent().build();
    }

    public void updateTenantFromDto(TenantDto tenantDTO, Tenant tenant) {
        tenant.setName(tenantDTO.getName());
        tenant.setCpf(tenantDTO.getCpf());
        tenant.setEmail(tenantDTO.getEmail());
        tenant.setBirthDate(tenantDTO.getBirthDate());

        tenantDTO.getPhones().forEach(phoneDto -> {
            if (phoneDto.getId() != null) {
                Phone existingPhone = tenant.getPhones().stream()
                        .filter(phone -> phone.getId().equals(phoneDto.getId()))
                        .findFirst()
                        .orElse(null);

                if (existingPhone != null) {
                    existingPhone.setDdd(phoneDto.getDdd());
                    existingPhone.setPhoneNumber(phoneDto.getPhoneNumber());
                } } else {
                // Lança uma exceção caso o telefone não seja encontrado
                throw new EntityNotFoundException("Telefone não encontrado com o ID: " + phoneDto.getId());
            }

        });

    }

}
