package rnd.sueta.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import rnd.sueta.constants.RecommendationConstants;
import rnd.sueta.gateway.RedisGateway;
import rnd.sueta.gateway.impl.RedisGatewayImpl;
import rnd.sueta.model.CacheProperties;
import rnd.sueta.model.EventWithPlace;
import rnd.sueta.model.PlaceWithCoordinates;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, PlaceWithCoordinates> placeRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return getDefaultTypedRedisTemplate(redisConnectionFactory, PlaceWithCoordinates.class);
    }

    @Bean
    public RedisTemplate<String, EventWithPlace> eventRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return getDefaultTypedRedisTemplate(redisConnectionFactory, EventWithPlace.class);
    }

    @Bean
    public RedisGateway<PlaceWithCoordinates> placeRedisGateway(
            StringRedisTemplate stringRedisTemplate, RedisTemplate<String, PlaceWithCoordinates> redisTemplate
    ) {
        CacheProperties cacheProperties = CacheProperties.builder()
                .setKey(RecommendationConstants.TOP_PLACES_ZSET_KEY)
                .keyPrefix(RecommendationConstants.TOP_PLACE_VALUE_KEY_PREFIX)
                .ttl(RecommendationConstants.TOP_PLACES_TTL_DAYS)
                .build();

        return new RedisGatewayImpl<>(stringRedisTemplate, redisTemplate, cacheProperties);
    }

    @Bean
    public RedisGateway<EventWithPlace> eventRedisGateway(
            StringRedisTemplate stringRedisTemplate, RedisTemplate<String, EventWithPlace> redisTemplate
    ) {
        CacheProperties cacheProperties = CacheProperties.builder()
                .setKey(RecommendationConstants.TOP_EVENTS_ZSET_KEY)
                .keyPrefix(RecommendationConstants.TOP_EVENT_VALUE_KEY_PREFIX)
                .ttl(RecommendationConstants.TOP_EVENTS_TTL_DAYS)
                .build();

        return new RedisGatewayImpl<>(stringRedisTemplate, redisTemplate, cacheProperties);
    }

    private <T> RedisTemplate<String, T> getDefaultTypedRedisTemplate(
            RedisConnectionFactory redisConnectionFactory, Class<T> type
    ) {
        RedisTemplate<String, T> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);

        Jackson2JsonRedisSerializer<T> jsonSerializer = new Jackson2JsonRedisSerializer<>(type);
        template.setValueSerializer(jsonSerializer);
        template.setHashValueSerializer(jsonSerializer);

        template.afterPropertiesSet();
        return template;
    }
}
