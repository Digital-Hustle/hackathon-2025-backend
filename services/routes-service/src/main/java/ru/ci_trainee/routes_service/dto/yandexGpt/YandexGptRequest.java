package ru.ci_trainee.routes_service.dto.yandexGpt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class YandexGptRequest {
    @JsonProperty("model")
    private String model;

    @JsonProperty("input")
    private String input;

    @JsonProperty("temperature")
    private Double temperature;

    @JsonProperty("max_output_tokens")
    private Integer maxOutputTokens;
}
