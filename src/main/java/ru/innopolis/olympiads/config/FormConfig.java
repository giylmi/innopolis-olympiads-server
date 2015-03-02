package ru.innopolis.olympiads.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by giylmi on 28.02.2015.
 */
@Configuration
@ImportResource("classpath:/document.xml")
@PropertySource("classpath:/validation.properties")
public class FormConfig {
}
