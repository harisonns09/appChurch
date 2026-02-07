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
    public List<EventoResponseDTO> findByIgrejaId(Long idIgreja) {
        return eventoRepository.findByIgrejaId(idIgreja).stream()
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


        if (inscricaoRepository.existsByCpfAndEventoId(dto.cpf(), eventoId)) {
            // Lança um erro que o Controller vai pegar e mandar pro Front
            throw new IllegalArgumentException("Este CPF já possui uma inscrição confirmada ou pendente para este evento.");
        }

        Inscricao newInscricao = new Inscricao(dto, evento);

        newInscricao = inscricaoRepository.save(newInscricao);

        String dataHoje = newInscricao.getDataInscricao().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        newInscricao.setNumeroInscricao(dataHoje + eventoId + newInscricao.getId());

        return new InscricaoResponseDTO(inscricaoRepository.save(newInscricao));
    }

    @Override
    @Transactional
    public void processarPagamentoWebhook(InfinitePayWebhookDTO payload) {

        String numeroInscricao = payload.orderNsu();

        if (numeroInscricao == null) {
            throw new RuntimeException("Webhook recebido sem ID de Inscrição no metadata");
        }

        // 3. Busca no Banco
        Inscricao inscricao = inscricaoRepository.findByNumero_Inscricao(numeroInscricao);

        // 4. Atualiza Status (Idempotência: Só atualiza se ainda não estiver pago)
        if (!"PAGO".equals(inscricao.getStatus())) {
            inscricao.setStatus("PAGO");
            inscricao.setDataPagamento(java.time.LocalDateTime.now());
            inscricao.setComprovante(payload.receiptUrl());

            inscricaoRepository.save(inscricao);
            System.out.println("Inscrição #" + numeroInscricao + " atualizada para PAGO via Webhook.");
        }
    }

    @Override
    @Transactional
    public void atualizarMetodoPagamento(String idInscricao, String tipoPagamento) {
        try {
            Inscricao inscricao = inscricaoRepository.findByNumero_Inscricao(idInscricao);
            if(!inscricao.getTipoPagamento().equals(tipoPagamento)) {
                inscricao.setTipoPagamento(tipoPagamento);

                inscricaoRepository.save(inscricao);
            }

        }catch (RuntimeException e){
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar pagamento: " + e.getMessage());
        }

    }


}
