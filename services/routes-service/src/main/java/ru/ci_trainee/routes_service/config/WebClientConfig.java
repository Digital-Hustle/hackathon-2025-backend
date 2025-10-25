package ru.ci_trainee.routes_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${yandex.gpt.api.key}")
    private String apiKey;

    @Bean
    public WebClient yandexGptWebClient() {
        return WebClient.builder()
                .baseUrl("https://rest-assistant.api.cloud.yandex.net/v1")
                .defaultHeader("Authorization", "Api-Key " + apiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}
