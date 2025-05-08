package org.example.railwayapp.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "railwayEntityManagerFactory",
        transactionManagerRef = "railwayTransactionManager",
        basePackages = {"org.example.railwayapp.repository.railway"} // Репозитории для railway_station
)
public class RailwayDbConfig {

    @Bean(name = "railwayDataSourceProperties")
    @ConfigurationProperties("spring.datasource.railway")
    public DataSourceProperties railwayDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "railwayDataSource")
    @ConfigurationProperties("spring.datasource.railway.configuration")
    public DataSource railwayDataSource(@Qualifier("railwayDataSourceProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean(name = "railwayEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean railwayEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("railwayDataSource") DataSource dataSource) {

        Map<String, String> jpaProperties = new HashMap<>();


        return builder
                .dataSource(dataSource)
                .packages("org.example.railwayapp.model.railway") // Сущности для railway_station
                .persistenceUnit("railway")
                .properties(jpaProperties)
                .build();
    }

    @Bean(name = "railwayTransactionManager")
    public PlatformTransactionManager railwayTransactionManager(
            @Qualifier("railwayEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}