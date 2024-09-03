package com.zqh.gulimail.ware.config;


import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement//开启事务
@MapperScan("com.zqh.gulimail.ware.dao")
public class MyBatisConfig {
    //分页插件
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
//        设置请求页面对于最大页后操作，ture调回首页，false继续请求，默认false
//        paginationInterceptor.setOverflow(true);
////        设置最大单页限制数量，默认500，-1无限
//        paginationInterceptor.setLimit(1000);

        return paginationInterceptor;
    }
    
}
