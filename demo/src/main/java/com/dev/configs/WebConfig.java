package com.dev.configs;

import com.dev.helpers.Constants;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix(Constants.WEB_CONFIG.PATH_PREFIX,
                HandlerTypePredicate
                        .forAnnotation(RestController.class)
                        .and(HandlerTypePredicate
                                .forBasePackage(Constants.WEB_CONFIG.API_BASE_PACKAGE)));
    }
}
