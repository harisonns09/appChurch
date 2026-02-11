package com.church.appChurch.controller;

import com.church.appChurch.model.dto.PessoaRequestDTO;
import com.church.appChurch.model.dto.PessoaResponseDTO;
import com.church.appChurch.model.dto.VisitanteRequestDTO;
import com.church.appChurch.service.IPessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class PessoaController {

    @Autowired
    private IPessoaService pessoaService;

    @GetMapping("/igrejas/{igrejaId}/membros")
    public List<PessoaResponseDTO> getMembros(@PathVariable Long igrejaId) {
        return pessoaService.findAllByIgrejaId(igrejaId);
    }

    @GetMapping("/igrejas/{igrejaId}/membros/{id}")
    public ResponseEntity<PessoaResponseDTO> carregarPessoa(@PathVariable Integer id) {
        return pessoaService.findById(id)
                .map(registro -> ResponseEntity.ok(registro))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/igrejas/{igrejaId}/membros")
    public ResponseEntity<?> addPessoa(@RequestBody @Valid PessoaRequestDTO pessoa) {
        return ResponseEntity.ok(pessoaService.addPessoa(pessoa));
    }

    @DeleteMapping("/igrejas/{igrejaId}/membros/{id}")
    public ResponseEntity<Void> deletePessoa(@PathVariable int id) {
        pessoaService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/igrejas/{igrejaId}/membros/{id}")
    public ResponseEntity<PessoaResponseDTO> updatePessoa(@PathVariable Integer id, @RequestBody @Valid PessoaRequestDTO dto) {
        return ResponseEntity.ok(pessoaService.update(id, dto));
    }


    @PostMapping("/public/visitantes")
    public ResponseEntity<PessoaResponseDTO> cadastrarVisitante(@RequestBody PessoaRequestDTO dto) {
        // Aqui você pode forçar o status para 'VISITANTE' se quiser garantir
        return ResponseEntity.ok(pessoaService.addPessoa(dto));
    }

    @GetMapping("/igrejas/{igrejaId}/visitantes")
    public List<PessoaResponseDTO> getVisitantes(@PathVariable Long igrejaId) {
        return pessoaService.findAllVisitorsByIgrejaId(igrejaId);
    }

    @PostMapping("/public/{igrejaId}/membros")
    public ResponseEntity<?> addPessoaPublic(@RequestBody @Valid PessoaRequestDTO pessoa) {
        return ResponseEntity.ok(pessoaService.addPessoa(pessoa));
    }
}
