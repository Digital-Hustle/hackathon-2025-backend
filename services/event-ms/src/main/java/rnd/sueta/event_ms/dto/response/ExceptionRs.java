package rnd.sueta.event_ms.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ExceptionRs(
        int status,

        String error,

        String path,

        String message,

        LocalDateTime timestamp,

        Map<String, String> errors
) {
}
