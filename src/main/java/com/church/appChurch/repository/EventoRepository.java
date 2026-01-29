package com.church.appChurch.repository;

import com.church.appChurch.model.Evento;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventoRepository extends ListCrudRepository<Evento, Integer> {


    public Evento save(Evento evento);

    public Optional<Evento> findById(Integer id);



}
