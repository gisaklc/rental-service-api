package com.rentalservice.validator;

import com.rentalservice.exception.DuplicatedTupleException;
import com.rentalservice.model.entity.Tenant;
import com.rentalservice.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class TenantValidator extends ValidadorBase<Tenant> {

    private final TenantRepository tenantRepository;

    public void validar(Tenant tenant){

        if(existTenantCpf(tenant)){
            throw new DuplicatedTupleException("Cpf já cadastrado!");
        }

        if(existTenantEmail(tenant)){
            throw new DuplicatedTupleException("Email já cadastrado!");
        }

    }

    private boolean existTenantEmail(Tenant tenant){

        if (tenant.getId() == null) {
            return tenantRepository.findByEmail(tenant.getEmail()).isPresent();
        }

        return tenantRepository.findByEmail(tenant.getEmail())
                .map(Tenant::getId)
                .map(id -> !id.equals(tenant.getId()))
                .orElse(false);  // Se o email não for encontrado, assume-se que é válido
    }

    private boolean existTenantCpf(Tenant tenant) {
        if (tenant.getId() == null) {
            return tenantRepository.findByCpf(tenant.getCpf()).isPresent();
        }

        return tenantRepository.findByCpf(tenant.getCpf())
                .map(Tenant::getId)
                .map(id -> !id.equals(tenant.getId()))
                .orElse(false);  // Se o CPF não for encontrado, assume-se que é válido
    }

}
