package com.church.appChurch.service;

import com.church.appChurch.model.Ministerios;
import com.church.appChurch.repository.MinisterioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MinisterioServiceImpl implements IMinisterioService {

    @Autowired
    private MinisterioRepository ministerioRepository;

    @Override
    public List<Ministerios> findAll() {
        return ministerioRepository.findAll();
    }

    @Override
    public Optional<Ministerios> findById(Long id) {
        return ministerioRepository.findById(id);
    }

    @Override
    public Ministerios addMinisterio(Ministerios ministerio) {
        return ministerioRepository.save(ministerio);
    }

    @Override
    public void deleteById(Long id) {
        ministerioRepository.deleteById(id);
    }

    @Override
    public Ministerios save(Ministerios ministerio) {
        return ministerioRepository.save(ministerio);
    }
}
