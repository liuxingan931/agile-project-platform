package com.agile;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.agile.module_b.mapper","com.agile.module_c.mapper","com.agile.module_a.mapper","com.agile.module_e.mapper"})
public class AgileApplication {
    public static void main(String[] args) {
        SpringApplication.run(AgileApplication.class, args);
    }
}
