package com.church.appChurch.model.dto;

import com.church.appChurch.model.Ministerio;

public record MinisterioResponseDTO(
        Long id,
        String nome,
        String liderResponsavel,
        Long igrejaId
) {

    public MinisterioResponseDTO(Ministerio ministerio) {
        this(ministerio.getId(),
                ministerio.getNome(),
                ministerio.getLiderResponsavel(),
                ministerio.getIgreja().getId()
        );

    }

}
