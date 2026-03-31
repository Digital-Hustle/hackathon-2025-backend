package rnd.sueta.event_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import rnd.sueta.event_ms.config.properties.AppMinioProperties;
import rnd.sueta.event_ms.config.properties.BorderProperties;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties({
        BorderProperties.class,
        AppMinioProperties.class
})
public class EventMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventMsApplication.class, args);
    }

}
