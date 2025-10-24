package ru.core.profilems.rabbit;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestRest {
    private final MessageProducerService messageProducerService;

    @PostMapping
    public ResponseEntity<String> sendMessage(@RequestBody MessageRequest request) {
        var message = new MessageDto(request.getContent(), request.getType());
        messageProducerService.sendMessage(message);
        return ResponseEntity.ok("Message sent successfully");
    }

    @PostMapping("/batch")
    public ResponseEntity<String> sendMessages(@RequestBody List<MessageRequest> requests) {
        requests.forEach(request -> {
            var message = new MessageDto(request.getContent(), request.getType());
            messageProducerService.sendMessage(message);
        });
        return ResponseEntity.ok("Messages sent successfully");
    }
}

@Data
class MessageRequest {
    private String content;
    private String type;
}