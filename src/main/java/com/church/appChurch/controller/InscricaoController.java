package com.church.appChurch.controller;

import com.church.appChurch.model.dto.InscricaoResponseDTO;
import com.church.appChurch.service.IInscricaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @PutMapping("/confirmarPagamento/{idEvento}/{idInscricao}")
    public ResponseEntity<?> confirmarPagamento(
            @PathVariable Long idEvento,
            @PathVariable String idInscricao,
            @RequestBody Map<String, String> payload) { // <-- Captura o JSON do React

        // Extrai o valor enviado pelo React ("INTEGRAL" ou "PROMOCIONAL")
        String tipoValor = payload.get("tipoValor");

        // Agora passamos o tipoValor para o Service salvar no banco
        inscricaoService.confirmarPagamento(idInscricao, tipoValor);

        return ResponseEntity.ok().build();
    }
}