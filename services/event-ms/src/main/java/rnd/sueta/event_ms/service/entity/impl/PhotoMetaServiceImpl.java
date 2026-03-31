package rnd.sueta.event_ms.service.entity.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rnd.sueta.event_ms.model.entity.PhotoMeta;
import rnd.sueta.event_ms.repository.AbstractPhotoMetaRepository;
import rnd.sueta.event_ms.service.entity.PhotoMetaService;

import java.util.UUID;

@RequiredArgsConstructor
public class PhotoMetaServiceImpl implements PhotoMetaService {

    private final AbstractPhotoMetaRepository photoMetaInfoRepository;

    @Override
    public Page<PhotoMeta> getByOwnerId(UUID ownerId, Pageable pageable) {
        return photoMetaInfoRepository.findAllByOwnerId(ownerId, pageable);
    }

    @Override
    public PhotoMeta getById(UUID id) {
        return photoMetaInfoRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public PhotoMeta create(UUID ownerId, PhotoMeta photoMeta) {
        return photoMetaInfoRepository.save(
                ownerId,
                photoMeta.toBuilder()
                        .id(UUID.randomUUID())
                        .build()
        );
    }

    @Override
    public void delete(UUID id) {
        photoMetaInfoRepository.deleteById(id);
    }
}
