package com.church.appChurch.model.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public record PessoaRequestDTO(
        @NotBlank(message = "Nome é obrigatório")
        String nome,

        String email,
        String telefone,
        String cpf,
        LocalDate dataNascimento,

        String ministerio,
        String status,

        // Novos Campos (Opcionais, então sem @NotBlank se não forem obrigatórios)
        String genero,
        String estadoCivil,
        String cep,
        String endereco,
        String numero,
        String bairro,
        String cidade,
        String estado,
        LocalDate dataBatismo,

        Long igrejaId
) {}