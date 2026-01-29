package com.church.appChurch.model;

import com.church.appChurch.model.dto.EventoRequestDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tb_eventos")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_evento", nullable = false, unique = true)
    private String nomeEvento;

    @Column(name = "dataEvento", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataEvento;

    @Column(name = "ministerio_responsavel", nullable = false)
    private String ministerioResponsavel;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "lista_pessoas")
    private List<String> listaPessoasInscritas;

    public Evento() {
        super();
    }

    public Evento(EventoRequestDTO dto) {
        this.nomeEvento = dto.nomeEvento();
        this.dataEvento = dto.dataEvento();
        this.ministerioResponsavel = dto.ministerioResponsavel();
        this.descricao = dto.descricao();
        this.listaPessoasInscritas = dto.listaPessoasInscritas();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeEvento() {
        return nomeEvento;
    }

    public void setNomeEvento(String nomeEvento) {
        this.nomeEvento = nomeEvento;
    }

    public LocalDate getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(LocalDate dataEvento) {
        this.dataEvento = dataEvento;
    }

    public String getMinisterioResponsavel() {
        return ministerioResponsavel;
    }

    public void setMinisterioResponsavel(String ministerioResponsavel) {
        this.ministerioResponsavel = ministerioResponsavel;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<String> getListaPessoasInscritas() {
        return listaPessoasInscritas;
    }

    public void setListaPessoasInscritas(List<String> listaPessoasInscritas) {
        this.listaPessoasInscritas = listaPessoasInscritas;
    }
}
