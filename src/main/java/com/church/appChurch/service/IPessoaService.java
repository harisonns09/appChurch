package com.church.appChurch.service;

import com.church.appChurch.dto.PessoaRequestDTO;
import com.church.appChurch.dto.PessoaResponseDTO;
import com.church.appChurch.model.Pessoa;

import java.util.List;
import java.util.Optional;

public interface IPessoaService {

    public List<PessoaResponseDTO> findAll();
    public List<PessoaResponseDTO> findByNome(String nome);
    public Optional<PessoaResponseDTO> findById(Integer id);
    public PessoaResponseDTO addPessoa(PessoaRequestDTO dto);
    public void deleteById(int id);
    public PessoaResponseDTO update(Integer id,PessoaRequestDTO dto);
}
