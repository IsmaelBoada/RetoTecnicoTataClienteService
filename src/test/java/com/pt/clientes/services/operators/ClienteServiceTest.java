package com.pt.clientes.services.operators;

import com.pt.clientes.database.models.Cliente;
import com.pt.clientes.database.models.Persona;
import com.pt.clientes.database.repository.ClienteRepository;
import com.pt.clientes.rest.builder.ClienteBuilder;
import com.pt.clientes.rest.events.ClienteRegistradoEvent;
import com.pt.clientes.rest.model.ClienteDto;
import com.pt.clientes.rest.model.PersonaDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;


    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private PersonaService personaService;

    @Mock
    private ClienteRegistroProducer clienteRegistradoProducer;

    @Mock
    private ClienteBuilder clienteBuilder;

    @Test
    void save() {
        UUID tokenId = UUID.randomUUID();

        PersonaDto personaDto = new PersonaDto();
        ClienteDto model = new ClienteDto();
        model.setPersona(personaDto);

        Persona persona = mock(Persona.class);
        Cliente cliente = mock(Cliente.class);
        ClienteDto respuesta = new ClienteDto();

        when(personaService.create(personaDto)).thenReturn(persona);
        when(clienteBuilder.builder(model, persona)).thenReturn(cliente);
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        when(cliente.getTokenId()).thenReturn(tokenId);
        when(cliente.getPersona()).thenReturn(persona);
        when(persona.getNombre()).thenReturn("Ismael Boada");
        when(persona.getIdentificacion()).thenReturn("1712345678");

        when(clienteBuilder.builderResponse(cliente)).thenReturn(respuesta);

        ClienteDto resultado = clienteService.save(model);

        assertSame(respuesta, resultado);

        verify(personaService).create(personaDto);
        verify(clienteRepository).save(cliente);

        verify(clienteRegistradoProducer).publicar(
                new ClienteRegistradoEvent(
                        tokenId,
                        "Ismael Boada",
                        "1712345678"
                )
        );
    }

    @Test
    void update() {
        UUID tokenId = UUID.randomUUID();

        Cliente clienteExistente = mock(Cliente.class);
        ClienteDto model = new ClienteDto();
        Cliente clienteActualizado = mock(Cliente.class);
        ClienteDto respuesta = new ClienteDto();

        when(clienteRepository.findByTokenId(tokenId))
                .thenReturn(Optional.of(clienteExistente));

        when(clienteBuilder.builderUpdate(clienteExistente, model))
                .thenReturn(clienteActualizado);

        when(clienteRepository.save(clienteActualizado))
                .thenReturn(clienteActualizado);

        when(clienteBuilder.builderResponse(clienteActualizado))
                .thenReturn(respuesta);

        ClienteDto resultado = clienteService.update(tokenId, model);

        assertSame(respuesta, resultado);

        verify(clienteBuilder).builderUpdate(clienteExistente, model);
        verify(clienteRepository).save(clienteActualizado);
    }

    @Test
    void findByTokenId() {
        UUID tokenId = UUID.randomUUID();

        Cliente cliente = mock(Cliente.class);
        ClienteDto respuesta = new ClienteDto();

        when(clienteRepository.findByTokenId(tokenId))
                .thenReturn(Optional.of(cliente));

        when(clienteBuilder.builderResponse(cliente))
                .thenReturn(respuesta);

        ClienteDto resultado = clienteService.findByTokenId(tokenId);

        assertSame(respuesta, resultado);

        verify(clienteRepository).findByTokenId(tokenId);
        verify(clienteBuilder).builderResponse(cliente);
    }

    @Test
    void delete() {
        UUID tokenId = UUID.randomUUID();

        Cliente cliente = mock(Cliente.class);

        when(clienteRepository.findByTokenId(tokenId))
                .thenReturn(Optional.of(cliente));

        assertDoesNotThrow(() -> clienteService.delete(tokenId));

        verify(clienteRepository).delete(cliente);
    }
}