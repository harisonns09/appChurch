package com.church.appChurch.model.dto.infinitepay;

public record InfinitePayWebhookDTO(
        String id,          // ID da transação na InfinitePay
        String status,      // Ex: "paid", "approved", "failed"
        String created_at,
        InfinitePayMetadata metadata // Aqui voltará o ID da inscrição que enviamos
) {}