package rosa.isa.starwarsapi.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@PropertySource("classpath:documentation.properties")
@Configuration
public class SwaggerConfiguration {
    private final Environment env;

    @Autowired
    public SwaggerConfiguration(Environment env) {
        this.env = env;
    }

    @Bean
    public OpenAPI springOpenAPI() {
        return new OpenAPI()
                .info(buildSwaggerInfo());
    }

    private Info buildSwaggerInfo() {
        return new Info()
                .title(env.getProperty("documentation.info.title"))
                .description(env.getProperty("documentation.info.description"))
                .version(env.getProperty("documentation.info.version"))
                .contact(buildSwaggerContactInfo());
    }

    private Contact buildSwaggerContactInfo() {
        return new Contact()
                .name(env.getProperty("documentation.contact.name"))
                .url(env.getProperty("documentation.contact.url"));
    }
}