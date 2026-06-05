package rnd.sueta.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import rnd.sueta.enums.PhotoOwnerType;

import java.util.UUID;

@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PhotoMetaDto(
        UUID id,

        String originalFileName,

        PhotoOwnerType ownerType,

        String extension,

        Integer fileSize,

        String contentType,

        String url
) {
}
