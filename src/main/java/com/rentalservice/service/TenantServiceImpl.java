package com.rentalservice.service;

import com.rentalservice.model.entity.Tenant;
import com.rentalservice.model.entity.User;
import com.rentalservice.repository.TenantRepository;
import com.rentalservice.security.SecurityContextService;
import com.rentalservice.validator.TenantValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TenantServiceImpl implements TenantService {

    private final TenantRepository tenantRepository;

    private final TenantValidator validator;
    private final SecurityContextService contextService;

    @Override
    @Transactional
    public Tenant saveTenant(Tenant tenant) {
        validator.validar(tenant);

        User user = contextService.getLoggedInUser();
        tenant.setUser(user);

        return tenantRepository.save(tenant);
    }

    public Optional<Tenant> obterPorId(UUID uuid){
        return tenantRepository.findById(uuid);
    }


    @Override
    @Transactional
    public Tenant updateTenant(Tenant tenant) {

        validator.validar(tenant);

        return tenantRepository.save(tenant);

    }

    void updatePhones(Tenant tenant, Tenant existingTenant) {
        tenant.getPhones().forEach(newPhone -> {
            existingTenant.getPhones().forEach(existingPhone -> {
                if (existingPhone.getId().equals(newPhone.getId())) {
                    existingPhone.setDdd(newPhone.getDdd());
                    existingPhone.setPhoneNumber(newPhone.getPhoneNumber());
                }
            });
        });
    }

    @Override
    public void deleteTenant(UUID uuid) {
        // Deletar Tenant pelo ID
        Tenant existingTenant = obterPorId(uuid)
                .orElseThrow(() -> new IllegalStateException("Tenant with id " + uuid + " not found"));
        tenantRepository.delete(existingTenant);
    }

    @Override
    public List<Tenant> findAllTenants() {
        return tenantRepository.findAll();
    }

}
