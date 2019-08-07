package com.example.mysphere;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.example.mysphere.mapper")
@SpringBootApplication
public class MysphereApplication {

    public static void main(String[] args) {
        SpringApplication.run(MysphereApplication.class, args);
    }

}
