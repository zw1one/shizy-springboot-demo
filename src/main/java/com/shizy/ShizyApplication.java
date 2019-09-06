package com.shizy;


import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@Import(DynamicDataSourceAutoConfiguration.class)//mybatis-plus 多数据源支持
@SpringBootApplication
public class ShizyApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShizyApplication.class, args);
    }
}
