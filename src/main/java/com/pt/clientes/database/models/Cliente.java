package com.pt.clientes.database.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID tokenId;

    private String contrasena;

    @Column(nullable = false)
    private Boolean estado;

    @ManyToOne
    private Persona persona;


    @PrePersist
    public void prePersist() {
        if (Objects.isNull(tokenId)) tokenId = UUID.randomUUID();
        if (Objects.isNull(estado)) estado = Boolean.TRUE;
    }


}
