package com.church.appChurch.repository;

import com.church.appChurch.model.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog,Long> {

    // Buscar logs de uma igreja específica, ordenados por data
    Page<AuditLog> findByChurchIdOrderByTimestampDesc(Long churchId, Pageable pageable);

    // Buscar logs de um usuário específico dentro da igreja
    Page<AuditLog> findByChurchIdAndUsernameOrderByTimestampDesc(Long churchId, String username, Pageable pageable);
}
