package com.church.appChurch.controller;

import com.church.appChurch.model.Igreja;
import com.church.appChurch.model.dto.IgrejaRequestDTO;
import com.church.appChurch.model.dto.IgrejaResponseDTO;
import com.church.appChurch.repository.IgrejaRepository;
import com.church.appChurch.service.IIgrejaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class IgrejaController {

    @Autowired
    private IIgrejaService igrejaService;

    @GetMapping("/igrejas")
    public List<IgrejaResponseDTO> findAll(){
        return igrejaService.findAll();
    }

    @GetMapping("/igreja/{id}")
    public ResponseEntity<IgrejaResponseDTO> findById(@PathVariable Long id){
        return igrejaService.findById(id)
                .map(igreja -> ResponseEntity.ok(igreja)).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/igreja")
    public ResponseEntity<?> addIgreja(@RequestBody @Valid IgrejaRequestDTO igreja){
        return ResponseEntity.ok(igrejaService.save(igreja));
    }

    @PutMapping("/igreja/{id}")
    public ResponseEntity<IgrejaResponseDTO> update(@PathVariable Long id, @RequestBody @Valid IgrejaRequestDTO dto){
        return ResponseEntity.ok(igrejaService.update(id, dto));
    }



}
