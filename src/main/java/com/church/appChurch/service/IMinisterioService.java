package com.church.appChurch.service;

import com.church.appChurch.model.Ministerios;
import java.util.List;
import java.util.Optional;

public interface IMinisterioService {

    public List<Ministerios> findAll();
    public Optional<Ministerios> findById(Long id);
    public Ministerios addMinisterio(Ministerios ministerio);
    public void deleteById(Long id);
    public Ministerios save(Ministerios ministerio);
}
