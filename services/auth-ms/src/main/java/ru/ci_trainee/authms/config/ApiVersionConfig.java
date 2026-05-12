package ru.ci_trainee.authms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.ApiVersionConfigurer;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.ci_trainee.authms.constants.ApiVersionConstants;
import ru.ci_trainee.authms.constants.UrlPaths;

@Configuration
public class ApiVersionConfig implements WebMvcConfigurer {

    @Override
    public void configureApiVersioning(ApiVersionConfigurer configurer) {
        configurer
                .usePathSegment(1)
                .setDefaultVersion(null)
                .addSupportedVersions(ApiVersionConstants.VERSION_1);
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix(
                UrlPaths.API_VERSION, HandlerTypePredicate.forAnnotation(RestController.class)
        );
    }
}
