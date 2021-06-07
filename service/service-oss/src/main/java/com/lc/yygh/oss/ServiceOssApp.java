package com.lc.yygh.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * ClassName ServiceOssApp
 * Description
 * Create by lujun
 * Date 2021/5/10 21:11
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = "com.lc.yygh")
@EnableDiscoveryClient
public class ServiceOssApp {
    public static void main(String[] args) {
        SpringApplication.run(ServiceOssApp.class,args);
    }
}
