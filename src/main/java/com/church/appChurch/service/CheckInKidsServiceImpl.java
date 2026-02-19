package com.church.appChurch.service;

import com.church.appChurch.enums.StatusCheckIn;
import com.church.appChurch.model.CheckInKids;
import com.church.appChurch.model.Pessoa;
import com.church.appChurch.model.dto.CheckInKidsRequestDTO;
import com.church.appChurch.model.dto.CheckInKidsResponseDTO;
import com.church.appChurch.repository.CheckInKidsRepository;
import com.church.appChurch.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class CheckInKidsServiceImpl implements ICheckInKidsService{

    @Autowired
    private CheckInKidsRepository checkInKidsRepository;
    @Autowired
    private PessoaRepository pessoaRepository;

    @Override
    public CheckInKidsResponseDTO realizarCheckIn(Long igrejaId, CheckInKidsRequestDTO dto) {

        // Gera código único curto (Ex: K-8291)
        String codigo = gerarCodigoSeguranca();


        CheckInKids checkIn = new CheckInKids(dto,codigo);

        return new CheckInKidsResponseDTO(checkInKidsRepository.save(checkIn));


    }

    @Override
    public void realizarCheckOut(Long checkInKidsId) {
        CheckInKids checkIn = checkInKidsRepository.findById(checkInKidsId)
                .orElseThrow(() -> new IllegalArgumentException("Check-in não encontrado"));

        checkIn.setStatus(StatusCheckIn.FINALIZADO);
        checkIn.setDataSaida(LocalDateTime.now());

        checkInKidsRepository.save(checkIn);
    }

    @Override
    public List<CheckInKidsResponseDTO> listarAtivos(Long igrejaId) {
        return checkInKidsRepository.findByIgrejaIdAndStatusOrderByDataEntradaDesc(igrejaId, StatusCheckIn.ATIVO)
                .stream()
                .map(CheckInKidsResponseDTO::new)
                .toList();
    }

    private String gerarCodigoSeguranca() {
        Random random = new Random();
        int numero = 1000 + random.nextInt(9000); // 1000 a 9999
        return "K-" + numero;
    }
}
