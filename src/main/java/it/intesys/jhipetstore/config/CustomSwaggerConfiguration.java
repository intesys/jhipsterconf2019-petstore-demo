package it.intesys.jhipetstore.config;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Predicates;
import io.github.jhipster.config.JHipsterConstants;
import io.github.jhipster.config.JHipsterProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.zalando.problem.Problem;
import org.zalando.problem.StatusType;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@Profile(JHipsterConstants.SPRING_PROFILE_SWAGGER)
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
            .securitySchemes(Collections.singletonList(jwtHeaderKey()))
            .securityContexts(securityContexts(bearerAuth(),"/api/account", "/api/account/change-password"))
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
            .securitySchemes(Collections.singletonList(jwtHeaderKey()))
            .select()
            .paths(regex("/api-first.*"))
            .build();
        return docket;
    }

    /**
     * Bearer security scheme of the entire json spec file
     * @return a Security Scheme
     */
    private static SecurityScheme jwtHeaderKey() {
        return new ApiKey("jwt", "Authorization", "header");
    }

    /**
     * Enables the given list of {@link SecurityReference} for all the paths in the paths list
     * @param securityReferences
     * @param paths
     * @return
     */
    private static List<SecurityContext> securityContexts(List<SecurityReference> securityReferences, String... paths){
        return Arrays.stream(paths)
            .map(pathItem ->
                SecurityContext.builder()
                    .securityReferences(securityReferences)
                    .forPaths(PathSelectors.regex(pathItem))
                    .build())
            .collect(Collectors.toList());
    }

    /**
     * security:
     *  - Bearer: []
     *
     * @return a singleton list with one Bearer security reference
     */
    private static List<SecurityReference> bearerAuth() {
        return Collections.singletonList(new SecurityReference("jwt", new AuthorizationScope[0]));
    }


}
