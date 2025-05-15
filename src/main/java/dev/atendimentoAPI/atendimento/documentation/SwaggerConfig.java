package dev.atendimentoAPI.atendimento.documentation;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                    .title("API de Atendimentos")
                    .description("Documentação da API")
                    .version("v1.0")
                    .description("Documentação da API para gerenciamento de atendimentos."))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new io.swagger.v3.oas.models.Components()
                    .addSecuritySchemes("bearerAuth",
                        new SecurityScheme()
                            .name("Authorization")
                            .type(Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")));
    }
}
