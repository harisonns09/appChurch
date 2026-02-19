package com.church.appChurch.service;

import com.church.appChurch.model.dto.PessoaRequestDTO;
import com.church.appChurch.model.dto.PessoaResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IPessoaService {

    public List<PessoaResponseDTO> findAllByIgrejaId(Long igrejaId);
    public List<PessoaResponseDTO> findByNome(String nome);
    public Optional<PessoaResponseDTO> findById(Integer id);
    public PessoaResponseDTO addPessoa(PessoaRequestDTO dto);
    public void deleteById(int id);
    public PessoaResponseDTO update(Integer id,PessoaRequestDTO dto);
    public List<PessoaResponseDTO> findAllVisitorsByIgrejaId(Long igrejaId);
    Page<PessoaResponseDTO> findPaged(Long igrejaId, String nome, String genero, Integer mesAniversario, Pageable pageable);
}
