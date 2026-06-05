package rnd.sueta.repository;

import org.jooq.DSLContext;
import org.jooq.generated.Tables;
import org.springframework.stereotype.Repository;
import rnd.sueta.model.JoinTable;

@Repository
public class EventPhotoMetaRepository extends AbstractPhotoMetaRepository {

    public EventPhotoMetaRepository(DSLContext dslContext) {
        super(dslContext, JoinTable.builder()
                .table(Tables.EVENT_PHOTOS)
                .linkedTableId(Tables.EVENT_PHOTOS.PHOTO_ID)
                .ownerTableId(Tables.EVENT_PHOTOS.OWNER_ID)
                .build());
    }
}
