package com.church.appChurch.model.dto;

import com.church.appChurch.model.Evento;
import com.church.appChurch.model.Pessoa;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

public record EventoResponseDTO(
        Long id,
        String nomeEvento,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate dataEvento,
        String descricao,
        String ministerioResponsavel,
        List<String> listaPessoasInscritas
) {

    public EventoResponseDTO(Evento evento) {
        this(evento.getId(),
                evento.getNomeEvento(),
                evento.getDataEvento(),
                evento.getDescricao(),
                evento.getMinisterioResponsavel(),
                evento.getListaPessoasInscritas()
        );
    }

}
