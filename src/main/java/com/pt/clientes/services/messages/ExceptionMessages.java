package com.pt.clientes.services.messages;


public abstract class ExceptionMessages {


    public ExceptionMessages() {
    }

    public static final String CLIENTE_NOT_FOUND = "Cliente con token id {0} no encontrado ";
    public static final String ERROR_IDENTIFICACION_REGISTRADA = "La identificación {0}, ya se encuentra registrada en el sistema";
    public static final String ERROR_IDENTIFICACION_SIZE = "La identificación {0}, debe tener entre 10 y 13 caracteres";
    public static final String ERROR_NO_EXISTE_PERSONA = "La persona con token id {0}, no existe en el sistema";
    public static final String ERROR_VALIDACIONES_REQUEST = "Existe los siguientes errores: ";

    public static final String ERROR_GENERO_ENUM = "Genero no disponible:";
    public static final String PERSONA_NO_FOUND = "Es requerido la información persona para poder crear el cliente";
}
