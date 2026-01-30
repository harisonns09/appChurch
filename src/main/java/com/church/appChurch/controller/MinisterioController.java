package com.church.appChurch.controller;

import com.church.appChurch.model.Ministerio;
import com.church.appChurch.model.dto.MinisterioRequestDTO;
import com.church.appChurch.model.dto.MinisterioResponseDTO;
import com.church.appChurch.service.IMinisterioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class MinisterioController {

    @Autowired
    private IMinisterioService ministerioService;

    @GetMapping("/ministerios")
    public List<MinisterioResponseDTO> getMinisterios() {
        return ministerioService.findAll();
    }

    @GetMapping("/ministerio/{id}")
    public ResponseEntity<Ministerio> carregarMinisterio(@PathVariable Long id) {
        return ministerioService.findById(id)
                .map(registro -> ResponseEntity.ok(registro))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/ministerios")
    public ResponseEntity<?> addMinisterio(@RequestBody @Valid MinisterioRequestDTO ministerio) {
        try {
            return ResponseEntity.ok(ministerioService.addMinisterio(ministerio));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/ministerio/{id}")
    public ResponseEntity<Void> deleteMinisterio(@PathVariable Long id) {
        ministerioService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/ministerio/{id}")
    public ResponseEntity<Ministerio> updateMinisterio(@PathVariable Long id, @RequestBody Ministerio dadosAtualizados) {
        dadosAtualizados.setId(id);
        Ministerio salvo = ministerioService.save(dadosAtualizados);
        return ResponseEntity.ok(salvo);
    }
}
