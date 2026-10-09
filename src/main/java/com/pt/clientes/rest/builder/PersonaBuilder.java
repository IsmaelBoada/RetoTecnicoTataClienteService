package com.pt.clientes.rest.builder;

import com.pt.clientes.database.models.Persona;
import com.pt.clientes.rest.model.PersonaDto;
import org.springframework.stereotype.Component;

@Component
public class PersonaBuilder {


    public Persona builder(PersonaDto model) {
        return Persona.builder()
                .nombre(model.getNombre())
                .genero(model.getGenero())
                .edad(model.getEdad())
                .identificacion(model.getIdentificacion())
                .direccion(model.getDireccion())
                .telefono(model.getTelefono())
                .build();
    }


    public Persona builderUpdate(Persona item, PersonaDto model) {
        return Persona.builder()
                .id(item.getId())
                .tokenId(item.getTokenId())
                .nombre(model.getNombre())
                .genero(model.getGenero())
                .edad(model.getEdad())
                .identificacion(model.getIdentificacion())
                .direccion(model.getDireccion())
                .telefono(model.getTelefono())
                .build();
    }

    public PersonaDto builderResponse(Persona model) {
        return PersonaDto.builder()
                .id(model.getId())
                .tokenId(model.getTokenId())
                .nombre(model.getNombre())
                .genero(model.getGenero())
                .edad(model.getEdad())
                .identificacion(model.getIdentificacion())
                .direccion(model.getDireccion())
                .telefono(model.getTelefono())
                .build();
    }


}
