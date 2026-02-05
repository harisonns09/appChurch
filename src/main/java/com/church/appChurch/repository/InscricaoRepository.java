package com.church.appChurch.repository;

import com.church.appChurch.model.Inscricao;
import com.church.appChurch.model.Ministerio;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InscricaoRepository extends ListCrudRepository<Inscricao, Long> {

    public Inscricao save(Inscricao inscricao);

    void deleteAllByEventoId(int id);

    @Query("SELECT i FROM Inscricao i WHERE i.evento.id = :id")
    public List<Inscricao> findbyEventoId(@Param("id") int id);

    @Query("SELECT i FROM Inscricao i WHERE i.numeroInscricao = :numero_inscricao")
    public Inscricao findbyNumero_Inscricao(@Param("numero_inscricao") String numero_inscricao);
}
