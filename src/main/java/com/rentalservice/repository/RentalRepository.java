package com.rentalservice.repository;

import com.rentalservice.dto.RentalProjection;
import com.rentalservice.model.entity.PaymentStatus;
import com.rentalservice.model.entity.Rental;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RentalRepository  extends JpaRepository<Rental, UUID> , JpaSpecificationExecutor<Rental> {

    @Query("SELECT COUNT(r) > 0 FROM Rental r " +
            "WHERE r.tenant.id = :tenantId AND r.endDate IS NULL " +
            "AND r.contractStatus <> 'FINALIZADO'")
    boolean existsActiveRentalByTenantId(@Param("tenantId") UUID tenantId);


    @Query("SELECT r.id as id, r.tenant.id as tenantId, r.apartment.id as apartmentId, r.entryDate as entryDate, r.endDate as endDate, r.monthlyRent as monthlyRent, r.contractStatus as contractStatus, p.referenceMonth as nextDueDate " +
            "FROM Rental r " +
            "JOIN r.payments p " +
            "WHERE p.status != 'PAGO' " +
            "AND p.referenceMonth = (SELECT MIN(p2.referenceMonth) FROM Payment p2 WHERE p2.rental = r AND p2.status != 'PAGO') " +
            "AND ((:status = 'EM_DIA' AND p.referenceMonth > CURRENT_DATE) OR (:status = 'ATRASADO' AND p.referenceMonth <= CURRENT_DATE)) " +
            "ORDER BY r.id, p.referenceMonth")
    Page<RentalProjection> findRentalsByPaymentStatus(@Param("status") String status, Pageable pageable);


    @Query("SELECT r " +
            "FROM Rental r " +
            "JOIN r.payments p " +
            "WHERE p.status != 'PAGO' " +  // Exclui pagamentos pagos
            "AND p.referenceMonth = (" +
            "   SELECT MIN(p2.referenceMonth) " +
            "   FROM Payment p2 " +
            "   WHERE p2.rental = r " +
            "   AND p2.status != 'PAGO' " +  // Exclui pagamentos pagos
            ") " +
            "AND (" +
            "   (:status = 'EM_DIA' AND p.referenceMonth > CURRENT_DATE) " +  // Se for "Em Dia"
            "   OR " +
            "   (:status = 'EM_ATRASO' AND p.referenceMonth <= CURRENT_DATE) " +  // Se for "Atrasado"
            ") " +
            "ORDER BY r.id, p.referenceMonth")
    List<Rental> findRentalsByPaymentStatusAsEntity(@Param("status") String status, Pageable pageable);

    @Query("SELECT r FROM Rental r LEFT JOIN FETCH r.payments WHERE r.id = :id")
    Optional<Rental> findByIdWithPayments(UUID id);

    @Query("SELECT r FROM Rental r WHERE r.id = :id")
    Optional<Rental> findByIdWithoutPayments(UUID id);

}

