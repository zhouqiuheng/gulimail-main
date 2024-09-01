package com.zqh.gulimail.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


/*
*
* 1.逻辑删除
*  1)配置全局的逻辑删除（可省略）
*  2）配置bean（mybatis-plus3.1.1后可以省略）
*  3）给Bean（Entity）加上逻辑删除注解@Tablelgic
*
* 2.JSRR303(数据校验)
*  1）给Bean添加校验注解javax.validation.constraints，并定义message信息;
*  2）开启校验功能@Valid
*  3)给校验的Bean后跟一个BindingResult,可获得结果
*  4）分组校验
*      1.	@NotNull(message = "修改必须指定Id",groups = {UpdateGroup.class})
	@Null(message = "新增不能指定Id",groups = {AddGroup.class})给校验信息分组
*      2.使用@Validated设置方法的校验分组
*  5)自定义校验
*     1.编写一个自定义的校验注解
*     2.编写一个自定义的校验器关联校验器和注解ConstraintValidator(可以指定多个不同的校验器，适应不同类型的校验)
*
* 3.全局校验
*   1)编写异常处理类，使用@ControllerAdvice
*   2）使用 @ExceptionHandler标注方法可以处理的异常
* */

//一般会主动扫描有@FeignClients的接口
@EnableFeignClients(basePackages = "com.zqh.gulimail.product.feign")
@EnableDiscoveryClient
@SpringBootApplication
public class GulimailProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimailProductApplication.class, args);
    }

}
