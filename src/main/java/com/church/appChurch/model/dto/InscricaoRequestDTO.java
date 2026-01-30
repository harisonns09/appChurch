package com.church.appChurch.model.dto;

import com.church.appChurch.model.Evento;
import jakarta.persistence.*;

import java.time.LocalDateTime;

public record InscricaoRequestDTO(

        String nome,
        String email,
        String telefone

) {
}

