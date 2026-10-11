package com.pt.clientes.services.operators;

import com.pt.clientes.config.RabbitMQConfig;
import com.pt.clientes.rest.events.ClienteRegistradoEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClienteRegistroProducer {

    private final RabbitTemplate rabbitTemplate;

    public void publicar(ClienteRegistradoEvent evento) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.CLIENTE_REGISTRADO,
                evento
        );
    }

}
