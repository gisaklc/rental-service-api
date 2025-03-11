package com.rentalservice.model.entity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "apartment")
@EntityListeners(AuditingEntityListener.class)
@ToString(exclude = "rentals") // Evita que 'phones' entre no toString do Tenant
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "number", nullable = false)
    private String number;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ApartmentStatus status;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "apartment")
    private List<Rental> rentals;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
