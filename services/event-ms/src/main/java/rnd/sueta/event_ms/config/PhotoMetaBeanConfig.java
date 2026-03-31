package rnd.sueta.event_ms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rnd.sueta.event_ms.repository.AbstractPhotoMetaRepository;
import rnd.sueta.event_ms.service.entity.PhotoMetaService;
import rnd.sueta.event_ms.service.entity.impl.PhotoMetaServiceImpl;

@Configuration
public class PhotoMetaBeanConfig {

    @Bean
    public PhotoMetaService placePhotoMetaService(
            AbstractPhotoMetaRepository placePhotoMetaRepository
    ) {
        return new PhotoMetaServiceImpl(placePhotoMetaRepository);
    }

    @Bean
    public PhotoMetaService eventPhotoMetaService(
            AbstractPhotoMetaRepository eventPhotoMetaRepository
    ) {
        return new PhotoMetaServiceImpl(eventPhotoMetaRepository);
    }
}
