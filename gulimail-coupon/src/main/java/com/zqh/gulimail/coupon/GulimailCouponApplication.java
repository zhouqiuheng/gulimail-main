package com.zqh.gulimail.coupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/*
*
* 1.使用nacos作为配置中心管理配置
*   1）。引入依赖
*      <dependency>
*           <groupId>com.alibaba.cloud</groupId>
*           <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
*        </dependency>
*   2）创建一个bootstrap.properties
*     spring.application.name=gulimail-coupon
*     spring.cloud.nacos.config.server-addr=127.0.0.1:8848
*     spring.cloud.nacos.config.username=nacos
*     spring.cloud.nacos.config.password=nacos
*     spring.config.import=nacos:nacos-config-example.properties?refreshEnabled=true&group=DEFAULT_GROUP
*     1.在application.properties里面加上spring.cloud.bootstrap.enabled=true
*     2.导入依赖
*       <dependency>
*            <groupId>org.springframework.cloud</groupId>
*            <artifactId>spring-cloud-starter-bootstrap</artifactId>
*       </dependency>
*  3）给配置中心默认添加以data ID （应用名 如：gulimail-coupon）.properties，在里面添加配置
*  4)动态获取配置
*     @RefreshScope动态获取并刷新配置
*     @Value("${配置项目的名字}")
* 同时使用会优先使用配置中心里面的配置
*
* 2.细节
*   1).命名空间
*      默认：public(保留空间)；默认新增 的所有配置都在public空间
*      1、开发，测试，生产：利用命名空间做环境隔离
*        在bootstrap.properties:配置上选择哪个环境（用的是命名空间的ID）spring.cloud.nacos.config.namespace=gulimail-coupon-prop
*      2、每一个微服务之间互相隔离配置，每一个微服务都创建自己的命名空间，只加载自己命名空间下的所用配置
*   2）.配置集
*      所用的配置的集合
*   3）.配置集ID
*        类似文件名
*          Data Id
*   4）.配置分组
*       默认配置集都是DEFAULT_GROUP
*        11.11用1个配置，6.18用一个配置
*       在bootstrap.properties:spring.cloud.nacos.config.group=组名
* 每个微服务创建自己的命名空间，使用配置分组区分环境
* 3、同时加载多个配置集
*   1）微服务任何配置信息，任何配置文件都可以放在配置中心中
*   2）只需要在bootstrap.properties中声明配置中心的哪些配置文件即可
* */


@EnableDiscoveryClient
@SpringBootApplication
public class GulimailCouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimailCouponApplication.class, args);
    }

}
