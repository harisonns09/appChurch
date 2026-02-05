package com.church.appChurch.model.dto;

import java.math.BigDecimal;

public record CheckoutRequestDTO(
        String nome,
        String email,
        String telefone,
        String cpf,
        String numeroInscricao,
        BigDecimal amount // O valor que vem do evento
) {}