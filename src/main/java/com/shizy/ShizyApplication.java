package com.shizy;


import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Import(DynamicDataSourceAutoConfiguration.class)//mybatis-plus 多数据源支持
@SpringBootApplication
@EnableTransactionManagement
public class ShizyApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShizyApplication.class, args);
    }
}
