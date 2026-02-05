package com.church.appChurch.model.dto.infinitepay;

// Itens do pedido
// O JSON pede "description", "quantity" e "price" (em centavos)
public record InfinitePayItem(
        String description,
        Integer quantity,
        Integer price
) {}
