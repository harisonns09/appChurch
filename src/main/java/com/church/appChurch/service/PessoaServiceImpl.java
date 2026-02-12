package com.church.appChurch.service;

import com.church.appChurch.model.dto.PessoaRequestDTO;
import com.church.appChurch.model.dto.PessoaResponseDTO;
import com.church.appChurch.model.Pessoa;
import com.church.appChurch.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaServiceImpl implements IPessoaService {

    private final PessoaRepository pessoaRepository;

    @Autowired
    public PessoaServiceImpl(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Override
    public List<PessoaResponseDTO> findAllByIgrejaId(Long igrejaID) {

        return pessoaRepository.findAllByIgrejaId(igrejaID).stream()
                .map(pessoa -> new PessoaResponseDTO(pessoa))
                .toList();
    }


    @Override
    public List<PessoaResponseDTO> findByNome(String nome) {

        return pessoaRepository.findByNome(nome).stream().map(PessoaResponseDTO::new).toList();
    }

    @Override
    public Optional<PessoaResponseDTO> findById(Integer id) {

        return pessoaRepository.findById(id).map(PessoaResponseDTO::new);
    }

    @Override
    public PessoaResponseDTO addPessoa(PessoaRequestDTO dto) {

//        if(cpf == null || cpf.length() != 11){
//            throw new IllegalArgumentException("CPF inválido");
//        }
//        if(pessoaRepository.existsByCpf(cpf)){
//            throw new IllegalArgumentException("CPF já cadastrado");
//        }

        Pessoa newPessoa = new Pessoa(dto);
        return new PessoaResponseDTO(pessoaRepository.save(newPessoa));
    }

    @Override
    public void deleteById(int id) {
        pessoaRepository.deleteById(id);
    }

    @Override
    public PessoaResponseDTO update(Integer id, PessoaRequestDTO dto) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada"));

        pessoa.setNome(dto.nome());
        pessoa.setDataNascimento(dto.dataNascimento());
        pessoa.setTelefone(dto.telefone());
        pessoa.setEmail(dto.email());
        pessoa.setMinisterio(dto.ministerio());
        pessoa.setStatus(dto.status());

        pessoa.setGenero(dto.genero());
        pessoa.setEstadoCivil(dto.estadoCivil());
        pessoa.setCep(dto.cep());
        pessoa.setEndereco(dto.endereco());
        pessoa.setNumero(dto.numero());
        pessoa.setBairro(dto.bairro());
        pessoa.setCidade(dto.cidade());
        pessoa.setEstado(dto.estado());
        pessoa.setComplemento(dto.complemento());
        pessoa.setDataBatismo(dto.dataBatismo());

        return new PessoaResponseDTO(pessoaRepository.save(pessoa));
    }

    @Override
    public List<PessoaResponseDTO> findAllVisitorsByIgrejaId(Long igrejaId) {
        return pessoaRepository.findAllVisitantesByIgreja(igrejaId)
                .stream()
                .map(PessoaResponseDTO::new)
                .toList();
    }

}
