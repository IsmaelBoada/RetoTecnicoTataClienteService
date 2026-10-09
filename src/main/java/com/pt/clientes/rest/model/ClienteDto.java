package com.pt.clientes.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDto {

    private Long id;
    private UUID tokenId;
    private String contrasena;
    private Boolean estado;
    private PersonaDto persona;

}
