package com.church.appChurch.model.dto.infinitepay;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public record InfinitePayWebhookDTO(
        @JsonProperty("invoice_slug") String invoiceSlug,
        @JsonProperty("amount") BigDecimal amount,
        @JsonProperty("paid_amount") BigDecimal paidAmount,
        @JsonProperty("installments") Integer installments,
        @JsonProperty("capture_method") String captureMethod,
        @JsonProperty("transaction_nsu") String transactionNsu, // ID da InfinitePay
        @JsonProperty("order_nsu") String orderNsu,             // ID da Inscrição (Nosso)
        @JsonProperty("receipt_url") String receiptUrl,
        @JsonProperty("items") List<InfinitePayItem> items,
        @JsonProperty("metadata") InfinitePayMetadata metadata // Mantemos opcional
) {}