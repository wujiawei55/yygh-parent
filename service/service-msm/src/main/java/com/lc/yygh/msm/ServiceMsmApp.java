package com.lc.yygh.msm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
//exclude = DataSourceAutoConfiguration.class:取消数据源的自动配置
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
//由于Swagger2Config在不同的包下，加注解扫描到它
@ComponentScan(basePackages = "com.lc.yygh")
@EnableDiscoveryClient
//@EnableFeignClients(basePackages ="com.lc.yygh")
public class ServiceMsmApp {
    public static void main(String[] args) {
        SpringApplication.run(ServiceMsmApp.class,args);
    }
}
