package ru.core.profilems.rabbit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class MessageDto {
    private String id;
    private String content;
    private LocalDateTime timestamp;
    private String type;

    // Конструкторы, геттеры, сеттеры
    public MessageDto() {
        this.id = UUID.randomUUID().toString();
        this.timestamp = LocalDateTime.now();
    }

    public MessageDto(String content, String type) {
        this();
        this.content = content;
        this.type = type;
    }
}
