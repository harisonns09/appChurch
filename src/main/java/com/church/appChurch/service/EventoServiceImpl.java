package com.church.appChurch.service;

import com.church.appChurch.model.Evento;
import com.church.appChurch.model.Igreja;
import com.church.appChurch.model.Inscricao;
import com.church.appChurch.model.dto.EventoRequestDTO;
import com.church.appChurch.model.dto.EventoResponseDTO;
import com.church.appChurch.model.dto.InscricaoRequestDTO;
import com.church.appChurch.model.dto.InscricaoResponseDTO;
import com.church.appChurch.model.dto.infinitepay.InfinitePayWebhookDTO;
import com.church.appChurch.repository.EventoRepository;
import com.church.appChurch.repository.IgrejaRepository;
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

    @Autowired
    private IgrejaRepository igrejaRepository;

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
        Igreja igreja = igrejaRepository.findById(dto.igrejaId())
                .orElseThrow(() -> new RuntimeException("Igreja não encontrada"));
        Evento newEvento = new Evento(dto);
        newEvento.setIgreja(igreja);
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
        // Atualizando novos campos
        evento.setHorario(dto.horario());
        evento.setLocal(dto.local());
        evento.setPreco(dto.preco());

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

    @Override
    @Transactional
    public void processarPagamentoWebhook(InfinitePayWebhookDTO payload) {
        // 1. Verifica se o status é de sucesso
        // A InfinitePay pode enviar "paid" ou "approved"
        if (!"paid".equalsIgnoreCase(payload.status()) && !"approved".equalsIgnoreCase(payload.status())) {
            // Se for "failed" ou "pending", talvez apenas logar e ignorar
            return;
        }

        // 2. Extrai o ID da inscrição do Metadata
        // Lembre-se: alteramos o InfinitePayMetadata para ter um campo registrationId ou numeroInscricao
        String inscricaoIdStr = payload.metadata().numero_inscricao(); // Ou o nome do campo que você criou

        if (inscricaoIdStr == null) {
            throw new RuntimeException("Webhook recebido sem ID de Inscrição no metadata");
        }

        Long inscricaoId = Long.parseLong(inscricaoIdStr);

        // 3. Busca no Banco
        Inscricao inscricao = inscricaoRepository.findById(inscricaoId)
                .orElseThrow(() -> new RuntimeException("Inscrição não encontrada para o ID: " + inscricaoId));

        // 4. Atualiza Status (Idempotência: Só atualiza se ainda não estiver pago)
        if (!"PAGO".equals(inscricao.getStatus())) {
            inscricao.setStatus("PAGO");
            inscricao.setDataPagamento(java.time.LocalDateTime.now());

            inscricaoRepository.save(inscricao);
            System.out.println("Inscrição #" + inscricaoId + " atualizada para PAGO via Webhook.");
        }
    }



}
