package com.church.appChurch.service;

import com.church.appChurch.model.dto.CheckoutRequestDTO;
import com.church.appChurch.model.dto.CheckoutResponseDTO;
import com.church.appChurch.model.dto.infinitepay.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Service
public class InfinitePayService {

    @Value("${infinitepay.url}")
    private String apiUrl;

    @Value("${infinitepay.handle}")
    private String handle;

    @Value("${infinitepay.redirect-base}")
    private String redirectBase;

    @Value("${infinitepay.token}")
    private String apiToken;

    @Value("${infinitepay.webhook-url}")
    private String webhookUrlConfig;

    public CheckoutResponseDTO createCheckoutLink(String eventId, CheckoutRequestDTO data) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiToken);


        int priceInCents = data.amount().multiply(new java.math.BigDecimal("100")).intValue();

        // 3. Montar Item (Seguindo o JSON: description, quantity, price)
        var item = new InfinitePayItem(
                "Inscricao - "+ data.nome(), // description
                1,                            // quantity
                priceInCents                  // price
        );

        // 4. Montar Metadata (Opcional, mas bom para controle)
        var metadata = new InfinitePayMetadata(
                data.nome(),
                data.email(),
                data.amount().toString(),
                data.numeroInscricao()
        );

        var custumer = new InfinitePayCustomerDTO(
                data.nome(),
                data.email(),
                data.telefone()
        );

        // 5. Gera ID único do pedido
        String orderNsu = data.numeroInscricao();

        if (orderNsu == null || orderNsu.isEmpty()) {
            orderNsu = UUID.randomUUID().toString();
        }

        String webhookUrlParaEnvio = webhookUrlConfig;

        // 6. URL de retorno (Para onde o usuário volta após pagar)
        String returnUrl = redirectBase + "/" + eventId + "/inscricao?status=success&transactionId=" + data.numeroInscricao();

        // 7. Montar Payload Principal
        var payload = new InfinitePayCheckoutRequestDTO(
                handle,
                List.of(item),
                orderNsu,
                returnUrl,
                webhookUrlParaEnvio,
                custumer,
                metadata
        );

        // 8. Enviar Requisição POST
        HttpEntity<InfinitePayCheckoutRequestDTO> request = new HttpEntity<>(payload);

        try {
            InfinitePayCheckoutResponseDTO response = restTemplate.postForObject(
                    apiUrl,
                    request,
                    InfinitePayCheckoutResponseDTO.class
            );

            if (response != null && response.url() != null) {
                // Sucesso: Retorna a URL para o Frontend redirecionar
                return new CheckoutResponseDTO(response.url(), response.id());
            } else {
                throw new RuntimeException("InfinitePay retornou resposta vazia.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao gerar link InfinitePay: " + e.getMessage());
        }
    }
}