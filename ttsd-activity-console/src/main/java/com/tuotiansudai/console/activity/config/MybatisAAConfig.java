package com.tuotiansudai.console.activity.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class MybatisAAConfig {

    @Bean
    @ConfigurationProperties(prefix="spring.datasource.aa")
    public DataSource hikariCPAADataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    public MapperScannerConfigurer aaMapperScannerConfigurer() {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setBasePackage("com.tuotiansudai.repository.mapper");
        configurer.setSqlSessionFactoryBeanName("aaSqlSessionFactory");
        return configurer;
    }

    @Bean
    public DataSourceTransactionManager aaTransactionManager(@Qualifier("hikariCPAADataSource") DataSource hikariCPAADataSource) {
        return new DataSourceTransactionManager(hikariCPAADataSource);
    }

    @Bean
    public SqlSessionFactory aaSqlSessionFactory(@Qualifier("hikariCPAADataSource") DataSource hikariCPAADataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(hikariCPAADataSource);
        sessionFactory.setTypeAliasesPackage("com.tuotiansudai.repository.model");
        return sessionFactory.getObject();
    }
}
