package com.church.appChurch.repository;

import com.church.appChurch.model.Pessoa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PessoaRepository extends ListCrudRepository<Pessoa, Integer> {


    public Pessoa save(Pessoa pessoa);

    public List<Pessoa> findByNome(String nome);

    @Query(value = "SELECT * FROM tb_pessoas WHERE igreja_id = :igrejaId AND status = 'Ativo' ORDER BY nome", nativeQuery = true)
    public List<Pessoa> findAllByIgrejaId(Long igrejaId);

    @Query(value = "SELECT * FROM tb_pessoas WHERE igreja_id = :igrejaId AND status = 'Visitante' ORDER BY id", nativeQuery = true)
    List<Pessoa> findAllVisitantesByIgreja(@Param("igrejaId") Long igrejaId);


}
