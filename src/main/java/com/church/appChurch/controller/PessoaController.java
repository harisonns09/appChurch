package com.church.appChurch.controller;

import com.church.appChurch.model.dto.PessoaRequestDTO;
import com.church.appChurch.model.dto.PessoaResponseDTO;
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

    @GetMapping("/membros")
    public List<PessoaResponseDTO> getMembros() {
        return pessoaService.findAll();
    }

    @GetMapping("/membro/{id}")
    public ResponseEntity<PessoaResponseDTO> caregarPessoa(@PathVariable Integer id) {
        return pessoaService.findById(id)
                .map(registro -> ResponseEntity.ok(registro))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/membros")
    public ResponseEntity<?> addPessoa(@RequestBody @Valid PessoaRequestDTO pessoa) {
        return ResponseEntity.ok(pessoaService.addPessoa(pessoa));
    }

    @DeleteMapping("/membro/{id}")
    public ResponseEntity<Void> deletePessoa(@PathVariable int id) {
        pessoaService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/membro/{id}")
    public ResponseEntity<PessoaResponseDTO> updatePessoa(@PathVariable Integer id, @RequestBody @Valid PessoaRequestDTO dto) {
        return ResponseEntity.ok(pessoaService.update(id, dto));
    }
}
