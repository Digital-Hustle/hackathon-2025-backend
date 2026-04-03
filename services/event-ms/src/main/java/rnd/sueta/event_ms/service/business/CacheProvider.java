package rnd.sueta.event_ms.service.business;

import org.springframework.data.domain.Page;

import java.util.UUID;

public interface CacheProvider<T> {

    Page<T> getTop(int page, int size);

    T getById(UUID id);

    T update(T cacheObject);

    void rebuildTop();

    void delete(UUID id);
}
