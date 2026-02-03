package com.church.appChurch.model;

import com.church.appChurch.model.dto.IgrejaRequestDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_igreja")
public class Igreja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String instagram;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String state;

    public Igreja() {
        super();
    }

    public Igreja(IgrejaRequestDTO dto){
        this.name = dto.name();
        this.instagram = dto.instagram();
        this.address = dto.address();
        this.city = dto.city();
        this.state = dto.state();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
