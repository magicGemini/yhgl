package com.cec6.yhgl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("com.cec6.yhgl.dao")
@ComponentScan(basePackages = {"com.cec6.yhgl.config",
        "com.cec6.yhgl.controller",
        "com.cec6.yhgl.service"})
@SpringBootApplication
public class YhglApplication {

    public static void main(String[] args) {
        SpringApplication.run(YhglApplication.class, args);
    }

}
