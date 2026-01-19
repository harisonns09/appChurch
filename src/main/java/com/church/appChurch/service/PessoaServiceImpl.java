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
        //Validação dos dados
        String cpf = limparCpf(pessoa.getCpf());
        pessoa.setCpf(cpf);

        if(cpf == null || cpf.length() != 11){
            throw new IllegalArgumentException("CPF inválido");
        }
        if(pessoaRepository.existsByCpf(cpf)){
            throw new IllegalArgumentException("CPF já cadastrado");
        }

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

    private String limparCpf(String cpf) {
        if (cpf == null) return null;
        // A expressão "\\D" significa "tudo que NÃO for Dígito"
        return cpf.replaceAll("\\D", "");
    }
}
