package com.misino.config;

import org.hibernate.ejb.HibernatePersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by misino on 12/16/13.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.misino.data.repositories")
@PropertySource("classpath:database.properties")
public class MysqlDatabaseConfig
{
    private static final String DB_DRIVER = "db.driver";
    private static final String DB_CONNECTION_URL = "db.url";
    private static final String DB_USER_NAME = "db.username";
    private static final String DB_USER_PASSWORD = "db.password";

    private static final String HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String ENTITYMANAGER_PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";

    @Resource
    Environment env;

    @Bean
    DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(  );
        dataSource.setDriverClassName( env.getProperty( DB_DRIVER ) );
        dataSource.setUrl( env.getRequiredProperty( DB_CONNECTION_URL ) );
        dataSource.setUsername( env.getRequiredProperty( DB_USER_NAME ) );
        dataSource.setPassword(env.getRequiredProperty( DB_USER_PASSWORD ));
        return dataSource;
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource( dataSource() );
        entityManagerFactory.setPersistenceProviderClass( HibernatePersistence.class );
        entityManagerFactory.setJpaProperties( hibProperties() );
        entityManagerFactory.setPackagesToScan( env.getRequiredProperty( ENTITYMANAGER_PACKAGES_TO_SCAN ) );
        return entityManagerFactory;
    }

    private Properties hibProperties() {
        Properties properties = new Properties();
        properties.put(HIBERNATE_DIALECT, env.getRequiredProperty(HIBERNATE_DIALECT));
        properties.put(HIBERNATE_SHOW_SQL, env.getRequiredProperty(HIBERNATE_SHOW_SQL));
        return properties;
    }

    @Bean
    PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager( );
        transactionManager.setEntityManagerFactory( entityManagerFactory().getObject() );
        return transactionManager;
    }
}
