package com.rentalservice.repository;

import com.rentalservice.model.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TenantRepository extends JpaRepository<Tenant, UUID> {

    Optional<Tenant> findByEmail(String email);

    Optional<Tenant> findByCpf(String email);
}
