package rnd.sueta.event_ms.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.generated.Tables;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import rnd.sueta.event_ms.model.JoinTable;
import rnd.sueta.event_ms.model.entity.PhotoMeta;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public abstract class AbstractPhotoMetaRepository {

    private final DSLContext dsl;
    private final JoinTable joinTable;

    public Page<PhotoMeta> findAllByOwnerId(UUID ownerId, Pageable pageable) {
        List<PhotoMeta> photos = dsl.select(Tables.PHOTOS)
                .from(Tables.PHOTOS)
                .join(joinTable.table())
                .on(joinTable.linkedTableId().eq(Tables.PHOTOS.ID))
                .where(joinTable.ownerTableId().eq(ownerId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchInto(PhotoMeta.class);

        long total = countOwnerPhotos(ownerId);

        return new PageImpl<>(photos, pageable, total);
    }

    public Optional<PhotoMeta> findById(UUID id) {
        return dsl.select()
                .from(Tables.PHOTOS)
                .where(Tables.PHOTOS.ID.eq(id))
                .fetchOptionalInto(PhotoMeta.class);
    }

    public PhotoMeta save(UUID ownerId, PhotoMeta photoMeta) {
        PhotoMeta savedPhotoMeta = dsl.insertInto(Tables.PHOTOS)
                .set(dsl.newRecord(Tables.PHOTOS, photoMeta))
                .onConflict(Tables.PHOTOS.ID)
                .doUpdate()
                .set(dsl.newRecord(Tables.PHOTOS, photoMeta))
                .returning()
                .fetchOne(jooqRecord -> jooqRecord.into(PhotoMeta.class));

        dsl.insertInto(joinTable.table())
                .set(joinTable.linkedTableId(), photoMeta.getId())
                .set(joinTable.ownerTableId(), ownerId)
                .execute();

        return savedPhotoMeta;
    }

    public void deleteById(UUID id) {
        dsl.deleteFrom(Tables.PHOTOS)
                .where(Tables.PHOTOS.ID.eq(id))
                .execute();
    }

    private Long countOwnerPhotos(UUID ownerId) {
        Long total = dsl.selectCount()
                .from(Tables.PHOTOS)
                .join(joinTable.table())
                .on(joinTable.linkedTableId().eq(Tables.PHOTOS.ID))
                .where(joinTable.ownerTableId().eq(ownerId))
                .fetchOneInto(Long.class);

        return Objects.requireNonNullElse(total, 0L);
    }
}
