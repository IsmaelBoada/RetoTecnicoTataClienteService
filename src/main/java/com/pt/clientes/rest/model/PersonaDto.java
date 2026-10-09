package com.pt.clientes.rest.model;

import com.pt.clientes.services.enums.GeneroEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonaDto {

    private Long id;
    private UUID tokenId;
    @NotNull(message = "Es requerido el nombre")
    private String nombre;
    private GeneroEnum genero;
    private Integer edad;
    @NotNull(message = "Es requerido la identificación")
    private String identificacion;
    @NotNull(message = "Es requerido la dirección")
    private String direccion;
    private String telefono;

}
