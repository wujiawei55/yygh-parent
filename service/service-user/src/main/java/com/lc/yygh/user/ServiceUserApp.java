package com.lc.yygh.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//由于Swagger2Config在不同的包下，加注解扫描到它
@ComponentScan(basePackages = "com.lc.yygh")
@EnableDiscoveryClient
@EnableFeignClients(basePackages ="com.lc.yygh")
public class ServiceUserApp {
    public static void main(String[] args) {
        SpringApplication.run(ServiceUserApp.class,args);
    }
}
