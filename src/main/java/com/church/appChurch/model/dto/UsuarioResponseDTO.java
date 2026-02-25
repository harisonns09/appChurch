package com.church.appChurch.model.dto;

import com.church.appChurch.model.Usuario;

public record UsuarioResponseDTO(

        Long id,
        String user,
        String password,
        String role,
        Long igreja
) {

    public UsuarioResponseDTO(Usuario usuario){
        this(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getPassword(),
                usuario.getRole() != null ? usuario.getRole().name() : "SEM_PERMISSAO",
                usuario.getIgreja().getId()
        );
    }
}
