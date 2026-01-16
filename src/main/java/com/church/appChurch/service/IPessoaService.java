package com.church.appChurch.service;

import com.church.appChurch.model.Pessoa;

import java.util.List;
import java.util.Optional;

public interface IPessoaService {

    public List<Pessoa> findAll();
    public List<Pessoa> findByNome(String nome);
    public Optional<Pessoa> findById(Integer id);
    public Pessoa addPessoa(Pessoa pessoa);
    public void deleteById(int id);
    public Pessoa save(Pessoa dadosAtualizados);
}
