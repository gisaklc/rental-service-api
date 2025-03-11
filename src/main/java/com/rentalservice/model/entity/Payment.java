package com.rentalservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.UUID;

@Entity
@Table(name = "payment")
@Data
@EntityListeners(AuditingEntityListener.class)
@Builder(toBuilder = true) // Permite modificar objetos existentes
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "rental_id")
    private Rental rental;

    private LocalDate paymentData;

    private LocalDate referenceMonth;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String paymentMethod;


}
