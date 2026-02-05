package com.church.appChurch.controller;

import com.church.appChurch.model.dto.CheckoutRequestDTO;
import com.church.appChurch.model.dto.CheckoutResponseDTO;
import com.church.appChurch.model.dto.InscricaoRequestDTO;
import com.church.appChurch.service.IEventoService;
import com.church.appChurch.service.InfinitePayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CheckoutController {

    @Autowired
    private InfinitePayService infinitePayService;

    @Autowired
    private IEventoService eventoService;

    @PostMapping("/igrejas/{churchId}/eventos/{eventId}/checkout")
    public ResponseEntity<CheckoutResponseDTO> createCheckout(
            @PathVariable String churchId,
            @PathVariable String eventId,
            @RequestBody CheckoutRequestDTO data
    ) {

        // Dica: Salve o "data" no seu banco como uma "Inscrição Pendente" antes de chamar o serviço
        CheckoutResponseDTO response = infinitePayService.createCheckoutLink(eventId, data);
        return ResponseEntity.ok(response);
    }


}