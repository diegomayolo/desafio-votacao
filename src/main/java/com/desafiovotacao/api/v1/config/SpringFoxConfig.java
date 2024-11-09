package com.desafiovotacao.api.v1.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringFoxConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info().title("Desafio Votação <DB>")
                                            .description("API foi desenvolvido com o intuito de permitir a votação de associados em assembleias.")
                                            .version("1.0.0")
                                            .contact(new Contact().name("Diego Luis Mayolo").email("diego.mayolo@outlook.com")))
                            .externalDocs(new ExternalDocumentation().description( "Repositório da API")
                                                                     .url("https://github.com/diegomayolo/desafio-votacao"));
    }
}
