package com.church.appChurch.model;

import com.church.appChurch.enums.StatusCheckIn;
import com.church.appChurch.model.dto.CheckInKidsRequestDTO;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_kids_checkin")
public class CheckInKids {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long igrejaId;

    @Column(name = "nome_crianca")
    private String crianca;

    @Column(name = "nome_responsavel")
    private String responsavel;

    @Column(name = "telefone_responsavel")
    private String telefoneResponsavel;

    // O código que vai na etiqueta (Ex: "A-452")
    private String codigoSeguranca;

    private LocalDateTime dataEntrada;
    private LocalDateTime dataSaida;

    @Column(columnDefinition = "TEXT")
    private String observacoes; // "Fralda trocada", "Não pode glúten"

    @Enumerated(EnumType.STRING)
    private StatusCheckIn status;

    public CheckInKids() {
        super();
    }

    // Construtor utilitário
    public CheckInKids(CheckInKidsRequestDTO dto, String codigoSeguranca) {
        this.igrejaId = dto.igrejaId();
        this.crianca = dto.nomeCrianca();
        this.responsavel = dto.nomeResponsavel();
        this.codigoSeguranca = codigoSeguranca;
        this.observacoes = dto.observacoes();
        this.dataEntrada = LocalDateTime.now();
        this.status = StatusCheckIn.ATIVO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIgrejaId() {
        return igrejaId;
    }

    public void setIgrejaId(Long igrejaId) {
        this.igrejaId = igrejaId;
    }

    public String getCrianca() {
        return crianca;
    }

    public void setCrianca(String crianca) {
        this.crianca = crianca;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getCodigoSeguranca() {
        return codigoSeguranca;
    }

    public void setCodigoSeguranca(String codigoSeguranca) {
        this.codigoSeguranca = codigoSeguranca;
    }

    public LocalDateTime getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDateTime dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public LocalDateTime getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(LocalDateTime dataSaida) {
        this.dataSaida = dataSaida;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public StatusCheckIn getStatus() {
        return status;
    }

    public void setStatus(StatusCheckIn status) {
        this.status = status;
    }

    public String getTelefoneResponsavel() {
        return telefoneResponsavel;
    }

    public void setTelefoneResponsavel(String telefoneResponsavel) {
        this.telefoneResponsavel = telefoneResponsavel;
    }
}