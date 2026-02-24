package com.church.appChurch.controller;

import com.church.appChurch.enums.UserRole;
import com.church.appChurch.model.dto.UsuarioRequestDTO;
import com.church.appChurch.model.dto.UsuarioResponseDTO;
import com.church.appChurch.service.IUsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    IUsuarioService usuarioService;

    @GetMapping("/roles")
    public ResponseEntity<List<String>> listarRoles() {
        // Isso pega todos os valores do seu Enum e transforma numa lista de strings
        List<String> roles = Arrays.stream(UserRole.values())
                .map(UserRole::name)
                .collect(Collectors.toList());

        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{igrejaId}")
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios(@PathVariable Long igrejaId) {
        return ResponseEntity.ok(usuarioService.listarUsuarios(igrejaId));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UsuarioRequestDTO data) {
        return ResponseEntity.ok(usuarioService.register(data));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UsuarioResponseDTO> alterarUsuario(@PathVariable Long userId, @RequestBody @Valid UsuarioRequestDTO data) {

        return ResponseEntity.ok(usuarioService.alterarUsuario(userId, data));
    }
}
