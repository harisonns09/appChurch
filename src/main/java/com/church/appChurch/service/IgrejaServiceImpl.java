package com.church.appChurch.service;

import com.church.appChurch.model.Evento;
import com.church.appChurch.model.Igreja;
import com.church.appChurch.model.dto.EventoResponseDTO;
import com.church.appChurch.model.dto.IgrejaRequestDTO;
import com.church.appChurch.model.dto.IgrejaResponseDTO;
import com.church.appChurch.repository.IgrejaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IgrejaServiceImpl implements IIgrejaService{

    @Autowired
    private IgrejaRepository igrejaRepository;

    @Override
    public List<IgrejaResponseDTO> findAll() {
        return igrejaRepository.findAll().stream().map(
                igreja -> new IgrejaResponseDTO(igreja)).toList(
        );
    }

    @Override
    public Optional<IgrejaResponseDTO> findById(Long id) {
        return igrejaRepository.findById(id).map(igreja -> new IgrejaResponseDTO(igreja));
    }

    @Override
    public IgrejaResponseDTO save(IgrejaRequestDTO igreja) {
        Igreja newIgreja = new Igreja(igreja);
        return new IgrejaResponseDTO(igrejaRepository.save(newIgreja));

    }

    @Override
    public IgrejaResponseDTO update(Long id, IgrejaRequestDTO dto) {

        Igreja igreja = igrejaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Igreja não encontrada"));

        igreja.setName(dto.name());
        igreja.setAddress(dto.address());
        igreja.setCity(dto.city());
        igreja.setState(dto.state());
        igreja.setInstagram(dto.instagram());

        return new IgrejaResponseDTO(igrejaRepository.save(igreja));

    }

    @Override
    public void delete(Long id) {
        igrejaRepository.deleteById(id);
    }
}
