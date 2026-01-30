package com.church.appChurch.model;

import com.church.appChurch.model.dto.MinisterioRequestDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_ministerios")
public class Ministerio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, unique = true)
    private String nome;

    @Column(name = "lider_Responsavel", nullable = false)
    private String liderResponsavel;

    public Ministerio() {
    }

    public Ministerio(MinisterioRequestDTO dto) {
        this.nome = dto.nome();
        this.liderResponsavel = dto.liderResponsavel();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getLiderResponsavel() {
        return liderResponsavel;
    }

    public void setLiderResponsavel(String liderResponsavel) {
        this.liderResponsavel = liderResponsavel;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
