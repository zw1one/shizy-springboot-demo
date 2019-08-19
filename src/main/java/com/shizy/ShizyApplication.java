package com.shizy;


import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;


//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})//不用数据库的话就加上这个排除
@SpringBootApplication
@Import(DynamicDataSourceAutoConfiguration.class)
public class ShizyApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShizyApplication.class, args);
    }
}
