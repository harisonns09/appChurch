package com.church.appChurch.service;

import com.church.appChurch.model.dto.UsuarioRequestDTO;
import com.church.appChurch.model.dto.UsuarioResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUsuarioService {

    public ResponseEntity<?> register(UsuarioRequestDTO data);

    public List<UsuarioResponseDTO> listarUsuarios(Long igrejaId);

    public UsuarioResponseDTO alterarUsuario(Long userId, UsuarioRequestDTO data);
}
