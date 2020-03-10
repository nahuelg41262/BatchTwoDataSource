package com.maite.db.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@PropertySource({ "classpath:application.properties" })
@EnableJpaRepositories(
    basePackages = "com.maite.jurex.model.repositories",
    entityManagerFactoryRef = "jurexEntityManager", 
    transactionManagerRef = "jurexTransactionManager"
)
public class JurexManagerConfiguration {
    @Autowired
    private Environment env;
 
    @Bean
    public LocalContainerEntityManagerFactoryBean jurexEntityManager() {
        LocalContainerEntityManagerFactoryBean em
          = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(jurexDataSource());
        em.setPackagesToScan(
          new String[] { "com.maite.jurex.model.entities" });
 
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("spring.jpa.jurex.hibernate.ddl-auto",
          env.getProperty("spring.jpa.jurex.hibernate.ddl-auto"));
        properties.put("spring.jpa.jurex.hibernate.dialect",
          env.getProperty("spring.jpa.jurex.hibernate.dialect"));
        em.setJpaPropertyMap(properties);
 
        return em;
    }
 
    @Primary
    @Bean
    public DataSource jurexDataSource() {
  
        DriverManagerDataSource dataSource
          = new DriverManagerDataSource();
        dataSource.setDriverClassName(
          env.getProperty("spring.jurex.driverClassName"));
        dataSource.setUrl(env.getProperty("spring.jurex.url"));
        dataSource.setUsername(env.getProperty("spring.jurex.username"));
        dataSource.setPassword(env.getProperty("spring.jurex.password"));
 
        return dataSource;
    }
 
    @Bean
    public PlatformTransactionManager jurexTransactionManager() {
  
        JpaTransactionManager transactionManager
          = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
          jurexEntityManager().getObject());
        return transactionManager;
    }
}