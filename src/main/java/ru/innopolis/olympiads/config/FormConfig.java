package ru.innopolis.olympiads.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import ru.innopolis.olympiads.properties.PropertiesHolder;

/**
 * Created by giylmi on 28.02.2015.
 */
@Configuration
@ImportResource("classpath:/document.xml")
public class FormConfig {

    @Bean
    public PropertiesHolder propertiesHolder(){
        PropertiesHolder propertiesHolder = new PropertiesHolder();
        Resource[] resources = new ClassPathResource[] {
                new ClassPathResource("validation.properties")
        };
        propertiesHolder.setResources(resources);
        return propertiesHolder;
    }
}
