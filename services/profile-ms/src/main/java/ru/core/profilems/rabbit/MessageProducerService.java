package ru.core.profilems.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class MessageProducerService {

    private final RabbitTemplate rabbitTemplate;

    public MessageProducerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(Object message) {
        try {
            rabbitTemplate.convertAndSend(
                    RabbitMqConfig.EXCHANGE_NAME,
                    RabbitMqConfig.ROUTING_KEY,
                    message
            );
            log.info("Message sent successfully: {}", message);
        } catch (Exception e) {
            log.error("Failed to send message: {}", message, e);
            throw new RuntimeException("Failed to send message", e);
        }
    }

    // Альтернативный метод с подтверждением
    public void sendMessageWithConfirmation(Object message) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

        rabbitTemplate.convertAndSend(
                RabbitMqConfig.EXCHANGE_NAME,
                RabbitMqConfig.ROUTING_KEY,
                message,
                correlationData
        );

        // Можно добавить логику ожидания подтверждения
        log.info("Message sent with correlation ID: {}", correlationData.getId());
    }
}
