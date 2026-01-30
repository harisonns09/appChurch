package com.church.appChurch.service;

import com.church.appChurch.model.Ministerio;
import com.church.appChurch.model.dto.MinisterioRequestDTO;
import com.church.appChurch.model.dto.MinisterioResponseDTO;

import java.util.List;
import java.util.Optional;

public interface IMinisterioService {

    public List<MinisterioResponseDTO> findAll();
    public Optional<Ministerio> findById(Long id);
    public MinisterioResponseDTO addMinisterio(MinisterioRequestDTO ministerio);
    public void deleteById(Long id);
    public Ministerio save(Ministerio ministerio);
}
