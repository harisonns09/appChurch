package com.church.appChurch.model.dto.infinitepay;

public record InfinitePayCheckoutResponseDTO(
        String url, // A URL para onde redirecionamos o usuário
        String id,  // ID da transação na InfinitePay
        String status
) {}