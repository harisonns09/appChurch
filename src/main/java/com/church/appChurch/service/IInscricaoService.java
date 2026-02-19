package com.church.appChurch.service;

import com.church.appChurch.model.dto.InscricaoResponseDTO;
import java.util.List;

public interface IInscricaoService {

    /**
     * Busca todas as inscrições vinculadas a um CPF.
     * @param cpf O CPF do participante
     * @return Lista de DTOs de inscrição
     */
    List<InscricaoResponseDTO> buscarInscricoesPorCpf(String cpf);

    void confirmarPagamento(String idInscricao, String tipoValor);
}