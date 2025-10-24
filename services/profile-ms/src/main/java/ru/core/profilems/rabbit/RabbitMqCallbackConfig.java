package ru.core.profilems.rabbit;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RabbitMqCallbackConfig {

    @PostConstruct
    public void init() {
        log.info("RabbitMQ producer configured with confirmations and returns");
    }

    @Bean
    public RabbitTemplate.ConfirmCallback confirmCallback() {
        return (correlationData, ack, cause) -> {
            if (ack) {
                log.info("Message confirmed with correlation ID: {}", correlationData.getId());
            } else {
                log.error("Message rejected with correlation ID: {}, cause: {}",
                        correlationData.getId(), cause);
            }
        };
    }

    @Bean
    public RabbitTemplate.ReturnsCallback returnsCallback() {
        return returned -> {
            log.error("Message returned: {}", returned.toString());
        };
    }
}
