package com.rentalservice.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "rental")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "payments")
@EntityListeners(AuditingEntityListener.class)
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name= "tenant_id", nullable = false)
    private Tenant tenant;

    @ManyToOne
    @JoinColumn(name= "apartment_id", nullable = false)
    private Apartment apartment;

    @OneToMany(mappedBy = "rental")
    private List<Payment> payments;

    private LocalDate entryDate;

    private LocalDate endDate;

    private BigDecimal monthlyRent;

    @Enumerated(EnumType.STRING)
    private ContractStatus contractStatus;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "update_date")
    private LocalDateTime updateDate;

}
