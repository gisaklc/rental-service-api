package com.rentalservice.service;

import com.rentalservice.model.entity.Tenant;

import java.util.List;
import java.util.UUID;

interface TenantService {

    Tenant saveTenant(Tenant tenant);

    Tenant updateTenant(Tenant tenant);

    void deleteTenant(UUID id);

    List<Tenant> findAllTenants();
}
