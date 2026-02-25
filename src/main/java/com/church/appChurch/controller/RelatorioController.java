package com.church.appChurch.controller;

import com.church.appChurch.service.IRelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/relatorios")
@CrossOrigin(origins = "*") // Ajuste conforme sua segurança
public class RelatorioController {

    @Autowired
    private IRelatorioService relatorioService;

    @GetMapping("/eventos/{eventoId}/excel")
    public ResponseEntity<byte[]> baixarListaInscritos(@PathVariable Long eventoId) {
        byte[] arquivo = relatorioService.gerarPlanilhaInscritos(eventoId);

        String nomeArquivo = "inscritos_evento_" + eventoId + ".xlsx";

        return ResponseEntity.ok()
                // Header que força o download
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nomeArquivo)
                // Tipo MIME correto para Excel moderno (.xlsx)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(arquivo);
    }
}
