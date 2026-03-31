package rnd.sueta.event_ms.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ThreadLocalMap {

    private static final ThreadLocal<Map<String, String>> CONTEXT_HOLDER = ThreadLocal.withInitial(HashMap::new);

    public static String get(String key) {
        return CONTEXT_HOLDER.get().get(key);
    }

    public static void put(String key, String value) {
        CONTEXT_HOLDER.get().put(key, value);
    }

    public static void remove(String key) {
        CONTEXT_HOLDER.get().remove(key);
    }

    public static void clearMap() {
        CONTEXT_HOLDER.get().clear();
    }

    public static void clear() {
        CONTEXT_HOLDER.remove();
    }
}
