package com.church.appChurch.service;

import com.church.appChurch.infra.audit.Loggable;
import com.church.appChurch.model.Igreja;
import com.church.appChurch.model.Usuario;
import com.church.appChurch.model.dto.UsuarioRequestDTO;
import com.church.appChurch.model.dto.UsuarioResponseDTO;
import com.church.appChurch.repository.IgrejaRepository;
import com.church.appChurch.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements IUsuarioService{

    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    IgrejaRepository igrejaRepository;

    @Override
    @Loggable(action = "CRIAR", entity = "USUARIO") // <--- SÓ ADICIONAR ISSO
    public ResponseEntity register(UsuarioRequestDTO data) {
        if(this.usuarioRepository.findByUsername(data.login()) != null) {
            return ResponseEntity.badRequest().body("Usuário já existe");
        }

        Igreja igreja = igrejaRepository.findById(data.igrejaId())
                .orElseThrow(() -> new RuntimeException("Igreja não encontrada"));

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        Usuario newUser = new Usuario(data.login(), encryptedPassword, data.role(), igreja);
        this.usuarioRepository.save(newUser);

        return ResponseEntity.ok().build();
    }

    @Override
    public List<UsuarioResponseDTO> listarUsuarios(Long igrejaId) {
        return usuarioRepository.findByIgrejaId(igrejaId).stream()
                .map(u -> new UsuarioResponseDTO(u)).toList();
    }

    @Override
    @Loggable(action = "ALTERAR", entity = "USUARIO") // <--- SÓ ADICIONAR ISSO
    public UsuarioResponseDTO alterarUsuario(Long userId, UsuarioRequestDTO data) {
        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        usuario.setRole(data.role());
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        usuario.setPassword(encryptedPassword);

        return new UsuarioResponseDTO(usuarioRepository.save(usuario));
    }
}
