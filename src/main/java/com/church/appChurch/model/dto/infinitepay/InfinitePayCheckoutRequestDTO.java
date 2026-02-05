package com.church.appChurch.model.dto.infinitepay;

import java.util.List;

// Este Record define o JSON exato que será enviado
public record InfinitePayCheckoutRequestDTO(
        String handle,
        List<InfinitePayItem> items,
        String order_nsu,
        // Adicionamos 'url' (ou redirect_url) para o cliente voltar ao seu site após pagar
        String redirect_url,
        InfinitePayCustomerDTO customer,
        InfinitePayMetadata metadata
) {}

