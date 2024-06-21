package com.zqh.gulimail.gatway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/*
* 1、开启服务注册发现@EnableDiscoveryClient
*    （配置nacos的注册中心地址）
*
* */

@EnableDiscoveryClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class GulimailGatwayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimailGatwayApplication.class, args);
    }

}
