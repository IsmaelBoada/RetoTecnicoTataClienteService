package com.pt.clientes.services.operators;

import com.pt.clientes.database.models.Persona;
import com.pt.clientes.database.repository.ClienteRepository;
import com.pt.clientes.database.repository.PersonaRepository;
import com.pt.clientes.rest.builder.PersonaBuilder;
import com.pt.clientes.rest.model.PersonaDto;
import com.pt.clientes.services.exception.GeneralException;
import com.pt.clientes.services.messages.ExceptionMessages;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PersonaService {

    private final PersonaBuilder personaBuilder;
    private final PersonaRepository personaRepository;
    private final ClienteRepository clienteRepository;


    @Transactional
    public Persona create(PersonaDto model) {


        return savePersona(model);
    }

    public PersonaDto save(PersonaDto model) {
        return personaBuilder.builderResponse(savePersona(model));
    }

    private Persona savePersona(PersonaDto model) {
        validateSizeIdentificacion(model.getIdentificacion());
        validateExistsIdentificacion(model.getIdentificacion());

        Persona persona = personaBuilder.builder(model);

        return personaRepository.save(persona);
    }


    public PersonaDto update(UUID tokenId, PersonaDto model) {

        Persona persona = personaRepository.findByTokenId(tokenId)
                .orElseThrow(() -> new GeneralException(MessageFormat.format(
                        ExceptionMessages.ERROR_NO_EXISTE_PERSONA, tokenId)));

        if (!persona.getIdentificacion().equals(model.getIdentificacion())) {
            validateSizeIdentificacion(model.getIdentificacion());
            validateExistsIdentificacion(model.getIdentificacion());
        }

        return personaBuilder.builderResponse(personaRepository.save(personaBuilder
                .builderUpdate(persona, model)));
    }


    public PersonaDto findByTokenId(UUID tokenId) {
        return personaBuilder.builderResponse(personaRepository.findByTokenId(tokenId)
                .orElseThrow(() -> new GeneralException(MessageFormat.format(
                        ExceptionMessages.ERROR_NO_EXISTE_PERSONA, tokenId))));
    }


    @Transactional
    public void delete(UUID tokenId) {
        Persona persona = personaRepository.findByTokenId(tokenId)
                .orElseThrow(() -> new GeneralException(MessageFormat.format(
                        ExceptionMessages.ERROR_NO_EXISTE_PERSONA, tokenId)));

        clienteRepository.deleteByPersonaId(tokenId);
        personaRepository.delete(persona);
    }


    private void validateExistsIdentificacion(String identificacion) {

        personaRepository.findByIdentificacion(identificacion)
                .ifPresent(persona -> {
                    throw new GeneralException(MessageFormat.format(ExceptionMessages.ERROR_IDENTIFICACION_REGISTRADA,
                            identificacion));
                });
    }

    private void validateSizeIdentificacion(String identificacion) {
        if (identificacion.length() < 10 || identificacion.length() > 13) {
            throw new GeneralException(MessageFormat.format(ExceptionMessages.ERROR_IDENTIFICACION_SIZE,
                    identificacion));
        }
    }


}
