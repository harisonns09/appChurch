package com.church.appChurch.controller;

import com.church.appChurch.model.dto.infinitepay.InfinitePayWebhookDTO;
import com.church.appChurch.service.IEventoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/webhooks")
@CrossOrigin(origins = "*")
public class WebhookController {

    private static final Logger logger = LoggerFactory.getLogger(WebhookController.class);

    @Autowired
    private IEventoService eventoService;

    @PostMapping("/infinitepay")
    public ResponseEntity<Void> handleInfinitePayWebhook(@RequestBody InfinitePayWebhookDTO payload) {

        logger.info("Webhook InfinitePay recebido. Order NSU: {}", payload.orderNsu());

        try {
            eventoService.processarPagamentoWebhook(payload);

            // Retorna 200 OK para confirmar recebimento
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Erro ao processar webhook", e);

            return ResponseEntity.internalServerError().build();
        }
    }
}
