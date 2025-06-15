package com.arka.micro_stock.configuration.swagger;

import com.arka.micro_stock.configuration.util.ConstantsConfiguration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(ConstantsConfiguration.SWAGGER_TITLE)
                        .version(ConstantsConfiguration.SWAGGER_VERSION)
                        .description(ConstantsConfiguration.SWAGGER_DESCRIPTION))
                .addSecurityItem(new SecurityRequirement().addList(ConstantsConfiguration.SWAGGER_SECURITY_NAME))
                .components(new Components()
                        .addSecuritySchemes(ConstantsConfiguration.SWAGGER_SECURITY_NAME,
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme(ConstantsConfiguration.SWAGGER_SECURITY_SCHEME)
                                        .bearerFormat(ConstantsConfiguration.SWAGGER_BEARER_FORMAT)));
    }
}
