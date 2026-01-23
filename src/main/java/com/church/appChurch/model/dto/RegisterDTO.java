package com.church.appChurch.model.dto;

import com.church.appChurch.enums.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
