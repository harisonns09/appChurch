package com.church.appChurch.model;

import com.church.appChurch.model.dto.InscricaoRequestDTO;
import com.church.appChurch.model.dto.InscricaoResponseDTO;
import com.google.api.client.util.DateTime;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_inscricoes")
public class Inscricao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "telefone", nullable = false)
    private String telefone; // Sugestão: adicione telefone, é vital para eventos

    @Column(unique = true)
    private String numeroInscricao;

    @Column(name = "data_inscricao")
    private LocalDateTime dataInscricao;

    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    public Inscricao() {
        super();
    }

    public Inscricao(InscricaoRequestDTO dto, Evento evento){
        this.nome = dto.nome();
        this.email = dto.email();
        this.telefone = dto.telefone();
        this.dataInscricao = LocalDateTime.now();
        this.evento = evento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public LocalDateTime getDataInscricao() {
        return dataInscricao;
    }

    public void setDataInscricao(LocalDateTime dataInscricao) {
        this.dataInscricao = dataInscricao;
    }

    public String getNumeroInscricao() {
        return numeroInscricao;
    }

    public void setNumeroInscricao(String numeroInscricao) {
        this.numeroInscricao = numeroInscricao;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
