package com.church.appChurch.service;

import com.church.appChurch.model.Evento;
import com.church.appChurch.model.Inscricao;
import com.church.appChurch.model.dto.EventoRequestDTO;
import com.church.appChurch.model.dto.EventoResponseDTO;
import com.church.appChurch.model.dto.InscricaoRequestDTO;
import com.church.appChurch.model.dto.InscricaoResponseDTO;
import com.church.appChurch.repository.EventoRepository;
import com.church.appChurch.repository.InscricaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class EventoServiceImpl implements IEventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private InscricaoRepository inscricaoRepository;

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
        List<Inscricao> inscricoes = inscricaoRepository.findbyEventoId(id);
        for (Inscricao inscricao : inscricoes) {
            inscricaoRepository.delete(inscricao);
        }
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

    @Override
    @Transactional
    public InscricaoResponseDTO realizarInscricao(int eventoId, InscricaoRequestDTO dto) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(()-> new RuntimeException("Evento não encontrado"));


        Inscricao newInscricao = new Inscricao(dto, evento);

        newInscricao = inscricaoRepository.save(newInscricao);

        String dataHoje = newInscricao.getDataInscricao().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        newInscricao.setNumeroInscricao(dataHoje + eventoId + newInscricao.getId());

        return new InscricaoResponseDTO(inscricaoRepository.save(newInscricao));
    }


}
