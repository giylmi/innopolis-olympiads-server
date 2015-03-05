package ru.innopolis.olympiads.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import ru.innopolis.olympiads.properties.PropertiesHolder;

/**
 * Created by giylmi on 28.02.2015.
 */
public class ApplicationContextAware implements org.springframework.context.ApplicationContextAware {

    static ApplicationContext context;
    static PropertiesHolder propertiesHolder;

    public static PropertiesHolder getPropertiesHolder() {
        return propertiesHolder;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
        propertiesHolder = applicationContext.getBean(PropertiesHolder.class);
    }

    public static ApplicationContext getContext(){
        return context;
    }

    public static Environment getEnvironment(){
        return context.getEnvironment();
    }
}
