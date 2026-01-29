package com.church.appChurch.service;

import com.church.appChurch.model.Evento;
import com.church.appChurch.model.Ministerios;
import com.church.appChurch.model.dto.EventoRequestDTO;
import com.church.appChurch.model.dto.EventoResponseDTO;
import com.church.appChurch.model.dto.PessoaRequestDTO;
import com.church.appChurch.repository.EventoRepository;
import com.church.appChurch.repository.MinisterioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventoServiceImpl implements IEventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Override
    public List<EventoResponseDTO> findAll() {
        return eventoRepository.findAll().stream()
                .map(evento -> new EventoResponseDTO(evento))
                .toList();
    }

    @Override
    public Optional<EventoResponseDTO> findById(Integer id) {
        return eventoRepository.findById(id).map(EventoResponseDTO::new);
    }

    @Override
    public EventoResponseDTO addEvento(EventoRequestDTO dto) {
        Evento newEvento = new Evento(dto);
        return new EventoResponseDTO(eventoRepository.save(newEvento));

    }

    @Override
    public void deleteById(int id) {
        eventoRepository.deleteById(id);
    }

    @Override
    public EventoResponseDTO update(Integer id, EventoRequestDTO dto) {
        Evento evento = eventoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Evento não encontrado"));

        evento.setNomeEvento(dto.nomeEvento());
        evento.setDataEvento(dto.dataEvento());
        evento.setDescricao(dto.descricao());
        evento.setMinisterioResponsavel(dto.ministerioResponsavel());

        return new EventoResponseDTO(eventoRepository.save(evento));
    }
}
