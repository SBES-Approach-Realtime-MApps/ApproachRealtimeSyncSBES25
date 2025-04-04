package br.unesp.rc.MSReplicator.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = "br.unesp.rc.ReservationModel.repository",
    entityManagerFactoryRef = "mariaDBEntityManagerFactory",
    transactionManagerRef = "mariaDBTransactionManager"
)
public class MariaDBConfig {

    @Primary
    @Bean(name = "mariaDBDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.mariadb")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "mariaDBEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
        EntityManagerFactoryBuilder builder,
        @Qualifier("mariaDBDataSource") DataSource dataSource
    ){
        return builder
            .dataSource(dataSource)
            .packages("br.unesp.rc.ReservationModel.model")
            .persistenceUnit("mariaDbPU")
            .build();
    }

    @Primary
    @Bean(name = "mariaDBTransactionManager")
    public PlatformTransactionManager transactionManager(
        @Qualifier("mariaDBEntityManagerFactory") EntityManagerFactory entityManagerFactory
    ) {
       return new JpaTransactionManager(entityManagerFactory); 
    }
}
