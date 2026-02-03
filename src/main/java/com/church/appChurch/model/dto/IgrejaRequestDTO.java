package com.church.appChurch.model.dto;

import jakarta.validation.constraints.NotBlank;

public record IgrejaRequestDTO(

        @NotBlank(message = "O nome da igreja é obrigatório")
        String name,
        String instagram,
        @NotBlank(message = "O endereço é obrigatório")
        String address,
        @NotBlank(message = "A cidade é obrigatória")
        String city,
        @NotBlank(message = "O estado é obrigatório")
        String state

) {
}
