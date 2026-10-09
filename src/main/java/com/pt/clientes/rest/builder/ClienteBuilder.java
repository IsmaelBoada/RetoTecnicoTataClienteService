package com.pt.clientes.rest.builder;

import com.pt.clientes.database.models.Cliente;
import com.pt.clientes.database.models.Persona;
import com.pt.clientes.rest.model.ClienteDto;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ClienteBuilder {

    public Cliente builder(ClienteDto model, Persona persona) {
        return Cliente.builder()
                .contrasena(model.getContrasena())
                .estado(model.getEstado())
                .persona(persona)
                .build();
    }


    public Cliente builderUpdate(Cliente item, ClienteDto model) {
        return Cliente.builder()
                .id(item.getId())
                .tokenId(item.getTokenId())
                .contrasena(model.getContrasena())
                .estado(model.getEstado())
                .build();
    }

    public ClienteDto builderResponse(Cliente model) {
        return ClienteDto.builder()
                .id(model.getId())
                .tokenId(model.getTokenId())
                .estado(model.getEstado())
                .build();
    }
}
