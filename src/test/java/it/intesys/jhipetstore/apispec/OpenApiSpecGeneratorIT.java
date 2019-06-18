package it.intesys.jhipetstore.apispec;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.github.jhipster.config.JHipsterConstants;
import io.swagger.models.Swagger;
import it.intesys.jhipetstore.JhipetstoreApp;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.spring.web.json.Json;
import springfox.documentation.spring.web.json.JsonSerializer;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;

import java.io.FileOutputStream;
import java.io.IOException;

@SpringBootTest(classes = JhipetstoreApp.class)
@ActiveProfiles(value = {JHipsterConstants.SPRING_PROFILE_SWAGGER})
public class OpenApiSpecGeneratorIT {

    @Autowired
    DocumentationCache documentationCache;

    @Autowired
    ServiceModelToSwagger2Mapper mapper;

    @Autowired
    JsonSerializer jsonSerializer;

    @Test
    void createOpenApiJsonSpec() {
        documentationCache.all().forEach((group, documentation) ->{
            Swagger swagger = mapper.mapDocumentation(documentation);
            Json spec = jsonSerializer.toJson(swagger);
            try {
                IOUtils.write(spec.value().getBytes(),
                    new FileOutputStream("openapi-specs/openapi-spec-" + group + ".json"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
