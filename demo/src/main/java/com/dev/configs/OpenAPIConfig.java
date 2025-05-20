package com.dev.configs;

import com.dev.helpers.Constants;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList(Constants.OPEN_API.SECURITY_SCHEME_NAME))
                .components(new Components()
                        .addSecuritySchemes(Constants.OPEN_API.SECURITY_SCHEME_NAME,
                                new SecurityScheme()
                                        .name(Constants.OPEN_API.SECURITY_SCHEME_NAME)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme(Constants.OPEN_API.SCHEME)
                                        .bearerFormat(Constants.OPEN_API.BEARER_FORMAT)
                                        .in(SecurityScheme.In.HEADER)));
    }
}
