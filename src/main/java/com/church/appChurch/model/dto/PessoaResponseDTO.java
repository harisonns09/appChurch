package com.church.appChurch.model.dto;

import com.church.appChurch.model.Pessoa;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record PessoaResponseDTO(
        Integer id,
        String nome,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate dataNascimento,
        String telefone,
        String email,
        String ministerio,
        String status,
        String cpf
) {

    public PessoaResponseDTO(Pessoa pessoa) {
        this(pessoa.getId(),
                pessoa.getNome(),
                pessoa.getDataNascimento(),
                pessoa.getTelefone(),
                pessoa.getEmail(),
                pessoa.getMinisterio(),
                pessoa.getStatus(),
                pessoa.getCpf());
    }

}
