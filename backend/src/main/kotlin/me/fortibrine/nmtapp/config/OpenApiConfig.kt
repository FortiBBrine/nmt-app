package me.fortibrine.nmtapp.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class OpenApiConfig {

    @Bean
    fun openApi(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("NmtApp")
                    .version("1.0")
                    .description("Документація API")
            )
            .addSecurityItem(SecurityRequirement().addList("BearerAuth"))
            .components(Components()
                .addSecuritySchemes("BearerAuth", SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")));
    }

}