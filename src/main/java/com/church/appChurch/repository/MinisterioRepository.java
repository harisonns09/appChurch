package com.church.appChurch.repository;

import com.church.appChurch.model.Ministerio;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MinisterioRepository extends ListCrudRepository<Ministerio, Long> {

    public Ministerio save(Ministerio ministerio);

    public List<Ministerio> findByNome(String nome);

    public Optional<Ministerio> findById(Long id);
}
