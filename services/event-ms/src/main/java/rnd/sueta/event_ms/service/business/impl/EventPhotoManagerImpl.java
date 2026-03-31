package rnd.sueta.event_ms.service.business.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rnd.sueta.event_ms.enums.PhotoOwnerType;
import rnd.sueta.event_ms.helper.PhotoHelper;
import rnd.sueta.event_ms.model.PhotoWithUrl;
import rnd.sueta.event_ms.model.entity.PhotoMeta;
import rnd.sueta.event_ms.service.business.PhotoManager;
import rnd.sueta.event_ms.service.entity.EventService;
import rnd.sueta.event_ms.service.entity.PhotoMetaService;
import rnd.sueta.event_ms.service.entity.PhotoService;
import rnd.sueta.event_ms.util.PhotoFactory;
import rnd.sueta.event_ms.validator.PhotoValidator;

import java.util.UUID;

@Slf4j
@Service("eventPhotoManager")
@RequiredArgsConstructor
public class EventPhotoManagerImpl implements PhotoManager {

    private final PhotoService photoService;
    private final EventService eventService;
    private final PhotoMetaService eventPhotoMetaService;
    private final PhotoHelper photoHelper;

    @Override
    public Page<PhotoWithUrl> getAllByOwnerId(UUID ownerId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<PhotoMeta> photosPage = eventPhotoMetaService.getByOwnerId(ownerId, pageable);
        return photosPage.map(photoMetaInfo -> {
            String url = photoHelper.getPhotoUrl(photoMetaInfo);

            return PhotoFactory.newDefaultPhotoWithUrl(photoMetaInfo, url);
        });
    }

    @Override
    public PhotoMeta createPhoto(UUID ownerId, MultipartFile photo) {
        PhotoValidator.checkPhotoExtension(photo);

        boolean ownerExists = eventService.exists(ownerId);
        PhotoValidator.checkOwnerExistence(ownerExists);

        PhotoMeta photoMeta = PhotoFactory.newDefaultPhotoMetaInfo(photo).toBuilder()
                .ownerType(PhotoOwnerType.EVENTS)
                .build();

        PhotoMeta savedPhotoMeta = eventPhotoMetaService.create(ownerId, photoMeta);

        String absolutePhotoPath = photoHelper.getAbsolutePhotoPath(
                photoMeta.toBuilder()
                        .id(savedPhotoMeta.getId())
                        .build()
        );
        photoService.save(absolutePhotoPath, photo);

        return savedPhotoMeta;
    }

    @Override
    public void deletePhoto(UUID id) {
        try {
            PhotoMeta photoMeta = eventPhotoMetaService.getById(id);
            String absolutePhotoPath = photoHelper.getAbsolutePhotoPath(photoMeta);

            eventPhotoMetaService.delete(id);
            photoService.delete(absolutePhotoPath);
        } catch (EntityNotFoundException exception) {
            log.debug("Photo with id {} not found, deletion skipped", id);
        }
    }
}
