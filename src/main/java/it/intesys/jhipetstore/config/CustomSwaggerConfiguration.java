package it.intesys.jhipetstore.config;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Predicates;
import io.github.jhipster.config.JHipsterProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.zalando.problem.Problem;
import org.zalando.problem.StatusType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.net.URI;
import java.util.ArrayList;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
public class CustomSwaggerConfiguration {

    private final JHipsterProperties.Swagger properties;
    private final TypeResolver typeResolver;

    public CustomSwaggerConfiguration(JHipsterProperties properties, TypeResolver typeResolver) {
        this.properties = properties.getSwagger();
        this.typeResolver = typeResolver;
    }

    @Bean
    public Docket authDocket() {
        ApiInfo apiInfo = new ApiInfo(
            properties.getTitle() + " Authentication",
            properties.getDescription(),
            properties.getVersion(),
            properties.getTermsOfServiceUrl(),
            null,
            properties.getLicense(),
            properties.getLicenseUrl(),
            new ArrayList<>()
        );


        Docket docket = new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo)
            .groupName("auth")
            .forCodeGeneration(true)
            .directModelSubstitute(java.nio.ByteBuffer.class, String.class)
            .directModelSubstitute(URI.class, String.class)
            .directModelSubstitute(StatusType.class, Integer.class)
            .genericModelSubstitutes(ResponseEntity.class)
            .additionalModels(typeResolver.resolve(Problem.class))
            .select()
            .paths(Predicates.or(
                regex("/api/account.*"), regex("/api/authenticate.*"),
                regex("/api/activate.*"), regex("/api/register.*"))
            )
            .build();
        return docket;
    }

    @Bean
    public Docket apiFirstDocket() {
        ApiInfo apiInfo = new ApiInfo(
            properties.getTitle() + " Openapi",
            properties.getDescription(),
            properties.getVersion(),
            properties.getTermsOfServiceUrl(),
            null,
            properties.getLicense(),
            properties.getLicenseUrl(),
            new ArrayList<>()
        );


        Docket docket = new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo)
            .groupName("api-first")
            .forCodeGeneration(true)
            .directModelSubstitute(java.nio.ByteBuffer.class, String.class)
            .directModelSubstitute(URI.class, String.class)
            .directModelSubstitute(StatusType.class, Integer.class)
            .genericModelSubstitutes(ResponseEntity.class)
            .additionalModels(typeResolver.resolve(Problem.class))
            .select()
            .paths(regex("/api-first.*"))
            .build();
        return docket;
    }

}
