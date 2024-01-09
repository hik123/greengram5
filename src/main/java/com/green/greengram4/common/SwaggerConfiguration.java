package com.green.greengram4.common;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "Greengram ver.4"
                    , description = "인스타 클론 코딩 v4"
                    , version = "4.0.0"
        ),
        security = @SecurityRequirement(name = "asfdsf")
)

public class SwaggerConfiguration {
   /* @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Greengram Ver.3")
                        .description("인스타그램 클론 코딩 v3")
                        .version("2.0.0")
                );
    }

    */
}
