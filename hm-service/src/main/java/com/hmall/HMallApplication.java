package com.hmall;

import com.hmall.api.config.config;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@MapperScan("com.hmall.mapper")
@EnableFeignClients(basePackages = "com.hmall.api.client", defaultConfiguration = config.class)
@SpringBootApplication
public class HMallApplication {
    public static void main(String[] args) {
        SpringApplication.run(HMallApplication.class, args);
    }
}