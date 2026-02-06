package com.church.appChurch.service;

import com.church.appChurch.model.Inscricao;
import com.church.appChurch.model.dto.InscricaoResponseDTO;
import com.church.appChurch.repository.InscricaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InscricaoServiceImpl implements IInscricaoService {

    @Autowired
    private InscricaoRepository inscricaoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<InscricaoResponseDTO> buscarInscricoesPorCpf(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException("O CPF é obrigatório para a consulta.");
        }

        // 1. Sanitização: Remove caracteres não numéricos (pontos e traços)
        String cpfLimpo = cpf.replaceAll("\\D", "");

        // 2. Busca no banco
        List<Inscricao> inscricoes = inscricaoRepository.findAllByCpf(cpfLimpo);

        // 3. Conversão: Transforma a lista de Entidades em lista de DTOs
        return inscricoes.stream()
                .map(InscricaoResponseDTO::new) // Chama o construtor do DTO para cada item
                .collect(Collectors.toList());
    }
}