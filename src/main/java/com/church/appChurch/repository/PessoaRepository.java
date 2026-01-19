package com.church.appChurch.repository;

import com.church.appChurch.model.Pessoa;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface PessoaRepository extends ListCrudRepository<Pessoa, Integer> {


    public Pessoa save(Pessoa pessoa);

    public List<Pessoa> findByNome(String nome);

    public Optional<Pessoa> findById(Integer id);

    // O Spring cria o SQL sozinho com esse nome
    boolean existsByCpf(String cpf);

    // Para a atualização: busca o dono do CPF
    Optional<Pessoa> findByCpf(String cpf);

}
