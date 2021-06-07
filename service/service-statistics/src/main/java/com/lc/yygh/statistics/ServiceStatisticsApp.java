package com.lc.yygh.statistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * ClassName ServiceStatisticsApp
 * Description
 * Create by lujun
 * Date 2021/5/17 12:47
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//取消数据源自动配置
@ComponentScan(basePackages = {"com.lc.yygh"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.lc.yygh"})
public class ServiceStatisticsApp {
    public static void main(String[] args) {
        SpringApplication.run(ServiceStatisticsApp.class, args);
    }
}
