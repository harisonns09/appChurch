package com.church.appChurch.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record PessoaRequestDTO (
        @NotBlank(message = "O nome é obrigatório)")
        String nome,

        @NotNull(message = "A data de nascimento é obrigatória")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate dataNascimento,

        @NotBlank(message = "O telefone é obrigatório")
        String telefone,

        @NotBlank(message = "O email é obrigatório")
        String email,

        String ministerio,
        String status,

        @NotBlank(message = "O CPF é obrigatório")
        @CPF(message = "CPF inválido")
        String cpf
){


}
