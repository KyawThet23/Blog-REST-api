package com.example.blogrestapi;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Spring boot blog app rest Api",
                description = "Documentation for REST Apis",
                version = "v1.0",
                contact = @Contact(
                        name = "Kyaw Thet Htun",
                        email = "kyawthe235@gmail.com"
                )

        ),
        externalDocs = @ExternalDocumentation(
                description = "Remote repository for version control",
                url = "https://github.com/KyawThet23/Blog-REST-api"
        )
)
public class BlogRestApiApplication {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(BlogRestApiApplication.class, args);
    }

}
