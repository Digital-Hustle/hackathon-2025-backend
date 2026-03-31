package rnd.sueta.event_ms.service.business;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import rnd.sueta.event_ms.model.PhotoWithUrl;
import rnd.sueta.event_ms.model.entity.PhotoMeta;

import java.util.UUID;

public interface PhotoManager {

    Page<PhotoWithUrl> getAllByOwnerId(UUID ownerId, int page, int size);

    PhotoMeta createPhoto(UUID ownerId, MultipartFile photo);

    void deletePhoto(UUID id);
}
