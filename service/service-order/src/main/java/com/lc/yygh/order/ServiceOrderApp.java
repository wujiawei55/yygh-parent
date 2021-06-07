package com.lc.yygh.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * ClassName ServiceOrderApp
 * Description
 * Create by lujun
 * Date 2021/5/15 13:52
 */

@SpringBootApplication
@ComponentScan(basePackages = {"com.lc.yygh"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.lc.yygh"})
public class ServiceOrderApp{
    public static void main(String[] args) {
        SpringApplication.run(ServiceOrderApp.class, args);
    }
}