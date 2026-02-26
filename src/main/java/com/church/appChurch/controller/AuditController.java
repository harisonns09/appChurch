package com.church.appChurch.controller;

import com.church.appChurch.model.AuditLog;
import com.church.appChurch.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/audit")
@CrossOrigin(origins = "*") // Importante para evitar erro de CORS vindo do React
public class AuditController {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @GetMapping("/{churchId}")
    public ResponseEntity<Page<AuditLog>> getLogs(
            @PathVariable Long churchId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(auditLogRepository.findAll(pageable));
    }

}
