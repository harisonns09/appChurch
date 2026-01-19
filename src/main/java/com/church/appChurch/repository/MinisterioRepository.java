package com.church.appChurch.repository;

import com.church.appChurch.model.Ministerios;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MinisterioRepository extends ListCrudRepository<Ministerios, Long> {

    public Ministerios save(Ministerios ministerio);

    public List<Ministerios> findByNome(String nome);

    public Optional<Ministerios> findById(Long id);
}
