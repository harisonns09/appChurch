package com.church.appChurch.model.dto;

import com.church.appChurch.model.Pessoa;
import java.time.LocalDate;

public record PessoaResponseDTO(
        Long id,
        Long churchId,
        String nome,
        String email,
        String telefone,
        LocalDate dataNascimento,
        String ministerio,
        String status,

        // Novos campos
        String genero,
        String estadoCivil,
        String cep,
        String endereco,
        String numero,
        String bairro,
        String cidade,
        String estado,
        LocalDate dataBatismo,
        String observacao
) {
    public PessoaResponseDTO(Pessoa pessoa) {
        this(
                pessoa.getId(),
                pessoa.getIgreja().getId(),
                pessoa.getNome(),
                pessoa.getEmail(),
                pessoa.getTelefone(),
                pessoa.getDataNascimento(),
                pessoa.getMinisterio(),
                pessoa.getStatus(),
                pessoa.getGenero(),
                pessoa.getEstadoCivil(),
                pessoa.getCep(),
                pessoa.getEndereco(),
                pessoa.getNumero(),
                pessoa.getBairro(),
                pessoa.getCidade(),
                pessoa.getEstado(),
                pessoa.getDataBatismo(),
                pessoa.getObservacao()
        );
    }
}