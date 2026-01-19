package com.church.appChurch.controller;

import com.church.appChurch.model.Pessoa;
import com.church.appChurch.service.IPessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public List<Pessoa> getMembros() {
        return pessoaService.findAll();
    }

    @GetMapping("/membro/{id}")
    public ResponseEntity<Pessoa> caregarPessoa(@PathVariable Integer id) {
        return pessoaService.findById(id)
                .map(registro -> ResponseEntity.ok(registro))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/membros")
    public ResponseEntity<?> addPessoa(@RequestBody Pessoa pessoa) {
        try {
            return ResponseEntity.ok(pessoaService.addPessoa(pessoa));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/membro/{id}")
    public ResponseEntity<Void> deletePessoa(@PathVariable int id) {
        pessoaService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/membro/{id}")
    public ResponseEntity<Pessoa> updatePessoa(@PathVariable Integer id, @RequestBody Pessoa dadosAtualizados) {
        dadosAtualizados.setId(id);
        Pessoa salvo = pessoaService.save(dadosAtualizados);
        return ResponseEntity.ok(salvo);
    }
}
