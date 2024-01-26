package com.demo.book.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SwaggerConfig {

    @Bean
    public OpenAPI springOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .info(new Info().title("SpringBoot API")
                        .description("SpringBoot sample application")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("SpringBoot Wiki Documentation")
                        .url("https://springboot.wiki.github.org/docs"));
//		.addSecurityItem(new SecurityRequirement()
//		        .addList(securitySchemeName))
//		      .components(new Components()
//		        .addSecuritySchemes(securitySchemeName, SecurityScheme)
//		        //  .name(securitySchemeName)
//		          //.type()
//		          .scheme("bearer")
//		          .bearerFormat("JWT")));
    }

//    @Value("${bezkoder.openapi.dev-url}")
//    private String devUrl;
//
//    @Value("${bezkoder.openapi.prod-url}")
//    private String prodUrl;
//
//    @Bean
//    public OpenAPI myOpenAPI() {
//        Server devServer = new Server();
//        devServer.url(devUrl);
//        devServer.setDescription("Server URL in Development environment");
//
//        Server prodServer = new Server();
//        prodServer.url(prodUrl);
//        prodServer.setDescription("Server URL in Production environment");
//
//        Contact contact = new Contact();
//        contact.setEmail("contact@pasanabeysekara.com");
//        contact.setName("Pasan Abeysekara");
//        contact.url("https://www.pasanabeysekara.com");
//
//        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");
//
//        Info info = new Info()
//                .title("Demo Service API")
//                .version("1.0")
//                .contact(contact)
//                .description("This API exposes endpoints to manage demo.").termsOfService("https://www.pasanabeysekara.com")
//                .license(mitLicense);
//
//        return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
//    }


}
