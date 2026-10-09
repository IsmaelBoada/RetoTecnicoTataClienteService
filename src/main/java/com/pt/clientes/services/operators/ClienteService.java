package com.pt.clientes.services.operators;

import com.pt.clientes.database.models.Cliente;
import com.pt.clientes.database.repository.ClienteRepository;
import com.pt.clientes.rest.builder.ClienteBuilder;
import com.pt.clientes.rest.model.ClienteDto;
import com.pt.clientes.services.exception.GeneralException;
import com.pt.clientes.services.messages.ExceptionMessages;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ClienteService {


    private final ClienteBuilder clienteBuilder;
    private final ClienteRepository clienteRepository;


    public ClienteDto save(ClienteDto model) {
        return clienteBuilder.builderResponse(clienteRepository.save(clienteBuilder.builder(model)));
    }

    public ClienteDto update(UUID idToken, ClienteDto model) {
        Cliente cliente = clienteRepository.findByTokenIdAndEstado(idToken, Boolean.TRUE).orElseThrow(() -> new GeneralException(MessageFormat
                .format(ExceptionMessages.CLIENTE_NOT_FOUND, idToken)));
        return clienteBuilder.builderResponse(clienteRepository.save(clienteBuilder.builderUpdate(cliente, model)));
    }

    public ClienteDto findByTokenId(UUID tokenId) {
        return clienteBuilder.builderResponse(clienteRepository.findByTokenIdAndEstado(tokenId, Boolean.TRUE)
                .orElseThrow(() -> new GeneralException(MessageFormat.format(ExceptionMessages.CLIENTE_NOT_FOUND, tokenId))));
    }

    public void delete(UUID tokenId) {
        Cliente cliente = clienteRepository.findByTokenIdAndEstado(tokenId, Boolean.TRUE)
                .orElseThrow(() -> new GeneralException(MessageFormat.format(ExceptionMessages.CLIENTE_NOT_FOUND, tokenId)));
        cliente.setEstado(Boolean.FALSE);
        clienteRepository.save(cliente);
    }

}
