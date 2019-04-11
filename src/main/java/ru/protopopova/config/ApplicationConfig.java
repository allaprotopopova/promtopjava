package ru.protopopova.config;

import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource("classpath:db/h2sqldb.properties")
@EnableJpaRepositories("ru.protopopova.repository")
@EnableTransactionManagement
public class ApplicationConfig {
    @Value("classpath:db/initDB_hsql.sql")
    private Resource schemaScript;
    @Value("classpath:db/populateDB.sql")
    private Resource dataScript;

    @Value("${database.url}")
    private String jdbcUrl;
    @Value("${database.username}")
    private String jdbcUserName;
    @Value("${database.password}")
    private String jdbcPassword;



    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(jdbcUserName);
        dataSource.setPassword(jdbcPassword);
        return dataSource;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        return new PropertySourcesPlaceholderConfigurer();
    }
    @Bean
    public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {
        final DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator());
        return initializer;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("ru.protopopova.model");
        factory.setDataSource(dataSource());
        factory.setJpaPropertyMap(getJpaPropertiesMap());
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    private Map<String, ?> getJpaPropertiesMap() {
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        map.put(AvailableSettings.FORMAT_SQL, true);
        map.put(AvailableSettings.USE_SQL_COMMENTS, true);
        map.put(AvailableSettings.JPA_PROXY_COMPLIANCE, false);
        return map;

    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory());
        return transactionManager;
    }

    private DatabasePopulator databasePopulator() {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(schemaScript);
        populator.addScript(dataScript);
        return populator;
    }

}

