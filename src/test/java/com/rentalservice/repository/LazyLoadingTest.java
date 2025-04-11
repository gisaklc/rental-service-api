package com.rentalservice.repository;


import com.rentalservice.model.entity.Rental;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LazyLoadingTest {

    @Autowired
    private RentalRepository rentalRepository;

   @Test
    @Transactional
    public void testLazyLoading() {
        // Carregar a entidade principal
        // Carregar a entidade principal sem carregar a coleção de pagamentos
        Rental rental = rentalRepository.findByIdWithoutPayments(UUID.fromString("505bfa3c-36e5-4a41-9353-7599787613c8")).orElse(null);
        assertNotNull(rental);

        // Verificar que a coleção de pagamentos não foi carregada
        System.out.println("Antes de acessar os pagamentos");
        System.out.println("Pagamentos inicializados: " + Hibernate.isInitialized(rental.getPayments()));
        assertFalse(Hibernate.isInitialized(rental.getPayments()));

        // Acessar a coleção de pagamentos para forçar o carregamento
        rental.getPayments().size();
        System.out.println("Depois de acessar os pagamentos");
        System.out.println("Pagamentos inicializados: " + Hibernate.isInitialized(rental.getPayments()));
    }

    @Test
    @Transactional
    public void testEagerLoading() {
        // Carregar a entidade principal
        Rental rental = rentalRepository.findById(UUID.fromString("505bfa3c-36e5-4a41-9353-7599787613c8")).orElse(null);
        assertNotNull(rental);

        // Verificar que a coleção de pagamentos foi carregada
        System.out.println("Pagamentos inicializados: " + Hibernate.isInitialized(rental.getPayments()));
        assertTrue(Hibernate.isInitialized(rental.getPayments()));
    }
}
