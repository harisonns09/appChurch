package com.church.appChurch.controller;

import com.church.appChurch.model.dto.EventoRequestDTO;
import com.church.appChurch.model.dto.EventoResponseDTO;
import com.church.appChurch.model.dto.InscricaoRequestDTO;
import com.church.appChurch.service.IEventoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class EventoController {

    @Autowired
    private IEventoService eventoService;

    @GetMapping("/igrejas/{id}/eventos")
    public List<EventoResponseDTO> getEventos() {
        return eventoService.findAll();
    }

    @GetMapping("/eventos/{id}")
    public ResponseEntity<EventoResponseDTO> carregarEvento(@PathVariable Integer id) {
        return eventoService.findById(id)
                .map(registro -> ResponseEntity.ok(registro))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/igrejas/{idIgreja}/eventos")
    public ResponseEntity<?> addEvento(@PathVariable Integer idIgreja, @RequestBody @Valid EventoRequestDTO evento) {
        return ResponseEntity.ok(eventoService.addEvento(evento));
    }

    @DeleteMapping("/igrejas/{idIgreja}/eventos/{id}")
    public ResponseEntity<Void> deleteEvento(@PathVariable int idIgreja, @PathVariable int id) {
        eventoService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/igrejas/{idIgreja}/eventos/{id}")
    public ResponseEntity<EventoResponseDTO> updateEvento(@PathVariable Integer idIgreja,@PathVariable Integer id, @RequestBody @Valid EventoRequestDTO dto) {
        return ResponseEntity.ok(eventoService.update(id, dto));
    }

    @PostMapping("/eventos/{id}/inscricao")
    public ResponseEntity<?> realizarInscricao(@PathVariable int id, @RequestBody @Valid InscricaoRequestDTO dto) {
        try{
            return ResponseEntity.ok(eventoService.realizarInscricao(id, dto));

        }catch ( RuntimeException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", e.getMessage()));        }
    }

    @PutMapping("/igrejas/{idIgreja}/eventos/{idEvento}/inscricoes/{idInscricao}/pagamento")
    public ResponseEntity<?> atualizarFormaPagamento(
            @PathVariable String idIgreja,
            @PathVariable Integer idEvento,
            @PathVariable String idInscricao,
            @RequestBody Map<String, String> payload
    ) {
        String formaPagamento = payload.get("formaPagamento");

        eventoService.atualizarMetodoPagamento(idInscricao, formaPagamento);

        return ResponseEntity.ok().build();
    }


}
