package com.church.appChurch.service;

import com.church.appChurch.model.Ministerio;
import com.church.appChurch.model.dto.MinisterioRequestDTO;
import com.church.appChurch.model.dto.MinisterioResponseDTO;
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
    public List<MinisterioResponseDTO> findAll() {

        return ministerioRepository.findAll().stream()
                .map(ministerio -> new MinisterioResponseDTO(ministerio))
                .toList();
    }

    @Override
    public Optional<Ministerio> findById(Long id) {
        return ministerioRepository.findById(id);
    }

    @Override
    public MinisterioResponseDTO addMinisterio(MinisterioRequestDTO dto) {
        Ministerio newMinisterio = new Ministerio(dto);

        return new MinisterioResponseDTO(ministerioRepository.save(newMinisterio));
    }

    @Override
    public void deleteById(Long id) {
        ministerioRepository.deleteById(id);
    }

    @Override
    public Ministerio save(Ministerio ministerio) {
        return ministerioRepository.save(ministerio);
    }
}
