package br.ufc.quixada.SupplierApplicationInsight.config.documentation;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocumentationConfigurations {
    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().addSecurityItem(new SecurityRequirement().
                        addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes
                        ("Bearer Authentication", createAPIKeyScheme()))
                .info(new Info().title("API de Fornecedores do Desafio Full-Stack Insight")
                        .description("Essa API tem o objetivo de persistir e manipular dados de um sistema de gerência de processos com foco em manter dados dos fornecedores e seus registros de fornecimentos.")
                        .version("1.0").contact(new Contact().name("Juan Pimentel")
                                .email("juandbpimentel@gmail.com")));
    }
}
