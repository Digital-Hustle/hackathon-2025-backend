package rnd.sueta.repository;

import org.jooq.DSLContext;
import org.jooq.generated.Tables;
import org.springframework.stereotype.Repository;
import rnd.sueta.model.JoinTable;

@Repository
public class PlaceReviewRepository extends AbstractReviewRepository {

    public PlaceReviewRepository(DSLContext dslContext) {
        super(dslContext, JoinTable.builder()
                .table(Tables.PLACE_REVIEWS)
                .linkedTableId(Tables.PLACE_REVIEWS.REVIEW_ID)
                .ownerTableId(Tables.PLACE_REVIEWS.OWNER_ID)
                .build());
    }
}
