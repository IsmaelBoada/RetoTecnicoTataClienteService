package com.pt.clientes.rest.events;

import java.util.UUID;

public record ClienteRegistradoEvent(UUID tokenId,
                                     String nombre,
                                     String identificacion) {
}
