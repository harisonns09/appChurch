package com.church.appChurch.infra.audit;

import com.church.appChurch.model.AuditLog;
import com.church.appChurch.model.Usuario;
import com.church.appChurch.repository.AuditLogRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditAspect {
    @Autowired
    private AuditLogRepository auditLogRepository;

    @AfterReturning(pointcut = "@annotation(loggable)", returning = "result")
    public void logAuditoria(JoinPoint joinPoint, Loggable loggable, Object result) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = (authentication != null) ? authentication.getName() : "SISTEMA";
            Long churchId = 0L;
            if(authentication != null && authentication.getPrincipal() instanceof Usuario){
                churchId = ((Usuario) authentication.getPrincipal()).getIgreja().getId();
            }

            AuditLog log = new AuditLog();
            log.setAction(loggable.action());
            log.setEntityName(loggable.entity());
            log.setUsername(username);
            log.setChurchId(churchId);

            if(result != null){
                log.setDetails("Operação realizada com sucesso");
            }


            auditLogRepository.save(log);


        } catch (Exception e) {
            System.err.println("Erro ao salvar log de auditoria: " + e.getMessage());
        }
    }
}
