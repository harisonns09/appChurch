package com.church.appChurch.service;

import com.church.appChurch.infra.audit.Loggable;
import com.church.appChurch.model.Igreja;
import com.church.appChurch.model.Ministerio;
import com.church.appChurch.model.dto.MinisterioRequestDTO;
import com.church.appChurch.model.dto.MinisterioResponseDTO;
import com.church.appChurch.repository.IgrejaRepository;
import com.church.appChurch.repository.MinisterioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MinisterioServiceImpl implements IMinisterioService {

    @Autowired
    private MinisterioRepository ministerioRepository;

    @Autowired
    private IgrejaRepository igrejaRepository;


    @Override
    public List<MinisterioResponseDTO> findAllByIgreja(Long igrejaId) {

        return ministerioRepository.findAllByIgrejaId(igrejaId).stream()
                .map(ministerio -> new MinisterioResponseDTO(ministerio))
                .toList();
    }

    @Override
    public Optional<Ministerio> findById(Long id) {
        return ministerioRepository.findById(id);
    }

    @Override
    @Loggable(action = "CRIAR", entity = "MINISTERIO") // <--- SÓ ADICIONAR ISSO
    public MinisterioResponseDTO addMinisterio(Long igrejaId, MinisterioRequestDTO dto) {
        Igreja igreja = igrejaRepository.findById(igrejaId)
                .orElseThrow(() -> new RuntimeException("Igreja não encontrada com ID: " + igrejaId));

        Ministerio ministerio = new Ministerio(dto, igreja);

        Ministerio ministerioSalvo = ministerioRepository.save(ministerio);

        return new MinisterioResponseDTO(ministerioSalvo);
    }

    @Override
    @Loggable(action = "DELETAR", entity = "MINISTERIO") // <--- SÓ ADICIONAR ISSO
    public void deleteById(Long id) {
        ministerioRepository.deleteById(id);
    }

    @Override
    @Loggable(action = "ALTERAR", entity = "MINISTERIO") // <--- SÓ ADICIONAR ISSO
    public MinisterioResponseDTO updateMinisterio(Long idIgreja, Long idMinisterio, MinisterioRequestDTO dto) {

        Ministerio ministerio = ministerioRepository.findById(idMinisterio)
                        .orElseThrow(() -> new RuntimeException("Ministerio não encontrado com ID: " + idMinisterio));

        if(!ministerio.getIgreja().getId().equals(idIgreja)) {
            throw new RuntimeException("Ministério não pertence a esta Igreja");
        }

        ministerio.setNome(dto.nome());
        ministerio.setLiderResponsavel(dto.liderResponsavel());

        return new MinisterioResponseDTO(ministerioRepository.save(ministerio));
    }
}
