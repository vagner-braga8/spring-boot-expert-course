package io.github.vagnerbraga8.libraryapi.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DataBaseConfiguration {

    @Value("${spring.datasource.url}")
    String url;

    @Value("${spring.datasource.username}")
    String username;

    @Value("${spring.datasource.password}")
    String password;

    @Value("${spring.datasource.driver-class-name}")
    String driver;

   // @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource(); //Não recomendado para usar em produção *larga escala
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setDriverClassName(driver);
        return ds;
    }

    @Bean
    public DataSource hikariDataSource(){

        HikariConfig config = new HikariConfig(); //recomendado *por default é utilizado o hikari

        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driver);

        config.setMaximumPoolSize(10); //Liberar o máximo de conexões
        config.setMinimumIdle(1); //O mínimo de conexões liberadas de início *tamanho inicial da pool
        config.setPoolName("library-db-pool");
        config.setMaxLifetime(600000); //600.000ms (máximo 10 min de vida. *Mata e reconecta a conexão a cada 10 min)
        config.setConnectionTimeout(100000); //tempo para tentar obter uma conexão
        config.setConnectionTestQuery("select 1"); //query de teste

        return new HikariDataSource(config);
    }
}
