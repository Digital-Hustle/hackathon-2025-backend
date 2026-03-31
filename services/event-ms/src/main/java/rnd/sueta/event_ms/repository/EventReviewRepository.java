package rnd.sueta.event_ms.repository;

import org.jooq.DSLContext;
import org.jooq.generated.Tables;
import org.springframework.stereotype.Repository;
import rnd.sueta.event_ms.model.JoinTable;

@Repository
public class EventReviewRepository extends AbstractReviewRepository {

    public EventReviewRepository(DSLContext dslContext) {
        super(dslContext, JoinTable.builder()
                .table(Tables.EVENT_REVIEWS)
                .linkedTableId(Tables.EVENT_REVIEWS.REVIEW_ID)
                .ownerTableId(Tables.EVENT_REVIEWS.OWNER_ID)
                .build());
    }
}
