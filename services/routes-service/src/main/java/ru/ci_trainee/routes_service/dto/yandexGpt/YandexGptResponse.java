package ru.ci_trainee.routes_service.dto.yandexGpt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YandexGptResponse {
    @JsonProperty("output")
    private List<Output> output;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Output {
        @JsonProperty("content")
        private List<Content> content;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Content {
        @JsonProperty("text")
        private String text;
    }
}
