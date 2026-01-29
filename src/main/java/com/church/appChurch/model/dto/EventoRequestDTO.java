package com.church.appChurch.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.List;

public record EventoRequestDTO(
        @NotBlank(message = "O nome é obrigatório)")
        String nomeEvento,

        @NotNull(message = "A data do evento é obrigatória")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate dataEvento,

        String descricao,

        String ministerioResponsavel,

        List<String> listaPessoasInscritas
){

}
