package ru.ci_trainee.routes_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.ci_trainee.routes_service.dto.yandexGpt.YandexGptRequest;
import ru.ci_trainee.routes_service.dto.yandexGpt.YandexGptResponse;
import ru.ci_trainee.routes_service.service.YandexGptService;


@Service
@RequiredArgsConstructor
public class YandexGptServiceImpl implements YandexGptService {

    private static final String YANDEX_CLOUD_FOLDER = "b1g3hmh3evjneku7o5i0";
    private static final String YANDEX_CLOUD_MODEL = "yandexgpt-lite";

    private final WebClient yandexGptWebClient;

    @Override
    public Mono<String> generateResponseAsync(String prompt, Double temperature, Integer maxTokens) {
        String model = "gpt://" + YANDEX_CLOUD_FOLDER + "/" + YANDEX_CLOUD_MODEL;

        YandexGptRequest request = new YandexGptRequest(
                model,
                prompt,
                temperature,
                maxTokens
        );

        return yandexGptWebClient.post()
                .uri("/responses")
                .bodyValue(request)
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        clientResponse -> Mono.error(new RuntimeException("Yandex GPT API error: " + clientResponse.statusCode()))
                )
                .bodyToMono(YandexGptResponse.class)
                .map(response -> {
                    if (response.getOutput() != null &&
                            !response.getOutput().isEmpty() &&
                            response.getOutput().get(0).getContent() != null &&
                            !response.getOutput().get(0).getContent().isEmpty()) {

                        return response.getOutput().get(0).getContent().get(0).getText();
                    }
                    return "No response from AI";
                })
                .onErrorResume(throwable -> Mono.just("Error calling Yandex GPT: " + throwable.getMessage()));
    }
}
