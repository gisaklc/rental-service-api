package com.rentalservice.repository.specs;

import com.rentalservice.model.entity.*;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Essa subquery encontra o
 * primeiro pagamento pendente
 * (com a menor referência) para cada aluguel.
 **/


public class RentalSpecs {

    public static Specification<Rental> byTenantName(String tenantName) {
        return (root, query, cb) -> {
            String likePattern = "%" + tenantName.toLowerCase() + "%";
            return cb.like(cb.lower(root.get("tenant").get("name")), likePattern);
        };
    }

    public static Specification<Rental> byApartmentNumber(String apartmentNumber) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("apartment").get("number")), "%" + apartmentNumber.toLowerCase() + "%");
    }

    public static Specification<Rental> byContractStatus(String contractStatus) {
        return (root, query, cb) -> cb.equal(root.get("contractStatus"), contractStatus);
    }
}

