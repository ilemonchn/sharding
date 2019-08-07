package com.example.mysphere.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
public class MyBatisConfig {

    @Resource
    private DataSource shardingDataSource;

    @Bean(value = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory()throws Exception{
        return new SqlSessionFactoryBean(){{
           setDataSource(shardingDataSource);
           setMapperLocations(new PathMatchingResourcePatternResolver()
                   .getResources("classpath*:com/example/mysphere/mapper/*Mapper.xml"));
        }}.getObject();
    }


    @Bean("dataSource")
    public DataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
//        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/order01?useUnicode=true&characterEncoding=utf8");
        dataSource.setUsername("root");
        dataSource.setPassword("654321");
        return dataSource;
    }



}
