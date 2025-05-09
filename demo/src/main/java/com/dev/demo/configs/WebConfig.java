package com.dev.demo.configs;

import com.dev.demo.helpers.Constants;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final String PATH_PREFIX =
            String.format("%s/%s", Constants.OTHERS.API_PREFIX, Constants.OTHERS.API_VERSION);
    private static final String BASE_PACKAGE = "com.dev.demo.controllers";

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix(
                PATH_PREFIX, HandlerTypePredicate
                        .forAnnotation(RestController.class)
                        .and(HandlerTypePredicate.forBasePackage(BASE_PACKAGE)));
    }
}
