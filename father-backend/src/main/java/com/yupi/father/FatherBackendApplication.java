package com.yupi.father;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties
@EnableScheduling
@MapperScan("com.yupi.father.mapper")
public class FatherBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(FatherBackendApplication.class, args);
    }

}
