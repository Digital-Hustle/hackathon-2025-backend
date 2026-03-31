package rnd.sueta.event_ms.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rnd.sueta.event_ms.constants.PhotoConstants;
import rnd.sueta.event_ms.enums.PhotoOwnerType;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "place_photos", schema = "event")
public class PhotoMeta {

    @Id
    private UUID id;

    @Column(name = "original_file_name", nullable = false)
    private String originalFileName;

    @Enumerated(EnumType.STRING)
    @Column(name = "owner_type", nullable = false)
    private PhotoOwnerType ownerType;

    @Column(name = "extension", nullable = false, length = PhotoConstants.PHOTO_EXTENSION_LENGTH)
    private String extension;

    @Column(name = "file_size", nullable = false)
    private Integer fileSize;

    @Column(name = "content_type", length = PhotoConstants.CONTENT_TYPE_LENGTH)
    private String contentType;

    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;
}
