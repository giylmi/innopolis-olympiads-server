package ru.innopolis.olympiads.properties;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by giylmi on 05.03.2015.
 */
public class PropertiesHolder implements InitializingBean {

    private Resource[] resources;
    private Properties properties;

    public PropertiesHolder(){}

    @Override
    public void afterPropertiesSet() throws Exception {
        properties = new Properties();
        for (Resource resource: resources)
            if (resource.exists()){
                Properties newProperties = new Properties();
                InputStreamReader fis = new InputStreamReader(resource.getInputStream(), "UTF-8");
                newProperties.load(fis);
                properties.putAll(newProperties);
            }
    }

    public String getProperty(String propertyName){
        String property = properties.getProperty(propertyName);
        return (property == null || property.isEmpty()) ? propertyName : property;
    }

    public void setResources(Resource[] resources) {
        this.resources = resources;
    }
}
