package rnd.sueta.event_ms.repository;

import org.jooq.DSLContext;
import org.jooq.generated.Tables;
import org.springframework.stereotype.Repository;
import rnd.sueta.event_ms.model.JoinTable;

@Repository
public class PlacePhotoMetaRepository extends AbstractPhotoMetaRepository {

    public PlacePhotoMetaRepository(DSLContext dslContext) {
        super(dslContext, JoinTable.builder()
                .table(Tables.PLACE_PHOTOS)
                .linkedTableId(Tables.PLACE_PHOTOS.PHOTO_ID)
                .ownerTableId(Tables.PLACE_PHOTOS.OWNER_ID)
                .build());
    }
}
