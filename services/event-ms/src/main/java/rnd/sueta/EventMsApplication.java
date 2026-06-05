package rnd.sueta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import rnd.sueta.config.properties.AppMinioProperties;
import rnd.sueta.config.properties.BorderProperties;

@EnableCaching
@EnableScheduling
@EnableConfigurationProperties({
        BorderProperties.class,
        AppMinioProperties.class
})
@SpringBootApplication
public class EventMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventMsApplication.class, args);
    }

}
