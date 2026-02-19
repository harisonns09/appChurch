package com.church.appChurch.service;

import com.church.appChurch.model.dto.PessoaRequestDTO;
import com.church.appChurch.model.dto.PessoaResponseDTO;
import com.church.appChurch.model.Pessoa;
import com.church.appChurch.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import jakarta.persistence.criteria.Predicate;

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


    @Override
    public Page<PessoaResponseDTO> findPaged(Long igrejaId, String nome, String genero, Integer mes, Pageable pageable) {
        // Busca as entidades usando a Specification que montamos antes
        Specification<Pessoa> spec = createSpecification(igrejaId, nome, genero, mes);

        Page<Pessoa> pessoas = pessoaRepository.findAll(spec, pageable);

        // Converte a página de Entidades para uma página de DTOs
        return pessoas.map(pessoa -> new PessoaResponseDTO(pessoa));
        // Ou use seu Mapper: return pessoas.map(mapper::toResponseDTO);
    }



    private Specification<Pessoa> createSpecification(Long igrejaId, String nome, String genero, Integer mesAniversario) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filtro obrigatório: ID da Igreja
            if (igrejaId != null) {
                predicates.add(cb.equal(root.get("igreja").get("id"), igrejaId));
            }

            // Filtro opcional: Nome (Case Insensitive)
            if (nome != null && !nome.trim().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%"));
            }

            // Filtro opcional: Gênero (Exato)
            if (genero != null && !genero.trim().isEmpty()) {
                predicates.add(cb.equal(root.get("genero"), genero));
            }

            // Filtro opcional: Mês de Aniversário
            if (mesAniversario != null && mesAniversario > 0) {
                // cb.function chama a função MONTH do SQL do seu banco (H2, MySQL, SQL Server, etc)
                predicates.add(cb.equal(cb.function("MONTH", Integer.class, root.get("dataNascimento")), mesAniversario));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
