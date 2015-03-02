package ru.innopolis.olympiads.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySources;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.innopolis.olympiads.dao.adapter.DataSourceAdapter;

import javax.sql.DataSource;

/**
 * Created by giylmi on 25.02.2015.
 */
@Configuration
@EnableTransactionManagement
@PropertySource("classpath:database-local.properties")
@ComponentScan(basePackages = {"ru.innopolis.olympiads.dao"})
public class DaoConfig {

    @Autowired
    Environment env;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc.driver"));
        dataSource.setUrl(env.getProperty("jdbc.url"));
        dataSource.setUsername(env.getProperty("jdbc.user"));
        dataSource.setPassword(env.getProperty("jdbc.password"));
        return dataSource;
    }

    @Bean
    @Autowired
    public DataSourceAdapter dataSourceAdapter(DataSource dataSource){
        DataSourceAdapter dataSourceAdapter = new DataSourceAdapter();
        dataSourceAdapter.setDataSource(dataSource);
        return dataSourceAdapter;
    }

    @Bean
    @Autowired
    public DataSourceTransactionManager transactionManager(DataSource dataSource){
        DataSourceTransactionManager manager = new DataSourceTransactionManager(dataSource);
        return manager;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        propertySourcesPlaceholderConfigurer.setFileEncoding("UTF-8");
        return propertySourcesPlaceholderConfigurer;
    }

    @Bean
    public ApplicationContextAware applicationContextAware(){
        return new ApplicationContextAware();
    }
}
