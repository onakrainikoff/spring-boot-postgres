package ru.on8off.postgres.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"ru.on8off.postgres.repository.masterdb"},
        transactionManagerRef = "transactionManager",
        entityManagerFactoryRef = "entityManagerFactory"
)
public class MasterDBConfiguration {

    @Bean
    @Primary
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager manager = new JpaTransactionManager(entityManagerFactory().getObject());
        manager.setNestedTransactionAllowed(true);
        return manager;
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean  entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource());
        emf.setPackagesToScan("ru.on8off.postgres.repository.masterdb.entity");
        emf.setPersistenceUnitName("postgresql-master");
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.put("hibernate.jdbc.lob.non_contextual_creation", true);
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.format_sql", true);
        emf.setJpaPropertyMap(properties);
        return emf;
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix="db.master")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }
}
