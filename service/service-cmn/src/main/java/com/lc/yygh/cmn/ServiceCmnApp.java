package com.lc.yygh.cmn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * ClassName ServiceHospApp
 * Description
 * Create by lujun
 * Date 2021/4/16 21:16
 */
@SpringBootApplication
//由于Swagger2Config在不同的包下，加注解扫描到它
@ComponentScan(basePackages = "com.lc.yygh")
@EnableDiscoveryClient
public class ServiceCmnApp {
    public static void main(String[] args) {
        SpringApplication.run(ServiceCmnApp.class,args);
    }
}
