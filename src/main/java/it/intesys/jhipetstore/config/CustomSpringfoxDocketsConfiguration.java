package it.intesys.jhipetstore.config;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.config.apidoc.PageableParameterBuilderPlugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.zalando.problem.Problem;
import org.zalando.problem.StatusType;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.TypeNameExtractor;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
public class CustomSpringfoxDocketsConfiguration {

    private final JHipsterProperties.Swagger properties;
    private final TypeResolver typeResolver;

    public CustomSpringfoxDocketsConfiguration(JHipsterProperties properties, TypeResolver typeResolver) {
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
            .securitySchemes(Lists.newArrayList(jwtHeaderKey()))
            .securityContexts(securityContexts("/api/account", "/api/account/change_password"))
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
            .groupName("openapi")
            .forCodeGeneration(true)
            .directModelSubstitute(java.nio.ByteBuffer.class, String.class)
            .directModelSubstitute(URI.class, String.class)
            .directModelSubstitute(StatusType.class, Integer.class)
            .genericModelSubstitutes(ResponseEntity.class)
            .securitySchemes(Lists.newArrayList(jwtHeaderKey()))
            .additionalModels(typeResolver.resolve(Problem.class))
            .select()
            .paths(regex("/api/public.*"))
            .build();
        return docket;
    }

    @Bean
    SecurityScheme jwtHeaderKey() {
        return new ApiKey("Bearer", "Authorization", "header");
    }


    private List<SecurityContext> securityContexts(String... paths){
        ArrayList<String> pathList = Lists.newArrayList(paths);

        return pathList.stream()
            .map(pathItem ->
                SecurityContext.builder()
                    .securityReferences(defaultAuth())
                    .forPaths(PathSelectors.regex(pathItem))
                    .build()).collect(Collectors.toList());
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[0];
        return Lists.newArrayList(new SecurityReference("Bearer", authorizationScopes));
    }

    @Bean
    PageableParameterBuilderPlugin pageableParameterBuilderPlugin(TypeNameExtractor nameExtractor,
                                                                  TypeResolver resolver) {

        return new PageableParameterBuilderPlugin(nameExtractor, resolver);
    }


}
