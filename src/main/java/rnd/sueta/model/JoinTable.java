package rnd.sueta.model;

import lombok.Builder;
import org.jooq.Field;
import org.jooq.impl.TableImpl;

import java.util.UUID;

@Builder(toBuilder = true)
public record JoinTable(

        TableImpl<?> table,

        Field<UUID> linkedTableId,

        Field<UUID> ownerTableId
) {
}
