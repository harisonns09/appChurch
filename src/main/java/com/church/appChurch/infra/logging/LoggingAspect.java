package com.church.appChurch.infra.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // MUDANÇA AQUI: Removi service e repository. Deixei apenas controller.
    // Isso foca na "Entrada e Saída" da API.
    @Around("execution(* com.church.appChurch.controller..*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        final StopWatch stopWatch = new StopWatch();

        // Pega o nome curto do método (ex: PessoaController.getMembros)
        String methodName = joinPoint.getSignature().toShortString();

        // Opcional: Se quiser limpar mais, remova os argumentos do log de entrada
        logger.info("REQ  -> {}", methodName);

        stopWatch.start();
        Object proceed = joinPoint.proceed();
        stopWatch.stop();

        // Loga o tempo total. Se for muito rápido, nem precisaria logar, mas é bom manter.
        logger.info("RESP <- {} :: {} ms", methodName, stopWatch.getTotalTimeMillis());

        return proceed;
    }
}