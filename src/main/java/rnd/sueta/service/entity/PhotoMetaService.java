package rnd.sueta.service.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rnd.sueta.model.entity.PhotoMeta;

import java.util.UUID;

public interface PhotoMetaService {

    Page<PhotoMeta> getByOwnerId(UUID ownerId, Pageable pageable);

    PhotoMeta getById(UUID id);

    PhotoMeta create(UUID ownerId, PhotoMeta photoMeta);

    void delete(UUID id);
}
