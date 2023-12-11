package com.exaze.ritika.travel.portal.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Value("http://localhost:8090")
    private String devUrl;

    @Value("https://travel-portal-api.com")
    private String prodUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Server URL in Production environment");
        Info info = new Info()
                .title("Travel Portal API")
                .version("1.0")
                .description("This API exposes endpoints to all modules of Travel Portal API");

        return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
    }
}
