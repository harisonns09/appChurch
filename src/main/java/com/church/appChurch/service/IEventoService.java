package com.church.appChurch.service;

import com.church.appChurch.model.dto.EventoRequestDTO;
import com.church.appChurch.model.dto.EventoResponseDTO;
import com.church.appChurch.model.dto.InscricaoRequestDTO;
import com.church.appChurch.model.dto.InscricaoResponseDTO;
import com.church.appChurch.model.dto.infinitepay.InfinitePayWebhookDTO;
import jakarta.validation.Valid;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public interface IEventoService {

    public List<EventoResponseDTO> findAll();
    public Optional<EventoResponseDTO> findById(Integer id);
    public EventoResponseDTO addEvento(EventoRequestDTO dto);
    public void deleteById(int id);
    public EventoResponseDTO update(Integer id,EventoRequestDTO dto);

    public InscricaoResponseDTO realizarInscricao(int id, @Valid InscricaoRequestDTO dto);

    public void processarPagamentoWebhook(InfinitePayWebhookDTO payload);
}
