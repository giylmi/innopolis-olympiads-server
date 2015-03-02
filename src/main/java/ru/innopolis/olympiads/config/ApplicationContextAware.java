package ru.innopolis.olympiads.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

/**
 * Created by giylmi on 28.02.2015.
 */
public class ApplicationContextAware implements org.springframework.context.ApplicationContextAware {

    static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static ApplicationContext getContext(){
        return context;
    }

    public static Environment getEnvironment(){
        return context.getEnvironment();
    }
}
