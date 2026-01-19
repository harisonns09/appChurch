package com.church.appChurch.controller;

import com.church.appChurch.model.Ministerios;
import com.church.appChurch.service.IMinisterioService;
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
    public List<Ministerios> getMinisterios() {
        return ministerioService.findAll();
    }

    @GetMapping("/ministerio/{id}")
    public ResponseEntity<Ministerios> carregarMinisterio(@PathVariable Long id) {
        return ministerioService.findById(id)
                .map(registro -> ResponseEntity.ok(registro))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/ministerios")
    public ResponseEntity<?> addMinisterio(@RequestBody Ministerios ministerio) {
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
    public ResponseEntity<Ministerios> updateMinisterio(@PathVariable Long id, @RequestBody Ministerios dadosAtualizados) {
        dadosAtualizados.setId(id);
        Ministerios salvo = ministerioService.save(dadosAtualizados);
        return ResponseEntity.ok(salvo);
    }
}
