package com.church.appChurch.controller;

import com.church.appChurch.model.dto.InscricaoResponseDTO;
import com.church.appChurch.service.IInscricaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/inscricoes")
public class InscricaoController {

    @Autowired
    private IInscricaoService inscricaoService;

    @GetMapping("/{cpf}")
    public ResponseEntity<List<InscricaoResponseDTO>> buscarInscricoes(@PathVariable String cpf) {
        List<InscricaoResponseDTO> inscricoes = inscricaoService.buscarInscricoesPorCpf(cpf);

        if (inscricoes.isEmpty()) {
            return ResponseEntity.noContent().build(); // Retorna 204 se não tiver nada
        }

        return ResponseEntity.ok(inscricoes);
    }
}