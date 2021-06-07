package com.lc.yygh.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * ClassName ServiceTaskApp
 * Description
 * Create by lujun
 * Date 2021/5/17 5:27
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})//取消数据源自动配置
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.lc.yygh"})
public class ServiceTaskApp {
    public static void main(String[] args) {
        SpringApplication.run(ServiceTaskApp.class, args);
    }
}