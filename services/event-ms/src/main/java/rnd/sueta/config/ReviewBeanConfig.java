package rnd.sueta.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rnd.sueta.repository.AbstractReviewRepository;
import rnd.sueta.service.entity.ReviewService;
import rnd.sueta.service.entity.impl.ReviewServiceImpl;

@Configuration
public class ReviewBeanConfig {

    @Bean
    public ReviewService placeReviewService(AbstractReviewRepository placeReviewRepository) {
        return new ReviewServiceImpl(placeReviewRepository);
    }

    @Bean
    public ReviewService eventReviewService(AbstractReviewRepository eventReviewRepository) {
        return new ReviewServiceImpl(eventReviewRepository);
    }
}
