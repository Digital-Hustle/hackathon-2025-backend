package rnd.sueta.event_ms.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.generated.Tables;
import org.jooq.generated.tables.records.RouteEventsRecord;
import org.jooq.generated.tables.records.RoutePlacesRecord;
import org.springframework.stereotype.Repository;
import rnd.sueta.event_ms.model.RouteEvents;
import rnd.sueta.event_ms.model.RoutePlaces;
import rnd.sueta.event_ms.model.entity.Route;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class RouteRepository {
    private final DSLContext dsl;

    public Optional<Route> findById(UUID id) {
        return dsl.select()
                .from(Tables.ROUTES)
                .where(Tables.ROUTES.ID.eq(id))
                .fetchOptionalInto(Route.class);
    }

    public Route save(Route route) {
        return dsl.insertInto(Tables.ROUTES)
                .set(dsl.newRecord(Tables.ROUTES, route))
                .onConflict(Tables.ROUTES.ID)
                .doUpdate()
                .set(dsl.newRecord(Tables.ROUTES, route))
                .returning()
                .fetchOneInto(Route.class);
    }

    public List<RoutePlaces> saveRoutePlaces(List<RoutePlaces> routePlaces) {
        List<RoutePlacesRecord> records = routePlaces.stream()
                .map(routePlaceVal -> dsl.newRecord(Tables.ROUTE_PLACES, routePlaceVal))
                .toList();

        dsl.batchStore(records).execute();
        return routePlaces;
    }

    public List<RouteEvents> saveRouteEvents(List<RouteEvents> routeEvents) {
        List<RouteEventsRecord> records = routeEvents.stream()
                .map(routeEventVal -> dsl.newRecord(Tables.ROUTE_EVENTS, routeEventVal))
                .toList();

        dsl.batchStore(records).execute();
        return routeEvents;
    }
}
