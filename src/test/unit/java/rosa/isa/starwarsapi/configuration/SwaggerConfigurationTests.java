package unit.java.rosa.isa.starwarsapi.configuration;

import io.swagger.v3.oas.models.info.Info;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import rosa.isa.starwarsapi.configuration.SwaggerConfiguration;
import unit.java.rosa.isa.starwarsapi.Fixtures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@PropertySource("classpath:documentation.properties")
public class SwaggerConfigurationTests {

    @Mock
    private Environment env;

    @InjectMocks
    private SwaggerConfiguration swaggerConfiguration;

    @Test
    public void swagger_configuration_returnOpenApiObject() {
        var openApiMock = Fixtures.getOpenAPISwaggerConfig();

        var openApiInfo = new Info();
        BeanUtils.copyProperties(openApiMock.getInfo(), openApiInfo);

        var openAPIContact = openApiInfo.getContact();

        when(env.getProperty("documentation.info.title")).thenReturn(openApiInfo.getTitle());
        when(env.getProperty("documentation.info.description")).thenReturn(openApiInfo.getDescription());
        when(env.getProperty("documentation.info.version")).thenReturn(openApiInfo.getVersion());
        when(env.getProperty("documentation.contact.name")).thenReturn(openAPIContact.getName());
        when(env.getProperty("documentation.contact.url")).thenReturn(openAPIContact.getUrl());

        var configuration = swaggerConfiguration.springOpenAPI();

        assertEquals(configuration, openApiMock);
    }
}