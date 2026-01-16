package com.church.appChurch.service;

import com.church.appChurch.model.Pessoa;
import com.church.appChurch.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaServiceImpl implements IPessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Override
    public List<Pessoa> findAll() {
        return pessoaRepository.findAll();
    }


    @Override
    public List<Pessoa> findByNome(String nome) {
        return pessoaRepository.findByNome(nome);
    }

    @Override
    public Optional<Pessoa> findById(Integer id) {
        return pessoaRepository.findById(id);
    }

    @Override
    public Pessoa addPessoa(Pessoa pessoa) {
            return pessoaRepository.save(pessoa);
    }

    @Override
    public void deleteById(int id) {
        pessoaRepository.deleteById(id);
    }

    @Override
    public Pessoa save(Pessoa dadosAtualizados) {
        return pessoaRepository.save(dadosAtualizados);
    }
}
