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

    @GetMapping("/igrejas/{igrejaId}/ministerios")
    public List<MinisterioResponseDTO> getMinisterios(@PathVariable Long igrejaId) {
        return ministerioService.findAllByIgreja(igrejaId);
    }

    @GetMapping("/igrejas/{igrejaId}/ministerios/{id}")
    public ResponseEntity<Ministerio> carregarMinisterio(@PathVariable Long id) {
        return ministerioService.findById(id)
                .map(registro -> ResponseEntity.ok(registro))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/igrejas/{igrejaId}/ministerios")
    public ResponseEntity<?> addMinisterio(@PathVariable Long igrejaId, @RequestBody @Valid MinisterioRequestDTO ministerio) {
        try {
            return ResponseEntity.ok(ministerioService.addMinisterio(igrejaId, ministerio));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/igrejas/{igrejaId}/ministerios/{id}")
    public ResponseEntity<Void> deleteMinisterio(@PathVariable Long id) {
        ministerioService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/igrejas/{igrejaId}/ministerios/{id}")
    public ResponseEntity<MinisterioResponseDTO> updateMinisterio(@PathVariable Long igrejaId, @PathVariable Long id, @RequestBody MinisterioRequestDTO dto) {

        return ResponseEntity.ok(ministerioService.updateMinisterio(igrejaId, id, dto));
    }
}
