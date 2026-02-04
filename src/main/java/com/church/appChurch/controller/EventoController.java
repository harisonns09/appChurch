package com.church.appChurch.controller;

import com.church.appChurch.model.dto.EventoRequestDTO;
import com.church.appChurch.model.dto.EventoResponseDTO;
import com.church.appChurch.model.dto.InscricaoRequestDTO;
import com.church.appChurch.service.IEventoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class EventoController {

    @Autowired
    private IEventoService eventoService;

    @GetMapping("/igreja/{id}/eventos")
    public List<EventoResponseDTO> getEventos() {
        return eventoService.findAll();
    }

    @GetMapping("/igreja/{idIgreja}/evento/{id}")
    public ResponseEntity<EventoResponseDTO> carregarEvento(@PathVariable Integer id) {
        return eventoService.findById(id)
                .map(registro -> ResponseEntity.ok(registro))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/igreja/{idIgreja}/eventos")
    public ResponseEntity<?> addEvento(@RequestBody @Valid EventoRequestDTO evento) {
        return ResponseEntity.ok(eventoService.addEvento(evento));
    }

    @DeleteMapping("/igreja/{idIgreja}/evento/{id}")
    public ResponseEntity<Void> deleteEvento(@PathVariable int id) {
        eventoService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/igreja/{idIgreja}/evento/{id}")
    public ResponseEntity<EventoResponseDTO> updateEvento(@PathVariable Integer id, @RequestBody @Valid EventoRequestDTO dto) {
        return ResponseEntity.ok(eventoService.update(id, dto));
    }

    @PostMapping("/igreja/{idIgreja}/evento/{id}/inscricao")
    public ResponseEntity<?> realizarInscricao(@PathVariable int id, @RequestBody @Valid InscricaoRequestDTO dto) {
        try{
            return ResponseEntity.ok(eventoService.realizarInscricao(id, dto));

        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
