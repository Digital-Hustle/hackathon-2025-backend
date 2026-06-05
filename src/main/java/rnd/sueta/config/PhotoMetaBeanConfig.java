package rnd.sueta.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rnd.sueta.repository.AbstractPhotoMetaRepository;
import rnd.sueta.service.entity.PhotoMetaService;
import rnd.sueta.service.entity.impl.PhotoMetaServiceImpl;

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
