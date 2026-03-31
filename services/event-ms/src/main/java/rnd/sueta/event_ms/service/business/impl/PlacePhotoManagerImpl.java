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
import rnd.sueta.event_ms.service.entity.PhotoMetaService;
import rnd.sueta.event_ms.service.entity.PhotoService;
import rnd.sueta.event_ms.service.entity.PlaceService;
import rnd.sueta.event_ms.util.PhotoFactory;
import rnd.sueta.event_ms.validator.PhotoValidator;

import java.util.UUID;

@Slf4j
@Service("placePhotoManager")
@RequiredArgsConstructor
public class PlacePhotoManagerImpl implements PhotoManager {

    private final PhotoService photoService;
    private final PlaceService placeService;
    private final PhotoMetaService placePhotoMetaService;
    private final PhotoHelper photoHelper;

    @Override
    public Page<PhotoWithUrl> getAllByOwnerId(UUID ownerId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<PhotoMeta> photosPage = placePhotoMetaService.getByOwnerId(ownerId, pageable);
        return photosPage.map(photoMetaInfo -> {
            String url = photoHelper.getPhotoUrl(photoMetaInfo);

            return PhotoFactory.newDefaultPhotoWithUrl(photoMetaInfo, url);
        });
    }

    @Override
    public PhotoMeta createPhoto(UUID ownerId, MultipartFile photo) {
        PhotoValidator.checkPhotoExtension(photo);

        boolean ownerExists = placeService.exists(ownerId);
        PhotoValidator.checkOwnerExistence(ownerExists);

        PhotoMeta photoMeta = PhotoFactory.newDefaultPhotoMetaInfo(photo).toBuilder()
                .ownerType(PhotoOwnerType.PLACES)
                .build();

        PhotoMeta savedPhotoMeta = placePhotoMetaService.create(ownerId, photoMeta);

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
            PhotoMeta photoMeta = placePhotoMetaService.getById(id);
            String absolutePhotoPath = photoHelper.getAbsolutePhotoPath(photoMeta);

            placePhotoMetaService.delete(id);
            photoService.delete(absolutePhotoPath);
        } catch (EntityNotFoundException exception) {
            log.debug("Photo with id {} not found, deletion skipped", id);
        }
    }
}
