package com.pt.clientes.services.operators;

import com.pt.clientes.database.models.Cliente;
import com.pt.clientes.database.repository.ClienteRepository;
import com.pt.clientes.rest.builder.ClienteBuilder;
import com.pt.clientes.rest.events.ClienteRegistradoEvent;
import com.pt.clientes.rest.model.ClienteDto;
import com.pt.clientes.services.exception.GeneralException;
import com.pt.clientes.services.messages.ExceptionMessages;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ClienteService {


    private final ClienteBuilder clienteBuilder;
    private final ClienteRepository clienteRepository;
    private final PersonaService personaService;
    private final ClienteRegistroProducer clienteRegistradoProducer;

    @Transactional
    public ClienteDto save(ClienteDto model) {

        if (Objects.isNull(model.getPersona())) {
            throw new GeneralException(ExceptionMessages.PERSONA_NO_FOUND);
        }

        Cliente cliente = clienteRepository.save(clienteBuilder.builder(model,
                personaService.create(model.getPersona())));

        clienteRegistradoProducer.publicar(
                new ClienteRegistradoEvent(
                        cliente.getTokenId(),
                        cliente.getPersona().getNombre(),
                        cliente.getPersona().getIdentificacion()
                )
        );


        return clienteBuilder.builderResponse(cliente);
    }

    public ClienteDto update(UUID idToken, ClienteDto model) {
        Cliente cliente = clienteRepository.findByTokenId(idToken).orElseThrow(() -> new GeneralException(MessageFormat
                .format(ExceptionMessages.CLIENTE_NOT_FOUND, idToken)));
        return clienteBuilder.builderResponse(clienteRepository.save(clienteBuilder.builderUpdate(cliente, model)));
    }

    public ClienteDto findByTokenId(UUID tokenId) {
        return clienteBuilder.builderResponse(clienteRepository.findByTokenId(tokenId)
                .orElseThrow(() -> new GeneralException(MessageFormat.format(ExceptionMessages.CLIENTE_NOT_FOUND, tokenId))));
    }

    public void delete(UUID tokenId) {
        Cliente cliente = clienteRepository.findByTokenId(tokenId)
                .orElseThrow(() -> new GeneralException(MessageFormat.format(ExceptionMessages.CLIENTE_NOT_FOUND, tokenId)));
        clienteRepository.delete(cliente);
    }

}
