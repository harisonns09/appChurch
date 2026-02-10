package com.church.appChurch.service;

import com.church.appChurch.enums.StatusPagamento;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class EventoServiceImpl implements IEventoService {

    // 1. Instanciando o Logger padrão do SLF4J
    private static final Logger log = LoggerFactory.getLogger(EventoServiceImpl.class);

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private InscricaoRepository inscricaoRepository;

    @Autowired
    private IgrejaRepository igrejaRepository;

    @Override
    public List<EventoResponseDTO> findByIgrejaId(Long idIgreja) {
        // Leitura simples não precisa de log (o AOP do Controller já registra a chamada)
        return eventoRepository.findByIgrejaId(idIgreja).stream()
                .map(EventoResponseDTO::new)
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

        Evento saved = eventoRepository.save(newEvento);

        // Log de Auditoria: Importante saber quando um evento é criado
        log.info("Novo evento criado: ID={} | Nome='{}' | IgrejaID={}", saved.getId(), saved.getNomeEvento(), igreja.getId());

        return new EventoResponseDTO(saved);
    }

    @Override
    @Transactional // Adicionado Transactional pois há deleção em cascata manual
    public void deleteById(int id) {
        log.info("Solicitada exclusão do evento ID: {}", id);

        List<Inscricao> inscricoes = inscricaoRepository.findbyEventoId(id);
        int qtdInscricoes = inscricoes.size();

        for (Inscricao inscricao : inscricoes) {
            inscricaoRepository.delete(inscricao);
        }
        eventoRepository.deleteById(id);

        log.info("Evento ID: {} excluído com sucesso. ({} inscrições removidas em cascata)", id, qtdInscricoes);
    }

    @Override
    public EventoResponseDTO update(Integer id, EventoRequestDTO dto) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado"));

        evento.setNomeEvento(dto.nomeEvento());
        evento.setDataEvento(dto.dataEvento());
        evento.setDescricao(dto.descricao());
        evento.setHorario(dto.horario());
        evento.setLocal(dto.local());
        evento.setPreco(dto.preco());
        evento.setMinisterioResponsavel(dto.ministerioResponsavel());

        Evento updated = eventoRepository.save(evento);
        log.info("Evento ID: {} atualizado com sucesso.", id);

        return new EventoResponseDTO(updated);
    }

    @Override
    @Transactional
    public InscricaoResponseDTO realizarInscricao(int eventoId, InscricaoRequestDTO dto) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(()-> new RuntimeException("Evento não encontrado"));

        if (inscricaoRepository.existsByCpfAndEventoId(dto.cpf(), eventoId)) {
            // Log de Aviso (Warn): Alguém tentou duplicar inscrição
            log.warn("Tentativa de inscrição duplicada bloqueada. CPF: ***.***.{}-** | Evento: {}", dto.cpf().substring(9), eventoId);
            throw new IllegalArgumentException("Este CPF já possui uma inscrição confirmada ou pendente para este evento.");
        }

        Inscricao newInscricao = new Inscricao(dto, evento);
        newInscricao = inscricaoRepository.save(newInscricao);

        String dataHoje = newInscricao.getDataInscricao().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String numeroGerado = dataHoje + eventoId + newInscricao.getId();
        newInscricao.setNumeroInscricao(numeroGerado);

        Inscricao saved = inscricaoRepository.save(newInscricao);

        log.info("Inscrição realizada: #{} | Evento: {} | Nome: {}", numeroGerado, evento.getNomeEvento(), saved.getNome());

        return new InscricaoResponseDTO(saved);
    }

    @Override
    @Transactional
    public void processarPagamentoWebhook(InfinitePayWebhookDTO payload) {
        String numeroInscricao = payload.orderNsu(); // Assumindo que NSU é o ID da inscrição

        log.info("Webhook InfinitePay recebido. NSU/Inscrição: {}", numeroInscricao);

        if (numeroInscricao == null) {
            log.error("Webhook recebido sem OrderNSU (ID Inscrição). Payload: {}", payload);
            throw new RuntimeException("Webhook recebido sem ID de Inscrição no metadata");
        }

        Inscricao inscricao = inscricaoRepository.findByNumero_Inscricao(numeroInscricao);

        if (inscricao == null) {
            log.error("Inscrição não encontrada para o número: {}", numeroInscricao);
            throw new RuntimeException("Inscrição não encontrada");
        }

        // Idempotência: Só atualiza se ainda não estiver pago
        if (!"PAGO".equals(inscricao.getStatus())) {
            inscricao.setStatus(StatusPagamento.PAGO.getStatusPagamento());
            inscricao.setDataPagamento(java.time.LocalDateTime.now());
            inscricao.setComprovante(payload.receiptUrl());

            inscricaoRepository.save(inscricao);

            log.info("Pagamento confirmado com sucesso via Webhook para inscrição: #{}", numeroInscricao);
        } else {
            log.warn("Webhook ignorado: Inscrição #{} já consta como PAGO.", numeroInscricao);
        }
    }

    @Override
    @Transactional
    public void atualizarMetodoPagamento(String idInscricao, String tipoPagamento) {
        try {
            Inscricao inscricao = inscricaoRepository.findByNumero_Inscricao(idInscricao);

            if (inscricao == null) {
                throw new RuntimeException("Inscrição não encontrada: " + idInscricao);
            }

            if (!tipoPagamento.equals(inscricao.getTipoPagamento())) {
                String tipoAntigo = inscricao.getTipoPagamento();
                inscricao.setTipoPagamento(tipoPagamento);
                inscricaoRepository.save(inscricao);

                log.info("Método de pagamento alterado na inscrição #{}. De '{}' para '{}'", idInscricao, tipoAntigo, tipoPagamento);
            }
        } catch (Exception e) { // Catch genérico para garantir log
            // Logamos o erro completo com StackTrace para debug
            log.error("Erro ao atualizar método de pagamento da inscrição {}", idInscricao, e);
            throw new RuntimeException("Erro ao atualizar pagamento: " + e.getMessage());
        }
    }
}