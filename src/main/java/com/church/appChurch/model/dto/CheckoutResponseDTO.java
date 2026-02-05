package com.church.appChurch.model.dto;

public record CheckoutResponseDTO(
        String checkoutUrl,
        String transactionId
) {}