package com.church.appChurch.model.dto;

import com.church.appChurch.enums.UserRole;

public record UsuarioRequestDTO(
        Long id,
        String login,
        String password,
        UserRole role,
        Long igrejaId) {
}
