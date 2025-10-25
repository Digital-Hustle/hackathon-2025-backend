package ru.ci_trainee.routes_service.service;

import reactor.core.publisher.Mono;

public interface YandexGptService {
    Mono<String> generateResponseAsync(String prompt, Double temperature, Integer maxTokens);
}
