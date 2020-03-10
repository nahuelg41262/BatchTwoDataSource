package com.maite.db.configuration;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@PropertySource({ "classpath:application.properties" })
@EnableJpaRepositories(
    basePackages = "com.maite.model.repositories",
    entityManagerFactoryRef = "maiteEntityManager", 
    transactionManagerRef = "maiteTransactionManager"
)
public class MaiteManagerConfiguration {
    @Autowired
    private Environment env;
 
    @Bean
    public LocalContainerEntityManagerFactoryBean maiteEntityManager() {
        LocalContainerEntityManagerFactoryBean em
          = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(maiteDataSource());
        em.setPackagesToScan(
          new String[] { "com.maite.model.entities" });
 
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("spring.jpa.maite.hibernate.ddl-auto",
          env.getProperty("spring.jpa.maite.hibernate.ddl-auto"));
        properties.put("spring.jpa.maite.hibernate.dialect",
                env.getProperty("spring.jpa.maite.hibernate.dialect"));

        em.setJpaPropertyMap(properties);
 
        return em;
    }
 
    @Bean
    public DataSource maiteDataSource() {
  
        DriverManagerDataSource dataSource
          = new DriverManagerDataSource();
        dataSource.setDriverClassName(
          env.getProperty("spring.maite.driver-class-name"));
        dataSource.setUrl(env.getProperty("spring.maite.url"));
        dataSource.setUsername(env.getProperty("spring.maite.username"));
        dataSource.setPassword(env.getProperty("spring.maite.password"));
 
        return dataSource;
    }
 
    @Bean
    public PlatformTransactionManager maiteTransactionManager() {
  
        JpaTransactionManager transactionManager
          = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
          maiteEntityManager().getObject());
        return transactionManager;
    }
}