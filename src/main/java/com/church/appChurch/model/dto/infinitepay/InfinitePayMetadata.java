package com.church.appChurch.model.dto.infinitepay;

// Metadados opcionais para você rastrear quem pagou
public record InfinitePayMetadata(
        String customer_name,
        String customer_email,
        String original_amount,
        String numero_inscricao
) {}
