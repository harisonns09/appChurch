package com.church.appChurch.infra.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class RequestIdFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            // Gera um ID único para a requisição
            String requestId = UUID.randomUUID().toString();
            MDC.put("requestId", requestId);

            // Adiciona no header da resposta para o Frontend saber qual foi o ID
            ((HttpServletResponse) response).setHeader("X-Request-ID", requestId);

            chain.doFilter(request, response);
        } finally {
            MDC.clear(); // Limpa para não sujar a thread pool
        }
    }
}