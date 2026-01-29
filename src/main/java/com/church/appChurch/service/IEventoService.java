package com.church.appChurch.service;

import com.church.appChurch.model.dto.EventoRequestDTO;
import com.church.appChurch.model.dto.EventoResponseDTO;

import java.util.List;
import java.util.Optional;

public interface IEventoService {

    public List<EventoResponseDTO> findAll();
    public Optional<EventoResponseDTO> findById(Integer id);
    public EventoResponseDTO addEvento(EventoRequestDTO dto);
    public void deleteById(int id);
    public EventoResponseDTO update(Integer id,EventoRequestDTO dto);
}
